package net.emilsg.clutter.util;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> FRUITS_AND_BERRIES = TagKey.of(Registries.ITEM.getKey(), new Identifier("clutter", "fruits_and_berries"));
    public static final TagKey<Item> TRELLIS_ITEMS = TagKey.of(Registries.ITEM.getKey(), new Identifier("clutter", "trellis_items"));
}
