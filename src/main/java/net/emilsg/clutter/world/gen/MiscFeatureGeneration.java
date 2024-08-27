package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

import static net.minecraft.world.biome.BiomeKeys.BEACH;

public class MiscFeatureGeneration {
    public static void generateMiscFeatures() {
        if (ModConfigManager.get(Configs.generateSeashells, true)) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(BEACH),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BEACH_PATCH_PLACED_KEY);
        }
    }
}
