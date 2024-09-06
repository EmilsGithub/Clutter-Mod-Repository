package net.emilsg.clutter.item;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.variants.EchofinVariant;
import net.emilsg.clutter.item.custom.*;
import net.emilsg.clutter.util.ModItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;


public class ModItems {

    public static final Item AQUATIC_TORCH = registerItem("aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.AQUATIC_TORCH, ModBlocks.AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item EXPOSED_AQUATIC_TORCH = registerItem("exposed_aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.EXPOSED_AQUATIC_TORCH, ModBlocks.EXPOSED_AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item WEATHERED_AQUATIC_TORCH = registerItem("weathered_aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.WEATHERED_AQUATIC_TORCH, ModBlocks.WEATHERED_AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item OXIDIZED_AQUATIC_TORCH = registerItem("oxidized_aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.OXIDIZED_AQUATIC_TORCH, ModBlocks.OXIDIZED_AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);

    public static final Item WAXED_AQUATIC_TORCH = registerItem("waxed_aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.WAXED_AQUATIC_TORCH, ModBlocks.WAXED_AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item WAXED_EXPOSED_AQUATIC_TORCH = registerItem("waxed_exposed_aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.WAXED_EXPOSED_AQUATIC_TORCH, ModBlocks.WAXED_EXPOSED_AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item WAXED_WEATHERED_AQUATIC_TORCH = registerItem("waxed_weathered_aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.WAXED_WEATHERED_AQUATIC_TORCH, ModBlocks.WAXED_WEATHERED_AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item WAXED_OXIDIZED_AQUATIC_TORCH = registerItem("waxed_oxidized_aquatic_torch", new VerticallyAttachableBlockItem(ModBlocks.WAXED_OXIDIZED_AQUATIC_TORCH, ModBlocks.WAXED_OXIDIZED_AQUATIC_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);

    public static final Item PRISMARINE_TORCH = registerItem("prismarine_torch", new VerticallyAttachableBlockItem(ModBlocks.PRISMARINE_TORCH, ModBlocks.PRISMARINE_WALL_TORCH, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);

    public static final Item DEAD_CUP_CORAL_FAN = registerItem("dead_cup_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_CUP_CORAL_FAN, ModBlocks.DEAD_CUP_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item CUP_CORAL_FAN = registerItem("cup_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.CUP_CORAL_FAN, ModBlocks.CUP_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_GHOST_CORAL_FAN = registerItem("dead_ghost_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_GHOST_CORAL_FAN, ModBlocks.DEAD_GHOST_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item GHOST_CORAL_FAN = registerItem("ghost_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.GHOST_CORAL_FAN, ModBlocks.GHOST_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_DIAMOND_CORAL_FAN = registerItem("dead_diamond_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_DIAMOND_CORAL_FAN, ModBlocks.DEAD_DIAMOND_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DIAMOND_CORAL_FAN = registerItem("diamond_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DIAMOND_CORAL_FAN, ModBlocks.DIAMOND_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_PASSION_CORAL_FAN = registerItem("dead_passion_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_PASSION_CORAL_FAN, ModBlocks.DEAD_PASSION_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item PASSION_CORAL_FAN = registerItem("passion_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.PASSION_CORAL_FAN, ModBlocks.PASSION_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_ANCHOR_CORAL_FAN = registerItem("dead_anchor_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_ANCHOR_CORAL_FAN, ModBlocks.DEAD_ANCHOR_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item ANCHOR_CORAL_FAN = registerItem("anchor_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.ANCHOR_CORAL_FAN, ModBlocks.ANCHOR_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_GEM_CORAL_FAN = registerItem("dead_gem_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_GEM_CORAL_FAN, ModBlocks.DEAD_GEM_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item GEM_CORAL_FAN = registerItem("gem_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.GEM_CORAL_FAN, ModBlocks.GEM_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_STONE_CORAL_FAN = registerItem("dead_stone_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_STONE_CORAL_FAN, ModBlocks.DEAD_STONE_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item STONE_CORAL_FAN = registerItem("stone_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.STONE_CORAL_FAN, ModBlocks.STONE_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_SLATE_CORAL_FAN = registerItem("dead_slate_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_SLATE_CORAL_FAN, ModBlocks.DEAD_SLATE_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item SLATE_CORAL_FAN = registerItem("slate_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.SLATE_CORAL_FAN, ModBlocks.SLATE_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_THORN_CORAL_FAN = registerItem("dead_thorn_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_THORN_CORAL_FAN, ModBlocks.DEAD_THORN_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item THORN_CORAL_FAN = registerItem("thorn_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.THORN_CORAL_FAN, ModBlocks.THORN_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_COCOA_CORAL_FAN = registerItem("dead_cocoa_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_COCOA_CORAL_FAN, ModBlocks.DEAD_COCOA_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item COCOA_CORAL_FAN = registerItem("cocoa_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.COCOA_CORAL_FAN, ModBlocks.COCOA_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item DEAD_TOXIC_CORAL_FAN = registerItem("dead_toxic_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.DEAD_TOXIC_CORAL_FAN, ModBlocks.DEAD_TOXIC_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item TOXIC_CORAL_FAN = registerItem("toxic_coral_fan", new VerticallyAttachableBlockItem(ModBlocks.TOXIC_CORAL_FAN, ModBlocks.TOXIC_CORAL_WALL_FAN, new FabricItemSettings(), Direction.DOWN), ModItemGroups.CLUTTER_BLOCKS);

    public static final Item COPPER_NUGGET = registerItem("copper_nugget", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SILVER_NUGGET = registerItem("silver_nugget", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SILVER_INGOT = registerItem("silver_ingot", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item RAW_SILVER = registerItem("raw_silver", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item ONYX = registerItem("onyx", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item RAW_ONYX = registerItem("raw_onyx", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SULPHUR = registerItem("sulphur", new SulphurItem(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item SEA_CONCH = registerItem("sea_conch", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SEASHELL = registerItem("seashell", new AliasedBlockItem(ModBlocks.SEASHELL_BLOCK, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CLAM = registerItem("clam", new AliasedBlockItem(ModBlocks.PEARL_CLAM_BLOCK, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item PEARL = registerItem("pearl", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item SILVER_HELMET = registerItem("silver_helmet", new ArmorItem(ModArmorMaterials.SILVER, ArmorItem.Type.HELMET, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SILVER_CHESTPLATE = registerItem("silver_chestplate", new ArmorItem(ModArmorMaterials.SILVER, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SILVER_LEGGINGS = registerItem("silver_leggings", new ArmorItem(ModArmorMaterials.SILVER, ArmorItem.Type.LEGGINGS, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SILVER_BOOTS = registerItem("silver_boots", new ArmorItem(ModArmorMaterials.SILVER, ArmorItem.Type.BOOTS, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item COPPER_DIVING_HELMET = registerItem("copper_diving_helmet", new CopperDivingArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COPPER_DIVING_CHESTPLATE = registerItem("copper_diving_chestplate", new CopperDivingArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COPPER_DIVING_LEGGINGS = registerItem("copper_diving_leggings", new CopperDivingArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COPPER_DIVING_BOOTS = registerItem("copper_diving_boots", new CopperDivingArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item HOPS = registerItem("hops", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0).build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item HOPS_SEEDS = registerItem("hops_seeds", new AliasedBlockItem(ModBlocks.HOPS_CROP, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COTTON = registerItem("cotton", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0).build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COTTON_SEEDS = registerItem("cotton_seeds", new AliasedBlockItem(ModBlocks.COTTON_CROP, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item THORNBLOOM_SEEDS = registerItem("thornbloom_seeds", new AliasedBlockItem(ModBlocks.THORNBLOOM_CROP, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item KIWI_SEEDS = registerItem("kiwi_seeds", new AliasedBlockItem(ModBlocks.KIWI_CROP, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item GLOWLILY_SEEDLING = registerItem("glowlily_seedling", new AliasedBlockItem(ModBlocks.GLOWLILY_CROP, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SPONGE_SHARD = registerItem("sponge_shard", new AliasedBlockItem(ModBlocks.SMALL_SPONGE, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item RAW_CHORUS_ECHOFIN = registerItem("raw_chorus_echofin", new RandomTeleportItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).meat().alwaysEdible().build()), 200, 10, 48), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COOKED_CHORUS_ECHOFIN = registerItem("cooked_chorus_echofin", new RandomTeleportItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.2f).meat().alwaysEdible().build()), 150, 20, 96), ModItemGroups.CLUTTER_ITEMS);
    public static final Item RAW_LEVITATING_ECHOFIN = registerItem("raw_levitating_echofin", new FoodWithEffectItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.1f).meat().alwaysEdible().build()), StatusEffects.LEVITATION, 100, 0, 10, 80), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COOKED_LEVITATING_ECHOFIN = registerItem("cooked_levitating_echofin", new FoodWithEffectItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.2f).meat().alwaysEdible().build()), StatusEffects.LEVITATION, 100, 1, 10, 60), ModItemGroups.CLUTTER_ITEMS);
    public static final Item RAW_VENISON = registerItem("raw_venison", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.2f).meat().build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COOKED_VENISON = registerItem("cooked_venison", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.4f).meat().build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item RAW_VENISON_RIBS = registerItem("raw_venison_ribs", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(5).saturationModifier(0.2f).meat().build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COOKED_VENISON_RIBS = registerItem("cooked_venison_ribs", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(10).saturationModifier(0.4f).meat().build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item THORNBLOOM_PEAR = registerItem("thornbloom_pear", new UseTimeFoodItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.25f).build()), 20), ModItemGroups.CLUTTER_ITEMS);
    public static final Item BAKED_THORNBLOOM_PEAR = registerItem("baked_thornbloom_pear", new UseTimeFoodItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.4f).build()), 10), ModItemGroups.CLUTTER_ITEMS);
    public static final Item KIWI = registerItem("kiwi", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item KIWI_PIE = registerItem("kiwi_pie", new Item(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5f).alwaysEdible().build())), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CHERRY_PIE = registerItem("cherry_pie", new FoodWithEffectItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(6).saturationModifier(0.5f).alwaysEdible().build()), StatusEffects.SPEED, 100, 1, 20, 0), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CHERRIES = registerItem("cherries", new FoodWithEffectItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).alwaysEdible().build()), StatusEffects.SPEED, 100, 0, 10, 0), ModItemGroups.CLUTTER_ITEMS);
    public static final Item GLOWLILY_BULB = registerItem("glowlily_bulb", new GlowlilyBulbItem(new FabricItemSettings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).build())), ModItemGroups.CLUTTER_ITEMS);

    public static final Item MOSSBLOOM_ANTLER = registerItem("mossbloom_antler", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item BEER_MUG = registerItem("beer_mug", new BeerItem(ModBlocks.BEER_MUG, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item WOODEN_MUG = registerItem("wooden_mug", new AliasedBlockItem(ModBlocks.WOODEN_MUG, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item COPPER_COIN = registerItem("copper_coin", new CoinItem(new FabricItemSettings().rarity(Rarity.UNCOMMON), ModBlocks.COPPER_COIN_STACK), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SILVER_COIN = registerItem("silver_coin", new CoinItem(new FabricItemSettings().rarity(Rarity.RARE), ModBlocks.SILVER_COIN_STACK), ModItemGroups.CLUTTER_ITEMS);
    public static final Item GOLDEN_COIN = registerItem("golden_coin", new CoinItem(new FabricItemSettings().rarity(Rarity.EPIC), ModBlocks.GOLDEN_COIN_STACK), ModItemGroups.CLUTTER_ITEMS);

    public static final Item COMMON_COIN_POUCH = registerItem("common_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.COMMON), 2, 3), ModItemGroups.CLUTTER_ITEMS);
    public static final Item UNCOMMON_COIN_POUCH = registerItem("uncommon_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.UNCOMMON), 3, 6), ModItemGroups.CLUTTER_ITEMS);
    public static final Item RARE_COIN_POUCH = registerItem("rare_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.RARE), 6, 9), ModItemGroups.CLUTTER_ITEMS);
    public static final Item EPIC_COIN_POUCH = registerItem("epic_coin_pouch", new CoinPouchItem(new FabricItemSettings().rarity(Rarity.EPIC), 9, 12), ModItemGroups.CLUTTER_ITEMS);

    public static final Item BUTTERFLY_COCOON = registerItem("butterfly_cocoon", new AliasedBlockItem(ModBlocks.BUTTERFLY_COCOON, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item KIWI_BIRD_EGG = registerItem("kiwi_bird_egg", new AliasedBlockItem(ModBlocks.KIWI_BIRD_EGG, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item EMPEROR_PENGUIN_EGG = registerItem("emperor_penguin_egg", new AliasedBlockItem(ModBlocks.EMPEROR_PENGUIN_EGG, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item SMALL_LILY_PADS = registerItem("small_lily_pads", new SmallLilyPadBlockItem(ModBlocks.SMALL_LILY_PADS, new FabricItemSettings()), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item GIANT_LILY_PAD = registerItem("giant_lily_pad", new GiantLilyPadItem(ModBlocks.GIANT_LILY_PAD, new FabricItemSettings()), ModItemGroups.CLUTTER_BLOCKS);
    public static final Item GIANT_LILY_PAD_SEEDLING = registerItem("giant_lily_pad_seedling", new PlaceableOnWaterItem(ModBlocks.GIANT_LILY_PAD_SEEDLING, new FabricItemSettings()), ModItemGroups.CLUTTER_BLOCKS);

    public static final Item LEVITATING_ECHOFIN_BUCKET = registerItem("levitating_echofin_bucket", new EchofinBucketItem(new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1), EchofinVariant.LEVITATING), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CHORUS_ECHOFIN_BUCKET = registerItem("chorus_echofin_bucket", new EchofinBucketItem(new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1), EchofinVariant.CHORUS), ModItemGroups.CLUTTER_ITEMS);

    public static final Item CLUTTER_RECIPE_BOOK = registerItem("clutter_recipe_book", new RecipeItemBookItem(new FabricItemSettings().maxCount(1), "item.clutter.clutter_recipe_book.tooltip", Formatting.BLUE), ModItemGroups.CLUTTER_ITEMS);

    public static final Item DECORATED_ELYTRA_SMITHING_TEMPLATE = registerItem("decorated_elytra_smithing_template", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item DECORATED_ELYTRA_SMITHING_TEMPLATE_SHARDS = registerItem("decorated_elytra_smithing_template_shards", new Item(new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item BUTTERFLY_IN_A_BOTTLE = registerItem("butterfly_in_a_bottle", new ButterflyBottleItem(new FabricItemSettings().recipeRemainder(Items.GLASS_BOTTLE).maxCount(1)), ModItemGroups.CLUTTER_ITEMS);

    public static final Item WHITE_BUTTERFLY_ELYTRA = registerItem("white_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.WHITE_DYE,  "white"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item LIGHT_GRAY_BUTTERFLY_ELYTRA = registerItem("light_gray_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.LIGHT_GRAY_DYE,  "light_gray"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item GRAY_BUTTERFLY_ELYTRA = registerItem("gray_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.GRAY_DYE,  "gray"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item BLACK_BUTTERFLY_ELYTRA = registerItem("black_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.BLACK_DYE,  "black"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item BROWN_BUTTERFLY_ELYTRA = registerItem("brown_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.BROWN_DYE,  "brown"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item RED_BUTTERFLY_ELYTRA = registerItem("red_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.RED_DYE,  "red"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item ORANGE_BUTTERFLY_ELYTRA = registerItem("orange_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.ORANGE_DYE,  "orange"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item YELLOW_BUTTERFLY_ELYTRA = registerItem("yellow_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.YELLOW_DYE,  "yellow"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item LIME_BUTTERFLY_ELYTRA = registerItem("lime_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.LIME_DYE,  "lime"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item GREEN_BUTTERFLY_ELYTRA = registerItem("green_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.GREEN_DYE,  "green"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CYAN_BUTTERFLY_ELYTRA = registerItem("cyan_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.CYAN_DYE,  "cyan"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item LIGHT_BLUE_BUTTERFLY_ELYTRA = registerItem("light_blue_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.LIGHT_BLUE_DYE,  "light_blue"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item BLUE_BUTTERFLY_ELYTRA = registerItem("blue_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.BLUE_DYE,  "blue"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item PURPLE_BUTTERFLY_ELYTRA = registerItem("purple_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.PURPLE_DYE,  "purple"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item MAGENTA_BUTTERFLY_ELYTRA = registerItem("magenta_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.MAGENTA_DYE,  "magenta"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item PINK_BUTTERFLY_ELYTRA = registerItem("pink_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.PINK_DYE,  "pink"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CRIMSON_BUTTERFLY_ELYTRA = registerItem("crimson_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.CRIMSON_ROOTS,  "crimson"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item WARPED_BUTTERFLY_ELYTRA = registerItem("warped_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.WARPED_ROOTS,  "warped"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SOUL_BUTTERFLY_ELYTRA = registerItem("soul_butterfly_elytra", new ButterflyElytraItem(new FabricItemSettings().maxDamage(432), Items.BONE,  "soul"), ModItemGroups.CLUTTER_ITEMS);

    public static final Item AMETHYST_GEMSTONE_ELYTRA = registerItem("amethyst_gemstone_elytra", new GemstoneElytraItem(new FabricItemSettings().maxDamage(432), Items.AMETHYST_SHARD, "amethyst"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item DIAMOND_GEMSTONE_ELYTRA = registerItem("diamond_gemstone_elytra", new GemstoneElytraItem(new FabricItemSettings().maxDamage(432), Items.DIAMOND, "diamond"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item EMERALD_GEMSTONE_ELYTRA = registerItem("emerald_gemstone_elytra", new GemstoneElytraItem(new FabricItemSettings().maxDamage(432), Items.EMERALD, "emerald"), ModItemGroups.CLUTTER_ITEMS);
    public static final Item QUARTZ_GEMSTONE_ELYTRA = registerItem("quartz_gemstone_elytra", new GemstoneElytraItem(new FabricItemSettings().maxDamage(432), Items.QUARTZ, "quartz"), ModItemGroups.CLUTTER_ITEMS);

    public static final Item BUTTERFLY_SPAWN_EGG = registerItem("butterfly_spawn_egg", new ClutterSpawnEggItem(ModEntities.BUTTERFLY, 757231, 12, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CHAMELEON_SPAWN_EGG = registerItem("chameleon_spawn_egg", new ClutterSpawnEggItem(ModEntities.CHAMELEON, 1744148, 16228345, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item ECHOFIN_SPAWN_EGG = registerItem("echofin_spawn_egg", new ClutterSpawnEggItem(ModEntities.ECHOFIN, 16511998, 4661575, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item MOSSBLOOM_SPAWN_EGG = registerItem("mossbloom_spawn_egg", new ClutterSpawnEggItem(ModEntities.MOSSBLOOM, 16053485, 7377453, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item KIWI_BIRD_SPAWN_EGG = registerItem("kiwi_bird_spawn_egg", new ClutterSpawnEggItem(ModEntities.KIWI_BIRD, 6243108, 6275609, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item EMPEROR_PENGUIN_SPAWN_EGG = registerItem("emperor_penguin_spawn_egg", new ClutterSpawnEggItem(ModEntities.EMPEROR_PENGUIN, 1973800, 16777210, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item BEAVER_SPAWN_EGG = registerItem("beaver_spawn_egg", new ClutterSpawnEggItem(ModEntities.BEAVER, 5916211, 3356222, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CAPYBARA_SPAWN_EGG = registerItem("capybara_spawn_egg", new ClutterSpawnEggItem(ModEntities.CAPYBARA, 2169626, 16651589, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CRIMSON_NEWT_SPAWN_EGG = registerItem("crimson_newt_spawn_egg", new ClutterSpawnEggItem(ModEntities.CRIMSON_NEWT, 15783361, 11280416, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item WARPED_NEWT_SPAWN_EGG = registerItem("warped_newt_spawn_egg", new ClutterSpawnEggItem(ModEntities.WARPED_NEWT, 1153925, 4464945, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item EMBER_TORTOISE_SPAWN_EGG = registerItem("ember_tortoise_spawn_egg", new ClutterSpawnEggItem(ModEntities.EMBER_TORTOISE, 6052956, 8924463, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item JELLYFISH_SPAWN_EGG = registerItem("jellyfish_spawn_egg", new ClutterSpawnEggItem(ModEntities.JELLYFISH, 5487623, 8732643, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item MANTA_RAY_SPAWN_EGG = registerItem("manta_ray_spawn_egg", new ClutterSpawnEggItem(ModEntities.MANTA_RAY, 12895428, 2566460, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SEAHORSE_SPAWN_EGG = registerItem("seahorse_spawn_egg", new ClutterSpawnEggItem(ModEntities.SEAHORSE, 0, 111, new FabricItemSettings()), ModItemGroups.CLUTTER_ITEMS);

    public static final Item BEACH_HAT = registerItem("beach_hat", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item TOP_HAT = registerItem("top_hat", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item BERET = registerItem("beret", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item COWBOY_HAT = registerItem("cowboy_hat", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item BUTTERFLY_WINGS = registerItem("butterfly_wings", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CROWN = registerItem("crown", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item CAP = registerItem("cap", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item PROPELLER_CAP = registerItem("propeller_cap", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item TIARA = registerItem("tiara", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item SILVER_TIARA = registerItem("silver_tiara", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);
    public static final Item VIKING_HELMET = registerItem("viking_helmet", new HatItem(new FabricItemSettings().maxCount(1)), ModItemGroups.CLUTTER_ITEMS);

    private static Item registerItem(String name, Item item, RegistryKey<ItemGroup> group) {
        return Registry.register(Registries.ITEM, new Identifier(Clutter.MOD_ID, name), item);
    }

    public static void registerModItems() {

    }
}
