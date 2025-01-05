package net.emilsg.clutter.item;

import net.emilsg.clutter.Clutter;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public interface ModEquipmentAssetKeys {
    RegistryKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.of(Clutter.MOD_ID, "equipment_asset"));

    RegistryKey<EquipmentAsset> SILVER = register("silver");

    static RegistryKey<EquipmentAsset> register(String name) {
        return RegistryKey.of(REGISTRY_KEY, Identifier.of(Clutter.MOD_ID, name));
    }
}
