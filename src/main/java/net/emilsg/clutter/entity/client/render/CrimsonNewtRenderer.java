package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.CrimsonNewtModel;
import net.emilsg.clutter.entity.custom.CrimsonNewtEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class CrimsonNewtRenderer extends MobEntityRenderer<CrimsonNewtEntity, CrimsonNewtModel<CrimsonNewtEntity>> {
    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/crimson_newt_box.png");

    public CrimsonNewtRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CrimsonNewtModel<>(ctx.getPart(ModModelLayers.CRIMSON_NEWT)), 0.4f);
    }

    @Override
    public void render(CrimsonNewtEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float scale = livingEntity.getNewtSize();
        this.shadowRadius = 0.35f * scale;

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected void scale(CrimsonNewtEntity entity, MatrixStack matrices, float amount) {
        float scale = entity.getNewtSize();
        matrices.scale(scale, scale, scale);
    }

    @Override
    public Identifier getTexture(CrimsonNewtEntity entity) {
        return TEXTURE;
    }
}
