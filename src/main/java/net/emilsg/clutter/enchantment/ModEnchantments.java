package net.emilsg.clutter.enchantment;

import net.emilsg.clutter.Clutter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;



public class ModEnchantments {

    public static Enchantment GREED = register("greed",
            new GreedEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static Enchantment NECROSIS = register("necrosis",
            new NecrosisEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Clutter.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {

    }

}
