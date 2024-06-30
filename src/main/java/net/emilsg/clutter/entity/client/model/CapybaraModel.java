package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.CapybaraAnimations;
import net.emilsg.clutter.entity.client.model.parent.TameableClutterModel;
import net.emilsg.clutter.entity.custom.CapybaraEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class CapybaraModel<T extends CapybaraEntity> extends TameableClutterModel<T> {
    private final ModelPart all;
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart root;

    public CapybaraModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("all");
        this.torso = all.getChild("torso");
        this.head = torso.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData all = modelPartData.addChild("all", ModelPartBuilder.create(), ModelTransform.pivot(2.25F, 19.5F, -4.5F));

        ModelPartData torso = all.addChild("torso", ModelPartBuilder.create().uv(0, 16).cuboid(-4.5F, -13.0F, -6.0F, 9.0F, 9.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.25F, 4.5F, 3.5F));

        ModelPartData head = torso.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -7.5F, 6.0F, 6.0F, 9.0F, new Dilation(0.0F))
                .uv(33, 23).cuboid(-3.0F, -4.0F, -3.5F, 6.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, -5.5F));

        ModelPartData rEar = head.addChild("rEar", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -4.0F, 0.5F));

        ModelPartData rEar_r1 = rEar.addChild("rEar_r1", ModelPartBuilder.create().uv(49, 19).cuboid(-2.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 1.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        ModelPartData lEar = head.addChild("lEar", ModelPartBuilder.create(), ModelTransform.pivot(2.5F, -4.0F, 0.5F));

        ModelPartData lEar_r1 = lEar.addChild("lEar_r1", ModelPartBuilder.create().uv(33, 19).cuboid(0.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 1.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

        ModelPartData FRLeg = torso.addChild("FRLeg", ModelPartBuilder.create().uv(52, 0).cuboid(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.25F, -4.5F, -3.5F));

        ModelPartData FLLeg = torso.addChild("FLLeg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.25F, -4.5F, -3.5F));

        ModelPartData BLLeg = torso.addChild("BLLeg", ModelPartBuilder.create().uv(40, 8).cuboid(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.25F, -4.5F, 6.25F));

        ModelPartData BRLeg = torso.addChild("BRLeg", ModelPartBuilder.create().uv(52, 8).cuboid(-1.5F, -0.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.25F, -4.5F, 6.25F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(CapybaraEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if (!entity.isSleeping() && !entity.isForceSleeping())
            this.setHeadAngles(entity, netHeadYaw, headPitch, ageInTicks);

        if (!entity.isMoving() && (entity.isForceSleeping()) || entity.isSleeping()) {
            this.updateAnimation(entity.sleepingAnimationState, entity.sleeperType() == 0 ? CapybaraAnimations.CAPYBARA_LAY_DOWN : entity.sleeperType() == 1 ? CapybaraAnimations.CAPYBARA_LAY_DOWN_2 : CapybaraAnimations.CAPYBARA_LAY_DOWN_3, ageInTicks, 1f);
        } else {
            this.animateMovement(CapybaraAnimations.CAPYBARA_WALK, limbSwing, limbSwingAmount, 1.5f, 2f);
        }

        if (entity.getRandom().nextInt(100) == 0) {
            this.updateAnimation(entity.earTwitchAnimationState, entity.getRandom().nextBoolean() ? CapybaraAnimations.CAPYBARA_EAR_TWITCH_ONE : CapybaraAnimations.CAPYBARA_EAR_TWITCH_TWO, ageInTicks, 1f);
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.child) {
            float babyScale = 0.5f;
            this.head.scale(createVec3f(babyScale));
            matrices.push();
            matrices.scale(babyScale, babyScale, babyScale);
            matrices.translate(0.0D, 1.5D, 0D);
            this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
            matrices.pop();
            this.head.scale(createVec3f(0.9f));
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
