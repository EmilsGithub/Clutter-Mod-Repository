package net.emilsg.clutter.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GemstoneElytraItem extends ClutterElytraItem {
    Item repairItem;
    String type;

    public GemstoneElytraItem(Settings settings, Item wingType, String type) {
        super(settings);
        this.repairItem = wingType;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(repairItem);
    }
}
