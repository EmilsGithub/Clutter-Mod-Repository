package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
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

public class CapybaraEntity extends AnimalEntity implements GeoEntity {

    private final AnimatableInstanceCache CACHE = new SingletonAnimatableInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlayAndHold("capybara.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("capybara.walk");
    private static final RawAnimation EAR_TWITCH_ONE = RawAnimation.begin().thenPlayAndHold("capybara.ear_twitch_one");
    private static final RawAnimation EAR_TWITCH_TWO = RawAnimation.begin().thenPlayAndHold("capybara.ear_twitch_two");
    private static final RawAnimation LAY_DOWN = RawAnimation.begin().thenPlayAndHold("capybara.lay_down");

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.POTATO);

    public CapybaraEntity(EntityType<? extends AnimalEntity> entityType, World world) {
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
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1));
        this.goalSelector.add(3, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.2));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0, 0.3f));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.POTATO);
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
        tAnimationState.getController().setAnimation(tAnimationState.isMoving() ? WALK : IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }
}
