package net.emilsg.clutter.compat.trinkets.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.compat.trinkets.TrinketsElytraUse;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModItemTags;
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
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

public class TrinketsElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final ElytraEntityModel<T> elytra;
    private static final Identifier DEFAULT = new Identifier("textures/entity/elytra.png");

    private String getColor(Item item) {
        if (item == ModItems.WHITE_BUTTERFLY_ELYTRA) return "white";
        if (item == ModItems.LIGHT_GRAY_BUTTERFLY_ELYTRA) return "light_gray";
        if (item == ModItems.GRAY_BUTTERFLY_ELYTRA) return "gray";
        if (item == ModItems.BLACK_BUTTERFLY_ELYTRA) return "black";
        if (item == ModItems.BROWN_BUTTERFLY_ELYTRA) return "brown";
        if (item == ModItems.RED_BUTTERFLY_ELYTRA) return "red";
        if (item == ModItems.ORANGE_BUTTERFLY_ELYTRA) return "orange";
        if (item == ModItems.YELLOW_BUTTERFLY_ELYTRA) return "yellow";
        if (item == ModItems.LIME_BUTTERFLY_ELYTRA) return "lime";
        if (item == ModItems.GREEN_BUTTERFLY_ELYTRA) return "green";
        if (item == ModItems.CYAN_BUTTERFLY_ELYTRA) return "cyan";
        if (item == ModItems.LIGHT_BLUE_BUTTERFLY_ELYTRA) return "light_blue";
        if (item == ModItems.BLUE_BUTTERFLY_ELYTRA) return "blue";
        if (item == ModItems.PURPLE_BUTTERFLY_ELYTRA) return "purple";
        if (item == ModItems.MAGENTA_BUTTERFLY_ELYTRA) return "magenta";
        if (item == ModItems.PINK_BUTTERFLY_ELYTRA) return "pink";
        if (item == ModItems.CRIMSON_BUTTERFLY_ELYTRA) return "crimson";
        if (item == ModItems.WARPED_BUTTERFLY_ELYTRA) return "warped";
        if (item == ModItems.SOUL_BUTTERFLY_ELYTRA) return "soul";
        return null;
    }

    public TrinketsElytraFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.elytra = new ElytraEntityModel<T>(loader.getModelPart(EntityModelLayers.ELYTRA));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        AbstractClientPlayerEntity abstractClientPlayerEntity;
        Identifier customElytraTexture = DEFAULT;
        String color;

        List<ItemStack> trinketStack = TrinketsElytraUse.getEquippedElytra(livingEntity);
        if (trinketStack.isEmpty()) return;
        ItemStack itemStack = trinketStack.get(0);

        if (!itemStack.isIn(ModItemTags.ELYTRON)) return;

        if ((color = getColor(itemStack.getItem())) != null) {
            customElytraTexture = new Identifier(Clutter.MOD_ID, "textures/entity/" + color + "_butterfly_elytra.png");
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
