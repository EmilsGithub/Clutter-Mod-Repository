package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.custom.parent.ClutterTameableEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

public abstract class TameableClutterModel<T extends ClutterTameableEntity> extends SinglePartEntityModel<T> {
    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    public Vector3f createVec3f(float scale) {
        return new Vector3f(scale, scale, scale);
    }

    protected void setHeadAngles(LivingEntity entity, float headYaw, float headPitch, float animationProgress) {
        if(getHeadPart() == null) return;
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        getHeadPart().yaw = headYaw * 0.017453292F;
        getHeadPart().pitch = headPitch * 0.017453292F;
    }

    protected abstract ModelPart getHeadPart();
}
