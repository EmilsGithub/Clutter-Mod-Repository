package net.emilsg.clutter.block.custom.plants;

import net.minecraft.block.CropBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

public class ModCropBlock extends CropBlock {
    private final Item seedItem;

    public ModCropBlock(Settings settings, Item seedItem) {
        super(settings);
        this.seedItem = seedItem;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return seedItem;
    }
}
