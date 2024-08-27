package net.emilsg.clutter.world.biome;

import com.mojang.datafixers.util.Pair;
import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.ModifiedVanillaOverworldBuilder;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class ModOverworldRegion extends Region {

    public ModOverworldRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            if (ModConfigManager.get(Configs.generateBiomes, true)) {
                if (ModConfigManager.get(Configs.generateRedwoodForests, true)) makeRedwoodForest(builder);
                if (ModConfigManager.get(Configs.generateLupineFields, true)) makeLupineFields(builder);
            }
        });
    }

    private void makeRedwoodForest(ModifiedVanillaOverworldBuilder builder) {
        builder.replaceBiome(BiomeKeys.OLD_GROWTH_PINE_TAIGA, ModBiomes.GIANT_REDWOOD_FOREST);
        builder.replaceBiome(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, ModBiomes.GIANT_REDWOOD_FOREST);
    }

    private void makeLupineFields(ModifiedVanillaOverworldBuilder builder) {
        builder.replaceBiome(BiomeKeys.SUNFLOWER_PLAINS, ModBiomes.LUPINE_FIELDS);
    }
}
