package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.entity.variants.SeahorseVariant;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.sound.SoundEvent;

public class SeahorseBucketItem extends EntityBucketItem {
    public static final MapCodec<SeahorseVariant> SEAHORSE_VARIANT_MAP_CODEC = SeahorseVariant.CODEC.fieldOf("Variant");

    public SeahorseBucketItem(EntityType<?> type, Fluid fluid, SoundEvent emptyingSound, Settings settings) {
        super(type, fluid, emptyingSound, settings);
    }

}
