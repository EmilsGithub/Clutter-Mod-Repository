package net.emilsg.clutter.entity.client.model.parent;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.passive.FishEntity;
import org.joml.Vector3f;

public abstract class ClutterFishModel<T extends FishEntity> extends SinglePartEntityModel<T> {

    @Override
    public abstract void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch);

    public Vector3f createVec3f(float scale) {
        return new Vector3f(scale, scale, scale);
    }

    protected abstract ModelPart getHeadPart();
}
