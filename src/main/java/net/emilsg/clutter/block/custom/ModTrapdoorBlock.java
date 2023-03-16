package net.emilsg.clutter.block.custom;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Direction;

public class ModTrapdoorBlock extends TrapdoorBlock {

    public ModTrapdoorBlock(Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction direction = ctx.getSide();
        blockState = ctx.canReplaceExisting() || !direction.getAxis().isHorizontal() ? (BlockState)((BlockState)blockState.with(FACING, ctx.getPlayerLookDirection())).with(HALF, direction == Direction.UP ? BlockHalf.BOTTOM : BlockHalf.TOP) : (BlockState)((BlockState)blockState.with(FACING, direction)).with(HALF, ctx.getHitPos().y - (double)ctx.getBlockPos().getY() > 0.5 ? BlockHalf.TOP : BlockHalf.BOTTOM);
        if (ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos())) {
            blockState = (BlockState)((BlockState)blockState.with(OPEN, true)).with(POWERED, true);
        }
        return (BlockState)blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

}
