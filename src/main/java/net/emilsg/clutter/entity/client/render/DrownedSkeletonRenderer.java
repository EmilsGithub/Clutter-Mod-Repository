package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.DrownedSkeletonModel;
import net.emilsg.clutter.entity.custom.DrownedSkeletonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DrownedSkeletonRenderer extends MobEntityRenderer<DrownedSkeletonEntity, DrownedSkeletonModel<DrownedSkeletonEntity>> {

    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/drowned_skeleton.png");

    public DrownedSkeletonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DrownedSkeletonModel<>(ctx.getPart(ModModelLayers.DROWNED_SKELETON)), 0.4f);
        this.addFeature(new ElytraFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new HeldItemFeatureRenderer<>(this, ctx.getHeldItemRenderer()));
    }

    @Override
    public void render(DrownedSkeletonEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.4f;

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(DrownedSkeletonEntity entity) {
        return TEXTURE;
    }
}
