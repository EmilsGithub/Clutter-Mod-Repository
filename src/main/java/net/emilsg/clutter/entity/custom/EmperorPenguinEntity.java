package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.goal.*;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class EmperorPenguinEntity extends ClutterAnimalEntity {
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ItemTags.FISHES);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(EmperorPenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> EGG_TIMER = DataTracker.registerData(EmperorPenguinEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState flapAnimationStateOne = new AnimationState();
    public final AnimationState flapAnimationStateTwo = new AnimationState();
    public final AnimationState preenAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();

    public int flapAnimationTimeout = 0;

    protected final SwimNavigation waterNavigation;
    protected final MobNavigation landNavigation;

    //TODO fix movement
    //TODO fix hitbox
    public EmperorPenguinEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setStepHeight(1.0f);
        this.moveControl = new EmperorPenguinMoveControl3(this);
        this.lookControl = new YawAdjustingLookControl(this, 90);
        this.waterNavigation = new SwimNavigation(this, world);
        this.landNavigation = new MobNavigation(this, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.14f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    public void updateSwimming() {
        if (!this.getWorld().isClient) {
            if (this.canMoveVoluntarily() && this.isTouchingWater()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.landNavigation;
                this.setSwimming(false);
            }
        }

    }

    public boolean isInSwimmingPose() {
        return this.isSwimming();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new EmperorPenguinMateGoal(this, 1));
        this.goalSelector.add(3, new EmperorPenguinLayEggGoal(this, 1, ModBlocks.EMPEROR_PENGUIN_EGG.getDefaultState()));
        this.goalSelector.add(4, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.25, true));
        this.goalSelector.add(7, new WanderAroundOnSurfaceGoal(this, 1f));
        this.goalSelector.add(8, new LeaveWaterGoal(this, 1.0));
        this.goalSelector.add(9, new HighWanderAroundFarGoal(this, 1, 1));
        this.goalSelector.add(10, new EmperorPenguinConditionalLookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(11, new EmperorPenguinConditionalLookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, SalmonEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, CodEntity.class, true));
    }

    private static class LeaveWaterGoal extends MoveToTargetPosGoal {
        private final EmperorPenguinEntity emperorPenguinEntity;

        public LeaveWaterGoal(EmperorPenguinEntity emperorPenguinEntity, double speed) {
            super(emperorPenguinEntity, speed, 8, 2);
            this.emperorPenguinEntity = emperorPenguinEntity;
        }

        public boolean canStart() {
            return super.canStart() && this.emperorPenguinEntity.getNavigation().isIdle() && this.emperorPenguinEntity.isTouchingWater() && this.emperorPenguinEntity.getY() >= (double)(this.emperorPenguinEntity.getWorld().getSeaLevel() - 3);
        }

        public boolean shouldContinue() {
            return super.shouldContinue();
        }

        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockPos blockPos = pos.up();
            return world.isAir(blockPos) && world.isAir(blockPos.up()) && world.getBlockState(pos).hasSolidTopSurface(world, pos, this.emperorPenguinEntity);
        }

        public void start() {
            this.emperorPenguinEntity.navigation = this.emperorPenguinEntity.landNavigation;
            super.start();
        }

        public void stop() {
            super.stop();
        }
    }

    private static class WanderAroundOnSurfaceGoal extends Goal {
        private final PathAwareEntity mob;
        private double x;
        private double y;
        private double z;
        private final double speed;
        private final World world;

        public WanderAroundOnSurfaceGoal(PathAwareEntity mob, double speed) {
            this.mob = mob;
            this.speed = speed;
            this.world = mob.getWorld();
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            if (!this.world.isDay()) {
                return false;
            } else if (this.mob.isTouchingWater()) {
                return false;
            } else {
                Vec3d vec3d = this.getWanderTarget();
                if (vec3d == null) {
                    return false;
                } else {
                    this.x = vec3d.x;
                    this.y = vec3d.y;
                    this.z = vec3d.z;
                    return true;
                }
            }
        }

        public boolean shouldContinue() {
            return !this.mob.getNavigation().isIdle();
        }

        public void start() {
            this.mob.getNavigation().startMovingTo(this.x, this.y, this.z, this.speed);
        }

        @Nullable
        private Vec3d getWanderTarget() {
            Random random = this.mob.getRandom();
            BlockPos blockPos = this.mob.getBlockPos();

            for(int i = 0; i < 10; ++i) {
                BlockPos blockPos2 = blockPos.add(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
                if (this.world.getBlockState(blockPos2).isOf(Blocks.WATER)) {
                    return Vec3d.ofBottomCenter(blockPos2);
                }
            }

            return null;
        }
    }

    private void setupAnimationStates() {
        if (this.isTouchingWater()) {
            this.swimAnimationState.startIfNotRunning(this.age);
        } else {
            this.swimAnimationState.stop();
        }

        if (this.flapAnimationTimeout <= 0 && random.nextInt(200) == 0 && this.getNavigation().isIdle() && !this.isMoving() && !this.isTouchingWater()) {
            this.flapAnimationTimeout = 40;
            this.pickRandomIdleAnim(random.nextInt(3));
        } else {
            --this.flapAnimationTimeout;
        }
    }

    private void pickRandomIdleAnim(int i) {
        switch (i) {
            default -> this.flapAnimationStateOne.startIfNotRunning(this.age);
            case 1 -> this.flapAnimationStateTwo.startIfNotRunning(this.age);
            case 2 -> this.preenAnimationState.startIfNotRunning(this.age);
        };
    }

    protected void updateLimbs(float v) {
        float f;
        if (this.getPose() == EntityPose.STANDING) {
            f = Math.min(v * 6.0f, 1.0f);
        } else {
            f = 0.0f;
        }

        this.limbAnimator.updateLimbs(f, 1f);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (world.isClient) {
            this.setupAnimationStates();
        }
    }

    public boolean isPushedByFluids() {
        return !this.isSwimming();
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.FISHES);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient) {
            if (this.hasEgg()) this.setEggTimer(this.getEggTimer() + 1);

        }
    }

    @Override
    public Box getBoundingBox(EntityPose pos) {
        Box originalBox = super.getBoundingBox();

        if (this.isTouchingWater()) {
            double heightReduction = (originalBox.getYLength() / 2);
            return originalBox.shrink(0, heightReduction, 0);
        }

        return originalBox;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        EntityDimensions dimensions = super.getDimensions(pose);
        if (this.isTouchingWater()) {
            return EntityDimensions.changing(dimensions.width, dimensions.height / 2);
        }
        return dimensions;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.EMPEROR_PENGUIN.create(world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_EGG, false);
        this.dataTracker.startTracking(EGG_TIMER, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("HasEgg", this.hasEgg());
        nbt.putInt("EggTimer", this.getEggTimer());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasEgg(nbt.getBoolean("HasEgg"));
        this.setEggTimer(nbt.getInt("EggTimer"));
    }

    public boolean canEat() {
        return super.canEat() && !this.hasEgg();
    }

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    public void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    public int getEggTimer() {
        return this.dataTracker.get(EGG_TIMER);
    }

    public void setEggTimer(int time) {
        this.dataTracker.set(EGG_TIMER, time);
    }

    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(this.getMovementSpeed(), movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }

    }

    private static class EmperorPenguinMoveControl2 extends MoveControl {
        private final EmperorPenguinEntity emperorPenguinEntity;

        public EmperorPenguinMoveControl2(EmperorPenguinEntity emperorPenguinEntity) {
            super(emperorPenguinEntity);
            this.emperorPenguinEntity = emperorPenguinEntity;
        }

        public void tick() {
            LivingEntity livingEntity = this.emperorPenguinEntity.getTarget();
            if (this.emperorPenguinEntity.isTouchingWater()) {
                if (livingEntity != null && livingEntity.getY() > this.emperorPenguinEntity.getY()) {
                    this.emperorPenguinEntity.setVelocity(this.emperorPenguinEntity.getVelocity().add(0.0, 0.01, 0.0));
                }

                if (this.state != State.MOVE_TO || this.emperorPenguinEntity.getNavigation().isIdle()) {
                    this.emperorPenguinEntity.setMovementSpeed(0.0F);
                    return;
                }

                double d = this.targetX - this.emperorPenguinEntity.getX();
                double e = this.targetY - this.emperorPenguinEntity.getY();
                double f = this.targetZ - this.emperorPenguinEntity.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                e /= g;
                float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F;
                this.emperorPenguinEntity.setYaw(this.wrapDegrees(this.emperorPenguinEntity.getYaw(), h, 90.0F));
                this.emperorPenguinEntity.bodyYaw = this.emperorPenguinEntity.getYaw();
                float i = (float)(this.speed * this.emperorPenguinEntity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                float j = MathHelper.lerp(0.125F, this.emperorPenguinEntity.getMovementSpeed(), i);
                this.emperorPenguinEntity.setMovementSpeed(j);
                this.emperorPenguinEntity.setVelocity(this.emperorPenguinEntity.getVelocity().add((double)j * d * 0.005, (double)j * e * 0.1, (double)j * f * 0.005));
            } else {
                if (!this.emperorPenguinEntity.isOnGround()) {
                    this.emperorPenguinEntity.setVelocity(this.emperorPenguinEntity.getVelocity().add(0.0, -0.008, 0.0));
                }

                super.tick();
            }

        }
    }

    private static class EmperorPenguinMoveControl3 extends MoveControl {
        private final EmperorPenguinEntity emperorPenguinEntity;

        public EmperorPenguinMoveControl3(EmperorPenguinEntity emperorPenguinEntity) {
            super(emperorPenguinEntity);
            this.emperorPenguinEntity = emperorPenguinEntity;
        }

        public void tick() {
            if (this.emperorPenguinEntity.isTouchingWater()) {
                this.emperorPenguinEntity.setVelocity(this.emperorPenguinEntity.getVelocity().add(0.0, 0.005, 0.0));

                if (this.state != State.MOVE_TO || this.emperorPenguinEntity.getNavigation().isIdle()) {
                    this.emperorPenguinEntity.setMovementSpeed(0.0F);
                    return;
                }

                double d = this.targetX - this.emperorPenguinEntity.getX();
                double e = this.targetY - this.emperorPenguinEntity.getY();
                double f = this.targetZ - this.emperorPenguinEntity.getZ();
                double g = Math.sqrt(d * d + e * e + f * f);
                e /= g;
                float h = (float) (MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F;
                this.emperorPenguinEntity.setYaw(this.wrapDegrees(this.emperorPenguinEntity.getYaw(), h, 180.0F));
                this.emperorPenguinEntity.bodyYaw = this.emperorPenguinEntity.getYaw();
                float i = (float) (this.speed * this.emperorPenguinEntity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                float j = MathHelper.lerp(0.125F, this.emperorPenguinEntity.getMovementSpeed(), i);
                this.emperorPenguinEntity.setMovementSpeed(j);
                this.emperorPenguinEntity.setVelocity(this.emperorPenguinEntity.getVelocity().add(0, this.emperorPenguinEntity.getMovementSpeed() * e * 0.1, 0));
            } else {
                this.emperorPenguinEntity.setVelocity(this.emperorPenguinEntity.getVelocity().add(0.0, 0, 0.0));
                super.tick();
            }
        }
    }

    private static class EmperorPenguinMoveControl extends MoveControl {
        private final EmperorPenguinEntity emperorPenguinEntity;
        private final int pitchChange;
        private final int yawChange;
        private final float speedInWater;
        private final float speedInAir;

        public EmperorPenguinMoveControl(EmperorPenguinEntity emperorPenguinEntity, int pitchChange, int yawChange, float speedInWater, float speedInAir) {
            super(emperorPenguinEntity);
            this.emperorPenguinEntity = emperorPenguinEntity;
            this.pitchChange = pitchChange;
            this.yawChange = yawChange;
            this.speedInWater = speedInWater;
            this.speedInAir = speedInAir;
        }

        public void tick() {
            if (this.emperorPenguinEntity.isTouchingWater() && this.state == State.MOVE_TO && !this.entity.getNavigation().isIdle()) {
                double d = this.targetX - this.entity.getX();
                double e = this.targetY - this.entity.getY();
                double f = this.targetZ - this.entity.getZ();
                double g = d * d + e * e + f * f;
                if (g < 2.500000277905201E-7) {
                    this.entity.setForwardSpeed(0.0F);
                } else {
                    float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F;
                    this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), h, (float)this.yawChange));
                    this.entity.bodyYaw = this.entity.getYaw();
                    this.entity.headYaw = this.entity.getYaw();
                    float i = (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    if (this.entity.isTouchingWater()) {
                        this.entity.setMovementSpeed(i * this.speedInWater);
                        double j = Math.sqrt(d * d + f * f);
                        float k;
                        if (Math.abs(e) > 9.999999747378752E-6 || Math.abs(j) > 9.999999747378752E-6) {
                            k = -((float)(MathHelper.atan2(e, j) * 57.2957763671875));
                            k = MathHelper.clamp(MathHelper.wrapDegrees(k), (float)(-this.pitchChange), (float)this.pitchChange);
                            this.entity.setPitch(this.wrapDegrees(this.entity.getPitch(), k, 5.0F));
                        }

                        k = MathHelper.cos(this.entity.getPitch() * 0.017453292F);
                        float l = MathHelper.sin(this.entity.getPitch() * 0.017453292F);
                        this.entity.forwardSpeed = k * i;
                        this.entity.upwardSpeed = -l * i;
                    } else {
                        float m = Math.abs(MathHelper.wrapDegrees(this.entity.getYaw() - h));
                        float n = method_45335(m);
                        this.entity.setMovementSpeed(i * this.speedInAir * n);
                    }

                }
            } else {
                this.emperorPenguinEntity.setVelocity(this.emperorPenguinEntity.getVelocity().add(0.0, 0, 0.0));
                super.tick();
            }
        }

        private static float method_45335(float f) {
            return 1.0F - MathHelper.clamp((f - 10.0F) / 50.0F, 0.0F, 1.0F);
        }
    }

    static class EmperorPenguinNavigation extends EntityNavigation {
        private boolean canJumpOutOfWater;

        public EmperorPenguinNavigation(MobEntity mobEntity, World world) {
            super(mobEntity, world);
        }

        protected PathNodeNavigator createPathNodeNavigator(int range) {
            this.canJumpOutOfWater = true;
            this.nodeMaker = new WaterPathNodeMaker(this.canJumpOutOfWater);
            return new PathNodeNavigator(this.nodeMaker, range);
        }

        protected boolean isAtValidPosition() {
            return this.canJumpOutOfWater || this.isInLiquid();
        }

        protected Vec3d getPos() {
            return new Vec3d(this.entity.getX(), this.entity.getBodyY(0.5), this.entity.getZ());
        }

        protected double adjustTargetY(Vec3d pos) {
            return pos.y;
        }

        protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
            return doesNotCollide(this.entity, origin, target, false);
        }

        public boolean isValidPosition(BlockPos pos) {
            if (this.entity instanceof EmperorPenguinEntity) {
                return this.world.getBlockState(pos).isOf(Blocks.WATER) || (this.world.getBlockState(pos).isReplaceable() && this.world.getBlockState(pos.down()).isSolidBlock(this.world, pos.down()));
            }

            return !this.world.getBlockState(pos.down()).isAir();
        }
    }
}
