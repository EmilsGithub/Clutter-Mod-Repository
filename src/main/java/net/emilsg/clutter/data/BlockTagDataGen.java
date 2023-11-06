package net.emilsg.clutter.data;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.util.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagDataGen extends FabricTagProvider.BlockTagProvider {

    public BlockTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        /** Vanilla **/

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
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
                ModBlocks.DEAD_ANCHOR_CORAL
        );

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(
                 ModBlocks.BONFIRE,
                 ModBlocks.SOUL_BONFIRE
        )
                .addTag(ModBlockTags.TRELLISES)
                .addTag(ModBlockTags.BENCHES)
                .addTag(ModBlockTags.ARMCHAIRS)
                .addTag(ModBlockTags.WOODEN_MOSAICS)
                .addTag(ModBlockTags.CUPBOARDS)
                .addTag(ModBlockTags.LAMPS)
                .addTag(ModBlockTags.WOODEN_MOSAICS)
                .addTag(ModBlockTags.TABLES)
                .addTag(ModBlockTags.FOOD_BOXES)
                .addTag(ModBlockTags.WINDOW_SILLS)
                .addTag(ModBlockTags.WOODEN_CHAIRS);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(
                ModBlocks.SULPHUR_BLOCK,
                ModBlocks.ONYX_ORE);

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(
                ModBlocks.RIPE_KIWI_LEAVES,
                ModBlocks.KIWI_LEAVES);

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
                ModBlocks.RIPE_KIWI_LEAVES);

        getOrCreateTagBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS).add(
                ModBlocks.SULPHUR_BLOCK);

        getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS).add(
                ModBlocks.THORNBLOOM,
                ModBlocks.GLOWLILY);

        getOrCreateTagBuilder(BlockTags.SAPLINGS).add(
                ModBlocks.KIWI_TREE_SAPLING);

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
                ModBlocks.POLISHED_ONYX_WALL);

        getOrCreateTagBuilder(BlockTags.STAIRS).add(
                ModBlocks.ONYX_STAIRS,
                ModBlocks.POLISHED_ONYX_STAIRS,
                ModBlocks.OAK_MOSAIC_STAIRS,
                ModBlocks.SPRUCE_MOSAIC_STAIRS,
                ModBlocks.BIRCH_MOSAIC_STAIRS,
                ModBlocks.JUNGLE_MOSAIC_STAIRS,
                ModBlocks.ACACIA_MOSAIC_STAIRS,
                ModBlocks.DARK_OAK_MOSAIC_STAIRS,
                ModBlocks.MANGROVE_MOSAIC_STAIRS,
                ModBlocks.CRIMSON_MOSAIC_STAIRS,
                ModBlocks.WARPED_MOSAIC_STAIRS,
                ModBlocks.CHERRY_MOSAIC_STAIRS);

        getOrCreateTagBuilder(BlockTags.SLABS).add(
                ModBlocks.ONYX_SLAB,
                ModBlocks.POLISHED_ONYX_SLAB,
                ModBlocks.OAK_MOSAIC_SLAB,
                ModBlocks.SPRUCE_MOSAIC_SLAB,
                ModBlocks.BIRCH_MOSAIC_SLAB,
                ModBlocks.JUNGLE_MOSAIC_SLAB,
                ModBlocks.ACACIA_MOSAIC_SLAB,
                ModBlocks.DARK_OAK_MOSAIC_SLAB,
                ModBlocks.MANGROVE_MOSAIC_SLAB,
                ModBlocks.CRIMSON_MOSAIC_SLAB,
                ModBlocks.WARPED_MOSAIC_SLAB,
                ModBlocks.CHERRY_MOSAIC_SLAB);

        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(
                ModBlocks.OAK_MOSAIC_SLAB,
                ModBlocks.SPRUCE_MOSAIC_SLAB,
                ModBlocks.BIRCH_MOSAIC_SLAB,
                ModBlocks.JUNGLE_MOSAIC_SLAB,
                ModBlocks.ACACIA_MOSAIC_SLAB,
                ModBlocks.DARK_OAK_MOSAIC_SLAB,
                ModBlocks.MANGROVE_MOSAIC_SLAB,
                ModBlocks.CRIMSON_MOSAIC_SLAB,
                ModBlocks.WARPED_MOSAIC_SLAB,
                ModBlocks.CHERRY_MOSAIC_SLAB);

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

        getOrCreateTagBuilder(ModBlockTags.WOODEN_MOSAICS).add(
                ModBlocks.OAK_MOSAIC,
                ModBlocks.SPRUCE_MOSAIC,
                ModBlocks.BIRCH_MOSAIC,
                ModBlocks.JUNGLE_MOSAIC,
                ModBlocks.ACACIA_MOSAIC,
                ModBlocks.DARK_OAK_MOSAIC,
                ModBlocks.MANGROVE_MOSAIC,
                ModBlocks.CRIMSON_MOSAIC,
                ModBlocks.WARPED_MOSAIC,
                ModBlocks.CHERRY_MOSAIC,
                ModBlocks.OAK_MOSAIC_STAIRS,
                ModBlocks.SPRUCE_MOSAIC_STAIRS,
                ModBlocks.BIRCH_MOSAIC_STAIRS,
                ModBlocks.JUNGLE_MOSAIC_STAIRS,
                ModBlocks.ACACIA_MOSAIC_STAIRS,
                ModBlocks.DARK_OAK_MOSAIC_STAIRS,
                ModBlocks.MANGROVE_MOSAIC_STAIRS,
                ModBlocks.CRIMSON_MOSAIC_STAIRS,
                ModBlocks.WARPED_MOSAIC_STAIRS,
                ModBlocks.CHERRY_MOSAIC_STAIRS,
                ModBlocks.OAK_MOSAIC_SLAB,
                ModBlocks.SPRUCE_MOSAIC_SLAB,
                ModBlocks.BIRCH_MOSAIC_SLAB,
                ModBlocks.JUNGLE_MOSAIC_SLAB,
                ModBlocks.ACACIA_MOSAIC_SLAB,
                ModBlocks.DARK_OAK_MOSAIC_SLAB,
                ModBlocks.MANGROVE_MOSAIC_SLAB,
                ModBlocks.CRIMSON_MOSAIC_SLAB,
                ModBlocks.WARPED_MOSAIC_SLAB,
                ModBlocks.CHERRY_MOSAIC_SLAB
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

        getOrCreateTagBuilder(ModBlockTags.WOODEN_CHAIRS).add(
                ModBlocks.OAK_CHAIR,
                ModBlocks.SPRUCE_CHAIR,
                ModBlocks.BIRCH_CHAIR,
                ModBlocks.JUNGLE_CHAIR,
                ModBlocks.ACACIA_CHAIR,
                ModBlocks.DARK_OAK_CHAIR,
                ModBlocks.MANGROVE_CHAIR,
                ModBlocks.CRIMSON_CHAIR,
                ModBlocks.WARPED_CHAIR,
                ModBlocks.CHERRY_CHAIR,
                ModBlocks.BAMBOO_CHAIR,
                ModBlocks.STRIPPED_OAK_CHAIR,
                ModBlocks.STRIPPED_SPRUCE_CHAIR,
                ModBlocks.STRIPPED_BIRCH_CHAIR,
                ModBlocks.STRIPPED_JUNGLE_CHAIR,
                ModBlocks.STRIPPED_ACACIA_CHAIR,
                ModBlocks.STRIPPED_DARK_OAK_CHAIR,
                ModBlocks.STRIPPED_MANGROVE_CHAIR,
                ModBlocks.STRIPPED_CRIMSON_CHAIR,
                ModBlocks.STRIPPED_WARPED_CHAIR,
                ModBlocks.STRIPPED_CHERRY_CHAIR
        );

        getOrCreateTagBuilder(ModBlockTags.CUPBOARDS).add(
                ModBlocks.OAK_CUPBOARD,
                ModBlocks.SPRUCE_CUPBOARD,
                ModBlocks.BIRCH_CUPBOARD,
                ModBlocks.JUNGLE_CUPBOARD,
                ModBlocks.ACACIA_CUPBOARD,
                ModBlocks.DARK_OAK_CUPBOARD,
                ModBlocks.MANGROVE_CUPBOARD,
                ModBlocks.CRIMSON_CUPBOARD,
                ModBlocks.WARPED_CUPBOARD,
                ModBlocks.BAMBOO_CUPBOARD,
                ModBlocks.CHERRY_CUPBOARD,
                ModBlocks.OAK_WALL_CUPBOARD,
                ModBlocks.SPRUCE_WALL_CUPBOARD,
                ModBlocks.BIRCH_WALL_CUPBOARD,
                ModBlocks.JUNGLE_WALL_CUPBOARD,
                ModBlocks.ACACIA_WALL_CUPBOARD,
                ModBlocks.DARK_OAK_WALL_CUPBOARD,
                ModBlocks.MANGROVE_WALL_CUPBOARD,
                ModBlocks.CRIMSON_WALL_CUPBOARD,
                ModBlocks.WARPED_WALL_CUPBOARD,
                ModBlocks.BAMBOO_WALL_CUPBOARD,
                ModBlocks.CHERRY_WALL_CUPBOARD
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

        getOrCreateTagBuilder(ModBlockTags.WINDOW_SILLS).add(
                ModBlocks.OAK_WINDOW_SILL,
                ModBlocks.SPRUCE_WINDOW_SILL,
                ModBlocks.BIRCH_WINDOW_SILL,
                ModBlocks.JUNGLE_WINDOW_SILL,
                ModBlocks.ACACIA_WINDOW_SILL,
                ModBlocks.DARK_OAK_WINDOW_SILL,
                ModBlocks.MANGROVE_WINDOW_SILL,
                ModBlocks.CRIMSON_WINDOW_SILL,
                ModBlocks.WARPED_WINDOW_SILL,
                ModBlocks.BAMBOO_WINDOW_SILL,
                ModBlocks.CHERRY_WINDOW_SILL
        );

        getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_CHAIRS).add(
                ModBlocks.ACACIA_CHAIR,
                ModBlocks.BIRCH_CHAIR,
                ModBlocks.CRIMSON_CHAIR,
                ModBlocks.DARK_OAK_CHAIR,
                ModBlocks.JUNGLE_CHAIR,
                ModBlocks.MANGROVE_CHAIR,
                ModBlocks.OAK_CHAIR,
                ModBlocks.SPRUCE_CHAIR,
                ModBlocks.WARPED_CHAIR,
                ModBlocks.CHERRY_CHAIR
        );

        getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_BENCHES).add(
                ModBlocks.ACACIA_BENCH,
                ModBlocks.BIRCH_BENCH,
                ModBlocks.CRIMSON_BENCH,
                ModBlocks.DARK_OAK_BENCH,
                ModBlocks.JUNGLE_BENCH,
                ModBlocks.MANGROVE_BENCH,
                ModBlocks.OAK_BENCH,
                ModBlocks.SPRUCE_BENCH,
                ModBlocks.WARPED_BENCH,
                ModBlocks.CHERRY_BENCH
        );

        getOrCreateTagBuilder(ModBlockTags.TABLES).add(
                ModBlocks.STRIPPED_ACACIA_TABLE,
                ModBlocks.STRIPPED_BIRCH_TABLE,
                ModBlocks.STRIPPED_CRIMSON_TABLE,
                ModBlocks.STRIPPED_DARK_OAK_TABLE,
                ModBlocks.STRIPPED_JUNGLE_TABLE,
                ModBlocks.STRIPPED_MANGROVE_TABLE,
                ModBlocks.STRIPPED_OAK_TABLE,
                ModBlocks.STRIPPED_SPRUCE_TABLE,
                ModBlocks.STRIPPED_WARPED_TABLE,
                ModBlocks.STRIPPED_CHERRY_TABLE,
                ModBlocks.BAMBOO_TABLE)
        .addTag(
                ModBlockTags.STRIPPABLE_TABLES
        );

        getOrCreateTagBuilder(ModBlockTags.STRIPPABLE_TABLES).add(
                ModBlocks.ACACIA_TABLE,
                ModBlocks.BIRCH_TABLE,
                ModBlocks.CRIMSON_TABLE,
                ModBlocks.DARK_OAK_TABLE,
                ModBlocks.JUNGLE_TABLE,
                ModBlocks.MANGROVE_TABLE,
                ModBlocks.OAK_TABLE,
                ModBlocks.SPRUCE_TABLE,
                ModBlocks.WARPED_TABLE,
                ModBlocks.CHERRY_TABLE
        );

        getOrCreateTagBuilder(ModBlockTags.IMMOVABLE)
                .add(ModBlocks.ONYX_BLOCK);

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

        getOrCreateTagBuilder(ModBlockTags.SEATS).add(
                ModBlocks.TOILET
        ).addTag(
                ModBlockTags.BENCHES
        );

        getOrCreateTagBuilder(ModBlockTags.TRELLISES).add(
                ModBlocks.ACACIA_TRELLIS,
                ModBlocks.BIRCH_TRELLIS,
                ModBlocks.CRIMSON_TRELLIS,
                ModBlocks.DARK_OAK_TRELLIS,
                ModBlocks.JUNGLE_TRELLIS,
                ModBlocks.MANGROVE_TRELLIS,
                ModBlocks.OAK_TRELLIS,
                ModBlocks.SPRUCE_TRELLIS,
                ModBlocks.WARPED_TRELLIS,
                ModBlocks.BAMBOO_TRELLIS,
                ModBlocks.CHERRY_TRELLIS
        );

        getOrCreateTagBuilder(ModBlockTags.FLAMMABLE).add(
                ModBlocks.OAK_MOSAIC,
                ModBlocks.SPRUCE_MOSAIC,
                ModBlocks.BIRCH_MOSAIC,
                ModBlocks.JUNGLE_MOSAIC,
                ModBlocks.ACACIA_MOSAIC,
                ModBlocks.DARK_OAK_MOSAIC,
                ModBlocks.MANGROVE_MOSAIC,
                ModBlocks.CHERRY_MOSAIC,
                ModBlocks.OAK_MOSAIC_STAIRS,
                ModBlocks.SPRUCE_MOSAIC_STAIRS,
                ModBlocks.BIRCH_MOSAIC_STAIRS,
                ModBlocks.JUNGLE_MOSAIC_STAIRS,
                ModBlocks.ACACIA_MOSAIC_STAIRS,
                ModBlocks.DARK_OAK_MOSAIC_STAIRS,
                ModBlocks.MANGROVE_MOSAIC_STAIRS,
                ModBlocks.CHERRY_MOSAIC_STAIRS,
                ModBlocks.OAK_MOSAIC_SLAB,
                ModBlocks.SPRUCE_MOSAIC_SLAB,
                ModBlocks.BIRCH_MOSAIC_SLAB,
                ModBlocks.JUNGLE_MOSAIC_SLAB,
                ModBlocks.ACACIA_MOSAIC_SLAB,
                ModBlocks.DARK_OAK_MOSAIC_SLAB,
                ModBlocks.MANGROVE_MOSAIC_SLAB,
                ModBlocks.CHERRY_MOSAIC_SLAB,
                ModBlocks.STRIPPED_OAK_CHAIR,
                ModBlocks.STRIPPED_SPRUCE_CHAIR,
                ModBlocks.STRIPPED_BIRCH_CHAIR,
                ModBlocks.STRIPPED_JUNGLE_CHAIR,
                ModBlocks.STRIPPED_ACACIA_CHAIR,
                ModBlocks.STRIPPED_DARK_OAK_CHAIR,
                ModBlocks.STRIPPED_MANGROVE_CHAIR,
                ModBlocks.STRIPPED_CHERRY_CHAIR,
                ModBlocks.OAK_CUPBOARD,
                ModBlocks.SPRUCE_CUPBOARD,
                ModBlocks.BIRCH_CUPBOARD,
                ModBlocks.JUNGLE_CUPBOARD,
                ModBlocks.ACACIA_CUPBOARD,
                ModBlocks.DARK_OAK_CUPBOARD,
                ModBlocks.MANGROVE_CUPBOARD,
                ModBlocks.BAMBOO_CUPBOARD,
                ModBlocks.CHERRY_CUPBOARD,
                ModBlocks.OAK_WALL_CUPBOARD,
                ModBlocks.SPRUCE_WALL_CUPBOARD,
                ModBlocks.BIRCH_WALL_CUPBOARD,
                ModBlocks.JUNGLE_WALL_CUPBOARD,
                ModBlocks.ACACIA_WALL_CUPBOARD,
                ModBlocks.DARK_OAK_WALL_CUPBOARD,
                ModBlocks.MANGROVE_WALL_CUPBOARD,
                ModBlocks.BAMBOO_WALL_CUPBOARD,
                ModBlocks.CHERRY_WALL_CUPBOARD,
                ModBlocks.OAK_WINDOW_SILL,
                ModBlocks.SPRUCE_WINDOW_SILL,
                ModBlocks.BIRCH_WINDOW_SILL,
                ModBlocks.JUNGLE_WINDOW_SILL,
                ModBlocks.ACACIA_WINDOW_SILL,
                ModBlocks.DARK_OAK_WINDOW_SILL,
                ModBlocks.MANGROVE_WINDOW_SILL,
                ModBlocks.BAMBOO_WINDOW_SILL,
                ModBlocks.CHERRY_WINDOW_SILL,
                ModBlocks.CHERRY_CHAIR,
                ModBlocks.ACACIA_CHAIR,
                ModBlocks.BIRCH_CHAIR,
                ModBlocks.DARK_OAK_CHAIR,
                ModBlocks.JUNGLE_CHAIR,
                ModBlocks.MANGROVE_CHAIR,
                ModBlocks.OAK_CHAIR,
                ModBlocks.BAMBOO_CHAIR,
                ModBlocks.SPRUCE_CHAIR,
                ModBlocks.ACACIA_BENCH,
                ModBlocks.BIRCH_BENCH,
                ModBlocks.DARK_OAK_BENCH,
                ModBlocks.JUNGLE_BENCH,
                ModBlocks.MANGROVE_BENCH,
                ModBlocks.OAK_BENCH,
                ModBlocks.SPRUCE_BENCH,
                ModBlocks.CHERRY_BENCH,
                ModBlocks.BAMBOO_BENCH,
                ModBlocks.STRIPPED_ACACIA_BENCH,
                ModBlocks.STRIPPED_BIRCH_BENCH,
                ModBlocks.STRIPPED_DARK_OAK_BENCH,
                ModBlocks.STRIPPED_JUNGLE_BENCH,
                ModBlocks.STRIPPED_MANGROVE_BENCH,
                ModBlocks.STRIPPED_OAK_BENCH,
                ModBlocks.STRIPPED_SPRUCE_BENCH,
                ModBlocks.STRIPPED_CHERRY_BENCH,
                ModBlocks.STRIPPED_ACACIA_TABLE,
                ModBlocks.STRIPPED_BIRCH_TABLE,
                ModBlocks.STRIPPED_DARK_OAK_TABLE,
                ModBlocks.STRIPPED_JUNGLE_TABLE,
                ModBlocks.STRIPPED_MANGROVE_TABLE,
                ModBlocks.STRIPPED_OAK_TABLE,
                ModBlocks.STRIPPED_SPRUCE_TABLE,
                ModBlocks.STRIPPED_CHERRY_TABLE,
                ModBlocks.BAMBOO_TABLE,
                ModBlocks.ACACIA_TABLE,
                ModBlocks.BIRCH_TABLE,
                ModBlocks.DARK_OAK_TABLE,
                ModBlocks.JUNGLE_TABLE,
                ModBlocks.MANGROVE_TABLE,
                ModBlocks.OAK_TABLE,
                ModBlocks.SPRUCE_TABLE,
                ModBlocks.CHERRY_TABLE,
                ModBlocks.FLOOR_SEATING,
                ModBlocks.ACACIA_WALL_BOOKSHELF,
                ModBlocks.BIRCH_WALL_BOOKSHELF,
                ModBlocks.DARK_OAK_WALL_BOOKSHELF,
                ModBlocks.JUNGLE_WALL_BOOKSHELF,
                ModBlocks.MANGROVE_WALL_BOOKSHELF,
                ModBlocks.OAK_WALL_BOOKSHELF,
                ModBlocks.SPRUCE_WALL_BOOKSHELF,
                ModBlocks.BAMBOO_WALL_BOOKSHELF,
                ModBlocks.CHERRY_WALL_BOOKSHELF,
                ModBlocks.ACACIA_TRELLIS,
                ModBlocks.BIRCH_TRELLIS,
                ModBlocks.DARK_OAK_TRELLIS,
                ModBlocks.JUNGLE_TRELLIS,
                ModBlocks.MANGROVE_TRELLIS,
                ModBlocks.OAK_TRELLIS,
                ModBlocks.SPRUCE_TRELLIS,
                ModBlocks.BAMBOO_TRELLIS,
                ModBlocks.CHERRY_TRELLIS
        )
                .addTag(ModBlockTags.ARMCHAIRS)
                .addTag(ModBlockTags.FOOD_BOXES)
                .addTag(ModBlockTags.LAMPS);

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
