package net.emilsg.clutter.potion;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static RegistryEntry<Potion> VULNERABILITY_POTION = registerPotion("vulnerability_potion", new Potion(new StatusEffectInstance(ModEffects.VULNERABILITY, 900, 0)));
    public static RegistryEntry<Potion> LONG_VULNERABILITY_POTION = registerPotion("long_vulnerability_potion", new Potion(new StatusEffectInstance(ModEffects.VULNERABILITY, 1800, 0)));
    public static RegistryEntry<Potion> STRONG_VULNERABILITY_POTION = registerPotion("strong_vulnerability_potion", new Potion(new StatusEffectInstance(ModEffects.VULNERABILITY, 450, 1)));

    public static RegistryEntry<Potion> MELTDOWN_POTION = registerPotion("meltdown_potion", new Potion(new StatusEffectInstance(ModEffects.MELTDOWN, 600, 0)));
    public static RegistryEntry<Potion> STRONG_MELTDOWN_POTION = registerPotion("strong_meltdown_potion", new Potion(new StatusEffectInstance(ModEffects.MELTDOWN, 600, 1)));

    public static RegistryEntry<Potion> SHIMMER_POTION = registerPotion("shimmer_potion", new Potion(new StatusEffectInstance(ModEffects.SHIMMER, 450, 0)));
    public static RegistryEntry<Potion> LONG_SHIMMER_POTION = registerPotion("long_shimmer_potion", new Potion(new StatusEffectInstance(ModEffects.SHIMMER, 900, 0)));

    private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(Clutter.MOD_ID, name), potion);
    }

    public static void registerPotions() {
    }

    public static void registerPotionRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.AWKWARD, ModItems.HOPS, ModPotions.VULNERABILITY_POTION);
            builder.registerPotionRecipe(ModPotions.VULNERABILITY_POTION, Items.REDSTONE, ModPotions.LONG_VULNERABILITY_POTION);
            builder.registerPotionRecipe(ModPotions.VULNERABILITY_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_VULNERABILITY_POTION);

            builder.registerPotionRecipe(Potions.THICK, ModItems.SULPHUR, ModPotions.MELTDOWN_POTION);
            builder.registerPotionRecipe(ModPotions.MELTDOWN_POTION, Items.GLOWSTONE_DUST, ModPotions.STRONG_MELTDOWN_POTION);

            builder.registerPotionRecipe(Potions.MUNDANE, ModItems.PEARL, ModPotions.SHIMMER_POTION);
            builder.registerPotionRecipe(ModPotions.SHIMMER_POTION, Items.REDSTONE, ModPotions.LONG_SHIMMER_POTION);
        });
    }
}

