package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.entity.variants.EchofinVariant;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.Objects;

public class EchofinEntity extends ClutterAnimalEntity implements GeoEntity {

    private static final RawAnimation SWIM = RawAnimation.begin().thenLoop("echofin.swimming");
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<BlockPos> HOME_POS;

    public EchofinEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.lookControl = new EchofinLookControl(this);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HOME_POS, BlockPos.ORIGIN);
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isPersistent() && this.getWorld().getDimensionEntry().matchesKey(DimensionTypes.THE_END);
    }

    @Override
    public int getLimitPerChunk() {
        return 5;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        if (!this.getWorld().isClient && heldItem.isOf(Items.BUCKET)) {
            EchofinVariant variant = this.getVariant();
            Item returnItem;
            if (Objects.requireNonNull(variant) == EchofinVariant.CHORUS) {
                returnItem = ModItems.CHORUS_ECHOFIN_BUCKET;
            } else {
                returnItem = ModItems.LEVITATING_ECHOFIN_BUCKET;
            }
            if(player.getStackInHand(hand).getCount() == 1) {
                player.setStackInHand(hand, new ItemStack(returnItem));
            } else {
                heldItem.decrement(1);
                if(player.getInventory().getEmptySlot() > 0) {
                    player.giveItemStack(new ItemStack(returnItem));
                } else {
                    this.dropItem(returnItem);
                }
            }
            player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, SoundCategory.PLAYERS, 1.0f, 1.5f);
            this.remove(RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    public void setHomePos(BlockPos pos) {
        this.dataTracker.set(HOME_POS, pos);
    }

    BlockPos getHomePos() {
        return (BlockPos)this.dataTracker.get(HOME_POS);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean isExperienceDroppingDisabled() {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new EchofinWanderAroundGoal());
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    class EchofinWanderAroundGoal extends Goal {
        private BlockPos homePos;

        EchofinWanderAroundGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            return EchofinEntity.this.navigation.isIdle() && EchofinEntity.this.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return EchofinEntity.this.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                EchofinEntity.this.navigation.startMovingAlong(EchofinEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }
        }

        private Vec3d getRandomLocation() {
            if (this.homePos == null) {
                this.homePos = EchofinEntity.this.getHomePos();
            }

            Vec3d vec3d2 = EchofinEntity.this.getRotationVec(0.0F);
            Vec3d vec3d3 = AboveGroundTargeting.find(EchofinEntity.this, 24, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 2);

            if (vec3d3 != null && EchofinEntity.this.getBlockPos().getSquaredDistance(Vec3d.ofCenter(homePos)) > 2 * 2) {
                return vec3d3;
            }

            if (EchofinEntity.this.getWorld().isNight() && EchofinEntity.this.getBlockPos().getSquaredDistance(Vec3d.ofCenter(homePos)) > 8 * 8) {
                return Vec3d.ofCenter(homePos);
            }

            BlockPos blockpos = homePos.add(-2 + EchofinEntity.this.random.nextInt(5), -1 + EchofinEntity.this.random.nextInt(3), -2 + EchofinEntity.this.random.nextInt(5));

            if (!EchofinEntity.this.getWorld().getBlockState(blockpos).isOpaque()) {
                return Vec3d.ofCenter(blockpos);
            }

            return null;
        }
    }

    @Override
    public boolean shouldSpawnSprintingParticles() {
        return false;
    }

    static class EchofinLookControl extends LookControl {
        EchofinLookControl(MobEntity entity) {
            super(entity);
        }

        public void tick() {
            super.tick();
        }

        protected boolean shouldStayHorizontal() {
            return true;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.getController().getCurrentAnimation() == null) {
            tAnimationState.getController().setAnimation(SWIM);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }



    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("HomePosX", this.getHomePos().getX());
        nbt.putInt("HomePosY", this.getHomePos().getY());
        nbt.putInt("HomePosZ", this.getHomePos().getZ());
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        int i = nbt.getInt("HomePosX");
        int j = nbt.getInt("HomePosY");
        int k = nbt.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    public static boolean isValidSpawn(EntityType<? extends ClutterAnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isOf(Blocks.END_STONE);
    }

    @Override
    public void tick() {
        if (this.getVariant() == EchofinVariant.CHORUS && random.nextBoolean()) {
            this.getWorld().addParticle(ParticleTypes.PORTAL, true, (double) this.getX() + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), this.getY() + random.nextDouble() / 16.0 * (double) (random.nextBoolean() ? 1 : -1), (double) this.getZ() + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), (random.nextBoolean() ? 0.1 : -0.1), (random.nextBoolean() ? 0.1 : -0.1), (random.nextBoolean() ? 0.1 : -0.1));
        }
        super.tick();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_WOOL_HIT;
    }

    @Override
    public boolean isFireImmune() {
        return this.getVariant().getId() >= 16;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_COD_DEATH;
    }

    /* VARIANTS */
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(EchofinEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EchofinVariant variant = Util.getRandom(EchofinVariant.values(), this.random);

        setVariant(variant);
        this.setHomePos(this.getBlockPos());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void refreshPositionAndAngles(BlockPos pos, float yaw, float pitch) {
        this.setHomePos(pos);
        super.refreshPositionAndAngles(pos, yaw, pitch);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public EchofinVariant getVariant() {
        return EchofinVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(EchofinVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    static {
        HOME_POS = DataTracker.registerData(EchofinEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    }
}