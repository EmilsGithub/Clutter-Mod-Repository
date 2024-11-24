package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.entity.custom.goal.*;
import net.emilsg.clutter.entity.custom.parent.ClutterFishEntity;
import net.emilsg.clutter.entity.variants.SeahorseVariant;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SeahorseEntity extends ClutterFishEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(SeahorseEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> HAS_CHILDREN = DataTracker.registerData(SeahorseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Float> HAS_CHILDREN_TIMER = DataTracker.registerData(SeahorseEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> CHILD = DataTracker.registerData(SeahorseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int ticker = 0;
    private int loveTicks;
    @Nullable private UUID lovingPlayer;
    protected int breedingAge;
    protected int forcedAge;
    protected int happyTicksRemaining;

    public final AnimationState swimmingAnimationState = new AnimationState();
    private int swimmingAnimationTimeout = 0;

    public final AnimationState flopAnimationState = new AnimationState();
    private int flopAnimationTimeout = 0;


    private void setupAnimationStates() {
        if (this.swimmingAnimationTimeout <= 0) {
            this.swimmingAnimationTimeout = 20;
            this.swimmingAnimationState.start(this.age);
        } else {
            --this.swimmingAnimationTimeout;
        }

        if (this.flopAnimationTimeout <= 0) {
            this.flopAnimationTimeout = 20;
            this.flopAnimationState.start(this.age);
        } else {
            --this.flopAnimationTimeout;
        }
    }

    public SeahorseEntity(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 0.7D));
        this.goalSelector.add(1, new SeahorseFollowParentGoal(this, 1.25D));
        this.goalSelector.add(2, new SeahorseMateGoal(this, 1D, SeahorseEntity.class));
        this.goalSelector.add(3, new SeahorseReleaseChildrenGoal(this));
        this.goalSelector.add(4, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.SEA_PICKLE), false));
        this.goalSelector.add(5, new SwimToRandomPlaceGoal(this, 0.5D));
        this.goalSelector.add(6, new SeahorseMoveToCoralGoal(this, 0.5D, 8));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return SeahorseEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2D);
    }

    public static boolean isValidNaturalSpawn(EntityType<? extends WaterCreatureEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos).getFluidState().isOf(Fluids.WATER);
    }


    @Override
    public void copyDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("Variant")) {
            this.setVariant(SeahorseVariant.byId(nbt.getInt("Variant")));
        }

        super.copyDataFromNbt(nbt);
    }

    public void copyDataToStack(SeahorseEntity entity, ItemStack stack) {
        stack.set(DataComponentTypes.CUSTOM_NAME, entity.getCustomName());
        NbtComponent.set(DataComponentTypes.BUCKET_ENTITY_DATA, stack, (nbtCompound) -> {
            if (entity.isAiDisabled()) {
                nbtCompound.putBoolean("NoAI", entity.isAiDisabled());
            }

            if (entity.isSilent()) {
                nbtCompound.putBoolean("Silent", entity.isSilent());
            }

            if (entity.hasNoGravity()) {
                nbtCompound.putBoolean("NoGravity", entity.hasNoGravity());
            }

            if (entity.isGlowing()) {
                nbtCompound.putBoolean("Glowing", entity.isGlowing());
            }

            if (entity.isInvulnerable()) {
                nbtCompound.putBoolean("Invulnerable", entity.isInvulnerable());
            }

            nbtCompound.putFloat("Health", entity.getHealth());
            nbtCompound.putInt("Variant", entity.getTypeVariant());
        });
    }


    public void copyDataFromNbt(SeahorseEntity entity, NbtCompound nbt) {
        if (nbt.contains("NoAI")) {
            entity.setAiDisabled(nbt.getBoolean("NoAI"));
        }

        if (nbt.contains("Silent")) {
            entity.setSilent(nbt.getBoolean("Silent"));
        }

        if (nbt.contains("NoGravity")) {
            entity.setNoGravity(nbt.getBoolean("NoGravity"));
        }

        if (nbt.contains("Glowing")) {
            entity.setGlowing(nbt.getBoolean("Glowing"));
        }

        if (nbt.contains("Invulnerable")) {
            entity.setInvulnerable(nbt.getBoolean("Invulnerable"));
        }

        if (nbt.contains("Variant")) {
            entity.setVariant(SeahorseVariant.byId(nbt.getInt("Variant")));
        }

        if (nbt.contains("Health", 99)) {
            entity.setHealth(nbt.getFloat("Health"));
        }

    }

    private ActionResult tryBucket(PlayerEntity player, Hand hand, SeahorseEntity seahorse) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.WATER_BUCKET && seahorse.isAlive()) {
            seahorse.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
            ItemStack bottleStack = new ItemStack(ModItems.SEAHORSE_BUCKET);
            copyDataToStack(seahorse, bottleStack);
            ItemStack seahorseBucketStack = ItemUsage.exchangeStack(itemStack, player, bottleStack, false);
            player.setStackInHand(hand, seahorseBucketStack);
            World world = seahorse.getWorld();

            seahorse.discard();
            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(HAS_CHILDREN, false);
        builder.add(HAS_CHILDREN_TIMER, 0.0f);
        builder.add(CHILD, false);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putBoolean("HasChildren", this.hasChildren());
        nbt.putFloat("HasChildrenTimer", this.getHasChildrenTimer());
        nbt.putInt("InLove", this.loveTicks);
        if (this.lovingPlayer != null) {
            nbt.putUuid("LoveCause", this.lovingPlayer);
        }
        nbt.putInt("Age", this.getBreedingAge());
        nbt.putInt("ForcedAge", this.forcedAge);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(VARIANT, nbt.getInt("Variant"));
        this.dataTracker.set(HAS_CHILDREN, nbt.getBoolean("HasChildren"));
        this.dataTracker.set(HAS_CHILDREN_TIMER, nbt.getFloat("HasChildrenTimer"));
        this.loveTicks = nbt.getInt("InLove");
        this.lovingPlayer = nbt.containsUuid("LoveCause") ? nbt.getUuid("LoveCause") : null;
        this.setBreedingAge(nbt.getInt("Age"));
        this.forcedAge = nbt.getInt("ForcedAge");
    }

    @Nullable
    public SeahorseEntity createChild(ServerWorld world, SeahorseEntity entity) {
        return ModEntityTypes.SEAHORSE.create(world);
    }

    public int getBreedingAge() {
        if (this.getWorld().isClient) {
            return (Boolean)this.dataTracker.get(CHILD) ? -1 : 1;
        } else {
            return this.breedingAge;
        }
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (CHILD.equals(data)) {
            this.calculateDimensions();
        }

        super.onTrackedDataSet(data);
    }

    public void growUp(int age, boolean overGrow) {
        int i = this.getBreedingAge();
        int j = i;
        i += age * 20;
        if (i > 0) {
            i = 0;
        }

        int k = i - j;
        this.setBreedingAge(i);
        if (overGrow) {
            this.forcedAge += k;
            if (this.happyTicksRemaining == 0) {
                this.happyTicksRemaining = 40;
            }
        }

        if (this.getBreedingAge() == 0) {
            this.setBreedingAge(this.forcedAge);
        }

    }

    public void growUp(int age) {
        this.growUp(age, false);
    }

    public void setBreedingAge(int age) {
        int i = this.getBreedingAge();
        this.breedingAge = age;
        if (i < 0 && age >= 0 || i >= 0 && age < 0) {
            this.dataTracker.set(CHILD, age < 0);
            this.onGrowUp();
        }

    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SEA_PICKLE);
    }

    public SeahorseVariant getVariant() {
        return SeahorseVariant.byId(this.getTypeVariant() & 255);
    }

    public void setVariant(SeahorseVariant variant) {
        this.dataTracker.set(VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(VARIANT);
    }

    public boolean hasChildren() {
        return this.dataTracker.get(HAS_CHILDREN);
    }

    public void setHasChildren(boolean hasChildren) {
        this.dataTracker.set(HAS_CHILDREN, hasChildren);
    }

    @Override
    protected void mobTick() {
        if (this.getBreedingAge() != 0) {
            this.loveTicks = 0;
        }

        super.mobTick();
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.isBreedingItem(itemStack)) {
            int i = this.getBreedingAge();
            if (!this.getWorld().isClient && i == 0 && this.canEat()) {
                this.eat(player, hand, itemStack);
                this.lovePlayer(player);
                return ActionResult.SUCCESS;
            }

            if (this.isBaby()) {
                this.eat(player, hand, itemStack);
                this.growUp(toGrowUpAge(-i), true);
                return ActionResult.success(this.getWorld().isClient);
            }

            if (this.getWorld().isClient) {
                return ActionResult.CONSUME;
            }
        }

        return tryBucket(player, hand, this);
    }

    protected void eat(PlayerEntity player, Hand hand, ItemStack stack) {
        if (!player.getAbilities().creativeMode) {
            stack.decrement(1);
        }

    }

    public boolean canEat() {
        return this.loveTicks <= 0;
    }

    public void lovePlayer(@Nullable PlayerEntity player) {
        this.loveTicks = 600;
        if (player != null) {
            this.lovingPlayer = player.getUuid();
        }

        this.getWorld().sendEntityStatus(this, (byte)18);
    }

    public void setLoveTicks(int loveTicks) {
        this.loveTicks = loveTicks;
    }

    public int getLoveTicks() {
        return this.loveTicks;
    }

    @Nullable
    public ServerPlayerEntity getLovingPlayer() {
        if (this.lovingPlayer == null) {
            return null;
        } else {
            PlayerEntity playerEntity = this.getWorld().getPlayerByUuid(this.lovingPlayer);
            return playerEntity instanceof ServerPlayerEntity ? (ServerPlayerEntity)playerEntity : null;
        }
    }

    public boolean isInLove() {
        return this.getLoveTicks() > 0;
    }

    public void resetLoveTicks() {
        this.setLoveTicks(0);
    }

    public boolean canBreedWith(SeahorseEntity other) {
        if (other == this) {
            return false;
        } else if (other.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && other.isInLove();
        }
    }

    public void breed(ServerWorld world, SeahorseEntity other) {
        this.breed(world, other, null);
    }

    public void breed(ServerWorld world, SeahorseEntity other, @Nullable SeahorseEntity baby) {
        this.setBreedingAge(6000);
        other.setBreedingAge(6000);
        this.setHasChildren(true);
        this.resetLoveTicks();
        other.resetLoveTicks();
        world.sendEntityStatus(this, (byte)18);
        if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }

    }

    public void handleStatus(byte status) {
        if (status == 18) {
            for(int i = 0; i < 7; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
            }
        } else {
            super.handleStatus(status);
        }

    }

    public float getHasChildrenTimer() {
        return this.dataTracker.get(HAS_CHILDREN_TIMER);
    }

    public void setHasChildrenTimer(float hasChildrenTimer) {
        this.dataTracker.set(HAS_CHILDREN_TIMER, hasChildrenTimer);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(true);
        }

        PassiveEntity.PassiveData passiveData = (PassiveEntity.PassiveData)entityData;
        if (passiveData.canSpawnBaby() && passiveData.getSpawnedCount() > 0 && world.getRandom().nextFloat() <= passiveData.getBabyChance()) {
            this.setBreedingAge(-24000);
        }

        passiveData.countSpawned();
        SeahorseVariant variant = Util.getRandom(SeahorseVariant.values(), this.random);
        this.setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();

        if(!world.isClient) {
            ticker++;
            if(this.hasChildren() && ticker == 120) {
                this.setHasChildrenTimer(this.getHasChildrenTimer() + 0.20f);
                ticker = 0;
            }
        }

        if (world.isClient) {
            this.setupAnimationStates();
        }
    }

    public void tickMovement() {
        if (!this.isTouchingWater() && this.isOnGround() && this.verticalCollision) {
            this.setVelocity(this.getVelocity().add(((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F), 0.4000000059604645, ((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F)));
            this.setOnGround(false);
            this.velocityDirty = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
        }

        if (this.getBreedingAge() != 0) {
            this.loveTicks = 0;
        }

        if (this.loveTicks > 0) {
            --this.loveTicks;
            if (this.loveTicks % 10 == 0) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                this.getWorld().addParticle(ParticleTypes.HEART, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
            }
        }

        if (this.getWorld().isClient) {
            if (this.happyTicksRemaining > 0) {
                if (this.happyTicksRemaining % 4 == 0) {
                    this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
                }

                --this.happyTicksRemaining;
            }
        } else if (this.isAlive()) {
            int i = this.getBreedingAge();
            if (i < 0) {
                ++i;
                this.setBreedingAge(i);
            } else if (i > 0) {
                --i;
                this.setBreedingAge(i);
            }
        }

        super.tickMovement();
    }

    protected void onGrowUp() {
        if (!this.isBaby() && this.hasVehicle()) {
            Entity var2 = this.getVehicle();
            if (var2 instanceof BoatEntity) {
                BoatEntity boatEntity = (BoatEntity)var2;
                if (!boatEntity.isSmallerThanBoat(this)) {
                    this.stopRiding();
                }
            }
        }

    }

    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.isFromBucket();
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL_FISH;
    }

    public boolean isBaby() {
        return this.getBreedingAge() < 0;
    }

    public void setBaby(boolean baby) {
        this.setBreedingAge(baby ? -24000 : 0);
    }

    public static int toGrowUpAge(int breedingAge) {
        return (int)((float)(breedingAge / 20) * 0.1F);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.loveTicks = 0;
            return super.damage(source, amount);
        }
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_TROPICAL_FISH_FLOP;
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ModItems.SEAHORSE_BUCKET);
    }

    public int getMaxChildren() {
        return 3;
    }
}
