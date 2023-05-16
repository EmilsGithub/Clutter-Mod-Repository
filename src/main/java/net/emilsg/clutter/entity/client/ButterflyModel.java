package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ButterflyModel extends GeoModel<ButterflyEntity> {
    @Override
    public Identifier getModelResource(ButterflyEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "geo/butterfly.geo.json");
    }

    @Override
    public Identifier getTextureResource(ButterflyEntity animatable) {
         return ButterflyRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ButterflyEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "animations/butterfly.animation.json");
    }


}
