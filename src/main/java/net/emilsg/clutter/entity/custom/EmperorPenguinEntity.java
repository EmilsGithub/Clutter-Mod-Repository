package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.goal.EmperorPenguinLayEggGoal;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
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
        this.setStepHeight(1.0f);
        this.moveControl = new EmperorPenguinMoveControl(this);
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
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new EmperorPenguinMateGoal(this, 1));
        this.goalSelector.add(3, new EmperorPenguinLayEggGoal(this, 1, ModBlocks.EMPEROR_PENGUIN_EGG.getDefaultState()));
        this.goalSelector.add(4, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1));
        this.goalSelector.add(6, new MeleeAttackGoal(this, 1.25, true));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1, 1));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));

        this.targetSelector.add(5, new ActiveTargetGoal<>(this, SalmonEntity.class, true));
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, CodEntity.class, true));
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

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (!this.getWorld().isClient && this.hasEgg()) {
            this.setEggTimer(this.getEggTimer() + 1);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.EMPEROR_PENGUIN.create(world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_EGG, false);
        this.dataTracker.startTracking(EGG_TIMER, 0);
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
                    float h = (float) (MathHelper.atan2(z, x) * 57.2957763671875) - 90.0F;
                    this.emperorPenguin.setYaw(this.wrapDegrees(this.emperorPenguin.getYaw(), h, 90.0F));
                    this.emperorPenguin.bodyYaw = this.emperorPenguin.getYaw();
                    float i = (float) (this.speed * this.emperorPenguin.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    this.emperorPenguin.setMovementSpeed(MathHelper.lerp(0.125F, this.emperorPenguin.getMovementSpeed(), i));
                    this.emperorPenguin.setVelocity(this.emperorPenguin.getVelocity().add(0.0, (double) this.emperorPenguin.getMovementSpeed() * y * 0.1, 0.0));
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
}
