package net.emilsg.clutter.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.Slot;

public class WallBookshelfBoxSlot extends Slot {

    public WallBookshelfBoxSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean canInsert(ItemStack stack) {
        return stack.isIn(ItemTags.BOOKSHELF_BOOKS) || stack.isIn(ItemTags.LECTERN_BOOKS);
    }
}
