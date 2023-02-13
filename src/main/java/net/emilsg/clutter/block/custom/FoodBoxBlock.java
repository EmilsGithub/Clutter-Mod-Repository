package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class FoodBoxBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
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

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
