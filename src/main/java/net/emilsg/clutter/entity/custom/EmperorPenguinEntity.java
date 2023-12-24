package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.entity.ModEntities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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

public class EmperorPenguinEntity extends ClutterAnimalEntity implements GeoEntity {
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.fromTag(ItemTags.FISHES);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(EmperorPenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("emperor_penguin.idle");
    private static final RawAnimation WADDLE = RawAnimation.begin().thenLoop("emperor_penguin.waddle");
    private static final RawAnimation FLAP = RawAnimation.begin().thenPlay("emperor_penguin.random_flap");
    private static final RawAnimation FLAP_TWO = RawAnimation.begin().thenPlay("emperor_penguin.random_flap_two");
    private static final RawAnimation SWIM = RawAnimation.begin().thenLoop("emperor_penguin.swim");
    private static final RawAnimation PADDLE = RawAnimation.begin().thenLoop("emperor_penguin.paddle");
    private final AnimatableInstanceCache CACHE = GeckoLibUtil.createInstanceCache(this);

    public EmperorPenguinEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setStepHeight(1.0f);
        this.moveControl = new EmperorPenguinMoveControl(this);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new EscapeDangerGoal(this,1.25));
        this.goalSelector.add(2, new EmperorPenguinMateGoal(this,1));
        this.goalSelector.add(3, new LayEggGoal(this, 1));
        this.goalSelector.add(4, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.25, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(this,1, 1));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));

        this.targetSelector.add(6, new ActiveTargetGoal<>(this, CodEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, SalmonEntity.class, true));
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    protected EntityNavigation createNavigation(World world) {
        return new EmperorPenguinSwimNavigation(this, world);
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
        controllerRegistrar.add(new AnimationController<>(this, "controller", 15, this::predicate));
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
        tAnimationState.getController().setAnimation(this.isTouchingWater() && this.isSubmergedInWater() && tAnimationState.isMoving() ? SWIM : this.isTouchingWater() && this.getNavigation().isIdle() ? PADDLE : tAnimationState.isMoving() && !this.isTouchingWater() ? WADDLE : IDLE);
        return PlayState.CONTINUE;
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

    public boolean canEat() {
        return super.canEat() && !this.hasEgg();
    }

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.CACHE;
    }

    private static class EmperorPenguinMateGoal extends AnimalMateGoal {
        private final EmperorPenguinEntity emperorPenguin;

        EmperorPenguinMateGoal(EmperorPenguinEntity emperorPenguin, double speed) {
            super(emperorPenguin, speed);
            this.emperorPenguin = emperorPenguin;
        }

        public boolean canStart() {
            return super.canStart() && !this.emperorPenguin.hasEgg();
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

            this.emperorPenguin.setHasEgg(true);
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
        private final EmperorPenguinEntity emperorPenguin;
        private final double speed;
        private BlockPos targetPos;

        LayEggGoal(EmperorPenguinEntity emperorPenguin, double speed) {
            this.emperorPenguin = emperorPenguin;
            this.speed = speed;
        }

        @Override
        public boolean canStart() {
            if (!this.emperorPenguin.hasEgg() || !this.emperorPenguin.getNavigation().isIdle()) {
                return false;
            }
            targetPos = findRandomValidPos();
            return targetPos != null;
        }


        private BlockPos findRandomValidPos() {
            BlockPos entityPos = this.emperorPenguin.getBlockPos();
            int range = 16;
            List<BlockPos> validPositions = new ArrayList<>();

            for (int dx = -range; dx <= range; dx++) {
                for (int dy = -range; dy <= range; dy++) {
                    for (int dz = -range; dz <= range; dz++) {
                        BlockPos pos = entityPos.add(dx, dy, dz);
                        if (isTargetPos(this.emperorPenguin.getWorld(), pos)) {
                            validPositions.add(pos);
                        }
                    }
                }
            }

            if (validPositions.isEmpty()) {
                return null;
            } else {
                return validPositions.get(this.emperorPenguin.getRandom().nextInt(validPositions.size()));
            }
        }


        @Override
        public void start() {
            this.emperorPenguin.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
        }

        @Override
        public boolean shouldContinue() {
            return this.emperorPenguin.hasEgg() && !this.emperorPenguin.getNavigation().isIdle();
        }

        @Override
        public void stop() {
            if (targetPos.isWithinDistance(this.emperorPenguin.getPos(), 2) && (this.emperorPenguin.getWorld().getBlockState(targetPos.up()).isAir() || this.emperorPenguin.getWorld().getBlockState(targetPos.up()).isReplaceable())) {
                World world = this.emperorPenguin.getWorld();
                world.setBlockState(targetPos.up(), ModBlocks.EMPEROR_PENGUIN_EGG.getDefaultState(), 3);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, targetPos, GameEvent.Emitter.of(this.emperorPenguin, ModBlocks.EMPEROR_PENGUIN_EGG.getDefaultState()));
                this.emperorPenguin.setHasEgg(false);
            }
        }

        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            return world.getBlockState(pos).isIn(BlockTags.ICE) || world.getBlockState(pos).isOf(Blocks.SNOW_BLOCK) || world.getBlockState(pos).isIn(BlockTags.DIRT);
        }
    }

    static class EmperorPenguinMoveControl extends MoveControl {
        private final EmperorPenguinEntity emperorPenguin;

        EmperorPenguinMoveControl(EmperorPenguinEntity emperorPenguin) {
            super(emperorPenguin);
            this.emperorPenguin = emperorPenguin;
        }

        private void updateVelocity() {
            if (this.emperorPenguin.isTouchingWater()) {
                this.emperorPenguin.setVelocity(this.emperorPenguin.getVelocity().add(0.0, 0.005, 0.0));
                this.emperorPenguin.setMovementSpeed(Math.max(this.emperorPenguin.getMovementSpeed() / 2.0F, 0.08F));
                if (this.emperorPenguin.isBaby()) {
                    this.emperorPenguin.setMovementSpeed(Math.max(this.emperorPenguin.getMovementSpeed() / 3.0F, 0.06F));
                }
            } else if (this.emperorPenguin.isOnGround()) {
                this.emperorPenguin.setMovementSpeed(this.emperorPenguin.getMovementSpeed());
            }

        }

        public void tick() {
            this.updateVelocity();
            if (this.state == State.MOVE_TO && !this.emperorPenguin.getNavigation().isIdle()) {
                double x = this.targetX - this.emperorPenguin.getX();
                double y = this.targetY - this.emperorPenguin.getY();
                double z = this.targetZ - this.emperorPenguin.getZ();
                double sqrt = Math.sqrt(x * x + y * y + z * z);
                if (sqrt < 9.999999747378752E-6) {
                    this.entity.setMovementSpeed(0.0F);
                } else {
                    y /= sqrt;
                    float h = (float)(MathHelper.atan2(z, x) * 57.2957763671875) - 90.0F;
                    this.emperorPenguin.setYaw(this.wrapDegrees(this.emperorPenguin.getYaw(), h, 90.0F));
                    this.emperorPenguin.bodyYaw = this.emperorPenguin.getYaw();
                    float i = (float)(this.speed * this.emperorPenguin.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    this.emperorPenguin.setMovementSpeed(MathHelper.lerp(0.125F, this.emperorPenguin.getMovementSpeed(), i));
                    this.emperorPenguin.setVelocity(this.emperorPenguin.getVelocity().add(0.0, (double)this.emperorPenguin.getMovementSpeed() * y * 0.1, 0.0));
                }
            } else {
                this.emperorPenguin.setMovementSpeed(0.0F);
            }
        }
    }

    static class EmperorPenguinSwimNavigation extends AmphibiousSwimNavigation {
        EmperorPenguinSwimNavigation(EmperorPenguinEntity owner, World world) {
            super(owner, world);
        }

        public boolean isValidPosition(BlockPos pos) {
            if (this.entity instanceof EmperorPenguinEntity) {
                return this.world.getBlockState(pos).isOf(Blocks.WATER) || (this.world.getBlockState(pos).isReplaceable() && this.world.getBlockState(pos.down()).isSolidBlock(this.world, pos.down()));
            }

            return !this.world.getBlockState(pos.down()).isAir();
        }
    }

    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement() && this.isTouchingWater()) {
            this.updateVelocity(0.1F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }

    }
}
