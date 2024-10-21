package net.emilsg.clutter.effect;

import net.emilsg.clutter.Clutter;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static RegistryEntry<StatusEffect> VULNERABILITY = registerStatusEffect("vulnerability", new Vulnerability(StatusEffectCategory.HARMFUL, 6677153));
    public static RegistryEntry<StatusEffect> MELTDOWN = registerStatusEffect("meltdown", new Meltdown(StatusEffectCategory.HARMFUL, 16737330));
    public static RegistryEntry<StatusEffect> SHIMMER = registerStatusEffect("shimmer", new Shimmer(StatusEffectCategory.BENEFICIAL, 2228216));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Clutter.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {

    }
}
