package net.emilsg.clutter.enchantment;

import net.emilsg.clutter.Clutter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NecrosisEnchantment extends Enchantment {
    private long lastCallTime = 0;

    protected NecrosisEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentTarget.WEAPON, slots);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCallTime < 100) {
            return;
        }
        lastCallTime = currentTime;

        if(!(target instanceof LivingEntity livingTarget) || user.getWorld().isClient() || target.getWorld().isClient()) return;

        int necrosisLevel = EnchantmentHelper.getLevel(Registries.ENCHANTMENT.get(new Identifier(Clutter.MOD_ID, "necrosis")), user.getEquippedStack(EquipmentSlot.MAINHAND));

        boolean targetIsAlive = livingTarget.isAlive();

        if(user.getWorld() instanceof ServerWorld serverWorld && !targetIsAlive && Math.random() < (0.25 * necrosisLevel)) {
            LivingEntity zombie = EntityType.ZOMBIE.create(serverWorld);

            if(target instanceof PiglinEntity piglin) {
                zombie = piglin.convertTo(EntityType.ZOMBIFIED_PIGLIN, true);
            } else if(target instanceof PlayerEntity player) {
                zombie = convertToZombie(player, true, serverWorld);
            } else if(target instanceof HoglinEntity hoglin) {
                zombie = hoglin.convertTo(EntityType.ZOGLIN, true);
            } else if(target instanceof VillagerEntity villager) {
                zombie = villager.convertTo(EntityType.ZOMBIE_VILLAGER, true);
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
            equipZombieWithNearbyItems(player, zombie);
        }
        world.spawnEntity(zombie);
        if (player.hasVehicle()) {
            Entity entity = player.getVehicle();
            player.stopRiding();
            zombie.startRiding(entity, true);
        }
        return zombie;
    }

    private void equipZombieWithNearbyItems(PlayerEntity player, ZombieEntity zombie) {
        ServerWorld world = (ServerWorld) player.getWorld();
        BlockPos playerPos = player.getBlockPos();

        Box searchBox = new Box(playerPos).expand(4);

        List<ItemEntity> nearbyItems = world.getEntitiesByClass(ItemEntity.class, searchBox, entity -> true);
        Set<ItemEntity> equippedItems = new HashSet<>();

        ItemStack strongestHelmet = ItemStack.EMPTY;
        ItemStack strongestChestplate = ItemStack.EMPTY;
        ItemStack strongestLeggings = ItemStack.EMPTY;
        ItemStack strongestBoots = ItemStack.EMPTY;
        ItemStack strongestWeapon = ItemStack.EMPTY;

        for (ItemEntity itemEntity : nearbyItems) {
            ItemStack itemStack = itemEntity.getStack();
            if (itemStack.getItem() instanceof ArmorItem armorItem) {
                switch (armorItem.getSlotType()) {
                    case HEAD -> strongestHelmet = getStrongerItem(strongestHelmet, itemStack);
                    case CHEST -> strongestChestplate = getStrongerItem(strongestChestplate, itemStack);
                    case LEGS -> strongestLeggings = getStrongerItem(strongestLeggings, itemStack);
                    case FEET -> strongestBoots = getStrongerItem(strongestBoots, itemStack);
                }
            } else if (isWeapon(itemStack)) {
                strongestWeapon = getStrongerItem(strongestWeapon, itemStack);
            }
        }

        zombie.equipStack(EquipmentSlot.HEAD, strongestHelmet);
        zombie.equipStack(EquipmentSlot.CHEST, strongestChestplate);
        zombie.equipStack(EquipmentSlot.LEGS, strongestLeggings);
        zombie.equipStack(EquipmentSlot.FEET, strongestBoots);
        zombie.equipStack(EquipmentSlot.MAINHAND, strongestWeapon);

        zombie.setEquipmentDropChance(EquipmentSlot.HEAD, 1f);
        zombie.setEquipmentDropChance(EquipmentSlot.CHEST, 1f);
        zombie.setEquipmentDropChance(EquipmentSlot.LEGS, 1f);
        zombie.setEquipmentDropChance(EquipmentSlot.FEET, 1f);
        zombie.setEquipmentDropChance(EquipmentSlot.MAINHAND, 1f);

        if (strongestHelmet != ItemStack.EMPTY) equippedItems.add(findItemEntity(nearbyItems, strongestHelmet));
        if (strongestChestplate != ItemStack.EMPTY) equippedItems.add(findItemEntity(nearbyItems, strongestChestplate));
        if (strongestLeggings != ItemStack.EMPTY) equippedItems.add(findItemEntity(nearbyItems, strongestLeggings));
        if (strongestBoots != ItemStack.EMPTY) equippedItems.add(findItemEntity(nearbyItems, strongestBoots));
        if (strongestWeapon != ItemStack.EMPTY) equippedItems.add(findItemEntity(nearbyItems, strongestWeapon));

        equippedItems.forEach(Entity::discard);
    }
    private ItemEntity findItemEntity(List<ItemEntity> itemEntities, ItemStack itemStack) {
        return itemEntities.stream()
                .filter(itemEntity -> {
                    ItemStack stack = itemEntity.getStack();
                    return stack.getItem() == itemStack.getItem() &&
                            ItemStack.areItemsEqual(stack, itemStack);
                })
                .findFirst()
                .orElse(null);
    }


    private ItemStack getStrongerItem(ItemStack currentStrongest, ItemStack contender) {
        return (isStronger(contender, currentStrongest)) ? contender : currentStrongest;
    }

    private boolean isWeapon(ItemStack itemStack) {
        return itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof AxeItem;
    }

    private boolean isStronger(ItemStack contender, ItemStack currentStrongest) {
        if (contender.isEmpty()) {
            return false;
        }
        if (currentStrongest.isEmpty()) {
            return true;
        }

        if (isWeapon(contender)) {
            return getAttackDamage(contender) > getAttackDamage(currentStrongest);
        }

        if (contender.getItem() instanceof ArmorItem armorItem1 && currentStrongest.getItem() instanceof ArmorItem armorItem2) {

            int armorComparison = Integer.compare(armorItem1.getProtection(), armorItem2.getProtection());
            if (armorComparison != 0) {
                return armorComparison > 0;
            }

            return (long) contender.getEnchantments().size() > (long) currentStrongest.getEnchantments().size();
        }

        return false;
    }

    private float getAttackDamage(ItemStack itemStack) {
        if(itemStack.getItem() instanceof SwordItem swordItem) {
            return swordItem.getAttackDamage();
        } else if(itemStack.getItem() instanceof AxeItem axeItem) {
            return axeItem.getAttackDamage();
        }
        return 0;
    }

}
