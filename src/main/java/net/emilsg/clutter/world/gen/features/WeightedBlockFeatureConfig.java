package net.emilsg.clutter.world.gen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

public class WeightedBlockFeatureConfig implements FeatureConfig {

    public static final Codec<WeightedBlockFeatureConfig> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    WeightedBlockStateProvider.CODEC.fieldOf("states").forGetter((config) -> config.states),
                    Codec.INT.fieldOf("size").forGetter((config) -> config.size)
            ).apply(instance, WeightedBlockFeatureConfig::new));

    public final WeightedBlockStateProvider states;
    public final int size;

    public WeightedBlockFeatureConfig(WeightedBlockStateProvider states, int size) {
        this.states = states;
        this.size = size;
    }
}
