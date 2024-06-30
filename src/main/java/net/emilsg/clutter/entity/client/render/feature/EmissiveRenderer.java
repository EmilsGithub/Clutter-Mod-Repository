package net.emilsg.clutter.entity.client.render.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class EmissiveRenderer<E extends Entity, EM extends EntityModel<E>> extends FeatureRenderer<E, EM> {
    private final Function<E, Identifier> emissiveTextureProvider;

    public EmissiveRenderer(FeatureRendererContext<E, EM> context, Function<E, Identifier> emissiveTextureProvider) {
        super(context);
        this.emissiveTextureProvider = emissiveTextureProvider;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, E entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        Identifier emissiveTexture = emissiveTextureProvider.apply(entity);
        if (emissiveTexture == null) return;
        RenderLayer emissiveLayer = RenderLayer.getEyes(emissiveTexture);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(emissiveLayer);
        this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.25f);
    }
}
