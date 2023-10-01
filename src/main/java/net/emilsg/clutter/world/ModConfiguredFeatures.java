package net.emilsg.clutter.world;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_ORE_KEY = registerKey("silver_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> ONYX_GEODE_KEY = registerKey("onyx_geode");

    public static final RegistryKey<ConfiguredFeature<?,?>> BLACKSTONE_SULPHUR_ORE_KEY = registerKey("blackstone_sulphur_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> BASALT_SULPHUR_ORE_KEY = registerKey("basalt_sulphur_ore");

    public static final RegistryKey<ConfiguredFeature<?,?>> CATTAILS_KEY = registerKey("cattails");
    public static final RegistryKey<ConfiguredFeature<?,?>> LUSH_MOSS_KEY = registerKey("lush_moss");
    public static final RegistryKey<ConfiguredFeature<?,?>> SCULK_MUSHROOM_KEY = registerKey("sculk_mushroom");
    public static final RegistryKey<ConfiguredFeature<?,?>> GIANT_LILY_PAD_SEEDLING_KEY = registerKey("giant_lily_pad_seedling");
    public static final RegistryKey<ConfiguredFeature<?,?>> SMALL_LILY_PADS_KEY = registerKey("small_lily_pads");

    public static final RegistryKey<ConfiguredFeature<?,?>> KIWI_TREE_KEY = registerKey("kiwi_tree");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest blackstoneReplaceables = new BlockMatchRuleTest(Blocks.BLACKSTONE);
        RuleTest basaltReplaceables = new BlockMatchRuleTest(Blocks.BASALT);
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        register(context, KIWI_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.JUNGLE_LOG),
                new StraightTrunkPlacer(2, 0, 1),
                new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.KIWI_LEAVES.getDefaultState(), 1).add(ModBlocks.RIPE_KIWI_LEAVES.getDefaultState(), 3).build()),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
                new TwoLayersFeatureSize(1, 0,1)).build()
        );

        List<OreFeatureConfig.Target> overworldSilverOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.SILVER_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_SILVER_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherBlackstoneSulphur = List.of(OreFeatureConfig.createTarget(blackstoneReplaceables, ModBlocks.BLACKSTONE_SULPHUR_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherBasaltSulphur = List.of(OreFeatureConfig.createTarget(basaltReplaceables, ModBlocks.BASALT_SULPHUR_ORE.getDefaultState()));

        register(context, SILVER_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldSilverOres, 8));

        register(context, BLACKSTONE_SULPHUR_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherBlackstoneSulphur, 12));
        register(context, BASALT_SULPHUR_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherBasaltSulphur, 20, 0.5f));



                register(context, ONYX_GEODE_KEY, Feature.GEODE,
                        new GeodeFeatureConfig(new GeodeLayerConfig(BlockStateProvider.of(Blocks.AIR),
                                BlockStateProvider.of(ModBlocks.ONYX_BLOCK), BlockStateProvider.of(ModBlocks.BUDDING_ONYX),
                                new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.ONYX_ORE.getDefaultState(), 2).add(ModBlocks.SULPHUR_BLOCK.getDefaultState(), 5).build()), BlockStateProvider.of(Blocks.SMOOTH_BASALT),
                                List.of(ModBlocks.SMALL_ONYX_BUD.getDefaultState(), ModBlocks.MEDIUM_ONYX_BUD.getDefaultState(), ModBlocks.LARGE_ONYX_BUD.getDefaultState(), ModBlocks.ONYX_CLUSTER.getDefaultState()),
                                BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                                new GeodeLayerThicknessConfig(0.75D, 1.1D, 1.5D, 2.75D),
                                new GeodeCrackConfig(0.65D, 1.5D, 2),
                                0.4D, 0.080D, true,
                                UniformIntProvider.create(4, 6), UniformIntProvider.create(3, 4), UniformIntProvider.create(1, 2),
                                -14, 14, 0.04D, 1));

        register(context, CATTAILS_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(24, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.CATTAILS)))));

        register(context, LUSH_MOSS_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(48, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LUSH_MOSS)))));


        register(context, SCULK_MUSHROOM_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(24, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SCULK_MUSHROOM)))));

        register(context, GIANT_LILY_PAD_SEEDLING_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(8, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.GIANT_LILY_PAD_SEEDLING)))));

        register(context, SMALL_LILY_PADS_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(8, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SMALL_LILY_PADS)))));

    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Clutter.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
