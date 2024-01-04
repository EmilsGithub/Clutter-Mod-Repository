package net.emilsg.clutter.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SyncPetsC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        Vec3d cameraPos = player.getCameraPosVec(1.0F);
        Vec3d rotationVec = player.getRotationVec(1.0F);
        Vec3d viewVec = cameraPos.add(rotationVec.multiply(10, 10, 10));

        List<Entity> nearbyEntities = player.getWorld().getOtherEntities(player, player.getBoundingBox().expand(3));
        Entity closestEntity = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : nearbyEntities) {
            Optional<Vec3d> raycastResult = entity.getBoundingBox().raycast(cameraPos, viewVec);
            if (raycastResult.isPresent()) {
                double distance = cameraPos.squaredDistanceTo(raycastResult.get());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        if (closestEntity != null) {
            Entity finalClosestEntity = closestEntity;
            player.sendMessage(Text.of("You pet " + finalClosestEntity.getName().getString()));
            server.execute(() -> {
                ParticleEffect particleEffect = ParticleTypes.HEART;

                if(finalClosestEntity instanceof PassiveEntity passiveEntity) {
                    passiveEntity.playAmbientSound();
                }

                Box hitbox = finalClosestEntity.getBoundingBox();
                double hitboxWidth = hitbox.getXLength();
                double hitboxHeight = hitbox.getYLength();
                double hitboxDepth = hitbox.getZLength();

                for (int i = 0; i < (int)(10 * finalClosestEntity.getWidth()); i++) {
                    Random random = new Random();

                    double offsetX = hitbox.minX + (hitboxWidth * random.nextDouble());
                    double offsetY = hitbox.minY + (hitboxHeight * random.nextDouble());
                    double offsetZ = hitbox.minZ + (hitboxDepth * random.nextDouble());

                    player.getServerWorld().spawnParticles(particleEffect, offsetX, offsetY, offsetZ, 1, 0, 0, 0, 0.0);
                }

            });
        }

    }
}
