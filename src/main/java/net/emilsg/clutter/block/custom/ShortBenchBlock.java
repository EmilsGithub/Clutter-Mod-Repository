package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class ShortBenchBlock extends SeatBlock {

    public final VoxelShape NS_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 4, 16, 6, 12),
            Block.createCuboidShape(2, 0, 3, 5, 2, 13),
            Block.createCuboidShape(11, 0, 3, 14, 2, 13)
    );

    public final VoxelShape EW_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4, 2, 0, 12, 6, 16),
            Block.createCuboidShape(3, 0, 2, 13, 2, 5),
            Block.createCuboidShape(3, 0, 11, 13, 2, 14)
    );

    public ShortBenchBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false).with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            default -> NS_SHAPE;
            case EAST, WEST -> EW_SHAPE;
        };
    }

    @Override
    protected float getYOffset() {
        return 0.15f;
    }

    @Override
    protected boolean isStrippable() {
        return false;
    }
}
