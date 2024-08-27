package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

import static net.minecraft.world.biome.BiomeKeys.*;

public class ModUnderwaterFeatureGeneration {
    public static void generateUnderwaterFeatures() {

        if (ModConfigManager.get(Configs.generateSmallSeaSponges, true)) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(WARM_OCEAN),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPONGE_IN_REEFS_PLACED_KEY);
        }

        if (ModConfigManager.get(Configs.generateClams, true)) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(COLD_OCEAN, DEEP_COLD_OCEAN, DEEP_OCEAN),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLAM_PLACED_KEY);
        }

    }
}
