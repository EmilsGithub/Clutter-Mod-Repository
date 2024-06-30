package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.KiwiBirdEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class KiwiBirdRenderer extends GeoEntityRenderer<KiwiBirdEntity> {

    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/kiwi_bird.png");

    public KiwiBirdRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new KiwiBirdModel());
        this.shadowRadius = 0.25f;
    }

    @Override
    public Identifier getTextureLocation(KiwiBirdEntity animatable) {
        return TEXTURE;
    }

    @Override
    public void render(KiwiBirdEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        float babyScale = 0.6f;
        float hasEggScale = 1.2f;

        if (entity.hasEgg() && !entity.isBaby()) {
            poseStack.scale(hasEggScale, hasEggScale, hasEggScale);
        } else if (entity.isBaby() && !entity.hasEgg()) {
            poseStack.scale(babyScale, babyScale, babyScale);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public float getMotionAnimThreshold(KiwiBirdEntity animatable) {
        return 0.0075f;
    }
}
