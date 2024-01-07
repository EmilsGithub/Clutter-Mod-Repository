package net.emilsg.clutter.world.gen.tree;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.emilsg.clutter.world.gen.type.ModTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class RedwoodTrunkPlacer extends TrunkPlacer {

    public static final Codec<RedwoodTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> fillTrunkPlacerFields(instance).apply(instance, RedwoodTrunkPlacer::new));

    public RedwoodTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.REDWOOD_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        BlockPos blockPos = startPos.down();
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos, config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.east(), config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.south(), config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.north(), config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.west(), config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.south().east(), config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.south().west(), config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.north().east(), config);
        GiantTrunkPlacer.setToDirt(world, replacer, random, blockPos.north().west(), config);

        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int currentHeight = -4; currentHeight < height; currentHeight++) {
            int layerRadius = calculateRadiusForLayer(currentHeight, height);
            int nextLayerRadius = currentHeight < height - 1 ? calculateRadiusForLayer(currentHeight + 1, height) : layerRadius;

            boolean isInTopHalf = nextLayerRadius < layerRadius;

            for (int dx = -layerRadius; dx <= layerRadius; ++dx) {
                for (int dz = -layerRadius; dz <= layerRadius; ++dz) {
                    if (dx * dx + dz * dz <= layerRadius * layerRadius) {
                        boolean isWithinNextLayer = dx * dx + dz * dz <= nextLayerRadius * nextLayerRadius;

                        boolean isOuterPartOfLayer = dx * dx + dz * dz >= (layerRadius - 1) * (layerRadius - 1);
                        if (!isInTopHalf || isWithinNextLayer || (isOuterPartOfLayer && random.nextBoolean())) {
                            this.setLog(world, replacer, random, mutable, config, startPos, dx, currentHeight, dz);
                        }
                    }
                }
            }
        }
        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height), 0, true));
    }

    public int getHeight(Random random) {
        return this.baseHeight + random.nextInt(this.firstRandomHeight + 15) + random.nextInt(this.secondRandomHeight + 15);
    }

    private int calculateRadiusForLayer(int currentHeight, int totalHeight) {
         if (currentHeight < totalHeight * 0.15) {
            return 3;
        } else if (currentHeight < totalHeight * 0.45) {
            return 2;
        } else if (currentHeight < totalHeight * 0.85) {
            return 1;
        } else {
            return 0;
        }
    }

    private void setLog(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos.Mutable tmpPos, TreeFeatureConfig config, BlockPos startPos, int dx, int dy, int dz) {
        tmpPos.set(startPos, dx, dy, dz);
        this.trySetState(world, replacer, random, tmpPos, config);
    }
}
