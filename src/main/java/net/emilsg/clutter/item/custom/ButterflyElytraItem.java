package net.emilsg.clutter.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ButterflyElytraItem extends ClutterElytraItem {
    Item repairItem;
    String color;

    public ButterflyElytraItem(Settings settings, Item wingType, String color) {
        super(settings);
        this.repairItem = wingType;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(repairItem);
    }
}
