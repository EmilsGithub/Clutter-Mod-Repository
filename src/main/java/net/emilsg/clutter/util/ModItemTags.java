package net.emilsg.clutter.util;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItemTags {
    public static final TagKey<Item> FRUITS_AND_BERRIES = TagKey.of(Registry.ITEM.getKey(), new Identifier("clutter", "fruits_and_berries"));
    public static final TagKey<Item> TRELLIS_ITEMS = TagKey.of(Registry.ITEM.getKey(), new Identifier("clutter", "trellis_items"));

    public static final TagKey<Item> MEATS = TagKey.of(Registry.ITEM.getKey(), new Identifier("clutter", "meats"));
    public static final TagKey<Item> VEGGIES = TagKey.of(Registry.ITEM.getKey(), new Identifier("clutter", "veggies"));
    public static final TagKey<Item> CARBS = TagKey.of(Registry.ITEM.getKey(), new Identifier("clutter", "carbs"));
    public static final TagKey<Item> PLATE_PLACEABLE = TagKey.of(Registry.ITEM.getKey(), new Identifier("clutter", "plate_placeable"));
}
