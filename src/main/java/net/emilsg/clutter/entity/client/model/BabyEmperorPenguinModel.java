package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.EmperorPenguinAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterModel;
import net.emilsg.clutter.entity.custom.EmperorPenguinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class BabyEmperorPenguinModel<T extends EmperorPenguinEntity> extends ClutterModel<T> {
    private final ModelPart root;
    private final ModelPart all;
    private final ModelPart head;

    public BabyEmperorPenguinModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("All");
        this.head = all.getChild("Head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData All = modelPartData.addChild("All", ModelPartBuilder.create().uv(0, 12).cuboid(-3.0F, -2.0F, -2.0F, 6.0F, 1.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.0F, -8.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData Head = All.addChild("Head", ModelPartBuilder.create().uv(0, 19).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(18, 20).cuboid(-0.5F, -2.0F, -3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 0.0F));

        ModelPartData L_Wing = All.addChild("L_Wing", ModelPartBuilder.create().uv(24, 0).cuboid(0.0F, 0.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -8.0F, 0.0F));

        ModelPartData R_Wing = All.addChild("R_Wing", ModelPartBuilder.create().uv(24, 8).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -8.0F, 0.0F));

        ModelPartData L_Leg = All.addChild("L_Leg", ModelPartBuilder.create().uv(24, 21).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(-2, 27).cuboid(-1.0F, 1.0F, -3.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -1.0F, 0.0F));

        ModelPartData R_Leg = All.addChild("R_Leg", ModelPartBuilder.create().uv(24, 17).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(2, 27).cuboid(-1.0F, 1.0F, -3.0F, 2.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -1.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.getPart().render(matrices, vertices, light, overlay, color);
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

        this.animateMovement(EmperorPenguinAnimations.EMPEROR_PENGUIN_WADDLE, limbSwing, limbSwingAmount, 1.5f, 1f);

        this.updateAnimation(emperorPenguinEntity.flapAnimationStateOne, EmperorPenguinAnimations.EMPEROR_PENGUIN_RANDOM_FLAP, ageInTicks, 1f);
        this.updateAnimation(emperorPenguinEntity.flapAnimationStateTwo, EmperorPenguinAnimations.EMPEROR_PENGUIN_RANDOM_FLAP_TWO, ageInTicks, 1f);
        this.updateAnimation(emperorPenguinEntity.preenAnimationState, EmperorPenguinAnimations.EMPEROR_PENGUIN_PREEN, ageInTicks, 1f);
    }
}