package net.emilsg.clutter.block.custom.plants;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchflowerBlock;
import net.minecraft.item.ItemConvertible;

public class KiwiCropBlock extends TorchflowerBlock {

    public KiwiCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.KIWI_SEEDS;
    }

    @Override
    public BlockState withAge(int age) {
        return age == 2 ? ModBlocks.KIWI_TREE_SAPLING.getDefaultState() : this.getDefaultState().with(this.getAgeProperty(), age);
    }
}
