package net.emilsg.clutter.util;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {

    public static final TagKey<Block> FLAMMABLE = create("clutter","flammable");

    public static final TagKey<Block> ARMCHAIRS = create("clutter","armchairs");
    public static final TagKey<Block> BUTTERFLY_VALID = create("clutter","butterfly_valid");
    public static final TagKey<Block> CANDELABRAS = create("clutter","candelabras");
    public static final TagKey<Block> CHANDELIERS = create("clutter","chandeliers");
    public static final TagKey<Block> CHIMNEYS = create("clutter","chimneys");
    public static final TagKey<Block> COPPER_TRAPDOORS = create("clutter","copper_trapdoors");
    public static final TagKey<Block> WOODEN_MOSAICS = create("clutter","wooden_mosaics");
    public static final TagKey<Block> CUPBOARDS = create("clutter","cupboards");
    public static final TagKey<Block> LAMPS = create("clutter","lamps");
    public static final TagKey<Block> TABLES = create("clutter","tables");
    public static final TagKey<Block> FOOD_BOXES = create("clutter","food_boxes");
    public static final TagKey<Block> WINDOW_SILLS = create("clutter","window_sills");
    public static final TagKey<Block> WOODEN_CHAIRS = create("clutter","wooden_chairs");

    public static final TagKey<Block> C_BOOKSHELVES = create("c","bookshelves");
    public static final TagKey<Block> C_ORES = create("c","ores");
    public static final TagKey<Block> C_RAW_SILVER_BLOCKS = create("c","raw_silver_blocks");

    public static final TagKey<Block> TRELLISES = create("clutter","trellises");
    public static final TagKey<Block> STRIPPABLE_CHAIRS = create("clutter","strippable_chairs");
    public static final TagKey<Block> STRIPPABLE_BENCHES = create("clutter","strippable_benches");
    public static final TagKey<Block> BENCHES = create("clutter","strippable_benches");
    public static final TagKey<Block> FULL_SCULK_BLOCKS = create("clutter","full_sculk_blocks");
    public static final TagKey<Block> STRIPPABLE_TABLES = create("clutter","strippable_tables");
    public static final TagKey<Block> IMMOVABLE = create("clutter","immovable");
    public static final TagKey<Block> COPPER_DOORS = create("clutter","copper_doors");
    public static final TagKey<Block> SEATS = create("clutter","seats");
    public static final TagKey<Block> BASALT = create("clutter","basalt");
    public static final TagKey<Block> GREEN_FIRE_BASE_BLOCKS = create("clutter","green_fire_base_blocks");

    private static TagKey<Block> create(String namespace, String path) {
        return TagKey.of(Registries.BLOCK.getKey(), new Identifier(namespace, path));
    }
}
