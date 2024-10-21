package net.emilsg.clutter.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public record NecrosisEnchantmentEffect(int level) implements EnchantmentEntityEffect {

    public static final MapCodec<NecrosisEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
            .group(Codec.INT.fieldOf("level").forGetter(NecrosisEnchantmentEffect::level))
            .apply(instance, NecrosisEnchantmentEffect::new));

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (!(target.getWorld() instanceof ServerWorld serverWorld)) return;

        if (!(target instanceof LivingEntity livingTarget) || target.getWorld().isClient()) return;

        boolean targetIsAlive = livingTarget.isAlive();

        if (!targetIsAlive && Math.random() < (0.25 * level)) {
            LivingEntity zombie = EntityType.ZOMBIE.create(serverWorld);

            if (target instanceof PiglinEntity piglin) zombie = piglin.convertTo(EntityType.ZOMBIFIED_PIGLIN, true);
            else if (target instanceof HoglinEntity hoglin) zombie = hoglin.convertTo(EntityType.ZOGLIN, true);
            else if (target instanceof VillagerEntity villager) zombie = convertToZombieVillager(villager, serverWorld);
            else if (target instanceof PlayerEntity player) zombie = convertToZombie(player, true, serverWorld);
            if (zombie == null) return;

            serverWorld.spawnEntity(zombie);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }

    @Nullable
    public ZombieEntity convertToZombie(PlayerEntity player, boolean keepEquipment, World world) {
        if (player.isRemoved()) {
            return null;
        }
        ZombieEntity zombie = EntityType.ZOMBIE.create(world);
        if (zombie == null) {
            return null;
        }
        zombie.copyPositionAndRotation(player);
        zombie.setBaby(false);
        zombie.setAiDisabled(false);
        if (player.getName() != null) {
            zombie.setCustomName(player.getName());
            zombie.setCustomNameVisible(true);
        }
        zombie.setPersistent();
        zombie.setInvulnerable(false);
        if (keepEquipment) {
            zombie.setCanPickUpLoot(true);
        }
        world.spawnEntity(zombie);
        if (player.hasVehicle()) {
            Entity entity = player.getVehicle();
            player.stopRiding();
            zombie.startRiding(entity, true);
        }
        return zombie;
    }

    private ZombieEntity convertToZombieVillager(VillagerEntity villagerEntity, ServerWorld world) {
        ZombieVillagerEntity zombieVillagerEntity = villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
        if (zombieVillagerEntity != null) {
            zombieVillagerEntity.initialize(world, world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true));
            zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
            zombieVillagerEntity.setGossipData(villagerEntity.getGossip().serialize(NbtOps.INSTANCE));
            zombieVillagerEntity.setOfferData(villagerEntity.getOffers().copy());
            zombieVillagerEntity.setXp(villagerEntity.getExperience());

            return zombieVillagerEntity;
        }
        return null;
    }
}
