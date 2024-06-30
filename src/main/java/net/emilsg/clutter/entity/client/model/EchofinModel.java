package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.EchofinAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterModel;
import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class EchofinModel<T extends EchofinEntity> extends ClutterModel<T> {
    private final ModelPart all;
    private final ModelPart root;


    public EchofinModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("all");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData all = modelPartData.addChild("all", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 23.0F, -2.0F));

        ModelPartData bodyOne = all.addChild("bodyOne", ModelPartBuilder.create().uv(0, 14).cuboid(0.0F, -5.5F, -4.0F, 0.0F, 3.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 30).cuboid(0.0F, 2.5F, -4.0F, 0.0F, 3.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 25).cuboid(-1.0F, -2.5F, -4.0F, 2.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.5F, -5.0F));

        ModelPartData bodyTwo = bodyOne.addChild("bodyTwo", ModelPartBuilder.create().uv(20, 28).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 7.0F, new Dilation(0.0F))
                .uv(20, 16).cuboid(0.0F, -6.5F, 0.0F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F))
                .uv(20, 31).cuboid(0.0F, 1.5F, 0.0F, 0.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

        ModelPartData tailOne = bodyTwo.addChild("tailOne", ModelPartBuilder.create().uv(28, 19).cuboid(-4.5F, 0.0F, 0.0F, 9.0F, 0.0F, 8.0F, new Dilation(0.0F))
                .uv(24, -1).cuboid(0.0F, -4.5F, 0.0F, 0.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 7.0F));

        ModelPartData rightWingOne = bodyOne.addChild("rightWingOne", ModelPartBuilder.create().uv(0, 18).cuboid(-2.0F, -0.5F, -0.75F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(4, 16).cuboid(-2.0F, 0.0F, 2.25F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 0.0F, 0.75F));

        ModelPartData rightWingTwo = rightWingOne.addChild("rightWingTwo", ModelPartBuilder.create().uv(-9, 9).cuboid(-4.0F, 0.0F, -0.5F, 4.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 0.0F, 0.75F));

        ModelPartData rightWingThree = rightWingTwo.addChild("rightWingThree", ModelPartBuilder.create().uv(-9, 0).cuboid(-4.0F, 0.0F, -0.5F, 4.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, 1.0F));

        ModelPartData leftWingOne = bodyOne.addChild("leftWingOne", ModelPartBuilder.create().uv(0, 41).cuboid(0.0F, -0.5F, -0.75F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(4, 41).cuboid(0.0F, 0.0F, 2.25F, 2.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 0.0F, 0.75F));

        ModelPartData leftWingTwo = leftWingOne.addChild("leftWingTwo", ModelPartBuilder.create().uv(36, 0).cuboid(0.0F, 0.0F, -0.5F, 4.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 0.0F, 0.75F));

        ModelPartData leftWingThree = leftWingTwo.addChild("leftWingThree", ModelPartBuilder.create().uv(36, 9).cuboid(0.0F, 0.0F, -0.5F, 4.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, 1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(EchofinEntity echofin, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(echofin.movingAnimState, EchofinAnimations.ECHOFIN_SWIMMING, ageInTicks, 1f);
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
        return null;
    }
}
