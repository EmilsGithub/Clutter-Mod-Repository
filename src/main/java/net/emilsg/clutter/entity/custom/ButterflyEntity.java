package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.custom.goal.ButterflyDupeSporeBlossomGoal;
import net.emilsg.clutter.entity.custom.goal.ButterflyPlaceCocoonGoal;
import net.emilsg.clutter.entity.custom.goal.ButterflyWanderNetherGoal;
import net.emilsg.clutter.entity.custom.goal.ButterflyWanderOverworldGoal;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.FluidTags;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class ButterflyEntity extends ClutterAnimalEntity {
    private static final TrackedData<Boolean> HAS_COCOON = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> DUPE_TIMER = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState flyingAnimState = new AnimationState();
    private int animationTimeout = 0;


    public ButterflyEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
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
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    private static boolean isTodayAroundHalloween() {
        LocalDate localDate = LocalDate.now();
        int i = localDate.get(ChronoField.DAY_OF_MONTH);
        int j = localDate.get(ChronoField.MONTH_OF_YEAR);
        return j == 10 && i >= 20 || j == 11 && i <= 3;
    }

    public static boolean isValidSpawn(EntityType<? extends ClutterAnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModBlockTags.BUTTERFLIES_SPAWN_ON);
    }

    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    private void setupAnimationStates() {
        if (this.animationTimeout <= 0) {
            this.animationTimeout = 10;
            this.flyingAnimState.start(this.age);
        } else {
            --this.animationTimeout;
        }
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
    public void tickMovement() {
        super.tickMovement();

        if (this.getDupeTimer() < 8000) {
            this.setDupeTimer(this.getDupeTimer() + 1);
        }

        if (this.isAlive() && (this.isSubmergedIn(FluidTags.LAVA) || this.isSubmergedIn(FluidTags.WATER))) {
            this.kill();
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(1, new ButterflyPlaceCocoonGoal(this, 1.0));
        this.goalSelector.add(2, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.SUGAR), false));
        this.goalSelector.add(3, new ButterflyDupeSporeBlossomGoal(this, 1, 1200));
        this.goalSelector.add(4, new ButterflyWanderNetherGoal(this));
        this.goalSelector.add(4, new ButterflyWanderOverworldGoal(this));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_COCOON, false);
        this.dataTracker.startTracking(DUPE_TIMER, 0);
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void onKilledBy(@Nullable LivingEntity adversary) {
        if (isTodayAroundHalloween() && adversary instanceof PlayerEntity && random.nextInt(10) == 0) {
            adversary.damage(this.getWorld().getDamageSources().magic(), 6.0f);
        }
        super.onKilledBy(adversary);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        super.breed(world, other);
        ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
        if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
            serverPlayerEntity = other.getLovingPlayer();
        }

        if (serverPlayerEntity != null) {
            serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
            Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, null);
        }

        this.setHasCocoon(true);
        this.setBreedingAge(6000);
        other.setBreedingAge(6000);
        this.resetLoveTicks();
        other.resetLoveTicks();
        world.sendEntityStatus(this, (byte) 18);
    }

    @Override
    public boolean isExperienceDroppingDisabled() {
        return true;
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

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        if (!this.getWorld().isClient && heldItem.isOf(Items.GLASS_BOTTLE)) {
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
                case CRIMSON -> returnItem = ModItems.CRIMSON_BUTTERFLY_IN_A_BOTTLE;
                case WARPED -> returnItem = ModItems.WARPED_BUTTERFLY_IN_A_BOTTLE;
                case SOUL -> returnItem = ModItems.SOUL_BUTTERFLY_IN_A_BOTTLE;
                default -> returnItem = ModItems.YELLOW_BUTTERFLY_IN_A_BOTTLE;
            }
            ItemStack returnStack = new ItemStack(returnItem);

            if (!player.getAbilities().creativeMode) {
                heldItem.decrement(1);
            }

            if (!player.getInventory().insertStack(returnStack)) {
                player.dropItem(returnStack, false);
            }

            player.playSound(SoundEvents.BLOCK_WOOL_FALL, SoundCategory.PLAYERS, 1.0f, 1.5f);
            this.remove(RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SUGAR);
    }

    @Override
    public boolean shouldSpawnSprintingParticles() {
        return false;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("HasCocoon", this.hasCocoon());
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("DupeTimer", this.getDupeTimer());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasCocoon(nbt.getBoolean("HasCocoon"));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
        this.setDupeTimer(nbt.getInt("DupeTimer"));
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

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        ButterflyVariant variant = ButterflyVariant.byId(0);

        if (spawnReason.equals(SpawnReason.SPAWN_EGG)) {
            variant = Util.getRandom(ButterflyVariant.values(), this.random);
        }

        if (registryEntry.isIn(BiomeTags.IS_OVERWORLD)) {
            variant = ButterflyVariant.byId(this.random.nextInt(15) + 1);
        } else if (registryEntry.isIn(BiomeTags.IS_NETHER)) {
            if (registryEntry.matchesKey(BiomeKeys.WARPED_FOREST)) {
                variant = ButterflyVariant.WARPED;
            } else if (registryEntry.matchesKey(BiomeKeys.CRIMSON_FOREST)) {
                variant = ButterflyVariant.CRIMSON;
            } else if (registryEntry.matchesKey(BiomeKeys.SOUL_SAND_VALLEY)) {
                variant = ButterflyVariant.SOUL;
            } else {
                if (random.nextBoolean()) {
                    variant = ButterflyVariant.CRIMSON;
                } else if (random.nextBoolean()) {
                    variant = ButterflyVariant.WARPED;
                } else {
                    variant = ButterflyVariant.SOUL;
                }
            }
        }

        this.setVariant(variant);
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

    public void setVariant(ButterflyVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    public boolean hasCocoon() {
        return this.dataTracker.get(HAS_COCOON);
    }

    public void setHasCocoon(boolean hasCocoon) {
        this.dataTracker.set(HAS_COCOON, hasCocoon);
    }

    public int getDupeTimer() {
        return this.dataTracker.get(DUPE_TIMER);
    }

    public void setDupeTimer(int time) {
        this.dataTracker.set(DUPE_TIMER, time);
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
}