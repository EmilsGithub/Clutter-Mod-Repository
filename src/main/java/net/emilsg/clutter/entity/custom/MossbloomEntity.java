package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.variants.MossbloomVariant;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.SpawnReason;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MossbloomEntity extends AnimalEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("mossbloom.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("mossbloom.walk");

    private static final RawAnimation WAG_TAIL = RawAnimation.begin().thenPlay("mossbloom.wag_tail");
    private static final RawAnimation LE_DROP = RawAnimation.begin().thenPlay("mossbloom.le_drop");
    private static final RawAnimation RE_DROP = RawAnimation.begin().thenPlay("mossbloom.re_drop");
    private static final RawAnimation EARS_DROP = RawAnimation.begin().thenPlay("mossbloom.ears_drop");

    public MossbloomEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.5f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.5));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.SMALL_DRIPLEAF, Items.BIG_DRIPLEAF), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.0));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0, 1));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        BlockPos pos = this.getBlockPos();
        BlockState blockState = world.getBlockState(pos.down());
        return (blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isOf(Blocks.STONE) || blockState.isOf(Blocks.MOSS_BLOCK) || blockState.isOf(Blocks.CLAY));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SMALL_DRIPLEAF);
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
            child.setVariant(Util.getRandom(MossbloomVariant.values(), this.random));
            child.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            world.spawnEntityAndPassengers(child);
            world.sendEntityStatus(this, (byte)18);
            if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
            }

        }
    }



    public static boolean isValidNaturalSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isValidLushSpawn(type, world, spawnReason, pos, random) && isLightLevelValidForNaturalSpawn(world, pos);
    }

    public static boolean isValidLushSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isOf(Blocks.MOSS_BLOCK) ||
                world.getBlockState(pos.down()).isOf(Blocks.MOSS_CARPET) ||
                world.getBlockState(pos.down()).isOf(Blocks.CLAY);
    }

    protected static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MossbloomEntity child = ModEntities.MOSSBLOOM.create(world);
        assert child != null;
        child.setVariant(Util.getRandom(MossbloomVariant.values(), this.random));
        return child;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 10, this::predicate));
        controllerRegistrar.add(new AnimationController<>(this, "idle_controller", 10, this::idlePredicate));
    }

    private <T extends GeoAnimatable> PlayState idlePredicate(AnimationState<T> tAnimationState) {
        if(this.random.nextInt(200) == 0) {
            tAnimationState.getController().setAnimation(this.random.nextBoolean() ? WAG_TAIL : this.random.nextBoolean() ? LE_DROP : this.random.nextBoolean() ? RE_DROP : EARS_DROP);
        }
        return PlayState.CONTINUE;
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(WALK);
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(IDLE);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        MossbloomVariant variant = Util.getRandom(MossbloomVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(MossbloomEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public MossbloomVariant getVariant() {
        return MossbloomVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(MossbloomVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
}
