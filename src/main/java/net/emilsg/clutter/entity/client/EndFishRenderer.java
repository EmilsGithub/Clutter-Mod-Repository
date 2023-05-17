package net.emilsg.clutter.entity.client;

import com.google.common.collect.Maps;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.custom.EndFishEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.emilsg.clutter.entity.variants.EndFishVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class EndFishRenderer extends GeoEntityRenderer<EndFishEntity> {
    public EndFishRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EndFishModel());
    }

    public static final Map<EndFishVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(EndFishVariant.class), (map) -> {
                map.put(EndFishVariant.DEFAULT,
                        new Identifier(Clutter.MOD_ID, "textures/entity/end_fish_default.png"));
                map.put(EndFishVariant.DEFAULT_TWO,
                        new Identifier(Clutter.MOD_ID, "textures/entity/end_fish_default.png"));
            });

    @Override
    public Identifier getTextureLocation(EndFishEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
