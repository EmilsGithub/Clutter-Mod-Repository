package net.emilsg.clutter.entity.client.model;

import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import org.joml.Vector3f;

public abstract class ClutterModel<T extends ClutterAnimalEntity> extends SinglePartEntityModel<T> {

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    public Vector3f createVec3f(float scale) {
        return new Vector3f(scale, scale, scale);
    }
}
