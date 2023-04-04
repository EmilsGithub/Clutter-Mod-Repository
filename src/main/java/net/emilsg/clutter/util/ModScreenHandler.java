package net.emilsg.clutter.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

public class ModScreenHandler extends GenericContainerScreenHandler {

    public ModScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
        super(type, syncId, playerInventory, inventory, rows);
    }

    public static GenericContainerScreenHandler createGeneric9x1(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, inventory, 1);
    }

    public static GenericContainerScreenHandler createGeneric9x2(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X2, syncId, playerInventory, inventory, 2);
    }

    public static GenericContainerScreenHandler createGeneric3x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_3X3, syncId, playerInventory, inventory, 1);
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        if (slot.inventory == this.getInventory()) {
            return stack.isIn(ModItemTags.BOOKSHELF_CAN_INSERT);
        } else {
            return false;
        }
    }




}
