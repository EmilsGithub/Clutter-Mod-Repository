package net.emilsg.clutter.block.custom.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class CattailPlantBlock extends TallFlowerBlock implements Fertilizable {
    public CattailPlantBlock(Settings settings) {
        super(settings);
    }

    public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        if (floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND) || floor.isOf(Blocks.CLAY)) {
            return true;
        } else {
            return super.canPlantOnTop(floor, world, pos);
        }
    }
}
