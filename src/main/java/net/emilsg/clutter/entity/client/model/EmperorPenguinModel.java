package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.EmperorPenguinAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterModel;
import net.emilsg.clutter.entity.custom.EmperorPenguinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class EmperorPenguinModel<T extends EmperorPenguinEntity> extends ClutterModel<T> {
    private final ModelPart root;
    private final ModelPart all;
    private final ModelPart head;

    public EmperorPenguinModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("All");
        this.head = all.getChild("Head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData All = modelPartData.addChild("All", ModelPartBuilder.create().uv(45, 17).cuboid(-3.0F, 9.0F, 2.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 41).cuboid(-4.0F, -6.0F, -4.0F, 8.0F, 15.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 29).cuboid(-4.0F, -6.0F, -5.0F, 8.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 13.0F, 0.0F));

        ModelPartData Head = All.addChild("Head", ModelPartBuilder.create().uv(38, 52).cuboid(-3.0F, -5.0F, -3.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, -1.0F));

        ModelPartData Beak = Head.addChild("Beak", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData Upper = Beak.addChild("Upper", ModelPartBuilder.create().uv(32, 46).cuboid(-0.5F, -1.0F, -4.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0625F))
                .uv(1, 1).cuboid(-0.5F, 0.125F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0625F)), ModelTransform.pivot(0.0F, -2.0F, -3.0F));

        ModelPartData Lower = Beak.addChild("Lower", ModelPartBuilder.create().uv(33, 42).cuboid(-0.5F, 0.0F, -3.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -3.0F));

        ModelPartData R_Wing = All.addChild("R_Wing", ModelPartBuilder.create().uv(43, 37).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -6.0F, -1.0F));

        ModelPartData L_Wing = All.addChild("L_Wing", ModelPartBuilder.create().uv(54, 37).cuboid(0.0F, 0.0F, -1.0F, 1.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -6.0F, -1.0F));

        ModelPartData L_Leg = All.addChild("L_Leg", ModelPartBuilder.create().uv(47, 5).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(2, 13).cuboid(-1.5F, 1.95F, -4.5F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 9.0F, -0.5F));

        ModelPartData R_Leg = All.addChild("R_Leg", ModelPartBuilder.create().uv(2, 5).cuboid(-1.5F, 1.95F, -4.5F, 3.0F, 0.0F, 3.0F, new Dilation(0.0F))
                .uv(47, 11).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 9.0F, -0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.getPart().render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    protected ModelPart getHeadPart() {
        return head;
    }

    @Override
    public void setAngles(EmperorPenguinEntity emperorPenguinEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(emperorPenguinEntity, netHeadYaw, headPitch, ageInTicks);

        this.animateMovement(EmperorPenguinAnimations.EMPEROR_PENGUIN_WALK, limbSwing, limbSwingAmount, 1.5f, 2f);

        this.updateAnimation(emperorPenguinEntity.flapAnimationStateOne, EmperorPenguinAnimations.EMPEROR_PENGUIN_RANDOM_FLAP, ageInTicks, 1f);
        this.updateAnimation(emperorPenguinEntity.flapAnimationStateTwo, EmperorPenguinAnimations.EMPEROR_PENGUIN_RANDOM_FLAP_TWO, ageInTicks, 1f);
        this.updateAnimation(emperorPenguinEntity.preenAnimationState, EmperorPenguinAnimations.EMPEROR_PENGUIN_PREEN, ageInTicks, 1f);
    }
}