package net.emilsg.clutter.entity.client.player.feature;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.ButterflyElytraItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final ElytraEntityModel<T> elytra;
    private static final Identifier DEFAULT = new Identifier("textures/entity/elytra.png");

    private static final Map<Item, String> itemTextureMap = new HashMap<>();

    static {
        itemTextureMap.put(ModItems.WHITE_BUTTERFLY_ELYTRA, "textures/entity/white_butterfly_elytra.png");
        itemTextureMap.put(ModItems.LIGHT_GRAY_BUTTERFLY_ELYTRA, "textures/entity/light_gray_butterfly_elytra.png");
        itemTextureMap.put(ModItems.GRAY_BUTTERFLY_ELYTRA, "textures/entity/gray_butterfly_elytra.png");
        itemTextureMap.put(ModItems.BLACK_BUTTERFLY_ELYTRA, "textures/entity/black_butterfly_elytra.png");
        itemTextureMap.put(ModItems.BROWN_BUTTERFLY_ELYTRA, "textures/entity/brown_butterfly_elytra.png");
        itemTextureMap.put(ModItems.RED_BUTTERFLY_ELYTRA, "textures/entity/red_butterfly_elytra.png");
        itemTextureMap.put(ModItems.ORANGE_BUTTERFLY_ELYTRA, "textures/entity/orange_butterfly_elytra.png");
        itemTextureMap.put(ModItems.YELLOW_BUTTERFLY_ELYTRA, "textures/entity/yellow_butterfly_elytra.png");
        itemTextureMap.put(ModItems.LIME_BUTTERFLY_ELYTRA, "textures/entity/lime_butterfly_elytra.png");
        itemTextureMap.put(ModItems.GREEN_BUTTERFLY_ELYTRA, "textures/entity/green_butterfly_elytra.png");
        itemTextureMap.put(ModItems.CYAN_BUTTERFLY_ELYTRA, "textures/entity/cyan_butterfly_elytra.png");
        itemTextureMap.put(ModItems.LIGHT_BLUE_BUTTERFLY_ELYTRA, "textures/entity/light_blue_butterfly_elytra.png");
        itemTextureMap.put(ModItems.BLUE_BUTTERFLY_ELYTRA, "textures/entity/blue_butterfly_elytra.png");
        itemTextureMap.put(ModItems.PURPLE_BUTTERFLY_ELYTRA, "textures/entity/purple_butterfly_elytra.png");
        itemTextureMap.put(ModItems.MAGENTA_BUTTERFLY_ELYTRA, "textures/entity/magenta_butterfly_elytra.png");
        itemTextureMap.put(ModItems.PINK_BUTTERFLY_ELYTRA, "textures/entity/pink_butterfly_elytra.png");
        itemTextureMap.put(ModItems.CRIMSON_BUTTERFLY_ELYTRA, "textures/entity/crimson_butterfly_elytra.png");
        itemTextureMap.put(ModItems.WARPED_BUTTERFLY_ELYTRA, "textures/entity/warped_butterfly_elytra.png");
        itemTextureMap.put(ModItems.SOUL_BUTTERFLY_ELYTRA, "textures/entity/soul_butterfly_elytra.png");
    }

    private String getTexture(Item item) {
        return itemTextureMap.get(item);
    }

    public ElytraFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.elytra = new ElytraEntityModel<T>(loader.getModelPart(EntityModelLayers.ELYTRA));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        AbstractClientPlayerEntity abstractClientPlayerEntity;
        Identifier customElytraTexture = DEFAULT;
        String texture;
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

        if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) return;

        if (!(itemStack.getItem() instanceof ButterflyElytraItem)) return;

        if ((texture = getTexture(itemStack.getItem())) != null) {
            customElytraTexture = new Identifier(Clutter.MOD_ID, texture);
        }

        if (itemStack.isOf(Items.ELYTRA)) {
            if (livingEntity instanceof AbstractClientPlayerEntity) {
                customElytraTexture = DEFAULT;
                abstractClientPlayerEntity = (AbstractClientPlayerEntity) livingEntity;

                if (abstractClientPlayerEntity.canRenderElytraTexture() && abstractClientPlayerEntity.getElytraTexture() != null) {
                    customElytraTexture = abstractClientPlayerEntity.getElytraTexture();
                } else if (abstractClientPlayerEntity.canRenderCapeTexture() && abstractClientPlayerEntity.getCapeTexture() != null &&
                        abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE)) {
                    customElytraTexture = abstractClientPlayerEntity.getCapeTexture();
                }
            }
        }

        matrixStack.push();
        matrixStack.translate(0.0f, 0.0f, 0.125f);
        (this.getContextModel()).copyStateTo(this.elytra);
        this.elytra.setAngles(livingEntity, f, g, j, k, l);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(customElytraTexture), false, itemStack.hasGlint());
        this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }
}
