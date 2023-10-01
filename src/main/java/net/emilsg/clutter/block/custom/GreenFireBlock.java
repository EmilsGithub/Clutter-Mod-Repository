package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class GreenFireBlock extends AbstractFireBlock {
    public GreenFireBlock(AbstractBlock.Settings settings) {
        super(settings, 1.0F);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return this.canPlaceAt(state, world, pos) ? this.getDefaultState() : Blocks.AIR.getDefaultState();
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return isFireBase(world.getBlockState(pos.down()));
    }

    public static boolean isFireBase(BlockState state) {
        return state.isIn(ModBlockTags.GREEN_FIRE_BASE_BLOCKS);
    }

    protected boolean isFlammable(BlockState state) {
        return true;
    }


}
