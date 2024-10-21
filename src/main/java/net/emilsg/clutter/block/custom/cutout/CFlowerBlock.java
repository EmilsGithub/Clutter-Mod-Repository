package net.emilsg.clutter.block.custom.cutout;

import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;

public class CFlowerBlock extends FlowerBlock implements ICutoutRenderable {

    public CFlowerBlock(RegistryEntry<StatusEffect> stewEffect, int effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);
    }

}
