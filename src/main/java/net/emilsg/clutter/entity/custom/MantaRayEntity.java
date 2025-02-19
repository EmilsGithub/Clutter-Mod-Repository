package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.custom.goal.MantaRayJumpGoal;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.entity.custom.parent.ClutterWaterEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class MantaRayEntity extends ClutterWaterEntity {
    private static final TrackedData<Float> SIZE = DataTracker.registerData(MantaRayEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public final AnimationState swimmingAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    private int animationTimeout = 0;
    private int flopAnimationTimeout = 0;

    public MantaRayEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 65, 10, 0.025F, 0.1F, true);
        this.lookControl = new YawAdjustingLookControl(this, 10);
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    protected void initGoals() {
        this.goalSelector.add(0, new MoveIntoWaterGoal(this));
        this.goalSelector.add(1, new SwimAroundGoal(this, 1.0, 10));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new MantaRayJumpGoal(this, 10));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.2000000476837158, true));
        this.goalSelector.add(5, new ChaseBoatGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, JellyfishEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        this.setPitch(0.0F);
        float scaledSize;
        float chance = random.nextFloat();

        if (chance < 0.31) scaledSize = 0.8f;
        else if (chance < 0.62) scaledSize = 1.0f;
        else if (chance < 0.95) scaledSize = 1.2f;
        else scaledSize = 1.5f;

        this.setSize(scaledSize);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SIZE, 0f);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).getFluidState().isOf(Fluids.WATER);
    }

    public float getSize() {
        return this.dataTracker.get(SIZE);
    }

    public void setSize(float size) {
        this.dataTracker.set(SIZE, MathHelper.clamp(size, 0f, 1.5f));
        this.calculateDimensions();
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (SIZE.equals(data)) {
            this.calculateDimensions();
        }

        super.onTrackedDataSet(data);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("Size", this.getSize());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSize(nbt.getFloat("Size"));
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return super.getBaseDimensions(pose).scaled(0.65f * this.getSize());
    }

    private void setupAnimationStates() {
        if (this.animationTimeout <= 0) {
            this.animationTimeout = 40;
            this.swimmingAnimationState.start(this.age);
        } else {
            --this.animationTimeout;
        }

        if (this.flopAnimationTimeout <= 0) {
            this.flopAnimationTimeout = 10;
            this.flopAnimationState.start(this.age);
        } else {
            --this.flopAnimationTimeout;
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

    private SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_GUARDIAN_FLOP;
    }

    @Override
    public void tickMovement() {
        if (!this.isTouchingWater() && this.isOnGround() && this.verticalCollision) {
            this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4000000059604645, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setOnGround(false);
            this.velocityDirty = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        super.tickMovement();
    }

    protected EntityNavigation createNavigation(World world) {
        return new SwimNavigation(this, world);
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

}
