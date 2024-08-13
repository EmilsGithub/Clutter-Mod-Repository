package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.KiwiBirdAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterModel;
import net.emilsg.clutter.entity.custom.KiwiBirdEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class KiwiBirdModel<T extends KiwiBirdEntity> extends ClutterModel<T> {
    private final ModelPart root;
    private final ModelPart all;
    private final ModelPart head;

    public KiwiBirdModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("All");
        this.head = all.getChild("Head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData All = modelPartData.addChild("All", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 0.5F));

        ModelPartData Torso = All.addChild("Torso", ModelPartBuilder.create().uv(7, 10).cuboid(-2.0F, -2.0F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData L_Leg = All.addChild("L_Leg", ModelPartBuilder.create().uv(12, 23).cuboid(-0.5F, 3.01F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(18, 20).cuboid(-0.5F, 1.01F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(23, 20).cuboid(-1.0F, -0.74F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 1.0F, 0.5F));

        ModelPartData R_Leg = All.addChild("R_Leg", ModelPartBuilder.create().uv(17, 23).cuboid(-0.5F, 3.01F, -1.0F, 1.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(13, 20).cuboid(-0.5F, 1.01F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(1, 20).cuboid(-1.0F, -0.74F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, 1.0F, 0.5F));

        ModelPartData Head = All.addChild("Head", ModelPartBuilder.create().uv(1, 2).cuboid(-1.5F, -1.5F, -2.75F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, -2.0F));

        ModelPartData beak_r1 = Head.addChild("beak_r1", ModelPartBuilder.create().uv(22, 4).cuboid(-0.5F, -0.75F, -3.25F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, -2.25F, 0.0873F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(KiwiBirdEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(entity, netHeadYaw, headPitch, ageInTicks);

        if (!entity.isSongPlaying())
            this.animateMovement(KiwiBirdAnimations.KIWI_BIRD_WALK, limbSwing, limbSwingAmount, 3f, 2f);
        if (entity.isSongPlaying())
            this.updateAnimation(entity.dancingAnimationState, KiwiBirdAnimations.KIWI_BIRD_DANCE, ageInTicks, 1f);
        this.updateAnimation(entity.idleAnimationState, KiwiBirdAnimations.KIWI_BIRD_IDLE, ageInTicks, 1f);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            float babyScale = 0.75f;
            matrices.push();
            matrices.scale(babyScale, babyScale, babyScale);
            matrices.translate(0.0D, 0.5D, 0D);
            this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
            matrices.pop();
            this.getHeadPart().scale(createVec3f(1.0f));
        } else {
            matrices.push();
            this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
            matrices.pop();
        }
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    protected ModelPart getHeadPart() {
        return head;
    }
}