package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.CrabAnimations;
import net.emilsg.clutter.entity.custom.CrabEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class CrabModel<T extends CrabEntity> extends ClutterModel<T> {
	private final ModelPart crab;

	public CrabModel(ModelPart root) {
		this.crab = root.getChild("cluttered_crab");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData cluttered_crab = modelPartData.addChild("cluttered_crab", ModelPartBuilder.create(), ModelTransform.of(1.5F, 26.75F, 1.5F, 0.0F, -1.5708F, 0.0F));

		ModelPartData body = cluttered_crab.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legs = body.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData LeftLegs = legs.addChild("LeftLegs", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, -2.75F, -1.5F));

		ModelPartData LeftLeg1 = LeftLegs.addChild("LeftLeg1", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 2.75F, 1.5F));

		ModelPartData LeftLeg1_r1 = LeftLeg1.addChild("LeftLeg1_r1", ModelPartBuilder.create().uv(8, 9).cuboid(0.0F, 0.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -4.0F, -0.5F, 0.0F, 0.1745F, -0.7854F));

		ModelPartData LeftLeg2 = LeftLegs.addChild("LeftLeg2", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 2.75F, 1.5F));

		ModelPartData LeftLeg2_r1 = LeftLeg2.addChild("LeftLeg2_r1", ModelPartBuilder.create().uv(0, 9).cuboid(0.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.7071F, -3.2929F, 2.5F, 0.0F, 0.1745F, -0.7854F));

		ModelPartData LeftLeg3 = LeftLegs.addChild("LeftLeg3", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 2.75F, 1.5F));

		ModelPartData LeftLeg3_r1 = LeftLeg3.addChild("LeftLeg3_r1", ModelPartBuilder.create().uv(2, 9).cuboid(0.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.7071F, -3.2929F, 4.5F, 0.0F, 0.1745F, -0.7854F));

		ModelPartData RightLegs = legs.addChild("RightLegs", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, -2.75F, -1.5F));

		ModelPartData RightLeg1 = RightLegs.addChild("RightLeg1", ModelPartBuilder.create(), ModelTransform.pivot(-4.2071F, -0.5429F, 1.0F));

		ModelPartData RightLeg1_r1 = RightLeg1.addChild("RightLeg1_r1", ModelPartBuilder.create().uv(10, 9).cuboid(0.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.7854F));

		ModelPartData RightLeg2 = RightLegs.addChild("RightLeg2", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 2.75F, 1.5F));

		ModelPartData RightLeg2_r1 = RightLeg2.addChild("RightLeg2_r1", ModelPartBuilder.create().uv(4, 0).cuboid(0.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.7071F, -3.2929F, 2.5F, 0.0F, -0.1745F, 0.7854F));

		ModelPartData RightLeg3 = RightLegs.addChild("RightLeg3", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 2.75F, 1.5F));

		ModelPartData RightLeg3_r1 = RightLeg3.addChild("RightLeg3_r1", ModelPartBuilder.create().uv(4, 2).cuboid(0.0F, -1.0F, -0.5F, 0.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.7071F, -3.2929F, 4.5F, 0.0F, -0.1745F, 0.7854F));

		ModelPartData MainBody = body.addChild("MainBody", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -2.0F, -3.0F, 7.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -6.0F, 2.0F));

		ModelPartData eyes = body.addChild("eyes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData LeftEye = eyes.addChild("LeftEye", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData LeftEye_r1 = LeftEye.addChild("LeftEye_r1", ModelPartBuilder.create().uv(2, 12).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -9.0F, -1.0F, 0.0443F, -0.1744F, -0.0077F));

		ModelPartData RightEye = eyes.addChild("RightEye", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData RightEye_r1 = RightEye.addChild("RightEye_r1", ModelPartBuilder.create().uv(0, 12).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, -9.0F, -1.0F, 0.0443F, 0.1744F, 0.0077F));

		ModelPartData Claws = body.addChild("Claws", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, -2.75F, -1.5F));

		ModelPartData LeftClaw = Claws.addChild("LeftClaw", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 2.75F, 1.5F));

		ModelPartData TopLeftClaw = LeftClaw.addChild("TopLeftClaw", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData LeftClawFlat2_r1 = TopLeftClaw.addChild("LeftClawFlat2_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.1624F, 0.2366F, -1.0438F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(0.8376F, 0.2366F, -1.0438F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 1).cuboid(-1.1624F, 0.2366F, -1.0438F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-1.1624F, -1.7634F, -1.0438F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.7619F, -6.3415F, -2.8514F, -0.4363F, -0.5236F, 0.0F));

		ModelPartData BottomLeftClaw = LeftClaw.addChild("BottomLeftClaw", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData LeftClawBottom_r1 = BottomLeftClaw.addChild("LeftClawBottom_r1", ModelPartBuilder.create().uv(0, 16).cuboid(6.0F, 0.5F, -6.5F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -5.5F, -2.0F, 0.0F, -0.5236F, 0.0F));

		ModelPartData RightClaw = Claws.addChild("RightClaw", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 2.75F, 1.5F));

		ModelPartData TopRightClaw = RightClaw.addChild("TopRightClaw", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData RightClawFlat2_r1 = TopRightClaw.addChild("RightClawFlat2_r1", ModelPartBuilder.create().uv(0, 1).cuboid(-1.0F, 0.25F, -1.75F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 2).cuboid(-1.0F, 0.25F, -1.75F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 3).cuboid(1.0F, 0.25F, -1.75F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(8, 12).cuboid(-1.0F, -1.75F, -1.75F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-6.366F, -6.0F, -2.366F, -0.4363F, 0.5236F, 0.0F));

		ModelPartData BottomRightClaw = RightClaw.addChild("BottomRightClaw", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData RightClawBottom_r1 = BottomRightClaw.addChild("RightClawBottom_r1", ModelPartBuilder.create().uv(16, 10).cuboid(-2.0F, 0.5F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -5.5F, -2.0F, 0.0F, 0.5236F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(CrabAnimations.CRAB_WALKING, limbAngle, limbDistance, 1.5f, 2f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		crab.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return crab;
	}
}