package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModDamageSources {

    public static final RegistryKey<DamageType> ANCHOR = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Clutter.MOD_ID, "anchor"));

    private static final Map<RegistryKey<DamageType>, DamageSource> sourceMap = new HashMap<>();

    public static DamageSource getDamageSource(DamageSources damageSources, RegistryKey<DamageType> damageType) {
        return sourceMap.computeIfAbsent(damageType, damageSources::create);
    }
}
