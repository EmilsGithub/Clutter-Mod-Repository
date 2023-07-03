package net.emilsg.clutter.util;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> FRUITS_AND_BERRIES = create("fruits_and_berries");
    public static final TagKey<Item> TRELLIS_ITEMS = create("trellis_items");
    public static final TagKey<Item> MEATS = create("meats");
    public static final TagKey<Item> VEGGIES = create("veggies");
    public static final TagKey<Item> CARBS = create("carbs");
    public static final TagKey<Item> PLATE_PLACEABLE = create("plate_placeable");
    public static final TagKey<Item> ELYTRON = create("elytron");
    public static final TagKey<Item> SEEDS = create("seeds");

    private static TagKey<Item> create(String path) {
        return create(path, "clutter");
    }

    private static TagKey<Item> create(String path, String namespace) {
        return TagKey.of(Registries.ITEM.getKey(), new Identifier(namespace, path));
    }
}
