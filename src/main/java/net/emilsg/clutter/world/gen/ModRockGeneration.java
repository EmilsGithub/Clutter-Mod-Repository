package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.config.ClutterConfig;
import net.emilsg.clutter.world.ModPlacedFeatures;
import net.emilsg.clutter.world.biome.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModRockGeneration {
    public static void generateOres() {
        if(ClutterConfig.getInstance().getBoolean(ClutterConfig.GENERATE_SILVER_ORES)) {
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                    GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.SILVER_ORE_PLACED_KEY);
        }

        if(ClutterConfig.getInstance().getBoolean(ClutterConfig.GENERATE_SULPHUR_ORES)) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
                    GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.BLACKSTONE_SULPHUR_ORE_PLACED_KEY);

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
                    GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.BASALT_SULPHUR_ORE_PLACED_KEY);
        }
    }
    public static void generateGeodes() {
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.ONYX_GEODE_PLACED_KEY);
    }
}
