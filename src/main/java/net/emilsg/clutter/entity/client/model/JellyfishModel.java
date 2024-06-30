package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.JellyfishAnimations;
import net.emilsg.clutter.entity.custom.JellyfishEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;


public class JellyfishModel<T extends JellyfishEntity> extends SinglePartEntityModel<T> {
    private final ModelPart all;
    private final ModelPart root;

    public JellyfishModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("all");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData all = modelPartData.addChild("all", ModelPartBuilder.create().uv(0, 10).cuboid(-3.0F, -9.0F, -3.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData outer_tentacles = all.addChild("outer_tentacles", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData n = outer_tentacles.addChild("n", ModelPartBuilder.create().uv(24, 0).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, -4.0F));

        ModelPartData n2 = n.addChild("n2", ModelPartBuilder.create().uv(24, 3).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        ModelPartData e = outer_tentacles.addChild("e", ModelPartBuilder.create().uv(16, 12).cuboid(0.0F, 0.0F, -4.0F, 0.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -6.0F, 0.0F));

        ModelPartData e2 = e.addChild("e2", ModelPartBuilder.create().uv(16, 15).cuboid(0.0F, 0.0F, -4.0F, 0.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        ModelPartData s = outer_tentacles.addChild("s", ModelPartBuilder.create().uv(12, 26).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, 4.0F));

        ModelPartData s2 = s.addChild("s2", ModelPartBuilder.create().uv(12, 29).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        ModelPartData w = outer_tentacles.addChild("w", ModelPartBuilder.create().uv(0, 12).cuboid(0.0F, 0.0F, -4.0F, 0.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -6.0F, 0.0F));

        ModelPartData w2 = w.addChild("w2", ModelPartBuilder.create().uv(0, 15).cuboid(0.0F, 0.0F, -4.0F, 0.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        ModelPartData inner_tentacles = all.addChild("inner_tentacles", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData n3 = inner_tentacles.addChild("n3", ModelPartBuilder.create().uv(52, 20).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, -3.0F));

        ModelPartData e3 = inner_tentacles.addChild("e3", ModelPartBuilder.create().uv(52, 24).cuboid(0.0F, 0.0F, -3.0F, 0.0F, 10.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -5.0F, 0.0F));

        ModelPartData s3 = inner_tentacles.addChild("s3", ModelPartBuilder.create().uv(52, 0).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, 3.0F));

        ModelPartData w3 = inner_tentacles.addChild("w3", ModelPartBuilder.create().uv(52, 4).cuboid(0.0F, 0.0F, -3.0F, 0.0F, 10.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -5.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(JellyfishEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.swimmingAnimationState, JellyfishAnimations.JELLYFISH_SWIM, animationProgress, 1f);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }


    @Override
    public ModelPart getPart() {
        return root;
    }
}