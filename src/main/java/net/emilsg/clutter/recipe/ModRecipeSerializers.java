package net.emilsg.clutter.recipe;

import net.emilsg.clutter.Clutter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializers {

    public static void registerRecipeSerializers() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Clutter.MOD_ID, KilningRecipe.Serializer.ID), KilningRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Clutter.MOD_ID, KilningRecipe.Type.ID), KilningRecipe.Type.INSTANCE);
    }
}
