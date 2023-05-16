package net.emilsg.clutter.mixin;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    @Shadow
    @Final
    private ElytraEntityModel<T> elytra;

    protected ElytraFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    private void onRender(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

        String color;
        if ((color = getColor(itemStack.getItem())) != null) {
            Identifier customElytraTexture = new Identifier(Clutter.MOD_ID, "textures/entity/" + color + "_butterfly_elytra.png");
            renderCustomElytra(matrixStack, vertexConsumerProvider, i, livingEntity, f, g, h, j, k, l, customElytraTexture);
            ci.cancel();
        }

    }

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
        return null;
    }

    private void renderCustomElytra(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l, Identifier texture) {
        matrixStack.push();
        matrixStack.translate(0.0F, 0.0F, 0.125F);
        this.getContextModel().copyStateTo(elytra);
        elytra.setAngles(livingEntity, f, g, j, k, l);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(texture), false, livingEntity.getEquippedStack(EquipmentSlot.CHEST).hasGlint());
        elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
    }
}

