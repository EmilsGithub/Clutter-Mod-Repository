package net.emilsg.clutter.block.custom.cutout;

import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;

public class CFlowerBlock extends FlowerBlock implements ICutoutRenderable {

    public CFlowerBlock(StatusEffect stewEffect, int duration, Settings settings) {
        super(stewEffect, duration, settings);
    }

}
