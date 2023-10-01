package net.emilsg.clutter.recipe;

import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;

public class BrickKilnRecipe extends AbstractCookingRecipe {

    public BrickKilnRecipe(Identifier id, String group, CookingRecipeCategory category, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(ModRecipeTypes.BRICK_KILN_RECIPE_TYPE, id, group, category, input, output, experience, cookTime);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.BRICK_KILN);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BRICK_KILN_RECIPE_SERIALIZER;
    }
}
