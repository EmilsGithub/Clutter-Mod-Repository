package net.emilsg.clutter.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;

public class ModEnchantmentTarget {
    public static boolean isAcceptableItem(ItemStack itemStack) {
        return EnchantmentTarget.WEAPON.isAcceptableItem(itemStack.getItem()) || EnchantmentTarget.DIGGER.isAcceptableItem(itemStack.getItem());
    }
}
