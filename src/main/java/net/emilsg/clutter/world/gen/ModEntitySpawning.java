package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.custom.ChameleonEntity;
import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.emilsg.clutter.entity.custom.MossbloomEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

public class ModEntitySpawning {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.MEADOW), SpawnGroup.CREATURE,
                ModEntities.BUTTERFLY, 30, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.foundInTheNether(), SpawnGroup.CREATURE,
                ModEntities.BUTTERFLY, 60, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_JUNGLE), SpawnGroup.CREATURE,
                ModEntities.CHAMELEON, 30, 1, 2);

        BiomeModifications.addSpawn(BiomeSelectors.foundInTheEnd(), SpawnGroup.CREATURE,
                ModEntities.ECHOFIN, 20, 3, 7);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES), SpawnGroup.CREATURE,
                ModEntities.MOSSBLOOM, 80, 1, 2);

        SpawnRestriction.register(ModEntities.BUTTERFLY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.ECHOFIN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EchofinEntity::isValidSpawn);
        SpawnRestriction.register(ModEntities.CHAMELEON, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChameleonEntity::isValidNaturalSpawn);
        SpawnRestriction.register(ModEntities.MOSSBLOOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MossbloomEntity::isValidNaturalSpawn);
    }
}
