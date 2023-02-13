package net.emilsg.clutter.block;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.custom.FoodBoxBlock;
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
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block COPPER_LANTERN = registerBlock("copper_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block COPPER_CHAIN = registerBlock("copper_chain", new ChainBlock(FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block COPPER_BARS = registerBlock("copper_bars", new PaneBlock(FabricBlockSettings.copy(Blocks.IRON_BARS)), ModItemGroup.CLUTTER);

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
