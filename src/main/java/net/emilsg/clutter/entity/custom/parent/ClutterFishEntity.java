package net.emilsg.clutter.entity.custom.parent;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ClutterFishEntity extends FishEntity {
    private static final TrackedData<Boolean> MOVING = DataTracker.registerData(ClutterFishEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected ClutterFishEntity(EntityType<? extends FishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(MOVING, false);
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

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.01F, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.7));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }

    }

    public boolean getHasSelfControl() {
        return hasSelfControl();
    }

    @Override
    protected abstract SoundEvent getFlopSound();

    @Override
    public abstract ItemStack getBucketItem();

    public boolean isMoving() {
        return this.dataTracker.get(MOVING);
    }

    public void setMoving(boolean moving) {
        this.dataTracker.set(MOVING, moving);
    }

}
