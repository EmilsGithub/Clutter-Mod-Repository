package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.client.animation.EmberTortoiseAnimations;
import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class EmberTortoiseModel<T extends EmberTortoiseEntity> extends ClutterModel<T> {
	private final ModelPart all;
	private final ModelPart neck;
	private final ModelPart head;

	public EmberTortoiseModel(ModelPart root) {
		this.all = root.getChild("all");
		this.neck = all.getChild("body").getChild("neck");
		this.head = neck.getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData all = modelPartData.addChild("all", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = all.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -10.0F, -12.0F, 20.0F, 4.0F, 24.0F, new Dilation(0.0F))
				.uv(7, 30).cuboid(-8.0F, -6.0F, -10.0F, 16.0F, 1.0F, 20.0F, new Dilation(0.0F))
				.uv(64, 41).cuboid(-6.0F, -14.0F, -12.0F, 12.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 55).cuboid(-9.0F, -16.0F, -11.0F, 18.0F, 6.0F, 22.0F, new Dilation(0.0F))
				.uv(0, 91).cuboid(-8.0F, -19.0F, -10.0F, 16.0F, 3.0F, 20.0F, new Dilation(0.0F))
				.uv(16, 83).cuboid(-8.0F, -23.0F, 5.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 67).cuboid(4.0F, -23.0F, -8.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 55).cuboid(4.0F, -27.0F, 5.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 13).cuboid(-7.0F, -24.0F, -9.0F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 83).cuboid(-1.0F, -26.0F, -9.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F))
				.uv(82, 22).cuboid(-3.0F, -25.0F, 4.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F))
				.uv(58, 55).cuboid(-8.0F, -27.0F, -3.0F, 7.0F, 8.0F, 7.0F, new Dilation(0.0F))
				.uv(80, 64).cuboid(1.0F, -25.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData fr_leg = body.addChild("fr_leg", ModelPartBuilder.create().uv(64, 28).cuboid(-3.0F, -0.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.25F, -6.5F, -9.25F));

		ModelPartData br_leg = body.addChild("br_leg", ModelPartBuilder.create().uv(0, 28).cuboid(-3.0F, -0.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.25F, -6.5F, 9.25F));

		ModelPartData fl_leg = body.addChild("fl_leg", ModelPartBuilder.create().uv(74, 77).cuboid(-3.0F, -0.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(7.25F, -6.5F, -9.25F));

		ModelPartData bl_leg = body.addChild("bl_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -0.5F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(7.25F, -6.5F, 9.25F));

		ModelPartData neck = body.addChild("neck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -8.0F, -12.0F));

		ModelPartData throat = neck.addChild("throat", ModelPartBuilder.create().uv(0, 41).cuboid(-3.0F, -2.5F, -2.0F, 6.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.4239F, -1.3827F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(64, 11).cuboid(-4.0F, -3.0F, -7.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, -1.25F, -4.0F));

		ModelPartData lower_jaw = head.addChild("lower_jaw", ModelPartBuilder.create().uv(64, 0).cuboid(-4.0F, -1.0F, -7.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(EmberTortoiseEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(entity, netHeadYaw, headPitch, ageInTicks);

		if(!entity.isShielding()) this.animateMovement(EmberTortoiseAnimations.EMBER_TORTOISE_WALK, limbSwing, limbSwingAmount, 3f, 2f);
		if(entity.isShielding()) this.updateAnimation(entity.shieldingAnimationState, EmberTortoiseAnimations.EMBER_TORTOISE_SHIELD, ageInTicks, 1f );
		this.updateAnimation(entity.attackAnimationState, EmberTortoiseAnimations.EMBER_TORTOISE_ATTACK, ageInTicks, 1f );
	}

	private void setHeadAngles(EmberTortoiseEntity entity, float headYaw, float headPitch, float animationProgress) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.neck.yaw = headYaw * 0.017453292F;
		this.neck.pitch = headPitch * 0.017453292F;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		if (this.child) {
			float babyScale = 0.5f;
			this.head.scale(createVec3f(babyScale));
			matrices.push();
			matrices.scale(babyScale, babyScale, babyScale);
			matrices.translate(0.0D, 1.5f, 0D);
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
		return all;
	}
}