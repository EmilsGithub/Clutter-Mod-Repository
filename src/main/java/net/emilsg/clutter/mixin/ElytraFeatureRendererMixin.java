package net.emilsg.clutter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.compat.trinkets.TrinketsElytraUse;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.ButterflyElytraItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin {


    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z")
    )
    private boolean canRenderElytra(boolean orgBool, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity) {
        return orgBool || getEquippedElytra(livingEntity).getItem() instanceof ButterflyElytraItem;
    }

    @ModifyVariable(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"))
    private Identifier getButterflyElytraTexture(Identifier value, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity) {

        ItemStack itemStack = getEquippedElytra(livingEntity);

        if (!(itemStack.getItem() instanceof ButterflyElytraItem butterflyElytraItem)) {
            return value;
        }

        String color = butterflyElytraItem.getColor();

        return new Identifier(Clutter.MOD_ID, "textures/entity/" + color + "_butterfly_elytra.png");
    }

    @Unique
    private ItemStack getEquippedElytra(LivingEntity livingEntity) {
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);

        if (Clutter.IS_TRINKETS_LOADED && !(itemStack.getItem() instanceof ButterflyElytraItem)) {
            List<ItemStack> trinketsStack = TrinketsElytraUse.getEquippedElytra(livingEntity);
            if(!trinketsStack.isEmpty()) {
                itemStack = trinketsStack.get(0);
            }
        }

        return itemStack;
    }
}
