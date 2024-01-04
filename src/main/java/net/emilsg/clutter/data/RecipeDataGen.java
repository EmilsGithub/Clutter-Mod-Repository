package net.emilsg.clutter.data;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.recipe.KilningRecipeBuilder;
import net.emilsg.clutter.util.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class RecipeDataGen extends FabricRecipeProvider {
    public RecipeDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        kilningRecipe(Items.SAND, Items.GLASS, 1, exporter);
        kilningRecipe(Items.RED_SAND, Items.GLASS, 1, exporter);
        kilningRecipe(Items.CLAY_BALL, Items.BRICK, 1, exporter);
        kilningRecipe(Items.CLAY, Items.TERRACOTTA, 1, exporter);
        kilningRecipe(Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT, 1, exporter);
        kilningRecipe(Items.NETHERRACK, Items.NETHER_BRICK, 1, exporter);
        kilningRecipe(Items.COBBLESTONE, Items.STONE, 1, exporter);
        kilningRecipe(Items.STONE, Items.SMOOTH_STONE, 1, exporter);
        kilningRecipe(Items.COBBLED_DEEPSLATE, Items.DEEPSLATE, 1, exporter);
        kilningRecipe(Items.QUARTZ_BLOCK, Items.SMOOTH_QUARTZ, 1, exporter);
        kilningRecipe(Items.BASALT, Items.SMOOTH_BASALT, 1, exporter);
        kilningRecipe(Items.SANDSTONE, Items.SMOOTH_SANDSTONE, 1, exporter);
        kilningRecipe(Items.RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE, 1, exporter);
        kilningRecipe(Items.WHITE_TERRACOTTA, Items.WHITE_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.GRAY_TERRACOTTA, Items.GRAY_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.BLACK_TERRACOTTA, Items.BLACK_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.BROWN_TERRACOTTA, Items.BROWN_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.RED_TERRACOTTA, Items.RED_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.ORANGE_TERRACOTTA, Items.ORANGE_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.YELLOW_TERRACOTTA, Items.YELLOW_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.LIME_TERRACOTTA, Items.LIME_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.GREEN_TERRACOTTA, Items.GREEN_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.CYAN_TERRACOTTA, Items.CYAN_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.BLUE_TERRACOTTA, Items.BLUE_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.PURPLE_TERRACOTTA, Items.PURPLE_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.MAGENTA_TERRACOTTA, Items.MAGENTA_GLAZED_TERRACOTTA, 1, exporter);
        kilningRecipe(Items.PINK_TERRACOTTA, Items.PINK_GLAZED_TERRACOTTA, 1, exporter);

        offerClutterWaxingRecipes(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.AQUATIC_TORCH)
                .pattern(" N ")
                .pattern("NCN")
                .pattern(" I ")
                .input('N', ModItemTags.C_COPPER_NUGGETS)
                .input('C', ItemTags.COALS)
                .input('I', Items.COPPER_INGOT)
                .criterion(hasItem(Items.COPPER_INGOT), conditionsFromItem(Items.COPPER_INGOT))
                .criterion(hasItem(ModItems.COPPER_NUGGET), conditionsFromItem(ModItems.COPPER_NUGGET))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModItems.AQUATIC_TORCH)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.PRISMARINE_TORCH)
                .pattern(" P ")
                .pattern(" C ")
                .pattern(" P ")
                .input('P', Items.PRISMARINE_SHARD)
                .input('C', Items.PRISMARINE_CRYSTALS)
                .criterion(hasItem(Items.PRISMARINE_SHARD), conditionsFromItem(Items.PRISMARINE_SHARD))
                .criterion(hasItem(Items.PRISMARINE_CRYSTALS), conditionsFromItem(Items.PRISMARINE_CRYSTALS))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModItems.PRISMARINE_TORCH)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TALL_BOTTLE)
                .pattern(" G ")
                .pattern(" G ")
                .pattern("GDG")
                .input('G', Items.GLASS)
                .input('D', Items.GREEN_DYE)
                .criterion(hasItem(Items.GREEN_DYE), conditionsFromItem(Items.GREEN_DYE))
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModBlocks.TALL_BOTTLE)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WINE_GLASS)
                .pattern("G G")
                .pattern(" G ")
                .pattern(" G ")
                .input('G', Items.GLASS)
                .criterion(hasItem(Items.GLASS), conditionsFromItem(Items.GLASS))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModBlocks.WINE_GLASS)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.BRICK_KILN)
                .pattern("SSS")
                .pattern("BFB")
                .pattern("BBB")
                .input('S', Items.SMOOTH_STONE)
                .input('B', Items.BRICKS)
                .input('F', Items.FURNACE)
                .criterion(hasItem(Items.FURNACE), conditionsFromItem(Items.FURNACE))
                .criterion(hasItem(Items.BRICKS), conditionsFromItem(Items.BRICKS))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModBlocks.BRICK_KILN)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.CARDBOARD_BOX)
                .pattern(" P ")
                .pattern("PCP")
                .pattern(" P ")
                .input('P', Items.PAPER)
                .input('C', Items.CHEST)
                .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                .criterion(hasItem(Items.CHEST), conditionsFromItem(Items.CHEST))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModBlocks.CARDBOARD_BOX)));

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
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModBlocks.RED_PRESENT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAILBOX)
                .pattern(" I ")
                .pattern("ICI")
                .pattern(" R ")
                .input('I', Items.IRON_INGOT)
                .input('C', Items.CHEST)
                .input('R', Items.REDSTONE)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModBlocks.MAILBOX)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PET_BED)
                .pattern("WWW")
                .input('W', ItemTags.WOOL)
                .criterion(hasItem(Items.WHITE_WOOL), conditionsFromTag(ItemTags.WOOL))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModBlocks.PET_BED)));

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
        offerHatRecipe(exporter, Items.EMERALD, Items.GOLD_INGOT, ModItems.CROWN);
        offerHatRecipe(exporter, Items.BONE, Items.IRON_INGOT, ModItems.VIKING_HELMET);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PROPELLER_CAP)
                .input(Ingredient.fromTag(ModItemTags.DYES))
                .input(Ingredient.fromTag(ModItemTags.DYES))
                .input(Ingredient.fromTag(ModItemTags.DYES))
                .input(ModItems.CAP)
                .criterion(hasItem(ModItems.CAP), conditionsFromItem(ModItems.CAP))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModItems.PROPELLER_CAP)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BUTTERFLY_WINGS)
                .input(Ingredient.fromTag(ModItemTags.BOTTLED_BUTTERFLIES))
                .input(Ingredient.fromTag(ModItemTags.BOTTLED_BUTTERFLIES))
                .input(Ingredient.fromTag(ModItemTags.BOTTLED_BUTTERFLIES))
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(ModItems.BUTTERFLY_WINGS)));

    }


    private void kilningRecipe(ItemConvertible ingredient, ItemConvertible result, int count, Consumer<RecipeJsonProvider> exporter) {
        new KilningRecipeBuilder(ingredient, result, count).offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(result)));
    }

    private void offerArmorRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible component, Item helmet, Item chestplate, Item leggings, Item boots) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, helmet)
                .pattern("###").pattern("# #").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(helmet)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, chestplate)
                .pattern("# #").pattern("###").pattern("###").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(chestplate)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, leggings)
                .pattern("###").pattern("# #").pattern("# #").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(leggings)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, boots)
                .pattern("# #").pattern("# #").input('#', component)
                .criterion(hasItem(component), conditionsFromItem(component)).offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(boots)));
    }

    private void offerSulphurRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible component, ItemConvertible result, RecipeCategory category) {
        ShapelessRecipeJsonBuilder.create(category, result)
                .input(component).input(ModItems.SULPHUR)
                .criterion(hasItem(component), conditionsFromItem(component))
                .criterion(hasItem(ModItems.SULPHUR), conditionsFromItem(ModItems.SULPHUR))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(result)));
    }

    private void offerHatRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible component, ItemConvertible secondComponent, Item result) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, result)
                .pattern(" C ").pattern("CSC")
                .input('C', component).input('S', secondComponent)
                .criterion(hasItem(component), conditionsFromItem(component))
                .criterion(hasItem(secondComponent), conditionsFromItem(secondComponent))
                .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(result)));
    }

    public static void offerClutterWaxingRecipes(Consumer<RecipeJsonProvider> exporter) {
        HoneycombItem.UNWAXED_TO_WAXED_BLOCKS.get().forEach((input, result) -> {
            if (Registries.ITEM.getId(input.asItem()).getNamespace().equals(Clutter.MOD_ID)) {

                ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, result)
                        .input(input).input(Items.HONEYCOMB)
                        .criterion(hasItem(input), conditionsFromItem(input))
                        .offerTo(exporter, new Identifier(Clutter.MOD_ID, getRecipeName(result)));

            }
        });
    }

    private InventoryChangedCriterion.Conditions conditionsFromIngredient(Ingredient ingredient) {
        if (ingredient.getMatchingStacks().length > 0) {
            return InventoryChangedCriterion.Conditions.items(ingredient.getMatchingStacks()[0].getItem());
        }
        return InventoryChangedCriterion.Conditions.items(Items.AIR);
    }
}
