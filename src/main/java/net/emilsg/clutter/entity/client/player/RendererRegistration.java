package net.emilsg.clutter.entity.client.player;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.compat.trinkets.TrinketsElytraUse;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ElytraItem;


public class RendererRegistration {

    public static void register() {
        LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register((AbstractClientPlayerEntity player) -> !(player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraItem));
        if(Clutter.IS_TRINKETS_LOADED) LivingEntityFeatureRenderEvents.ALLOW_CAPE_RENDER.register((AbstractClientPlayerEntity player) -> TrinketsElytraUse.getEquippedElytra(player).isEmpty());
    }

}
