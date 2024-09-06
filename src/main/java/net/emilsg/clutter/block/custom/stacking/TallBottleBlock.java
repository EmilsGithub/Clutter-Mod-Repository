package net.emilsg.clutter.block.custom.stacking;

import net.minecraft.item.Item;

public class TallBottleBlock extends AbstractStackableGlassBlock4 {

    public TallBottleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Item getDroppedItem() {
        return this.asItem();
    }

    @Override
    public float getModelXLength() {
        return 4f;
    }

    @Override
    public float getModelYHeight() {
        return 13f;
    }

    @Override
    public float getModelZDepth() {
        return 4f;
    }

}
