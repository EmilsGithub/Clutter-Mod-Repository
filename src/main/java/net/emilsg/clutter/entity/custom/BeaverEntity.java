package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.entity.custom.goal.HighWanderAroundFarGoal;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BeaverEntity extends ClutterAnimalEntity {
    private static final TrackedData<BlockPos> HOME_POS = DataTracker.registerData(BeaverEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);

    private static final Ingredient BREEDING_INGREDIENT = getIngredientWithName("sapling");

    public final AnimationState waterAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    protected final SwimNavigation waterNavigation;
    protected final MobNavigation landNavigation;
    private int waterAnimationTimeout = 0;
    private int idleAnimationTimeout = 0;

    public BeaverEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setStepHeight(1.0f);
        this.moveControl = new BeaverMoveControl(this);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.waterNavigation = new SwimNavigation(this, world);
        this.landNavigation = new MobNavigation(this, world);
    }

    private static Ingredient getIngredientWithName(String name) {
        List<Item> items = new ArrayList<>();

        Registries.ITEM.forEach(item -> {
            Identifier id = Registries.ITEM.getId(item);
            if (id.getPath().contains(name)) {
                items.add(item);
            }
        });

        return Ingredient.ofItems(items.toArray(new Item[0]));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) || world.getBlockState(pos).isOf(Blocks.WATER));
    }

    private void setupAnimationStates() {
        if (this.waterAnimationTimeout <= 0) {
            this.waterAnimationTimeout = 20;
            this.waterAnimationState.startIfNotRunning(this.age);
        } else {
            --this.waterAnimationTimeout;
        }

        if (this.idleAnimationTimeout <= 0 && !this.isMoving() && this.getNavigation().isIdle()) {
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.startIfNotRunning(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (world.isClient) {
            this.setupAnimationStates();
        }
    }

    protected void updateLimbs(float v) {
        float f;
        if (this.getPose() == EntityPose.STANDING) {
            f = Math.min(v * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.limbAnimator.updateLimbs(f, 0.5F);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HOME_POS, BlockPos.ORIGIN);
    }

    BlockPos getHomePos() {
        return this.dataTracker.get(HOME_POS);
    }

    public void setHomePos(BlockPos pos) {
        this.dataTracker.set(HOME_POS, pos);
    }

    @Override
    public void detachLeash(boolean sendPacket, boolean dropItem) {
        this.setHomePos(this.getBlockPos());
        super.detachLeash(sendPacket, dropItem);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AnimalMateGoal(this, 1.0f));
        this.goalSelector.add(1, new BeaverTemptGoal(this, 1.1f, BREEDING_INGREDIENT, false));
        this.goalSelector.add(2, new FollowParentGoal(this, 1.0f));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.add(4, new HighWanderAroundFarGoal(this, 1.0f, 0.001f));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(3, new RevengeGoal(this));

    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("HomePosX", this.getHomePos().getX());
        nbt.putInt("HomePosY", this.getHomePos().getY());
        nbt.putInt("HomePosZ", this.getHomePos().getZ());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        int i = nbt.getInt("HomePosX");
        int j = nbt.getInt("HomePosY");
        int k = nbt.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setHomePos(this.getBlockPos());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public float getScaleFactor() {
        return this.isBaby() ? 0.6F : 1.0F;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stackInHand = player.getStackInHand(hand);

        String itemID = Registries.ITEM.getId(stackInHand.getItem()).toString();
        Block heldBlock = Block.getBlockFromItem(stackInHand.getItem());

        if (itemID.contains("planks")) {
            ItemStack sticksWithCount = new ItemStack(Items.STICK);
            sticksWithCount.setCount(random.nextBetween(2, 3));
            this.dropStack(sticksWithCount);
            this.getWorld().addBlockBreakParticles(this.getBlockPos(), heldBlock.getDefaultState());
            if (!player.getAbilities().creativeMode) {
                stackInHand.decrement(1);
            }
            playSound(SoundEvents.BLOCK_WOOD_BREAK, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }

        String strippedPath = itemID.replace(":", ":stripped_");
        Identifier strippedID = new Identifier(strippedPath);
        if (Registries.ITEM.containsId(strippedID)) {
            this.dropStack(new ItemStack(Registries.ITEM.get(strippedID)));
            this.getWorld().addBlockBreakParticles(this.getBlockPos(), heldBlock.getDefaultState());
            if (!player.getAbilities().creativeMode) {
                stackInHand.decrement(1);
            }
            playSound(SoundEvents.BLOCK_WOOD_BREAK, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    protected EntityNavigation createNavigation(World world) {
        return new BeaverSwimNavigation(this, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntityTypes.BEAVER.create(world);
    }

    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement() && this.isTouchingWater()) {
            this.updateVelocity(0.1F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }

    }

    private static class BeaverMoveControl extends MoveControl {
        private final BeaverEntity beaver;

        public BeaverMoveControl(BeaverEntity beaver) {
            super(beaver);
            this.beaver = beaver;
        }

        public void tick() {
            if (this.beaver.isTouchingWater()) {
                this.beaver.setVelocity(this.beaver.getVelocity().add(0.0, 0.005, 0.0));

                if (this.state != State.MOVE_TO || this.beaver.getNavigation().isIdle()) {
                    this.beaver.setMovementSpeed(0.0F);
                    return;
                }

                double d = this.targetX - this.beaver.getX();
                double e = this.targetY - this.beaver.getY();
                double f = this.targetZ - this.beaver.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                e /= g;
                float h = (float) (MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F;
                this.beaver.setYaw(this.wrapDegrees(this.beaver.getYaw(), h, 90.0F));
                this.beaver.bodyYaw = this.beaver.getYaw();
                float i = (float) (this.speed * this.beaver.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                float j = MathHelper.lerp(0.125F, this.beaver.getMovementSpeed(), i);
                this.beaver.setMovementSpeed(j);
                this.beaver.setVelocity(this.beaver.getVelocity().add(0, this.beaver.getMovementSpeed() * e * 0.1, 0));
            } else {
                this.beaver.setVelocity(this.beaver.getVelocity().add(0.0, 0, 0.0));
                super.tick();
            }
        }
    }

    private static class BeaverTemptGoal extends TemptGoal {
        private final BeaverEntity beaver;

        public BeaverTemptGoal(PathAwareEntity entity, double speed, Ingredient food, boolean canBeScared) {
            super(entity, speed, food, canBeScared);
            this.beaver = (BeaverEntity) entity;
        }

        @Override
        public void stop() {
            super.stop();
            this.beaver.setHomePos(this.beaver.getBlockPos());
        }
    }

    private static class BeaverSwimNavigation extends AmphibiousSwimNavigation {
        BeaverSwimNavigation(BeaverEntity owner, World world) {
            super(owner, world);
        }

        public boolean isValidPosition(BlockPos pos) {
            if (this.entity instanceof BeaverEntity) {
                return this.world.getBlockState(pos).isOf(Blocks.WATER) || (this.world.getBlockState(pos).isReplaceable() && this.world.getBlockState(pos.down()).isSolidBlock(this.world, pos.down()));
            }

            return !this.world.getBlockState(pos.down()).isAir();
        }
    }
}
