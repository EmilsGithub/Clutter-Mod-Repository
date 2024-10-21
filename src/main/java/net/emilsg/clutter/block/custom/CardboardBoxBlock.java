package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.block.entity.CardboardBoxInventoryBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CardboardBoxBlock extends BlockWithEntity implements Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 11, 14);
    private final VoxelShape OPEN_SHAPE = VoxelShapes.union(Block.createCuboidShape(2, 0, 2.0999999999999996, 2.1, 11, 13.9), Block.createCuboidShape(13.9, 0, 2.0999999999999996, 14, 11, 13.9), Block.createCuboidShape(2, 0, 2, 14, 11, 2.1), Block.createCuboidShape(2, 0, 13.9, 14, 11, 14), Block.createCuboidShape(2, 0, 2, 14, 0.1, 14));
    public static final MapCodec<CardboardBoxBlock> CODEC = createCodec(CardboardBoxBlock::new);

    public CardboardBoxBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH).with(OPEN, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, OPEN);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(OPEN) ? OPEN_SHAPE : SHAPE;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if (player.isSneaking() && player.getStackInHand(player.getActiveHand()).isEmpty()) {
            world.setBlockState(pos, state.cycle(OPEN));
        } else if (!player.isSneaking()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CardboardBoxInventoryBlockEntity) {
                player.openHandledScreen((CardboardBoxInventoryBlockEntity) blockEntity);
            }
        }

        return ActionResult.CONSUME;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {

            world.setBlockState(pos, state.with(WATERLOGGED, true), Block.NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CardboardBoxInventoryBlockEntity(pos, state);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CardboardBoxInventoryBlockEntity) {
            ((CardboardBoxInventoryBlockEntity) blockEntity).tick();
        }
    }

    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        super.appendTooltip(stack, context, tooltip, options);
        if (stack.contains(DataComponentTypes.CONTAINER_LOOT)) {
            tooltip.add(Text.translatable("tooltip.container.clutter.not_empty").formatted(Formatting.YELLOW));
        } else {
            tooltip.add(Text.translatable("tooltip.container.clutter.empty"));
        }
    }

    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CardboardBoxInventoryBlockEntity cardboardBoxInventoryBlock) {
            if (!world.isClient && player.isCreative() && !cardboardBoxInventoryBlock.isEmpty()) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponentsFrom(blockEntity.createComponentMap());
                ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else {
                cardboardBoxInventoryBlock.generateLoot(player);
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CardboardBoxInventoryBlockEntity) {
                world.updateComparators(pos, state.getBlock());
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
