package net.emilsg.clutter.data;

import net.emilsg.clutter.util.ModBiomeTags;
import net.emilsg.clutter.world.biome.ModBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class BiomeTagDataGen extends FabricTagProvider<Biome> {

    public BiomeTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BiomeTags.IS_OVERWORLD)
                .add(ModBiomes.GIANT_REDWOOD_FOREST)
                .add(ModBiomes.LUPINE_FIELDS);

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_CRIMSON_NEWTS)
                .add(BiomeKeys.CRIMSON_FOREST);

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_WARPED_NEWTS)
                .add(BiomeKeys.WARPED_FOREST);

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_BUTTERFLIES).add(
                BiomeKeys.CRIMSON_FOREST,
                BiomeKeys.WARPED_FOREST,
                BiomeKeys.SOUL_SAND_VALLEY,
                BiomeKeys.FLOWER_FOREST,
                BiomeKeys.SUNFLOWER_PLAINS,
                BiomeKeys.MEADOW,
                BiomeKeys.CHERRY_GROVE
        )
                .addOptional(Identifier.of("biomesoplenty", "cherry_blossom_grove"))
                .addOptional(Identifier.of("biomesoplenty", "bamboo_grove"))
                .addOptional(Identifier.of("biomesoplenty", "lavender_field"))
        ;

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_EMBER_TORTOISES).add(
                BiomeKeys.BASALT_DELTAS
        )
                .addOptional(Identifier.of("biomesoplenty", "volcano"))
        ;

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_ECHOFINS).add(
                BiomeKeys.END_HIGHLANDS
        );

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_JELLYFISHES).add(
                BiomeKeys.WARM_OCEAN,
                BiomeKeys.LUKEWARM_OCEAN
        );

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_MANTA_RAYS).add(
                BiomeKeys.WARM_OCEAN,
                BiomeKeys.LUKEWARM_OCEAN
        );

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_SEAHORSES).add(
                BiomeKeys.WARM_OCEAN
        );

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_CAPYBARAS)
                .add(
                        BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.WINDSWEPT_SAVANNA,
                        BiomeKeys.SAVANNA
                )
                .addOptional(Identifier.of("terralith", "fractured_savanna"))
                .addOptional(Identifier.of("terralith", "savanna_badlands"))
                .addOptional(Identifier.of("terralith", "savanna_slopes"))
                .addOptional(Identifier.of("terralith", "lush_desert"))
        ;

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_BEAVERS)
                .add(BiomeKeys.RIVER)
                .addOptional(Identifier.of("terralith", "warm_river"))
                .addOptional(Identifier.of("biomesoplenty", "bayou"))
                .addOptional(Identifier.of("biomesoplenty", "wetland"))
        ;

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_EMPEROR_PENGUINS).add(
                BiomeKeys.ICE_SPIKES,
                BiomeKeys.SNOWY_PLAINS,
                BiomeKeys.SNOWY_BEACH
        )
                .addOptional(Identifier.of("terralith", "ice_marsh"))
                .addOptional(Identifier.of("terralith", "muskeg"))
        ;

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_KIWIS)
                .add(
                        BiomeKeys.BAMBOO_JUNGLE,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE
                )
                .addOptional(Identifier.of("terralith", "jungle_mountains"))
                .addOptional(Identifier.of("terralith", "rocky_jungle"))
                .addOptional(Identifier.of("terralith", "tropical_jungle"))
                .addOptional(Identifier.of("terralith", "rubble_jungle"))
                .addOptional(Identifier.of("biomesoplenty", "rainforest"))
                .addOptional(Identifier.of("biomesoplenty", "rocky_rainforest"))
                .addOptional(Identifier.of("biomesoplenty", "tropics"))
        ;

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_CHAMELEONS)
                .add(
                        BiomeKeys.BAMBOO_JUNGLE,
                        BiomeKeys.JUNGLE,
                        BiomeKeys.SPARSE_JUNGLE
                )
                .addOptional(Identifier.of("terralith", "jungle_mountains"))
                .addOptional(Identifier.of("terralith", "rocky_jungle"))
                .addOptional(Identifier.of("terralith", "tropical_jungle"))
                .addOptional(Identifier.of("terralith", "rubble_jungle"))
                .addOptional(Identifier.of("biomesoplenty", "rainforest"))
                .addOptional(Identifier.of("biomesoplenty", "rocky_rainforest"))
                .addOptional(Identifier.of("biomesoplenty", "tropics"))
        ;

        getOrCreateTagBuilder(ModBiomeTags.SPAWNS_MOSSBLOOMS).add(
                BiomeKeys.LUSH_CAVES
        );
    }

    @Override
    protected RegistryKey<Biome> reverseLookup(Biome element) {
        return super.reverseLookup(element);
    }
}
