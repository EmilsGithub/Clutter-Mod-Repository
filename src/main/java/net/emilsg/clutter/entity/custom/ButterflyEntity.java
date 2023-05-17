package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.chunk.ChunkStatus;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class ButterflyEntity extends AnimalEntity implements GeoEntity {
    private final AnimatableInstanceCache CACHE = new SingletonAnimatableInstanceCache(this);
    private static final TrackedData<BlockPos> HOME_POS;

    public ButterflyEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.lookControl = new ButterflyLookControl(this);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HOME_POS, BlockPos.ORIGIN);
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    public void setHomePos(BlockPos pos) {
        this.dataTracker.set(HOME_POS, pos);
    }

    BlockPos getHomePos() {
        return (BlockPos)this.dataTracker.get(HOME_POS);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    public void breed(ServerWorld world, AnimalEntity other) {
            ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
            if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
                serverPlayerEntity = other.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, null);
            }

            this.setBreedingAge(6000);
            other.setBreedingAge(6000);
            this.resetLoveTicks();
            other.resetLoveTicks();
            this.dropItem(ModItems.BUTTERFLY_COCOON);
            world.sendEntityStatus(this, (byte)18);
    }

    @Override
    public boolean isExperienceDroppingDisabled() {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(1, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.HONEY_BOTTLE), false));
        this.goalSelector.add(2, new ButterflyWanderAroundGoal());
    }

    public static boolean isValidSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModBlockTags.BUTTERFLY_VALID);
    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    class ButterflyWanderAroundGoal extends Goal {
        private BlockPos homePos;

        ButterflyWanderAroundGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            return ButterflyEntity.this.navigation.isIdle() && ButterflyEntity.this.random.nextInt(10) == 0;
        }

        public boolean shouldContinue() {
            return ButterflyEntity.this.navigation.isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                ButterflyEntity.this.navigation.startMovingAlong(ButterflyEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }
        }

        private Vec3d getRandomLocation() {
            if (this.homePos == null) {
                this.homePos = ButterflyEntity.this.getHomePos();
            }

            Vec3d vec3d2 = ButterflyEntity.this.getRotationVec(0.0F);
            Vec3d vec3d3 = AboveGroundTargeting.find(ButterflyEntity.this, 24, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 2);

            if (vec3d3 != null && ButterflyEntity.this.getBlockPos().getSquaredDistance(Vec3d.ofCenter(homePos)) > 2 * 2) {
                return vec3d3;
            }

            if (ButterflyEntity.this.world.isNight() && ButterflyEntity.this.getBlockPos().getSquaredDistance(Vec3d.ofCenter(homePos)) > 8 * 8) {
                return Vec3d.ofCenter(homePos);
            }

            BlockPos blockpos = homePos.add(-2 + ButterflyEntity.this.random.nextInt(5), -1 + ButterflyEntity.this.random.nextInt(3), -2 + ButterflyEntity.this.random.nextInt(5));

            if (!ButterflyEntity.this.world.getBlockState(blockpos).isOpaque()) {
                return Vec3d.ofCenter(blockpos);
            }

            return null;
        }
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        if (!world.isClient && heldItem.isOf(Items.GLASS_BOTTLE)) {
            ButterflyVariant variant = this.getVariant();
            Item returnItem;
            switch (variant) {
                case RED -> returnItem = ModItems.RED_BUTTERFLY_IN_A_BOTTLE;
                case BLUE -> returnItem = ModItems.BLUE_BUTTERFLY_IN_A_BOTTLE;
                case PURPLE -> returnItem = ModItems.PURPLE_BUTTERFLY_IN_A_BOTTLE;
                case WHITE -> returnItem = ModItems.WHITE_BUTTERFLY_IN_A_BOTTLE;
                case GRAY -> returnItem = ModItems.GRAY_BUTTERFLY_IN_A_BOTTLE;
                case ORANGE -> returnItem = ModItems.ORANGE_BUTTERFLY_IN_A_BOTTLE;
                case LIME -> returnItem = ModItems.LIME_BUTTERFLY_IN_A_BOTTLE;
                case GREEN -> returnItem = ModItems.GREEN_BUTTERFLY_IN_A_BOTTLE;
                case BLACK -> returnItem = ModItems.BLACK_BUTTERFLY_IN_A_BOTTLE;
                case LIGHT_GRAY -> returnItem = ModItems.LIGHT_GRAY_BUTTERFLY_IN_A_BOTTLE;
                case LIGHT_BLUE -> returnItem = ModItems.LIGHT_BLUE_BUTTERFLY_IN_A_BOTTLE;
                case BROWN -> returnItem = ModItems.BROWN_BUTTERFLY_IN_A_BOTTLE;
                case CYAN -> returnItem = ModItems.CYAN_BUTTERFLY_IN_A_BOTTLE;
                case MAGENTA -> returnItem = ModItems.MAGENTA_BUTTERFLY_IN_A_BOTTLE;
                case PINK -> returnItem = ModItems.PINK_BUTTERFLY_IN_A_BOTTLE;
                default -> returnItem = ModItems.YELLOW_BUTTERFLY_IN_A_BOTTLE;
            }
            if(!player.getAbilities().creativeMode) {
                heldItem.decrement(1);
            }
            player.giveItemStack(new ItemStack(returnItem));
            player.playSound(SoundEvents.BLOCK_WOOL_FALL, SoundCategory.PLAYERS, 1.0f, 1.5f);
            this.remove(RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.HONEY_BOTTLE);
    }

    @Override
    public boolean shouldSpawnSprintingParticles() {
        return false;
    }

    static class ButterflyLookControl extends LookControl {
        ButterflyLookControl(MobEntity entity) {
            super(entity);
        }

        public void tick() {
            super.tick();
        }

        protected boolean shouldStayHorizontal() {
            return true;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    //private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
    //    tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.butterfly.flying", Animation.LoopType.LOOP));
    //    return PlayState.CONTINUE;
    //}

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(tAnimationState.getController().getCurrentAnimation() == null) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.butterfly.flying", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return CACHE;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("HomePosX", this.getHomePos().getX());
        nbt.putInt("HomePosY", this.getHomePos().getY());
        nbt.putInt("HomePosZ", this.getHomePos().getZ());
        nbt.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        int i = nbt.getInt("HomePosX");
        int j = nbt.getInt("HomePosY");
        int k = nbt.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_WOOL_HIT;
    }

    @Override
    public boolean isFireImmune() {
        return this.getVariant().getId() >= 16;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_WOOL_BREAK;
    }

    /* VARIANTS */
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());

        ButterflyVariant variant = ButterflyVariant.byId(1);

        if(spawnReason.equals(SpawnReason.SPAWN_EGG)) {
            variant = Util.getRandom(ButterflyVariant.values(), this.random);
        }

        if (registryEntry.isIn(BiomeTags.IS_OVERWORLD)) {
            variant = ButterflyVariant.byId(this.random.nextInt(15));
        } else if (registryEntry.isIn(BiomeTags.IS_NETHER)) {
            if (registryEntry.matchesKey(BiomeKeys.WARPED_FOREST)) {
                variant = ButterflyVariant.WARPED;
            } else if (registryEntry.matchesKey(BiomeKeys.CRIMSON_FOREST)) {
                variant = ButterflyVariant.CRIMSON;
            } else if (registryEntry.matchesKey(BiomeKeys.SOUL_SAND_VALLEY)) {
                variant = ButterflyVariant.SOUL;
            } else {
                variant = ButterflyVariant.byId(this.random.nextBetween(16, 18));
            }
        }

        setVariant(variant);
        this.setHomePos(this.getBlockPos());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public ButterflyVariant getVariant() {
        return ButterflyVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(ButterflyVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    static {
        HOME_POS = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    }
}
