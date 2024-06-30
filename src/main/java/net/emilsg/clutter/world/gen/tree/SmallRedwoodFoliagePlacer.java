package net.emilsg.clutter.world.gen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.emilsg.clutter.world.gen.type.ModFoliagePlacerTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class SmallRedwoodFoliagePlacer extends FoliagePlacer {

    public static final Codec<SmallRedwoodFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            fillFoliagePlacerFields(instance).and(
                    IntProvider.POSITIVE_CODEC.fieldOf("crown_height").forGetter((placer) -> placer.crownHeight)
            ).apply(instance, SmallRedwoodFoliagePlacer::new)
    );

    private final IntProvider crownHeight;

    public SmallRedwoodFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider crownHeight) {
        super(radius, offset);
        this.crownHeight = crownHeight;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModFoliagePlacerTypes.SMALL_REDWOOD_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BlockPlacer placer, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter();
        int y = blockPos.getY() + offset;
        int[] radii = new int[]{1, 1, 2, 1, 2, 3, 1, 2};
        int[] radii2 = new int[]{1, 1, 2, 1, 3, 2, 1, 2, 1};

        top(random.nextBoolean() ? radii : radii2, y, random, blockPos, config, placer, world);
    }

    private void top(int[] radii, int y, Random random, BlockPos blockPos, TreeFeatureConfig config, FoliagePlacer.BlockPlacer placer, TestableWorld world) {
        for (int i = 0; i < radii.length; i++) {
            int currentRadius = radii[i];
            if (i == 0) {
                placer.placeBlock(blockPos, config.foliageProvider.get(random, blockPos));
            } else {
                this.generateSquare(world, placer, random, config, new BlockPos(blockPos.getX(), y - i, blockPos.getZ()), currentRadius, 0, false);
            }
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return crownHeight.get(random);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return dx == radius && dz == radius && radius > 0;
    }
}
