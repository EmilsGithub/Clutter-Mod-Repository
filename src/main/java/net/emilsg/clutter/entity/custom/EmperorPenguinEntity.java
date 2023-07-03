package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
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

public class EmperorPenguinEntity extends AnimalEntity implements GeoEntity {
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ItemTags.FISHES);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("emperor_penguin.idle");
    private static final RawAnimation WADDLE = RawAnimation.begin().thenLoop("emperor_penguin.waddle");
    private static final RawAnimation FLAP = RawAnimation.begin().thenPlay("emperor_penguin.random_flap");
    private static final RawAnimation FLAP_TWO = RawAnimation.begin().thenPlay("emperor_penguin.random_flap_two");
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

    public EmperorPenguinEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this,1.25));
        this.goalSelector.add(2, new AnimalMateGoal(this,1));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1));
        this.goalSelector.add(5, new AttackGoal(this, 1.25));
        this.goalSelector.add(6, new WanderAroundFarGoal(this,1, 1));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(5, new ActiveTargetGoal<>(this, CodEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, SalmonEntity.class, true));
    }

    private static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(EmperorPenguinEntity emperorPenguin, double speed) {
            super(emperorPenguin, speed, false);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return (double)(3.0F + entity.getWidth());
        }
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.FISHES);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.EMPEROR_PENGUIN.create(world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 10, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "idle_controller", 10, this::idlePredicate));
    }

    private <T extends GeoAnimatable> PlayState idlePredicate(AnimationState<T> tAnimationState) {
        if (this.random.nextInt(600) == 0 && !tAnimationState.isMoving()) {
            tAnimationState.setAndContinue(this.random.nextBoolean() ? FLAP : FLAP_TWO);
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }


    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.setAndContinue(WADDLE);
            return PlayState.CONTINUE;
        } else {
            tAnimationState.setAndContinue(IDLE);
            return PlayState.CONTINUE;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.CACHE;
    }
}
