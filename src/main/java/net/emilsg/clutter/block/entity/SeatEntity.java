package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.custom.SeatBlock;
import net.emilsg.clutter.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;

public class SeatEntity extends Entity {

    public static final HashMap<Vec3d, BlockPos> IS_OCCUPIED = new HashMap<>();

    public SeatEntity(EntityType<? extends Entity> type, World world) {
        super(ModEntities.SEAT, world);
    }

    public SeatEntity(World world) {
        super(ModEntities.SEAT, world);
        noClip = true;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        if (passenger instanceof PlayerEntity) {
            int posX = this.getBlockPos().getX();
            int posY = this.getBlockPos().getY();
            int posZ = this.getBlockPos().getZ();

            BlockState state = this.getBlockStateAtPos();
            Direction direction = state.get(Properties.HORIZONTAL_FACING);

            double zOffset = 0;
            double xOffset = 0;

            switch (direction) {
                case NORTH -> zOffset = -0.125f;
                case EAST -> xOffset = 0.125f;
                case SOUTH -> zOffset = 0.125f;
                default -> xOffset = -0.125f;
            }

            BlockPos pos = IS_OCCUPIED.remove(new Vec3d(posX, posY, posZ));
            if (pos != null) {
                remove(RemovalReason.DISCARDED);
                return new Vec3d(posX + 0.5f + xOffset, posY + 1.0f, posZ + 0.5f + zOffset);
            }
        }

        remove(RemovalReason.DISCARDED);
        return super.updatePassengerForDismount(passenger);
    }

    @Override
    public void tick() {
        if (!this.getWorld().isClient && !(!this.getPassengerList().isEmpty() || !(this.getWorld().getBlockState(this.getBlockPos()).getBlock() instanceof SeatBlock))) {
            remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        IS_OCCUPIED.remove(getPos());
        super.remove(reason);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
    }


}
