package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.goal.*;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class EmberTortoiseEntity extends ClutterAnimalEntity {
    private static final TrackedData<Boolean> MOVING = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SHIELDING = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> SHIELDING_DURATION = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> SHIELDING_COOLDOWN = DataTracker.registerData(EmberTortoiseEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.FIRE_CHARGE);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shieldingAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public int shieldingAnimationTimeout = 0;
    public int attackAnimationTimeout = 0;

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
        if (this.isShielding()) {
            LivingEntity livingEntity = (LivingEntity) source.getAttacker();

            if (source.getSource() instanceof ProjectileEntity projectile) {
                projectile.setVelocity(projectile.getVelocity().multiply(-1));
                return false;
            }

            if (livingEntity != null && livingEntity.getMainHandStack().getItem() instanceof PickaxeItem) {
                return super.damage(source, amount * 2);
            }

            return super.damage(source, amount / 16);
        }
        return super.damage(source, amount);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EmberTortoiseMeleeGoal(this, 1.0f, true));
        this.goalSelector.add(3, new EmberTortoiseMateGoal(this, 1.2f));
        this.goalSelector.add(4, new EmberTortoiseTemptGoal(this, 1.2f, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new EmberTortoiseFollowParentGoal(this, 1.2f));
        this.goalSelector.add(6, new EmberTortoiseWanderAroundFarGoal(this, 1.0f, 0.3f));
        this.goalSelector.add(7, new EmberTortoiseLookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new EmberTortoiseLookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, HoglinEntity.class, true));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(MOVING, false);
        builder.add(ATTACKING, false);
        builder.add(SHIELDING, false);
        builder.add(SHIELDING_COOLDOWN, 0);
        builder.add(SHIELDING_DURATION, 400);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !this.isMoving() && !this.isShielding()) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 20;
            attackAnimationState.start(this.age);
        } else {
            --this.attackAnimationTimeout;
        }

        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }

        if (this.isShielding() && shieldingAnimationTimeout <= 0) {
            this.shieldingAnimationState.start(this.age);
            this.shieldingAnimationTimeout = 1;
        } else if (!this.isShielding()) {
            this.shieldingAnimationTimeout = 0;
        }
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        super.takeKnockback(0, x, z);
    }

    @Override
    public boolean isPushable() {
        return !this.isShielding();
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

    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, net.minecraft.util.math.random.Random random) {
        return world.getBlockState(pos.down()).isIn(ModBlockTags.EMBER_TORTOISES_SPAWN_ON);
    }

    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }

    public boolean isMoving() {
        return this.dataTracker.get(MOVING);
    }

    public void setMoving(boolean moving) {
        this.dataTracker.set(MOVING, moving);
    }

    public boolean isShielding() {
        return this.dataTracker.get(SHIELDING);
    }

    public void setShielding(boolean shielding) {
        this.dataTracker.set(SHIELDING, shielding);
    }

    public int getShieldingCooldown() {
        return this.dataTracker.get(SHIELDING_COOLDOWN);
    }

    public void setShieldingCooldown(int shieldingCooldown) {
        this.dataTracker.set(SHIELDING_COOLDOWN, shieldingCooldown);
    }

    public int getShieldingDuration() {
        return this.dataTracker.get(SHIELDING_DURATION);
    }

    public void setShieldingDuration(int shieldingDuration) {
        this.dataTracker.set(SHIELDING_DURATION, shieldingDuration);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.EMBER_TORTOISE.create(world);
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
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (this.isAlive() && this.getHealth() <= (this.getMaxHealth() / 4) && getShieldingCooldown() <= 0 && !world.isClient()) {
            this.setShielding(true);
            this.setShieldingDuration((random.nextBetween(4, 8) + 1) * 100);
            this.setShieldingCooldown(2400);
        } else if (this.isAlive() && getShieldingDuration() <= 0 && !world.isClient()) {
            setShielding(false);
        }

        if (this.isShielding() && world.isClient) {
            Vec3d entityPos = this.getPos();
            Random random = new Random();
            int numberOfParticles = 10;

            for (int i = 0; i < numberOfParticles; i++) {
                double velocityX = (random.nextDouble() - 0.5) * 0.4;
                double velocityY = (random.nextDouble() - 0.5) * 0.4;
                double velocityZ = (random.nextDouble() - 0.5) * 0.4;

                world.addParticle(random.nextBoolean() ? ParticleTypes.FLAME : ParticleTypes.SMALL_FLAME,
                        entityPos.x, entityPos.y + 1, entityPos.z,
                        velocityX, velocityY, velocityZ);
            }
        }

        if (this.isShielding() && !world.isClient()) {
            setShieldingDuration(this.getShieldingDuration() - 1);

            List<LivingEntity> nearbyEntities = world.getEntitiesByClass(LivingEntity.class, new Box(this.getBlockPos()).expand(3), e -> true);

            if (nearbyEntities != null) {
                for (LivingEntity entity : nearbyEntities) {
                    entity.setOnFire(true);
                    entity.setFireTicks(100);
                }
            }
        }

        if (!world.isClient() && this.getHealth() < this.getMaxHealth() && random.nextInt(200) == 0 && this.getWorld().getDimensionEntry() == DimensionTypes.THE_NETHER && this.isAlive()) {
            this.setHealth((float) (int) (this.getHealth() + 1));
        }

        if (world.isClient) {
            this.setupAnimationStates();
        }
    }

    @Override
    public boolean isInvulnerable() {
        return this.isShielding();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.FIRE_CHARGE);
    }
}
