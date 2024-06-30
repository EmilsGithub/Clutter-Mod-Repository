package net.emilsg.clutter.world.gen.features;

import com.mojang.serialization.Codec;
import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class CattailsFeature extends Feature<CountConfig> {
    private final int radius;

    public CattailsFeature(Codec<CountConfig> codec, int radius) {
        super(codec);
        this.radius = radius;
    }

    @Override
    public boolean generate(FeatureContext<CountConfig> context) {
        int i = 0;
        Random random = context.getRandom();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        int j = context.getConfig().getCount().get(random);
        for (int k = 0; k < j; ++k) {
            int l = random.nextInt(radius) - random.nextInt(radius);
            int m = random.nextInt(radius) - random.nextInt(radius);
            int n = structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX() + l, blockPos.getZ() + m);
            BlockPos placedPos = new BlockPos(blockPos.getX() + l, n, blockPos.getZ() + m);
            BlockState cattailsDefaultState = ModBlocks.CATTAILS.getDefaultState();
            if (!structureWorldAccess.getBlockState(placedPos).isOf(Blocks.WATER) || !cattailsDefaultState.canPlaceAt(structureWorldAccess, placedPos))
                continue;
            structureWorldAccess.setBlockState(placedPos, cattailsDefaultState.with(Properties.WATERLOGGED, structureWorldAccess.getFluidState(placedPos).isOf(Fluids.WATER)), Block.NOTIFY_LISTENERS);
            structureWorldAccess.setBlockState(placedPos.up(), cattailsDefaultState.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).with(Properties.WATERLOGGED, structureWorldAccess.getFluidState(placedPos.up()).isOf(Fluids.WATER)), Block.NOTIFY_LISTENERS);
            ++i;
        }
        return i > 0;
    }

}
