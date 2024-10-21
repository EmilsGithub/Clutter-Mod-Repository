package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.MantaRayAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterAquaticModel;
import net.emilsg.clutter.entity.custom.MantaRayEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class MantaRayModel<T extends MantaRayEntity> extends ClutterAquaticModel<T> {
    private final ModelPart root;

    public MantaRayModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 1.0F));

        ModelPartData b = root.addChild("b", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -3.0F));

        ModelPartData cube_r1 = b.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-5.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.5F, -1.5F, -7.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r2 = b.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(4.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -1.5F, -7.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData cube_r3 = b.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -3.0F, -9.0F, 8.0F, 3.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData lf = b.addChild("lf", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -1.5F, 2.5F));

        ModelPartData cube_r4 = lf.addChild("cube_r4", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(-3.0F, -1.0F, -5.5F, 6.0F, 2.0F, 11.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData lf2 = lf.addChild("lf2", ModelPartBuilder.create(), ModelTransform.pivot(-6.0F, 0.0F, -0.5F));

        ModelPartData cube_r5 = lf2.addChild("cube_r5", ModelPartBuilder.create().uv(31, 0).mirrored().cuboid(-3.5F, -0.5F, -5.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-3.5F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData rf = b.addChild("rf", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -1.5F, 2.5F));

        ModelPartData cube_r6 = rf.addChild("cube_r6", ModelPartBuilder.create().uv(23, 20).mirrored().cuboid(-3.0F, -1.0F, -5.5F, 6.0F, 2.0F, 11.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(3.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData rf2 = rf.addChild("rf2", ModelPartBuilder.create(), ModelTransform.pivot(6.0F, 0.0F, -0.5F));

        ModelPartData cube_r7 = rf2.addChild("cube_r7", ModelPartBuilder.create().uv(0, 33).mirrored().cuboid(-3.5F, -0.5F, -5.0F, 7.0F, 1.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(3.5F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData t = b.addChild("t", ModelPartBuilder.create().uv(35, 35).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 9.0F));

        ModelPartData cube_r8 = t.addChild("cube_r8", ModelPartBuilder.create().uv(0, 6).cuboid(0.0F, -1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 1.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData t2 = t.addChild("t2", ModelPartBuilder.create().uv(24, 33).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.5F, 7.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(MantaRayEntity ray, float limbAngle, float limbDistance, float ageInTicks, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.getPart().getChild("root").pitch = headPitch * 0.017453292F;
        this.getPart().getChild("root").yaw = headYaw * 0.017453292F;

        if (ray.isTouchingWater() && !ray.isDead())
            this.updateAnimation(ray.swimmingAnimationState, MantaRayAnimations.MANTA_RAY_SWIM, ageInTicks, 1.0f);
        else if (!ray.isDead())
            this.updateAnimation(ray.flopAnimationState, MantaRayAnimations.MANTA_RAY_FLOP, ageInTicks, 1.0f);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        this.getPart().render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }
}
