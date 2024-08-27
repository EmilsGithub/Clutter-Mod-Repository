package net.emilsg.clutter.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GemstoneElytraItem extends ClutterElytraItem {
    Item repairItem;
    String stringType;

    public GemstoneElytraItem(Settings settings, Item component, String stringType) {
        super(settings, component);
        this.repairItem = component;
        this.stringType = stringType;
    }

    public String getTypeString() {
        return stringType;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(repairItem);
    }
}
