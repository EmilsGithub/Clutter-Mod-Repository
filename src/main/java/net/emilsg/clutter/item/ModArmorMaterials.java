package net.emilsg.clutter.item;

import net.emilsg.clutter.util.ModItemTags;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.Map;

public interface ModArmorMaterials {

    ArmorMaterial SILVER = new ArmorMaterial(14, (Map)Util.make(new EnumMap(EquipmentType.class), (map) -> {
        map.put(EquipmentType.BOOTS, 2);
        map.put(EquipmentType.LEGGINGS, 5);
        map.put(EquipmentType.CHESTPLATE, 6);
        map.put(EquipmentType.HELMET, 2);
        map.put(EquipmentType.BODY, 4);
    }), 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F, ModItemTags.REPAIRS_SILVER_ARMOR, ModEquipmentAssetKeys.SILVER);
}