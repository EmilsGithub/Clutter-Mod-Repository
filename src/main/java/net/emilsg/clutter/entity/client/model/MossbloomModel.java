package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.MossbloomAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterModel;
import net.emilsg.clutter.entity.custom.MossbloomEntity;
import net.emilsg.clutter.entity.variants.MossbloomVariant;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class MossbloomModel<T extends MossbloomEntity> extends ClutterModel<T> {
    private final ModelPart root;
    private final ModelPart all;
    private final ModelPart neck;
    private final ModelPart leftHorn;
    private final ModelPart rightHorn;


    public MossbloomModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("all");
        this.neck = all.getChild("neck");

        this.leftHorn = neck.getChild("neck_two").getChild("head").getChild("lh");
        this.rightHorn = neck.getChild("neck_two").getChild("head").getChild("rh");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData all = modelPartData.addChild("all", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 11.0F, 0.0F));

        ModelPartData neck = all.addChild("neck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, -6.5F));

        ModelPartData cube_r1 = neck.addChild("cube_r1", ModelPartBuilder.create().uv(26, 0).cuboid(-2.0F, -2.5F, -2.5F, 4.0F, 3.0F, 3.0F, new Dilation(0.25F)), ModelTransform.of(0.0F, 1.0784F, -0.0982F, -0.4363F, 0.0F, 0.0F));

        ModelPartData neck_two = neck.addChild("neck_two", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, -1.0F));

        ModelPartData cube_r2 = neck_two.addChild("cube_r2", ModelPartBuilder.create().uv(40, 13).cuboid(-2.0F, -2.0F, -5.275F, 4.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -1.0F, -1.1345F, 0.0F, 0.0F));

        ModelPartData head = neck_two.addChild("head", ModelPartBuilder.create().uv(27, 11).cuboid(-2.5F, -7.0F, -7.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(52, 0).cuboid(-1.5F, -5.0F, -10.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 37).cuboid(0.0F, -2.0F, -10.0F, 0.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 1.0F));

        ModelPartData le = head.addChild("le", ModelPartBuilder.create(), ModelTransform.pivot(2.5F, -6.0F, -3.5F));

        ModelPartData cube_r3 = le.addChild("cube_r3", ModelPartBuilder.create().uv(24, 21).cuboid(-1.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        ModelPartData re = head.addChild("re", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -6.0F, -3.5F));

        ModelPartData cube_r4 = re.addChild("cube_r4", ModelPartBuilder.create().uv(34, 8).cuboid(-2.0F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        ModelPartData lh = head.addChild("lh", ModelPartBuilder.create().uv(32, 48).mirrored().cuboid(-3.0F, -13.0F, -1.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                .uv(32, 32).mirrored().cuboid(-3.0F, -13.0F, -1.01F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.0F, -7.0F, -4.0F));

        ModelPartData rh = head.addChild("rh", ModelPartBuilder.create().uv(32, 32).cuboid(-13.0F, -13.0F, -1.01F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F))
                .uv(32, 48).cuboid(-13.0F, -13.0F, -1.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -7.0F, -4.0F));

        ModelPartData torso = all.addChild("torso", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -4.0F, -8.5F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(-4.0F, -3.0F, -0.5F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 48).cuboid(-4.0F, -4.0F, -8.5F, 8.0F, 8.0F, 8.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, 0.5F));

        ModelPartData tail = torso.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 6.5F));

        ModelPartData cube_r5 = tail.addChild("cube_r5", ModelPartBuilder.create().uv(36, 2).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData fl = all.addChild("fl", ModelPartBuilder.create().uv(24, 35).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 4.0F, -6.0F));

        ModelPartData fr = all.addChild("fr", ModelPartBuilder.create().uv(16, 31).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 4.0F, -6.0F));

        ModelPartData bl = all.addChild("bl", ModelPartBuilder.create().uv(8, 31).cuboid(-2.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 4.0F, 6.0F));

        ModelPartData br = all.addChild("br", ModelPartBuilder.create().uv(0, 31).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 4.0F, 6.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(MossbloomEntity mossbloom, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(mossbloom, netHeadYaw, headPitch, ageInTicks);
        this.updateVisibleParts(mossbloom);

        this.animateMovement(MossbloomAnimations.MOSSBLOOM_WALK, limbSwing, limbSwingAmount, 1.5f, 2f);

        if (mossbloom.isVariantOf(MossbloomVariant.M)) this.updateAnimation(mossbloom.shakingAnimationState, MossbloomAnimations.MOSSBLOOM_SHAKE_HEAD, ageInTicks, 1f);
        this.updateAnimation(mossbloom.idleAnimationState, MossbloomAnimations.MOSSBLOOM_IDLE, ageInTicks, 1f);

        this.updateAnimation(mossbloom.earTwitchAnimationStateLE, MossbloomAnimations.MOSSBLOOM_LE_DROP, ageInTicks, 2f);
        this.updateAnimation(mossbloom.earTwitchAnimationStateRE, MossbloomAnimations.MOSSBLOOM_RE_DROP, ageInTicks, 2f);
        this.updateAnimation(mossbloom.earTwitchAnimationStateBE, MossbloomAnimations.MOSSBLOOM_EARS_DROP, ageInTicks, 2f);
    }


    private void updateVisibleParts(MossbloomEntity mossbloom) {
        boolean horns = mossbloom.getHasHorns();

        leftHorn.visible = horns && !mossbloom.isBaby();
        rightHorn.visible = horns && !mossbloom.isBaby();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        if (this.child) {
            float babyScale = 0.75f;
            matrices.push();
            matrices.scale(babyScale, babyScale, babyScale);
            matrices.translate(0.0D, 0.5D, 0D);
            this.getPart().render(matrices, vertexConsumer, light, overlay, color);
            matrices.pop();
            this.getHeadPart().scale(createVec3f(1.0f));
        } else {
            matrices.push();
            this.getPart().render(matrices, vertexConsumer, light, overlay, color);
            matrices.pop();
        }
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    protected ModelPart getHeadPart() {
        return neck;
    }
}
