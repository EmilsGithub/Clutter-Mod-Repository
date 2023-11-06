package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.CapybaraEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class CapybaraRenderer extends GeoEntityRenderer<CapybaraEntity> {

    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/capybara.png");

    public CapybaraRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CapybaraModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public Identifier getTextureLocation(CapybaraEntity animatable) {
        return TEXTURE;
    }

    @Override
    public void render(CapybaraEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        float babyScale = 0.6f;
        if(entity.isBaby()) {
            poseStack.scale(babyScale, babyScale, babyScale);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public float getMotionAnimThreshold(CapybaraEntity animatable) {
        return 0.0075f;
    }
}
