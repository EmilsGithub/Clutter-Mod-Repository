package net.emilsg.clutter.item.custom;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;

public class ClutterElytraItem extends ElytraItem implements FabricElytraItem, Equipment {

    public ClutterElytraItem(Settings settings) {
        super(settings);
    }

    public boolean isBroken(ItemStack stack) {
        return !isUsable(stack);
    }

}
