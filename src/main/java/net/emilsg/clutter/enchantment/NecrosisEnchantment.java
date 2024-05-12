package net.emilsg.clutter.enchantment;

import net.emilsg.clutter.Clutter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NecrosisEnchantment extends Enchantment {
    private long lastCallTime = 0;

    protected NecrosisEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentTarget.WEAPON, slots);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(!(user.getWorld() instanceof ServerWorld serverWorld)) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCallTime < 100) {
            return;
        }
        lastCallTime = currentTime;

        if(!(target instanceof LivingEntity livingTarget) || user.getWorld().isClient() || target.getWorld().isClient()) return;

        int necrosisLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(new Identifier(Clutter.MOD_ID, "necrosis")), user.getEquippedStack(EquipmentSlot.MAINHAND));

        boolean targetIsAlive = livingTarget.isAlive();

        if(!targetIsAlive && Math.random() < (0.25 * necrosisLevel)) {
            LivingEntity zombie = EntityType.ZOMBIE.create(serverWorld);

            if(target instanceof PiglinEntity piglin) {
                zombie = piglin.convertTo(EntityType.ZOMBIFIED_PIGLIN, true);
            } else if(target instanceof HoglinEntity hoglin) {
                zombie = hoglin.convertTo(EntityType.ZOGLIN, true);
            } else if(target instanceof VillagerEntity villager) {
                zombie = villager.convertTo(EntityType.ZOMBIE_VILLAGER, true);
            } else if (target instanceof PlayerEntity player) {
                zombie = convertToZombie(player, true, serverWorld);
            }
            if(zombie == null) return;

            serverWorld.spawnEntity(zombie);
        }
    }

    public boolean isTreasure() {
        return true;
    }

    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if (stack.getItem() instanceof AxeItem) {
            return true;
        }
        return super.isAcceptableItem(stack);
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

}