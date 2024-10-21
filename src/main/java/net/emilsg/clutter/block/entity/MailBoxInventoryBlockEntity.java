package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.block.custom.MailBoxBlock;
import net.emilsg.clutter.util.ModScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MailBoxInventoryBlockEntity extends LootableContainerBlockEntity {
    private final ViewerCountManager stateManager = new ViewerCountManager() {

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            MailBoxInventoryBlockEntity.this.playSound(SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN);

            if (!isEmpty()) {
                world.setBlockState(pos, state.with(MailBoxBlock.HATCH_OPEN, true), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, state.with(MailBoxBlock.HATCH_OPEN, true), Block.NOTIFY_ALL);
            }
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            MailBoxInventoryBlockEntity.this.playSound(SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE);

            if (!isEmpty()) {
                world.setBlockState(pos, state.with(MailBoxBlock.HATCH_OPEN, false), Block.NOTIFY_ALL);
            } else {
                world.setBlockState(pos, state.with(MailBoxBlock.HATCH_OPEN, false), Block.NOTIFY_ALL);
            }
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == MailBoxInventoryBlockEntity.this;
            }
            return false;
        }
    };
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);


    public MailBoxInventoryBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.MAILBOX, blockPos, blockState);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.clutter.mailbox");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return ModScreenHandler.createGeneric3x3(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return 9;
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.readInventoryNbt(nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, false, registryLookup);
        }
    }

    public void readInventoryNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt) && nbt.contains("Items", NbtElement.LIST_TYPE)) {
            Inventories.readNbt(nbt, this.inventory, registries);
        }
    }

    void playSound(SoundEvent soundEvent) {
        double d = (double) this.pos.getX() + 0.5;
        double e = (double) this.pos.getY() + 0.5;
        double f = (double) this.pos.getZ() + 0.5;
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.7f);
    }
}
