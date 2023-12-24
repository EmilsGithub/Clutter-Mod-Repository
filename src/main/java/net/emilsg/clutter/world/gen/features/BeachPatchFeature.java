package net.emilsg.clutter.world.gen.features;

import com.mojang.serialization.Codec;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.PearlClamBlock;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BeachPatchFeature extends Feature<CountConfig> {

    public BeachPatchFeature(Codec<CountConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<CountConfig> context) {
        int i = 0;
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        int j = context.getConfig().getCount().get(random);
        double radius = 8.0;

        for (int k = 0; k < j; ++k) {
            double angle = 2 * Math.PI * random.nextDouble();
            double distance = radius * Math.sqrt(random.nextDouble());
            int l = (int)(distance * Math.cos(angle));
            int m = (int)(distance * Math.sin(angle));
            int n = structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockPos.getX() + l, blockPos.getZ() + m);
            BlockPos blockPos2 = new BlockPos(blockPos.getX() + l, n, blockPos.getZ() + m);

            if (structureWorldAccess.getBlockState(blockPos2.down()).isIn(BlockTags.SAND)) {
                if (!structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER)) {
                    BlockState randomState = getRandomWeightedBlockState(random);
                    if (randomState.canPlaceAt(structureWorldAccess, blockPos2)) {
                        structureWorldAccess.setBlockState(blockPos2, randomState, Block.NOTIFY_LISTENERS);
                        ++i;
                    }
                }
            }
        }

        return i > 0;
    }


    private BlockState getRandomWeightedBlockState(Random random) {
        BlockState seashell = ModBlocks.SEASHELL_BLOCK.getDefaultState().with(ModProperties.AMOUNT, random.nextInt(3) + 1).with(Properties.HORIZONTAL_FACING, Direction.Type.HORIZONTAL.random(random));
        BlockState clam = ModBlocks.PEARL_CLAM_BLOCK.getDefaultState().with(PearlClamBlock.HAS_PEARL, false).with(Properties.HORIZONTAL_FACING, Direction.Type.HORIZONTAL.random(random));

        return random.nextInt(4) < 3 ? seashell : clam;
    }

}
