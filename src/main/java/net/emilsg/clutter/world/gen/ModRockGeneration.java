package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModRockGeneration {

    public static void generateOres() {
        if (ModConfigManager.get(Configs.generateSilverOres, true)) {
            BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                    GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.SILVER_ORE_PLACED_KEY);
        }
        if (ModConfigManager.get(Configs.generateSulphurOres, true)) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
                    GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.BLACKSTONE_SULPHUR_ORE_PLACED_KEY);

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BASALT_DELTAS),
                    GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.BASALT_SULPHUR_ORE_PLACED_KEY);
        }
    }

    public static void generateGeodes() {
        if (ModConfigManager.get(Configs.generateOnyxGeodes, true)) {
            BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                    GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.ONYX_GEODE_PLACED_KEY);
        }
    }
}
