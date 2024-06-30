package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.config.ClutterConfig;
import net.emilsg.clutter.world.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

import static net.minecraft.world.biome.BiomeKeys.*;

public class ModUnderwaterFeatureGeneration {
    public static void generateUnderwaterFeatures() {

        if (ClutterConfig.getInstance().getBoolean(ClutterConfig.GENERATE_SMALL_SPONGES)) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(WARM_OCEAN),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SPONGE_IN_REEFS_PLACED_KEY);
        }

        if (ClutterConfig.getInstance().getBoolean(ClutterConfig.GENERATE_CLAMS)) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(COLD_OCEAN, DEEP_COLD_OCEAN, DEEP_OCEAN),
                    GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLAM_PLACED_KEY);
        }

    }
}
