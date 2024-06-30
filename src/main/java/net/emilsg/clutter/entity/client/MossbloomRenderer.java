package net.emilsg.clutter.entity.client;

import com.google.common.collect.Maps;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.MossbloomEntity;
import net.emilsg.clutter.entity.variants.MossbloomVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

import java.util.Map;

public class MossbloomRenderer extends GeoEntityRenderer<MossbloomEntity> {
    public static final Map<MossbloomVariant, Identifier> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(MossbloomVariant.class), (map) -> {
        map.put(MossbloomVariant.M, new Identifier(Clutter.MOD_ID, "textures/entity/mossbloom_m.png"));
        map.put(MossbloomVariant.F, new Identifier(Clutter.MOD_ID, "textures/entity/mossbloom_f.png"));
    });

    public MossbloomRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MossbloomModel());
        this.shadowRadius = 0.5f;
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void render(MossbloomEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        float babyScale = 0.6f;

        if (entity.isBaby()) {
            poseStack.scale(babyScale, babyScale, babyScale);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public Identifier getTextureLocation(MossbloomEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
