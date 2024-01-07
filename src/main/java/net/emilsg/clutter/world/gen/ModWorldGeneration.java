package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.ModConfigs;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        if(ModConfigs.GENERATE_ORES) {
            ModRockGeneration.generateOres();
        }
        if(ModConfigs.GENERATE_GEODES) {
            ModRockGeneration.generateGeodes();
        }
        if(ModConfigs.SPAWN_CLUTTER_MOBS) {
            ModEntitySpawning.addSpawns();
        }
        if(ModConfigs.GENERATE_FOLIAGE) {
            ModFoliageGeneration.generateFoliage();
        }

        ModFoliageGeneration.generateModBiomeFoliage();

        MiscFeatureGeneration.generateMiscFeatures();
        ModUnderwaterFeatureGeneration.generateUnderwaterFeatures();
    }
}


