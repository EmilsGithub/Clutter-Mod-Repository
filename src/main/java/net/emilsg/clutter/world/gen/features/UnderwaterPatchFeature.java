package net.emilsg.clutter.world.gen.features;

import com.mojang.serialization.Codec;
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

public class UnderwaterPatchFeature extends Feature<CountConfig> {

    private final BlockState state;
    private final BlockStateModifier stateModifier;
    public UnderwaterPatchFeature(Codec<CountConfig> codec, BlockState state, BlockStateModifier stateModifier) {
        super(codec);
        this.state = state;
        this.stateModifier = stateModifier;
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
            if (!structureWorldAccess.getBlockState(blockPos2).isOf(Blocks.WATER) || !state.canPlaceAt(structureWorldAccess, blockPos2))
                continue;

            BlockState newState = stateModifier.modify(state, random);
            structureWorldAccess.setBlockState(blockPos2, newState, Block.NOTIFY_LISTENERS);
            ++i;
        }

        return i > 0;
    }

    @FunctionalInterface
    public interface BlockStateModifier {
        BlockState modify(BlockState originalState, Random random);
    }

}
