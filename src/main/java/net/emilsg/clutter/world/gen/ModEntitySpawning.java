package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.*;
import net.emilsg.clutter.mixin.ServerWorldAccessor;
import net.emilsg.clutter.util.ModBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.spawner.Spawner;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

public class ModEntitySpawning {
    public static void addSpawns() {

        ServerWorldEvents.LOAD.register((server, world) -> {
            if (world.isClient) return;

            //if(world.getDimensionKey().equals(DimensionTypes.THE_NETHER)) {
            //    if (ClutterConfig.getInstance().getBoolean(SPAWN_CRIMSON_NEWTS)) registerSpawners(world, new NetherAnimalSpawner(400, ModBiomeTags.SPAWNS_CRIMSON_NEWTS, ModEntities.CRIMSON_NEWT));
            //    if (ClutterConfig.getInstance().getBoolean(SPAWN_WARPED_NEWTS)) registerSpawners(world, new NetherAnimalSpawner(400, ModBiomeTags.SPAWNS_WARPED_NEWTS, ModEntities.WARPED_NEWT));
            //    if (ClutterConfig.getInstance().getBoolean(SPAWN_BUTTERFLIES)) registerSpawners(world, new NetherButterflySpawner(400, ModBiomeTags.SPAWNS_NETHER_BUTTERFLIES));
            //    if (ClutterConfig.getInstance().getBoolean(SPAWN_EMBER_TORTOISES)) registerSpawners(world, new NetherAnimalSpawner(400, ModBiomeTags.SPAWNS_EMBER_TORTOISES, ModEntities.EMBER_TORTOISE));
            //}

            //if (world.getDimensionKey().equals(DimensionTypes.THE_END)) {
            //    if (ModConfigManager.get(Configs.spawnClutterMobs, true))
            //        registerSpawners(world, new EndAnimalSpawner(400, BiomeTags.IS_END, ModEntities.ECHOFIN));
            //}
        });

        if (ModConfigManager.get(Configs.spawnEchofins, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_ECHOFINS), SpawnGroup.CREATURE,
                    ModEntities.ECHOFIN, 30, 1, 3);
        }

        if (ModConfigManager.get(Configs.spawnCrimsonNewts, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_CRIMSON_NEWTS), SpawnGroup.CREATURE,
                    ModEntities.CRIMSON_NEWT, 60, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnWarpedNewts, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_WARPED_NEWTS), SpawnGroup.CREATURE,
                    ModEntities.WARPED_NEWT, 60, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnEmberTortoises, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_WARPED_NEWTS), SpawnGroup.CREATURE,
                    ModEntities.EMBER_TORTOISE, 60, 1, 2);
        }

        if (ModConfigManager.get(Configs.spawnButterflies, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(FLOWER_FOREST, SUNFLOWER_PLAINS, MEADOW, CHERRY_GROVE), SpawnGroup.CREATURE,
                    ModEntities.BUTTERFLY, 20, 2, 4);
        }

        if (ModConfigManager.get(Configs.spawnChameleons, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                    ModEntities.CHAMELEON, 15, 1, 2);
        }

        if (ModConfigManager.get(Configs.spawnMossblooms, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(LUSH_CAVES), SpawnGroup.AMBIENT,
                    ModEntities.MOSSBLOOM, 30, 1, 2);
        }

        if (ModConfigManager.get(Configs.spawnKiwis, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                    ModEntities.KIWI_BIRD, 30, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnEmperorPenguins, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ICE_SPIKES, SNOWY_PLAINS, SNOWY_BEACH), SpawnGroup.CREATURE,
                    ModEntities.EMPEROR_PENGUIN, 5, 2, 4);
        }

        if (ModConfigManager.get(Configs.spawnBeavers, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(RIVER), SpawnGroup.CREATURE,
                    ModEntities.BEAVER, 5, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnCapybaras, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_SAVANNA), SpawnGroup.CREATURE,
                    ModEntities.CAPYBARA, 5, 3, 5);
        }
        if (ModConfigManager.get(Configs.spawnJellyfishes, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_JELLYFISHES), SpawnGroup.WATER_AMBIENT,
                    ModEntities.JELLYFISH, 6, 5, 9);
        }

        if (ModConfigManager.get(Configs.spawnSeahorses, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_SEAHORSES), SpawnGroup.WATER_AMBIENT,
                    ModEntities.SEAHORSE, 20, 4, 7);
        }

        if (ModConfigManager.get(Configs.spawnMantaRays, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_MANTA_RAYS), SpawnGroup.WATER_CREATURE,
                    ModEntities.MANTA_RAY, 20, 1, 3);
        }

        SpawnRestriction.register(ModEntities.MOSSBLOOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MossbloomEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CHAMELEON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChameleonEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.KIWI_BIRD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KiwiBirdEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.EMPEROR_PENGUIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmperorPenguinEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.BEAVER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeaverEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAPYBARA, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CapybaraEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.JELLYFISH, SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, JellyfishEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.SEAHORSE, SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, SeahorseEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.MANTA_RAY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, MantaRayEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntities.BUTTERFLY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.CRIMSON_NEWT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrimsonNewtEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.WARPED_NEWT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarpedNewtEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.EMBER_TORTOISE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmberTortoiseEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.ECHOFIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EchofinEntity::isValidSpawn);

    }

    private static void registerSpawners(ServerWorld world, Spawner spawner) {
        ServerWorldAccessor serverWorldAccessor = (ServerWorldAccessor) world;

        List<Spawner> worldSpawners = new ArrayList<>(((serverWorldAccessor).getWorldSpawners()));
        worldSpawners.add(spawner);
        (serverWorldAccessor).setWorldSpawners(worldSpawners);
    }
}
