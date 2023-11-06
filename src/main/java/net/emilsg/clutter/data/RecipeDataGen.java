package net.emilsg.clutter.data;

import net.emilsg.clutter.recipe.KilningRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;

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
    }

    private void kilningRecipe(ItemConvertible ingredient, ItemConvertible result, int count, Consumer<RecipeJsonProvider> exporter) {
        new KilningRecipeBuilder(ingredient, result, count).criterion(hasItem(ingredient), conditionsFromItem(ingredient)).offerTo(exporter);
    }
}
