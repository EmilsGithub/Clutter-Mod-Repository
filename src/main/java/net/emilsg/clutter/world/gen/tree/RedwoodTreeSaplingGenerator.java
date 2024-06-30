package net.emilsg.clutter.world.gen.tree;

import net.emilsg.clutter.world.ModConfiguredFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class RedwoodTreeSaplingGenerator extends LargeTreeSaplingGenerator {

    public static boolean canGenerateGiantTree(BlockState state, BlockView world, BlockPos pos, int x, int z) {
        Block block = state.getBlock();
        for (int i = x; i < x + 3; i++) {
            for (int j = z; j < z + 3; j++) {
                if (!world.getBlockState(pos.add(i, 0, j)).isOf(block)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ModConfiguredFeatures.SMALL_REDWOOD_KEY;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return ModConfiguredFeatures.MEDIUM_REDWOOD_KEY;
    }

    @Nullable
    protected RegistryKey<ConfiguredFeature<?, ?>> getGiantTreeFeature(Random random) {
        return ModConfiguredFeatures.REDWOOD_KEY;
    }

    @Override
    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (canGenerateGiantTree(state, world, pos, i, j)) {
                    return generateGiantTree(world, chunkGenerator, pos, state, random, i, j);
                }
            }
        }

        return super.generate(world, chunkGenerator, pos, state, random);
    }

    public boolean generateGiantTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random random, int x, int z) {
        RegistryKey<ConfiguredFeature<?, ?>> registryKey = this.getGiantTreeFeature(random);
        if (registryKey == null) {
            return false;
        }
        RegistryEntry<ConfiguredFeature<?, ?>> registryEntry = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE).getEntry(registryKey).orElse(null);
        if (registryEntry == null) {
            return false;
        }
        ConfiguredFeature<?, ?> configuredFeature = registryEntry.value();

        BlockState air = Blocks.AIR.getDefaultState();
        for (int i = x; i < x + 3; i++) {
            for (int j = z; j < z + 3; j++) {
                world.setBlockState(pos.add(i, 0, j), air, Block.NO_REDRAW);
            }
        }

        if (configuredFeature.generate(world, chunkGenerator, random, pos.add(x, 0, z))) {
            return true;
        }

        for (int i = x; i < x + 3; i++) {
            for (int j = z; j < z + 3; j++) {
                world.setBlockState(pos.add(i, 0, j), state, Block.NO_REDRAW);
            }
        }
        return false;
    }

}