package net.emilsg.clutter.entity.custom;

import net.emilsg.clutter.entity.custom.goal.EchofinConditionalActiveTargetGoal;
import net.emilsg.clutter.entity.custom.goal.EchofinWanderAroundGoal;
import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.emilsg.clutter.entity.variants.EchofinVariant;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EchofinEntity extends ClutterAnimalEntity {

    private static final TrackedData<BlockPos> HOME_POS = DataTracker.registerData(EchofinEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(EchofinEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ABILITY_TIMER = DataTracker.registerData(EchofinEntity.class, TrackedDataHandlerRegistry.INTEGER);

    final int maxAbilityTimer = 2400;

    public final AnimationState movingAnimState = new AnimationState();
    private int animationTimeout = 0;


    public EchofinEntity(EntityType<? extends ClutterAnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.lookControl = new EchofinLookControl(this);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return ClutterAnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0f);
    }

    public static boolean isValidSpawn(EntityType<? extends ClutterAnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModBlockTags.ECHOFINS_SPAWN_ON);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(2, new TeleportOrLevitateAttackGoal(this, 3.0, true));
        this.goalSelector.add(3, new EchofinWanderAroundGoal(this));
        this.targetSelector.add(1, new EchofinConditionalActiveTargetGoal(this, PlayerEntity.class, false));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(HOME_POS, BlockPos.ORIGIN);
        builder.add(DATA_ID_TYPE_VARIANT, 0);
        builder.add(ABILITY_TIMER, 0);
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return world.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    private void setupAnimationStates() {
        if (this.animationTimeout <= 0) {
            this.animationTimeout = 20;
            this.movingAnimState.start(this.age);
        } else {
            --this.animationTimeout;
        }
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        super.onDamaged(damageSource);
        this.setEntityAbilityTimer(0);
    }

    @Override
    public void tick() {
        super.tick();
        World world = this.getWorld();
        if (world instanceof ServerWorld) {
            setEntityAbilityTimer(getAbilityTimerEntitiesTimer() + random.nextInt(3));
        }

        if (hasAbility() && random.nextInt(1000) == 0) {
            setEntityAbilityTimer(0);
        }

        if (world.isClient) {
            this.setupAnimationStates();

            if (this.getVariant() == EchofinVariant.CHORUS && random.nextBoolean()) {
                this.getWorld().addParticle(ParticleTypes.PORTAL, true, this.getX() + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), this.getY() + random.nextDouble() / 16.0 * (double) (random.nextBoolean() ? 1 : -1), this.getZ() + random.nextDouble() / 4.0 * (double) (random.nextBoolean() ? 1 : -1), (random.nextBoolean() ? 0.1 : -0.1), (random.nextBoolean() ? 0.1 : -0.1), (random.nextBoolean() ? 0.1 : -0.1));
            }
        }
    }

    @Override
    public int getLimitPerChunk() {
        return 3;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack heldItem = player.getStackInHand(hand);

        if (!this.getWorld().isClient && heldItem.isOf(Items.BUCKET)) {
            EchofinVariant variant = this.getVariant();
            Item returnItem;
            if (Objects.requireNonNull(variant) == EchofinVariant.CHORUS) {
                returnItem = ModItems.CHORUS_ECHOFIN_BUCKET;
            } else {
                returnItem = ModItems.LEVITATING_ECHOFIN_BUCKET;
            }
            if (player.getStackInHand(hand).getCount() == 1) {
                player.setStackInHand(hand, new ItemStack(returnItem));
            } else {
                heldItem.decrement(1);
                if (player.getInventory().getEmptySlot() > 0) {
                    player.giveItemStack(new ItemStack(returnItem));
                } else {
                    this.dropItem(returnItem);
                }
            }
            player.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0f, 1.5f);
            this.remove(RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    public BlockPos getHomePos() {
        return this.dataTracker.get(HOME_POS);
    }

    public void setHomePos(BlockPos pos) {
        this.dataTracker.set(HOME_POS, pos);
    }

    public boolean shouldLevitatePlayers() {
        return hasAbility() && this.getVariant() == EchofinVariant.LEVITATING;
    }

    public boolean shouldTeleportPlayers() {
        return hasAbility() && this.getVariant() == EchofinVariant.CHORUS;
    }

    public boolean hasAbility() {
        return this.getAbilityTimerEntitiesTimer() >= maxAbilityTimer;
    }

    public int getAbilityTimerEntitiesTimer() {
        return this.dataTracker.get(ABILITY_TIMER);
    }

    public void setEntityAbilityTimer(int timer) {
        this.dataTracker.set(ABILITY_TIMER, timer);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    public boolean isExperienceDroppingDisabled() {
        return true;
    }


    private void teleportPlayer(PlayerEntity player, World world) {
        for (int i = 0; i < 16; i++) {
            double d = player.getX() + (player.getRandom().nextDouble() - 0.5) * 512.0;
            double e = MathHelper.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
            double f = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 512.0;

            if (player.hasVehicle()) {
                player.stopRiding();
            }

            Vec3d vec3d = player.getPos();
            if (player.teleport(d, e, f, true)) {
                world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(player));
                SoundEvent soundEvent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                SoundCategory soundCategory = SoundCategory.PLAYERS;
                this.setEntityAbilityTimer(0);

                world.playSound(null, player.getX(), player.getY(), player.getZ(), soundEvent, soundCategory);
                player.onLanding();
                break;
            }
        }
    }

    private void levitatePlayer(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, 2), this);
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
    public boolean shouldSpawnSprintingParticles() {
        return false;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("HomePosX", this.getHomePos().getX());
        nbt.putInt("HomePosY", this.getHomePos().getY());
        nbt.putInt("HomePosZ", this.getHomePos().getZ());
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("AbilityTimer", this.getAbilityTimerEntitiesTimer());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        int i = nbt.getInt("HomePosX");
        int j = nbt.getInt("HomePosY");
        int k = nbt.getInt("HomePosZ");
        this.setHomePos(new BlockPos(i, j, k));
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
        this.setEntityAbilityTimer(nbt.getInt("AbilityTimer"));
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
        return SoundEvents.ENTITY_COD_DEATH;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        EchofinVariant variant = Util.getRandom(EchofinVariant.values(), this.random);

        setVariant(variant);
        this.setHomePos(this.getBlockPos());
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void refreshPositionAndAngles(BlockPos pos, float yaw, float pitch) {
        this.setHomePos(pos);
        super.refreshPositionAndAngles(pos, yaw, pitch);
    }

    private static class TeleportOrLevitateAttackGoal extends MeleeAttackGoal {
        private final EchofinEntity echofinEntity;

        public TeleportOrLevitateAttackGoal(EchofinEntity echofinEntity, double speed, boolean pauseWhenMobIdle) {
            super(echofinEntity, speed, pauseWhenMobIdle);
            this.echofinEntity = echofinEntity;
        }

        @Override
        protected void attack(LivingEntity target) {
            World world = this.echofinEntity.getWorld();
            if (this.canAttack(target)) {
                this.resetCooldown();
                this.mob.swingHand(Hand.MAIN_HAND);
                if(this.mob.tryAttack(target) && target instanceof PlayerEntity player) {
                    if (echofinEntity.shouldTeleportPlayers()) echofinEntity.teleportPlayer(player, world);
                    else if (echofinEntity.shouldLevitatePlayers()) echofinEntity.levitatePlayer(player);
                }
            }
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public EchofinVariant getVariant() {
        return EchofinVariant.byId(this.getTypeVariant() & 255);
    }

    public void setVariant(EchofinVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private static class EchofinLookControl extends LookControl {
        EchofinLookControl(MobEntity entity) {
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