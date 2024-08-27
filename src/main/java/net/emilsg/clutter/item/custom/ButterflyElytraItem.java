package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ButterflyElytraItem extends ClutterElytraItem {
    String color;

    public ButterflyElytraItem(Settings settings, Item component, String color) {
        super(settings, component);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(ModItems.BUTTERFLY_IN_A_BOTTLE);
    }
}
