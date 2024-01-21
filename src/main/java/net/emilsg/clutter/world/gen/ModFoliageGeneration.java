package net.emilsg.clutter.world.gen;

import net.emilsg.clutter.world.ModPlacedFeatures;
import net.emilsg.clutter.world.biome.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

import static net.minecraft.world.biome.BiomeKeys.*;

public class ModFoliageGeneration {
    public static void generateFoliage() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(SWAMP, MANGROVE_SWAMP, RIVER),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CATTAILS_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(LUSH_CAVES),
                GenerationStep.Feature.UNDERGROUND_DECORATION, ModPlacedFeatures.LUSH_MOSS_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(DEEP_DARK),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SCULK_MUSHROOM_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(SWAMP, MANGROVE_SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.GIANT_LILY_PAD_SEEDLING_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(SWAMP, MANGROVE_SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SMALL_LILY_PADS_PLACED_KEY);
    }

    public static void generateModBiomeFoliage() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GIANT_REDWOOD_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.REDWOOD_BUSH_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GIANT_REDWOOD_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.REDWOOD_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GIANT_REDWOOD_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.REDWOOD_PLACED_KEY_2);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GIANT_REDWOOD_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MEDIUM_REDWOOD_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.GIANT_REDWOOD_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SMALL_REDWOOD_PLACED_KEY);
    }
}
