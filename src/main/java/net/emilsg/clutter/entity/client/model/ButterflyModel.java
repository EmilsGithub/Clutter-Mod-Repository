package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.ButterflyAnimations;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class ButterflyModel<T extends ButterflyEntity> extends ClutterModel<T> {
	private final ModelPart all;

	public ButterflyModel(ModelPart root) {
		this.all = root.getChild("All");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData All = modelPartData.addChild("All", ModelPartBuilder.create(), ModelTransform.of(0.0F, 20.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData Body = All.addChild("Body", ModelPartBuilder.create().uv(3, 2).cuboid(0.25F, -1.8848F, -0.3293F, 0.5F, 4.0F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(-0.5F, -0.1152F, -0.4207F));

		ModelPartData cube_r1 = Body.addChild("cube_r1", ModelPartBuilder.create().uv(4, 2).cuboid(0.0F, -1.0F, 0.0F, 0.25F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.25F, -1.75F, -0.25F, 0.2182F, 0.0F, -0.2182F));

		ModelPartData cube_r2 = Body.addChild("cube_r2", ModelPartBuilder.create().uv(4, 2).cuboid(-0.25F, -1.0F, 0.0F, 0.25F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.75F, -1.75F, -0.25F, 0.2182F, 0.0F, 0.2182F));

		ModelPartData L_Wing = All.addChild("L_Wing", ModelPartBuilder.create().uv(0, 8).mirrored().cuboid(-0.05F, -3.8848F, -0.0793F, 4.0F, 8.0F, 0.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.pivot(0.25F, -0.1152F, -0.4207F));

		ModelPartData R_Wing = All.addChild("R_Wing", ModelPartBuilder.create().uv(0, 8).cuboid(-3.95F, -3.8848F, -0.0793F, 4.0F, 8.0F, 0.0F, new Dilation(0.01F)), ModelTransform.pivot(-0.25F, -0.1152F, -0.4207F));
		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void setAngles(ButterflyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.flyingAnimState, ButterflyAnimations.BUTTERFLY_FLYING, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		all.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return all;
	}
}