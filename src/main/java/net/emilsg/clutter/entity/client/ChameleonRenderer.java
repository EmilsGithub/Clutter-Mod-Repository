package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.ChameleonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeAccess;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ChameleonRenderer extends GeoEntityRenderer<ChameleonEntity> {
    public ChameleonRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ChameleonModel());
    }

    @Override
    public Identifier getTextureLocation(ChameleonEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "textures/entity/chameleon.png");
    }

    @Override
    public void render(ChameleonEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public float getMotionAnimThreshold(ChameleonEntity animatable) {
        return 0.0075f;
    }
}
