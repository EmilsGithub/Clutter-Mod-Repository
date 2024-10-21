package net.emilsg.clutter.entity.client.player.feature;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.player.model.ScubaModel;
import net.emilsg.clutter.item.custom.CopperDivingArmorItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ScubaFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier SCUBA_TANK_TEXTURE = Identifier.of(Clutter.MOD_ID, "textures/entity/scuba_tank_texture.png");
    private final ScubaModel<T> tank;

    public ScubaFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.tank = new ScubaModel<>(loader.getModelPart(ModModelLayers.SCUBA_TANK));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.CHEST);

        if (!(itemStack.getItem() instanceof CopperDivingArmorItem)) return;

        matrices.push();
        this.getContextModel().copyStateTo(tank);
        this.tank.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(SCUBA_TANK_TEXTURE), itemStack.hasGlint());
        this.tank.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }
}
