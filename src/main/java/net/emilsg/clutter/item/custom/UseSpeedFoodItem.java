package net.emilsg.clutter.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UseSpeedFoodItem extends Item {
    private final int useTimeInTicks;

    public UseSpeedFoodItem(Settings settings, int useTimeInTicks) {
        super(settings);
        this.useTimeInTicks = useTimeInTicks;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return useTimeInTicks;
    }
}
