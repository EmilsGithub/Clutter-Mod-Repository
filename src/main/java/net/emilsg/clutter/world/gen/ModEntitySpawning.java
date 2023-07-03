package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.*;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawning {
    public static void addSpawns() {
        if(ModConfigs.SPAWN_CLUTTER_MOBS) {

            if (ModConfigs.SPAWN_BUTTERFLIES) {
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.MEADOW, BiomeKeys.CHERRY_GROVE), SpawnGroup.AMBIENT,
                        ModEntities.BUTTERFLY, 20, 2, 4);

                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.WARPED_FOREST, BiomeKeys.CRIMSON_FOREST, BiomeKeys.SOUL_SAND_VALLEY), SpawnGroup.AMBIENT,
                        ModEntities.BUTTERFLY, 5, 1, 3);
            }

            if (ModConfigs.SPAWN_CHAMELEONS) {
                BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                        ModEntities.CHAMELEON, 15, 1, 2);
            }

            if (ModConfigs.SPAWN_ECHOFINS) {
                BiomeModifications.addSpawn(BiomeSelectors.excludeByKey(BiomeKeys.SMALL_END_ISLANDS).and(BiomeSelectors.foundInTheEnd()), SpawnGroup.AMBIENT,
                        ModEntities.ECHOFIN, 2, 3, 7);
            }

            if (ModConfigs.SPAWN_MOSSBLOOMS) {
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES), SpawnGroup.AMBIENT,
                        ModEntities.MOSSBLOOM, 30, 1, 2);
            }

            if (ModConfigs.SPAWN_KIWI_BIRDS) {
                BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                        ModEntities.KIWI_BIRD, 30, 2, 3);
            }
        }
        SpawnRestriction.register(ModEntities.MOSSBLOOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MossbloomEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.ECHOFIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EchofinEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.CHAMELEON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChameleonEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.BUTTERFLY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.KIWI_BIRD, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KiwiBirdEntity::isValidNaturalSpawn);
    }
}
