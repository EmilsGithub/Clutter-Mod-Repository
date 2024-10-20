package net.emilsg.clutter.data;

import net.emilsg.clutter.block.ClutterWoodType;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.util.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class BlockTagDataGen extends FabricTagProvider.BlockTagProvider {

    public BlockTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        for (Block block : ClutterWoodType.CLUTTER_WOOD_BLOCKS) {
            Identifier blockID = Registries.BLOCK.getId(block);
            if(!blockID.getPath().contains("warped") && !blockID.getPath().contains("crimson") && !blockID.getNamespace().contains("minecraft")) getOrCreateTagBuilder(ModBlockTags.FLAMMABLE).add(block);
            if(!blockID.getNamespace().contains("minecraft")) getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
        }

        for (ClutterWoodType woodType : ClutterWoodType.CLUTTER_WOOD_TYPES) {
            if(woodType.chair() != null) getOrCreateTagBuilder(ModBlockTags.WOODEN_CHAIRS).add(woodType.chair());
            if(woodType.strippedChair() != null) getOrCreateTagBuilder(ModBlockTags.WOODEN_CHAIRS).add(woodType.strippedChair());
            if(woodType.door() != null) getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(woodType.door());
            if(woodType.trapdoor() != null) getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(woodType.trapdoor());
            if(woodType.shortBench() != null) getOrCreateTagBuilder(ModBlockTags.SHORT_BENCHES).add(woodType.shortBench());
            if(woodType.windowSill() != null) getOrCreateTagBuilder(ModBlockTags.WINDOW_SILLS).add(woodType.windowSill());
            if(woodType.mosaic() != null) getOrCreateTagBuilder(ModBlockTags.WOODEN_MOSAICS).add(woodType.mosaic());
            if(woodType.mosaicSlab() != null) getOrCreateTagBuilder(ModBlockTags.WOODEN_MOSAICS).add(woodType.mosaicSlab());
            if(woodType.mosaicStairs() != null) getOrCreateTagBuilder(ModBlockTags.WOODEN_MOSAICS).add(woodType.mosaicStairs());
            if(woodType.bench() != null) getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_BENCHES).add(woodType.bench());
            if(woodType.table() != null) getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_TABLES).add(woodType.table());
            if(woodType.chair() != null) getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_CHAIRS).add(woodType.chair());
            if(woodType.cupboard() != null) getOrCreateTagBuilder(ModBlockTags.CUPBOARDS).add(woodType.cupboard());
            if(woodType.wallCupboard() != null) getOrCreateTagBuilder(ModBlockTags.CUPBOARDS).add(woodType.wallCupboard());
            if(woodType.slab() != null) getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(woodType.slab());
            if(woodType.mosaicSlab() != null) getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(woodType.mosaicSlab());
            if(woodType.stairs() != null) getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(woodType.stairs());
            if(woodType.mosaicStairs() != null) getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(woodType.mosaicStairs());
            if(woodType.strippedBench() != null) getOrCreateTagBuilder(ModBlockTags.BENCHES).add(woodType.strippedBench());
            if(woodType.bench() != null) getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_BENCHES).add(woodType.bench());
            if(woodType.trellis() != null) getOrCreateTagBuilder(ModBlockTags.TRELLISES).add(woodType.trellis());
            if(woodType.strippedTable() != null) getOrCreateTagBuilder(ModBlockTags.TABLES).add(woodType.strippedTable());
            if(woodType.table() != null) getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_TABLES).add(woodType.table());
            if(woodType.wallBookshelf() != null) getOrCreateTagBuilder(ModBlockTags.BOOKSHELVES).add(woodType.wallBookshelf());
        }

        /** Vanilla **/

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
                        ModBlocks.OVERGROWN_PACKED_MUD,
                        ModBlocks.QUARTZ_CRYSTAL,
                        ModBlocks.ANCHOR_BLOCK,
                        ModBlocks.AQUATIC_TORCH,
                        ModBlocks.EXPOSED_AQUATIC_TORCH,
                        ModBlocks.WEATHERED_AQUATIC_TORCH,
                        ModBlocks.OXIDIZED_AQUATIC_TORCH,
                        ModBlocks.WAXED_AQUATIC_TORCH,
                        ModBlocks.WAXED_EXPOSED_AQUATIC_TORCH,
                        ModBlocks.WAXED_WEATHERED_AQUATIC_TORCH,
                        ModBlocks.WAXED_OXIDIZED_AQUATIC_TORCH,
                        ModBlocks.PRISMARINE_TORCH,
                        ModBlocks.MAILBOX,
                        ModBlocks.BRICK_KILN,
                        ModBlocks.BASALT_SULPHUR_ORE,
                        ModBlocks.BLACKSTONE_SULPHUR_ORE,
                        ModBlocks.BUDDING_ONYX,
                        ModBlocks.ONYX_CLUSTER,
                        ModBlocks.LARGE_ONYX_BUD,
                        ModBlocks.MEDIUM_ONYX_BUD,
                        ModBlocks.SMALL_ONYX_BUD,
                        ModBlocks.ONYX_BLOCK,
                        ModBlocks.ONYX_SLAB,
                        ModBlocks.ONYX_STAIRS,
                        ModBlocks.ONYX_WALL,
                        ModBlocks.POLISHED_ONYX,
                        ModBlocks.POLISHED_ONYX_SLAB,
                        ModBlocks.POLISHED_ONYX_STAIRS,
                        ModBlocks.POLISHED_ONYX_WALL,
                        ModBlocks.BLACK_ONYX_BLOCK,
                        ModBlocks.BLACK_ONYX_SLAB,
                        ModBlocks.BLACK_ONYX_STAIRS,
                        ModBlocks.BLACK_ONYX_WALL,
                        ModBlocks.POLISHED_BLACK_ONYX,
                        ModBlocks.POLISHED_BLACK_ONYX_SLAB,
                        ModBlocks.POLISHED_BLACK_ONYX_STAIRS,
                        ModBlocks.POLISHED_BLACK_ONYX_WALL,
                        ModBlocks.FROG_STATUE,
                        ModBlocks.CUP_CORAL_BLOCK,
                        ModBlocks.GHOST_CORAL_BLOCK,
                        ModBlocks.STONE_CORAL_BLOCK,
                        ModBlocks.SLATE_CORAL_BLOCK,
                        ModBlocks.THORN_CORAL_BLOCK,
                        ModBlocks.COCOA_CORAL_BLOCK,
                        ModBlocks.PASSION_CORAL_BLOCK,
                        ModBlocks.TOXIC_CORAL_BLOCK,
                        ModBlocks.GEM_CORAL_BLOCK,
                        ModBlocks.DIAMOND_CORAL_BLOCK,
                        ModBlocks.ANCHOR_CORAL_BLOCK,
                        ModBlocks.DEAD_CUP_CORAL_BLOCK,
                        ModBlocks.DEAD_GHOST_CORAL_BLOCK,
                        ModBlocks.DEAD_STONE_CORAL_BLOCK,
                        ModBlocks.DEAD_SLATE_CORAL_BLOCK,
                        ModBlocks.DEAD_THORN_CORAL_BLOCK,
                        ModBlocks.DEAD_COCOA_CORAL_BLOCK,
                        ModBlocks.DEAD_PASSION_CORAL_BLOCK,
                        ModBlocks.DEAD_TOXIC_CORAL_BLOCK,
                        ModBlocks.DEAD_GEM_CORAL_BLOCK,
                        ModBlocks.DEAD_DIAMOND_CORAL_BLOCK,
                        ModBlocks.DEAD_ANCHOR_CORAL_BLOCK,
                        ModBlocks.DEAD_CUP_CORAL_FAN,
                        ModBlocks.DEAD_GHOST_CORAL_FAN,
                        ModBlocks.DEAD_STONE_CORAL_FAN,
                        ModBlocks.DEAD_SLATE_CORAL_FAN,
                        ModBlocks.DEAD_THORN_CORAL_FAN,
                        ModBlocks.DEAD_COCOA_CORAL_FAN,
                        ModBlocks.DEAD_PASSION_CORAL_FAN,
                        ModBlocks.DEAD_TOXIC_CORAL_FAN,
                        ModBlocks.DEAD_GEM_CORAL_FAN,
                        ModBlocks.DEAD_DIAMOND_CORAL_FAN,
                        ModBlocks.DEAD_ANCHOR_CORAL_FAN,
                        ModBlocks.DEAD_CUP_CORAL_WALL_FAN,
                        ModBlocks.DEAD_GHOST_CORAL_WALL_FAN,
                        ModBlocks.DEAD_STONE_CORAL_WALL_FAN,
                        ModBlocks.DEAD_SLATE_CORAL_WALL_FAN,
                        ModBlocks.DEAD_THORN_CORAL_WALL_FAN,
                        ModBlocks.DEAD_COCOA_CORAL_WALL_FAN,
                        ModBlocks.DEAD_PASSION_CORAL_WALL_FAN,
                        ModBlocks.DEAD_TOXIC_CORAL_WALL_FAN,
                        ModBlocks.DEAD_GEM_CORAL_WALL_FAN,
                        ModBlocks.DEAD_DIAMOND_CORAL_WALL_FAN,
                        ModBlocks.DEAD_ANCHOR_CORAL_WALL_FAN,
                        ModBlocks.DEAD_CUP_CORAL,
                        ModBlocks.DEAD_GHOST_CORAL,
                        ModBlocks.DEAD_STONE_CORAL,
                        ModBlocks.DEAD_SLATE_CORAL,
                        ModBlocks.DEAD_THORN_CORAL,
                        ModBlocks.DEAD_COCOA_CORAL,
                        ModBlocks.DEAD_PASSION_CORAL,
                        ModBlocks.DEAD_TOXIC_CORAL,
                        ModBlocks.DEAD_GEM_CORAL,
                        ModBlocks.DEAD_DIAMOND_CORAL,
                        ModBlocks.DEAD_ANCHOR_CORAL,
                        ModBlocks.GOLDEN_CHAIN,
                        ModBlocks.COPPER_CHAIN,
                        ModBlocks.EXPOSED_COPPER_CHAIN,
                        ModBlocks.WEATHERED_COPPER_CHAIN,
                        ModBlocks.OXIDIZED_COPPER_CHAIN,
                        ModBlocks.SILVER_CHAIN,
                        ModBlocks.WAXED_COPPER_CHAIN,
                        ModBlocks.WAXED_EXPOSED_COPPER_CHAIN,
                        ModBlocks.WAXED_WEATHERED_COPPER_CHAIN,
                        ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN,
                        ModBlocks.LARGE_CHAIN,
                        ModBlocks.LARGE_SILVER_CHAIN,
                        ModBlocks.LARGE_GOLDEN_CHAIN,
                        ModBlocks.LARGE_COPPER_CHAIN,
                        ModBlocks.EXPOSED_LARGE_COPPER_CHAIN,
                        ModBlocks.WEATHERED_LARGE_COPPER_CHAIN,
                        ModBlocks.OXIDIZED_LARGE_COPPER_CHAIN,
                        ModBlocks.WAXED_LARGE_COPPER_CHAIN,
                        ModBlocks.WAXED_EXPOSED_LARGE_COPPER_CHAIN,
                        ModBlocks.WAXED_WEATHERED_LARGE_COPPER_CHAIN,
                        ModBlocks.WAXED_OXIDIZED_LARGE_COPPER_CHAIN,
                        ModBlocks.IRON_BUTTON,
                        ModBlocks.GOLDEN_BUTTON,
                        ModBlocks.COPPER_BUTTON,
                        ModBlocks.EXPOSED_COPPER_BUTTON,
                        ModBlocks.WEATHERED_COPPER_BUTTON,
                        ModBlocks.OXIDIZED_COPPER_BUTTON,
                        ModBlocks.WAXED_COPPER_BUTTON,
                        ModBlocks.WAXED_EXPOSED_COPPER_BUTTON,
                        ModBlocks.WAXED_WEATHERED_COPPER_BUTTON,
                        ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON,
                        ModBlocks.COPPER_BARS,
                        ModBlocks.EXPOSED_COPPER_BARS,
                        ModBlocks.WEATHERED_COPPER_BARS,
                        ModBlocks.OXIDIZED_COPPER_BARS,
                        ModBlocks.WAXED_COPPER_BARS,
                        ModBlocks.WAXED_EXPOSED_COPPER_BARS,
                        ModBlocks.WAXED_WEATHERED_COPPER_BARS,
                        ModBlocks.WAXED_OXIDIZED_COPPER_BARS,
                        ModBlocks.COPPER_TRAPDOOR,
                        ModBlocks.EXPOSED_COPPER_TRAPDOOR,
                        ModBlocks.WEATHERED_COPPER_TRAPDOOR,
                        ModBlocks.OXIDIZED_COPPER_TRAPDOOR,
                        ModBlocks.WAXED_COPPER_TRAPDOOR,
                        ModBlocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                        ModBlocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                        ModBlocks.WAXED_OXIDIZED_COPPER_TRAPDOOR,
                        ModBlocks.COPPER_PRESSURE_PLATE,
                        ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE,
                        ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE,
                        ModBlocks.OXIDIZED_COPPER_PRESSURE_PLATE,
                        ModBlocks.WAXED_COPPER_PRESSURE_PLATE,
                        ModBlocks.WAXED_EXPOSED_COPPER_PRESSURE_PLATE,
                        ModBlocks.WAXED_WEATHERED_COPPER_PRESSURE_PLATE,
                        ModBlocks.WAXED_OXIDIZED_COPPER_PRESSURE_PLATE,
                        ModBlocks.SILVER_BLOCK,
                        ModBlocks.SILVER_TRAPDOOR,
                        ModBlocks.SILVER_DOOR,
                        ModBlocks.GOLDEN_DOOR,
                        ModBlocks.GOLDEN_TRAPDOOR,
                        ModBlocks.RAW_SILVER_BLOCK,
                        ModBlocks.SILVER_ORE,
                        ModBlocks.DEEPSLATE_SILVER_ORE,
                        ModBlocks.TUBE_TV,
                        ModBlocks.SILVER_PRESSURE_PLATE,
                        ModBlocks.TOILET
                )
                .addTag(ModBlockTags.CHIMNEYS)
                .addTag(ModBlockTags.CHANDELIERS)
                .addTag(ModBlockTags.LARGE_CHANDELIERS)
                .addTag(ModBlockTags.CANDELABRAS)
                .addTag(ModBlockTags.CHAINS)
                .addTag(ModBlockTags.LARGE_CHAINS)
                .addTag(ModBlockTags.COPPER_DOORS)
                .addTag(ModBlockTags.WALL_CANDLES)
                .addTag(ModBlockTags.LANTERNS);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(
                        ModBlocks.BONFIRE,
                        ModBlocks.SOUL_BONFIRE
                )
                .addTag(ModBlockTags.ARMCHAIRS)
                .addTag(ModBlockTags.LAMPS)
                .addTag(ModBlockTags.FOOD_BOXES);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(
                ModBlocks.SULPHUR_BLOCK,
                ModBlocks.ONYX_ORE);

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(
                ModBlocks.RIPE_KIWI_LEAVES,
                ModBlocks.KIWI_LEAVES,
                ModBlocks.SMALL_SPONGE);

        getOrCreateTagBuilder(BlockTags.CORAL_BLOCKS).add(
                ModBlocks.CUP_CORAL_BLOCK, ModBlocks.GHOST_CORAL_BLOCK, ModBlocks.STONE_CORAL_BLOCK, ModBlocks.SLATE_CORAL_BLOCK,
                ModBlocks.THORN_CORAL_BLOCK, ModBlocks.COCOA_CORAL_BLOCK, ModBlocks.PASSION_CORAL_BLOCK, ModBlocks.TOXIC_CORAL_BLOCK,
                ModBlocks.GEM_CORAL_BLOCK, ModBlocks.DIAMOND_CORAL_BLOCK, ModBlocks.ANCHOR_CORAL_BLOCK);

        getOrCreateTagBuilder(BlockTags.CORALS).add(
                ModBlocks.CUP_CORAL_FAN, ModBlocks.GHOST_CORAL_FAN, ModBlocks.STONE_CORAL_FAN, ModBlocks.SLATE_CORAL_FAN,
                ModBlocks.THORN_CORAL_FAN, ModBlocks.COCOA_CORAL_FAN, ModBlocks.PASSION_CORAL_FAN, ModBlocks.TOXIC_CORAL_FAN,
                ModBlocks.GEM_CORAL_FAN, ModBlocks.DIAMOND_CORAL_FAN, ModBlocks.ANCHOR_CORAL_FAN)
        ;

        getOrCreateTagBuilder(BlockTags.CORAL_PLANTS).add(
                ModBlocks.CUP_CORAL, ModBlocks.GHOST_CORAL, ModBlocks.STONE_CORAL, ModBlocks.SLATE_CORAL,
                ModBlocks.THORN_CORAL, ModBlocks.COCOA_CORAL, ModBlocks.PASSION_CORAL, ModBlocks.TOXIC_CORAL,
                ModBlocks.GEM_CORAL, ModBlocks.DIAMOND_CORAL, ModBlocks.ANCHOR_CORAL
        );

        getOrCreateTagBuilder(BlockTags.WALL_CORALS).add(
                ModBlocks.CUP_CORAL_WALL_FAN, ModBlocks.GHOST_CORAL_WALL_FAN, ModBlocks.STONE_CORAL_WALL_FAN, ModBlocks.SLATE_CORAL_WALL_FAN,
                ModBlocks.THORN_CORAL_WALL_FAN, ModBlocks.COCOA_CORAL_WALL_FAN, ModBlocks.PASSION_CORAL_WALL_FAN, ModBlocks.TOXIC_CORAL_WALL_FAN,
                ModBlocks.GEM_CORAL_WALL_FAN, ModBlocks.DIAMOND_CORAL_WALL_FAN, ModBlocks.ANCHOR_CORAL_WALL_FAN
        );

        getOrCreateTagBuilder(BlockTags.FROG_PREFER_JUMP_TO).add(
                ModBlocks.GIANT_LILY_PAD,
                ModBlocks.GIANT_LILY_PAD_SEEDLING,
                ModBlocks.SMALL_LILY_PADS);

        getOrCreateTagBuilder(BlockTags.DIRT).add(
                ModBlocks.OVERGROWN_PACKED_MUD);

        getOrCreateTagBuilder(BlockTags.ANIMALS_SPAWNABLE_ON).add(
                ModBlocks.OVERGROWN_PACKED_MUD);

        getOrCreateTagBuilder(BlockTags.SNIFFER_DIGGABLE_BLOCK).add(
                ModBlocks.OVERGROWN_PACKED_MUD);

        getOrCreateTagBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(
                ModBlocks.OVERGROWN_PACKED_MUD);

        getOrCreateTagBuilder(BlockTags.FROGS_SPAWNABLE_ON).add(
                ModBlocks.GIANT_LILY_PAD,
                ModBlocks.GIANT_LILY_PAD_SEEDLING,
                ModBlocks.SMALL_LILY_PADS);

        getOrCreateTagBuilder(BlockTags.WOOL).add(
                ModBlocks.FLOOR_SEATING);

        getOrCreateTagBuilder(BlockTags.ENDERMAN_HOLDABLE).add(
                ModBlocks.GLOWLILY,
                ModBlocks.THORNBLOOM);

        getOrCreateTagBuilder(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(
                ModBlocks.ACACIA_WALL_BOOKSHELF,
                ModBlocks.BIRCH_WALL_BOOKSHELF,
                ModBlocks.CRIMSON_WALL_BOOKSHELF,
                ModBlocks.DARK_OAK_WALL_BOOKSHELF,
                ModBlocks.JUNGLE_WALL_BOOKSHELF,
                ModBlocks.MANGROVE_WALL_BOOKSHELF,
                ModBlocks.OAK_WALL_BOOKSHELF,
                ModBlocks.SPRUCE_WALL_BOOKSHELF,
                ModBlocks.WARPED_WALL_BOOKSHELF,
                ModBlocks.BAMBOO_WALL_BOOKSHELF,
                Blocks.BOOKSHELF,
                ModBlocks.BOOK_PILE);

        getOrCreateTagBuilder(BlockTags.ENCHANTMENT_POWER_TRANSMITTER).add(
                ModBlocks.BOOK_PILE);

        getOrCreateTagBuilder(BlockTags.LEAVES).add(
                ModBlocks.KIWI_LEAVES,
                ModBlocks.RIPE_KIWI_LEAVES,
                ModBlocks.REDWOOD_LEAVES);

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(
                ModBlocks.REDWOOD_FENCE);

        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(
                ModBlocks.REDWOOD_FENCE_GATE);

        getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(
                ModBlocks.REDWOOD_BUTTON);

        getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(
                ModBlocks.REDWOOD_PRESSURE_PLATE);

        getOrCreateTagBuilder(BlockTags.LOGS).add(
                ModBlocks.REDWOOD_LOG,
                ModBlocks.STRIPPED_REDWOOD_LOG,
                ModBlocks.REDWOOD_WOOD,
                ModBlocks.STRIPPED_REDWOOD_WOOD);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(
                ModBlocks.REDWOOD_LOG,
                ModBlocks.STRIPPED_REDWOOD_LOG,
                ModBlocks.REDWOOD_WOOD,
                ModBlocks.STRIPPED_REDWOOD_WOOD);

        getOrCreateTagBuilder(BlockTags.PLANKS).add(
                ModBlocks.REDWOOD_PLANKS);

        getOrCreateTagBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
                ModBlocks.SULPHUR_BLOCK);

        getOrCreateTagBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(
                ModBlocks.REDWOOD_LOG,
                ModBlocks.REDWOOD_WOOD,
                ModBlocks.STRIPPED_REDWOOD_LOG,
                ModBlocks.STRIPPED_REDWOOD_WOOD);

        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(
                ModBlocks.SMALL_BLUE_LUPINE,
                ModBlocks.SMALL_MAGENTA_LUPINE,
                ModBlocks.SMALL_PURPLE_LUPINE,
                ModBlocks.SMALL_YELLOW_LUPINE,
                ModBlocks.SMALL_RED_LUPINE,
                ModBlocks.SMALL_WHITE_LUPINE);

        getOrCreateTagBuilder(BlockTags.TALL_FLOWERS).add(
                ModBlocks.BLUE_LUPINE,
                ModBlocks.MAGENTA_LUPINE,
                ModBlocks.PURPLE_LUPINE,
                ModBlocks.YELLOW_LUPINE,
                ModBlocks.RED_LUPINE,
                ModBlocks.WHITE_LUPINE);

        getOrCreateTagBuilder(BlockTags.REPLACEABLE_BY_TREES).add(
                ModBlocks.BLUE_LUPINE,
                ModBlocks.MAGENTA_LUPINE,
                ModBlocks.PURPLE_LUPINE,
                ModBlocks.YELLOW_LUPINE,
                ModBlocks.RED_LUPINE,
                ModBlocks.WHITE_LUPINE);

        getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(
                ModBlocks.BLUE_LUPINE,
                ModBlocks.MAGENTA_LUPINE,
                ModBlocks.PURPLE_LUPINE,
                ModBlocks.YELLOW_LUPINE,
                ModBlocks.RED_LUPINE,
                ModBlocks.WHITE_LUPINE);

        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(
                ModBlocks.KIWI_TREE_SAPLING,
                ModBlocks.REDWOOD_SAPLING);


        getOrCreateTagBuilder(BlockTags.CROPS).add(
                ModBlocks.HOPS_CROP,
                ModBlocks.COTTON_CROP,
                ModBlocks.THORNBLOOM_CROP,
                ModBlocks.GLOWLILY_CROP,
                ModBlocks.KIWI_CROP);

        getOrCreateTagBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS).add(
                ModBlocks.LUSH_MOSS);

        getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(
                        ModBlocks.RED_WALL_MUSHROOMS,
                        ModBlocks.BROWN_WALL_MUSHROOMS,
                        ModBlocks.SCULK_WALL_MUSHROOMS,
                        ModBlocks.CRIMSON_WALL_FUNGI,
                        ModBlocks.WARPED_WALL_FUNGI)
                .addTag(ModBlockTags.TRELLISES);

        getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(
                ModBlocks.HOPS_CROP,
                ModBlocks.COTTON_CROP,
                ModBlocks.THORNBLOOM_CROP,
                ModBlocks.GLOWLILY_CROP,
                ModBlocks.KIWI_CROP);

        getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(
                ModBlocks.HOPS_CROP,
                ModBlocks.COTTON_CROP,
                ModBlocks.THORNBLOOM_CROP,
                ModBlocks.GLOWLILY_CROP,
                ModBlocks.KIWI_TREE_SAPLING,
                ModBlocks.KIWI_CROP,
                ModBlocks.THORNBLOOM,
                ModBlocks.GLOWLILY);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(
                ModBlocks.SILVER_BLOCK,
                ModBlocks.SILVER_ORE,
                ModBlocks.DEEPSLATE_SILVER_ORE);

        getOrCreateTagBuilder(BlockTags.PIGLIN_REPELLENTS).add(
                ModBlocks.SOUL_BONFIRE,
                ModBlocks.SILVER_SOUL_LANTERN,
                ModBlocks.GOLDEN_SOUL_LANTERN,
                ModBlocks.COPPER_SOUL_LANTERN,
                ModBlocks.EXPOSED_COPPER_SOUL_LANTERN,
                ModBlocks.OXIDIZED_COPPER_SOUL_LANTERN,
                ModBlocks.WEATHERED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_WEATHERED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_OXIDIZED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_EXPOSED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_COPPER_SOUL_LANTERN);

        getOrCreateTagBuilder(BlockTags.REPLACEABLE).add(
                ModBlocks.LUSH_MOSS);

        getOrCreateTagBuilder(BlockTags.WALLS).add(
                ModBlocks.ONYX_WALL,
                ModBlocks.BLACK_ONYX_WALL,
                ModBlocks.POLISHED_BLACK_ONYX_WALL,
                ModBlocks.POLISHED_ONYX_WALL);

        getOrCreateTagBuilder(BlockTags.STAIRS).addTag(
                BlockTags.WOODEN_STAIRS
        );

        getOrCreateTagBuilder(BlockTags.SLABS)
                .addTag(BlockTags.WOODEN_SLABS)
                .add(
                ModBlocks.ONYX_SLAB,
                ModBlocks.POLISHED_ONYX_SLAB,
                ModBlocks.BLACK_ONYX_SLAB,
                ModBlocks.POLISHED_BLACK_ONYX_SLAB);

        getOrCreateTagBuilder(BlockTags.TRAPDOORS).add(
                ModBlocks.SILVER_TRAPDOOR,
                ModBlocks.GOLDEN_TRAPDOOR,
                ModBlocks.COPPER_TRAPDOOR,
                ModBlocks.EXPOSED_COPPER_TRAPDOOR,
                ModBlocks.WEATHERED_COPPER_TRAPDOOR,
                ModBlocks.OXIDIZED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_OXIDIZED_COPPER_TRAPDOOR);

        getOrCreateTagBuilder(BlockTags.DOORS).add(
                ModBlocks.SILVER_DOOR,
                ModBlocks.GOLDEN_DOOR,
                ModBlocks.COPPER_DOOR,
                ModBlocks.EXPOSED_COPPER_DOOR,
                ModBlocks.WEATHERED_COPPER_DOOR,
                ModBlocks.OXIDIZED_COPPER_DOOR,
                ModBlocks.WAXED_COPPER_DOOR,
                ModBlocks.WAXED_EXPOSED_COPPER_DOOR,
                ModBlocks.WAXED_WEATHERED_COPPER_DOOR,
                ModBlocks.WAXED_OXIDIZED_COPPER_DOOR);

        /** Modded **/

        getOrCreateTagBuilder(ModBlockTags.BENCHES).addTag(ModBlockTags.STRIPPABLE_BENCHES);
        getOrCreateTagBuilder(ModBlockTags.TABLES).addTag(ModBlockTags.STRIPPABLE_TABLES);


        getOrCreateTagBuilder(ModBlockTags.GREEN_FIRE_BASE_BLOCKS).add(
                Blocks.COPPER_BLOCK,
                Blocks.CUT_COPPER,
                Blocks.CUT_COPPER_SLAB,
                Blocks.CUT_COPPER_STAIRS,
                Blocks.EXPOSED_COPPER,
                Blocks.EXPOSED_CUT_COPPER,
                Blocks.EXPOSED_CUT_COPPER_SLAB,
                Blocks.EXPOSED_CUT_COPPER_STAIRS,
                Blocks.WEATHERED_COPPER,
                Blocks.WEATHERED_CUT_COPPER,
                Blocks.WEATHERED_CUT_COPPER_SLAB,
                Blocks.WEATHERED_CUT_COPPER_STAIRS,
                Blocks.OXIDIZED_COPPER,
                Blocks.OXIDIZED_CUT_COPPER,
                Blocks.OXIDIZED_CUT_COPPER_SLAB,
                Blocks.OXIDIZED_CUT_COPPER_STAIRS,
                Blocks.WAXED_COPPER_BLOCK,
                Blocks.WAXED_CUT_COPPER,
                Blocks.WAXED_CUT_COPPER_SLAB,
                Blocks.WAXED_CUT_COPPER_STAIRS,
                Blocks.WAXED_EXPOSED_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB,
                Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS,
                Blocks.WAXED_WEATHERED_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB,
                Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Blocks.WAXED_OXIDIZED_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.RAW_COPPER_BLOCK,
                ModBlocks.COPPER_TRAPDOOR,
                ModBlocks.EXPOSED_COPPER_TRAPDOOR,
                ModBlocks.WEATHERED_COPPER_TRAPDOOR,
                ModBlocks.OXIDIZED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_OXIDIZED_COPPER_TRAPDOOR
        );

        getOrCreateTagBuilder(ModBlockTags.LANTERNS).add(
                ModBlocks.SILVER_LANTERN,
                ModBlocks.GOLDEN_LANTERN,
                ModBlocks.COPPER_LANTERN,
                ModBlocks.EXPOSED_COPPER_LANTERN,
                ModBlocks.WEATHERED_COPPER_LANTERN,
                ModBlocks.OXIDIZED_COPPER_LANTERN,
                ModBlocks.WAXED_COPPER_LANTERN,
                ModBlocks.WAXED_EXPOSED_COPPER_LANTERN,
                ModBlocks.WAXED_WEATHERED_COPPER_LANTERN,
                ModBlocks.WAXED_OXIDIZED_COPPER_LANTERN,
                ModBlocks.SILVER_SOUL_LANTERN,
                ModBlocks.GOLDEN_SOUL_LANTERN,
                ModBlocks.COPPER_SOUL_LANTERN,
                ModBlocks.EXPOSED_COPPER_SOUL_LANTERN,
                ModBlocks.WEATHERED_COPPER_SOUL_LANTERN,
                ModBlocks.OXIDIZED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_EXPOSED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_WEATHERED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_OXIDIZED_COPPER_SOUL_LANTERN
        );

        getOrCreateTagBuilder(ModBlockTags.ARMCHAIRS).add(
                ModBlocks.WHITE_ARMCHAIR,
                ModBlocks.LIGHT_GRAY_ARMCHAIR,
                ModBlocks.GRAY_ARMCHAIR,
                ModBlocks.BLACK_ARMCHAIR,
                ModBlocks.BROWN_ARMCHAIR,
                ModBlocks.RED_ARMCHAIR,
                ModBlocks.ORANGE_ARMCHAIR,
                ModBlocks.YELLOW_ARMCHAIR,
                ModBlocks.LIME_ARMCHAIR,
                ModBlocks.GREEN_ARMCHAIR,
                ModBlocks.CYAN_ARMCHAIR,
                ModBlocks.LIGHT_BLUE_ARMCHAIR,
                ModBlocks.BLUE_ARMCHAIR,
                ModBlocks.PURPLE_ARMCHAIR,
                ModBlocks.MAGENTA_ARMCHAIR,
                ModBlocks.PINK_ARMCHAIR
        );

        getOrCreateTagBuilder(ModBlockTags.LAMPS).add(
                ModBlocks.WHITE_LAMP,
                ModBlocks.LIGHT_GRAY_LAMP,
                ModBlocks.GRAY_LAMP,
                ModBlocks.BLACK_LAMP,
                ModBlocks.BROWN_LAMP,
                ModBlocks.RED_LAMP,
                ModBlocks.ORANGE_LAMP,
                ModBlocks.YELLOW_LAMP,
                ModBlocks.LIME_LAMP,
                ModBlocks.GREEN_LAMP,
                ModBlocks.CYAN_LAMP,
                ModBlocks.LIGHT_BLUE_LAMP,
                ModBlocks.BLUE_LAMP,
                ModBlocks.PURPLE_LAMP,
                ModBlocks.MAGENTA_LAMP,
                ModBlocks.PINK_LAMP,
                ModBlocks.TALL_WHITE_LAMP,
                ModBlocks.TALL_LIGHT_GRAY_LAMP,
                ModBlocks.TALL_GRAY_LAMP,
                ModBlocks.TALL_BLACK_LAMP,
                ModBlocks.TALL_BROWN_LAMP,
                ModBlocks.TALL_RED_LAMP,
                ModBlocks.TALL_ORANGE_LAMP,
                ModBlocks.TALL_YELLOW_LAMP,
                ModBlocks.TALL_LIME_LAMP,
                ModBlocks.TALL_GREEN_LAMP,
                ModBlocks.TALL_CYAN_LAMP,
                ModBlocks.TALL_LIGHT_BLUE_LAMP,
                ModBlocks.TALL_BLUE_LAMP,
                ModBlocks.TALL_PURPLE_LAMP,
                ModBlocks.TALL_MAGENTA_LAMP,
                ModBlocks.TALL_PINK_LAMP
        );

        getOrCreateTagBuilder(ModBlockTags.WALL_CANDLES).add(
                ModBlocks.GOLDEN_WALL_CANDLE,
                ModBlocks.WHITE_GOLDEN_WALL_CANDLE,
                ModBlocks.LIGHT_GRAY_GOLDEN_WALL_CANDLE,
                ModBlocks.GRAY_GOLDEN_WALL_CANDLE,
                ModBlocks.BLACK_GOLDEN_WALL_CANDLE,
                ModBlocks.BROWN_GOLDEN_WALL_CANDLE,
                ModBlocks.RED_GOLDEN_WALL_CANDLE,
                ModBlocks.ORANGE_GOLDEN_WALL_CANDLE,
                ModBlocks.YELLOW_GOLDEN_WALL_CANDLE,
                ModBlocks.LIME_GOLDEN_WALL_CANDLE,
                ModBlocks.GREEN_GOLDEN_WALL_CANDLE,
                ModBlocks.CYAN_GOLDEN_WALL_CANDLE,
                ModBlocks.LIGHT_BLUE_GOLDEN_WALL_CANDLE,
                ModBlocks.BLUE_GOLDEN_WALL_CANDLE,
                ModBlocks.PURPLE_GOLDEN_WALL_CANDLE,
                ModBlocks.MAGENTA_GOLDEN_WALL_CANDLE,
                ModBlocks.PINK_GOLDEN_WALL_CANDLE,
                ModBlocks.SILVER_WALL_CANDLE,
                ModBlocks.WHITE_SILVER_WALL_CANDLE,
                ModBlocks.LIGHT_GRAY_SILVER_WALL_CANDLE,
                ModBlocks.GRAY_SILVER_WALL_CANDLE,
                ModBlocks.BLACK_SILVER_WALL_CANDLE,
                ModBlocks.BROWN_SILVER_WALL_CANDLE,
                ModBlocks.RED_SILVER_WALL_CANDLE,
                ModBlocks.ORANGE_SILVER_WALL_CANDLE,
                ModBlocks.YELLOW_SILVER_WALL_CANDLE,
                ModBlocks.LIME_SILVER_WALL_CANDLE,
                ModBlocks.GREEN_SILVER_WALL_CANDLE,
                ModBlocks.CYAN_SILVER_WALL_CANDLE,
                ModBlocks.LIGHT_BLUE_SILVER_WALL_CANDLE,
                ModBlocks.BLUE_SILVER_WALL_CANDLE,
                ModBlocks.PURPLE_SILVER_WALL_CANDLE,
                ModBlocks.MAGENTA_SILVER_WALL_CANDLE,
                ModBlocks.PINK_SILVER_WALL_CANDLE,
                ModBlocks.IRON_WALL_CANDLE,
                ModBlocks.WHITE_IRON_WALL_CANDLE,
                ModBlocks.LIGHT_GRAY_IRON_WALL_CANDLE,
                ModBlocks.GRAY_IRON_WALL_CANDLE,
                ModBlocks.BLACK_IRON_WALL_CANDLE,
                ModBlocks.BROWN_IRON_WALL_CANDLE,
                ModBlocks.RED_IRON_WALL_CANDLE,
                ModBlocks.ORANGE_IRON_WALL_CANDLE,
                ModBlocks.YELLOW_IRON_WALL_CANDLE,
                ModBlocks.LIME_IRON_WALL_CANDLE,
                ModBlocks.GREEN_IRON_WALL_CANDLE,
                ModBlocks.CYAN_IRON_WALL_CANDLE,
                ModBlocks.LIGHT_BLUE_IRON_WALL_CANDLE,
                ModBlocks.BLUE_IRON_WALL_CANDLE,
                ModBlocks.PURPLE_IRON_WALL_CANDLE,
                ModBlocks.MAGENTA_IRON_WALL_CANDLE,
                ModBlocks.PINK_IRON_WALL_CANDLE,
                ModBlocks.COPPER_WALL_CANDLE,
                ModBlocks.WHITE_COPPER_WALL_CANDLE,
                ModBlocks.LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.GRAY_COPPER_WALL_CANDLE,
                ModBlocks.BLACK_COPPER_WALL_CANDLE,
                ModBlocks.BROWN_COPPER_WALL_CANDLE,
                ModBlocks.RED_COPPER_WALL_CANDLE,
                ModBlocks.ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.LIME_COPPER_WALL_CANDLE,
                ModBlocks.GREEN_COPPER_WALL_CANDLE,
                ModBlocks.CYAN_COPPER_WALL_CANDLE,
                ModBlocks.LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.BLUE_COPPER_WALL_CANDLE,
                ModBlocks.PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.PINK_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_WHITE_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_BLACK_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_BROWN_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_RED_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_LIME_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_GREEN_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_CYAN_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.EXPOSED_PINK_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_WHITE_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_BLACK_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_BROWN_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_RED_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_LIME_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_GREEN_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_CYAN_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.WEATHERED_PINK_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_WHITE_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_BLACK_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_BROWN_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_RED_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_LIME_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_GREEN_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_CYAN_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.OXIDIZED_PINK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WHITE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_BLACK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_BROWN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_RED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_LIME_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_GREEN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_CYAN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_PINK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_WHITE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_BLACK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_BROWN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_RED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_LIME_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_GREEN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_CYAN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_EXPOSED_PINK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_WHITE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_BLACK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_BROWN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_RED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_LIME_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_GREEN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_CYAN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_WEATHERED_PINK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_WHITE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_LIGHT_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_GRAY_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_BLACK_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_BROWN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_RED_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_ORANGE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_YELLOW_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_LIME_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_GREEN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_CYAN_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_LIGHT_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_BLUE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_PURPLE_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_MAGENTA_COPPER_WALL_CANDLE,
                ModBlocks.WAXED_OXIDIZED_PINK_COPPER_WALL_CANDLE
        );

        getOrCreateTagBuilder(ModBlockTags.CANDELABRAS).add(
                ModBlocks.GOLDEN_CANDELABRA,
                ModBlocks.WHITE_GOLDEN_CANDELABRA,
                ModBlocks.LIGHT_GRAY_GOLDEN_CANDELABRA,
                ModBlocks.GRAY_GOLDEN_CANDELABRA,
                ModBlocks.BLACK_GOLDEN_CANDELABRA,
                ModBlocks.BROWN_GOLDEN_CANDELABRA,
                ModBlocks.RED_GOLDEN_CANDELABRA,
                ModBlocks.ORANGE_GOLDEN_CANDELABRA,
                ModBlocks.YELLOW_GOLDEN_CANDELABRA,
                ModBlocks.LIME_GOLDEN_CANDELABRA,
                ModBlocks.GREEN_GOLDEN_CANDELABRA,
                ModBlocks.CYAN_GOLDEN_CANDELABRA,
                ModBlocks.LIGHT_BLUE_GOLDEN_CANDELABRA,
                ModBlocks.BLUE_GOLDEN_CANDELABRA,
                ModBlocks.PURPLE_GOLDEN_CANDELABRA,
                ModBlocks.MAGENTA_GOLDEN_CANDELABRA,
                ModBlocks.PINK_GOLDEN_CANDELABRA,
                ModBlocks.SILVER_CANDELABRA,
                ModBlocks.WHITE_SILVER_CANDELABRA,
                ModBlocks.LIGHT_GRAY_SILVER_CANDELABRA,
                ModBlocks.GRAY_SILVER_CANDELABRA,
                ModBlocks.BLACK_SILVER_CANDELABRA,
                ModBlocks.BROWN_SILVER_CANDELABRA,
                ModBlocks.RED_SILVER_CANDELABRA,
                ModBlocks.ORANGE_SILVER_CANDELABRA,
                ModBlocks.YELLOW_SILVER_CANDELABRA,
                ModBlocks.LIME_SILVER_CANDELABRA,
                ModBlocks.GREEN_SILVER_CANDELABRA,
                ModBlocks.CYAN_SILVER_CANDELABRA,
                ModBlocks.LIGHT_BLUE_SILVER_CANDELABRA,
                ModBlocks.BLUE_SILVER_CANDELABRA,
                ModBlocks.PURPLE_SILVER_CANDELABRA,
                ModBlocks.MAGENTA_SILVER_CANDELABRA,
                ModBlocks.PINK_SILVER_CANDELABRA,
                ModBlocks.IRON_CANDELABRA,
                ModBlocks.WHITE_IRON_CANDELABRA,
                ModBlocks.LIGHT_GRAY_IRON_CANDELABRA,
                ModBlocks.GRAY_IRON_CANDELABRA,
                ModBlocks.BLACK_IRON_CANDELABRA,
                ModBlocks.BROWN_IRON_CANDELABRA,
                ModBlocks.RED_IRON_CANDELABRA,
                ModBlocks.ORANGE_IRON_CANDELABRA,
                ModBlocks.YELLOW_IRON_CANDELABRA,
                ModBlocks.LIME_IRON_CANDELABRA,
                ModBlocks.GREEN_IRON_CANDELABRA,
                ModBlocks.CYAN_IRON_CANDELABRA,
                ModBlocks.LIGHT_BLUE_IRON_CANDELABRA,
                ModBlocks.BLUE_IRON_CANDELABRA,
                ModBlocks.PURPLE_IRON_CANDELABRA,
                ModBlocks.MAGENTA_IRON_CANDELABRA,
                ModBlocks.PINK_IRON_CANDELABRA,
                ModBlocks.COPPER_CANDELABRA,
                ModBlocks.WHITE_COPPER_CANDELABRA,
                ModBlocks.LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.GRAY_COPPER_CANDELABRA,
                ModBlocks.BLACK_COPPER_CANDELABRA,
                ModBlocks.BROWN_COPPER_CANDELABRA,
                ModBlocks.RED_COPPER_CANDELABRA,
                ModBlocks.ORANGE_COPPER_CANDELABRA,
                ModBlocks.YELLOW_COPPER_CANDELABRA,
                ModBlocks.LIME_COPPER_CANDELABRA,
                ModBlocks.GREEN_COPPER_CANDELABRA,
                ModBlocks.CYAN_COPPER_CANDELABRA,
                ModBlocks.LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.BLUE_COPPER_CANDELABRA,
                ModBlocks.PURPLE_COPPER_CANDELABRA,
                ModBlocks.MAGENTA_COPPER_CANDELABRA,
                ModBlocks.PINK_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_WHITE_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_GRAY_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_BLACK_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_BROWN_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_RED_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_ORANGE_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_YELLOW_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_LIME_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_GREEN_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_CYAN_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_BLUE_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_PURPLE_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_MAGENTA_COPPER_CANDELABRA,
                ModBlocks.EXPOSED_PINK_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_WHITE_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_GRAY_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_BLACK_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_BROWN_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_RED_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_ORANGE_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_YELLOW_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_LIME_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_GREEN_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_CYAN_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_BLUE_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_PURPLE_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_MAGENTA_COPPER_CANDELABRA,
                ModBlocks.WEATHERED_PINK_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_WHITE_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_GRAY_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_BLACK_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_BROWN_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_RED_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_ORANGE_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_YELLOW_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_LIME_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_GREEN_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_CYAN_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_BLUE_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_PURPLE_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_MAGENTA_COPPER_CANDELABRA,
                ModBlocks.OXIDIZED_PINK_COPPER_CANDELABRA,
                ModBlocks.WAXED_COPPER_CANDELABRA,
                ModBlocks.WAXED_WHITE_COPPER_CANDELABRA,
                ModBlocks.WAXED_LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_BLACK_COPPER_CANDELABRA,
                ModBlocks.WAXED_BROWN_COPPER_CANDELABRA,
                ModBlocks.WAXED_RED_COPPER_CANDELABRA,
                ModBlocks.WAXED_ORANGE_COPPER_CANDELABRA,
                ModBlocks.WAXED_YELLOW_COPPER_CANDELABRA,
                ModBlocks.WAXED_LIME_COPPER_CANDELABRA,
                ModBlocks.WAXED_GREEN_COPPER_CANDELABRA,
                ModBlocks.WAXED_CYAN_COPPER_CANDELABRA,
                ModBlocks.WAXED_LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_PURPLE_COPPER_CANDELABRA,
                ModBlocks.WAXED_MAGENTA_COPPER_CANDELABRA,
                ModBlocks.WAXED_PINK_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_WHITE_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_BLACK_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_BROWN_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_RED_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_ORANGE_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_YELLOW_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_LIME_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_GREEN_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_CYAN_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_PURPLE_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_MAGENTA_COPPER_CANDELABRA,
                ModBlocks.WAXED_EXPOSED_PINK_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_WHITE_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_BLACK_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_BROWN_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_RED_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_ORANGE_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_YELLOW_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_LIME_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_GREEN_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_CYAN_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_PURPLE_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_MAGENTA_COPPER_CANDELABRA,
                ModBlocks.WAXED_WEATHERED_PINK_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_WHITE_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_LIGHT_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_GRAY_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_BLACK_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_BROWN_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_RED_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_ORANGE_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_YELLOW_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_LIME_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_GREEN_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_CYAN_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_LIGHT_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_BLUE_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_PURPLE_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_MAGENTA_COPPER_CANDELABRA,
                ModBlocks.WAXED_OXIDIZED_PINK_COPPER_CANDELABRA
        );

        getOrCreateTagBuilder(ModBlockTags.CHIMNEYS).add(
                ModBlocks.COBBLESTONE_CHIMNEY,
                ModBlocks.BRICK_CHIMNEY,
                ModBlocks.STONE_BRICK_CHIMNEY,
                ModBlocks.MOSSY_STONE_BRICK_CHIMNEY,
                ModBlocks.DEEPSLATE_BRICK_CHIMNEY,
                ModBlocks.MUD_BRICK_CHIMNEY,
                ModBlocks.NETHER_BRICK_CHIMNEY,
                ModBlocks.RED_NETHER_BRICK_CHIMNEY,
                ModBlocks.POLISHED_BLACKSTONE_BRICK_CHIMNEY,
                ModBlocks.END_STONE_BRICK_CHIMNEY,
                ModBlocks.DEEPSLATE_TILE_CHIMNEY,
                ModBlocks.ANDESITE_CHIMNEY,
                ModBlocks.DIORITE_CHIMNEY,
                ModBlocks.GRANITE_CHIMNEY,
                ModBlocks.BLACKSTONE_CHIMNEY,
                ModBlocks.PURPUR_CHIMNEY
        );

        getOrCreateTagBuilder(ModBlockTags.CHAINS).add(
                Blocks.CHAIN,
                ModBlocks.SILVER_CHAIN,
                ModBlocks.GOLDEN_CHAIN,
                ModBlocks.COPPER_CHAIN,
                ModBlocks.EXPOSED_COPPER_CHAIN,
                ModBlocks.WEATHERED_COPPER_CHAIN,
                ModBlocks.OXIDIZED_COPPER_CHAIN,
                ModBlocks.WAXED_COPPER_CHAIN,
                ModBlocks.WAXED_EXPOSED_COPPER_CHAIN,
                ModBlocks.WAXED_WEATHERED_COPPER_CHAIN,
                ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN
        );

        getOrCreateTagBuilder(ModBlockTags.LARGE_CHAINS).add(
                ModBlocks.LARGE_CHAIN,
                ModBlocks.LARGE_SILVER_CHAIN,
                ModBlocks.LARGE_GOLDEN_CHAIN,
                ModBlocks.LARGE_COPPER_CHAIN,
                ModBlocks.EXPOSED_LARGE_COPPER_CHAIN,
                ModBlocks.WEATHERED_LARGE_COPPER_CHAIN,
                ModBlocks.OXIDIZED_LARGE_COPPER_CHAIN,
                ModBlocks.WAXED_LARGE_COPPER_CHAIN,
                ModBlocks.WAXED_EXPOSED_LARGE_COPPER_CHAIN,
                ModBlocks.WAXED_WEATHERED_LARGE_COPPER_CHAIN,
                ModBlocks.WAXED_OXIDIZED_LARGE_COPPER_CHAIN
        );

        getOrCreateTagBuilder(ModBlockTags.FOOD_BOXES).add(
                ModBlocks.APPLE_FOOD_BOX,
                ModBlocks.BEETROOT_FOOD_BOX,
                ModBlocks.CARROT_FOOD_BOX,
                ModBlocks.POTATO_FOOD_BOX,
                ModBlocks.MELON_FOOD_BOX,
                ModBlocks.CHORUS_FRUIT_FOOD_BOX,
                ModBlocks.SWEET_BERRY_FOOD_BOX,
                ModBlocks.GLOW_BERRY_FOOD_BOX,
                ModBlocks.BREAD_FOOD_BOX,
                ModBlocks.FOOD_BOX
        );

        getOrCreateTagBuilder(ModBlockTags.KIWI_EGG_HATCH_BOOST).add(
                Blocks.HAY_BLOCK
        );

        getOrCreateTagBuilder(ModBlockTags.EMPEROR_PENGUIN_EGG_HATCH_BOOST).add(
                Blocks.SNOW
        );



        getOrCreateTagBuilder(ModBlockTags.IMMOVABLE).add(
                ModBlocks.BUDDING_ONYX,
                ModBlocks.ONYX_BLOCK,
                ModBlocks.ONYX_SLAB,
                ModBlocks.ONYX_STAIRS,
                ModBlocks.ONYX_WALL,
                ModBlocks.POLISHED_ONYX,
                ModBlocks.POLISHED_ONYX_SLAB,
                ModBlocks.POLISHED_ONYX_STAIRS,
                ModBlocks.POLISHED_ONYX_WALL,
                ModBlocks.BLACK_ONYX_BLOCK,
                ModBlocks.BLACK_ONYX_SLAB,
                ModBlocks.BLACK_ONYX_STAIRS,
                ModBlocks.BLACK_ONYX_WALL,
                ModBlocks.POLISHED_BLACK_ONYX,
                ModBlocks.POLISHED_BLACK_ONYX_SLAB,
                ModBlocks.POLISHED_BLACK_ONYX_STAIRS,
                ModBlocks.POLISHED_BLACK_ONYX_WALL
        );

        getOrCreateTagBuilder(ModBlockTags.COPPER_DOORS).add(
                ModBlocks.COPPER_DOOR,
                ModBlocks.EXPOSED_COPPER_DOOR,
                ModBlocks.WEATHERED_COPPER_DOOR,
                ModBlocks.OXIDIZED_COPPER_DOOR,
                ModBlocks.WAXED_COPPER_DOOR,
                ModBlocks.WAXED_EXPOSED_COPPER_DOOR,
                ModBlocks.WAXED_WEATHERED_COPPER_DOOR,
                ModBlocks.WAXED_OXIDIZED_COPPER_DOOR
        );

        getOrCreateTagBuilder(ModBlockTags.PACKED_MUD).add(
                ModBlocks.OVERGROWN_PACKED_MUD,
                Blocks.PACKED_MUD
        );

        getOrCreateTagBuilder(ModBlockTags.SHELVES).add(
                ModBlocks.ACACIA_SHELF,
                ModBlocks.BIRCH_SHELF,
                ModBlocks.CRIMSON_SHELF,
                ModBlocks.DARK_OAK_SHELF,
                ModBlocks.JUNGLE_SHELF,
                ModBlocks.MANGROVE_SHELF,
                ModBlocks.OAK_SHELF,
                ModBlocks.SPRUCE_SHELF,
                ModBlocks.WARPED_SHELF,
                ModBlocks.BAMBOO_SHELF,
                ModBlocks.CHERRY_SHELF
        );

        getOrCreateTagBuilder(ModBlockTags.ECHOFINS_SPAWN_ON).add(
                Blocks.CHORUS_FLOWER,
                Blocks.CHORUS_PLANT
        );

        getOrCreateTagBuilder(ModBlockTags.BUTTERFLIES_SPAWN_ON).add(
                Blocks.GRASS_BLOCK,
                Blocks.SOUL_SAND,
                Blocks.SOUL_SOIL,
                Blocks.WARPED_NYLIUM,
                Blocks.CRIMSON_NYLIUM,
                Blocks.NETHERRACK
        );

        getOrCreateTagBuilder(ModBlockTags.EMBER_TORTOISES_SPAWN_ON).add(
                Blocks.NETHERRACK,
                Blocks.BASALT,
                Blocks.BLACKSTONE
        );

        getOrCreateTagBuilder(ModBlockTags.CRIMSON_NEWTS_SPAWN_ON).add(
                Blocks.NETHERRACK,
                Blocks.CRIMSON_NYLIUM
        );

        getOrCreateTagBuilder(ModBlockTags.WARPED_NEWTS_SPAWN_ON).add(
                Blocks.NETHERRACK,
                Blocks.WARPED_NYLIUM
        );

        getOrCreateTagBuilder(ModBlockTags.CHANDELIERS).add(
                ModBlocks.GOLDEN_CHANDELIER,
                ModBlocks.WHITE_GOLDEN_CHANDELIER,
                ModBlocks.LIGHT_GRAY_GOLDEN_CHANDELIER,
                ModBlocks.GRAY_GOLDEN_CHANDELIER,
                ModBlocks.BLACK_GOLDEN_CHANDELIER,
                ModBlocks.BROWN_GOLDEN_CHANDELIER,
                ModBlocks.RED_GOLDEN_CHANDELIER,
                ModBlocks.ORANGE_GOLDEN_CHANDELIER,
                ModBlocks.YELLOW_GOLDEN_CHANDELIER,
                ModBlocks.LIME_GOLDEN_CHANDELIER,
                ModBlocks.GREEN_GOLDEN_CHANDELIER,
                ModBlocks.CYAN_GOLDEN_CHANDELIER,
                ModBlocks.LIGHT_BLUE_GOLDEN_CHANDELIER,
                ModBlocks.BLUE_GOLDEN_CHANDELIER,
                ModBlocks.PURPLE_GOLDEN_CHANDELIER,
                ModBlocks.MAGENTA_GOLDEN_CHANDELIER,
                ModBlocks.PINK_GOLDEN_CHANDELIER,
                ModBlocks.SILVER_CHANDELIER,
                ModBlocks.WHITE_SILVER_CHANDELIER,
                ModBlocks.LIGHT_GRAY_SILVER_CHANDELIER,
                ModBlocks.GRAY_SILVER_CHANDELIER,
                ModBlocks.BLACK_SILVER_CHANDELIER,
                ModBlocks.BROWN_SILVER_CHANDELIER,
                ModBlocks.RED_SILVER_CHANDELIER,
                ModBlocks.ORANGE_SILVER_CHANDELIER,
                ModBlocks.YELLOW_SILVER_CHANDELIER,
                ModBlocks.LIME_SILVER_CHANDELIER,
                ModBlocks.GREEN_SILVER_CHANDELIER,
                ModBlocks.CYAN_SILVER_CHANDELIER,
                ModBlocks.LIGHT_BLUE_SILVER_CHANDELIER,
                ModBlocks.BLUE_SILVER_CHANDELIER,
                ModBlocks.PURPLE_SILVER_CHANDELIER,
                ModBlocks.MAGENTA_SILVER_CHANDELIER,
                ModBlocks.PINK_SILVER_CHANDELIER,
                ModBlocks.IRON_CHANDELIER,
                ModBlocks.WHITE_IRON_CHANDELIER,
                ModBlocks.LIGHT_GRAY_IRON_CHANDELIER,
                ModBlocks.GRAY_IRON_CHANDELIER,
                ModBlocks.BLACK_IRON_CHANDELIER,
                ModBlocks.BROWN_IRON_CHANDELIER,
                ModBlocks.RED_IRON_CHANDELIER,
                ModBlocks.ORANGE_IRON_CHANDELIER,
                ModBlocks.YELLOW_IRON_CHANDELIER,
                ModBlocks.LIME_IRON_CHANDELIER,
                ModBlocks.GREEN_IRON_CHANDELIER,
                ModBlocks.CYAN_IRON_CHANDELIER,
                ModBlocks.LIGHT_BLUE_IRON_CHANDELIER,
                ModBlocks.BLUE_IRON_CHANDELIER,
                ModBlocks.PURPLE_IRON_CHANDELIER,
                ModBlocks.MAGENTA_IRON_CHANDELIER,
                ModBlocks.PINK_IRON_CHANDELIER,
                ModBlocks.COPPER_CHANDELIER,
                ModBlocks.WHITE_COPPER_CHANDELIER,
                ModBlocks.LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.GRAY_COPPER_CHANDELIER,
                ModBlocks.BLACK_COPPER_CHANDELIER,
                ModBlocks.BROWN_COPPER_CHANDELIER,
                ModBlocks.RED_COPPER_CHANDELIER,
                ModBlocks.ORANGE_COPPER_CHANDELIER,
                ModBlocks.YELLOW_COPPER_CHANDELIER,
                ModBlocks.LIME_COPPER_CHANDELIER,
                ModBlocks.GREEN_COPPER_CHANDELIER,
                ModBlocks.CYAN_COPPER_CHANDELIER,
                ModBlocks.LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.BLUE_COPPER_CHANDELIER,
                ModBlocks.PURPLE_COPPER_CHANDELIER,
                ModBlocks.MAGENTA_COPPER_CHANDELIER,
                ModBlocks.PINK_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_WHITE_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_GRAY_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_BLACK_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_BROWN_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_RED_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_ORANGE_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_YELLOW_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_LIME_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_GREEN_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_CYAN_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_BLUE_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_PURPLE_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_MAGENTA_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_PINK_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_WHITE_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_GRAY_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_BLACK_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_BROWN_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_RED_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_ORANGE_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_YELLOW_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_LIME_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_GREEN_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_CYAN_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_BLUE_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_PURPLE_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_MAGENTA_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_PINK_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_WHITE_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_GRAY_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_BLACK_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_BROWN_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_RED_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_ORANGE_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_YELLOW_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_LIME_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_GREEN_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_CYAN_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_BLUE_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_PURPLE_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_MAGENTA_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_PINK_COPPER_CHANDELIER,
                ModBlocks.WAXED_COPPER_CHANDELIER,
                ModBlocks.WAXED_WHITE_COPPER_CHANDELIER,
                ModBlocks.WAXED_LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_BLACK_COPPER_CHANDELIER,
                ModBlocks.WAXED_BROWN_COPPER_CHANDELIER,
                ModBlocks.WAXED_RED_COPPER_CHANDELIER,
                ModBlocks.WAXED_ORANGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_YELLOW_COPPER_CHANDELIER,
                ModBlocks.WAXED_LIME_COPPER_CHANDELIER,
                ModBlocks.WAXED_GREEN_COPPER_CHANDELIER,
                ModBlocks.WAXED_CYAN_COPPER_CHANDELIER,
                ModBlocks.WAXED_LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_PURPLE_COPPER_CHANDELIER,
                ModBlocks.WAXED_MAGENTA_COPPER_CHANDELIER,
                ModBlocks.WAXED_PINK_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_WHITE_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_BLACK_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_BROWN_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_RED_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_ORANGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_YELLOW_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_LIME_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_GREEN_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_CYAN_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_PURPLE_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_MAGENTA_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_PINK_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_WHITE_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_BLACK_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_BROWN_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_RED_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_ORANGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_YELLOW_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_LIME_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_GREEN_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_CYAN_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_PURPLE_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_MAGENTA_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_PINK_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_WHITE_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_LIGHT_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_GRAY_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_BLACK_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_BROWN_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_RED_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_ORANGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_YELLOW_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_LIME_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_GREEN_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_CYAN_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_LIGHT_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_BLUE_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_PURPLE_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_MAGENTA_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_PINK_COPPER_CHANDELIER
        );

        getOrCreateTagBuilder(ModBlockTags.FLAMMABLE)
                .addTag(ModBlockTags.ARMCHAIRS)
                .addTag(ModBlockTags.FOOD_BOXES)
                .addTag(ModBlockTags.LAMPS);

        getOrCreateTagBuilder(ModBlockTags.LARGE_CHANDELIERS).add(
                ModBlocks.LARGE_GOLDEN_CHANDELIER,
                ModBlocks.LARGE_SILVER_CHANDELIER,
                ModBlocks.LARGE_IRON_CHANDELIER,
                ModBlocks.LARGE_COPPER_CHANDELIER,
                ModBlocks.EXPOSED_LARGE_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_LARGE_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_LARGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_LARGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_LARGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_LARGE_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_LARGE_COPPER_CHANDELIER
        );

        /** Common **/

        getOrCreateTagBuilder(ModBlockTags.C_BOOKSHELVES).add(
                ModBlocks.ACACIA_WALL_BOOKSHELF,
                ModBlocks.BIRCH_WALL_BOOKSHELF,
                ModBlocks.CRIMSON_WALL_BOOKSHELF,
                ModBlocks.DARK_OAK_WALL_BOOKSHELF,
                ModBlocks.JUNGLE_WALL_BOOKSHELF,
                ModBlocks.MANGROVE_WALL_BOOKSHELF,
                ModBlocks.OAK_WALL_BOOKSHELF,
                ModBlocks.SPRUCE_WALL_BOOKSHELF,
                ModBlocks.WARPED_WALL_BOOKSHELF,
                ModBlocks.BAMBOO_WALL_BOOKSHELF,
                ModBlocks.CHERRY_WALL_BOOKSHELF,
                Blocks.BOOKSHELF
        );

        getOrCreateTagBuilder(ModBlockTags.C_ORES).add(
                ModBlocks.SILVER_ORE,
                ModBlocks.DEEPSLATE_SILVER_ORE
        );

        getOrCreateTagBuilder(ModBlockTags.C_RAW_SILVER_BLOCKS).add(
                ModBlocks.RAW_SILVER_BLOCK
        );

    }
}