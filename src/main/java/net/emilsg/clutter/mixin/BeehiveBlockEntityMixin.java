package net.emilsg.clutter.mixin;

import net.emilsg.clutter.block.custom.ChimneyBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin extends BlockEntity {

    public BeehiveBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "isSmoked", at = @At("RETURN"), cancellable = true)
    private void isSmokedByChimney(CallbackInfoReturnable<Boolean> cir) {
        if (ChimneyBlock.isChimneyInRange(this.world, this.getPos())) {
            cir.setReturnValue(true);
        }
    }

}
