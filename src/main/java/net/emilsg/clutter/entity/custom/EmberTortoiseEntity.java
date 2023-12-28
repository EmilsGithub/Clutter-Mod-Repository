package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

public class EmberTortoiseEntity extends ClutterAnimalEntity {
    private static final TrackedData<Boolean> MOVING = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SHIELDING = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> SHIELDING_DURATION = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> SHIELDING_COOLDOWN = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState shieldingAnimationState = new AnimationState();
    private int shieldingAnimationTimeout = 0;

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(ModItems.SULPHUR);

    public EmberTortoiseEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(this.isShielding()) {
            return super.damage(source, amount / 8);
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canBeHitByProjectile() {
        if(!this.isShielding()) return super.canBeHitByProjectile();
        else return !this.isShielding();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EmberMeleeGoal(this, 1.0f, true));
        this.goalSelector.add(3, new EmberMateGoal(this, 1.2f));
        this.goalSelector.add(4, new EmberTemptGoal(this, 1.2f, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new EmberFollowParentGoal(this, 1.2f));
        this.goalSelector.add(6, new EmberWanderAroundFarGoal(this, 1.0f, 0.3f));
        this.goalSelector.add(7, new EmberLookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new EmberLookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MOVING, false);
        this.dataTracker.startTracking(SHIELDING, false);
        this.dataTracker.startTracking(SHIELDING_COOLDOWN, 0);
        this.dataTracker.startTracking(SHIELDING_DURATION, 400);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !this.isMoving() && !this.isShielding()) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isShielding() && shieldingAnimationTimeout == 0) {
            this.shieldingAnimationState.start(this.age);
            this.shieldingAnimationTimeout = 1;
        } else if (!this.isShielding()){
            this.shieldingAnimationTimeout = 0;
        }
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        super.takeKnockback(this.isShielding() ? strength / 4 : strength / 2, x, z);
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

    public void setMoving(boolean moving) {
        this.dataTracker.set(MOVING, moving);
    }

    public boolean isMoving() {
        return this.dataTracker.get(MOVING);
    }

    public void setShielding(boolean shielding) {
        this.dataTracker.set(SHIELDING, shielding);
    }

    public boolean isShielding() {
        return this.dataTracker.get(SHIELDING);
    }

    public void setShieldingCooldown(int shieldingCooldown) {
        this.dataTracker.set(SHIELDING_COOLDOWN, shieldingCooldown);
    }

    public int getShieldingCooldown() {
        return this.dataTracker.get(SHIELDING_COOLDOWN);
    }

    public void setShieldingDuration(int shieldingDuration) {
        this.dataTracker.set(SHIELDING_DURATION, shieldingDuration);
    }

    public int getShieldingDuration() {
        return this.dataTracker.get(SHIELDING_DURATION);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.EMBER_TORTOISE.create(world);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.4F;
    }


    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Shielding", this.isShielding());
        nbt.putInt("ShieldingDuration", this.getShieldingDuration());
        nbt.putInt("ShieldingCooldown", this.getShieldingCooldown());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setShielding(nbt.getBoolean("Shielding"));
        this.setShieldingDuration(nbt.getInt("ShieldingDuration"));
        this.setShieldingCooldown(nbt.getInt("ShieldingCooldown"));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            BlockPos oldPos = this.getBlockPos();
            BlockPos newPos = this.getBlockPos();
            this.setMoving(oldPos != newPos);
        }
    }


    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (!this.isDead() && this.getHealth() <= (this.getMaxHealth() / 4) && getShieldingCooldown() <= 0 && !world.isClient()) {
            this.setShielding(true);
            this.setShieldingDuration((random.nextBetween(4, 8) + 1) * 100);
            this.setShieldingCooldown(2400);
        } else if (!this.isDead() && getShieldingDuration() <= 0 && !world.isClient()) {
            setShielding(false);
        }

        if (this.isShielding() && !world.isClient()) {
            setShieldingDuration(this.getShieldingDuration() - 1);
        }

        if(!world.isClient() && this.getHealth() < this.getMaxHealth() && random.nextInt(20) == 0 && this.getWorld().getDimensionKey() == DimensionTypes.THE_NETHER) {
            this.setHealth(this.getHealth() + 1);
        }

        if(world.isClient) {
            this.setupAnimationStates();
        }
    }

    @Override
    public boolean isInvulnerable() {
        return this.isShielding();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModItems.SULPHUR);
    }

    private static class EmberMeleeGoal extends MeleeAttackGoal {
        EmberTortoiseEntity emberTortoise;

        public EmberMeleeGoal(EmberTortoiseEntity emberTortoise, double speed, boolean pauseWhenMobIdle) {
            super(emberTortoise, speed, pauseWhenMobIdle);
            this.emberTortoise = emberTortoise;
        }

        @Override
        public boolean canStart() {

            return !emberTortoise.isShielding() && super.canStart();
        }
    }

    private static class EmberMateGoal extends AnimalMateGoal {
        EmberTortoiseEntity emberTortoise;

        public EmberMateGoal(EmberTortoiseEntity emberTortoise, double speed) {
            super(emberTortoise, speed);
            this.emberTortoise = emberTortoise;
        }

        @Override
        public boolean canStart() {
            return !this.emberTortoise.isShielding() && super.canStart();
        }
    }

    private static class EmberTemptGoal extends TemptGoal {
        EmberTortoiseEntity emberTortoise;

        public EmberTemptGoal(EmberTortoiseEntity emberTortoise, double speed, Ingredient food, boolean canBeScared) {
            super(emberTortoise, speed, food, canBeScared);
            this.emberTortoise = emberTortoise;
        }

        @Override
        public boolean canStart() {
            return !this.emberTortoise.isShielding() && super.canStart();
        }
    }

    private static class EmberFollowParentGoal extends FollowParentGoal {
        AnimalEntity animalEntity;

        public EmberFollowParentGoal(AnimalEntity animal, double speed) {
            super(animal, speed);
            this.animalEntity = animal;
        }

        @Override
        public boolean canStart() {
            if(animalEntity instanceof EmberTortoiseEntity emberTortoise) return !emberTortoise.isShielding() && super.canStart();
            else return super.canStart();
        }
    }

    private static class EmberWanderAroundFarGoal extends WanderAroundFarGoal {
        EmberTortoiseEntity emberTortoise;

        public EmberWanderAroundFarGoal(EmberTortoiseEntity emberTortoise, double speed, float probability) {
            super(emberTortoise, speed, probability);
            this.emberTortoise = emberTortoise;
        }

        @Override
        public boolean canStart() {
            return !this.emberTortoise.isShielding() && super.canStart();
        }
    }

    private static class EmberLookAtEntityGoal extends LookAtEntityGoal {
        EmberTortoiseEntity emberTortoise;

        public EmberLookAtEntityGoal(EmberTortoiseEntity emberTortoise, Class<? extends LivingEntity> targetType, float range) {
            super(emberTortoise, targetType, range);
            this.emberTortoise = emberTortoise;
        }

        @Override
        public boolean canStart() {
            return !this.emberTortoise.isShielding() && super.canStart();
        }
    }

    private static class EmberLookAroundGoal extends LookAroundGoal {
        EmberTortoiseEntity emberTortoise;

        public EmberLookAroundGoal(EmberTortoiseEntity emberTortoise) {
            super(emberTortoise);
            this.emberTortoise = emberTortoise;
        }

        @Override
        public boolean canStart() {
            return !this.emberTortoise.isShielding() && super.canStart();
        }
    }
}
