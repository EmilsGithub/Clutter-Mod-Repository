package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.CrabModel;
import net.emilsg.clutter.entity.custom.CrabEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CrabRenderer extends MobEntityRenderer<CrabEntity, CrabModel<CrabEntity>> {
    private static final Identifier BLUE_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/blue_crab.png");
    private static final Identifier BROWN_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/brown_crab.png");
    private static final Identifier ORANGE_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/orange_crab.png");
    private static final Identifier PURPLE_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/purple_crab.png");

    public CrabRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CrabModel<>(ctx.getPart(ModModelLayers.CRAB)), 0.4f);
    }

    @Override
    public void render(CrabEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.4f;

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(CrabEntity entity) {
        return switch (entity.getVariant()) {
            case BLUE -> BLUE_TEXTURE;
            case BROWN -> BROWN_TEXTURE;
            case PURPLE -> PURPLE_TEXTURE;
            case ORANGE ->  ORANGE_TEXTURE;
        };
    }
}
