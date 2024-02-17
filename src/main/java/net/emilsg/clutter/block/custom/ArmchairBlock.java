package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ArmchairBlock extends SeatBlock{
    private static final VoxelShape BASE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 2, 2, 2),
            Block.createCuboidShape(14, 0, 0, 16, 2, 2),
            Block.createCuboidShape(14, 0, 14, 16, 2, 16),
            Block.createCuboidShape(0, 0, 14, 2, 2, 16),
            Block.createCuboidShape(0, 2, 0, 16, 7, 16)
    );

    protected static final VoxelShape NORTH = VoxelShapes.union(
            BASE,
            Block.createCuboidShape(0, 7, 0, 3, 10, 12),
            Block.createCuboidShape(13, 7, 0, 16, 10, 12),
            Block.createCuboidShape(0, 7, 12, 16, 16, 16)
    );
    protected static final VoxelShape SOUTH = VoxelShapes.union(
            BASE,
            Block.createCuboidShape(13, 7, 4, 16, 10, 16),
            Block.createCuboidShape(0, 7, 4, 3, 10, 16),
            Block.createCuboidShape(0, 7, 0, 16, 16, 4)
    );
    protected static final VoxelShape EAST = VoxelShapes.union(
            BASE,
            Block.createCuboidShape(4, 7, 0, 16, 10, 3),
            Block.createCuboidShape(4, 7, 13, 16, 10, 16),
            Block.createCuboidShape(0, 7, 0, 4, 16, 16)
    );
    protected static final VoxelShape WEST = VoxelShapes.union(
            BASE,
            Block.createCuboidShape(0, 7, 13, 12, 10, 16),
            Block.createCuboidShape(0, 7, 0, 12, 10, 3),
            Block.createCuboidShape(12, 7, 0, 16, 16, 16)
    );

    public ArmchairBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction i = state.get(FACING);
        return switch (i) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> VoxelShapes.empty();
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos;
        World worldAccess = ctx.getWorld();
        boolean bl = worldAccess.getFluidState(blockPos = ctx.getBlockPos()).getFluid() == Fluids.WATER;
        return (BlockState)this.getDefaultState().with(WATERLOGGED, bl).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
}
