package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.entity.ModEntityTypes;
import net.emilsg.clutter.entity.custom.*;
import net.emilsg.clutter.mixin.ServerWorldAccessor;
import net.emilsg.clutter.util.ModBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
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

        if (ModConfigManager.get(Configs.spawnEchofins, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_ECHOFINS), SpawnGroup.AMBIENT,
                    ModEntityTypes.ECHOFIN, 30, 1, 3);
        }

        if (ModConfigManager.get(Configs.spawnCrimsonNewts, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_CRIMSON_NEWTS), SpawnGroup.CREATURE,
                    ModEntityTypes.CRIMSON_NEWT, 60, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnWarpedNewts, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_WARPED_NEWTS), SpawnGroup.CREATURE,
                    ModEntityTypes.WARPED_NEWT, 60, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnEmberTortoises, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_EMBER_TORTOISES), SpawnGroup.CREATURE,
                    ModEntityTypes.EMBER_TORTOISE, 60, 1, 2);
        }

        if (ModConfigManager.get(Configs.spawnButterflies, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_BUTTERFLIES), SpawnGroup.CREATURE,
                    ModEntityTypes.BUTTERFLY, 20, 2, 4);
        }

        if (ModConfigManager.get(Configs.spawnChameleons, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                    ModEntityTypes.CHAMELEON, 15, 1, 2);
        }

        if (ModConfigManager.get(Configs.spawnMossblooms, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(LUSH_CAVES), SpawnGroup.AMBIENT,
                    ModEntityTypes.MOSSBLOOM, 30, 1, 2);
        }

        if (ModConfigManager.get(Configs.spawnKiwis, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                    ModEntityTypes.KIWI_BIRD, 30, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnEmperorPenguins, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ICE_SPIKES, SNOWY_PLAINS, SNOWY_BEACH), SpawnGroup.CREATURE,
                    ModEntityTypes.EMPEROR_PENGUIN, 5, 2, 4);
        }

        if (ModConfigManager.get(Configs.spawnBeavers, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.includeByKey(RIVER), SpawnGroup.CREATURE,
                    ModEntityTypes.BEAVER, 5, 2, 3);
        }

        if (ModConfigManager.get(Configs.spawnCapybaras, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_SAVANNA), SpawnGroup.CREATURE,
                    ModEntityTypes.CAPYBARA, 5, 3, 5);
        }
        if (ModConfigManager.get(Configs.spawnJellyfishes, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_JELLYFISHES), SpawnGroup.WATER_AMBIENT,
                    ModEntityTypes.JELLYFISH, 6, 5, 9);
        }

        if (ModConfigManager.get(Configs.spawnSeahorses, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_SEAHORSES), SpawnGroup.WATER_AMBIENT,
                    ModEntityTypes.SEAHORSE, 20, 4, 7);
        }

        if (ModConfigManager.get(Configs.spawnMantaRays, true)) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModBiomeTags.SPAWNS_MANTA_RAYS), SpawnGroup.WATER_CREATURE,
                    ModEntityTypes.MANTA_RAY, 20, 1, 3);
        }

        SpawnRestriction.register(ModEntityTypes.MOSSBLOOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MossbloomEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.CHAMELEON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChameleonEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.KIWI_BIRD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KiwiBirdEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.EMPEROR_PENGUIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmperorPenguinEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.BEAVER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeaverEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.CAPYBARA, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CapybaraEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.JELLYFISH, SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, JellyfishEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.SEAHORSE, SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, SeahorseEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.MANTA_RAY, SpawnRestriction.Location.IN_WATER, Heightmap.Type.OCEAN_FLOOR, MantaRayEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntityTypes.BUTTERFLY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::isValidSpawn);
        SpawnRestriction.register(ModEntityTypes.CRIMSON_NEWT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrimsonNewtEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.WARPED_NEWT, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarpedNewtEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.EMBER_TORTOISE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmberTortoiseEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntityTypes.ECHOFIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EchofinEntity::isValidSpawn);

    }

    private static void registerSpawners(ServerWorld world, Spawner spawner) {
        ServerWorldAccessor serverWorldAccessor = (ServerWorldAccessor) world;

        List<Spawner> worldSpawners = new ArrayList<>(((serverWorldAccessor).getWorldSpawners()));
        worldSpawners.add(spawner);
        (serverWorldAccessor).setWorldSpawners(worldSpawners);
    }
}
