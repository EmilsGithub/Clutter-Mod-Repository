package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.entity.custom.goal.MossbloomDropHornsGoal;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.entity.variants.MossbloomVariant;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class MossbloomEntity extends ClutterAnimalEntity{

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(MossbloomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> HAS_HORNS = DataTracker.registerData(MossbloomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> HORN_DROP_TIMER = DataTracker.registerData(MossbloomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TIME_TILL_DROP = DataTracker.registerData(MossbloomEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> IS_SHAKING = DataTracker.registerData(MossbloomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shakingAnimationState = new AnimationState();
    public final AnimationState earTwitchAnimationStateLE = new AnimationState();
    public final AnimationState earTwitchAnimationStateRE = new AnimationState();
    public final AnimationState earTwitchAnimationStateBE = new AnimationState();
    public int idleAnimationTimeout = 0;
    public int shakingAnimationTimeout = 0;
    public int earTwitchAnimationTimeout = 0;

    public static int SHOULD_DROP_HORNS_VALUE = 12000;

    public MossbloomEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    private void setupAnimationStates() {
        if (this.shakingAnimationTimeout <= 0 && this.getIsShaking()) {
            this.shakingAnimationTimeout = 60;
            this.shakingAnimationState.start(this.age);
        } else {
            --this.shakingAnimationTimeout;
        }

        if (this.idleAnimationTimeout <= 0 && !this.isMoving()) {
            this.idleAnimationTimeout = 10;
            this.idleAnimationState.startIfNotRunning(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.earTwitchAnimationTimeout <= 0 && random.nextInt(100) == 0) {
            this.earTwitchAnimationTimeout = 5;
            this.pickRandomIdleAnim(random.nextInt(3));
        } else {
            --this.earTwitchAnimationTimeout;
        }
    }

    private void pickRandomIdleAnim(int i) {
        switch (i) {
            default -> this.earTwitchAnimationStateBE.startIfNotRunning(this.age);
            case 1 -> this.earTwitchAnimationStateRE.startIfNotRunning(this.age);
            case 2 -> this.earTwitchAnimationStateLE.startIfNotRunning(this.age);
        };
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isValidLushSpawn(type, world, spawnReason, pos, random) && isLightLevelValidForNaturalSpawn(world, pos);
    }

    public static boolean isValidLushSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isOf(Blocks.MOSS_BLOCK) ||
                world.getBlockState(pos.down()).isOf(Blocks.MOSS_CARPET) ||
                world.getBlockState(pos.down()).isOf(Blocks.CLAY);
    }

    @Override
    public int getLimitPerChunk() {
        return 2;
    }

    protected static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new MossbloomDropHornsGoal(this));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 1.5));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1));
        this.goalSelector.add(4, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.BIG_DRIPLEAF), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.0));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0, 1));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockPos pos = this.getBlockPos();
        BlockState blockState = world.getBlockState(pos.down());
        return (blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isOf(Blocks.STONE) || blockState.isOf(Blocks.MOSS_BLOCK) || blockState.isOf(Blocks.CLAY));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(HAS_HORNS, true);
        this.dataTracker.startTracking(HORN_DROP_TIMER, 0);
        this.dataTracker.startTracking(IS_SHAKING, false);
        this.dataTracker.startTracking(TIME_TILL_DROP, 0);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.BIG_DRIPLEAF);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other == this) {
            return false;
        } else if (other.getClass() != this.getClass()) {
            return false;
        } else if (((MossbloomEntity) other).getVariant().getId() == this.getVariant().getId()) {
            return false;
        } else {
            return this.isInLove() && other.isInLove();
        }
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        MossbloomEntity child = (MossbloomEntity) this.createChild(world, other);
        if (child != null) {
            ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
            if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
                serverPlayerEntity = other.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, child);
            }

            this.setBreedingAge(6000);
            other.setBreedingAge(6000);
            this.resetLoveTicks();
            other.resetLoveTicks();
            child.setBaby(true);

            boolean isVariantM = random.nextBoolean();
            child.setVariant(isVariantM ? MossbloomVariant.M : MossbloomVariant.F);

            child.setHasHorns(isVariantM);
            if (isVariantM) child.setHornDropTimer(-SHOULD_DROP_HORNS_VALUE);

            child.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            world.spawnEntityAndPassengers(child);
            world.sendEntityStatus(this, (byte) 18);
            if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
            }

        }
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if (world.isClient) {
            this.setupAnimationStates();
        }

        if(this.getVariant() == MossbloomVariant.M && !this.isBaby()) {
            if (this.getHornDropTimer() >= (SHOULD_DROP_HORNS_VALUE / 3)) this.setHasHorns(true);
            this.setHornDropTimer(this.getHornDropTimer() + random.nextInt(1) + 1);
        }

        if(this.getVariant() == MossbloomVariant.M && this.isBaby()) {
            this.setHasHorns(false);
        }
    }

    @Override
    protected void onGrowUp() {
        super.onGrowUp();

        if(!this.isBaby() && this.getVariant() == MossbloomVariant.M) {
            this.setHasHorns(true);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MossbloomEntity child = ModEntityTypes.MOSSBLOOM.create(world);
        assert child != null;
        child.setVariant(Util.getRandom(MossbloomVariant.values(), this.random));
        return child;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        MossbloomVariant variant = Util.getRandom(MossbloomVariant.values(), this.random);
        this.setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariantInt());
        nbt.putBoolean("HasHorns", this.getHasHorns());
        nbt.putInt("HornDropTimer", this.getHornDropTimer());
        nbt.putBoolean("IsShaking", this.getIsShaking());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        this.dataTracker.set(HAS_HORNS, nbt.getBoolean("HasHorns"));
        this.dataTracker.set(HORN_DROP_TIMER, nbt.getInt("HornDropTimer"));
        this.dataTracker.set(IS_SHAKING, nbt.getBoolean("IsShaking"));
    }

    public int getTimeTillDrop() {
        return this.dataTracker.get(TIME_TILL_DROP);
    }

    public void setTimeTillDrop(int timeTillDrop) {
        this.dataTracker.set(TIME_TILL_DROP, timeTillDrop);
    }

    public boolean getIsShaking() {
        return this.dataTracker.get(IS_SHAKING);
    }

    public void setIsShaking(boolean isShaking) {
        this.dataTracker.set(IS_SHAKING, isShaking);
    }

    public boolean getHasHorns() {
        return this.dataTracker.get(HAS_HORNS);
    }

    public void setHasHorns(boolean hasHorns) {
        this.dataTracker.set(HAS_HORNS, hasHorns);
    }

    public int getHornDropTimer() {
        return this.dataTracker.get(HORN_DROP_TIMER);
    }

    public void setHornDropTimer(int hornDropTimer) {
        this.dataTracker.set(HORN_DROP_TIMER, hornDropTimer);
    }

    public MossbloomVariant getVariant() {
        return MossbloomVariant.byId(this.getVariantInt() & 255);
    }

    public boolean isVariantOf(MossbloomVariant variant) {
        return this.getVariant() == variant;
    }

    public void setVariant(MossbloomVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    private int getVariantInt() {
        return this.dataTracker.get(VARIANT);
    }
}
