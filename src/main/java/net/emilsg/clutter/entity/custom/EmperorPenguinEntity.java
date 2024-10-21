package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.goal.EmperorPenguinLayEggGoal;
import net.emilsg.clutter.entity.custom.goal.EmperorPenguinMateGoal;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EmperorPenguinEntity extends ClutterAnimalEntity {
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ItemTags.FISHES);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(EmperorPenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> EGG_TIMER = DataTracker.registerData(EmperorPenguinEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState flapAnimationStateOne = new AnimationState();
    public final AnimationState flapAnimationStateTwo = new AnimationState();
    public final AnimationState preenAnimationState = new AnimationState();

    public int flapAnimationTimeout = 0;

    public EmperorPenguinEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
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

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new EmperorPenguinMateGoal(this, 1));
        this.goalSelector.add(3, new EmperorPenguinLayEggGoal(this, 1, ModBlocks.EMPEROR_PENGUIN_EGG.getDefaultState()));
        this.goalSelector.add(4, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1f));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    private void setupAnimationStates() {
        if (this.flapAnimationTimeout <= 0 && random.nextInt(200) == 0 && this.getNavigation().isIdle() && !this.isMoving()) {
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

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.EMPEROR_PENGUIN.create(world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(HAS_EGG, false);
        builder.add(EGG_TIMER, 0);
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
}
