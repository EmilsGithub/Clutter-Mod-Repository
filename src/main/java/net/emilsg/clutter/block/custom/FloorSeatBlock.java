package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class FloorSeatBlock extends SeatBlock{

    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 4.0, 15.0)
    );

    public FloorSeatBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
