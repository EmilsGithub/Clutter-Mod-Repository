package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        if (ModConfigManager.get(Configs.generateOres, true)) {
            ModRockGeneration.generateOres();
        }
        if (ModConfigManager.get(Configs.generateGeodes, true)) {
            ModRockGeneration.generateGeodes();
        }
        if (ModConfigManager.get(Configs.spawnClutterMobs, true)) {
            ModEntitySpawning.addSpawns();
        }
        if (ModConfigManager.get(Configs.generateFoliage, true)) {
            ModFoliageGeneration.generateFoliage();
        }
        if (ModConfigManager.get(Configs.generateUnderwaterFeatures, true)) {
            ModUnderwaterFeatureGeneration.generateUnderwaterFeatures();
        }
        if (ModConfigManager.get(Configs.generateMiscFeatures, true)) {
            MiscFeatureGeneration.generateMiscFeatures();
        }
    }
}


