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
    }
}
