package net.emilsg.clutter.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UseTimeFoodItem extends Item {
    private final int useTimeInTicks;

    public UseTimeFoodItem(Settings settings, int useTimeInTicks) {
        super(settings);
        this.useTimeInTicks = useTimeInTicks;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return useTimeInTicks;
    }
}
