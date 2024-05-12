package net.emilsg.clutter.mixin;

import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.MossBlock;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MossBlock.class)
public class MossBlockMixin {
    @Inject(at = @At("HEAD"), method = "grow", cancellable = true)
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
        world.getRegistryManager().getOptional(RegistryKeys.CONFIGURED_FEATURE).flatMap((registry) -> {
            return registry.getEntry(UndergroundConfiguredFeatures.MOSS_PATCH_BONEMEAL);
        }).ifPresent((reference) -> {
            ((ConfiguredFeature<?, ?>)reference.value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos.up());

            for (int i = 0; i < 20; ++i) {
                BlockPos blockPos = pos.up().add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
                if (world.getBlockState(blockPos).isAir() && ModBlocks.LUSH_MOSS.getDefaultState().canPlaceAt(world, blockPos)) {
                    world.setBlockState(blockPos, ModBlocks.LUSH_MOSS.getDefaultState());
                }
            }
            ci.cancel();
        });
    }
}
