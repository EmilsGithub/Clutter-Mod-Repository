package net.emilsg.clutter.enchantment;

import net.emilsg.clutter.config.ClutterConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class GreedEnchantment extends Enchantment {

    public GreedEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentTarget.WEAPON, slots);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return EnchantmentTarget.WEAPON.isAcceptableItem(stack.getItem()) || EnchantmentTarget.DIGGER.isAcceptableItem(stack.getItem());
    }

    public boolean isTreasure() {
        return true;
    }

    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public boolean canAccept(Enchantment other) {
        return !ClutterConfig.getInstance().getBoolean("greed_with_looting_and_mending") ? (super.canAccept(other) && other != Enchantments.LOOTING && other != Enchantments.FORTUNE && other != Enchantments.MENDING) : super.canAccept(other);
    }
}
