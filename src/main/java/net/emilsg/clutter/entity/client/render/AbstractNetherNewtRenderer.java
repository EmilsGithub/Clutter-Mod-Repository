package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.NetherNewtModel;
import net.emilsg.clutter.entity.custom.AbstractNetherNewtEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public abstract class AbstractNetherNewtRenderer extends MobEntityRenderer<AbstractNetherNewtEntity, NetherNewtModel<AbstractNetherNewtEntity>> {
    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/crimson_newt_box.png");

    public AbstractNetherNewtRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new NetherNewtModel<>(ctx.getPart(ModModelLayers.NETHER_NEWT)), 0.4f);
    }

    @Override
    public void render(AbstractNetherNewtEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        float scale = livingEntity.getNewtSize();
        this.shadowRadius = 0.35f * scale;

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected void scale(AbstractNetherNewtEntity entity, MatrixStack matrices, float amount) {
        float scale = entity.getNewtSize();
        matrices.scale(scale, scale, scale);
    }

    @Override
    public abstract Identifier getTexture(AbstractNetherNewtEntity entity);
}
