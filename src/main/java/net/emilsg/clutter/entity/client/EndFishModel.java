package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.EndFishEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EndFishModel extends GeoModel<EndFishEntity> {
    @Override
    public Identifier getModelResource(EndFishEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "geo/end_fish.geo.json");
    }

    @Override
    public Identifier getTextureResource(EndFishEntity animatable) {
         return EndFishRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(EndFishEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "animations/animation.fish.json");
    }
}
