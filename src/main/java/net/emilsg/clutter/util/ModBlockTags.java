package net.emilsg.clutter.util;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> ARMCHAIRS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "armchairs"));
    public static final TagKey<Block> FLOOR_SEATING = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "floor_seating"));
    public static final TagKey<Block> BOOKSHELVES = TagKey.of(Registries.BLOCK.getKey(), new Identifier("c", "bookshelves"));
    public static final TagKey<Block> FULL_SCULK_BLOCKS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "full_sculk_blocks"));
    public static final TagKey<Block> CHAINS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "chains"));
    public static final TagKey<Block> COPPER_DOORS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "copper_doors"));
    public static final TagKey<Block> TABLES = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "tables"));
    public static final TagKey<Block> WOODEN_CHAIRS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "wooden_chairs"));
    public static final TagKey<Block> STRIPPABLE_CHAIRS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "strippable_chairs"));
    public static final TagKey<Block> STRIPPABLE_TABLES = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "strippable_tables"));
    public static final TagKey<Block> TRELLISES = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "trellises"));
    public static final TagKey<Block> LONG_CURTAIN_BLOCKS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "long_curtain_blocks"));
}
