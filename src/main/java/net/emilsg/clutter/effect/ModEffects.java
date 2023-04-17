package net.emilsg.clutter.effect;

import net.emilsg.clutter.Clutter;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static StatusEffect VULNERABILITY;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Clutter.MOD_ID, name),
                new Vulnerability(StatusEffectCategory.HARMFUL, 6677153));
    }

    public static void registerEffects() {
        VULNERABILITY = registerStatusEffect("vulnerability");
    }
}
