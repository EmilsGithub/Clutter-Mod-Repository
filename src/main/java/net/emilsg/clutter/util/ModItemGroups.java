package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.*;
import net.emilsg.clutter.block.custom.plushies.AbstractPlushieBlock;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.ClutterElytraItem;
import net.emilsg.clutter.item.custom.ClutterSpawnEggItem;
import net.emilsg.clutter.item.custom.HatItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> CLUTTER_BLOCKS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Clutter.MOD_ID, "clutter_blocks"));
    public static final RegistryKey<ItemGroup> CLUTTER_ITEMS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Clutter.MOD_ID, "clutter_items"));
    public static final RegistryKey<ItemGroup> CLUTTER_SPAWN_EGGS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Clutter.MOD_ID, "clutter_spawn_eggs"));
    public static final RegistryKey<ItemGroup> CLUTTER_NATURE = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Clutter.MOD_ID, "clutter_nature"));


    public static void registerItemGroups() {

        Registry.register(Registries.ITEM_GROUP, CLUTTER_BLOCKS, FabricItemGroup.builder()
                .displayName(Text.translatable("clutter.identifier").append(Text.translatable("itemgroup.clutter_blocks")))
                .icon(() -> new ItemStack(ModBlocks.CYAN_ARMCHAIR))
                .entries((displayContext, entries) -> {

                    //Color
                    entries.add(ModBlocks.WHITE_LAMP);
                    entries.add(ModBlocks.LIGHT_GRAY_LAMP);
                    entries.add(ModBlocks.GRAY_LAMP);
                    entries.add(ModBlocks.BLACK_LAMP);
                    entries.add(ModBlocks.BROWN_LAMP);
                    entries.add(ModBlocks.RED_LAMP);
                    entries.add(ModBlocks.ORANGE_LAMP);
                    entries.add(ModBlocks.YELLOW_LAMP);
                    entries.add(ModBlocks.LIME_LAMP);
                    entries.add(ModBlocks.GREEN_LAMP);
                    entries.add(ModBlocks.CYAN_LAMP);
                    entries.add(ModBlocks.LIGHT_BLUE_LAMP);
                    entries.add(ModBlocks.BLUE_LAMP);
                    entries.add(ModBlocks.PURPLE_LAMP);
                    entries.add(ModBlocks.MAGENTA_LAMP);
                    entries.add(ModBlocks.PINK_LAMP);

                    entries.add(ModBlocks.TALL_WHITE_LAMP);
                    entries.add(ModBlocks.TALL_LIGHT_GRAY_LAMP);
                    entries.add(ModBlocks.TALL_GRAY_LAMP);
                    entries.add(ModBlocks.TALL_BLACK_LAMP);
                    entries.add(ModBlocks.TALL_BROWN_LAMP);
                    entries.add(ModBlocks.TALL_RED_LAMP);
                    entries.add(ModBlocks.TALL_ORANGE_LAMP);
                    entries.add(ModBlocks.TALL_YELLOW_LAMP);
                    entries.add(ModBlocks.TALL_LIME_LAMP);
                    entries.add(ModBlocks.TALL_GREEN_LAMP);
                    entries.add(ModBlocks.TALL_CYAN_LAMP);
                    entries.add(ModBlocks.TALL_LIGHT_BLUE_LAMP);
                    entries.add(ModBlocks.TALL_BLUE_LAMP);
                    entries.add(ModBlocks.TALL_PURPLE_LAMP);
                    entries.add(ModBlocks.TALL_MAGENTA_LAMP);
                    entries.add(ModBlocks.TALL_PINK_LAMP);

                    entries.add(ModBlocks.WHITE_ARMCHAIR);
                    entries.add(ModBlocks.LIGHT_GRAY_ARMCHAIR);
                    entries.add(ModBlocks.GRAY_ARMCHAIR);
                    entries.add(ModBlocks.BLACK_ARMCHAIR);
                    entries.add(ModBlocks.BROWN_ARMCHAIR);
                    entries.add(ModBlocks.RED_ARMCHAIR);
                    entries.add(ModBlocks.ORANGE_ARMCHAIR);
                    entries.add(ModBlocks.YELLOW_ARMCHAIR);
                    entries.add(ModBlocks.LIME_ARMCHAIR);
                    entries.add(ModBlocks.GREEN_ARMCHAIR);
                    entries.add(ModBlocks.CYAN_ARMCHAIR);
                    entries.add(ModBlocks.LIGHT_BLUE_ARMCHAIR);
                    entries.add(ModBlocks.BLUE_ARMCHAIR);
                    entries.add(ModBlocks.PURPLE_ARMCHAIR);
                    entries.add(ModBlocks.MAGENTA_ARMCHAIR);
                    entries.add(ModBlocks.PINK_ARMCHAIR);

                    entries.add(ModBlocks.WHITE_BUNK_BED);
                    entries.add(ModBlocks.LIGHT_GRAY_BUNK_BED);
                    entries.add(ModBlocks.GRAY_BUNK_BED);
                    entries.add(ModBlocks.BLACK_BUNK_BED);
                    entries.add(ModBlocks.BROWN_BUNK_BED);
                    entries.add(ModBlocks.RED_BUNK_BED);
                    entries.add(ModBlocks.ORANGE_BUNK_BED);
                    entries.add(ModBlocks.YELLOW_BUNK_BED);
                    entries.add(ModBlocks.LIME_BUNK_BED);
                    entries.add(ModBlocks.GREEN_BUNK_BED);
                    entries.add(ModBlocks.CYAN_BUNK_BED);
                    entries.add(ModBlocks.LIGHT_BLUE_BUNK_BED);
                    entries.add(ModBlocks.BLUE_BUNK_BED);
                    entries.add(ModBlocks.PURPLE_BUNK_BED);
                    entries.add(ModBlocks.MAGENTA_BUNK_BED);
                    entries.add(ModBlocks.PINK_BUNK_BED);

                    entries.add(ModBlocks.WHITE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LIGHT_GRAY_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.GRAY_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.BLACK_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.BROWN_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.RED_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.ORANGE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.YELLOW_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LIME_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.GREEN_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.CYAN_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LIGHT_BLUE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.BLUE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.PURPLE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.MAGENTA_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.PINK_KITCHEN_CURTAINS);

                    entries.add(ModBlocks.LONG_WHITE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_LIGHT_GRAY_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_GRAY_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_BLACK_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_BROWN_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_RED_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_ORANGE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_YELLOW_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_LIME_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_GREEN_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_CYAN_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_LIGHT_BLUE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_BLUE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_PURPLE_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_MAGENTA_KITCHEN_CURTAINS);
                    entries.add(ModBlocks.LONG_PINK_KITCHEN_CURTAINS);

                    entries.add(ModBlocks.TALL_WHITE_CURTAINS);
                    entries.add(ModBlocks.TALL_LIGHT_GRAY_CURTAINS);
                    entries.add(ModBlocks.TALL_GRAY_CURTAINS);
                    entries.add(ModBlocks.TALL_BLACK_CURTAINS);
                    entries.add(ModBlocks.TALL_BROWN_CURTAINS);
                    entries.add(ModBlocks.TALL_RED_CURTAINS);
                    entries.add(ModBlocks.TALL_ORANGE_CURTAINS);
                    entries.add(ModBlocks.TALL_YELLOW_CURTAINS);
                    entries.add(ModBlocks.TALL_LIME_CURTAINS);
                    entries.add(ModBlocks.TALL_GREEN_CURTAINS);
                    entries.add(ModBlocks.TALL_CYAN_CURTAINS);
                    entries.add(ModBlocks.TALL_LIGHT_BLUE_CURTAINS);
                    entries.add(ModBlocks.TALL_BLUE_CURTAINS);
                    entries.add(ModBlocks.TALL_PURPLE_CURTAINS);
                    entries.add(ModBlocks.TALL_MAGENTA_CURTAINS);
                    entries.add(ModBlocks.TALL_PINK_CURTAINS);

                    entries.add(ModBlocks.LARGE_WHITE_CANDLE);
                    entries.add(ModBlocks.LARGE_LIGHT_GRAY_CANDLE);
                    entries.add(ModBlocks.LARGE_GRAY_CANDLE);
                    entries.add(ModBlocks.LARGE_BLACK_CANDLE);
                    entries.add(ModBlocks.LARGE_BROWN_CANDLE);
                    entries.add(ModBlocks.LARGE_RED_CANDLE);
                    entries.add(ModBlocks.LARGE_ORANGE_CANDLE);
                    entries.add(ModBlocks.LARGE_YELLOW_CANDLE);
                    entries.add(ModBlocks.LARGE_LIME_CANDLE);
                    entries.add(ModBlocks.LARGE_GREEN_CANDLE);
                    entries.add(ModBlocks.LARGE_CYAN_CANDLE);
                    entries.add(ModBlocks.LARGE_LIGHT_BLUE_CANDLE);
                    entries.add(ModBlocks.LARGE_BLUE_CANDLE);
                    entries.add(ModBlocks.LARGE_PURPLE_CANDLE);
                    entries.add(ModBlocks.LARGE_MAGENTA_CANDLE);
                    entries.add(ModBlocks.LARGE_PINK_CANDLE);

                    //Other

                    for (Block block : Registries.BLOCK) {
                        if (block instanceof AbstractPlushieBlock plushieBlock) entries.add(plushieBlock);
                        if (block instanceof ChimneyBlock chimneyBlock) entries.add(chimneyBlock);
                        if (block instanceof FoodBoxBlock foodBoxBlock) entries.add(foodBoxBlock);
                    }

                    entries.add(ModBlocks.SMALL_TARGET_BLOCK);
                    entries.add(ModBlocks.MAILBOX);
                    entries.add(ModBlocks.CARDBOARD_BOX);
                    entries.add(ModBlocks.RED_PRESENT);
                    entries.add(ModBlocks.BRICK_KILN);
                    entries.add(ModBlocks.FISH_TANK);

                    entries.add(ModBlocks.TALL_BOTTLE);
                    entries.add(ModBlocks.LAB_BOTTLE);

                    entries.add(ModBlocks.TUBE_TV);
                    entries.add(ModBlocks.BONFIRE);
                    entries.add(ModBlocks.SOUL_BONFIRE);
                    entries.add(ModBlocks.BREAD);
                    entries.add(ModBlocks.FROG_STATUE);
                    entries.add(ModBlocks.BOWL);
                    entries.add(ModBlocks.TOWEL);
                    entries.add(ModBlocks.TOILET);
                    entries.add(ModBlocks.FLOOR_SEATING);
                    entries.add(ModBlocks.PET_BED);

                    entries.add(ModBlocks.COPPER_COIN_STACK);
                    entries.add(ModBlocks.SILVER_COIN_STACK);
                    entries.add(ModBlocks.GOLDEN_COIN_STACK);

                    entries.add(ModBlocks.SILVER_ORE);
                    entries.add(ModBlocks.DEEPSLATE_SILVER_ORE);
                    entries.add(ModBlocks.RAW_SILVER_BLOCK);
                    entries.add(ModBlocks.SILVER_BLOCK);

                    entries.add(ModBlocks.BLACKSTONE_SULPHUR_ORE);
                    entries.add(ModBlocks.BASALT_SULPHUR_ORE);
                    entries.add(ModBlocks.SULPHUR_BLOCK);
                    entries.add(ModBlocks.ONYX_ORE);

                    entries.add(ModBlocks.SMALL_ONYX_BUD );
                    entries.add(ModBlocks.MEDIUM_ONYX_BUD);
                    entries.add(ModBlocks.LARGE_ONYX_BUD );
                    entries.add(ModBlocks.ONYX_CLUSTER);
                    entries.add(ModBlocks.BUDDING_ONYX);
                    entries.add(ModBlocks.ONYX_BLOCK);
                    entries.add(ModBlocks.ONYX_SLAB);
                    entries.add(ModBlocks.ONYX_STAIRS);
                    entries.add(ModBlocks.ONYX_WALL);
                    entries.add(ModBlocks.POLISHED_ONYX);
                    entries.add(ModBlocks.POLISHED_ONYX_SLAB);
                    entries.add(ModBlocks.POLISHED_ONYX_STAIRS);
                    entries.add(ModBlocks.POLISHED_ONYX_WALL);
                    entries.add(ModBlocks.BLACK_ONYX_BLOCK);
                    entries.add(ModBlocks.BLACK_ONYX_SLAB);
                    entries.add(ModBlocks.BLACK_ONYX_STAIRS);
                    entries.add(ModBlocks.BLACK_ONYX_WALL);
                    entries.add(ModBlocks.POLISHED_BLACK_ONYX);
                    entries.add(ModBlocks.POLISHED_BLACK_ONYX_SLAB);
                    entries.add(ModBlocks.POLISHED_BLACK_ONYX_STAIRS);
                    entries.add(ModBlocks.POLISHED_BLACK_ONYX_WALL);

                    //Redwood
                    entries.add(ModBlocks.REDWOOD_LEAVES);
                    entries.add(ModBlocks.REDWOOD_LOG);
                    entries.add(ModBlocks.REDWOOD_WOOD);
                    entries.add(ModBlocks.STRIPPED_REDWOOD_LOG);
                    entries.add(ModBlocks.STRIPPED_REDWOOD_WOOD);
                    entries.add(ModBlocks.REDWOOD_PLANKS);
                    entries.add(ModBlocks.REDWOOD_STAIRS);
                    entries.add(ModBlocks.REDWOOD_SLAB);
                    entries.add(ModBlocks.REDWOOD_FENCE);
                    entries.add(ModBlocks.REDWOOD_FENCE_GATE);
                    entries.add(ModBlocks.REDWOOD_BUTTON);
                    entries.add(ModBlocks.REDWOOD_PRESSURE_PLATE);
                    entries.add(ModBlocks.REDWOOD_DOOR);
                    entries.add(ModBlocks.REDWOOD_TRAPDOOR);

                    //Mosaic
                    entries.add(ModBlocks.OAK_MOSAIC);
                    entries.add(ModBlocks.SPRUCE_MOSAIC);
                    entries.add(ModBlocks.BIRCH_MOSAIC);
                    entries.add(ModBlocks.JUNGLE_MOSAIC);
                    entries.add(ModBlocks.ACACIA_MOSAIC);
                    entries.add(ModBlocks.DARK_OAK_MOSAIC);
                    entries.add(ModBlocks.MANGROVE_MOSAIC);
                    entries.add(ModBlocks.CRIMSON_MOSAIC);
                    entries.add(ModBlocks.WARPED_MOSAIC);
                    entries.add(ModBlocks.CHERRY_MOSAIC);
                    entries.add(ModBlocks.REDWOOD_MOSAIC);
                    entries.add(ModBlocks.OAK_MOSAIC_STAIRS);
                    entries.add(ModBlocks.SPRUCE_MOSAIC_STAIRS);
                    entries.add(ModBlocks.BIRCH_MOSAIC_STAIRS);
                    entries.add(ModBlocks.JUNGLE_MOSAIC_STAIRS);
                    entries.add(ModBlocks.ACACIA_MOSAIC_STAIRS);
                    entries.add(ModBlocks.DARK_OAK_MOSAIC_STAIRS);
                    entries.add(ModBlocks.MANGROVE_MOSAIC_STAIRS);
                    entries.add(ModBlocks.CRIMSON_MOSAIC_STAIRS);
                    entries.add(ModBlocks.WARPED_MOSAIC_STAIRS);
                    entries.add(ModBlocks.CHERRY_MOSAIC_STAIRS);
                    entries.add(ModBlocks.REDWOOD_MOSAIC_STAIRS);
                    entries.add(ModBlocks.OAK_MOSAIC_SLAB);
                    entries.add(ModBlocks.SPRUCE_MOSAIC_SLAB);
                    entries.add(ModBlocks.BIRCH_MOSAIC_SLAB);
                    entries.add(ModBlocks.JUNGLE_MOSAIC_SLAB);
                    entries.add(ModBlocks.ACACIA_MOSAIC_SLAB);
                    entries.add(ModBlocks.DARK_OAK_MOSAIC_SLAB);
                    entries.add(ModBlocks.MANGROVE_MOSAIC_SLAB);
                    entries.add(ModBlocks.CRIMSON_MOSAIC_SLAB);
                    entries.add(ModBlocks.WARPED_MOSAIC_SLAB);
                    entries.add(ModBlocks.CHERRY_MOSAIC_SLAB);
                    entries.add(ModBlocks.REDWOOD_MOSAIC_SLAB);

                    //Tables
                    entries.add(ModBlocks.OAK_TABLE);
                    entries.add(ModBlocks.SPRUCE_TABLE);
                    entries.add(ModBlocks.BIRCH_TABLE);
                    entries.add(ModBlocks.JUNGLE_TABLE);
                    entries.add(ModBlocks.ACACIA_TABLE);
                    entries.add(ModBlocks.DARK_OAK_TABLE);
                    entries.add(ModBlocks.MANGROVE_TABLE);
                    entries.add(ModBlocks.CRIMSON_TABLE);
                    entries.add(ModBlocks.WARPED_TABLE);
                    entries.add(ModBlocks.BAMBOO_TABLE);
                    entries.add(ModBlocks.CHERRY_TABLE);
                    entries.add(ModBlocks.REDWOOD_TABLE);
                    entries.add(ModBlocks.STRIPPED_OAK_TABLE);
                    entries.add(ModBlocks.STRIPPED_SPRUCE_TABLE);
                    entries.add(ModBlocks.STRIPPED_BIRCH_TABLE);
                    entries.add(ModBlocks.STRIPPED_JUNGLE_TABLE);
                    entries.add(ModBlocks.STRIPPED_ACACIA_TABLE);
                    entries.add(ModBlocks.STRIPPED_DARK_OAK_TABLE);
                    entries.add(ModBlocks.STRIPPED_MANGROVE_TABLE);
                    entries.add(ModBlocks.STRIPPED_CRIMSON_TABLE);
                    entries.add(ModBlocks.STRIPPED_WARPED_TABLE);
                    entries.add(ModBlocks.STRIPPED_CHERRY_TABLE);
                    entries.add(ModBlocks.STRIPPED_REDWOOD_TABLE);

                    //Chairs
                    entries.add(ModBlocks.OAK_CHAIR);
                    entries.add(ModBlocks.SPRUCE_CHAIR);
                    entries.add(ModBlocks.BIRCH_CHAIR);
                    entries.add(ModBlocks.JUNGLE_CHAIR);
                    entries.add(ModBlocks.ACACIA_CHAIR);
                    entries.add(ModBlocks.DARK_OAK_CHAIR);
                    entries.add(ModBlocks.MANGROVE_CHAIR);
                    entries.add(ModBlocks.CRIMSON_CHAIR);
                    entries.add(ModBlocks.WARPED_CHAIR);
                    entries.add(ModBlocks.BAMBOO_CHAIR);
                    entries.add(ModBlocks.CHERRY_CHAIR);
                    entries.add(ModBlocks.REDWOOD_CHAIR);
                    entries.add(ModBlocks.STRIPPED_OAK_CHAIR);
                    entries.add(ModBlocks.STRIPPED_SPRUCE_CHAIR);
                    entries.add(ModBlocks.STRIPPED_BIRCH_CHAIR);
                    entries.add(ModBlocks.STRIPPED_JUNGLE_CHAIR);
                    entries.add(ModBlocks.STRIPPED_ACACIA_CHAIR);
                    entries.add(ModBlocks.STRIPPED_DARK_OAK_CHAIR);
                    entries.add(ModBlocks.STRIPPED_MANGROVE_CHAIR);
                    entries.add(ModBlocks.STRIPPED_CRIMSON_CHAIR);
                    entries.add(ModBlocks.STRIPPED_WARPED_CHAIR);
                    entries.add(ModBlocks.STRIPPED_CHERRY_CHAIR);
                    entries.add(ModBlocks.STRIPPED_REDWOOD_CHAIR);

                    //Benches
                    entries.add(ModBlocks.OAK_BENCH);
                    entries.add(ModBlocks.SPRUCE_BENCH);
                    entries.add(ModBlocks.BIRCH_BENCH);
                    entries.add(ModBlocks.JUNGLE_BENCH);
                    entries.add(ModBlocks.ACACIA_BENCH);
                    entries.add(ModBlocks.DARK_OAK_BENCH);
                    entries.add(ModBlocks.MANGROVE_BENCH);
                    entries.add(ModBlocks.CRIMSON_BENCH);
                    entries.add(ModBlocks.WARPED_BENCH);
                    entries.add(ModBlocks.BAMBOO_BENCH);
                    entries.add(ModBlocks.CHERRY_BENCH);
                    entries.add(ModBlocks.REDWOOD_BENCH);
                    entries.add(ModBlocks.STRIPPED_OAK_BENCH);
                    entries.add(ModBlocks.STRIPPED_SPRUCE_BENCH);
                    entries.add(ModBlocks.STRIPPED_BIRCH_BENCH);
                    entries.add(ModBlocks.STRIPPED_JUNGLE_BENCH);
                    entries.add(ModBlocks.STRIPPED_ACACIA_BENCH);
                    entries.add(ModBlocks.STRIPPED_DARK_OAK_BENCH);
                    entries.add(ModBlocks.STRIPPED_MANGROVE_BENCH);
                    entries.add(ModBlocks.STRIPPED_CRIMSON_BENCH);
                    entries.add(ModBlocks.STRIPPED_WARPED_BENCH);
                    entries.add(ModBlocks.STRIPPED_CHERRY_BENCH);
                    entries.add(ModBlocks.STRIPPED_REDWOOD_BENCH);

                    //Cupboards
                    entries.add(ModBlocks.OAK_CUPBOARD);
                    entries.add(ModBlocks.SPRUCE_CUPBOARD);
                    entries.add(ModBlocks.BIRCH_CUPBOARD);
                    entries.add(ModBlocks.JUNGLE_CUPBOARD);
                    entries.add(ModBlocks.ACACIA_CUPBOARD);
                    entries.add(ModBlocks.DARK_OAK_CUPBOARD);
                    entries.add(ModBlocks.MANGROVE_CUPBOARD);
                    entries.add(ModBlocks.CRIMSON_CUPBOARD);
                    entries.add(ModBlocks.WARPED_CUPBOARD);
                    entries.add(ModBlocks.BAMBOO_CUPBOARD);
                    entries.add(ModBlocks.CHERRY_CUPBOARD);
                    entries.add(ModBlocks.REDWOOD_CUPBOARD);

                    entries.add(ModBlocks.OAK_WALL_CUPBOARD);
                    entries.add(ModBlocks.SPRUCE_WALL_CUPBOARD);
                    entries.add(ModBlocks.BIRCH_WALL_CUPBOARD);
                    entries.add(ModBlocks.JUNGLE_WALL_CUPBOARD);
                    entries.add(ModBlocks.ACACIA_WALL_CUPBOARD);
                    entries.add(ModBlocks.DARK_OAK_WALL_CUPBOARD);
                    entries.add(ModBlocks.MANGROVE_WALL_CUPBOARD);
                    entries.add(ModBlocks.CRIMSON_WALL_CUPBOARD);
                    entries.add(ModBlocks.WARPED_WALL_CUPBOARD);
                    entries.add(ModBlocks.BAMBOO_WALL_CUPBOARD);
                    entries.add(ModBlocks.CHERRY_WALL_CUPBOARD);
                    entries.add(ModBlocks.REDWOOD_WALL_CUPBOARD);

                    //Shelves
                    entries.add(ModBlocks.OAK_SHELF);
                    entries.add(ModBlocks.SPRUCE_SHELF);
                    entries.add(ModBlocks.BIRCH_SHELF);
                    entries.add(ModBlocks.JUNGLE_SHELF);
                    entries.add(ModBlocks.ACACIA_SHELF);
                    entries.add(ModBlocks.DARK_OAK_SHELF);
                    entries.add(ModBlocks.MANGROVE_SHELF);
                    entries.add(ModBlocks.CRIMSON_SHELF);
                    entries.add(ModBlocks.WARPED_SHELF);
                    entries.add(ModBlocks.BAMBOO_SHELF);
                    entries.add(ModBlocks.CHERRY_SHELF);
                    entries.add(ModBlocks.REDWOOD_SHELF);

                    //Trellises
                    entries.add(ModBlocks.OAK_TRELLIS);
                    entries.add(ModBlocks.SPRUCE_TRELLIS);
                    entries.add(ModBlocks.BIRCH_TRELLIS);
                    entries.add(ModBlocks.JUNGLE_TRELLIS);
                    entries.add(ModBlocks.ACACIA_TRELLIS);
                    entries.add(ModBlocks.DARK_OAK_TRELLIS);
                    entries.add(ModBlocks.MANGROVE_TRELLIS);
                    entries.add(ModBlocks.CRIMSON_TRELLIS);
                    entries.add(ModBlocks.WARPED_TRELLIS);
                    entries.add(ModBlocks.BAMBOO_TRELLIS);
                    entries.add(ModBlocks.CHERRY_TRELLIS);
                    entries.add(ModBlocks.REDWOOD_TRELLIS);

                    //Window Sill
                    entries.add(ModBlocks.OAK_WINDOW_SILL);
                    entries.add(ModBlocks.SPRUCE_WINDOW_SILL);
                    entries.add(ModBlocks.BIRCH_WINDOW_SILL);
                    entries.add(ModBlocks.JUNGLE_WINDOW_SILL);
                    entries.add(ModBlocks.ACACIA_WINDOW_SILL);
                    entries.add(ModBlocks.DARK_OAK_WINDOW_SILL);
                    entries.add(ModBlocks.MANGROVE_WINDOW_SILL);
                    entries.add(ModBlocks.CRIMSON_WINDOW_SILL);
                    entries.add(ModBlocks.WARPED_WINDOW_SILL);
                    entries.add(ModBlocks.BAMBOO_WINDOW_SILL);
                    entries.add(ModBlocks.CHERRY_WINDOW_SILL);
                    entries.add(ModBlocks.REDWOOD_WINDOW_SILL);

                    //Wall Bookshelf
                    entries.add(ModBlocks.OAK_WALL_BOOKSHELF);
                    entries.add(ModBlocks.SPRUCE_WALL_BOOKSHELF);
                    entries.add(ModBlocks.BIRCH_WALL_BOOKSHELF);
                    entries.add(ModBlocks.JUNGLE_WALL_BOOKSHELF);
                    entries.add(ModBlocks.ACACIA_WALL_BOOKSHELF);
                    entries.add(ModBlocks.DARK_OAK_WALL_BOOKSHELF);
                    entries.add(ModBlocks.MANGROVE_WALL_BOOKSHELF);
                    entries.add(ModBlocks.CRIMSON_WALL_BOOKSHELF);
                    entries.add(ModBlocks.WARPED_WALL_BOOKSHELF);
                    entries.add(ModBlocks.BAMBOO_WALL_BOOKSHELF);
                    entries.add(ModBlocks.CHERRY_WALL_BOOKSHELF);
                    entries.add(ModBlocks.REDWOOD_WALL_BOOKSHELF);

                    //Bulk
                    entries.add(ModBlocks.COPPER_BARS);
                    entries.add(ModBlocks.EXPOSED_COPPER_BARS);
                    entries.add(ModBlocks.WEATHERED_COPPER_BARS);
                    entries.add(ModBlocks.OXIDIZED_COPPER_BARS);
                    entries.add(ModBlocks.WAXED_COPPER_BARS);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_BARS);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_BARS);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_BARS);

                    entries.add(ModBlocks.IRON_BUTTON);
                    entries.add(ModBlocks.SILVER_BUTTON);
                    entries.add(ModBlocks.GOLDEN_BUTTON);
                    entries.add(ModBlocks.COPPER_BUTTON);
                    entries.add(ModBlocks.EXPOSED_COPPER_BUTTON);
                    entries.add(ModBlocks.WEATHERED_COPPER_BUTTON);
                    entries.add(ModBlocks.OXIDIZED_COPPER_BUTTON);
                    entries.add(ModBlocks.WAXED_COPPER_BUTTON);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);

                    entries.add(ModBlocks.SILVER_CHAIN);
                    entries.add(ModBlocks.GOLDEN_CHAIN);
                    entries.add(ModBlocks.COPPER_CHAIN);
                    entries.add(ModBlocks.EXPOSED_COPPER_CHAIN);
                    entries.add(ModBlocks.WEATHERED_COPPER_CHAIN);
                    entries.add(ModBlocks.OXIDIZED_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN);

                    entries.add(ModBlocks.LARGE_CHAIN);
                    entries.add(ModBlocks.LARGE_SILVER_CHAIN);
                    entries.add(ModBlocks.LARGE_GOLDEN_CHAIN);
                    entries.add(ModBlocks.LARGE_COPPER_CHAIN);
                    entries.add(ModBlocks.EXPOSED_LARGE_COPPER_CHAIN);
                    entries.add(ModBlocks.WEATHERED_LARGE_COPPER_CHAIN);
                    entries.add(ModBlocks.OXIDIZED_LARGE_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_LARGE_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_EXPOSED_LARGE_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_WEATHERED_LARGE_COPPER_CHAIN);
                    entries.add(ModBlocks.WAXED_OXIDIZED_LARGE_COPPER_CHAIN);

                    entries.add(ModBlocks.SILVER_DOOR);
                    entries.add(ModBlocks.GOLDEN_DOOR);
                    entries.add(ModBlocks.COPPER_DOOR);
                    entries.add(ModBlocks.EXPOSED_COPPER_DOOR);
                    entries.add(ModBlocks.WEATHERED_COPPER_DOOR);
                    entries.add(ModBlocks.OXIDIZED_COPPER_DOOR);
                    entries.add(ModBlocks.WAXED_COPPER_DOOR);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_DOOR);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_DOOR);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_DOOR);

                    entries.add(ModBlocks.GOLDEN_LANTERN);
                    entries.add(ModBlocks.GOLDEN_SOUL_LANTERN);
                    entries.add(ModBlocks.SILVER_LANTERN);
                    entries.add(ModBlocks.SILVER_SOUL_LANTERN);
                    entries.add(ModBlocks.COPPER_LANTERN);
                    entries.add(ModBlocks.EXPOSED_COPPER_LANTERN);
                    entries.add(ModBlocks.WEATHERED_COPPER_LANTERN);
                    entries.add(ModBlocks.OXIDIZED_COPPER_LANTERN);
                    entries.add(ModBlocks.WAXED_COPPER_LANTERN);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_LANTERN);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_LANTERN);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_LANTERN);
                    entries.add(ModBlocks.COPPER_SOUL_LANTERN);
                    entries.add(ModBlocks.EXPOSED_COPPER_SOUL_LANTERN);
                    entries.add(ModBlocks.WEATHERED_COPPER_SOUL_LANTERN);
                    entries.add(ModBlocks.OXIDIZED_COPPER_SOUL_LANTERN);
                    entries.add(ModBlocks.WAXED_COPPER_SOUL_LANTERN);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_SOUL_LANTERN);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_SOUL_LANTERN);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_SOUL_LANTERN);

                    entries.add(ModBlocks.COPPER_PRESSURE_PLATE);
                    entries.add(ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE);
                    entries.add(ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE);
                    entries.add(ModBlocks.OXIDIZED_COPPER_PRESSURE_PLATE);
                    entries.add(ModBlocks.WAXED_COPPER_PRESSURE_PLATE);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_PRESSURE_PLATE);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_PRESSURE_PLATE);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_PRESSURE_PLATE);

                    entries.add(ModBlocks.SILVER_TRAPDOOR);
                    entries.add(ModBlocks.GOLDEN_TRAPDOOR);
                    entries.add(ModBlocks.COPPER_TRAPDOOR);
                    entries.add(ModBlocks.EXPOSED_COPPER_TRAPDOOR);
                    entries.add(ModBlocks.WEATHERED_COPPER_TRAPDOOR);
                    entries.add(ModBlocks.OXIDIZED_COPPER_TRAPDOOR);
                    entries.add(ModBlocks.WAXED_COPPER_TRAPDOOR);
                    entries.add(ModBlocks.WAXED_EXPOSED_COPPER_TRAPDOOR);
                    entries.add(ModBlocks.WAXED_WEATHERED_COPPER_TRAPDOOR);
                    entries.add(ModBlocks.WAXED_OXIDIZED_COPPER_TRAPDOOR);

                    entries.add(ModBlocks.REINFORCED_COPPER_GLASS);
                    entries.add(ModBlocks.EXPOSED_REINFORCED_COPPER_GLASS);
                    entries.add(ModBlocks.WEATHERED_REINFORCED_COPPER_GLASS);
                    entries.add(ModBlocks.OXIDIZED_REINFORCED_COPPER_GLASS);
                    entries.add(ModBlocks.WAXED_REINFORCED_COPPER_GLASS);
                    entries.add(ModBlocks.WAXED_EXPOSED_REINFORCED_COPPER_GLASS);
                    entries.add(ModBlocks.WAXED_WEATHERED_REINFORCED_COPPER_GLASS);
                    entries.add(ModBlocks.WAXED_OXIDIZED_REINFORCED_COPPER_GLASS);

                    //Torches
                    entries.add(ModItems.AQUATIC_TORCH);
                    entries.add(ModItems.EXPOSED_AQUATIC_TORCH);
                    entries.add(ModItems.WEATHERED_AQUATIC_TORCH);
                    entries.add(ModItems.OXIDIZED_AQUATIC_TORCH);
                    entries.add(ModItems.WAXED_AQUATIC_TORCH);
                    entries.add(ModItems.WAXED_EXPOSED_AQUATIC_TORCH);
                    entries.add(ModItems.WAXED_WEATHERED_AQUATIC_TORCH);
                    entries.add(ModItems.WAXED_OXIDIZED_AQUATIC_TORCH);
                    entries.add(ModItems.PRISMARINE_TORCH);

                    //Lights

                    for (Block block : Registries.BLOCK) {
                        if (block instanceof LargeChandelierBlock largeChandelierBlock) entries.add(largeChandelierBlock);
                        if (block instanceof CandelabraBlock candelabraBlock) entries.add(candelabraBlock);
                        if (block instanceof ChandelierBlock chandelierBlock) entries.add(chandelierBlock);
                        if (block instanceof WallCandleBlock wallCandleBlock) entries.add(wallCandleBlock);
                    }

                }).build());

        Registry.register(Registries.ITEM_GROUP, CLUTTER_ITEMS, FabricItemGroup.builder()
                .displayName(Text.translatable("clutter.identifier").append(Text.translatable("itemgroup.clutter_items")))
                .icon(() -> new ItemStack(ModItems.CYAN_BUTTERFLY_ELYTRA))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.CLUTTER_RECIPE_BOOK);

                    entries.add(ModItems.ONYX);
                    entries.add(ModItems.RAW_ONYX);
                    entries.add(ModItems.SULPHUR);

                    entries.add(ModItems.SILVER_INGOT);
                    entries.add(ModItems.RAW_SILVER);
                    entries.add(ModItems.SILVER_NUGGET);
                    entries.add(ModItems.SILVER_HELMET);
                    entries.add(ModItems.SILVER_CHESTPLATE);
                    entries.add(ModItems.SILVER_LEGGINGS);
                    entries.add(ModItems.SILVER_BOOTS);

                    entries.add(ModItems.COPPER_NUGGET);
                    entries.add(ModItems.COPPER_DIVING_HELMET);
                    entries.add(ModItems.COPPER_DIVING_CHESTPLATE);
                    entries.add(ModItems.COPPER_DIVING_LEGGINGS);
                    entries.add(ModItems.COPPER_DIVING_BOOTS);

                    entries.add(ModItems.SEASHELL);
                    entries.add(ModItems.SEA_CONCH);
                    entries.add(ModItems.CLAM);
                    entries.add(ModItems.PEARL);

                    entries.add(ModItems.HOPS);
                    entries.add(ModItems.HOPS_SEEDS);

                    entries.add(ModItems.COTTON);
                    entries.add(ModItems.COTTON_SEEDS);

                    entries.add(ModItems.KIWI);
                    entries.add(ModItems.KIWI_SEEDS);

                    entries.add(ModItems.THORNBLOOM_PEAR);
                    entries.add(ModItems.BAKED_THORNBLOOM_PEAR);
                    entries.add(ModItems.THORNBLOOM_SEEDS);

                    entries.add(ModItems.GLOWLILY_BULB);

                    entries.add(ModItems.CHERRIES);
                    entries.add(ModItems.CHERRY_PIE);
                    entries.add(ModItems.KIWI_PIE);

                    entries.add(ModItems.RAW_CHORUS_ECHOFIN);
                    entries.add(ModItems.COOKED_CHORUS_ECHOFIN);
                    entries.add(ModItems.RAW_LEVITATING_ECHOFIN);
                    entries.add(ModItems.COOKED_LEVITATING_ECHOFIN);

                    entries.add(ModItems.GLOWLILY_SEEDLING);

                    entries.add(ModItems.SPONGE_SHARD);
                    entries.add(ModItems.RAW_VENISON);
                    entries.add(ModItems.COOKED_VENISON);
                    entries.add(ModItems.RAW_VENISON_RIBS);
                    entries.add(ModItems.COOKED_VENISON_RIBS);
                    entries.add(ModItems.MOSSBLOOM_ANTLER);

                    entries.add(ModItems.CHORUS_ECHOFIN_BUCKET);
                    entries.add(ModItems.LEVITATING_ECHOFIN_BUCKET);
                    entries.add(ModItems.BUTTERFLY_IN_A_BOTTLE);

                    entries.add(ModItems.KIWI_BIRD_EGG);
                    entries.add(ModItems.EMPEROR_PENGUIN_EGG);
                    entries.add(ModItems.BUTTERFLY_COCOON);

                    entries.add(ModItems.BEER_MUG);
                    entries.add(ModItems.WOODEN_MUG);

                    entries.add(ModItems.COPPER_COIN);
                    entries.add(ModItems.SILVER_COIN);
                    entries.add(ModItems.GOLDEN_COIN);
                    entries.add(ModItems.COMMON_COIN_POUCH);
                    entries.add(ModItems.UNCOMMON_COIN_POUCH);
                    entries.add(ModItems.RARE_COIN_POUCH);
                    entries.add(ModItems.EPIC_COIN_POUCH);

                    entries.add(ModItems.DECORATED_ELYTRA_SMITHING_TEMPLATE);
                    entries.add(ModItems.DECORATED_ELYTRA_SMITHING_TEMPLATE_SHARDS);

                    for (Item item : Registries.ITEM) {
                        if (item instanceof ClutterElytraItem elytraItem) entries.add(elytraItem);
                        if (item instanceof HatItem hatItem) entries.add(hatItem);
                    }

                }).build());

        Registry.register(Registries.ITEM_GROUP, CLUTTER_SPAWN_EGGS, FabricItemGroup.builder()
                .displayName(Text.translatable("clutter.identifier").append(Text.translatable("itemgroup.clutter_spawn_eggs")))
                .icon(() -> new ItemStack(ModItems.ECHOFIN_SPAWN_EGG))
                .entries((displayContext, entries) -> {
                    for (Item item : Registries.ITEM) {
                        if (item instanceof ClutterSpawnEggItem spawnEggItem) entries.add(spawnEggItem);
                    }
                }).build());

        Registry.register(Registries.ITEM_GROUP, CLUTTER_NATURE, FabricItemGroup.builder()
                .displayName(Text.translatable("clutter.identifier").append(Text.translatable("itemgroup.clutter_nature")))
                .icon(() -> new ItemStack(ModBlocks.GLOWLILY))
                .entries((displayContext, entries) -> {

                    entries.add(ModBlocks.GLOWLILY);
                    entries.add(ModBlocks.GLOWLILY_BLOCK);

                    entries.add(ModBlocks.THORNBLOOM);

                    entries.add(ModBlocks.KIWI_TREE_SAPLING);
                    entries.add(ModBlocks.RIPE_KIWI_LEAVES);
                    entries.add(ModBlocks.KIWI_LEAVES);

                    entries.add(ModBlocks.REDWOOD_SAPLING);
                    entries.add(ModBlocks.REDWOOD_LEAVES);
                    entries.add(ModBlocks.REDWOOD_LOG);

                    entries.add(ModBlocks.OVERGROWN_PACKED_MUD);

                    entries.add(ModBlocks.CATTAILS);
                    entries.add(ModBlocks.LUSH_MOSS);

                    entries.add(ModItems.SMALL_LILY_PADS);
                    entries.add(ModItems.GIANT_LILY_PAD_SEEDLING);
                    entries.add(ModItems.GIANT_LILY_PAD);

                    entries.add(ModBlocks.SMALL_BLUE_LUPINE);
                    entries.add(ModBlocks.BLUE_LUPINE);
                    entries.add(ModBlocks.SMALL_WHITE_LUPINE);
                    entries.add(ModBlocks.WHITE_LUPINE);
                    entries.add(ModBlocks.SMALL_PURPLE_LUPINE);
                    entries.add(ModBlocks.PURPLE_LUPINE);
                    entries.add(ModBlocks.SMALL_MAGENTA_LUPINE);
                    entries.add(ModBlocks.MAGENTA_LUPINE);
                    entries.add(ModBlocks.SMALL_YELLOW_LUPINE);
                    entries.add(ModBlocks.YELLOW_LUPINE);
                    entries.add(ModBlocks.SMALL_RED_LUPINE);
                    entries.add(ModBlocks.RED_LUPINE);

                    entries.add(ModBlocks.GIANT_FERN);

                    entries.add(ModBlocks.YELLOW_POLYPORE);
                    entries.add(ModBlocks.RED_POLYPORE);
                    entries.add(ModBlocks.WARPED_WALL_FUNGI);
                    entries.add(ModBlocks.CRIMSON_WALL_FUNGI);
                    entries.add(ModBlocks.BROWN_WALL_MUSHROOMS);
                    entries.add(ModBlocks.RED_WALL_MUSHROOMS);
                    entries.add(ModBlocks.SCULK_WALL_MUSHROOMS);
                    entries.add(ModBlocks.SCULK_MUSHROOM);

                    entries.add(ModBlocks.CUP_CORAL_BLOCK);
                    entries.add(ModBlocks.GHOST_CORAL_BLOCK);
                    entries.add(ModBlocks.DIAMOND_CORAL_BLOCK);
                    entries.add(ModBlocks.PASSION_CORAL_BLOCK);
                    entries.add(ModBlocks.ANCHOR_CORAL_BLOCK);
                    entries.add(ModBlocks.GEM_CORAL_BLOCK);
                    entries.add(ModBlocks.STONE_CORAL_BLOCK);
                    entries.add(ModBlocks.SLATE_CORAL_BLOCK);
                    entries.add(ModBlocks.THORN_CORAL_BLOCK);
                    entries.add(ModBlocks.COCOA_CORAL_BLOCK);
                    entries.add(ModBlocks.TOXIC_CORAL_BLOCK);

                    entries.add(ModBlocks.DEAD_CUP_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_GHOST_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_DIAMOND_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_PASSION_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_ANCHOR_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_GEM_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_STONE_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_SLATE_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_THORN_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_COCOA_CORAL_BLOCK);
                    entries.add(ModBlocks.DEAD_TOXIC_CORAL_BLOCK);

                    entries.add(ModBlocks.CUP_CORAL);
                    entries.add(ModBlocks.GHOST_CORAL);
                    entries.add(ModBlocks.DIAMOND_CORAL);
                    entries.add(ModBlocks.PASSION_CORAL);
                    entries.add(ModBlocks.ANCHOR_CORAL);
                    entries.add(ModBlocks.GEM_CORAL);
                    entries.add(ModBlocks.STONE_CORAL);
                    entries.add(ModBlocks.SLATE_CORAL);
                    entries.add(ModBlocks.THORN_CORAL);
                    entries.add(ModBlocks.COCOA_CORAL);
                    entries.add(ModBlocks.TOXIC_CORAL);

                    entries.add(ModBlocks.DEAD_CUP_CORAL);
                    entries.add(ModBlocks.DEAD_GHOST_CORAL);
                    entries.add(ModBlocks.DEAD_DIAMOND_CORAL);
                    entries.add(ModBlocks.DEAD_PASSION_CORAL);
                    entries.add(ModBlocks.DEAD_ANCHOR_CORAL);
                    entries.add(ModBlocks.DEAD_GEM_CORAL);
                    entries.add(ModBlocks.DEAD_STONE_CORAL);
                    entries.add(ModBlocks.DEAD_SLATE_CORAL);
                    entries.add(ModBlocks.DEAD_THORN_CORAL);
                    entries.add(ModBlocks.DEAD_COCOA_CORAL);
                    entries.add(ModBlocks.DEAD_TOXIC_CORAL);

                    entries.add(ModItems.CUP_CORAL_FAN);
                    entries.add(ModItems.GHOST_CORAL_FAN);
                    entries.add(ModItems.DIAMOND_CORAL_FAN);
                    entries.add(ModItems.PASSION_CORAL_FAN);
                    entries.add(ModItems.ANCHOR_CORAL_FAN);
                    entries.add(ModItems.GEM_CORAL_FAN);
                    entries.add(ModItems.STONE_CORAL_FAN);
                    entries.add(ModItems.SLATE_CORAL_FAN);
                    entries.add(ModItems.THORN_CORAL_FAN);
                    entries.add(ModItems.COCOA_CORAL_FAN);
                    entries.add(ModItems.TOXIC_CORAL_FAN);

                    entries.add(ModItems.DEAD_CUP_CORAL_FAN);
                    entries.add(ModItems.DEAD_GHOST_CORAL_FAN);
                    entries.add(ModItems.DEAD_DIAMOND_CORAL_FAN);
                    entries.add(ModItems.DEAD_PASSION_CORAL_FAN);
                    entries.add(ModItems.DEAD_ANCHOR_CORAL_FAN);
                    entries.add(ModItems.DEAD_GEM_CORAL_FAN);
                    entries.add(ModItems.DEAD_STONE_CORAL_FAN);
                    entries.add(ModItems.DEAD_SLATE_CORAL_FAN);
                    entries.add(ModItems.DEAD_THORN_CORAL_FAN);
                    entries.add(ModItems.DEAD_COCOA_CORAL_FAN);
                    entries.add(ModItems.DEAD_TOXIC_CORAL_FAN);

                }).build());


    }
}
