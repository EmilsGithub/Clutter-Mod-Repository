package net.emilsg.clutter.block;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.custom.ChimneyBlock;
import net.emilsg.clutter.block.custom.FoodBoxBlock;
import net.emilsg.clutter.block.custom.ModMushroomPlantBlock;
import net.emilsg.clutter.block.custom.MugBlock;
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

public class ModBlocks {

    public static final Block RED_WALL_MUSHROOMS = registerBlock("red_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);
    public static final Block BROWN_WALL_MUSHROOMS = registerBlock("brown_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);
    public static final Block SCULK_WALL_MUSHROOMS = registerBlock("sculk_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE).luminance(state -> 5)), ModItemGroup.CLUTTER);
    public static final Block SCULK_MUSHROOM = registerBlock("sculk_mushroom", new ModMushroomPlantBlock(FabricBlockSettings.copy(Blocks.DANDELION).luminance(state -> 5)), ModItemGroup.CLUTTER);

    public static final Block COBBLESTONE_CHIMNEY = registerBlock("cobblestone_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE_WALL).luminance(state -> 12)), ModItemGroup.CLUTTER);

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
}
