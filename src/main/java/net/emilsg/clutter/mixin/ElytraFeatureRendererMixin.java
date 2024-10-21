package net.emilsg.clutter.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.compat.trinkets.TrinketsElytraUse;
import net.emilsg.clutter.item.custom.ButterflyElytraItem;
import net.emilsg.clutter.item.custom.ClutterElytraItem;
import net.emilsg.clutter.item.custom.GemstoneElytraItem;
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
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(value = ElytraFeatureRenderer.class, priority = 1500)
public abstract class ElytraFeatureRendererMixin {

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean canRenderElytra(boolean orgBool, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity) {
        return orgBool || getEquippedElytra(livingEntity).getItem() instanceof ElytraItem;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"))
    private Identifier getButterflyElytraTexture(Identifier value, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity) {
        ItemStack itemStack = getEquippedElytra(livingEntity);
        Item elytraItem = itemStack.getItem();

        if (elytraItem == Items.ELYTRA || !(elytraItem instanceof ClutterElytraItem)) {
            return value;
        }

        if (elytraItem instanceof ButterflyElytraItem butterflyElytraItem) {
            String color = butterflyElytraItem.getColor();
            return Identifier.of(Clutter.MOD_ID, "textures/entity/elytra/" + color + "_butterfly_elytra.png");
        }

        if (elytraItem instanceof GemstoneElytraItem gemstoneElytraItem) {
            String type = gemstoneElytraItem.getTypeString();
            return Identifier.of(Clutter.MOD_ID, "textures/entity/elytra/" + type + "_gemstone_elytra.png");
        }

        return value;
    }

    @Unique
    private ItemStack getEquippedElytra(LivingEntity livingEntity) {
        ItemStack chestItemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack itemStack = chestItemStack;

        if (Clutter.IS_TRINKETS_LOADED && !(chestItemStack.getItem() instanceof ElytraItem) && !Clutter.IS_ELYTRA_SLOT_LOADED && !Clutter.IS_ELYTRA_TRINKET_LOADED) {
            List<ItemStack> trinketsStack = TrinketsElytraUse.getEquippedElytra(livingEntity);
            if (!trinketsStack.isEmpty()) {
                itemStack = trinketsStack.get(0);
            }
        }

        return itemStack;
    }
}
