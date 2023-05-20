package net.emilsg.clutter.entity.client;

import com.google.common.collect.Maps;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class ButterflyRenderer extends GeoEntityRenderer<ButterflyEntity> {
    public ButterflyRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ButterflyModel());
    }

    public static final Map<ButterflyVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ButterflyVariant.class), (map) -> {
                map.put(ButterflyVariant.YELLOW, new Identifier(Clutter.MOD_ID, "textures/entity/yellow_butterfly.png"));
                map.put(ButterflyVariant.RED, new Identifier(Clutter.MOD_ID, "textures/entity/red_butterfly.png"));
                map.put(ButterflyVariant.BLUE, new Identifier(Clutter.MOD_ID, "textures/entity/blue_butterfly.png"));
                map.put(ButterflyVariant.PURPLE, new Identifier(Clutter.MOD_ID, "textures/entity/purple_butterfly.png"));
                map.put(ButterflyVariant.WHITE, new Identifier(Clutter.MOD_ID, "textures/entity/white_butterfly.png"));
                map.put(ButterflyVariant.GRAY, new Identifier(Clutter.MOD_ID, "textures/entity/gray_butterfly.png"));
                map.put(ButterflyVariant.ORANGE, new Identifier(Clutter.MOD_ID, "textures/entity/orange_butterfly.png"));
                map.put(ButterflyVariant.LIME, new Identifier(Clutter.MOD_ID, "textures/entity/lime_butterfly.png"));
                map.put(ButterflyVariant.GREEN, new Identifier(Clutter.MOD_ID, "textures/entity/green_butterfly.png"));
                map.put(ButterflyVariant.BLACK, new Identifier(Clutter.MOD_ID, "textures/entity/black_butterfly.png"));
                map.put(ButterflyVariant.LIGHT_GRAY, new Identifier(Clutter.MOD_ID, "textures/entity/light_gray_butterfly.png"));
                map.put(ButterflyVariant.LIGHT_BLUE, new Identifier(Clutter.MOD_ID, "textures/entity/light_blue_butterfly.png"));
                map.put(ButterflyVariant.BROWN, new Identifier(Clutter.MOD_ID, "textures/entity/brown_butterfly.png"));
                map.put(ButterflyVariant.CYAN, new Identifier(Clutter.MOD_ID, "textures/entity/cyan_butterfly.png"));
                map.put(ButterflyVariant.MAGENTA, new Identifier(Clutter.MOD_ID, "textures/entity/magenta_butterfly.png"));
                map.put(ButterflyVariant.PINK, new Identifier(Clutter.MOD_ID, "textures/entity/pink_butterfly.png"));
                map.put(ButterflyVariant.WARPED, new Identifier(Clutter.MOD_ID, "textures/entity/warped_butterfly.png"));
                map.put(ButterflyVariant.CRIMSON, new Identifier(Clutter.MOD_ID, "textures/entity/crimson_butterfly.png"));
                map.put(ButterflyVariant.SOUL, new Identifier(Clutter.MOD_ID, "textures/entity/soul_butterfly.png"));
            });

    @Override
    public Identifier getTextureLocation(ButterflyEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public void render(ButterflyEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f,0.5f,0.5f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
