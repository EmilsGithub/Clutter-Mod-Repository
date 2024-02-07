package net.emilsg.clutter.block.custom.cutout;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;

public class CFlowerPotBlock extends FlowerPotBlock implements ICutoutRenderable {

    public CFlowerPotBlock(Block content, Settings settings) {
        super(content, settings);
    }
}
