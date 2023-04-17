package net.emilsg.clutter.item;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.custom.BeerItem;
import net.emilsg.clutter.util.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


public class ModItems {

    public static final Item COPPER_NUGGET = registerItem("copper_nugget", new Item(new FabricItemSettings()));
    public static final Item SILVER_NUGGET = registerItem("silver_nugget", new Item(new FabricItemSettings()));
    public static final Item SILVER_INGOT = registerItem("silver_ingot", new Item(new FabricItemSettings()));
    public static final Item RAW_SILVER = registerItem("raw_silver", new Item(new FabricItemSettings()));

    public static final Item HOPS = registerItem("hops", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0).build())));
    public static final Item HOPS_SEEDS = registerItem("hops_seeds", new AliasedBlockItem(ModBlocks.HOPS_CROP, new FabricItemSettings()));

    public static final Item COTTON = registerItem("cotton", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0).build())));
    public static final Item COTTON_SEEDS = registerItem("cotton_seeds", new AliasedBlockItem(ModBlocks.COTTON_CROP, new FabricItemSettings()));

    public static final Item BEER_MUG = registerItem("beer_mug", new BeerItem(ModBlocks.BEER_MUG, new FabricItemSettings()));
    public static final Item WOODEN_MUG = registerItem("wooden_mug", new AliasedBlockItem(ModBlocks.WOODEN_MUG, new FabricItemSettings()));

    public static final Item COPPER_COIN = registerItem("copper_coin", new Item(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON)));
    public static final Item SILVER_COIN = registerItem("silver_coin", new Item(new FabricItemSettings().maxCount(16).rarity(Rarity.RARE)));
    public static final Item GOLDEN_COIN = registerItem("golden_coin", new Item(new FabricItemSettings().maxCount(16).rarity(Rarity.EPIC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Clutter.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {
        Item[] items = {
                COPPER_NUGGET,
                SILVER_NUGGET,
                SILVER_INGOT,
                RAW_SILVER,
                HOPS,
                HOPS_SEEDS,
                COTTON,
                COTTON_SEEDS,
                BEER_MUG,
                WOODEN_MUG,
                COPPER_COIN,
                SILVER_COIN,
                GOLDEN_COIN
        };

        for (Item item : items) {
            addToItemGroup(ModItemGroup.CLUTTER, item);
        }
    }


    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }
    public static void registerModItems() {
        Clutter.LOGGER.info("Registering Mod Items for " + Clutter.MOD_ID);
        addItemsToItemGroup();
    }
}
