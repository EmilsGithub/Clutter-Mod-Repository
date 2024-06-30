package net.emilsg.clutter.entity.custom.parent;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ClutterWaterEntity extends WaterCreatureEntity {
    private static final TrackedData<Boolean> MOVING = DataTracker.registerData(ClutterWaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected ClutterWaterEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(MOVING, false);
    }

    @Override
    public void tickMovement() {
        if (!this.getWorld().isClient) {
            Vec3d velocity = this.getVelocity();
            boolean isMoving = velocity.lengthSquared() > 0.0005f;
            this.setMoving(isMoving);
        }
        super.tickMovement();
    }

    public boolean isMoving() {
        return this.dataTracker.get(MOVING);
    }

    public void setMoving(boolean moving) {
        this.dataTracker.set(MOVING, moving);
    }
}
