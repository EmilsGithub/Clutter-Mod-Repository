package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class TowelBlock extends HorizontalFacingBlock {

    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 13, 0, 3, 15, 4),
            Block.createCuboidShape(13, 13, 0, 15, 15, 4),
            Block.createCuboidShape(3, 1.5, 1.5, 13, 15.5, 4.5)
    );
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(12, 13, 1, 16, 15, 3),
            Block.createCuboidShape(12, 13, 13, 16, 15, 15),
            Block.createCuboidShape(11.5, 1.5, 3, 14.5, 15.5, 13)
    );
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(13, 13, 12, 15, 15, 16),
            Block.createCuboidShape(1, 13, 12, 3, 15, 16),
            Block.createCuboidShape(3, 1.5, 11.5, 13, 15.5, 14.5)
    );
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 13, 13, 4, 15, 15),
            Block.createCuboidShape(0, 13, 1, 4, 15, 3),
            Block.createCuboidShape(1.5, 1.5, 3, 4.5, 15.5, 13)
    );

    public TowelBlock(Settings settings) {
        super(settings);
    }

    public static final MapCodec<TowelBlock> CODEC = createCodec(TowelBlock::new);

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
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
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction i = state.get(FACING);
        return switch (i) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> VoxelShapes.empty();
        };
    }
}
