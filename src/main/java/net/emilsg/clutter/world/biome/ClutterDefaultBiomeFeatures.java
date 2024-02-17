package net.emilsg.clutter.world.biome;

import net.emilsg.clutter.world.ModPlacedFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;

public class ClutterDefaultBiomeFeatures {

    public static void addRedwoodTreeFeatures(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.REDWOOD_PLACED_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.REDWOOD_PLACED_KEY_2);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MEDIUM_REDWOOD_PLACED_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.SMALL_REDWOOD_PLACED_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.REDWOOD_BUSH_PLACED_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DEAD_REDWOOD_PLACED_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FALLEN_REDWOOD_PLACED_KEY);
    }

    public static void addRedwoodFoliageFeatures(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.GIANT_FERN_PLACED_KEY);
    }

    public static void addRedwoodRockFeatures(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.REDWOOD_ROCK_PLACED_KEY);
        builder.feature(GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.REDWOOD_SILVER_ORE_PLACED_KEY);
    }

    public static void addLupineFieldsFoliageFeatures(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.LUPINES_PLACED_KEY);
    }

    public static void addLupineFieldsRockFeatures(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, ModPlacedFeatures.LUPINE_FIELDS_ROCK_PLACED_KEY);
    }
}
