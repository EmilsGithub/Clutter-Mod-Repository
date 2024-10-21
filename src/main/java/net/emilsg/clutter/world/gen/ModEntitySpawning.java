package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.*;
import net.emilsg.clutter.util.ModBiomeTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;

import static net.minecraft.world.biome.BiomeKeys.*;

public class ModEntitySpawning {
    public static void addSpawns() {

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

        SpawnRestriction.register(ModEntities.MOSSBLOOM, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MossbloomEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CHAMELEON, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChameleonEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.KIWI_BIRD, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KiwiBirdEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.EMPEROR_PENGUIN, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmperorPenguinEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.BEAVER, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeaverEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAPYBARA, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CapybaraEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.JELLYFISH, SpawnLocationTypes.IN_WATER, Heightmap.Type.OCEAN_FLOOR, JellyfishEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.SEAHORSE, SpawnLocationTypes.IN_WATER, Heightmap.Type.OCEAN_FLOOR, SeahorseEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.MANTA_RAY, SpawnLocationTypes.IN_WATER, Heightmap.Type.OCEAN_FLOOR, MantaRayEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntities.BUTTERFLY, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.CRIMSON_NEWT, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrimsonNewtEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.WARPED_NEWT, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WarpedNewtEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.EMBER_TORTOISE, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmberTortoiseEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.ECHOFIN, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EchofinEntity::isValidSpawn);

    }
}
