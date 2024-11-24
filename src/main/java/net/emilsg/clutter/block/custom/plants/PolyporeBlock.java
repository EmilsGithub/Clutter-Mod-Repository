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
import net.minecraft.util.shape.VoxelShapes;
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

    protected static final VoxelShape WEST_SHAPE_TWO = VoxelShapes.union(
            Block.createCuboidShape(8, 3, 1, 16, 6, 11),
            Block.createCuboidShape(8, 8, 6, 16, 11, 16)
    );
    protected static final VoxelShape EAST_SHAPE_TWO = VoxelShapes.union(
            Block.createCuboidShape(0, 3, 5, 8, 6, 15),
            Block.createCuboidShape(0, 8, 0, 8, 11, 10)
    );
    protected static final VoxelShape SOUTH_SHAPE_TWO = VoxelShapes.union(
            Block.createCuboidShape(1, 3, 0, 11, 6, 8),
            Block.createCuboidShape(6, 8, 0, 16, 11, 8)
    );
    protected static final VoxelShape NORTH_SHAPE_TWO = VoxelShapes.union(
            Block.createCuboidShape(5, 3, 8, 15, 6, 16),
            Block.createCuboidShape(0, 8, 8, 10, 11, 16)
    );

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
        if (!context.shouldCancelInteraction() && state.getBlock() == this && context.getStack().isOf(this.asItem()) && state.get(POLYPORE_COUNT) < 2) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            int polyPoreCount = blockState.get(POLYPORE_COUNT);
            return blockState.with(POLYPORE_COUNT,  polyPoreCount == 1 ? polyPoreCount + 1 : polyPoreCount);
        }
        if (!ctx.canReplaceExisting() && (blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getSide().getOpposite()))).isOf(this) && blockState.get(FACING) == ctx.getSide()) {
            return null;
        }
        blockState = this.getDefaultState();
        World worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        for (Direction direction : ctx.getPlacementDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = blockState.with(FACING, direction.getOpposite())).canPlaceAt(worldView, blockPos))
                continue;
            return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(POLYPORE_COUNT) == 1) {
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
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
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
