package net.emilsg.clutter.world.gen.features;

import com.mojang.serialization.Codec;
import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FallenTreeFeature extends Feature<CountConfig> {

    public FallenTreeFeature(Codec<CountConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<CountConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos startPos = context.getOrigin();

        startPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, startPos);

        Direction.Axis axis = random.nextBoolean() ? Direction.Axis.X : Direction.Axis.Z;
        int length = 4 + random.nextInt(5);

        BlockPos.Mutable checkPos = startPos.mutableCopy();
        boolean canGenerate = true;
        for (int i = 0; i < length; i++) {
            if (!world.getBlockState(checkPos.down()).isSolidBlock(world, checkPos.down()) ||
                    !world.getBlockState(checkPos).isReplaceable()) {
                canGenerate = false;
                break;
            }
            checkPos.move(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH);
        }

        if (!canGenerate) {
            return false;
        }

        BlockPos.Mutable currentPos = startPos.mutableCopy();
        for (int i = 0; i < length; i++) {
            world.setBlockState(currentPos, ModBlocks.REDWOOD_LOG.getDefaultState().with(PillarBlock.AXIS, axis), 3);

            if (random.nextFloat() < 0.25f) {
                BlockPos above = currentPos.up();
                if (world.isAir(above)) {
                    world.setBlockState(above, random.nextBoolean() ? Blocks.BROWN_MUSHROOM.getDefaultState() : Blocks.RED_MUSHROOM.getDefaultState(), 3);
                }
            }
            currentPos.move(axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH);
        }

        return true;
    }


}
