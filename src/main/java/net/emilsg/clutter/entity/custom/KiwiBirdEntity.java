package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
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

import java.util.ArrayList;
import java.util.List;

public class KiwiBirdEntity extends AnimalEntity implements GeoEntity {
    private boolean songPlaying;
    @Nullable
    private BlockPos songSource;

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ModItemTags.SEEDS);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(KiwiBirdEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("kiwi_bird.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("kiwi_bird.walk");
    private static final RawAnimation DANCE = RawAnimation.begin().thenLoop("kiwi_bird.dance");

    public KiwiBirdEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.225f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this,1.25));
        this.goalSelector.add(2, new KiwiMateGoal(this, 1));
        this.goalSelector.add(3, new LayEggGoal(this, 1));
        this.goalSelector.add(4, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1));
        this.goalSelector.add(6, new KiwiWanderAroundFarGoal(this, 1.0, 1));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_EGG, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    public void tickMovement() {
        if (this.songSource == null || !this.songSource.isWithinDistance(this.getPos(), 3.46) || !this.getWorld().getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }
        super.tickMovement();
    }

    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }

    public boolean canEat() {
        return super.canEat() && !this.hasEgg();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ModItemTags.SEEDS);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.KIWI_BIRD.create(world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(isSongPlaying() ? DANCE : tAnimationState.isMoving() ? WALK : IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PARROT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PARROT_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PARROT_AMBIENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 0.75F);
    }

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    private class KiwiWanderAroundFarGoal extends WanderAroundFarGoal {

        public KiwiWanderAroundFarGoal(PathAwareEntity mob, double speed, float probability) {
            super(mob, speed, probability);
        }

        @Override
        public boolean canStart() {
            if (isSongPlaying()) {
                return false;
            }
            return super.canStart();
        }
    }

    private static class KiwiMateGoal extends AnimalMateGoal {
        private final KiwiBirdEntity kiwiBird;

        KiwiMateGoal(KiwiBirdEntity kiwiBird, double speed) {
            super(kiwiBird, speed);
            this.kiwiBird = kiwiBird;
        }

        public boolean canStart() {
            return super.canStart() && !this.kiwiBird.hasEgg();
        }

        protected void breed() {
            ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
            if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
                serverPlayerEntity = this.mate.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, null);
            }

            this.kiwiBird.setHasEgg(true);
            this.animal.setBreedingAge(6000);
            this.mate.setBreedingAge(6000);
            this.animal.resetLoveTicks();
            this.mate.resetLoveTicks();
            Random random = this.animal.getRandom();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }

        }
    }

    public static class LayEggGoal extends Goal {
        private final KiwiBirdEntity kiwiBird;
        private final double speed;
        private BlockPos targetPos;

        LayEggGoal(KiwiBirdEntity kiwiBird, double speed) {
            this.kiwiBird = kiwiBird;
            this.speed = speed;
        }

        @Override
        public boolean canStart() {
            if (!this.kiwiBird.hasEgg() || !this.kiwiBird.getNavigation().isIdle()) {
                return false;
            }
            targetPos = findRandomValidPos();
            return targetPos != null;
        }


        private BlockPos findRandomValidPos() {
            BlockPos entityPos = this.kiwiBird.getBlockPos();
            int range = 16;
            List<BlockPos> validPositions = new ArrayList<>();

            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    for (int dz = -range; dz <= range; dz++) {
                        BlockPos pos = entityPos.add(dx, dy, dz);
                        if (isTargetPos(this.kiwiBird.getWorld(), pos)) {
                            validPositions.add(pos);
                        }
                    }
                }
            }

            if (validPositions.isEmpty()) {
                return null;
            } else {
                return validPositions.get(this.kiwiBird.getRandom().nextInt(validPositions.size()));
            }
        }




        @Override
        public void start() {
            this.kiwiBird.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
        }

        @Override
        public boolean shouldContinue() {
            return this.kiwiBird.hasEgg() && !this.kiwiBird.getNavigation().isIdle();
        }

        @Override
        public void stop() {
            if (targetPos.isWithinDistance(this.kiwiBird.getPos(), 2) && (this.kiwiBird.getWorld().getBlockState(targetPos.up()).isAir() || this.kiwiBird.getWorld().getBlockState(targetPos.up()).isReplaceable())) {
                World world = this.kiwiBird.getWorld();
                world.setBlockState(targetPos.up(), ModBlocks.KIWI_BIRD_EGG.getDefaultState(), 3);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, targetPos, GameEvent.Emitter.of(this.kiwiBird, ModBlocks.KIWI_BIRD_EGG.getDefaultState()));
                this.kiwiBird.setHasEgg(false);
            }
        }

        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            return world.getBlockState(pos).isIn(BlockTags.DIRT);
        }
    }

}
