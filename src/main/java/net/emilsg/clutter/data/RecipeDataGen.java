package net.emilsg.clutter.data;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ClutterWoodType;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.item.custom.ClutterElytraItem;
import net.emilsg.clutter.recipe.KilningRecipe;
import net.emilsg.clutter.util.ModItemTags;
import net.emilsg.clutter.util.RegistryType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeDataGen extends FabricRecipeProvider {

    public RecipeDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void offerKilning(RecipeExporter exporter, ItemConvertible input, ItemConvertible output, float experience, int cookingTime, RecipeCategory category, String group) {
        List<ItemConvertible> inputs = new ArrayList<>();
        inputs.add(input);
        offerMultipleOptions(exporter, KilningRecipe.Serializer.INSTANCE, KilningRecipe::new, inputs, output, experience, cookingTime, group, "_from_kilning");
    }

    public static <T extends AbstractCookingRecipe> void offerMultipleOptions(
            RecipeExporter exporter,
            RecipeSerializer<T> serializer,
            AbstractCookingRecipe.RecipeFactory<T> recipeFactory,
            List<ItemConvertible> inputs,
            ItemConvertible output,
            float experience,
            int cookingTime,
            String group,
            String suffix
    ) {
        for (ItemConvertible itemConvertible : inputs) {
            CookingRecipeJsonBuilder.create(Ingredient.ofItems(itemConvertible), RecipeCategory.MISC, output, experience, cookingTime, serializer, recipeFactory)
                    .group(group)
                    .criterion(hasItem(itemConvertible), conditionsFromItem(itemConvertible))
                    .offerTo(exporter, getItemPath(output) + suffix + "_" + getItemPath(itemConvertible));
        }
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerKilning(exporter, Items.SAND, Items.GLASS, 0.2f, 50, RecipeCategory.MISC, "glass");
        offerKilning(exporter, Items.RED_SAND, Items.GLASS, 1, 50, RecipeCategory.MISC, "glass");
        offerKilning(exporter, Items.CLAY_BALL, Items.BRICK, 1, 50, RecipeCategory.MISC, "brick");
        offerKilning(exporter, Items.CLAY, Items.TERRACOTTA, 1, 50, RecipeCategory.MISC, "terracotta");
        offerKilning(exporter, Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT, 1, 50, RecipeCategory.MISC, "popped_chorus_fruit");
        offerKilning(exporter, Items.NETHERRACK, Items.NETHER_BRICK, 1, 50, RecipeCategory.MISC, "nether_brick");
        offerKilning(exporter, Items.COBBLESTONE, Items.STONE, 1, 50, RecipeCategory.MISC, "stone");
        offerKilning(exporter, Items.STONE, Items.SMOOTH_STONE, 1, 50, RecipeCategory.MISC, "smooth_stone");
        offerKilning(exporter, Items.COBBLED_DEEPSLATE, Items.DEEPSLATE, 1, 50, RecipeCategory.MISC, "deepslate");
        offerKilning(exporter, Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ, 1, 50, RecipeCategory.MISC, "smooth_quartz");
        offerKilning(exporter, Items.BASALT, Items.SMOOTH_BASALT, 1, 50, RecipeCategory.MISC, "smooth_basalt");
        offerKilning(exporter, Items.SANDSTONE, Items.SMOOTH_SANDSTONE, 1, 50, RecipeCategory.MISC, "smooth_sandstone");
        offerKilning(exporter, Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE, 1, 50, RecipeCategory.MISC, "smooth_red_sandstone");
        offerKilning(exporter, Items.WHITE_TERRACOTTA, Items.WHITE_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "white_glazed_terracotta");
        offerKilning(exporter, Items.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "light_gray_glazed_terracotta");
        offerKilning(exporter, Items.GRAY_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "gray_glazed_terracotta");
        offerKilning(exporter, Items.BLACK_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "black_glazed_terracotta");
        offerKilning(exporter, Items.BROWN_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "brown_glazed_terracotta");
        offerKilning(exporter, Items.RED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "red_glazed_terracotta");
        offerKilning(exporter, Items.ORANGE_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "orange_glazed_terracotta");
        offerKilning(exporter, Items.YELLOW_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "yellow_glazed_terracotta");
        offerKilning(exporter, Items.LIME_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "lime_glazed_terracotta");
        offerKilning(exporter, Items.GREEN_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "green_glazed_terracotta");
        offerKilning(exporter, Items.CYAN_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "cyan_glazed_terracotta");
        offerKilning(exporter, Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "light_blue_glazed_terracotta");
        offerKilning(exporter, Items.BLUE_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "blue_glazed_terracotta");
        offerKilning(exporter, Items.PURPLE_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "purple_glazed_terracotta");
        offerKilning(exporter, Items.MAGENTA_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "magenta_glazed_terracotta");
        offerKilning(exporter, Items.PINK_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA, 1, 50, RecipeCategory.MISC, "pink_glazed_terracotta");

        offerWoodRecipes(exporter, ClutterWoodType.REDWOOD);
        offerWoodRecipes(exporter, ClutterWoodType.OAK);
        offerWoodRecipes(exporter, ClutterWoodType.BIRCH);
        offerWoodRecipes(exporter, ClutterWoodType.JUNGLE);
        offerWoodRecipes(exporter, ClutterWoodType.ACACIA);
        offerWoodRecipes(exporter, ClutterWoodType.SPRUCE);
        offerWoodRecipes(exporter, ClutterWoodType.DARK_OAK);
        offerWoodRecipes(exporter, ClutterWoodType.MANGROVE);
        offerWoodRecipes(exporter, ClutterWoodType.CRIMSON);
        offerWoodRecipes(exporter, ClutterWoodType.WARPED);
        offerWoodRecipes(exporter, ClutterWoodType.CHERRY);
        offerWoodRecipes(exporter, ClutterWoodType.BAMBOO);

        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.BLUE_LUPINE, Items.BLUE_DYE, RecipeCategory.MISC, 2);
        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.SMALL_BLUE_LUPINE, Items.BLUE_DYE, RecipeCategory.MISC, 1);

        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.RED_LUPINE, Items.RED_DYE, RecipeCategory.MISC, 2);
        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.SMALL_RED_LUPINE, Items.RED_DYE, RecipeCategory.MISC, 1);

        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.MAGENTA_LUPINE, Items.MAGENTA_DYE, RecipeCategory.MISC, 2);
        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.SMALL_MAGENTA_LUPINE, Items.MAGENTA_DYE, RecipeCategory.MISC, 1);

        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.YELLOW_LUPINE, Items.YELLOW_DYE, RecipeCategory.MISC, 2);
        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.SMALL_YELLOW_LUPINE, Items.YELLOW_DYE, RecipeCategory.MISC, 1);

        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.WHITE_LUPINE, Items.WHITE_DYE, RecipeCategory.MISC, 2);
        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.SMALL_WHITE_LUPINE, Items.WHITE_DYE, RecipeCategory.MISC, 1);

        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.PURPLE_LUPINE, Items.PURPLE_DYE, RecipeCategory.MISC, 2);
        offerSimpleDyeRecipeWithCount(exporter, ModBlocks.SMALL_PURPLE_LUPINE, Items.PURPLE_DYE, RecipeCategory.MISC, 1);


        offerClutterWaxingRecipes(exporter);

        offerCombinationRecipe(exporter, Items.SCULK_VEIN, Items.RED_MUSHROOM, ModBlocks.SCULK_MUSHROOM, 2, RecipeCategory.MISC);
        offerCombinationRecipe(exporter, Items.SCULK_VEIN, Items.BROWN_MUSHROOM, ModBlocks.SCULK_MUSHROOM, 2, RecipeCategory.MISC);
        offerCombinationRecipe(exporter, Items.SCULK_VEIN, ModBlocks.BROWN_WALL_MUSHROOMS, ModBlocks.SCULK_WALL_MUSHROOMS, 2, RecipeCategory.MISC);
        offerCombinationRecipe(exporter, Items.SCULK_VEIN, ModBlocks.RED_WALL_MUSHROOMS, ModBlocks.SCULK_WALL_MUSHROOMS, 2, RecipeCategory.MISC);
        offerPlusRecipe(exporter, Blocks.GLASS, ModItems.COPPER_NUGGET, ModBlocks.REINFORCED_COPPER_GLASS, RecipeCategory.BUILDING_BLOCKS);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.QUARTZ_CRYSTAL)
                .pattern("Q")
                .pattern("Q")
                .input('Q', Items.QUARTZ)
                .criterion(hasItem(Items.QUARTZ), conditionsFromItem(Items.QUARTZ))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.QUARTZ_CRYSTAL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.AQUATIC_TORCH)
                .pattern(" N ")
                .pattern("NCN")
                .pattern(" I ")
                .input('N', ModItemTags.C_COPPER_NUGGETS)
                .input('C', ItemTags.COALS)
                .input('I', Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(ModItems.COPPER_NUGGET), conditionsFromItem(ModItems.COPPER_NUGGET))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModItems.AQUATIC_TORCH)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.PRISMARINE_TORCH)
                .pattern(" P ")
                .pattern(" C ")
                .pattern(" P ")
                .input('P', Items.PRISMARINE_SHARD)
                .input('C', Items.PRISMARINE_CRYSTALS)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .criterion(hasItem(Items.PRISMARINE_CRYSTALS), conditionsFromItem(Items.PRISMARINE_CRYSTALS))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModItems.PRISMARINE_TORCH)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.TALL_BOTTLE)
                .pattern(" G ")
                .pattern(" G ")
                .pattern("GDG")
                .input('G', Items.GLASS)
                .input('D', Items.GREEN_DYE)
                .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.TALL_BOTTLE)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.LAB_BOTTLE)
                .input(Items.GLASS_BOTTLE)
                .criterion(hasItem(Items.GLASS_BOTTLE), conditionsFromItem(Items.GLASS_BOTTLE))
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.LAB_BOTTLE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BRICK_KILN)
                .pattern("SSS")
                .pattern("BFB")
                .pattern("BBB")
                .input('S', Items.SMOOTH_STONE)
                .input('B', Items.BRICKS)
                .input('F', Items.FURNACE)
                .criterion(hasItem(Items.FURNACE), conditionsFromItem(Items.FURNACE))
                .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.BRICK_KILN)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CARDBOARD_BOX)
                .pattern(" P ")
                .pattern("PCP")
                .pattern(" P ")
                .input('P', Items.PAPER)
                .input('C', Items.CHEST)
                .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.CARDBOARD_BOX)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.RED_PRESENT)
                .pattern("RPR")
                .pattern("PCP")
                .pattern("RPR")
                .input('P', Items.PAPER)
                .input('R', Items.RED_DYE)
                .input('C', ModBlocks.CARDBOARD_BOX)
                .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                .criterion(hasItem(ModBlocks.CARDBOARD_BOX), conditionsFromItem(ModBlocks.CARDBOARD_BOX))
                .criterion(hasItem(Items.RED_DYE), conditionsFromItem(Items.RED_DYE))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.RED_PRESENT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAILBOX)
                .pattern(" I ")
                .pattern("ICI")
                .pattern(" R ")
                .input('I', Items.IRON_INGOT)
                .input('C', Items.CHEST)
                .input('R', Items.REDSTONE)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.MAILBOX)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PET_BED)
                .pattern("WWW")
                .input('W', ItemTags.WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromTag(ItemTags.WOOL))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModBlocks.PET_BED)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PETTING_GLOVE)
                .pattern(" LL")
                .pattern("LLL")
                .pattern(" WW")
                .input('L', Items.LEATHER)
                .input('W', ItemTags.WOOL)
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromTag(ItemTags.WOOL))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModItems.PETTING_GLOVE)));

        offerCompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.WET_SPONGE, ModItems.SPONGE_SHARD);
        offer2x2CompactingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GLOWLILY_BLOCK, ModItems.GLOWLILY_BULB);

        offerArmorRecipe(exporter, ModItems.SILVER_INGOT, ModItems.SILVER_HELMET, ModItems.SILVER_CHESTPLATE, ModItems.SILVER_LEGGINGS, ModItems.SILVER_BOOTS);

        offerSulphurRecipe(exporter, ModBlocks.ONYX_BLOCK, ModBlocks.BLACK_ONYX_BLOCK, RecipeCategory.BUILDING_BLOCKS);
        offerSulphurRecipe(exporter, ModBlocks.ONYX_SLAB, ModBlocks.BLACK_ONYX_SLAB, RecipeCategory.BUILDING_BLOCKS);
        offerSulphurRecipe(exporter, ModBlocks.ONYX_STAIRS, ModBlocks.BLACK_ONYX_STAIRS, RecipeCategory.BUILDING_BLOCKS);
        offerSulphurRecipe(exporter, ModBlocks.ONYX_WALL, ModBlocks.BLACK_ONYX_WALL, RecipeCategory.BUILDING_BLOCKS);
        offerSulphurRecipe(exporter, ModBlocks.POLISHED_ONYX, ModBlocks.POLISHED_BLACK_ONYX, RecipeCategory.BUILDING_BLOCKS);
        offerSulphurRecipe(exporter, ModBlocks.POLISHED_ONYX_SLAB, ModBlocks.POLISHED_BLACK_ONYX_SLAB, RecipeCategory.BUILDING_BLOCKS);
        offerSulphurRecipe(exporter, ModBlocks.POLISHED_ONYX_STAIRS, ModBlocks.POLISHED_BLACK_ONYX_STAIRS, RecipeCategory.BUILDING_BLOCKS);
        offerSulphurRecipe(exporter, ModBlocks.POLISHED_ONYX_WALL, ModBlocks.POLISHED_BLACK_ONYX_WALL, RecipeCategory.BUILDING_BLOCKS);

        offerHatRecipe(exporter, Items.WHEAT, Items.PINK_DYE, ModItems.BEACH_HAT);
        offerHatRecipe(exporter, Items.LEATHER, Items.GOLD_NUGGET, ModItems.COWBOY_HAT);
        offerHatRecipe(exporter, Items.BLACK_WOOL, Items.RED_DYE, ModItems.TOP_HAT);
        offerHatRecipe(exporter, Items.LIGHT_BLUE_WOOL, Items.WHITE_DYE, ModItems.CAP);
        offerHatRecipe(exporter, Items.PINK_WOOL, Items.BLACK_DYE, ModItems.BERET);
        offerHatRecipe(exporter, Items.GOLD_INGOT, Items.EMERALD, ModItems.TIARA);
        offerHatRecipe(exporter, ModItems.SILVER_INGOT, Items.DIAMOND, ModItems.SILVER_TIARA);
        offerHatRecipe(exporter, Items.EMERALD, Items.GOLD_INGOT, ModItems.CROWN);
        offerHatRecipe(exporter, Items.BONE, Items.IRON_INGOT, ModItems.VIKING_HELMET);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PROPELLER_CAP)
                .input(Ingredient.fromTag(ModItemTags.DYES))
                .input(Ingredient.fromTag(ModItemTags.DYES))
                .input(Ingredient.fromTag(ModItemTags.DYES))
                .input(ModItems.CAP)
                .criterion(hasItem(ModItems.CAP), conditionsFromItem(ModItems.CAP))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModItems.PROPELLER_CAP)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BUTTERFLY_WINGS)
                .input(ModItems.BUTTERFLY_IN_A_BOTTLE)
                .input(ModItems.BUTTERFLY_IN_A_BOTTLE)
                .input(ModItems.BUTTERFLY_IN_A_BOTTLE)
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(ModItems.BUTTERFLY_WINGS)));

        offerAllCookingRecipes(exporter, ModItems.THORNBLOOM_PEAR, ModItems.BAKED_THORNBLOOM_PEAR, 0.35f, "thornbloom_pear");
        offerAllCookingRecipes(exporter, ModItems.RAW_CHORUS_ECHOFIN, ModItems.COOKED_CHORUS_ECHOFIN, 0.35f, "chorus_echofin");
        offerAllCookingRecipes(exporter, ModItems.RAW_LEVITATING_ECHOFIN, ModItems.COOKED_LEVITATING_ECHOFIN, 0.35f, "levitating_echofin");
        offerAllCookingRecipes(exporter, ModItems.RAW_VENISON, ModItems.COOKED_VENISON, 0.35f, "venison");
        offerAllCookingRecipes(exporter, ModItems.RAW_VENISON_RIBS, ModItems.COOKED_VENISON_RIBS, 0.35f, "venison_ribs");

        offerAllSmeltingRecipes(exporter, ModItems.RAW_SILVER, ModItems.SILVER_INGOT, 1f, "silver");
        offerAllSmeltingRecipes(exporter, ModBlocks.SILVER_ORE.asItem(), ModItems.SILVER_INGOT, 1f, "silver");
        offerAllSmeltingRecipes(exporter, ModBlocks.DEEPSLATE_SILVER_ORE.asItem(), ModItems.SILVER_INGOT, 1f, "silver");

        offerAllSmeltingRecipes(exporter, ModItems.RAW_ONYX, ModItems.ONYX, 1f, "onyx");
        offerAllSmeltingRecipes(exporter, ModBlocks.ONYX_ORE.asItem(), ModItems.ONYX, 1f, "onyx");

        offerAllSmeltingRecipes(exporter, ModBlocks.BASALT_SULPHUR_ORE.asItem(), ModItems.SULPHUR, 1f, "sulphur");
        offerAllSmeltingRecipes(exporter, ModBlocks.BLACKSTONE_SULPHUR_ORE.asItem(), ModItems.SULPHUR, 1f, "sulphur");

        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ONYX_SLAB, ModBlocks.ONYX_BLOCK, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ONYX_STAIRS, ModBlocks.ONYX_BLOCK, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ONYX_WALL, ModBlocks.ONYX_BLOCK, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ONYX, ModBlocks.ONYX_BLOCK, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ONYX_SLAB, ModBlocks.POLISHED_ONYX, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ONYX_STAIRS, ModBlocks.POLISHED_ONYX, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_ONYX_WALL, ModBlocks.POLISHED_ONYX, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLACK_ONYX_SLAB, ModBlocks.BLACK_ONYX_BLOCK, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLACK_ONYX_STAIRS, ModBlocks.BLACK_ONYX_BLOCK, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLACK_ONYX_WALL, ModBlocks.BLACK_ONYX_BLOCK, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLACK_ONYX, ModBlocks.BLACK_ONYX_BLOCK, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLACK_ONYX_SLAB, ModBlocks.POLISHED_BLACK_ONYX, 2);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLACK_ONYX_STAIRS, ModBlocks.POLISHED_BLACK_ONYX, 1);
        offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_BLACK_ONYX_WALL, ModBlocks.POLISHED_BLACK_ONYX, 1);

        for (Item elytra : Registries.ITEM) {
            if (elytra instanceof ClutterElytraItem clutterElytraItem) offerDecoratedElytraRecipes(exporter, elytra, clutterElytraItem.getComponent());
        }
    }

    public void offerAllCookingRecipes(RecipeExporter exporter, Item component, Item result, float experience, String group) {
        List<ItemConvertible> COOKING_LIST = List.of(component);
        offerSmoking(exporter, COOKING_LIST, RecipeCategory.FOOD, result, experience, 100, group);
        offerSmelting(exporter, COOKING_LIST, RecipeCategory.FOOD, result, experience, 200, group);
        offerCampfireCooking(exporter, COOKING_LIST, RecipeCategory.FOOD, result, experience, 600, group);
    }

    public void offerAllSmeltingRecipes(RecipeExporter exporter, Item component, Item result, float experience, String group) {
        List<ItemConvertible> SMELTING_LIST = List.of(component);
        offerBlasting(exporter, SMELTING_LIST, RecipeCategory.MISC, result, experience, 100, group);
        offerSmelting(exporter, SMELTING_LIST, RecipeCategory.MISC, result, experience, 200, group);
    }

    public static void offerCampfireCooking(RecipeExporter exporter, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
        offerMultipleOptions(exporter, RecipeSerializer.CAMPFIRE_COOKING, CampfireCookingRecipe::new, inputs, category, output, experience, cookingTime, group, "_from_campfire_cooking");
    }

    public static void offerSmoking(RecipeExporter exporter, List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience, int cookingTime, String group) {
        offerMultipleOptions(exporter, RecipeSerializer.SMOKING, SmokingRecipe::new, inputs, category, output, experience, cookingTime, group, "_from_smoking");
    }

    public void offerDecoratedElytraRecipes(RecipeExporter exporter, Item result, Item addition) {
        SmithingTransformRecipeJsonBuilder
                .create(Ingredient.ofItems(ModItems.DECORATED_ELYTRA_SMITHING_TEMPLATE), Ingredient.fromTag(ModItemTags.ELYTRON), Ingredient.ofItems(addition), RecipeCategory.MISC, result)
                .criterion("has_elytra", conditionsFromItem(ModItems.BUTTERFLY_IN_A_BOTTLE))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(result)));
    }

    public void offerClutterWaxingRecipes(RecipeExporter exporter) {
        HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().forEach((input, result) -> {
            if (Registries.ITEM.getId(input.asItem()).getNamespace().equals(Clutter.MOD_ID)) {

                ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, result)
                        .input(input).input(Items.HONEYCOMB)
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(result)));

            }
        });
    }

    private void offerCombinationRecipe(RecipeExporter exporter, ItemConvertible input, ItemConvertible input2, ItemConvertible output, int count, RecipeCategory recipeCategory) {
        ShapelessRecipeJsonBuilder.create(recipeCategory, output.asItem(), count)
                .input(input.asItem())
                .input(input2.asItem())
                .criterion(hasItem(input.asItem()), conditionsFromItem(input.asItem()))
                .criterion(hasItem(input2.asItem()), conditionsFromItem(input2.asItem()))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output.asItem()) + "_from_" + getRecipeName(input2.asItem())));
    }

    private void offerArmorRecipe(RecipeExporter exporter, ItemConvertible component, Item helmet, Item chestplate, Item leggings, Item boots) {
        offerHelmetRecipe(exporter, component, helmet);
        offerChestplateRecipe(exporter, component, chestplate);
        offerLeggingsRecipe(exporter, component, leggings);
        offerBootsRecipe(exporter, component, boots);
    }

    private void offerHelmetRecipe(RecipeExporter exporter, ItemConvertible component, Item helmet) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, helmet)
                .pattern("###").pattern("# #").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(helmet)));
    }

    private void offerChestplateRecipe(RecipeExporter exporter, ItemConvertible component, Item chestplate) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, chestplate)
                .pattern("# #").pattern("###").pattern("###").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(chestplate)));
    }

    private void offerLeggingsRecipe(RecipeExporter exporter, ItemConvertible component, Item leggings) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, leggings)
                .pattern("###").pattern("# #").pattern("# #").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(leggings)));
    }

    private void offerBootsRecipe(RecipeExporter exporter, ItemConvertible component, Item boots) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, boots)
                .pattern("# #").pattern("# #").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(boots)));
    }

    private void offerSimpleDyeRecipeWithCount(RecipeExporter exporter, ItemConvertible component, ItemConvertible result, RecipeCategory category, int count) {
        ShapelessRecipeJsonBuilder.create(category, result, count)
                .input(component)
                .criterion(hasItem(component), conditionsFromItem(component))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(result) + "_from_" + getRecipeName(component)));
    }

    private void offerSulphurRecipe(RecipeExporter exporter, ItemConvertible component, ItemConvertible result, RecipeCategory category) {
        ShapelessRecipeJsonBuilder.create(category, result)
                .input(component).input(ModItems.SULPHUR)
                .criterion(hasItem(component), conditionsFromItem(component))
                .criterion(hasItem(ModItems.SULPHUR), conditionsFromItem(ModItems.SULPHUR))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(result)));
    }

    private void offerHatRecipe(RecipeExporter exporter, ItemConvertible component, ItemConvertible secondComponent, Item result) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, result)
                .pattern(" C ").pattern("CSC")
                .input('C', component).input('S', secondComponent)
                .criterion(hasItem(component), conditionsFromItem(component))
                .criterion(hasItem(secondComponent), conditionsFromItem(secondComponent))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(result)));
    }

    private void offerPlusRecipe(RecipeExporter exporter, ItemConvertible inner, ItemConvertible outer, ItemConvertible output, RecipeCategory category) {
        ShapedRecipeJsonBuilder.create(category, output)
                .pattern(" O ")
                .pattern("OIO")
                .pattern(" O ")
                .input('I', inner)
                .input('O', outer)
                .criterion(hasItem(inner), conditionsFromItem(inner))
                .criterion(hasItem(outer), conditionsFromItem(outer))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerWoodRecipes(RecipeExporter exporter, ClutterWoodType woodType) {
        RegistryType registryType = woodType.registryType();

        if(registryType.isOf(RegistryType.MODDED)) offerBarkBlockRecipe(exporter, woodType.strippedWood(), woodType.strippedLog());
        if(registryType.isOf(RegistryType.MODDED)) offerBarkBlockRecipe(exporter, woodType.wood(), woodType.log());
        if(registryType.isOf(RegistryType.MODDED)) offerPlanksRecipe(exporter, woodType.planks(), woodType.logTag(), 4);
        if(registryType.isOf(RegistryType.MODDED)) offerStairsRecipe(exporter, woodType.planks(), woodType.stairs());
        if(registryType.isOf(RegistryType.MODDED)) offerSlabRecipe(exporter, woodType.slab(), woodType.planks());
        if(registryType.isOf(RegistryType.MODDED)) offerPressurePlateRecipe(exporter, woodType.pressurePlate(), woodType.planks());
        if(registryType.isOf(RegistryType.MODDED)) offerTrapdoorRecipe(exporter, woodType.trapdoor(), woodType.planks());
        if(registryType.isOf(RegistryType.MODDED)) offerSingleItemRecipe(exporter, woodType.button(), woodType.planks(), RecipeCategory.REDSTONE);
        if(registryType.isOf(RegistryType.MODDED)) offerFenceRecipe(exporter, woodType.fence(), woodType.planks());
        if(registryType.isOf(RegistryType.MODDED)) offerFenceGateRecipe(exporter, woodType.fenceGate(), woodType.planks());
        if(registryType.isOf(RegistryType.MODDED)) offerDoorRecipe(exporter, woodType.door(), woodType.planks());
        if(registryType.isOf(RegistryType.MODDED)) offerSignRecipe(exporter, woodType.signItem(), woodType.planks());
        if(registryType.isOf(RegistryType.MODDED)) offerHangingSignRecipe(exporter, woodType.hangingSignItem(), woodType.strippedLog());

        if(woodType.mosaic() != null) offerMosaicRecipe(exporter, RecipeCategory.DECORATIONS, woodType.mosaic(), woodType.slab());
        if(woodType.mosaicSlab() != null) offerSlabRecipe(exporter, woodType.mosaicSlab(), woodType.mosaic());
        if(woodType.mosaicStairs() != null) offerStairsRecipe(exporter, woodType.mosaic(), woodType.mosaicStairs());

        offerShortBenchRecipe(exporter, woodType.planks(), woodType.log(), woodType.shortBench());
        offerChairRecipe(exporter, woodType.planks(), woodType.log(), woodType.chair());
        if(woodType.strippedChair() != null) offerChairRecipe(exporter, woodType.planks(), woodType.strippedLog(), woodType.strippedChair());
        offerBenchRecipe(exporter, woodType.planks(), woodType.log(), woodType.bench());
        if(woodType.strippedBench() != null) offerBenchRecipe(exporter, woodType.planks(), woodType.strippedLog(), woodType.strippedBench());
        offerTableRecipe(exporter, woodType.planks(), woodType.log(), woodType.table());
        if(woodType.strippedTable() != null) offerTableRecipe(exporter, woodType.planks(), woodType.strippedLog(), woodType.strippedTable());
        offerCupboardRecipe(exporter, woodType.planks(), woodType.slab(), woodType.cupboard());
        offerWallCupboardRecipe(exporter, woodType.planks(), woodType.slab(), woodType.wallCupboard());
        offerWallBookshelfRecipe(exporter, woodType.slab(), woodType.wallBookshelf());
        offerWindowSillRecipe(exporter, woodType.slab(), woodType.windowSill());
        offerShelfRecipe(exporter, woodType.slab(), woodType.shelf());
        offerTrellisRecipe(exporter, woodType.slab(), woodType.trellis());
    }

    private static void offerStairsRecipe(RecipeExporter exporter, ItemConvertible input, ItemConvertible output) {
        createStairsRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerPressurePlateRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createPressurePlateRecipe(RecipeCategory.REDSTONE, output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerSlabRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerTrapdoorRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createTrapdoorRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerSingleItemRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input, RecipeCategory category) {
        ShapelessRecipeJsonBuilder.create(category, output).input(input).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerFenceRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createFenceRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerFenceGateRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createFenceGateRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerDoorRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createDoorRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    public static void offerSignRecipe(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        createSignRecipe(output, Ingredient.ofItems(input)).criterion(hasItem(input), conditionsFromItem(input)).offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerShortBenchRecipe(RecipeExporter exporter, ItemConvertible planks, ItemConvertible log, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 6)
                .pattern("PPP")
                .pattern("L L")
                .input('P', planks)
                .input('L', log)
                .group("short_benches")
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .criterion(hasItem(log), conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerChairRecipe(RecipeExporter exporter, ItemConvertible planks, ItemConvertible log, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("LL ")
                .input('P', planks)
                .input('L', log)
                .group("chairs")
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .criterion(hasItem(log), conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerBenchRecipe(RecipeExporter exporter, ItemConvertible planks, ItemConvertible log, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 4)
                .pattern("PSP")
                .pattern("PPP")
                .pattern("L L")
                .input('S', Items.STICK)
                .input('P', planks)
                .input('L', log)
                .group("benches")
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .criterion(hasItem(log), conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerTableRecipe(RecipeExporter exporter, ItemConvertible planks, ItemConvertible log, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output)
                .pattern("PPP")
                .pattern("L L")
                .pattern("L L")
                .input('P', planks)
                .input('L', log)
                .group("tables")
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .criterion(hasItem(log), conditionsFromItem(log))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerShelfRecipe(RecipeExporter exporter, ItemConvertible slab, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 2)
                .pattern("SSS")
                .input('S', slab)
                .group("shelves")
                .criterion(hasItem(slab), conditionsFromItem(slab))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerTrellisRecipe(RecipeExporter exporter, ItemConvertible slab, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 2)
                .pattern(" S ")
                .pattern("SSS")
                .pattern(" S ")
                .input('S', slab)
                .group("trellises")
                .criterion(hasItem(slab), conditionsFromItem(slab))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerCupboardRecipe(RecipeExporter exporter, ItemConvertible planks, ItemConvertible slab, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output)
                .pattern("PSP")
                .pattern("PSP")
                .pattern("PSP")
                .input('S', slab)
                .input('P', planks)
                .group("cupboards")
                .criterion(hasItem(slab), conditionsFromItem(slab))
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerWallCupboardRecipe(RecipeExporter exporter, ItemConvertible planks, ItemConvertible slab, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 2)
                .pattern("PS ")
                .pattern("PS ")
                .pattern("PS ")
                .input('S', slab)
                .input('P', planks)
                .group("wall_cupboards")
                .criterion(hasItem(slab), conditionsFromItem(slab))
                .criterion(hasItem(planks), conditionsFromItem(planks))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerWindowSillRecipe(RecipeExporter exporter, ItemConvertible slab, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output)
                .pattern("P  ")
                .pattern("SSS")
                .input('S', slab)
                .input('P', Items.FLOWER_POT)
                .group("window_sills")
                .criterion(hasItem(slab), conditionsFromItem(slab))
                .criterion(hasItem(Items.FLOWER_POT), conditionsFromItem(Items.FLOWER_POT))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }

    private void offerWallBookshelfRecipe(RecipeExporter exporter, ItemConvertible slab, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output)
                .pattern("BBB")
                .pattern("SSS")
                .input('B', Items.BOOK)
                .input('S', slab)
                .group("wall_bookshelves")
                .criterion(hasItem(slab), conditionsFromItem(slab))
                .criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK))
                .offerTo(exporter, Identifier.of(Clutter.MOD_ID, getRecipeName(output)));
    }
}
