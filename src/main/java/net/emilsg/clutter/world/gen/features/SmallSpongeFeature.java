package net.emilsg.clutter.world.gen.features;

import com.mojang.serialization.Codec;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.SmallSpongeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SmallSpongeFeature extends Feature<CountConfig>{

        public SmallSpongeFeature(Codec<CountConfig> codec) {
            super(codec);
        }

        @Override
        public boolean generate(FeatureContext<CountConfig> context) {
            int i = 0;
            Random random = context.getRandom();
            StructureWorldAccess structureWorldAccess = context.getWorld();
            BlockPos blockPos = context.getOrigin();
            int j = context.getConfig().getCount().get(random);
            for (int k = 0; k < j; ++k) {
                int l = random.nextInt(8) - random.nextInt(8);
                int m = random.nextInt(8) - random.nextInt(8);
                int n = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + l, blockPos.getZ() + m);
                BlockPos blockPos2 = new BlockPos(blockPos.getX() + l, n, blockPos.getZ() + m);
                BlockState blockState = ModBlocks.SMALL_SPONGE.getDefaultState().with(SmallSpongeBlock.AGE, random.nextInt(2) + 1).with(SmallSpongeBlock.WATERLOGGED, true);
                if (!structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) || !blockState.canPlaceAt(structureWorldAccess, blockPos2)) continue;
                structureWorldAccess.setBlockState(blockPos2, blockState, Block.NOTIFY_LISTENERS);
                ++i;
            }
            return i > 0;
        }

}
