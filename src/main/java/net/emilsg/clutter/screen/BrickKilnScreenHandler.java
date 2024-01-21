package net.emilsg.clutter.screen;

import net.emilsg.clutter.block.entity.BrickKilnBlockEntity;
import net.emilsg.clutter.screen.slot.BrickKilnFuelSlot;
import net.emilsg.clutter.screen.slot.BrickKilnOutputSlot;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BrickKilnScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final BrickKilnBlockEntity blockEntity;

    public BrickKilnScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()), new ArrayPropertyDelegate(4));
    }

    public BrickKilnScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.BRICK_KILN_SCREEN_HANDLER, syncId);
        checkSize(((Inventory) blockEntity), 3);
        this.inventory = (Inventory)blockEntity;
        this.propertyDelegate = propertyDelegate;
        this.blockEntity = ((BrickKilnBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory, 0, 56, 17));
        this.addSlot(new BrickKilnOutputSlot(playerInventory.player, inventory, 1, 116, 35));
        this.addSlot(new BrickKilnFuelSlot(this, inventory, 2, 56, 53));


        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(propertyDelegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot == 2) {
                if (!this.insertItem(originalStack, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (isFuel(originalStack)) {
                if (!this.insertItem(originalStack, 2, 3, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(itemStack);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public int getFuelProgress() {
        int i = this.propertyDelegate.get(3);
        if (i == 0) {
            i = 200;
        }
        return this.propertyDelegate.get(2) * 13 / i;
    }

    public boolean isBurning() {
        return this.propertyDelegate.get(2) > 0;
    }

    public int getKilnProgress() {
        int i = this.propertyDelegate.get(0);
        int j = this.propertyDelegate.get(1);
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 24 / j;
    }
}
