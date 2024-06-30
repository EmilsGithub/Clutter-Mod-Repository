package net.emilsg.clutter.block.custom.plants;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CattailPlantBlock extends TallFlowerBlock implements Fertilizable, Waterloggable {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 16, 13);

    public CattailPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        return SHAPE.offset(vec3d.x, 0, vec3d.z);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        DoubleBlockHalf type = state.get(HALF);
        BlockPos otherHalfPos = type == DoubleBlockHalf.LOWER ? pos.up() : pos.down();
        BlockState otherHalfState = world.getBlockState(otherHalfPos);

        if (otherHalfState.isOf(this) && otherHalfState.get(HALF) != type) {
            return state;
        }

        return !state.canPlaceAt(world, pos) || !otherHalfState.isOf(this) ? Blocks.AIR.getDefaultState() : state;
    }


    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockpos = pos.down();
        BlockState downState = world.getBlockState(blockpos);
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return downState.isOf(this) && downState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        return this.canPlantOnTop(downState, world, blockpos);
    }

    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        if (world.getFluidState(pos.up()).isOf(Fluids.WATER) && !world.getFluidState(pos.up(2)).isOf(Fluids.WATER)) {
            return super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.CLAY) || floor.isOf(Blocks.SAND);
        }
        return false;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        boolean blockIsWater = world.getFluidState(blockPos).getFluid() == Fluids.WATER;
        if (blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).isReplaceable()) {
            return this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, blockIsWater);
        }
        return this.getDefaultState().with(WATERLOGGED, blockIsWater);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, HALF);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }
}
