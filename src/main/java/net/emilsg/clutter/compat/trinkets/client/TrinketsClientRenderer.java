package net.emilsg.clutter.compat.trinkets.client;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.HatItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

import java.util.HashMap;
import java.util.Map;

public class TrinketsClientRenderer implements TrinketRenderer {

    private static final Map<Item, float[]> HAT_CONFIGURATIONS = new HashMap<>();
    private static final MinecraftClient client = MinecraftClient.getInstance();

    static {
        HAT_CONFIGURATIONS.put(ModItems.BEACH_HAT, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.BERET, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.BUTTERFLY_WINGS, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.CAP, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.COWBOY_HAT, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.CROWN, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.PROPELLER_CAP, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.TIARA, new float[]{0.65f, -0.05f});
        HAT_CONFIGURATIONS.put(ModItems.SILVER_TIARA, new float[]{0.65f, -0.05f});
        HAT_CONFIGURATIONS.put(ModItems.TOP_HAT, new float[]{0.65f, 0.1f});
        HAT_CONFIGURATIONS.put(ModItems.VIKING_HELMET, new float[]{0.65f, 0.1f});
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!(entity instanceof AbstractClientPlayerEntity player)) return;

        if(client.options.getPerspective().isFirstPerson() && client.player != null && client.player.isMainPlayer() && Clutter.IS_FIRST_PERSON_MODEL_LOADED && !(client.currentScreen instanceof InventoryScreen)) return;

        TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player, headYaw, headPitch);

        float scale = 0.65f;
        float yDefault = player.hasStackEquipped(EquipmentSlot.HEAD) ? 0.1f : 0.0f;

        Item headItem = stack.getItem();
        if (headItem instanceof HatItem && HAT_CONFIGURATIONS.containsKey(headItem)) {
            float[] config = HAT_CONFIGURATIONS.get(headItem);
            scale = config[0];
            yDefault = yDefault + config[1];
        }


        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yDefault, 0.45);

        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
    }
}
