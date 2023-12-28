package net.emilsg.clutter.world.spawner;

import net.emilsg.clutter.entity.custom.parent.ClutterAnimalEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.spawner.Spawner;

public class NetherAnimalSpawner implements Spawner {
    private int cooldown;
    private final int maxCooldown;
    private final TagKey<Biome> biomeTagKey;
    private final EntityType animalEntityType;

    public NetherAnimalSpawner(int maxCooldownInTicks, TagKey<Biome> biomeTagKey, EntityType animalEntityType) {
        this.maxCooldown = maxCooldownInTicks;
        this.biomeTagKey = biomeTagKey;
        this.animalEntityType = animalEntityType;
    }

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        if (!world.getPlayers().isEmpty()) {
            if (!world.getDimensionKey().equals(DimensionTypes.THE_NETHER)) {
                return 0;
            }

            if (!spawnMonsters) {
                return 0;
            }

            ServerPlayerEntity playerEntity = world.getRandomAlivePlayer();
            if (playerEntity == null) {
                return 0;
            }

            if (playerEntity.isSpectator() || world.isNearOccupiedPointOfInterest(playerEntity.getBlockPos(), 2)) {
                return 0;
            }

            this.cooldown--;

            Random random = world.random;
            int spawnDistance = 24 + random.nextInt(41);
            double angle = random.nextDouble() * Math.PI * 2;
            int x = playerEntity.getBlockPos().getX() + (int) (spawnDistance * Math.cos(angle));
            int z = playerEntity.getBlockPos().getZ() + (int) (spawnDistance * Math.sin(angle));
            int maxY = 120;
            int minY = 31;

            int y = minY + random.nextInt(maxY - minY + 1);

            BlockPos spawnPos = new BlockPos(x, y, z);

            if (this.cooldown > 0) {
                return 0;
            }

            int passiveMobCount = world.getEntitiesByClass(ClutterAnimalEntity.class, new Box(playerEntity.getBlockPos()).expand(64), e -> true).size();
            if (passiveMobCount >= 17) {
                return 0;
            }

            if (!world.isRegionLoaded(spawnPos.getX() - 10, spawnPos.getZ() - 10, spawnPos.getX() + 10, spawnPos.getZ() + 10)) {
                return 0;
            }

            if (!world.getBiome(spawnPos).isIn(biomeTagKey)) {
                return 0;
            }

            BlockState blockState = world.getBlockState(spawnPos);
            FluidState fluidState = world.getFluidState(spawnPos);

            if (!SpawnHelper.isClearForSpawn(world, spawnPos, blockState, fluidState, animalEntityType)) {
                return 0;
            }

            AnimalEntity animalEntity = (AnimalEntity) animalEntityType.create(world);

            if (animalEntity == null) {
                return 0;
            }

            if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, spawnPos, animalEntityType)) {
                this.cooldown = maxCooldown;
                return this.spawn(spawnPos, world, animalEntity);
            }
        }

        return 0;
    }


    private int spawn(BlockPos pos, ServerWorld world, AnimalEntity animalEntity) {
        if (animalEntity == null) {
            return 0;
        }
        animalEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.NATURAL, null, null);
        animalEntity.refreshPositionAndAngles(pos, 0.0f, 0.0f);
        world.spawnEntityAndPassengers(animalEntity);
        return world.random.nextBetween(2, 5) + 1;
    }
}