package net.emilsg.clutter.world;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.util.ModProperties;
import net.emilsg.clutter.world.gen.features.ModFeatures;
import net.emilsg.clutter.world.gen.tree.RedwoodFoliagePlacer;
import net.emilsg.clutter.world.gen.tree.RedwoodTrunkPlacer;
import net.emilsg.clutter.world.gen.tree.SmallRedwoodFoliagePlacer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.MegaPineFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_ORE_KEY = registerKey("silver_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> ONYX_GEODE_KEY = registerKey("onyx_geode");

    public static final RegistryKey<ConfiguredFeature<?,?>> BLACKSTONE_SULPHUR_ORE_KEY = registerKey("blackstone_sulphur_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> BASALT_SULPHUR_ORE_KEY = registerKey("basalt_sulphur_ore");


    public static final RegistryKey<ConfiguredFeature<?,?>> LUSH_MOSS_KEY = registerKey("lush_moss");
    public static final RegistryKey<ConfiguredFeature<?,?>> SCULK_MUSHROOM_KEY = registerKey("sculk_mushroom");
    public static final RegistryKey<ConfiguredFeature<?,?>> GIANT_LILY_PAD_SEEDLING_KEY = registerKey("giant_lily_pad_seedling");
    public static final RegistryKey<ConfiguredFeature<?,?>> SMALL_LILY_PADS_KEY = registerKey("small_lily_pads");
    public static final RegistryKey<ConfiguredFeature<?,?>> CATTAILS_KEY = registerKey("cattails");
    public static final RegistryKey<ConfiguredFeature<?,?>> CATTAILS_RIVER_KEY = registerKey("cattails_river");

    public static final RegistryKey<ConfiguredFeature<?,?>> SPONGE_IN_REEFS_KEY = registerKey("sponge_in_reefs");
    public static final RegistryKey<ConfiguredFeature<?,?>> CLAM_KEY = registerKey("clam");
    public static final RegistryKey<ConfiguredFeature<?,?>> BEACH_PATCH_KEY = registerKey("beach_patch");


    public static final RegistryKey<ConfiguredFeature<?,?>> KIWI_TREE_KEY = registerKey("kiwi_tree");

    public static final RegistryKey<ConfiguredFeature<?,?>> REDWOOD_BUSH_KEY = registerKey("redwood_bush");
    public static final RegistryKey<ConfiguredFeature<?,?>> REDWOOD_KEY = registerKey("redwood");
    public static final RegistryKey<ConfiguredFeature<?,?>> REDWOOD_KEY_2 = registerKey("redwood_2");
    public static final RegistryKey<ConfiguredFeature<?,?>> MEDIUM_REDWOOD_KEY = registerKey("medium_redwood");
    public static final RegistryKey<ConfiguredFeature<?,?>> SMALL_REDWOOD_KEY = registerKey("small_redwood");


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

        register(context, LUSH_MOSS_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(48, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LUSH_MOSS)))));

        register(context, CATTAILS_KEY, ModFeatures.CATTAILS, new CountConfig(18));
        register(context, CATTAILS_RIVER_KEY, ModFeatures.CATTAILS_RIVER, new CountConfig(24));

        register(context, SPONGE_IN_REEFS_KEY, ModFeatures.SMALL_SPONGE, new CountConfig(2));

        register(context, CLAM_KEY, ModFeatures.CLAM, new CountConfig(6));

        register(context, BEACH_PATCH_KEY, ModFeatures.BEACH_PATCH, new CountConfig(UniformIntProvider.create(6, 14)));

        register(context, SCULK_MUSHROOM_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(24, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SCULK_MUSHROOM)))));

        register(context, GIANT_LILY_PAD_SEEDLING_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(8, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.GIANT_LILY_PAD_SEEDLING)))));

        register(context, SMALL_LILY_PADS_KEY, Feature.FLOWER,
                ConfiguredFeatures.createRandomPatchFeatureConfig(8, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder()
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState(), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(ModProperties.PAD_AMOUNT, 2), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST).with(ModProperties.PAD_AMOUNT, 2), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH).with(ModProperties.PAD_AMOUNT, 2), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST).with(ModProperties.PAD_AMOUNT, 2), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(ModProperties.PAD_AMOUNT, 3), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST).with(ModProperties.PAD_AMOUNT, 3), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH).with(ModProperties.PAD_AMOUNT, 3), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST).with(ModProperties.PAD_AMOUNT, 3), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(ModProperties.PAD_AMOUNT, 4), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST).with(ModProperties.PAD_AMOUNT, 4), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH).with(ModProperties.PAD_AMOUNT, 4), 1)
                                .add(ModBlocks.SMALL_LILY_PADS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST).with(ModProperties.PAD_AMOUNT, 4), 1)
                        .build())))));


        register(context, REDWOOD_BUSH_KEY, Feature.TREE,
                new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.REDWOOD_LOG),
                        new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(ModBlocks.REDWOOD_LEAVES),
                        new BushFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(1), 2),
                        new TwoLayersFeatureSize(0, 0, 0)).build());

        register(context, REDWOOD_KEY, Feature.TREE,
                new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.REDWOOD_LOG),
                        new RedwoodTrunkPlacer(20, 5, 5), BlockStateProvider.of(ModBlocks.REDWOOD_LEAVES),
                        new RedwoodFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)),
                        new TwoLayersFeatureSize(1, 3, 3)).build());

        register(context, REDWOOD_KEY_2, Feature.TREE,
                new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.REDWOOD_LOG),
                        new RedwoodTrunkPlacer(20, 5, 5), BlockStateProvider.of(ModBlocks.REDWOOD_LEAVES),
                        new RedwoodFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)),
                        new TwoLayersFeatureSize(1, 3, 3)).build());

        register(context, MEDIUM_REDWOOD_KEY, Feature.TREE,
                new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.REDWOOD_LOG),
                        new GiantTrunkPlacer(10, 2, 7), BlockStateProvider.of(ModBlocks.REDWOOD_LEAVES),
                        new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(3, 7)),
                        new TwoLayersFeatureSize(1, 1, 2)).build());

        register(context, SMALL_REDWOOD_KEY, Feature.TREE,
                new TreeFeatureConfig.Builder(BlockStateProvider.of(ModBlocks.REDWOOD_LOG),
                        new StraightTrunkPlacer(9, 4, 4), BlockStateProvider.of(ModBlocks.REDWOOD_LEAVES),
                        new SmallRedwoodFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)),
                        new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());

    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Clutter.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
