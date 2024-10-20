package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.parent.ClutterTameableEntity;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.ButterflyBottleItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class ChameleonEntity extends ClutterTameableEntity {

    private static final Ingredient BREEDING_INGREDIENT;
    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(ChameleonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState sittingAnimationState = new AnimationState();

    private void setupAnimationStates() {
        if (this.isSitting() && !this.sittingAnimationState.isRunning()) {
            this.sittingAnimationState.start(this.age);
        } else if (!this.isSitting()) {
            this.sittingAnimationState.stop();
        }
    }

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(
                ModItems.BUTTERFLY_IN_A_BOTTLE);
    }

    public ChameleonEntity(EntityType<? extends ClutterTameableEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
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
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected float getUnscaledRidingOffset(Entity vehicle) {
        return 0.15F;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new RideAdultChameleonGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(3, new ChameleonEscapeDangerGoal(1.5));
        this.goalSelector.add(4, new FollowOwnerGoal(this, 1.2, 10.0F, 2.0F, false));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1));
        this.goalSelector.add(6, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(7, new FollowParentGoal(this, 1.2));
        this.goalSelector.add(8, new AttackGoal(this, 1.25));
        this.goalSelector.add(9, new WanderAroundFarGoal(this, 1.0, 1));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(11, new LookAroundGoal(this));

        this.targetSelector.add(8, new ActiveTargetGoal<>(this, ButterflyEntity.class, true));
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
    }

    @Override
    public void setBreedingAge(int age) {
        super.setBreedingAge(age);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() instanceof ButterflyBottleItem;
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(other instanceof ChameleonEntity chameleonEntity)) {
            return false;
        } else {
            if (!chameleonEntity.isTamed()) {
                return false;
            } else if (chameleonEntity.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && chameleonEntity.isInLove();
            }
        }
    }

    @Nullable
    public ChameleonEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        ChameleonEntity chameleonEntity = ModEntities.CHAMELEON.create(serverWorld);
        if (chameleonEntity != null) {
            UUID uUID = this.getOwnerUuid();
            if (uUID != null) {
                chameleonEntity.setOwnerUuid(uUID);
                chameleonEntity.setTamed(true);
            }
        }

        return chameleonEntity;
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        super.breed(world, other);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        Item itemForTaming = ModItems.THORNBLOOM_PEAR;

        if (item == itemForTaming && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            this.heal((float) item.getFoodComponent().getHunger());
            return ActionResult.SUCCESS;
        }

        if (item == itemForTaming && !isTamed()) {
            this.playSound(SoundEvents.ENTITY_FROG_EAT, 1.0F, 1.25F);
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
                    setSit(true);
                } else {
                    this.getWorld().sendEntityStatus(this, (byte) 6);
                }

                return ActionResult.SUCCESS;
            }
        }

        if (isTamed() && !this.getWorld().isClient() && hand == Hand.MAIN_HAND && !(itemStack.getItem() instanceof ButterflyBottleItem) && isOwner(player)) {
            setSit(!isSitting());
            return ActionResult.SUCCESS;
        }

        if (itemStack.getItem() == itemForTaming) {
            return ActionResult.PASS;
        }

        return super.interactMob(player, hand);
    }

    public void setSit(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
        super.setSitting(sitting);
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(18.0D);
            getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(6.0f);
        } else {
            getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(6.0D);
            getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(3.0f);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("isSitting", this.dataTracker.get(SITTING));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(SITTING, nbt.getBoolean("isSitting"));
    }

    @Override
    public AbstractTeam getScoreboardTeam() {
        return super.getScoreboardTeam();
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isSitting() && this.isOwner(player);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SITTING, false);
    }

    public static class RideAdultChameleonGoal extends Goal {
        private final ChameleonEntity babyChameleon;
        private ChameleonEntity targetChameleon;

        public RideAdultChameleonGoal(ChameleonEntity babyChameleon) {
            this.babyChameleon = babyChameleon;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            if (!babyChameleon.isBaby()) {
                return false;
            }

            List<ChameleonEntity> nearbyChameleons = babyChameleon.getWorld().getEntitiesByClass(ChameleonEntity.class, babyChameleon.getBoundingBox().expand(1.0), chameleon -> !chameleon.isBaby());

            if (nearbyChameleons.isEmpty()) {
                return false;
            }

            targetChameleon = nearbyChameleons.get(babyChameleon.getRandom().nextInt(nearbyChameleons.size()));
            return true;
        }

        @Override
        public void start() {
            if (targetChameleon != null) {
                babyChameleon.startRiding(targetChameleon); // Start riding the adult Chameleon
            }
        }

        @Override
        public boolean shouldContinue() {
            return babyChameleon.isBaby() && babyChameleon.hasVehicle() && babyChameleon.getVehicle() instanceof ChameleonEntity; // Continue while the baby is riding an adult Chameleon
        }

        @Override
        public void stop() {
            babyChameleon.stopRiding();
            targetChameleon = null;
        }
    }

    private static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(ChameleonEntity chameleon, double speed) {
            super(chameleon, speed, true);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 2.0F + entity.getWidth();
        }
    }

    class ChameleonEscapeDangerGoal extends EscapeDangerGoal {

        public ChameleonEscapeDangerGoal(double speed) {
            super(ChameleonEntity.this, speed);
        }

        protected boolean isInDanger() {
            return this.mob.shouldEscapePowderSnow() || this.mob.isOnFire();
        }
    }
}
