package net.emilsg.clutter.item.custom;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.item.*;

public class ButterflyElytraItem extends ElytraItem implements FabricElytraItem, Equipment {
    Item repairItem;

    public ButterflyElytraItem(Settings settings, Item wingType) {
        super(settings);
        this.repairItem = wingType;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(repairItem);
    }
}
