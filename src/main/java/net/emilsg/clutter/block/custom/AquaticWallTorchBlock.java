package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class AquaticWallTorchBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape WALL_NORTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(6.5, 1, 12, 9.5, 8, 16), Block.createCuboidShape(4.5, 8, 8, 11.5, 16, 15));
    private static final VoxelShape WALL_EAST_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 1, 6.5, 4, 8, 9.5), Block.createCuboidShape(1, 8, 4.5, 8, 16, 11.5));
    private static final VoxelShape WALL_SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape(12, 1, 6.5, 16, 8, 9.5), Block.createCuboidShape(8, 8, 4.5, 15, 16, 11.5));
    private static final VoxelShape WALL_WEST_SHAPE = VoxelShapes.union(Block.createCuboidShape(6.5, 1, 0, 9.5, 8, 4), Block.createCuboidShape(4.5, 8, 1, 11.5, 16, 8));

    public AquaticWallTorchBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false).with(FACING, Direction.NORTH));

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState();
        World worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = worldView.getFluidState(blockPos);

        return findWallState(state, worldView, blockPos, ctx, fluidState);
    }

    private BlockState findWallState(BlockState state, World world, BlockPos pos, ItemPlacementContext ctx, FluidState fluidState) {
        for (Direction wallDirection : ctx.getPlacementDirections()) {
            if (wallDirection.getAxis().isHorizontal()) {
                BlockState newState = state.with(FACING, wallDirection.getOpposite());
                if (newState.canPlaceAt(world, pos)) {
                    return newState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }
        return state;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return getWallShape(direction);
    }

    private VoxelShape getWallShape(Direction direction) {
        return switch (direction) {
            case NORTH -> WALL_NORTH_SHAPE;
            case EAST -> WALL_EAST_SHAPE;
            case WEST -> WALL_SOUTH_SHAPE;
            default -> WALL_WEST_SHAPE;
        };
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {

            world.setBlockState(pos, (state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
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

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(2) == 0 && state.get(WATERLOGGED) && world.getFluidState(pos.up()).isOf(Fluids.WATER)) {
            double x = pos.getX() + 0.5, y = pos.getY() + 1.05, z = pos.getZ() + 0.5;
            y += 0.1;
            Direction facing = state.get(FACING);
            double offset = 0.25;

            switch (facing) {
                case NORTH:
                    z += offset;
                    break;
                case EAST:
                    x -= offset;
                    break;
                case SOUTH:
                    z -= offset;
                    break;
                case WEST:
                    x += offset;
                    break;
            }

            world.addParticle(random.nextBoolean() ? ParticleTypes.BUBBLE : ParticleTypes.BUBBLE_POP, x, y, z, 0.0, 0.0, 0.0);
        }
    }

}
