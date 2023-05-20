package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EchofinModel extends GeoModel<EchofinEntity> {
    @Override
    public Identifier getModelResource(EchofinEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "geo/echofin.geo.json");
    }

    @Override
    public Identifier getTextureResource(EchofinEntity animatable) {
        return EchofinRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(EchofinEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "animations/animation.echofin.json");
    }
}
