package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class MugBlock extends HorizontalFacingBlock {
    protected static final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0));
    public static final MapCodec<MugBlock> CODEC = createCodec(MugBlock::new);


    public MugBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.WOODEN_MUG);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = Direction.DOWN;
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockState(pos.down()).isAir()) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

}
