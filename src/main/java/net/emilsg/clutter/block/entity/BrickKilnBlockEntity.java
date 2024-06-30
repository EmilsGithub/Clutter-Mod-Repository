package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.recipe.KilningRecipe;
import net.emilsg.clutter.screen.BrickKilnScreenHandler;
import net.emilsg.clutter.screen.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BrickKilnBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FUEL_ITEM_SLOT = 2;
    private static final int[] TOP_SLOTS = new int[]{INPUT_SLOT};
    private static final int[] BOTTOM_SLOTS = new int[]{OUTPUT_SLOT, FUEL_ITEM_SLOT};
    private static final int[] SIDE_SLOTS = new int[]{FUEL_ITEM_SLOT};
    protected final PropertyDelegate propertyDelegate;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 100;
    private int burnTime;
    private int fuelTime;

    public BrickKilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BRICK_KILN_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BrickKilnBlockEntity.this.progress;
                    case 1 -> BrickKilnBlockEntity.this.maxProgress;
                    case 2 -> BrickKilnBlockEntity.this.burnTime;
                    case 3 -> BrickKilnBlockEntity.this.fuelTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BrickKilnBlockEntity.this.progress = value;
                    case 1 -> BrickKilnBlockEntity.this.maxProgress = value;
                    case 2 -> BrickKilnBlockEntity.this.burnTime = value;
                    case 3 -> BrickKilnBlockEntity.this.fuelTime = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().containsKey(stack.getItem());
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.clutter.brick_kiln");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BrickKilnScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("brick_kiln.burntime", burnTime);
        nbt.putInt("brick_kiln.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        burnTime = nbt.getInt("brick_kiln.burntime");
        progress = nbt.getInt("brick_kiln.progress");
        fuelTime = getFuelTime(this.inventory.get(FUEL_ITEM_SLOT));
        super.readNbt(nbt);
    }

    public void tick(World world, BlockPos pos, BlockState state, BrickKilnBlockEntity blockEntity) {
        boolean wasBurning = blockEntity.isBurning();
        boolean willContinueBurning = false;

        if (blockEntity.isBurning()) {
            --blockEntity.burnTime;
        }

        if (!blockEntity.isBurning() && blockEntity.hasRecipe()) {
            if (canUseAsFuel(blockEntity.getStack(FUEL_ITEM_SLOT))) {
                ItemStack fuelStack = blockEntity.inventory.get(FUEL_ITEM_SLOT);
                blockEntity.fuelTime = blockEntity.getFuelTime(fuelStack) / 2;
                blockEntity.burnTime = blockEntity.fuelTime;
                if (fuelStack.getRecipeRemainder().isOf(Items.BUCKET))
                    blockEntity.setStack(FUEL_ITEM_SLOT, new ItemStack(Items.BUCKET));
                else if (!fuelStack.getRecipeRemainder().isOf(Items.BUCKET)) fuelStack.decrement(1);
                willContinueBurning = true;
            }
        }

        if (blockEntity.canInsertIntoOutputSlot() && blockEntity.hasRecipe()) {
            if (blockEntity.isBurning() || willContinueBurning) {
                blockEntity.increaseCraftingProgress();
                markDirty(world, pos, state);

                if (blockEntity.hasCraftingFinished()) {
                    blockEntity.craftItem();
                    blockEntity.resetProgress();
                }
                willContinueBurning = true;
            } else {
                blockEntity.resetProgress();
            }
        } else {
            blockEntity.resetProgress();
        }

        if (!world.isClient) {
            world.setBlockState(pos, state.with(Properties.LIT, blockEntity.burnTime > 0), Block.NOTIFY_ALL);
        }

    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    private void craftItem() {
        Optional<KilningRecipe> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT, 1);

        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().getOutput(null).getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipe.get().getOutput(null).getCount()));
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        if (this.isBurning()) this.progress++;
    }

    private boolean hasRecipe() {
        Optional<KilningRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack output = recipe.get().getOutput(null);

        return canInsertAmountIntoOutputSlot(output.getCount())
                && canInsertItemIntoOutputSlot(output);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.getStack(OUTPUT_SLOT).getMaxCount() >= this.getStack(OUTPUT_SLOT).getCount() + count;
    }

    private Optional<KilningRecipe> getCurrentRecipe() {
        SimpleInventory inventory = new SimpleInventory((this.size()));
        for (int i = 0; i < this.size(); i++) {
            inventory.setStack(i, this.getStack(i));
        }

        return this.getWorld().getRecipeManager().getFirstMatch(KilningRecipe.Type.INSTANCE, inventory, this.getWorld());
    }

    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == OUTPUT_SLOT) {
            return false;
        }
        if (slot == FUEL_ITEM_SLOT) {
            ItemStack itemStack = this.inventory.get(1);
            return canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == FUEL_ITEM_SLOT) {
            return stack.isOf(Items.WATER_BUCKET) || stack.isOf(Items.BUCKET);
        }
        return true;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        if (side == Direction.UP) {
            return TOP_SLOTS;
        }
        return SIDE_SLOTS;
    }
}
