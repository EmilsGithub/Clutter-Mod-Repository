package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.parent.ClutterTameableEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class CapybaraEntity extends ClutterTameableEntity {
    private static final TrackedData<Boolean> IS_SLEEPING = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FORCE_SLEEPING = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> SLEEPER = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.MELON);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState earTwitchAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public int earTwitchAnimationTimeout = 0;
    public int sleepingAnimationTimeout = 0;

    public CapybaraEntity(EntityType<? extends ClutterTameableEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    private void setupAnimationStates() {
        if (this.sleepingAnimationTimeout <= 0) {
            this.sleepingAnimationTimeout = 80;
            this.sleepingAnimationState.start(this.age);
        } else {
            --this.sleepingAnimationTimeout;
        }

        if (this.earTwitchAnimationTimeout <= 0) {
            this.earTwitchAnimationTimeout = 1;
            this.earTwitchAnimationState.start(this.age);
        } else {
            --this.earTwitchAnimationTimeout;
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

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getSource() instanceof ProjectileEntity projectile && this.isSleeping()) {
            projectile.setVelocity(projectile.getVelocity().multiply(-1));
            return false;
        }

        return super.damage(source, amount);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CapybaraSitGoal(this));
        this.goalSelector.add(3, new CapybaraEscapeDangerGoal(this, 1.25));
        this.goalSelector.add(4, new CapybaraMateGoal(this, 1));
        this.goalSelector.add(5, new CapybaraFollowOwnerGoal(this, 1.2, 10.0F, 2.0F, false));
        this.goalSelector.add(6, new CapybaraTemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(7, new FollowParentGoal(this, 1.2));
        this.goalSelector.add(8, new CapybaraWanderGoal(this, 1.0, 0.3f));
        this.goalSelector.add(9, new CapybaraLookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(10, new CapybaraLookAroundGoal(this));
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        Item itemForTaming = Items.MELON_SLICE;

        if (item == itemForTaming && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            this.heal((float) item.getFoodComponent().getHunger());
            return ActionResult.SUCCESS;
        }

        if (item == itemForTaming && !isTamed()) {
            this.playSound(SoundEvents.ENTITY_HORSE_EAT, 1.0F, 1.25F);
            if (this.getWorld().isClient()) {
                return ActionResult.CONSUME;
            } else {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }

                if (this.random.nextInt(3) == 0 && !this.getWorld().isClient()) {
                    super.setOwner(player);
                    this.navigation.recalculatePath();
                    this.setHealth(this.getMaxHealth());
                    this.setTarget(null);
                    this.getWorld().sendEntityStatus(this, (byte) 7);
                    setIsForceSleeping(true);
                    setIsSleeping(true);
                } else {
                    this.getWorld().sendEntityStatus(this, (byte) 6);
                }

                return ActionResult.SUCCESS;
            }
        }

        if (isTamed() && !this.getWorld().isClient() && hand == Hand.MAIN_HAND && !(itemStack.isOf(Items.MELON_SLICE)) && !(itemStack.isOf(Items.MELON)) && isOwner(player)) {
            setIsForceSleeping(!isSleeping());
            setIsSleeping(!isSleeping());
            return ActionResult.SUCCESS;
        }

        if (itemStack.getItem() == itemForTaming) {
            return ActionResult.PASS;
        }

        return super.interactMob(player, hand);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(IS_SLEEPING, false);
        this.dataTracker.startTracking(FORCE_SLEEPING, false);
        this.dataTracker.startTracking(SLEEPER, 0);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int sleeperType = random.nextBetween(0, 2);
        setSleeperType(sleeperType);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsSleeping", this.isSleeping());
        nbt.putBoolean("IsForceSleeping", this.isForceSleeping());
        nbt.putInt("Sleeper", this.sleeperType());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setIsSleeping(nbt.getBoolean("IsSleeping"));
        this.setIsForceSleeping(nbt.getBoolean("IsForceSleeping"));
        this.setSleeperType(nbt.getInt("Sleeper"));
    }

    public boolean canEat() {
        return super.canEat() && !this.isSleeping();
    }

    public boolean isSleeping() {
        return this.dataTracker.get(IS_SLEEPING);
    }

    public boolean isForceSleeping() {
        return this.dataTracker.get(FORCE_SLEEPING);
    }

    public int sleeperType() {
        return this.dataTracker.get(SLEEPER);
    }

    void setIsSleeping(boolean isSleeping) {
        this.dataTracker.set(IS_SLEEPING, isSleeping);
    }

    void setIsForceSleeping(boolean isSleeping) {
        this.dataTracker.set(FORCE_SLEEPING, isSleeping);
    }

    void setSleeperType(int type) {
        this.dataTracker.set(SLEEPER, type);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient && !this.isTamed()) this.setIsSleeping(!this.getWorld().isDay());

        if (!this.getWorld().isClient && this.isTamed()) this.setIsSleeping(isForceSleeping());

        if (this.isSleeping()) {
            applyEffectToNearbyEntities(this, new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 4);
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

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.MELON);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.CAPYBARA.create(world);
    }

    public void applyEffectToNearbyEntities(Entity centerEntity, StatusEffectInstance effect, double radius) {
        Box area = new Box(
                centerEntity.getX() - radius, centerEntity.getY() - radius, centerEntity.getZ() - radius,
                centerEntity.getX() + radius, centerEntity.getY() + radius, centerEntity.getZ() + radius
        );

        List<LivingEntity> nearbyEntities = centerEntity.getWorld().getEntitiesByClass(
                LivingEntity.class,
                area,
                e -> e != centerEntity
        );

        for (LivingEntity entity : nearbyEntities) {
            entity.addStatusEffect(effect);
        }
    }

    private class CapybaraWanderGoal extends WanderAroundFarGoal {

        public CapybaraWanderGoal(PathAwareEntity mob, double speed, float probability) {
            super(mob, speed, probability);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !isSleeping();
        }
    }

    private class CapybaraLookAtEntityGoal extends LookAtEntityGoal {

        public CapybaraLookAtEntityGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range) {
            super(mob, targetType, range);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !isSleeping();
        }
    }

    private class CapybaraLookAroundGoal extends LookAroundGoal {

        public CapybaraLookAroundGoal(MobEntity mob) {
            super(mob);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !isSleeping();
        }
    }

    private class CapybaraFollowOwnerGoal extends FollowOwnerGoal {

        public CapybaraFollowOwnerGoal(ClutterTameableEntity tameable, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
            super(tameable, speed, minDistance, maxDistance, leavesAllowed);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !isSleeping();
        }
    }

    private class CapybaraTemptGoal extends TemptGoal {

        public CapybaraTemptGoal(PathAwareEntity entity, double speed, Ingredient food, boolean canBeScared) {
            super(entity, speed, food, canBeScared);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !isSleeping();
        }
    }

    private class CapybaraSitGoal extends Goal {
        private final CapybaraEntity capybara;

        public CapybaraSitGoal(CapybaraEntity capybara) {
            this.capybara = capybara;
            this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
        }

        @Override
        public boolean shouldContinue() {
            return this.capybara.isForceSleeping();
        }

        @Override
        public boolean canStart() {
            if (!this.capybara.isTamed()) {
                return false;
            }
            if (this.capybara.isInsideWaterOrBubbleColumn()) {
                return false;
            }
            if (!this.capybara.isOnGround()) {
                return false;
            }
            LivingEntity owner = this.capybara.getOwner();
            if (owner == null) {
                return true;
            }
            if (this.capybara.squaredDistanceTo(owner) < 144.0 && owner.getAttacker() != null) {
                return false;
            }
            return this.capybara.isForceSleeping();
        }

        @Override
        public void start() {
            this.capybara.getNavigation().stop();
            this.capybara.setIsForceSleeping(true);
        }

        @Override
        public void stop() {
            this.capybara.setIsForceSleeping(false);
        }
    }

    private class CapybaraEscapeDangerGoal extends EscapeDangerGoal {

        public CapybaraEscapeDangerGoal(PathAwareEntity mob, double speed) {
            super(mob, speed);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !isSleeping();
        }
    }

    private class CapybaraMateGoal extends AnimalMateGoal {

        public CapybaraMateGoal(AnimalEntity animal, double speed) {
            super(animal, speed);
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !isSleeping();
        }
    }
}
