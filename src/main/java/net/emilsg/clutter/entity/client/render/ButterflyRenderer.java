package net.emilsg.clutter.entity.client.render;

import com.google.common.collect.Maps;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.ButterflyModel;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class ButterflyRenderer extends MobEntityRenderer<ButterflyEntity, ButterflyModel<ButterflyEntity>> {

    public static final Map<ButterflyVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(ButterflyVariant.class), (map) -> {
                map.put(ButterflyVariant.YELLOW, Identifier.of(Clutter.MOD_ID, "textures/entity/yellow_butterfly_box.png"));
                map.put(ButterflyVariant.RED, Identifier.of(Clutter.MOD_ID, "textures/entity/red_butterfly_box.png"));
                map.put(ButterflyVariant.BLUE, Identifier.of(Clutter.MOD_ID, "textures/entity/blue_butterfly_box.png"));
                map.put(ButterflyVariant.PURPLE, Identifier.of(Clutter.MOD_ID, "textures/entity/purple_butterfly_box.png"));
                map.put(ButterflyVariant.WHITE, Identifier.of(Clutter.MOD_ID, "textures/entity/white_butterfly_box.png"));
                map.put(ButterflyVariant.GRAY, Identifier.of(Clutter.MOD_ID, "textures/entity/gray_butterfly_box.png"));
                map.put(ButterflyVariant.ORANGE, Identifier.of(Clutter.MOD_ID, "textures/entity/orange_butterfly_box.png"));
                map.put(ButterflyVariant.LIME, Identifier.of(Clutter.MOD_ID, "textures/entity/lime_butterfly_box.png"));
                map.put(ButterflyVariant.GREEN, Identifier.of(Clutter.MOD_ID, "textures/entity/green_butterfly_box.png"));
                map.put(ButterflyVariant.BLACK, Identifier.of(Clutter.MOD_ID, "textures/entity/black_butterfly_box.png"));
                map.put(ButterflyVariant.LIGHT_GRAY, Identifier.of(Clutter.MOD_ID, "textures/entity/light_gray_butterfly_box.png"));
                map.put(ButterflyVariant.LIGHT_BLUE, Identifier.of(Clutter.MOD_ID, "textures/entity/light_blue_butterfly_box.png"));
                map.put(ButterflyVariant.BROWN, Identifier.of(Clutter.MOD_ID, "textures/entity/brown_butterfly_box.png"));
                map.put(ButterflyVariant.CYAN, Identifier.of(Clutter.MOD_ID, "textures/entity/cyan_butterfly_box.png"));
                map.put(ButterflyVariant.MAGENTA, Identifier.of(Clutter.MOD_ID, "textures/entity/magenta_butterfly_box.png"));
                map.put(ButterflyVariant.PINK, Identifier.of(Clutter.MOD_ID, "textures/entity/pink_butterfly_box.png"));
                map.put(ButterflyVariant.WARPED, Identifier.of(Clutter.MOD_ID, "textures/entity/warped_butterfly_box.png"));
                map.put(ButterflyVariant.CRIMSON, Identifier.of(Clutter.MOD_ID, "textures/entity/crimson_butterfly_box.png"));
                map.put(ButterflyVariant.SOUL, Identifier.of(Clutter.MOD_ID, "textures/entity/soul_butterfly_box.png"));
            });

    public ButterflyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ButterflyModel<>(ctx.getPart(ModModelLayers.BUTTERFLY)), 0.4f);
    }

    @Override
    public void render(ButterflyEntity butterflyEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.2f;

        super.render(butterflyEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(ButterflyEntity butterflyEntity) {
        return LOCATION_BY_VARIANT.get(butterflyEntity.getVariant());
    }
}
