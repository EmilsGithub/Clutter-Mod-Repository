package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class LargeChainBlock extends ChainBlock {
    protected static final VoxelShape Y_SHAPE = Block.createCuboidShape(5, 0.0, 5, 11, 16.0, 11);
    protected static final VoxelShape Z_SHAPE = Block.createCuboidShape(5, 5, 0.0, 11, 11, 16.0);
    protected static final VoxelShape X_SHAPE = Block.createCuboidShape(0.0, 5, 5, 16.0, 11, 11);

    public LargeChainBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(WATERLOGGED, false)).with(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS)) {
            default -> X_SHAPE;
            case Z -> Z_SHAPE;
            case Y -> Y_SHAPE;
        };
    }
}
