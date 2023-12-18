package net.emilsg.clutter.world.spawner;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.Spawner;

public class ButterflySpawner implements Spawner {

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!spawnMonsters) {
            return 0;
        }
        int count = 0;
        ServerPlayerEntity playerEntity = world.getRandomAlivePlayer();
        if (playerEntity == null) {
            return 0;
        }
        Random random = world.random;
        int i = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        int j = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
        BlockPos blockPos = playerEntity.getBlockPos().add(i, 0, j);
        if (!world.isRegionLoaded(blockPos.getX() - 10, blockPos.getZ() - 10, blockPos.getX() + 10, blockPos.getZ() + 10)) {
            return 0;
        }
        if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, blockPos, ModEntities.BUTTERFLY)) {
            return this.spawn(blockPos, world);
        }
        return count;
    }

    private int spawn(BlockPos pos, ServerWorld world) {
        ButterflyEntity butterflyEntity = ModEntities.BUTTERFLY.create(world);
        if (butterflyEntity == null) {
            return 0;
        }
        butterflyEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
        butterflyEntity.refreshPositionAndAngles(pos, 0.0f, 0.0f);
        world.spawnEntityAndPassengers(butterflyEntity);
        return world.random.nextBetween(1, 5) + 1;
    }
}
