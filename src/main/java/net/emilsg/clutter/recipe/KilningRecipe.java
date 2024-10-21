package net.emilsg.clutter.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CookingRecipeCategory;

public class KilningRecipe extends AbstractCookingRecipe  {

    public KilningRecipe(String group, CookingRecipeCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(Type.INSTANCE, group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Type implements RecipeType<KilningRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer {
        public static final RecipeSerializer<KilningRecipe> INSTANCE = new CookingRecipeSerializer<>(KilningRecipe::new, 50);
    }
}