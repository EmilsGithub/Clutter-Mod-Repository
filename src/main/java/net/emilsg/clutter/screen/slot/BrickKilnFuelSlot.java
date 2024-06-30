package net.emilsg.clutter.screen.slot;

import net.emilsg.clutter.screen.BrickKilnScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;

public class BrickKilnFuelSlot extends Slot {
    private final BrickKilnScreenHandler handler;

    public BrickKilnFuelSlot(BrickKilnScreenHandler handler, Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.handler = handler;
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.isOf(Items.BUCKET);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return this.handler.isFuel(stack) || BrickKilnFuelSlot.isBucket(stack);
    }

    @Override
    public int getMaxItemCount(ItemStack stack) {
        return BrickKilnFuelSlot.isBucket(stack) ? 1 : super.getMaxItemCount(stack);
    }
}
