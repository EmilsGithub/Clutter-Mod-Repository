package net.emilsg.clutter.mixin;

import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.TallPlantBlock.HALF;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
    @Inject(method = "grow", at = @At("HEAD"))
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (random.nextInt(1) == 0) {
            BlockPos blockPos = pos.up();
            RegistryEntry<Biome> registryEntry = world.getBiome(blockPos);

            if (registryEntry.matchesKey(BiomeKeys.MANGROVE_SWAMP) || registryEntry.matchesKey(BiomeKeys.SWAMP)) {
                BlockPos plantPos = blockPos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                if (world.isAir(plantPos) && ModBlocks.CATTAILS.getDefaultState().canPlaceAt(world, plantPos)) {
                    world.setBlockState(plantPos, ModBlocks.CATTAILS.getDefaultState(), 3);
                    world.setBlockState(plantPos.up(), ModBlocks.CATTAILS.getDefaultState().withIfExists(HALF, DoubleBlockHalf.UPPER), 3);
                }
            }


        }
    }
}
