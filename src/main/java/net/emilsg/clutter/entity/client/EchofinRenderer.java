package net.emilsg.clutter.entity.client;

import com.google.common.collect.Maps;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.emilsg.clutter.entity.variants.EchofinVariant;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class EchofinRenderer extends GeoEntityRenderer<EchofinEntity> {
    public EchofinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EchofinModel());
    }

    public static final Map<EchofinVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(EchofinVariant.class), (map) -> {
                map.put(EchofinVariant.LEVITATING,
                        new Identifier(Clutter.MOD_ID, "textures/entity/echofin.png"));
                map.put(EchofinVariant.CHORUS,
                        new Identifier(Clutter.MOD_ID, "textures/entity/chorus_echofin.png"));
            });

    @Override
    public Identifier getTextureLocation(EchofinEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
