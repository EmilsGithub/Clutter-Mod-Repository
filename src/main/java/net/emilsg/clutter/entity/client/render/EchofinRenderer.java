package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.EchofinModel;
import net.emilsg.clutter.entity.client.render.feature.EmissiveRenderer;
import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class EchofinRenderer extends MobEntityRenderer<EchofinEntity, EchofinModel<EchofinEntity>> {

    public static final Identifier CHORUS_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/chorus_echofin_box.png");
    public static final Identifier EMISSIVE_CHORUS_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/chorus_echofin_box_emissive.png");
    public static final Identifier LEVITATING_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/levitating_echofin_box.png");
    public static final Identifier EMISSIVE_LEVITATING_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/levitating_echofin_box_emissive.png");

    public EchofinRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new EchofinModel<>(ctx.getPart(ModModelLayers.ECHOFIN)), 0.4f);
        this.addFeature(new EmissiveRenderer<>(this, EchofinRenderer::getEmissiveTexture));
    }

    @Override
    public void render(EchofinEntity echofinEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.2f;

        super.render(echofinEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public static Identifier getEmissiveTexture(EchofinEntity echofinEntity) {
        return echofinEntity.hasAbility()
        ? switch (echofinEntity.getVariant()) {
            case LEVITATING -> EMISSIVE_LEVITATING_TEXTURE;
            case CHORUS -> EMISSIVE_CHORUS_TEXTURE;
        }
        : null;
    }

    @Override
    public Identifier getTexture(EchofinEntity echofinEntity) {
        return switch (echofinEntity.getVariant()) {
            case LEVITATING -> LEVITATING_TEXTURE;
            case CHORUS -> CHORUS_TEXTURE;
        };
    }
}
