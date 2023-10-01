package net.emilsg.clutter.screen;

import net.emilsg.clutter.block.custom.CardboardBoxBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class CardboardBoxSlot extends Slot {

    public CardboardBoxSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean canInsert(ItemStack stack) {
        return !(Block.getBlockFromItem(stack.getItem()) instanceof CardboardBoxBlock) && !(Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock);
    }
}
