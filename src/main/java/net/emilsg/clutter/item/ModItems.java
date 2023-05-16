package net.emilsg.clutter.item;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.variants.ButterflyVariant;
import net.emilsg.clutter.item.custom.*;
import net.emilsg.clutter.util.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Material;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


public class ModItems {

    public static final Item COPPER_NUGGET = registerItem("copper_nugget", new Item(new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);
    public static final Item SILVER_NUGGET = registerItem("silver_nugget", new Item(new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);
    public static final Item SILVER_INGOT = registerItem("silver_ingot", new Item(new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);
    public static final Item RAW_SILVER = registerItem("raw_silver", new Item(new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);

    public static final Item HOPS = registerItem("hops", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0).build())), ModItemGroup.CLUTTER_ITEMS);
    public static final Item HOPS_SEEDS = registerItem("hops_seeds", new AliasedBlockItem(ModBlocks.HOPS_CROP, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);

    public static final Item COTTON = registerItem("cotton", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0).build())), ModItemGroup.CLUTTER_ITEMS);
    public static final Item COTTON_SEEDS = registerItem("cotton_seeds", new AliasedBlockItem(ModBlocks.COTTON_CROP, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);

    public static final Item BEER_MUG = registerItem("beer_mug", new BeerItem(ModBlocks.BEER_MUG, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);
    public static final Item WOODEN_MUG = registerItem("wooden_mug", new AliasedBlockItem(ModBlocks.WOODEN_MUG, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);

    public static final Item COPPER_COIN = registerItem("copper_coin", new CoinItem(ModBlocks.COPPER_COIN_STACK, new FabricItemSettings().rarity(Rarity.UNCOMMON)), ModItemGroup.CLUTTER_ITEMS);
    public static final Item SILVER_COIN = registerItem("silver_coin", new CoinItem(ModBlocks.SILVER_COIN_STACK, new FabricItemSettings().rarity(Rarity.RARE)), ModItemGroup.CLUTTER_ITEMS);
    public static final Item GOLDEN_COIN = registerItem("golden_coin", new CoinItem(ModBlocks.GOLDEN_COIN_STACK, new FabricItemSettings().rarity(Rarity.EPIC)), ModItemGroup.CLUTTER_ITEMS);

    public static final Item COMMON_COIN_POUCH = registerItem("common_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.COMMON), 2, 3), ModItemGroup.CLUTTER_ITEMS);
    public static final Item UNCOMMON_COIN_POUCH = registerItem("uncommon_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.UNCOMMON), 3, 6), ModItemGroup.CLUTTER_ITEMS);
    public static final Item RARE_COIN_POUCH = registerItem("rare_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.RARE), 6, 9), ModItemGroup.CLUTTER_ITEMS);
    public static final Item EPIC_COIN_POUCH = registerItem("epic_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.EPIC), 9, 12), ModItemGroup.CLUTTER_ITEMS);

    public static final Item BUTTERFLY_COCOON = registerItem("butterfly_cocoon", new AliasedBlockItem(ModBlocks.BUTTERFLY_COCOON, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);
    public static final Item BUTTERFLY_SPAWN_EGG = registerItem("butterfly_spawn_egg", new SpawnEggItem(ModEntities.BUTTERFLY, -1, 423567, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);
    public static final Item CHAMELEON_SPAWN_EGG = registerItem("chameleon_spawn_egg", new SpawnEggItem(ModEntities.CHAMELEON, 26542, 987532, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);
    public static final Item END_FISH = registerItem("end_fish_spawn_egg", new SpawnEggItem(ModEntities.END_FISH, 56213, 5346, new FabricItemSettings()), ModItemGroup.CLUTTER_ITEMS);

    public static final Item USER_MANUAL = registerItem("user_manual", new ItemWithDescription(new FabricItemSettings(), "item.clutter.user_manual.tooltip", Formatting.BLUE), ModItemGroup.CLUTTER_ITEMS);

    public static final Item WHITE_BUTTERFLY_IN_A_BOTTLE = registerItem("white_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.WHITE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item LIGHT_GRAY_BUTTERFLY_IN_A_BOTTLE = registerItem("light_gray_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.LIGHT_GRAY), ModItemGroup.CLUTTER_ITEMS);
    public static final Item GRAY_BUTTERFLY_IN_A_BOTTLE = registerItem("gray_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.GRAY), ModItemGroup.CLUTTER_ITEMS);
    public static final Item BLACK_BUTTERFLY_IN_A_BOTTLE = registerItem("black_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.BLACK), ModItemGroup.CLUTTER_ITEMS);
    public static final Item BROWN_BUTTERFLY_IN_A_BOTTLE = registerItem("brown_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.BROWN), ModItemGroup.CLUTTER_ITEMS);
    public static final Item RED_BUTTERFLY_IN_A_BOTTLE = registerItem("red_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.RED), ModItemGroup.CLUTTER_ITEMS);
    public static final Item ORANGE_BUTTERFLY_IN_A_BOTTLE = registerItem("orange_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.ORANGE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item YELLOW_BUTTERFLY_IN_A_BOTTLE = registerItem("yellow_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.YELLOW), ModItemGroup.CLUTTER_ITEMS);
    public static final Item LIME_BUTTERFLY_IN_A_BOTTLE = registerItem("lime_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.LIME), ModItemGroup.CLUTTER_ITEMS);
    public static final Item GREEN_BUTTERFLY_IN_A_BOTTLE = registerItem("green_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.GREEN), ModItemGroup.CLUTTER_ITEMS);
    public static final Item CYAN_BUTTERFLY_IN_A_BOTTLE = registerItem("cyan_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.CYAN), ModItemGroup.CLUTTER_ITEMS);
    public static final Item LIGHT_BLUE_BUTTERFLY_IN_A_BOTTLE = registerItem("light_blue_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.LIGHT_BLUE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item BLUE_BUTTERFLY_IN_A_BOTTLE = registerItem("blue_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.BLUE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item PURPLE_BUTTERFLY_IN_A_BOTTLE = registerItem("purple_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.PURPLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item MAGENTA_BUTTERFLY_IN_A_BOTTLE = registerItem("magenta_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.MAGENTA), ModItemGroup.CLUTTER_ITEMS);
    public static final Item PINK_BUTTERFLY_IN_A_BOTTLE = registerItem("pink_butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings(), ButterflyVariant.PINK), ModItemGroup.CLUTTER_ITEMS);

    public static final Item WHITE_BUTTERFLY_ELYTRA = registerItem("white_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), WHITE_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item LIGHT_GRAY_BUTTERFLY_ELYTRA = registerItem("light_gray_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), LIGHT_GRAY_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item GRAY_BUTTERFLY_ELYTRA = registerItem("gray_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), GRAY_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item BLACK_BUTTERFLY_ELYTRA = registerItem("black_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), BLACK_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item BROWN_BUTTERFLY_ELYTRA = registerItem("brown_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), BROWN_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item RED_BUTTERFLY_ELYTRA = registerItem("red_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), RED_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item ORANGE_BUTTERFLY_ELYTRA = registerItem("orange_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), ORANGE_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item YELLOW_BUTTERFLY_ELYTRA = registerItem("yellow_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), YELLOW_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item LIME_BUTTERFLY_ELYTRA = registerItem("lime_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), LIME_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item GREEN_BUTTERFLY_ELYTRA = registerItem("green_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), GREEN_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item CYAN_BUTTERFLY_ELYTRA = registerItem("cyan_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), CYAN_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item LIGHT_BLUE_BUTTERFLY_ELYTRA = registerItem("light_blue_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), LIGHT_BLUE_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item BLUE_BUTTERFLY_ELYTRA = registerItem("blue_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), BLUE_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item PURPLE_BUTTERFLY_ELYTRA = registerItem("purple_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), PURPLE_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item MAGENTA_BUTTERFLY_ELYTRA = registerItem("magenta_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), MAGENTA_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);
    public static final Item PINK_BUTTERFLY_ELYTRA = registerItem("pink_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), PINK_BUTTERFLY_IN_A_BOTTLE), ModItemGroup.CLUTTER_ITEMS);

    private static Item registerItem(String name, Item item, ItemGroup group) {
        addToItemGroup(group, item);
        return Registry.register(Registries.ITEM, new Identifier(Clutter.MOD_ID, name), item);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }



    public static void registerModItems() {
        Clutter.LOGGER.info("Registering Mod Items for " + Clutter.MOD_ID);
    }
}
