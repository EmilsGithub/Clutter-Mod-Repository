package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
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

public class TallBottleBlock extends HorizontalFacingBlock {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final IntProperty BOTTLES = IntProperty.of("bottles", 0, 2);

    protected static final VoxelShape SINGLE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6, 0, 6, 10, 9, 10),
            Block.createCuboidShape(7, 9, 7, 9, 13, 9),
            Block.createCuboidShape(6.5, 10, 6.5, 9.5, 12, 9.5)
    );

    protected static final VoxelShape TWO_EW_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6, 0, 9, 10, 9, 13),
            Block.createCuboidShape(7, 9, 10, 9, 13, 12),
            Block.createCuboidShape(6.5, 10, 9.5, 9.5, 12, 12.5),
            Block.createCuboidShape(6.5, 10, 3.5, 9.5, 12, 6.5),
            Block.createCuboidShape(7, 9, 4, 9, 13, 6),
            Block.createCuboidShape(6, 0, 3, 10, 9, 7)
    );

    protected static final VoxelShape TWO_NS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 0, 6, 7, 9, 10),
            Block.createCuboidShape(4, 9, 7, 6, 13, 9),
            Block.createCuboidShape(3.5, 10, 6.5, 6.5, 12, 9.5),
            Block.createCuboidShape(9.5, 10, 6.5, 12.5, 12, 9.5),
            Block.createCuboidShape(10, 9, 7, 12, 13, 9),
            Block.createCuboidShape(9, 0, 6, 13, 9, 10)
    );

    protected static final VoxelShape THREE_NS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 0, 6, 5, 9, 10),
            Block.createCuboidShape(2, 9, 7, 4, 13, 9),
            Block.createCuboidShape(1.5, 10, 6.5, 4.5, 12, 9.5),
            Block.createCuboidShape(6, 0, 6, 10, 9, 10),
            Block.createCuboidShape(7, 9, 7, 9, 13, 9),
            Block.createCuboidShape(6.5, 10, 6.5, 9.5, 12, 9.5),
            Block.createCuboidShape(11.5, 10, 6.5, 14.5, 12, 9.5),
            Block.createCuboidShape(12, 9, 7, 14, 13, 9),
            Block.createCuboidShape(11, 0, 6, 15, 9, 10)
    );

    protected static final VoxelShape THREE_EW_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6, 0, 11, 10, 9, 15),
            Block.createCuboidShape(7, 9, 12, 9, 13, 14),
            Block.createCuboidShape(6.5, 10, 11.5, 9.5, 12, 14.5),
            Block.createCuboidShape(6, 0, 6, 10, 9, 10),
            Block.createCuboidShape(7, 9, 7, 9, 13, 9),
            Block.createCuboidShape(6.5, 10, 6.5, 9.5, 12, 9.5),
            Block.createCuboidShape(6.5, 10, 1.5, 9.5, 12, 4.5),
            Block.createCuboidShape(7, 9, 2, 9, 13, 4),
            Block.createCuboidShape(6, 0, 1, 10, 9, 5)
    );



    public TallBottleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(BOTTLES)) {
            default -> SINGLE_SHAPE;
            case 1 -> state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? TWO_NS_SHAPE : TWO_EW_SHAPE;
            case 2 -> state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH ? THREE_NS_SHAPE : THREE_EW_SHAPE;
        };
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.shouldCancelInteraction() && context.getStack().getItem() == this.asItem() && state.get(BOTTLES) < 2 || super.canReplace(state, context);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return (BlockState)blockState.cycle(BOTTLES);
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
        builder.add(BOTTLES, WATERLOGGED, FACING);
    }
}
