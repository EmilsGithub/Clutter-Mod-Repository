package net.emilsg.clutter.block.custom.plants;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class PolyporeBlock extends LadderBlock implements Fertilizable {
    public static final IntProperty POLYPORE_COUNT = IntProperty.of("count", 1, 2);

    protected static final VoxelShape EAST_SHAPE_ONE = Block.createCuboidShape(0.0, 7.0, 3.0, 8.0, 10.0, 13.0);
    protected static final VoxelShape WEST_SHAPE_ONE = Block.createCuboidShape(8.0, 7.0, 3.0, 16.0, 10.0, 13.0);
    protected static final VoxelShape SOUTH_SHAPE_ONE = Block.createCuboidShape(3.0, 7.0, 0.0, 13.0, 10.0, 8.0);
    protected static final VoxelShape NORTH_SHAPE_ONE = Block.createCuboidShape(3.0, 7.0, 8.0, 13.0, 10.0, 16.0);

    protected static final VoxelShape EAST_SHAPE_TWO = Block.createCuboidShape(0.0, 7.0, 3.0, 8.0, 10.0, 13.0);
    protected static final VoxelShape WEST_SHAPE_TWO = Block.createCuboidShape(8.0, 7.0, 3.0, 16.0, 10.0, 13.0);
    protected static final VoxelShape SOUTH_SHAPE_TWO = Block.createCuboidShape(3.0, 7.0, 0.0, 13.0, 10.0, 8.0);
    protected static final VoxelShape NORTH_SHAPE_TWO = Block.createCuboidShape(3.0, 7.0, 8.0, 13.0, 10.0, 16.0);

    public PolyporeBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POLYPORE_COUNT);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(POLYPORE_COUNT) == 1) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(POLYPORE_COUNT, blockState.get(POLYPORE_COUNT) + 1);
        }
        if (!ctx.canReplaceExisting() && (blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getSide().getOpposite()))).isOf(this) && blockState.get(FACING) == ctx.getSide()) {
            return null;
        }
        blockState = this.getDefaultState();
        World worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        for (Direction direction : ctx.getPlacementDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = (BlockState)blockState.with(FACING, direction.getOpposite())).canPlaceAt(worldView, blockPos)) continue;
            return (BlockState)blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(POLYPORE_COUNT) == 1) {
            return switch (state.get(FACING)) {
                case NORTH -> NORTH_SHAPE_ONE;
                case SOUTH -> SOUTH_SHAPE_ONE;
                case WEST -> WEST_SHAPE_ONE;
                default -> EAST_SHAPE_ONE;
            };
        }

        return switch (state.get(FACING)) {
            case NORTH -> NORTH_SHAPE_TWO;
            case SOUTH -> SOUTH_SHAPE_TWO;
            case WEST -> WEST_SHAPE_TWO;
            default -> EAST_SHAPE_TWO;
        };

    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = state.get(POLYPORE_COUNT);
        if (i < 2) {
            world.setBlockState(pos, state.with(POLYPORE_COUNT, i + 1), Block.NOTIFY_LISTENERS);
        } else {
            dropStack(world, pos, new ItemStack(this));
        }
    }

}
