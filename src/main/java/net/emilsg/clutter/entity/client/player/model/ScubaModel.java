package net.emilsg.clutter.entity.client.player.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

@Environment(value = EnvType.CLIENT)
public class ScubaModel<T extends LivingEntity> extends AnimalModel<T> {
    private final ModelPart tank;

    public ScubaModel(ModelPart root) {
        this.tank = root.getChild("tank");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData tank = modelPartData.addChild("tank", ModelPartBuilder.create().uv(16, 14).cuboid(-4.25F, -2.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(0.25F, -2.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 7).cuboid(-5.0F, 6.0F, 1.5F, 10.0F, 2.0F, 5.0F, new Dilation(-0.25F))
                .uv(0, 0).cuboid(-5.0F, 1.0F, 1.5F, 10.0F, 2.0F, 5.0F, new Dilation(-0.25F))
                .uv(12, 14).cuboid(-3.25F, 3.5F, 5.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 25.0F, 0.5F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.tank);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.tank.yaw = 0.0f;

        if (entity.isSneaking() && (entity instanceof PlayerEntity player && !player.getAbilities().flying) && !entity.getPose().equals(EntityPose.FALL_FLYING)) {
            this.tank.pitch = 0.5f;
            this.tank.pivotY = 3.2f;
        } else {
            this.tank.pitch = 0.0f;
            this.tank.pivotY = 0.0f;
        }
    }
}
