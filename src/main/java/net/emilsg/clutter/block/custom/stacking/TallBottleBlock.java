package net.emilsg.clutter.block.custom.stacking;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.Item;

public class TallBottleBlock extends AbstractStackableGlassBlock4 {

    public static final MapCodec<TallBottleBlock> CODEC = createCodec(TallBottleBlock::new);

    public TallBottleBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
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
