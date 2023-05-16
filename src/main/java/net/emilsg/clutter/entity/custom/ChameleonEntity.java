package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.ButterflyBottleItem;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;

public class ChameleonEntity extends AnimalEntity implements GeoEntity {
    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final Ingredient BREEDING_INGREDIENT;

    public ChameleonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
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
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.05d;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new RideAdultChameleonGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1));
        this.goalSelector.add(4, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(6, new AttackGoal(this, 1.25));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 2));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));

        this.targetSelector.add(6, new ActiveTargetGoal<>(this, ButterflyEntity.class, true));
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
                return false; // Only execute the goal if the entity is a baby Chameleon
            }

            // Find a nearby adult Chameleon entity
            List<ChameleonEntity> nearbyChameleons = babyChameleon.world.getEntitiesByClass(ChameleonEntity.class, babyChameleon.getBoundingBox().expand(1.0), chameleon -> !chameleon.isBaby());

            if (nearbyChameleons.isEmpty()) {
                return false; // No nearby adult Chameleons found
            }

            // Select a random adult Chameleon to ride
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
            babyChameleon.stopRiding(); // Stop riding the adult Chameleon
            targetChameleon = null;
        }
    }

    private static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(ChameleonEntity chameleon, double speed) {
            super(chameleon, speed, true);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double)(2.0F + entity.getWidth());
        }
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
        return super.canBreedWith(other);
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        super.breed(world, other);
    }

    @Override
    public boolean shouldSpawnSprintingParticles() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.CHAMELEON.create(world);
    }

    private PlayState predicate(AnimationState tAnimationState) {
        if(!tAnimationState.isMoving() && !this.getPassengerList().isEmpty()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("chameleon.lay_down", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        } else if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("chameleon.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if(this.isAttacking()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().thenLoop("chameleon.lick"));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin().then("chameleon.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(
                ModItems.WHITE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.LIGHT_GRAY_BUTTERFLY_IN_A_BOTTLE,
                ModItems.GRAY_BUTTERFLY_ELYTRA,
                ModItems.BLACK_BUTTERFLY_IN_A_BOTTLE,
                ModItems.BROWN_BUTTERFLY_IN_A_BOTTLE,
                ModItems.RED_BUTTERFLY_IN_A_BOTTLE,
                ModItems.ORANGE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.YELLOW_BUTTERFLY_IN_A_BOTTLE,
                ModItems.LIME_BUTTERFLY_IN_A_BOTTLE,
                ModItems.GREEN_BUTTERFLY_IN_A_BOTTLE,
                ModItems.CYAN_BUTTERFLY_IN_A_BOTTLE,
                ModItems.LIGHT_BLUE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.BLUE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.PURPLE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.MAGENTA_BUTTERFLY_IN_A_BOTTLE,
                ModItems.PINK_BUTTERFLY_IN_A_BOTTLE);
    }
}
