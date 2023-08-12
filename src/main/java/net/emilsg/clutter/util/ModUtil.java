package net.emilsg.clutter.util;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;


public class ModUtil {
    public static void registerModUtil() {
        registerFlammableBlocks();
        registerStrippableBlocks();
        registerCompostableItems();
        registerFuel();
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(ModBlockTags.FLAMMABLE, 60, 20);
        registry.add(ModBlocks.SULPHUR_BLOCK, 1, 5);
    }

    public static void registerStrippableBlocks() {

    }

    public static void registerCompostableItems() {
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(ModBlocks.LUSH_MOSS, 0.3f);
        registry.add(ModItems.HOPS_SEEDS, 0.3f);
        registry.add(ModItems.HOPS, 0.3f);
        registry.add(ModItems.COTTON_SEEDS, 0.3f);
        registry.add(ModItems.COTTON, 0.3f);
        registry.add(ModItems.KIWI_SEEDS, 0.3f);
        registry.add(ModItems.KIWI, 0.3f);
        registry.add(ModBlocks.KIWI_TREE_SAPLING, 0.3f);
        registry.add(ModItems.THORNBLOOM_SEEDS, 0.3f);
        registry.add(ModItems.THORNBLOOM_PEAR, 0.2f);
        registry.add(ModItems.BAKED_THORNBLOOM_PEAR, 0.3f);
        registry.add(ModBlocks.THORNBLOOM, 0.3f);
        registry.add(ModBlocks.KIWI_LEAVES, 0.2f);
    }

    public static void registerFuel() {
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(ModBlocks.SULPHUR_BLOCK, 400);

        registry.add(ModItems.SULPHUR, 100);
        registry.add(ModItems.WOODEN_MUG, 100);

        registry.add(ModBlocks.OAK_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.OAK_WINDOW_SILL, 100);
        registry.add(ModBlocks.SPRUCE_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.SPRUCE_WINDOW_SILL, 100);
        registry.add(ModBlocks.BIRCH_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.BIRCH_WINDOW_SILL, 100);
        registry.add(ModBlocks.JUNGLE_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.JUNGLE_WINDOW_SILL, 100);
        registry.add(ModBlocks.ACACIA_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.ACACIA_WINDOW_SILL, 100);
        registry.add(ModBlocks.DARK_OAK_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.DARK_OAK_WINDOW_SILL, 100);
        registry.add(ModBlocks.MANGROVE_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.MANGROVE_WINDOW_SILL, 100);
        registry.add(ModBlocks.CRIMSON_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.CRIMSON_WINDOW_SILL, 100);
        registry.add(ModBlocks.WARPED_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.WARPED_WINDOW_SILL, 100);
        registry.add(ModBlocks.BAMBOO_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.BAMBOO_WINDOW_SILL, 100);
        registry.add(ModBlocks.CHERRY_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.CHERRY_WINDOW_SILL, 100);

        registry.add(ModBlocks.FOOD_BOX, 100);

        registry.add(ModBlocks.OAK_TABLE, 100);
        registry.add(ModBlocks.SPRUCE_TABLE, 100);
        registry.add(ModBlocks.BIRCH_TABLE, 100);
        registry.add(ModBlocks.JUNGLE_TABLE, 100);
        registry.add(ModBlocks.ACACIA_TABLE, 100);
        registry.add(ModBlocks.DARK_OAK_TABLE, 100);
        registry.add(ModBlocks.MANGROVE_TABLE, 100);
        registry.add(ModBlocks.CRIMSON_TABLE, 100);
        registry.add(ModBlocks.WARPED_TABLE, 100);
        registry.add(ModBlocks.BAMBOO_TABLE, 100);
        registry.add(ModBlocks.CHERRY_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_OAK_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_SPRUCE_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_BIRCH_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_JUNGLE_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_ACACIA_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_DARK_OAK_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_MANGROVE_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_CRIMSON_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_WARPED_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_CHERRY_TABLE, 100);

        registry.add(ModBlocks.OAK_CHAIR, 100);
        registry.add(ModBlocks.SPRUCE_CHAIR, 100);
        registry.add(ModBlocks.BIRCH_CHAIR, 100);
        registry.add(ModBlocks.JUNGLE_CHAIR, 100);
        registry.add(ModBlocks.ACACIA_CHAIR, 100);
        registry.add(ModBlocks.DARK_OAK_CHAIR, 100);
        registry.add(ModBlocks.MANGROVE_CHAIR, 100);
        registry.add(ModBlocks.CRIMSON_CHAIR, 100);
        registry.add(ModBlocks.WARPED_CHAIR, 100);
        registry.add(ModBlocks.BAMBOO_CHAIR, 100);
        registry.add(ModBlocks.CHERRY_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_OAK_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_SPRUCE_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_BIRCH_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_JUNGLE_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_ACACIA_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_DARK_OAK_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_MANGROVE_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_CRIMSON_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_WARPED_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_CHERRY_CHAIR, 100);

        registry.add(ModBlocks.OAK_CUPBOARD, 100);
        registry.add(ModBlocks.SPRUCE_CUPBOARD, 100);
        registry.add(ModBlocks.BIRCH_CUPBOARD, 100);
        registry.add(ModBlocks.JUNGLE_CUPBOARD, 100);
        registry.add(ModBlocks.ACACIA_CUPBOARD, 100);
        registry.add(ModBlocks.DARK_OAK_CUPBOARD, 100);
        registry.add(ModBlocks.MANGROVE_CUPBOARD, 100);
        registry.add(ModBlocks.CRIMSON_CUPBOARD, 100);
        registry.add(ModBlocks.WARPED_CUPBOARD, 100);
        registry.add(ModBlocks.BAMBOO_CUPBOARD, 100);
        registry.add(ModBlocks.CHERRY_CUPBOARD, 100);

        registry.add(ModBlocks.OAK_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.SPRUCE_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.BIRCH_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.JUNGLE_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.ACACIA_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.DARK_OAK_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.MANGROVE_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.CRIMSON_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.WARPED_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.BAMBOO_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.CHERRY_WALL_CUPBOARD, 100);

        registry.add(ModBlocks.OAK_TRELLIS, 100);
        registry.add(ModBlocks.SPRUCE_TRELLIS, 100);
        registry.add(ModBlocks.BIRCH_TRELLIS, 100);
        registry.add(ModBlocks.JUNGLE_TRELLIS, 100);
        registry.add(ModBlocks.ACACIA_TRELLIS, 100);
        registry.add(ModBlocks.DARK_OAK_TRELLIS, 100);
        registry.add(ModBlocks.MANGROVE_TRELLIS, 100);
        registry.add(ModBlocks.CRIMSON_TRELLIS, 100);
        registry.add(ModBlocks.WARPED_TRELLIS, 100);
        registry.add(ModBlocks.BAMBOO_TRELLIS, 100);
        registry.add(ModBlocks.CHERRY_TRELLIS, 100);

        registry.add(ModBlocks.OAK_BENCH, 100);
        registry.add(ModBlocks.SPRUCE_BENCH, 100);
        registry.add(ModBlocks.BIRCH_BENCH, 100);
        registry.add(ModBlocks.JUNGLE_BENCH, 100);
        registry.add(ModBlocks.ACACIA_BENCH, 100);
        registry.add(ModBlocks.DARK_OAK_BENCH, 100);
        registry.add(ModBlocks.MANGROVE_BENCH, 100);
        registry.add(ModBlocks.CRIMSON_BENCH, 100);
        registry.add(ModBlocks.WARPED_BENCH, 100);
        registry.add(ModBlocks.BAMBOO_BENCH, 100);
        registry.add(ModBlocks.CHERRY_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_OAK_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_SPRUCE_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_BIRCH_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_JUNGLE_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_ACACIA_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_DARK_OAK_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_MANGROVE_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_CRIMSON_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_WARPED_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_CHERRY_BENCH, 100);
    }
}
