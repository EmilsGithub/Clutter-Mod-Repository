package net.emilsg.clutter.entity.client.player;

import net.emilsg.clutter.compat.trinkets.TrinketsElytraUse;
import net.emilsg.clutter.util.ModItemTags;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;


public class ElytraRenderer {

    public static void register() {
        LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register((AbstractClientPlayerEntity player) -> !TrinketsElytraUse.getEquippedElytra(player).isEmpty() || !player.getEquippedStack(EquipmentSlot.CHEST).isIn(ModItemTags.ELYTRON));

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((EntityType<? extends LivingEntity> livingEntity, LivingEntityRenderer<?, ?> livingEntityRenderer, RegistrationHelper registrationHelper, EntityRendererFactory.Context context) -> {
            registrationHelper.register(new TrinketsElytraFeatureRenderer<>(livingEntityRenderer, context.getModelLoader()));
        });
    }

}