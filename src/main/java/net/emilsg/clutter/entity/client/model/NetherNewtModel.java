package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.NetherNewtAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterModel;
import net.emilsg.clutter.entity.custom.AbstractNetherNewtEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;


public class NetherNewtModel<T extends AbstractNetherNewtEntity> extends ClutterModel<T> {

    private final ModelPart root;
    private final ModelPart all;
    private final ModelPart head;

    private final ModelPart fungi1;
    private final ModelPart fungi2;
    private final ModelPart fungi3;
    private final ModelPart fungi4;
    private final ModelPart fungi5;

    public NetherNewtModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("Torso");
        this.head = all.getChild("Skull");

        this.fungi1 = all.getChild("Mushroom");
        this.fungi2 = all.getChild("Mushroom2");
        this.fungi3 = all.getChild("Mushroom3");
        this.fungi4 = all.getChild("Mushroom4");
        this.fungi5 = all.getChild("Mushroom5");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData Torso = modelPartData.addChild("Torso", ModelPartBuilder.create().uv(13, 4).cuboid(0.0F, -7.0F, -2.0F, 0.0F, 2.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-1.5F, -5.0F, -3.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -5.0F, -3.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.125F)), ModelTransform.pivot(0.0F, 24.0F, -2.0F));

        ModelPartData Skull = Torso.addChild("Skull", ModelPartBuilder.create().uv(3, 15).cuboid(-2.3F, -2.5F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.25F))
                .uv(13, 5).cuboid(1.3F, -2.5F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.25F))
                .uv(13, 0).cuboid(-1.5F, -2.0F, -3.5F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.15F, -1.0F, -3.3F, 2.3F, 0.25F, 2.55F, new Dilation(0.0F))
                .uv(18, 18).cuboid(-2.5F, -2.975F, -4.5F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(13, 12).cuboid(-2.5F, -2.975F, -4.5F, 5.0F, 2.0F, 2.0F, new Dilation(0.125F)), ModelTransform.pivot(0.0F, -4.0F, -3.0F));

        ModelPartData Neck_r1 = Skull.addChild("Neck_r1", ModelPartBuilder.create().uv(0, 25).cuboid(-1.0F, -0.5F, -0.25F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 1.0908F, 0.0F, 0.0F));

        ModelPartData Jaw = Skull.addChild("Jaw", ModelPartBuilder.create().uv(0, 31).cuboid(-1.25F, 0.0F, -2.5F, 2.5F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 2).cuboid(-1.0F, -0.25F, -2.25F, 2.0F, 0.25F, 1.75F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, -1.0F));

        ModelPartData Beard = Jaw.addChild("Beard", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -1.5F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, -1.0F));

        ModelPartData Mushroom = Torso.addChild("Mushroom", ModelPartBuilder.create().uv(19, 29).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(25, 14).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(15, 29).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.9F, -4.75F, 3.5F));

        ModelPartData Mushroom2 = Torso.addChild("Mushroom2", ModelPartBuilder.create().uv(29, 0).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(25, 10).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(27, 28).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.9F, -4.75F, 0.5F));

        ModelPartData Mushroom3 = Torso.addChild("Mushroom3", ModelPartBuilder.create().uv(12, 28).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 25).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(27, 25).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.75F, -1.5F));

        ModelPartData Mushroom4 = Torso.addChild("Mushroom4", ModelPartBuilder.create().uv(27, 6).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 22).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(6, 25).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.9F, -4.75F, 0.5F));

        ModelPartData Mushroom5 = Torso.addChild("Mushroom5", ModelPartBuilder.create().uv(20, 16).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(23, 0).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(17, 5).cuboid(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.9F, -4.75F, 2.5F));

        ModelPartData FL_Leg = Torso.addChild("FL_Leg", ModelPartBuilder.create().uv(8, 28).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.125F)), ModelTransform.of(1.5F, -4.0F, -1.0F, 0.0F, 0.0F, -0.2618F));

        ModelPartData FL_Elbow = FL_Leg.addChild("FL_Elbow", ModelPartBuilder.create().uv(27, 3).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        ModelPartData FR_Leg = Torso.addChild("FR_Leg", ModelPartBuilder.create().uv(24, 26).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.125F)), ModelTransform.of(-1.5F, -4.0F, -1.0F, 0.0F, 0.0F, 0.2618F));

        ModelPartData FR_Elbow = FR_Leg.addChild("FR_Elbow", ModelPartBuilder.create().uv(20, 26).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        ModelPartData BL_Leg = Torso.addChild("BL_Leg", ModelPartBuilder.create().uv(16, 26).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.125F)), ModelTransform.of(1.5F, -4.0F, 3.0F, 0.0F, 0.0F, -0.2618F));

        ModelPartData BL_Elbow = BL_Leg.addChild("BL_Elbow", ModelPartBuilder.create().uv(7, 20).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        ModelPartData BR_Leg = Torso.addChild("BR_Leg", ModelPartBuilder.create().uv(13, 0).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.125F)), ModelTransform.of(-1.5F, -4.0F, 3.0F, 0.0F, 0.0F, 0.2618F));

        ModelPartData BR_Elbow = BR_Leg.addChild("BR_Elbow", ModelPartBuilder.create().uv(0, 13).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        ModelPartData Tail_One = Torso.addChild("Tail_One", ModelPartBuilder.create().uv(0, 2).cuboid(0.0F, -2.25F, 0.175F, 0.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(10, 20).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, -3.3F, 4.0F));

        ModelPartData Tail_Two = Tail_One.addChild("Tail_Two", ModelPartBuilder.create().uv(20, 5).cuboid(-1.0F, -1.0F, -0.35F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 1).cuboid(0.0F, -1.9F, 0.675F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.15F, 3.25F));

        ModelPartData Tail_Three = Tail_Two.addChild("Tail_Three", ModelPartBuilder.create().uv(0, 20).cuboid(-1.0F, -1.0F, -0.3F, 2.0F, 2.0F, 3.0F, new Dilation(-0.25F))
                .uv(2, 1).cuboid(0.0F, -1.7F, 0.675F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.15F, 2.6F));

        ModelPartData Tail_Four = Tail_Three.addChild("Tail_Four", ModelPartBuilder.create().uv(0, 10).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.15F, 2.45F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(AbstractNetherNewtEntity newt, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(newt, netHeadYaw, headPitch, ageInTicks);

        this.animateMovement(NetherNewtAnimations.NETHER_NEWT_WALK, limbSwing, limbSwingAmount, 1.5f, 2f);
        this.updateAnimation(newt.idleAnimationState, NetherNewtAnimations.NETHER_NEWT_IDLE, ageInTicks, 1f);

        updateVisibleParts(newt);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        if (this.child) {
            float babyScale = 0.5f;
            this.head.scale(createVec3f(babyScale));
            matrices.push();
            matrices.scale(babyScale, babyScale, babyScale);
            matrices.translate(0.0D, 1.5f, 0D);
            this.getPart().render(matrices, vertexConsumer, light, overlay, color);
            matrices.pop();
            this.head.scale(createVec3f(0.9f));
        } else {
            matrices.push();
            this.getPart().render(matrices, vertexConsumer, light, overlay, color);
            matrices.pop();
        }
    }

    private void updateVisibleParts(AbstractNetherNewtEntity netherNewtEntity) {
        int fungiCount = netherNewtEntity.getFungiCount();

        fungi1.visible = fungiCount >= 1;
        fungi2.visible = fungiCount >= 4;
        fungi3.visible = fungiCount >= 2;
        fungi4.visible = fungiCount >= 5;
        fungi5.visible = fungiCount >= 3;
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