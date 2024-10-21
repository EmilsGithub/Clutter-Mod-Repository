package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.SeahorseAnimations;
import net.emilsg.clutter.entity.client.model.parent.ClutterFishModel;
import net.emilsg.clutter.entity.custom.SeahorseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class SeahorseModel<T extends SeahorseEntity> extends ClutterFishModel<T> {
    private final ModelPart root;
    private final ModelPart all;
    private final ModelPart body;
    private final ModelPart stomach;
    private final ModelPart head;


    public SeahorseModel(ModelPart root) {
        this.root = root;
        this.all = root.getChild("all");
        this.body = all.getChild("body");
        this.head = body.getChild("head");
        this.stomach = body.getChild("stomach");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData all = modelPartData.addChild("all", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

        ModelPartData body = all.addChild("body", ModelPartBuilder.create().uv(0, 2).cuboid(0.0F, -2.0F, 1.0F, 0.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(4, 2).cuboid(-0.5F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.01F))
                .uv(2, 6).cuboid(-0.5F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData stomach = body.addChild("stomach", ModelPartBuilder.create().uv(6, 7).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.25F)), ModelTransform.pivot(-0.5F, 1.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 2.0F, 2.0F, new Dilation(0.2F))
                .uv(8, 0).cuboid(-0.5F, -1.0F, -3.5F, 1.0F, 1.0F, 2.0F, new Dilation(-0.1F))
                .uv(0, 7).cuboid(0.0F, -3.0F, -0.5F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -0.5F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 5.0F, 2.0F));

        ModelPartData tailOne = tail.addChild("tailOne", ModelPartBuilder.create().uv(10, 3).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(6, 5).cuboid(0.0F, 0.0F, 0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, -1.5F));

        ModelPartData tailTwo = tailOne.addChild("tailTwo", ModelPartBuilder.create().uv(3, 10).cuboid(-0.5F, -0.25F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(-0.25F))
                .uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(SeahorseEntity seahorse, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateParts(seahorse);
        if (seahorse.isTouchingWater() && !seahorse.isDead())
            this.updateAnimation(seahorse.swimmingAnimationState, SeahorseAnimations.SEAHORSE_SWIM, ageInTicks, 1.0f);
        else if (!seahorse.isDead())
            this.updateAnimation(seahorse.flopAnimationState, SeahorseAnimations.SEAHORSE_FLOP, ageInTicks, 1.0f);
    }

    private void updateParts(SeahorseEntity seahorse) {
        boolean hasChildren = seahorse.hasChildren();
        if (hasChildren) {
            this.stomach.scale(createVec3f(seahorse.getHasChildrenTimer()));
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        if (this.child) {
            float babyScale = 0.5f;
            matrices.push();
            matrices.scale(babyScale, babyScale, babyScale);
            matrices.translate(0.0D, 1.5D, 0D);
            this.getPart().render(matrices, vertexConsumer, light, overlay, color);
            matrices.pop();
            this.getHeadPart().scale(createVec3f(0.9f));
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
        return head;
    }
}
