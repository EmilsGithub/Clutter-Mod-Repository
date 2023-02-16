package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class FoodBoxBlock extends HorizontalRotationBlock implements Waterloggable {
    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 7.0, 15.0),
            Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 7.0, 16.0),
            Block.createCuboidShape(15.0, 7.0, 0.0, 16.0, 8.0, 16.0),
            Block.createCuboidShape(0.0, 7.0, 15.0, 16.0, 8.0, 16.0),
            Block.createCuboidShape(0.0, 7.0, 0.0, 1.0, 8.0, 16.0),
            Block.createCuboidShape(0.0, 7.0, 0.0, 16.0, 8.0, 1.0));

    public FoodBoxBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }


    }
