package net.emilsg.clutter.util;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandler extends GenericContainerScreenHandler {

    public ModScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory, int rows) {
        super(type, syncId, playerInventory, inventory, rows);
    }

    public static GenericContainerScreenHandler createGeneric9x1(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, inventory, 1);
    }

    public static GenericContainerScreenHandler createGeneric9x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, inventory, 3);
    }

    public static GenericContainerScreenHandler createGeneric3x3(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_3X3, syncId, playerInventory, inventory, 1);
    }

    public static HopperScreenHandler createGeneric5x1(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new HopperScreenHandler(syncId, playerInventory, inventory);
    }
}
