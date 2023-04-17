package net.emilsg.clutter.potion;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModPotions {
    public static Potion VULNERABILITY_POTION = registerPotion("vulnerability_potion", new StatusEffectInstance(ModEffects.VULNERABILITY, 900, 0));
    public static Potion LONG_VULNERABILITY_POTION = registerPotion("long_vulnerability_potion", new StatusEffectInstance(ModEffects.VULNERABILITY, 1800, 0));
    public static Potion STRONG_VULNERABILITY_POTION = registerPotion("strong_vulnerability_potion", new StatusEffectInstance(ModEffects.VULNERABILITY, 450, 1));

    public static Potion registerPotion(String name, StatusEffectInstance effectInstance) {
        return Registry.register(Registry.POTION, new Identifier(Clutter.MOD_ID, name), new Potion(effectInstance));
    }

    public static void registerPotionRecipes() {
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.AWKWARD, ModItems.BEER_MUG, ModPotions.VULNERABILITY_POTION);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(ModPotions.VULNERABILITY_POTION, Items.REDSTONE, ModPotions.LONG_VULNERABILITY_POTION);
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(ModPotions.VULNERABILITY_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_VULNERABILITY_POTION);
    }
}

