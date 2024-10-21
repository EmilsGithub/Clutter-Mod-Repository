package net.emilsg.clutter.item.custom;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;

public class CopperDivingArmorItem extends ArmorItem {

    public CopperDivingArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.COPPER_INGOT);
    }

}
