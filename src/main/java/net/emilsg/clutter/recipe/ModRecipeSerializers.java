package net.emilsg.clutter.recipe;

import net.emilsg.clutter.Clutter;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializers {
    public static final RecipeSerializer<BrickKilnRecipe> BRICK_KILN_RECIPE_SERIALIZER = Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Clutter.MOD_ID, "kilning"), new CookingRecipeSerializer<>(BrickKilnRecipe::new, 100));

    public static void registerRecipeSerializers() {

    }
}
