package net.emilsg.clutter.mixin;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.GreenFireBlock;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public abstract class AbstractFireBlockMixin {

    @Inject(method = "getState", at = @At("HEAD"), cancellable = true)
    private static void getState(BlockView world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);

        if (SoulFireBlock.isSoulBase(blockState)) {
            cir.setReturnValue(Blocks.SOUL_FIRE.getDefaultState());
        } else if (GreenFireBlock.isFireBase(blockState) && blockState.isSideSolid(world, pos, Direction.UP, SideShapeType.FULL)) {
            cir.setReturnValue(ModBlocks.GREEN_FIRE.getDefaultState());
        } else {
            cir.setReturnValue(((FireBlockInvoker) Blocks.FIRE).invokeGetStateForPosition(world, pos));
        }

        cir.cancel();
    }
}
