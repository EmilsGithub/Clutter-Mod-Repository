package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CrimsonNewtEntity extends ClutterAnimalEntity implements Angerable {
    int ticker = 6000;

    @Nullable private UUID angryAt;
    private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(CrimsonNewtEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private static final TrackedData<Boolean> MOVING = DataTracker.registerData(CrimsonNewtEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> SIZE = DataTracker.registerData(CrimsonNewtEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> FUNGI = DataTracker.registerData(CrimsonNewtEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.CRIMSON_ROOTS);

    public CrimsonNewtEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0f, true));
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.2f));
        this.goalSelector.add(5, new TemptGoal(this, 1.2f, BREEDING_INGREDIENT, false));
        this.goalSelector.add(6, new FollowParentGoal(this, 1.2f));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0f, 0.3f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new UniversalAngerGoal<>(this, true));
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isPersistent() && this.getWorld().getDimensionEntry().matchesKey(DimensionTypes.THE_NETHER);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGER_TIME, 0);
        this.dataTracker.startTracking(SIZE, 0f);
        this.dataTracker.startTracking(MOVING, false);
        this.dataTracker.startTracking(FUNGI, 1);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0 && !this.isMoving()) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
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

    public void setMoving(boolean moving) {
        this.dataTracker.set(MOVING, moving);
    }

    public boolean isMoving() {
        return this.dataTracker.get(MOVING);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        CrimsonNewtEntity crimsonNewtEntity = ModEntities.CRIMSON_NEWT.create(world);
        if (crimsonNewtEntity != null) {
            crimsonNewtEntity.setPersistent();
        }

        return crimsonNewtEntity;
    }

    @Override
    public void setBaby(boolean baby) {
        float scaledSize;
        switch (random.nextInt(3) + 1) {
            default -> scaledSize = 1;
            case 2 -> scaledSize = 1.25f;
            case 3 -> scaledSize = 1.5f;
        }
        this.setNewtSize(scaledSize);
        this.setFungiCount(random.nextInt(5) + 1);
        super.setBaby(baby);
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        CrimsonNewtEntity crimsonNewtEntity = (CrimsonNewtEntity) this.createChild(world, other);

        if(crimsonNewtEntity == null) return;

        float scaledSize;
        switch (random.nextInt(3) + 1) {
            default -> scaledSize = 1;
            case 2 -> scaledSize = 1.25f;
            case 3 -> scaledSize = 1.5f;
        }

        crimsonNewtEntity.setBaby(true);
        crimsonNewtEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
        crimsonNewtEntity.setNewtSize(scaledSize);
        crimsonNewtEntity.setFungiCount(random.nextInt(5) + 1);
        this.breed(world, other, crimsonNewtEntity);
        world.spawnEntityAndPassengers(crimsonNewtEntity);
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        float scaledSize;
        switch (random.nextInt(3) + 1) {
            default -> scaledSize = 1;
            case 2 -> scaledSize = 1.25f;
            case 3 -> scaledSize = 1.5f;
        }
        this.setNewtSize(scaledSize);
        this.setFungiCount(random.nextInt(5) + 1);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public void setNewtSize(float size) {
        this.dataTracker.set(SIZE, MathHelper.clamp(size, 0f, 1.5f));
        this.calculateDimensions();
    }

    public float getNewtSize() {
        return this.dataTracker.get(SIZE);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.4F;
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (SIZE.equals(data)) {
            this.calculateDimensions();
        }

        super.onTrackedDataSet(data);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt);
        nbt.putFloat("Size", this.getNewtSize());
        nbt.putInt("Fungi", this.getFungiCount());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.getWorld(), nbt);
        this.setNewtSize(nbt.getFloat("Size"));
        this.setFungiCount(nbt.getInt("Fungi"));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ENTITY_CRIMSON_NEWT_HURT;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return super.getDimensions(pose).scaled(0.65f * this.getNewtSize());
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.getWorld().isClient) {
            BlockPos oldPos = this.getBlockPos();
            BlockPos newPos = this.getBlockPos();
            this.setMoving(oldPos != newPos);
            this.tickAngerLogic((ServerWorld)this.getWorld(), true);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack stackInHand = player.getStackInHand(hand);
        World world = this.getWorld();
        BlockPos pos = this.getBlockPos();
        int fungiCount = this.getFungiCount();

        if(stackInHand.getItem() instanceof ShearsItem && !world.isClient && fungiCount != 0) {
            if(!player.getAbilities().creativeMode) stackInHand.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            this.dropStack(new ItemStack(Items.CRIMSON_FUNGUS, fungiCount));
            world.playSound(null, pos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            this.setFungiCount(0);
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if(!world.isClient) {
            ticker--;

            if (ticker <= 0 && this.getFungiCount() != 5) {
                this.setFungiCount(this.getFungiCount() + 1);
                ticker = 6000;
            }
        }

        if(world.isClient) {
            this.setupAnimationStates();
        }
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int angerTime) {
        this.dataTracker.set(ANGER_TIME, angerTime);
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.hasAngerTime() && super.canBeLeashedBy(player);
    }

    public boolean shouldAngerAt(LivingEntity entity) {
        if (!this.canTarget(entity)) {
            return false;
        } else {
            return entity.getType() == EntityType.PLAYER && this.isUniversallyAngry(entity.getWorld()) || entity.getUuid().equals(this.getAngryAt());
        }
    }

    public boolean isUniversallyAngry(World world) {
        return world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER) && this.hasAngerTime() && this.getAngryAt() == null;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.CRIMSON_ROOTS);
    }

    public int getFungiCount() {
        return this.dataTracker.get(FUNGI);
    }

    public void setFungiCount(int count) {
        this.dataTracker.set(FUNGI, MathHelper.clamp(count, 0, 5));
    }
}
