package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.ClutterConfig;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        if(ClutterConfig.getInstance().getBoolean(ClutterConfig.GENERATE_ORES)) {
            ModRockGeneration.generateOres();
        }
        if(ClutterConfig.getInstance().getBoolean(ClutterConfig.GENERATE_GEODES)) {
            ModRockGeneration.generateGeodes();
        }
        if(ClutterConfig.getInstance().getBoolean(ClutterConfig.SPAWN_CLUTTER_MOBS)) {
            ModEntitySpawning.addSpawns();
        }
        if(ClutterConfig.getInstance().getBoolean(ClutterConfig.GENERATE_FOLIAGE)) {
            ModFoliageGeneration.generateFoliage();
        }

        //ModFoliageGeneration.generateModBiomeFoliage();

        MiscFeatureGeneration.generateMiscFeatures();

        ModUnderwaterFeatureGeneration.generateUnderwaterFeatures();
    }
}


