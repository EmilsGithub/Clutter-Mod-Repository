package net.emilsg.clutter.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class StatedRockFeature extends Feature<WeightedBlockFeatureConfig> {

    public StatedRockFeature(Codec<WeightedBlockFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<WeightedBlockFeatureConfig> context) {
        WeightedBlockFeatureConfig config = context.getConfig();
        int size = config.size;
        BlockPos blockPos = context.getOrigin().down((int) (size / 2));
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();


        while (blockPos.getY() > structureWorldAccess.getBottomY() + 3) {
            if (!structureWorldAccess.isAir(blockPos.down())) {
                BlockState blockState = structureWorldAccess.getBlockState(blockPos.down());
                if (isSoil(blockState) || isStone(blockState)) {
                    break;
                }
            }
            blockPos = blockPos.down();
        }

        if (blockPos.getY() <= structureWorldAccess.getBottomY() + 3) {
            return false;
        } else {
            for (int i = 0; i < size; ++i) {
                int j = random.nextInt(size) + 1;
                int k = random.nextInt(size) + 1;
                int l = random.nextInt(size) + 1;
                float f = (float)(j + k + l) * 0.333F + 0.5F;

                for (BlockPos targetPos : BlockPos.iterate(blockPos.add(-j, -k, -l), blockPos.add(j, k, l))) {
                    if (targetPos.getSquaredDistance(blockPos) <= (double)(f * f)) {
                        BlockState stateToPlace = config.states.get(random, targetPos);
                        structureWorldAccess.setBlockState(targetPos, stateToPlace, 3);
                    }
                }

                blockPos = blockPos.add(-2 + random.nextInt(4), -random.nextInt(2), -2 + random.nextInt(4));
            }

            return true;
        }
    }



}

