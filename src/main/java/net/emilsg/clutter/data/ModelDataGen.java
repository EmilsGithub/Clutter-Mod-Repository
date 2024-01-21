package net.emilsg.clutter.data;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.TableBlock;
import net.emilsg.clutter.block.custom.WallBookshelfBlock;
import net.emilsg.clutter.block.custom.WindowSillBlock;
import net.emilsg.clutter.block.custom.WoodenBenchBlock;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModProperties;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class ModelDataGen extends FabricModelProvider {

    public static final TextureKey LOG = TextureKey.of("log");
    public static final TextureKey PLANKS = TextureKey.of("planks");
    public static final TextureKey FLOWER = TextureKey.of("flower");
    public static final TextureKey CUPBOARD_DOOR = TextureKey.of("cupboard_door");
    public static final TextureKey CUPBOARD_INSIDE = TextureKey.of("cupboard_inside");

    public static final Model WOODEN_CHAIR = block("wooden_chair_parent", LOG, PLANKS);
    public static final Model WOODEN_BENCH_ALL = block("wooden_bench_all_parent", LOG, PLANKS);
    public static final Model WOODEN_BENCH_NONE = block("wooden_bench_none_parent", LOG, PLANKS);
    public static final Model WOODEN_BENCH_LEFT = block("wooden_bench_left_parent", LOG, PLANKS);
    public static final Model WOODEN_BENCH_RIGHT = block("wooden_bench_right_parent", LOG, PLANKS);
    public static final Model WOODEN_SHELF = block("wooden_shelf_parent", LOG, PLANKS);

    public static final Model WALL_BOOKSHELF = block("wall_bookshelf_parent", PLANKS);
    public static final Model WALL_BOOKSHELF_TWO = block("wall_bookshelf_parent_two", PLANKS);
    public static final Model WALL_BOOKSHELF_THREE = block("wall_bookshelf_parent_three", PLANKS);
    public static final Model WALL_BOOKSHELF_FOUR = block("wall_bookshelf_parent_four", PLANKS);
    public static final Model WALL_BOOKSHELF_FIVE = block("wall_bookshelf_parent_five", PLANKS);
    public static final Model WALL_BOOKSHELF_SIX = block("wall_bookshelf_parent_six", PLANKS);
    public static final Model WALL_BOOKSHELF_SEVEN = block("wall_bookshelf_parent_seven", PLANKS);

    public static final Model TABLE_ALL = block("table_all_parent", LOG, PLANKS);
    public static final Model TABLE_N = block("table_n_parent", LOG, PLANKS);
    public static final Model TABLE_NE = block("table_ne_parent", LOG, PLANKS);
    public static final Model TABLE_E = block("table_e_parent", LOG, PLANKS);
    public static final Model TABLE_SE = block("table_se_parent", LOG, PLANKS);
    public static final Model TABLE_S = block("table_s_parent", LOG, PLANKS);
    public static final Model TABLE_SW = block("table_sw_parent", LOG, PLANKS);
    public static final Model TABLE_W = block("table_w_parent", LOG, PLANKS);
    public static final Model TABLE_NW = block("table_nw_parent", LOG, PLANKS);
    public static final Model TABLE_NONE = block("table_none_parent", PLANKS);

    public static final Model CUPBOARD_OPEN = block("cupboard_open_parent", PLANKS, CUPBOARD_DOOR, CUPBOARD_INSIDE);
    public static final Model CUPBOARD_CLOSED = block("cupboard_closed_parent", PLANKS, CUPBOARD_DOOR, CUPBOARD_INSIDE);
    public static final Model WALL_CUPBOARD_OPEN = block("wall_cupboard_open_parent", PLANKS, CUPBOARD_DOOR, CUPBOARD_INSIDE);
    public static final Model WALL_CUPBOARD_CLOSED = block("wall_cupboard_closed_parent", PLANKS, CUPBOARD_DOOR, CUPBOARD_INSIDE);

    public static final Model WINDOW_SILL = block("window_sill_parent", PLANKS, FLOWER);
    public static final Model WINDOW_SILL_EMPTY = block("window_sill_parent_empty", PLANKS);

    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        BlockStateModelGenerator.BlockTexturePool blackOnyxTexturePool = generator.registerCubeAllModelTexturePool(ModBlocks.BLACK_ONYX_BLOCK);
        BlockStateModelGenerator.BlockTexturePool polishedBlackOnyxTexturePool = generator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_BLACK_ONYX);

        blackOnyxTexturePool.slab(ModBlocks.BLACK_ONYX_SLAB).stairs(ModBlocks.BLACK_ONYX_STAIRS).wall(ModBlocks.BLACK_ONYX_WALL);
        polishedBlackOnyxTexturePool.slab(ModBlocks.POLISHED_BLACK_ONYX_SLAB).stairs(ModBlocks.POLISHED_BLACK_ONYX_STAIRS).wall(ModBlocks.POLISHED_BLACK_ONYX_WALL);

        generator.registerCoral(ModBlocks.CUP_CORAL, ModBlocks.DEAD_CUP_CORAL, ModBlocks.CUP_CORAL_BLOCK, ModBlocks.DEAD_CUP_CORAL_BLOCK, ModBlocks.CUP_CORAL_FAN, ModBlocks.DEAD_CUP_CORAL_FAN, ModBlocks.CUP_CORAL_WALL_FAN, ModBlocks.DEAD_CUP_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.GHOST_CORAL, ModBlocks.DEAD_GHOST_CORAL, ModBlocks.GHOST_CORAL_BLOCK, ModBlocks.DEAD_GHOST_CORAL_BLOCK, ModBlocks.GHOST_CORAL_FAN, ModBlocks.DEAD_GHOST_CORAL_FAN, ModBlocks.GHOST_CORAL_WALL_FAN, ModBlocks.DEAD_GHOST_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.STONE_CORAL, ModBlocks.DEAD_STONE_CORAL, ModBlocks.STONE_CORAL_BLOCK, ModBlocks.DEAD_STONE_CORAL_BLOCK, ModBlocks.STONE_CORAL_FAN, ModBlocks.DEAD_STONE_CORAL_FAN, ModBlocks.STONE_CORAL_WALL_FAN, ModBlocks.DEAD_STONE_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.SLATE_CORAL, ModBlocks.DEAD_SLATE_CORAL, ModBlocks.SLATE_CORAL_BLOCK, ModBlocks.DEAD_SLATE_CORAL_BLOCK, ModBlocks.SLATE_CORAL_FAN, ModBlocks.DEAD_SLATE_CORAL_FAN, ModBlocks.SLATE_CORAL_WALL_FAN, ModBlocks.DEAD_SLATE_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.THORN_CORAL, ModBlocks.DEAD_THORN_CORAL, ModBlocks.THORN_CORAL_BLOCK, ModBlocks.DEAD_THORN_CORAL_BLOCK, ModBlocks.THORN_CORAL_FAN, ModBlocks.DEAD_THORN_CORAL_FAN, ModBlocks.THORN_CORAL_WALL_FAN, ModBlocks.DEAD_THORN_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.COCOA_CORAL, ModBlocks.DEAD_COCOA_CORAL, ModBlocks.COCOA_CORAL_BLOCK, ModBlocks.DEAD_COCOA_CORAL_BLOCK, ModBlocks.COCOA_CORAL_FAN, ModBlocks.DEAD_COCOA_CORAL_FAN, ModBlocks.COCOA_CORAL_WALL_FAN, ModBlocks.DEAD_COCOA_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.PASSION_CORAL, ModBlocks.DEAD_PASSION_CORAL, ModBlocks.PASSION_CORAL_BLOCK, ModBlocks.DEAD_PASSION_CORAL_BLOCK, ModBlocks.PASSION_CORAL_FAN, ModBlocks.DEAD_PASSION_CORAL_FAN, ModBlocks.PASSION_CORAL_WALL_FAN, ModBlocks.DEAD_PASSION_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.TOXIC_CORAL, ModBlocks.DEAD_TOXIC_CORAL, ModBlocks.TOXIC_CORAL_BLOCK, ModBlocks.DEAD_TOXIC_CORAL_BLOCK, ModBlocks.TOXIC_CORAL_FAN, ModBlocks.DEAD_TOXIC_CORAL_FAN, ModBlocks.TOXIC_CORAL_WALL_FAN, ModBlocks.DEAD_TOXIC_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.GEM_CORAL, ModBlocks.DEAD_GEM_CORAL, ModBlocks.GEM_CORAL_BLOCK, ModBlocks.DEAD_GEM_CORAL_BLOCK, ModBlocks.GEM_CORAL_FAN, ModBlocks.DEAD_GEM_CORAL_FAN, ModBlocks.GEM_CORAL_WALL_FAN, ModBlocks.DEAD_GEM_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.DIAMOND_CORAL, ModBlocks.DEAD_DIAMOND_CORAL, ModBlocks.DIAMOND_CORAL_BLOCK, ModBlocks.DEAD_DIAMOND_CORAL_BLOCK, ModBlocks.DIAMOND_CORAL_FAN, ModBlocks.DEAD_DIAMOND_CORAL_FAN, ModBlocks.DIAMOND_CORAL_WALL_FAN, ModBlocks.DEAD_DIAMOND_CORAL_WALL_FAN);
        generator.registerCoral(ModBlocks.ANCHOR_CORAL, ModBlocks.DEAD_ANCHOR_CORAL, ModBlocks.ANCHOR_CORAL_BLOCK, ModBlocks.DEAD_ANCHOR_CORAL_BLOCK, ModBlocks.ANCHOR_CORAL_FAN, ModBlocks.DEAD_ANCHOR_CORAL_FAN, ModBlocks.ANCHOR_CORAL_WALL_FAN, ModBlocks.DEAD_ANCHOR_CORAL_WALL_FAN);

        //Redwood Wood Set, Example for other Wood Sets
        BlockStateModelGenerator.BlockTexturePool redwoodPlanksTexturePool = generator.registerCubeAllModelTexturePool(ModBlocks.REDWOOD_PLANKS);
        redwoodPlanksTexturePool.stairs(ModBlocks.REDWOOD_STAIRS).slab(ModBlocks.REDWOOD_SLAB)
                .button(ModBlocks.REDWOOD_BUTTON).fence(ModBlocks.REDWOOD_FENCE).fenceGate(ModBlocks.REDWOOD_FENCE_GATE)
                        .pressurePlate(ModBlocks.REDWOOD_PRESSURE_PLATE);

        BlockStateModelGenerator.BlockTexturePool redwoodMosaicTexturePool = generator.registerCubeAllModelTexturePool(ModBlocks.REDWOOD_MOSAIC);
        redwoodMosaicTexturePool.slab(ModBlocks.REDWOOD_MOSAIC_SLAB).stairs(ModBlocks.REDWOOD_MOSAIC_STAIRS);

        generator.registerLog(ModBlocks.REDWOOD_LOG).log(ModBlocks.REDWOOD_LOG).wood(ModBlocks.REDWOOD_WOOD);
        generator.registerLog(ModBlocks.STRIPPED_REDWOOD_LOG).log(ModBlocks.STRIPPED_REDWOOD_LOG).wood(ModBlocks.STRIPPED_REDWOOD_WOOD);
        generator.registerSingleton(ModBlocks.REDWOOD_LEAVES, TexturedModel.LEAVES);
        generator.registerTintableCross(ModBlocks.REDWOOD_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerDoor(ModBlocks.REDWOOD_DOOR);
        generator.registerTrapdoor(ModBlocks.REDWOOD_TRAPDOOR);

        registerClutterWoodSet(generator, ModBlocks.REDWOOD_LOG, ModBlocks.REDWOOD_PLANKS, ModBlocks.REDWOOD_CHAIR, ModBlocks.STRIPPED_REDWOOD_CHAIR, ModBlocks.REDWOOD_TABLE, ModBlocks.STRIPPED_REDWOOD_TABLE, ModBlocks.REDWOOD_BENCH, ModBlocks.STRIPPED_REDWOOD_BENCH, ModBlocks.REDWOOD_SHELF, ModBlocks.REDWOOD_WALL_BOOKSHELF, ModBlocks.REDWOOD_WALL_CUPBOARD, ModBlocks.REDWOOD_CUPBOARD, ModBlocks.REDWOOD_WINDOW_SILL, ModBlocks.REDWOOD_TRELLIS);

        registerClutterWoodSet(generator, Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, ModBlocks.SPRUCE_CHAIR, ModBlocks.STRIPPED_SPRUCE_CHAIR, ModBlocks.SPRUCE_TABLE, ModBlocks.STRIPPED_SPRUCE_TABLE, ModBlocks.SPRUCE_BENCH, ModBlocks.STRIPPED_SPRUCE_BENCH, ModBlocks.SPRUCE_SHELF, ModBlocks.SPRUCE_WALL_BOOKSHELF, ModBlocks.SPRUCE_WALL_CUPBOARD, ModBlocks.SPRUCE_CUPBOARD, ModBlocks.SPRUCE_WINDOW_SILL, ModBlocks.SPRUCE_TRELLIS);

        registerWindowSill(generator, ModBlocks.OAK_WINDOW_SILL, Blocks.OAK_PLANKS);
        registerWindowSill(generator, ModBlocks.BIRCH_WINDOW_SILL, Blocks.BIRCH_PLANKS);
        registerWindowSill(generator, ModBlocks.JUNGLE_WINDOW_SILL, Blocks.JUNGLE_PLANKS);
        registerWindowSill(generator, ModBlocks.ACACIA_WINDOW_SILL, Blocks.ACACIA_PLANKS);
        registerWindowSill(generator, ModBlocks.DARK_OAK_WINDOW_SILL, Blocks.DARK_OAK_PLANKS);
        registerWindowSill(generator, ModBlocks.MANGROVE_WINDOW_SILL, Blocks.MANGROVE_PLANKS);
        registerWindowSill(generator, ModBlocks.CRIMSON_WINDOW_SILL, Blocks.CRIMSON_PLANKS);
        registerWindowSill(generator, ModBlocks.WARPED_WINDOW_SILL, Blocks.WARPED_PLANKS);
        registerWindowSill(generator, ModBlocks.CHERRY_WINDOW_SILL, Blocks.CHERRY_PLANKS);
        registerWindowSill(generator, ModBlocks.BAMBOO_WINDOW_SILL, Blocks.BAMBOO_PLANKS);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemGen) {
        itemGen.registerArmor(((ArmorItem) ModItems.SILVER_HELMET));
        itemGen.registerArmor(((ArmorItem) ModItems.SILVER_CHESTPLATE));
        itemGen.registerArmor(((ArmorItem) ModItems.SILVER_LEGGINGS));
        itemGen.registerArmor(((ArmorItem) ModItems.SILVER_BOOTS));

        itemGen.registerArmor(((ArmorItem) ModItems.COPPER_DIVING_HELMET));
        itemGen.registerArmor(((ArmorItem) ModItems.COPPER_DIVING_CHESTPLATE));
        itemGen.registerArmor(((ArmorItem) ModItems.COPPER_DIVING_LEGGINGS));
        itemGen.registerArmor(((ArmorItem) ModItems.COPPER_DIVING_BOOTS));

        registerGeneratedItem(itemGen, ModItems.SPONGE_SHARD);
        registerGeneratedItem(itemGen, ModItems.SEA_CONCH);
        registerGeneratedItem(itemGen, ModItems.SEASHELL);
        registerGeneratedItem(itemGen, ModItems.CLAM);
        registerGeneratedItem(itemGen, ModItems.PEARL);
        registerGeneratedItem(itemGen, ModItems.JELLYFISH_SPAWN_EGG);

        registerSpawnEggItem(itemGen, ModItems.CAPYBARA_SPAWN_EGG);
        registerSpawnEggItem(itemGen, ModItems.CRIMSON_NEWT_SPAWN_EGG);
        registerSpawnEggItem(itemGen, ModItems.EMBER_TORTOISE_SPAWN_EGG);
    }

    private void registerClutterWoodSet(BlockStateModelGenerator generator, Block logTextureReference, Block planksTextureReference, Block chair, Block strippedChair, Block table, Block strippedTable, Block bench, Block strippedBench, Block shelf, Block wallBookshelf, Block wallCupboard, Block cupboard, Block windowSill, Block trellis) {
        registerChair(generator, chair, logTextureReference, planksTextureReference);
        registerChair(generator, strippedChair, logTextureReference, planksTextureReference);
        registerBench(generator, bench, logTextureReference, planksTextureReference);
        registerBench(generator, strippedBench, logTextureReference, planksTextureReference);
        registerTable(generator, table, logTextureReference, planksTextureReference);
        registerTable(generator, strippedTable, logTextureReference, planksTextureReference);
        registerShelf(generator, shelf, logTextureReference, planksTextureReference);
        registerWallBookshelf(generator, wallBookshelf, planksTextureReference);
        registerWallCupboard(generator, wallCupboard, planksTextureReference);
        registerCupboard(generator, cupboard, planksTextureReference);
        registerWindowSill(generator, windowSill, planksTextureReference);
        //registerTrellis(generator, trellis, planksTextureReference);
    }

    private void registerSpawnEggItem(ItemModelGenerator itemModelGenerator, Item egg) {
        itemModelGenerator.register(egg, new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
    }

    private void registerGeneratedItem(ItemModelGenerator itemModelGenerator, Item item) {
        itemModelGenerator.register(item, Models.GENERATED);
    }

    public void registerChair(BlockStateModelGenerator generator, Block woodenChairBlock, Block log, Block planks) {
        TexturedModel texturedModel = TexturedModel.makeFactory(block -> logAndPlanksMap(log, planks), WOODEN_CHAIR).get(woodenChairBlock);
        texturedModel.upload(woodenChairBlock, generator.modelCollector);
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(woodenChairBlock, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(woodenChairBlock))).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerShelf(BlockStateModelGenerator generator, Block woodenShelfBlock, Block log, Block planks) {
        TexturedModel texturedModel = TexturedModel.makeFactory(block -> logAndPlanksMap(log, planks), WOODEN_SHELF).get(woodenShelfBlock);
        texturedModel.upload(woodenShelfBlock, generator.modelCollector);
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(woodenShelfBlock, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockModelId(woodenShelfBlock))).coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerBench(BlockStateModelGenerator generator, Block woodenBenchBlock, Block log, Block planks) {
        Identifier allModel = generator.createSubModel(woodenBenchBlock, "_all", WOODEN_BENCH_ALL, id -> logAndPlanksMap(log, planks));
        Identifier noneModel = generator.createSubModel(woodenBenchBlock, "_none", WOODEN_BENCH_NONE, id -> logAndPlanksMap(log, planks));
        Identifier leftModel = generator.createSubModel(woodenBenchBlock, "_left", WOODEN_BENCH_LEFT, id -> logAndPlanksMap(log, planks));
        Identifier rightModel = generator.createSubModel(woodenBenchBlock, "_right", WOODEN_BENCH_RIGHT, id -> logAndPlanksMap(log, planks));

        generator.blockStateCollector.accept(createBenchBlockState(woodenBenchBlock, allModel, noneModel, leftModel, rightModel));
        generator.registerParentedItemModel(woodenBenchBlock, allModel);
    }

    public void registerWallBookshelf(BlockStateModelGenerator generator, Block wallBookshelf, Block planks) {
        Identifier oneModel = generator.createSubModel(wallBookshelf, "_one", WALL_BOOKSHELF, id -> planksMap(planks));
        Identifier twoModel = generator.createSubModel(wallBookshelf, "_two", WALL_BOOKSHELF_TWO, id -> planksMap(planks));
        Identifier threeModel = generator.createSubModel(wallBookshelf, "_three", WALL_BOOKSHELF_THREE, id -> planksMap(planks));
        Identifier fourModel = generator.createSubModel(wallBookshelf, "_four", WALL_BOOKSHELF_FOUR, id -> planksMap(planks));
        Identifier fiveModel = generator.createSubModel(wallBookshelf, "_five", WALL_BOOKSHELF_FIVE, id -> planksMap(planks));
        Identifier sixModel = generator.createSubModel(wallBookshelf, "_six", WALL_BOOKSHELF_SIX, id -> planksMap(planks));
        Identifier sevenModel = generator.createSubModel(wallBookshelf, "_seven", WALL_BOOKSHELF_SEVEN, id -> planksMap(planks));

        generator.blockStateCollector.accept(createWallBookshelfBlockState(wallBookshelf, oneModel, twoModel, threeModel, fourModel, fiveModel, sixModel, sevenModel));
        generator.registerParentedItemModel(wallBookshelf, oneModel);
    }

    public void registerTable(BlockStateModelGenerator generator, Block table, Block log, Block planks) {
        Identifier allModel = generator.createSubModel(table, "_all", TABLE_ALL, id -> logAndPlanksMap(log, planks));
        Identifier nModel = generator.createSubModel(table, "_n", TABLE_N, id -> logAndPlanksMap(log, planks));
        Identifier neModel = generator.createSubModel(table, "_ne", TABLE_NE, id -> logAndPlanksMap(log, planks));
        Identifier eModel = generator.createSubModel(table, "_e", TABLE_E, id -> logAndPlanksMap(log, planks));
        Identifier seModel = generator.createSubModel(table, "_se", TABLE_SE, id -> logAndPlanksMap(log, planks));
        Identifier sModel = generator.createSubModel(table, "_s", TABLE_S, id -> logAndPlanksMap(log, planks));
        Identifier swModel = generator.createSubModel(table, "_sw", TABLE_SW, id -> logAndPlanksMap(log, planks));
        Identifier wModel = generator.createSubModel(table, "_w", TABLE_W, id -> logAndPlanksMap(log, planks));
        Identifier nwModel = generator.createSubModel(table, "_nw", TABLE_NW, id -> logAndPlanksMap(log, planks));
        Identifier noneModel = generator.createSubModel(table, "_none", TABLE_NONE, id -> planksMap(planks));

        generator.blockStateCollector.accept(createTableBlockState(table, allModel, nModel, neModel, eModel, seModel, sModel, swModel, wModel, nwModel, noneModel));
        generator.registerParentedItemModel(table, allModel);
    }

    public void registerCupboard(BlockStateModelGenerator generator, Block cupboard, Block planks) {
        Identifier openModel = generator.createSubModel(cupboard, "_open", CUPBOARD_OPEN, id -> cupboardMap(planks));
        Identifier closedModel = generator.createSubModel(cupboard, "_closed", CUPBOARD_CLOSED, id -> cupboardMap(planks));

        generator.blockStateCollector.accept(createCupboardModel(cupboard, openModel, closedModel));
        generator.registerParentedItemModel(cupboard, closedModel);
    }

    public void registerWallCupboard(BlockStateModelGenerator generator, Block wallCupboard, Block planks) {
        Identifier openModel = generator.createSubModel(wallCupboard, "_open", WALL_CUPBOARD_OPEN, id -> cupboardMap(planks));
        Identifier closedModel = generator.createSubModel(wallCupboard, "_closed", WALL_CUPBOARD_CLOSED, id -> cupboardMap(planks));

        generator.blockStateCollector.accept(createCupboardModel(wallCupboard, openModel, closedModel));
        generator.registerParentedItemModel(wallCupboard, closedModel);
    }

    public void registerWindowSill(BlockStateModelGenerator generator, Block windowSill, Block planks) {
        String[] flowerNames = {
                "allium", "azure_bluet", "blue_orchid", "brown_mushroom", "cornflower",
                "crimson_fungus", "dandelion", "dead_bush", "fern",
                "lily_of_the_valley", "oak_window_sill_parent", "orange_tulip", "oxeye_daisy", "pink_tulip",
                "poppy", "red_mushroom", "red_tulip", "warped_fungus", "white_tulip", "wither_rose"
        };

        Identifier emptyModel = generator.createSubModel(windowSill, "_empty", WINDOW_SILL_EMPTY, id -> emptyWindowSillMap(planks));

        for (String flower : flowerNames) {
            generator.createSubModel(windowSill, "_" + flower, WINDOW_SILL, id -> windowSillMap(planks, flower));
        }

        flowerNames = new String[]{
                "empty", "allium", "azure_bluet", "blue_orchid", "brown_mushroom", "cornflower",
                "crimson_fungus", "dandelion", "dead_bush", "fern",
                "lily_of_the_valley", "orange_tulip", "oxeye_daisy", "pink_tulip",
                "poppy", "red_mushroom", "red_tulip", "warped_fungus", "white_tulip", "wither_rose"
        };

        generator.blockStateCollector.accept(createWindowSillBlockState(windowSill, flowerNames));
        generator.registerParentedItemModel(windowSill, emptyModel);
    }

    public static BlockStateSupplier createBenchBlockState(Block woodenBenchBlock, Identifier allModel, Identifier noneModel, Identifier leftModel, Identifier rightModel) {
        return VariantsBlockStateSupplier.create(woodenBenchBlock)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, WoodenBenchBlock.LEG_POSITIONS)
                        .register(Direction.NORTH, WoodenBenchBlock.LegPosition.ALL, BlockStateVariant.create().put(VariantSettings.MODEL, allModel))
                        .register(Direction.EAST, WoodenBenchBlock.LegPosition.ALL, BlockStateVariant.create().put(VariantSettings.MODEL, allModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, WoodenBenchBlock.LegPosition.ALL, BlockStateVariant.create().put(VariantSettings.MODEL, allModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, WoodenBenchBlock.LegPosition.ALL, BlockStateVariant.create().put(VariantSettings.MODEL, allModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, WoodenBenchBlock.LegPosition.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, leftModel))
                        .register(Direction.SOUTH, WoodenBenchBlock.LegPosition.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, rightModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.NORTH, WoodenBenchBlock.LegPosition.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, rightModel))
                        .register(Direction.SOUTH, WoodenBenchBlock.LegPosition.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, leftModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.NORTH, WoodenBenchBlock.LegPosition.NONE, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(Direction.SOUTH, WoodenBenchBlock.LegPosition.NONE, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.EAST, WoodenBenchBlock.LegPosition.NONE, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.WEST, WoodenBenchBlock.LegPosition.NONE, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.EAST, WoodenBenchBlock.LegPosition.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, rightModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.WEST, WoodenBenchBlock.LegPosition.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, leftModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.EAST, WoodenBenchBlock.LegPosition.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, leftModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.WEST, WoodenBenchBlock.LegPosition.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, rightModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, WoodenBenchBlock.LegPosition.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(Direction.EAST, WoodenBenchBlock.LegPosition.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.NORTH, WoodenBenchBlock.LegPosition.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, WoodenBenchBlock.LegPosition.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.SOUTH, WoodenBenchBlock.LegPosition.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(Direction.SOUTH, WoodenBenchBlock.LegPosition.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.EAST, WoodenBenchBlock.LegPosition.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.WEST, WoodenBenchBlock.LegPosition.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                );
    }

    public static BlockStateSupplier createWindowSillBlockState(Block windowSill, String[] flowerNames) {
        int i = 0;
        VariantsBlockStateSupplier supplier = VariantsBlockStateSupplier.create(windowSill);

        BlockStateVariantMap.DoubleProperty<Direction, Integer> builder = BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, WindowSillBlock.CURRENT_MODEL);

        for (String flower : flowerNames) {
            builder.register(Direction.NORTH, i, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Clutter.MOD_ID, "block/" + Registries.BLOCK.getId(windowSill).getPath() + "_" + flower)))
                    .register(Direction.EAST, i, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Clutter.MOD_ID, "block/" + Registries.BLOCK.getId(windowSill).getPath() + "_" + flower)).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                    .register(Direction.SOUTH, i, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Clutter.MOD_ID, "block/" + Registries.BLOCK.getId(windowSill).getPath() + "_" + flower)).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                    .register(Direction.WEST, i, BlockStateVariant.create().put(VariantSettings.MODEL, new Identifier(Clutter.MOD_ID, "block/" + Registries.BLOCK.getId(windowSill).getPath() + "_" + flower)).put(VariantSettings.Y, VariantSettings.Rotation.R270));

            i++;
        }

        return supplier.coordinate(builder);
    }

    public static BlockStateSupplier createWallBookshelfBlockState(Block wallBookshelfBlock, Identifier oneModel, Identifier twoModel, Identifier threeModel, Identifier fourModel, Identifier fiveModel, Identifier sixModel, Identifier sevenModel) {
        return VariantsBlockStateSupplier.create(wallBookshelfBlock)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, WallBookshelfBlock.CURRENT_MODEL)
                        .register(Direction.NORTH, 0, BlockStateVariant.create().put(VariantSettings.MODEL, oneModel))
                        .register(Direction.NORTH, 1, BlockStateVariant.create().put(VariantSettings.MODEL, twoModel))
                        .register(Direction.NORTH, 2, BlockStateVariant.create().put(VariantSettings.MODEL, threeModel))
                        .register(Direction.NORTH, 3, BlockStateVariant.create().put(VariantSettings.MODEL, fourModel))
                        .register(Direction.NORTH, 4, BlockStateVariant.create().put(VariantSettings.MODEL, fiveModel))
                        .register(Direction.NORTH, 5, BlockStateVariant.create().put(VariantSettings.MODEL, sixModel))
                        .register(Direction.NORTH, 6, BlockStateVariant.create().put(VariantSettings.MODEL, sevenModel))
                        .register(Direction.SOUTH, 0, BlockStateVariant.create().put(VariantSettings.MODEL, oneModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, 1, BlockStateVariant.create().put(VariantSettings.MODEL, twoModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, 2, BlockStateVariant.create().put(VariantSettings.MODEL, threeModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, 3, BlockStateVariant.create().put(VariantSettings.MODEL, fourModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, 4, BlockStateVariant.create().put(VariantSettings.MODEL, fiveModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, 5, BlockStateVariant.create().put(VariantSettings.MODEL, sixModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.SOUTH, 6, BlockStateVariant.create().put(VariantSettings.MODEL, sevenModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.EAST, 0, BlockStateVariant.create().put(VariantSettings.MODEL, oneModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, 1, BlockStateVariant.create().put(VariantSettings.MODEL, twoModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, 2, BlockStateVariant.create().put(VariantSettings.MODEL, threeModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, 3, BlockStateVariant.create().put(VariantSettings.MODEL, fourModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, 4, BlockStateVariant.create().put(VariantSettings.MODEL, fiveModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, 5, BlockStateVariant.create().put(VariantSettings.MODEL, sixModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.EAST, 6, BlockStateVariant.create().put(VariantSettings.MODEL, sevenModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.WEST, 0, BlockStateVariant.create().put(VariantSettings.MODEL, oneModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.WEST, 1, BlockStateVariant.create().put(VariantSettings.MODEL, twoModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.WEST, 2, BlockStateVariant.create().put(VariantSettings.MODEL, threeModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.WEST, 3, BlockStateVariant.create().put(VariantSettings.MODEL, fourModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.WEST, 4, BlockStateVariant.create().put(VariantSettings.MODEL, fiveModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.WEST, 5, BlockStateVariant.create().put(VariantSettings.MODEL, sixModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.WEST, 6, BlockStateVariant.create().put(VariantSettings.MODEL, sevenModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                );
    }

    public static BlockStateSupplier createTableBlockState(Block wallBookshelfBlock, Identifier allModel, Identifier nModel, Identifier neModel, Identifier eModel, Identifier seModel, Identifier sModel, Identifier swModel, Identifier wModel, Identifier nwModel, Identifier noneModel) {
        return VariantsBlockStateSupplier.create(wallBookshelfBlock)
                .coordinate(BlockStateVariantMap.create(ModProperties.LEGS, TableBlock.LEG_POSITIONS)
                        .register(true, TableBlock.LegPosition.ALL, BlockStateVariant.create().put(VariantSettings.MODEL, allModel))
                        .register(true, TableBlock.LegPosition.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, sModel))
                        .register(true, TableBlock.LegPosition.NORTH_EAST, BlockStateVariant.create().put(VariantSettings.MODEL, swModel))
                        .register(true, TableBlock.LegPosition.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, wModel))
                        .register(true, TableBlock.LegPosition.SOUTH_EAST, BlockStateVariant.create().put(VariantSettings.MODEL, nwModel))
                        .register(true, TableBlock.LegPosition.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, nModel))
                        .register(true, TableBlock.LegPosition.SOUTH_WEST, BlockStateVariant.create().put(VariantSettings.MODEL, neModel))
                        .register(true, TableBlock.LegPosition.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, eModel))
                        .register(true, TableBlock.LegPosition.NORTH_WEST, BlockStateVariant.create().put(VariantSettings.MODEL, seModel))
                        .register(true, TableBlock.LegPosition.NONE, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.ALL, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.NORTH_EAST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.SOUTH_EAST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.SOUTH_WEST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.NORTH_WEST, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                        .register(false, TableBlock.LegPosition.NONE, BlockStateVariant.create().put(VariantSettings.MODEL, noneModel))
                );
    }

    public static BlockStateSupplier createCupboardModel(Block cupboard, Identifier openModel, Identifier closedModel) {
        return VariantsBlockStateSupplier.create(cupboard)
                .coordinate(BlockStateVariantMap.create(ModProperties.OPEN, Properties.HORIZONTAL_FACING)
                        .register(false, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, closedModel))
                        .register(false, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, closedModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(false, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, closedModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(false, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, closedModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(true, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, openModel))
                        .register(true, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, openModel).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(true, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, openModel).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(true, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, openModel).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                );
    }

    private static Model block(String parent, TextureKey ... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("clutter", "block/parent/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static Model item(String parent, TextureKey ... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("clutter", "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static Model block(String parent, String variant, TextureKey ... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("clutter", "block/parent/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    public static TextureMap logAndPlanksMap(Block logBlock, Block planksBlock) {
        Identifier log = TextureMap.getId(logBlock);
        Identifier planks = TextureMap.getId(planksBlock);
        return new TextureMap().put(LOG, log).put(PLANKS, planks);
    }

    public static TextureMap planksMap(Block planksBlock) {
        Identifier planks = TextureMap.getId(planksBlock);
        return new TextureMap().put(PLANKS, planks);
    }

    public static TextureMap cupboardMap(Block planksBlock) {
        Identifier planks = TextureMap.getId(planksBlock);
        String woodType = planks.getPath().replace("_planks", "");
        Identifier door = new Identifier(Clutter.MOD_ID, woodType + "_cupboard_door");
        Identifier inside = new Identifier(Clutter.MOD_ID, woodType + "_cupboard_inside");
        return new TextureMap().put(PLANKS, planks).put(CUPBOARD_DOOR, door).put(CUPBOARD_INSIDE, inside);
    }

    public static TextureMap windowSillMap(Block planksBlock, String flower) {
        Identifier flowerId = new Identifier("block/" + flower);
        Identifier planks = TextureMap.getId(planksBlock);
        return new TextureMap().put(PLANKS, planks).put(FLOWER, flowerId);
    }

    public static TextureMap emptyWindowSillMap(Block planksBlock) {
        Identifier planks = TextureMap.getId(planksBlock);
        return new TextureMap().put(PLANKS, planks);
    }
}
