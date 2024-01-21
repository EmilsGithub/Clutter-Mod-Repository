package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
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
import net.minecraft.registry.tag.BiomeTags;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeaverEntity extends ClutterAnimalEntity {
    private static final TrackedData<BlockPos> HOME_POS;

    private static final Ingredient BREEDING_INGREDIENT = getIngredientWithName("sapling");

    public final AnimationState idleTailAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

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

    private static final Map<Block, Block> STRIPPABLE_LOGS_MAP = new HashMap<>();

    static {
        HOME_POS = DataTracker.registerData(BeaverEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
        STRIPPABLE_LOGS_MAP.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK);
        STRIPPABLE_LOGS_MAP.put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
        STRIPPABLE_LOGS_MAP.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
        STRIPPABLE_LOGS_MAP.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
        STRIPPABLE_LOGS_MAP.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
        STRIPPABLE_LOGS_MAP.put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);
        STRIPPABLE_LOGS_MAP.put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
        STRIPPABLE_LOGS_MAP.put(ModBlocks.REDWOOD_LOG, ModBlocks.STRIPPED_REDWOOD_LOG);
        STRIPPABLE_LOGS_MAP.put(ModBlocks.REDWOOD_WOOD, ModBlocks.STRIPPED_REDWOOD_WOOD);
    }

    public BeaverEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setStepHeight(1.0f);
        this.moveControl = new BeaverMoveControl(this);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !this.isMoving()) {
            this.idleAnimationTimeout = 40;
            this.idleTailAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if(world.isClient) {
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

        this.limbAnimator.updateLimbs(f, 0.2F);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HOME_POS, BlockPos.ORIGIN);
    }

    public void setHomePos(BlockPos pos) {
        this.dataTracker.set(HOME_POS, pos);
    }

    BlockPos getHomePos() {
        return (BlockPos)this.dataTracker.get(HOME_POS);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    @Override
    public void detachLeash(boolean sendPacket, boolean dropItem) {
        this.setHomePos(this.getBlockPos());
        super.detachLeash(sendPacket, dropItem);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AnimalMateGoal(this,1.0f));
        this.goalSelector.add(1, new BeaverTemptGoal(this, 1.1f, BREEDING_INGREDIENT, false));
        this.goalSelector.add(2, new FollowParentGoal(this, 1.0f));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.add(4, new BeaverWanderAroundFarGoal(this, 1.0f, 0.001f));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));

        this.targetSelector.add(3, new RevengeGoal(this));

    }

    static class BeaverTemptGoal extends TemptGoal {
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

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return (world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) || world.getBlockState(pos).isOf(Blocks.WATER) && world.getBiome(pos).isIn(BiomeTags.IS_RIVER));
    }

    @Override
    public float getScaleFactor() {
        return this.isBaby() ? 0.6F : 1.0F;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stackInHand = player.getStackInHand(hand);
        if(stackInHand.isIn(ModItemTags.STRIPPABLE_LOGS)) {
            Block heldBlock = Block.getBlockFromItem(stackInHand.getItem());
            Item strippedBlock = STRIPPABLE_LOGS_MAP.get(heldBlock).asItem();
            dropItem(strippedBlock);
            getWorld().addBlockBreakParticles(getBlockPos(), heldBlock.getDefaultState());
            if(!player.getAbilities().creativeMode) {
                stackInHand.decrement(1);
            }
            playSound(SoundEvents.BLOCK_WOOD_BREAK, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        } else if(Registries.ITEM.getId(stackInHand.getItem()).getPath().contains("planks")) {
            Block heldBlock = Block.getBlockFromItem(stackInHand.getItem());
            ItemStack sticksWithCount = new ItemStack(Items.STICK);
            sticksWithCount.setCount(random.nextBetween(2, 4));
            dropStack(sticksWithCount);
            getWorld().addBlockBreakParticles(getBlockPos(), heldBlock.getDefaultState());
            if(!player.getAbilities().creativeMode) {
                stackInHand.decrement(1);
            }
            playSound(SoundEvents.BLOCK_WOOD_BREAK, 1.0f, 1.0f);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }

    public static class BeaverWanderAroundFarGoal extends WanderAroundGoal {
        protected final float probability;

        public BeaverWanderAroundFarGoal(PathAwareEntity mob, double speed, float probability) {
            super(mob, speed);
            this.probability = probability;
        }

        @Nullable
        protected Vec3d getWanderTarget() {
            Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
            return vec3d == null ? super.getWanderTarget() : vec3d;
        }
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
        return ModEntities.BEAVER.create(world);
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

    static class BeaverMoveControl extends MoveControl {
        private final BeaverEntity beaver;

        BeaverMoveControl(BeaverEntity beaver) {
            super(beaver);
            this.beaver = beaver;
        }

        private void updateVelocity() {
            if (this.beaver.isTouchingWater()) {
                this.beaver.setVelocity(this.beaver.getVelocity().add(0.0, 0.005, 0.0));
                this.beaver.setMovementSpeed(Math.max(this.beaver.getMovementSpeed() / 2.0F, 0.08F));
                if (this.beaver.isBaby()) {
                    this.beaver.setMovementSpeed(Math.max(this.beaver.getMovementSpeed() / 3.0F, 0.06F));
                }
            } else if (this.beaver.isOnGround()) {
                this.beaver.setMovementSpeed(this.beaver.getMovementSpeed());
            }

        }

        public void tick() {
            this.updateVelocity();
            if (this.state == State.MOVE_TO && !this.beaver.getNavigation().isIdle()) {
                double x = this.targetX - this.beaver.getX();
                double y = this.targetY - this.beaver.getY();
                double z = this.targetZ - this.beaver.getZ();
                double sqrt = Math.sqrt(x * x + y * y + z * z);
                if (sqrt < 9.999999747378752E-6) {
                    this.entity.setMovementSpeed(0.0F);
                } else {
                    y /= sqrt;
                    float h = (float)(MathHelper.atan2(z, x) * 57.2957763671875) - 90.0F;
                    this.beaver.setYaw(this.wrapDegrees(this.beaver.getYaw(), h, 90.0F));
                    this.beaver.bodyYaw = this.beaver.getYaw();
                    float i = (float)(this.speed * this.beaver.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    this.beaver.setMovementSpeed(MathHelper.lerp(0.125F, this.beaver.getMovementSpeed(), i));
                    this.beaver.setVelocity(this.beaver.getVelocity().add(0.0, (double)this.beaver.getMovementSpeed() * y * 0.1, 0.0));
                }
            } else {
                this.beaver.setMovementSpeed(0.0F);
            }
        }
    }

    static class BeaverSwimNavigation extends AmphibiousSwimNavigation {
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
