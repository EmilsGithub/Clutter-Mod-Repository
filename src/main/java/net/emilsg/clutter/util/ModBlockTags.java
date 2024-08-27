package net.emilsg.clutter.util;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {

    public static final TagKey<Block> FLAMMABLE = create("clutter", "flammable");

    public static final TagKey<Block> ARMCHAIRS = create("clutter", "armchairs");
    public static final TagKey<Block> CANDELABRAS = create("clutter", "candelabras");
    public static final TagKey<Block> CHANDELIERS = create("clutter", "chandeliers");
    public static final TagKey<Block> CHIMNEYS = create("clutter", "chimneys");
    public static final TagKey<Block> LARGE_CHANDELIERS = create("clutter", "large_chandeliers");
    public static final TagKey<Block> CHAINS = create("clutter", "chains");
    public static final TagKey<Block> LARGE_CHAINS = create("clutter", "large_chains");
    public static final TagKey<Block> WOODEN_MOSAICS = create("clutter", "wooden_mosaics");
    public static final TagKey<Block> CUPBOARDS = create("clutter", "cupboards");
    public static final TagKey<Block> LAMPS = create("clutter", "lamps");
    public static final TagKey<Block> LANTERNS = create("clutter", "lanterns");
    public static final TagKey<Block> TABLES = create("clutter", "tables");
    public static final TagKey<Block> WALL_CANDLES = create("clutter", "wall_candles");
    public static final TagKey<Block> FOOD_BOXES = create("clutter", "food_boxes");
    public static final TagKey<Block> WINDOW_SILLS = create("clutter", "window_sills");
    public static final TagKey<Block> WOODEN_CHAIRS = create("clutter", "wooden_chairs");
    public static final TagKey<Block> PACKED_MUD = create("clutter", "packed_mud");

    public static final TagKey<Block> C_BOOKSHELVES = create("c", "bookshelves");
    public static final TagKey<Block> C_ORES = create("c", "ores");
    public static final TagKey<Block> C_RAW_SILVER_BLOCKS = create("c", "raw_silver_blocks");

    public static final TagKey<Block> TRELLISES = create("clutter", "trellises");
    public static final TagKey<Block> SHELVES = create("clutter", "shelves");
    public static final TagKey<Block> BOOKSHELVES = create("clutter", "bookshelves");
    public static final TagKey<Block> STRIPPABLE_CHAIRS = create("clutter", "strippable_chairs");
    public static final TagKey<Block> STRIPPABLE_BENCHES = create("clutter", "strippable_benches");
    public static final TagKey<Block> BENCHES = create("clutter", "benches");
    public static final TagKey<Block> STRIPPABLE_TABLES = create("clutter", "strippable_tables");
    public static final TagKey<Block> IMMOVABLE = create("clutter", "immovable");
    public static final TagKey<Block> COPPER_DOORS = create("clutter", "copper_doors");
    public static final TagKey<Block> GREEN_FIRE_BASE_BLOCKS = create("clutter", "green_fire_base_blocks");
    public static final TagKey<Block> KIWI_EGG_HATCH_BOOST = create("clutter", "kiwi_egg_hatch_boost");
    public static final TagKey<Block> EMPEROR_PENGUIN_EGG_HATCH_BOOST = create("clutter", "emperor_penguin_egg_hatch_boost");

    public static final TagKey<Block> CRIMSON_NEWTS_SPAWN_ON = create("clutter", "crimson_newts_spawn_on");
    public static final TagKey<Block> WARPED_NEWTS_SPAWN_ON = create("clutter", "warped_newts_spawn_on");
    public static final TagKey<Block> EMBER_TORTOISES_SPAWN_ON = create("clutter", "ember_tortoises_spawn_on");
    public static final TagKey<Block> BUTTERFLIES_SPAWN_ON = create("clutter", "butterflies_spawn_on");
    public static final TagKey<Block> ECHOFINS_SPAWN_ON = create("clutter", "echofins_spawn_on");


    private static TagKey<Block> create(String namespace, String path) {
        return TagKey.of(Registries.BLOCK.getKey(), new Identifier(namespace, path));
    }
}
