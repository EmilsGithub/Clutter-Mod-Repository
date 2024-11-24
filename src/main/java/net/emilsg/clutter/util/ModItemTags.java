package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> FRUITS_AND_BERRIES = create("fruits_and_berries");
    public static final TagKey<Item> TRELLIS_ITEMS = create("trellis_items");
    public static final TagKey<Item> ELYTRON = create("elytron");
    public static final TagKey<Item> SEEDS = create("seeds");
    public static final TagKey<Item> DYES = create("dyes");
    public static final TagKey<Item> STRIPPABLE_LOGS = create("strippable_logs");
    public static final TagKey<Item> REDWOOD_LOGS = create("redwood_logs");

    public static final TagKey<Item> PLUSHIES = create("plushies");

    public static final TagKey<Item> C_COPPER_NUGGETS = create("copper_nuggets", "c");
    public static final TagKey<Item> C_ORES = create("ores", "c");
    public static final TagKey<Item> C_RAW_SILVER_BLOCKS = create("raw_silver_blocks", "c");
    public static final TagKey<Item> C_RAW_SILVER_ORES = create("raw_silver_ores", "c");
    public static final TagKey<Item> C_RAW_SILVERS = create("raw_silvers", "c");
    public static final TagKey<Item> C_SILVER_BLOCKS = create("silver_blocks", "c");
    public static final TagKey<Item> C_SILVER_INGOTS = create("silver_ingots", "c");
    public static final TagKey<Item> C_SILVER_NUGGETS = create("silver_nuggets", "c");
    public static final TagKey<Item> C_ENTITY_WATER_BUCKETS = create("entity_water_buckets", "c");

    public static final TagKey<Item> TRINKETS_HAT = create("head/hat", "trinkets");
    public static final TagKey<Item> TRINKETS_CAPE = create("chest/cape", "trinkets");

    private static TagKey<Item> create(String path) {
        return create(path, Clutter.MOD_ID);
    }

    private static TagKey<Item> create(String path, String namespace) {
        return TagKey.of(Registries.ITEM.getKey(), Identifier.of(namespace, path));
    }
}
