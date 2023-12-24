package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.*;
import net.emilsg.clutter.mixin.ServerWorldAccessor;
import net.emilsg.clutter.util.ModBiomeTags;
import net.emilsg.clutter.world.spawner.EndAnimalSpawner;
import net.emilsg.clutter.world.spawner.NetherAnimalSpawner;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.spawner.Spawner;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.biome.BiomeKeys.*;

public class ModEntitySpawning {
    public static void addSpawns() {

        ServerWorldEvents.LOAD.register((server, world) -> {
            if(world.isClient) return;

            if(world.getDimensionKey().equals(DimensionTypes.THE_NETHER)) {
                if (ModConfigs.SPAWN_CRIMSON_NEWTS) registerSpawners(world, new NetherAnimalSpawner(400, ModBiomeTags.SPAWNS_CRIMSON_NEWTS, ModEntities.CRIMSON_NEWT));
                if (ModConfigs.SPAWN_BUTTERFLIES) registerSpawners(world, new NetherAnimalSpawner(400, ModBiomeTags.SPAWNS_NETHER_BUTTERFLIES, ModEntities.BUTTERFLY));
            }

            if(world.getDimensionKey().equals(DimensionTypes.THE_END)) {
                if (ModConfigs.SPAWN_ECHOFINS) registerSpawners(world, new EndAnimalSpawner(400, BiomeTags.IS_END, ModEntities.ECHOFIN));
            }

        });

            if (ModConfigs.SPAWN_BUTTERFLIES) {
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(FLOWER_FOREST, SUNFLOWER_PLAINS, MEADOW, CHERRY_GROVE), SpawnGroup.CREATURE,
                        ModEntities.BUTTERFLY, 20, 2, 4);

                //BiomeModifications.addSpawn(BiomeSelectors.includeByKey(WARPED_FOREST, CRIMSON_FOREST, SOUL_SAND_VALLEY), SpawnGroup.AMBIENT,
                //        ModEntities.BUTTERFLY, 5, 1, 3);
            }

            if (ModConfigs.SPAWN_CHAMELEONS) {
                BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                        ModEntities.CHAMELEON, 15, 1, 2);
            }

            //if (ModConfigs.SPAWN_ECHOFINS) {
            //    BiomeModifications.addSpawn(BiomeSelectors.excludeByKey(SMALL_END_ISLANDS).and(BiomeSelectors.foundInTheEnd()), SpawnGroup.AMBIENT,
            //            ModEntities.ECHOFIN, 2, 3, 7);
            //}

            if (ModConfigs.SPAWN_MOSSBLOOMS) {
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(LUSH_CAVES), SpawnGroup.AMBIENT,
                        ModEntities.MOSSBLOOM, 30, 1, 2);
            }

            if (ModConfigs.SPAWN_KIWI_BIRDS) {
                BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                        ModEntities.KIWI_BIRD, 30, 2, 3);
            }

            if (ModConfigs.SPAWN_EMPEROR_PENGUINS) {
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ICE_SPIKES, SNOWY_PLAINS, SNOWY_BEACH), SpawnGroup.CREATURE,
                        ModEntities.EMPEROR_PENGUIN, 5, 2, 4);
            }

            if (ModConfigs.SPAWN_BEAVERS) {
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(RIVER), SpawnGroup.CREATURE,
                        ModEntities.BEAVER, 5, 2, 3);
            }

            if (ModConfigs.SPAWN_CAPYBARAS) {
                BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_SAVANNA), SpawnGroup.CREATURE,
                        ModEntities.CAPYBARA, 5, 3, 5);
            }

        SpawnRestriction.register(ModEntities.MOSSBLOOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MossbloomEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.ECHOFIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EchofinEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.CHAMELEON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChameleonEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.BUTTERFLY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.KIWI_BIRD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KiwiBirdEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.EMPEROR_PENGUIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EmperorPenguinEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.BEAVER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeaverEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.CAPYBARA, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CapybaraEntity::isValidNaturalSpawn);
    }

    private static void registerSpawners(ServerWorld world, Spawner spawner) {
        ServerWorldAccessor serverWorldAccessor = (ServerWorldAccessor) world;

        List<Spawner> worldSpawners = new ArrayList<>(((serverWorldAccessor).getWorldSpawners()));
        worldSpawners.add(spawner);
        (serverWorldAccessor).setWorldSpawners(worldSpawners);
    }
}
