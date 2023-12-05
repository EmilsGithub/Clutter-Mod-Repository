package net.emilsg.clutter.world.gen.features;

import net.emilsg.clutter.Clutter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class ModFeatures {

    public static final Feature<CountConfig> SMALL_SPONGE = register("small_sponge", new SmallSpongeFeature(CountConfig.CODEC));

    public static void registerModFeatures() {

    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, new Identifier(Clutter.MOD_ID, (name)), feature);
    }
}
