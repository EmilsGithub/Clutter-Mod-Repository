package net.emilsg.clutter.entity.custom.parent;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ClutterAnimalEntity extends AnimalEntity {
    private static final TrackedData<Boolean> MOVING = DataTracker.registerData(ClutterAnimalEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected ClutterAnimalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
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
            boolean isMoving = velocity.lengthSquared() > 1.0E-7;
            this.setMoving(isMoving);
        }
        super.tickMovement();
    }

    @Override
    public abstract boolean isBreedingItem(ItemStack stack);

    public boolean isMoving() {
        return this.dataTracker.get(MOVING);
    }

    public void setMoving(boolean moving) {
        this.dataTracker.set(MOVING, moving);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
