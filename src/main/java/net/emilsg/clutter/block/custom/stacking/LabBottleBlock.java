package net.emilsg.clutter.block.custom.stacking;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.block.custom.cutout.ICutoutRenderable;
import net.emilsg.clutter.util.ModUtil;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.item.Item;
import net.minecraft.util.shape.VoxelShape;

public class LabBottleBlock extends AbstractStackableGlassBlock4 implements ICutoutRenderable {

    public LabBottleBlock(Settings settings) {
        super(settings);
    }

    public static final MapCodec<LabBottleBlock> CODEC = createCodec(LabBottleBlock::new);

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
        return 8f;
    }

    @Override
    public float getModelZDepth() {
        return 4f;
    }

    @Override
    public VoxelShape getFourShape() {
        return ModUtil.createBasicShape((this.getModelXLength() * 2) + 3f, 10f + 0.5f, (this.getModelZDepth() * 2) + 3f);
    }

}
