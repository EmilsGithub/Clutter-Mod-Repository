package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.util.ModScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class CardboardBoxBlockEntity extends LootableContainerBlockEntity implements SidedInventory {
    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 9).toArray();
    private final ViewerCountManager stateManager = new ViewerCountManager() {

        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {

        }

        protected void onContainerClose(World world, BlockPos pos, BlockState state) {

        }

        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == CardboardBoxBlockEntity.this;
            }
            return false;
        }
    };
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    public CardboardBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CARDBOARD_BOX, blockPos, blockState);
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
            if (!this.getCachedState().get(Properties.OPEN)) {
                this.world.setBlockState(this.getPos(), this.getCachedState().with(Properties.OPEN, true));
            }
            CardboardBoxBlockEntity.this.playSound(SoundEvents.BLOCK_WOOL_HIT);
        }
    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
            if (this.getCachedState().get(Properties.OPEN)) {
                this.world.setBlockState(this.getPos(), this.getCachedState().with(Properties.OPEN, false));
            }
            CardboardBoxBlockEntity.this.playSound(SoundEvents.BLOCK_WOOL_FALL);
        }
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return ModScreenHandler.createCardboardBoxScreen(syncId, playerInventory, this);
    }

    public int size() {
        return 9;
    }

    protected Text getContainerName() {
        return Text.translatable("container.clutter.cardboard_box");
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
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 1f);
    }

    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return stack.getItem().canBeNested();
    }

    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return true;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }
}
