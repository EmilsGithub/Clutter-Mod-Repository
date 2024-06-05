package net.emilsg.clutter.world;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> SILVER_ORE_PLACED_KEY = registerKey("silver_ore_placed");
    public static final RegistryKey<PlacedFeature> BLACKSTONE_SULPHUR_ORE_PLACED_KEY = registerKey("blackstone_sulphur_ore_placed");
    public static final RegistryKey<PlacedFeature> BASALT_SULPHUR_ORE_PLACED_KEY = registerKey("basalt_sulphur_ore_placed");
    public static final RegistryKey<PlacedFeature> ONYX_GEODE_PLACED_KEY = registerKey("onyx_geode_placed");
    public static final RegistryKey<PlacedFeature> CATTAILS_PLACED_KEY = registerKey("cattails_placed");
    public static final RegistryKey<PlacedFeature> CATTAILS_PLACED_RIVER_KEY = registerKey("cattails_placed_river");
    public static final RegistryKey<PlacedFeature> LUSH_MOSS_PLACED_KEY = registerKey("lush_moss_placed");
    public static final RegistryKey<PlacedFeature> GIANT_LILY_PAD_SEEDLING_PLACED_KEY = registerKey("giant_lily_pad_seedling_placed");
    public static final RegistryKey<PlacedFeature> SMALL_LILY_PADS_PLACED_KEY = registerKey("small_lily_pads_placed");
    public static final RegistryKey<PlacedFeature> SCULK_MUSHROOM_PLACED_KEY = registerKey("sculk_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SPONGE_IN_REEFS_PLACED_KEY = registerKey("sponge_in_reefs_placed");
    public static final RegistryKey<PlacedFeature> CLAM_PLACED_KEY = registerKey("clam_placed");
    public static final RegistryKey<PlacedFeature> BEACH_PATCH_PLACED_KEY = registerKey("beach_patch_placed");

    public static final RegistryKey<PlacedFeature> REDWOOD_BUSH_PLACED_KEY = registerKey("redwood_bush_placed");
    public static final RegistryKey<PlacedFeature> REDWOOD_PLACED_KEY = registerKey("redwood_placed");
    public static final RegistryKey<PlacedFeature> REDWOOD_PLACED_KEY_2 = registerKey("redwood_placed_2");
    public static final RegistryKey<PlacedFeature> MEDIUM_REDWOOD_PLACED_KEY = registerKey("medium_redwood_placed");
    public static final RegistryKey<PlacedFeature> SMALL_REDWOOD_PLACED_KEY = registerKey("small_redwood_placed");
    public static final RegistryKey<PlacedFeature> DEAD_REDWOOD_PLACED_KEY = registerKey("dead_redwood_placed");
    public static final RegistryKey<PlacedFeature> GIANT_FERN_PLACED_KEY = registerKey("giant_fern_placed");
    public static final RegistryKey<PlacedFeature> REDWOOD_ROCK_PLACED_KEY = registerKey("redwood_rock_placed");
    public static final RegistryKey<PlacedFeature> REDWOOD_SILVER_ORE_PLACED_KEY = registerKey("redwood_silver_ore_placed");
    public static final RegistryKey<PlacedFeature> FALLEN_REDWOOD_PLACED_KEY = registerKey("fallen_redwood_placed");
    public static final RegistryKey<PlacedFeature> REDWOOD_MUD_PLACED_KEY = registerKey("redwood_mud_placed");

    public static final RegistryKey<PlacedFeature> LUPINES_PLACED_KEY = registerKey("lupines_placed");
    public static final RegistryKey<PlacedFeature> LUPINES_RIVER_PLACED_KEY = registerKey("lupines_river_placed");
    public static final RegistryKey<PlacedFeature> LUPINE_FIELDS_ROCK_PLACED_KEY = registerKey("lupine_fields_rock_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, SILVER_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SILVER_ORE_KEY), ModOrePlacement.modifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(32))));

        register(context, REDWOOD_SILVER_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SILVER_ORE_KEY),
                ModOrePlacement.modifiersWithCount(2, HeightRangePlacementModifier.trapezoid(YOffset.fixed(48), YOffset.fixed(128))));

        register(context, REDWOOD_MUD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MUD_PATCH_KEY),
                ModOrePlacement.modifiersWithCount(6, HeightRangePlacementModifier.uniform(YOffset.fixed(48), YOffset.TOP)));

        register(context, BLACKSTONE_SULPHUR_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BLACKSTONE_SULPHUR_ORE_KEY), ModOrePlacement.modifiersWithCount(10, PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
        register(context, BASALT_SULPHUR_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BASALT_SULPHUR_ORE_KEY), ModOrePlacement.modifiersWithCount(7, PlacedFeatures.TEN_ABOVE_AND_BELOW_RANGE));
        register(context, ONYX_GEODE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ONYX_GEODE_KEY), RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.aboveBottom(10), YOffset.belowTop(20)), BiomePlacementModifier.of());

        register(context, SCULK_MUSHROOM_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SCULK_MUSHROOM_KEY), RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, LUSH_MOSS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LUSH_MOSS_KEY), RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, GIANT_LILY_PAD_SEEDLING_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.GIANT_LILY_PAD_SEEDLING_KEY), RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, SMALL_LILY_PADS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_LILY_PADS_KEY), RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, CATTAILS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CATTAILS_KEY), RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(60), YOffset.fixed(62)), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, CATTAILS_PLACED_RIVER_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CATTAILS_RIVER_KEY), RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(60), YOffset.fixed(62)), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        register(context, SPONGE_IN_REEFS_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SPONGE_IN_REEFS_KEY), RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, CLAM_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.CLAM_KEY), RarityFilterPlacementModifier.of(16), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, BEACH_PATCH_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BEACH_PATCH_KEY), RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        register(context, REDWOOD_BUSH_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.REDWOOD_BUSH_KEY), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(2, 0.5f, 1), ModBlocks.REDWOOD_SAPLING));
        register(context, REDWOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.REDWOOD_KEY), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(3, 0.25f, 1), ModBlocks.REDWOOD_SAPLING));
        register(context, REDWOOD_PLACED_KEY_2, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.REDWOOD_KEY_2), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(3, 0.25f, 1), ModBlocks.REDWOOD_SAPLING));
        register(context, MEDIUM_REDWOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MEDIUM_REDWOOD_KEY), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.25f, 0), ModBlocks.REDWOOD_SAPLING));
        register(context, SMALL_REDWOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_REDWOOD_KEY), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.2f, 0), ModBlocks.REDWOOD_SAPLING));
        register(context, DEAD_REDWOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DEAD_REDWOOD_KEY), VegetationPlacedFeatures.treeModifiersWithWouldSurvive(RarityFilterPlacementModifier.of(32), ModBlocks.REDWOOD_SAPLING));
        register(context, GIANT_FERN_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.GIANT_FERN_KEY), RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, REDWOOD_ROCK_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.REDWOOD_ROCK), RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, FALLEN_REDWOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FALLEN_REDWOOD_KEY), CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        register(context, LUPINES_RIVER_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LUPINES_KEY), CountPlacementModifier.of(UniformIntProvider.create(2, 4)), RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, LUPINES_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LUPINES_KEY), RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(UniformIntProvider.create(8, 16)), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
        register(context, LUPINE_FIELDS_ROCK_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LUPINE_FIELDS_ROCK), RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Clutter.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
