package net.emilsg.clutter.block;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.custom.*;
import net.emilsg.clutter.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ModBlocks {

    public static final Block RED_WALL_MUSHROOMS = registerBlock("red_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);
    public static final Block BROWN_WALL_MUSHROOMS = registerBlock("brown_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);
    public static final Block SCULK_WALL_MUSHROOMS = registerBlock("sculk_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE).luminance(state -> 6)), ModItemGroup.CLUTTER);
    public static final Block SCULK_MUSHROOM = registerBlock("sculk_mushroom", new ModMushroomPlantBlock(FabricBlockSettings.copy(Blocks.DANDELION).luminance(state -> 6)), ModItemGroup.CLUTTER);

    public static final Block ARMCHAIR = registerBlock("armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.WHITE_WOOL)), ModItemGroup.CLUTTER);

    public static final Block FLOOR_SEATING = registerBlock("floor_seating", new FloorSeatBlock(FabricBlockSettings.copy(Blocks.LIGHT_GRAY_WOOL)), ModItemGroup.CLUTTER);

    public static final Block COBBLESTONE_CHIMNEY = registerBlock("cobblestone_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE_WALL)), ModItemGroup.CLUTTER);
    public static final Block BRICK_CHIMNEY = registerBlock("brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block STONE_BRICK_CHIMNEY = registerBlock("stone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block MOSSY_STONE_BRICK_CHIMNEY = registerBlock("mossy_stone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.MOSSY_STONE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block DEEPSLATE_BRICK_CHIMNEY = registerBlock("deepslate_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block ANDESITE_CHIMNEY = registerBlock("andesite_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.ANDESITE_WALL)), ModItemGroup.CLUTTER);
    public static final Block DIORITE_CHIMNEY = registerBlock("diorite_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.DIORITE_WALL)), ModItemGroup.CLUTTER);
    public static final Block GRANITE_CHIMNEY = registerBlock("granite_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.GRANITE_WALL)), ModItemGroup.CLUTTER);
    public static final Block MUD_BRICK_CHIMNEY = registerBlock("mud_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.MUD_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block NETHER_BRICK_CHIMNEY = registerBlock("nether_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.NETHER_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block RED_NETHER_BRICK_CHIMNEY = registerBlock("red_nether_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.RED_NETHER_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block POLISHED_BLACKSTONE_BRICK_CHIMNEY = registerBlock("polished_blackstone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.POLISHED_BLACKSTONE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block BLACKSTONE_CHIMNEY = registerBlock("blackstone_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.BLACKSTONE_WALL)), ModItemGroup.CLUTTER);
    public static final Block END_STONE_BRICK_CHIMNEY = registerBlock("end_stone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.END_STONE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block DEEPSLATE_TILE_CHIMNEY = registerBlock("deepslate_tile_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_TILE_WALL)), ModItemGroup.CLUTTER);

    public static final Block OAK_WINDOW_SILL = registerBlock("oak_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block DARK_OAK_WINDOW_SILL = registerBlock("dark_oak_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.DARK_OAK_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block BIRCH_WINDOW_SILL = registerBlock("birch_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.BIRCH_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block JUNGLE_WINDOW_SILL = registerBlock("jungle_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.JUNGLE_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block ACACIA_WINDOW_SILL = registerBlock("acacia_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.ACACIA_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block SPRUCE_WINDOW_SILL = registerBlock("spruce_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block CRIMSON_WINDOW_SILL = registerBlock("crimson_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.CRIMSON_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block WARPED_WINDOW_SILL = registerBlock("warped_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.WARPED_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block MANGROVE_WINDOW_SILL = registerBlock("mangrove_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.MANGROVE_PLANKS)), ModItemGroup.CLUTTER);

    public static final Block OAK_WALL_BOOKSHELF = registerBlock("oak_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block DARK_OAK_WALL_BOOKSHELF = registerBlock("dark_oak_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.DARK_OAK_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block BIRCH_WALL_BOOKSHELF = registerBlock("birch_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.BIRCH_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block JUNGLE_WALL_BOOKSHELF = registerBlock("jungle_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.JUNGLE_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block ACACIA_WALL_BOOKSHELF = registerBlock("acacia_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.ACACIA_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block SPRUCE_WALL_BOOKSHELF = registerBlock("spruce_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block CRIMSON_WALL_BOOKSHELF = registerBlock("crimson_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.CRIMSON_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block WARPED_WALL_BOOKSHELF = registerBlock("warped_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.WARPED_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block MANGROVE_WALL_BOOKSHELF = registerBlock("mangrove_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.MANGROVE_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);

    public static final Block WOODEN_BEER_MUG = registerBlock("wooden_beer_mug", new MugBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block WOODEN_MUG = registerBlock("wooden_mug", new MugBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)), ModItemGroup.CLUTTER);

    public static final Block COPPER_BUTTON = registerBlock("copper_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 40, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block IRON_BUTTON = registerBlock("iron_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 80, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block COPPER_LANTERN = registerBlock("copper_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block COPPER_CHAIN = registerBlock("copper_chain", new ChainBlock(FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block COPPER_BARS = registerBlock("copper_bars", new PaneBlock(FabricBlockSettings.copy(Blocks.IRON_BARS)), ModItemGroup.CLUTTER);
    public static final Block COPPER_DOOR = registerBlock("copper_door", new DoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block COPPER_TRAPDOOR = registerBlock("copper_trapdoor", new TrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block COPPER_PRESSURE_PLATE = registerBlock("copper_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);

    public static final Block FOOD_BOX = registerBlock("food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block CARROT_FOOD_BOX = registerBlock("carrot_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block POTATO_FOOD_BOX = registerBlock("potato_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block BEETROOT_FOOD_BOX = registerBlock("beetroot_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block APPLE_FOOD_BOX = registerBlock("apple_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(Clutter.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Clutter.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    public static void registerModBlocks() {
        Clutter.LOGGER.info("Registering ModBlocks for " + Clutter.MOD_ID);
    }

    private static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}
