package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
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
import net.minecraft.entity.passive.TameableEntity;
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
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;

public class CapybaraEntity extends TameableEntity implements GeoEntity {

    private final AnimatableInstanceCache CACHE = new SingletonAnimatableInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlayAndHold("capybara.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("capybara.walk");
    private static final RawAnimation EAR_TWITCH_ONE = RawAnimation.begin().thenPlayAndHold("capybara.ear_twitch_one");
    private static final RawAnimation EAR_TWITCH_TWO = RawAnimation.begin().thenPlayAndHold("capybara.ear_twitch_two");
    private static final RawAnimation LAY_DOWN = RawAnimation.begin().thenLoop("capybara.lay_down");
    private static final RawAnimation LAY_DOWN_2 = RawAnimation.begin().thenLoop("capybara.lay_down_2");
    private static final RawAnimation LAY_DOWN_3 = RawAnimation.begin().thenLoop("capybara.lay_down_3");

    private static final TrackedData<Boolean> IS_SLEEPING = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FORCE_SLEEPING = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> SLEEPER = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.MELON);

    public CapybaraEntity(EntityType<? extends TameableEntity> entityType, World world) {
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
        this.goalSelector.add(3, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1));
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

            this.heal((float)item.getFoodComponent().getHunger());
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
                    this.getWorld().sendEntityStatus(this, (byte)7);
                    setIsForceSleeping(true);
                    setIsSleeping(true);
                } else {
                    this.getWorld().sendEntityStatus(this, (byte)6);
                }

                return ActionResult.SUCCESS;
            }
        }

        if(isTamed() && !this.getWorld().isClient() && hand == Hand.MAIN_HAND && !(itemStack.isOf(Items.MELON_SLICE)) && !(itemStack.isOf(Items.MELON)) && isOwner(player)) {
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

        if(!this.getWorld().isClient && this.isTamed()) this.setIsSleeping(isForceSleeping());

        if(this.isSleeping()) {
            applyEffectToNearbyEntities(this, new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 4);
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 10, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "idle_controller", 10, this::idlePredicate));
    }

    private <T extends GeoAnimatable> PlayState idlePredicate(AnimationState<T> tAnimationState) {
        if(this.random.nextInt(200) == 0) {
            tAnimationState.getController().setAnimation(this.getWorld().random.nextBoolean() ? EAR_TWITCH_ONE : EAR_TWITCH_TWO);
        }
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(this.isSleeping() ? sleeperType() == 0 ? LAY_DOWN : sleeperType() == 1 ? LAY_DOWN_2 : LAY_DOWN_3 : tAnimationState.isMoving() && !this.isSleeping() ? WALK : IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
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

        public CapybaraFollowOwnerGoal(TameableEntity tameable, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
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
}
