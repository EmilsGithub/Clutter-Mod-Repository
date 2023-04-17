package net.emilsg.clutter.effect;

import net.emilsg.clutter.Clutter;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static StatusEffect VULNERABILITY;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Clutter.MOD_ID, name),
                new Vulnerability(StatusEffectCategory.HARMFUL, 6677153));
    }

    public static void registerEffects() {
        VULNERABILITY = registerStatusEffect("vulnerability");
    }
}
