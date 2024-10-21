package net.emilsg.clutter.recipe;

import net.emilsg.clutter.Clutter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(Clutter.MOD_ID, "kilning"),
                KilningRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Identifier.of(Clutter.MOD_ID, "kilning"),
                KilningRecipe.Type.INSTANCE);
    }
}
