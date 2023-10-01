package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WineGlassBlock extends DrinkingGlassBlock {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty GLASSES = ModProperties.GLASSES;

    protected static final VoxelShape SINGLE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.createCuboidShape(7.5, 1, 7.5, 8.5, 4, 8.5),
            Block.createCuboidShape(6.5, 4, 6.5, 9.5, 8, 9.5)
    );

    protected static final VoxelShape TWO_EW_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.5, 0, 4, 9.5, 1, 7),
            Block.createCuboidShape(7.5, 1, 5, 8.5, 4, 6),
            Block.createCuboidShape(6.5, 4, 4, 9.5, 8, 7),
            Block.createCuboidShape(6.5, 0, 9, 9.5, 1, 12),
            Block.createCuboidShape(7.5, 1, 10, 8.5, 4, 11),
            Block.createCuboidShape(6.5, 4, 9, 9.5, 8, 12)
    );

    protected static final VoxelShape TWO_NS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(9, 0, 6.5, 12, 1, 9.5),
            Block.createCuboidShape(10, 1, 7.5, 11, 4, 8.5),
            Block.createCuboidShape(9, 4, 6.5, 12, 8, 9.5),
            Block.createCuboidShape(4, 0, 6.5, 7, 1, 9.5),
            Block.createCuboidShape(5, 1, 7.5, 6, 4, 8.5),
            Block.createCuboidShape(4, 4, 6.5, 7, 8, 9.5)
    );

    protected static final VoxelShape THREE_NS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.5, 0, 6.5, 4.5, 1, 9.5),
            Block.createCuboidShape(2.5, 1, 7.5, 3.5, 4, 8.5),
            Block.createCuboidShape(1.5, 4, 6.5, 4.5, 8, 9.5),
            Block.createCuboidShape(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.createCuboidShape(7.5, 1, 7.5, 8.5, 4, 8.5),
            Block.createCuboidShape(6.5, 4, 6.5, 9.5, 8, 9.5),
            Block.createCuboidShape(11.5, 0, 6.5, 14.5, 1, 9.5),
            Block.createCuboidShape(12.5, 1, 7.5, 13.5, 4, 8.5),
            Block.createCuboidShape(11.5, 4, 6.5, 14.5, 8, 9.5)
    );

    protected static final VoxelShape THREE_EW_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6.5, 0, 11.5, 9.5, 1, 14.5),
            Block.createCuboidShape(7.5, 1, 12.5, 8.5, 4, 13.5),
            Block.createCuboidShape(6.5, 4, 11.5, 9.5, 8, 14.5),
            Block.createCuboidShape(6.5, 0, 6.5, 9.5, 1, 9.5),
            Block.createCuboidShape(7.5, 1, 7.5, 8.5, 4, 8.5),
            Block.createCuboidShape(6.5, 4, 6.5, 9.5, 8, 9.5),
            Block.createCuboidShape(6.5, 0, 1.5, 9.5, 1, 4.5),
            Block.createCuboidShape(7.5, 1, 2.5, 8.5, 4, 3.5),
            Block.createCuboidShape(6.5, 4, 1.5, 9.5, 8, 4.5)
    );



    public WineGlassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(GLASSES)) {
            default -> SINGLE_SHAPE;
            case 1 -> state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? TWO_NS_SHAPE : TWO_EW_SHAPE;
            case 2 -> state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? THREE_NS_SHAPE : THREE_EW_SHAPE;
        };
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && state.get(GLASSES) < 2 || super.canReplace(state, context);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return (BlockState)blockState.cycle(GLASSES);
        } else {
            FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
            boolean bl = fluidState.getFluid() == Fluids.WATER;
            return (BlockState)super.getPlacementState(ctx).with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!(Boolean)state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            BlockState blockState = (BlockState)state.with(WATERLOGGED, true);
            world.setBlockState(pos, blockState, 3);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        } else {
            return false;
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GLASSES, WATERLOGGED, FACING);
    }
}
