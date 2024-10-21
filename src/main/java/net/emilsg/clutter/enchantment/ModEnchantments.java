package net.emilsg.clutter.enchantment;

import net.emilsg.clutter.Clutter;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;


public class ModEnchantments {

    public static final RegistryKey<Enchantment> GREED = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Clutter.MOD_ID, "greed"));
    public static final RegistryKey<Enchantment> NECROSIS = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Clutter.MOD_ID, "necrosis"));

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);


        register(registerable, GREED, Enchantment.builder(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE), items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        2, 3, Enchantment.leveledCost(5, 8), Enchantment.leveledCost(25, 7),
                        2, AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK, EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM, new GreedEnchantmentEffect(1)));

        register(registerable, NECROSIS, Enchantment.builder(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE), items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4, 4, Enchantment.leveledCost(5, 8), Enchantment.leveledCost(25, 7),
                        2, AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK, EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM, new NecrosisEnchantmentEffect(1)));
    }

    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }

    public static void registerModEnchantments() {

    }

}
