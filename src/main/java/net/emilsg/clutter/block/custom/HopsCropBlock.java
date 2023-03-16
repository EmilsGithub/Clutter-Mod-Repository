package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class HopsCropBlock extends CropBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 6);

    public HopsCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.HOPS_SEEDS;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
