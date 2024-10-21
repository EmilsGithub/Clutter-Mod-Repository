package net.emilsg.clutter.enchantment;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.Clutter;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final MapCodec<? extends EnchantmentEntityEffect> GREED = registerEntityEffect("greed", GreedEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> NECROSIS = registerEntityEffect("necrosis", NecrosisEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Clutter.MOD_ID, name), codec);
    }

    public static void registerEnchantmentEffects() {

    }
}
