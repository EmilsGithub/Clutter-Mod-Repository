package net.emilsg.clutter.util;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockTags {
    public static final TagKey<Block> ARMCHAIRS = TagKey.of(Registry.BLOCK.getKey(), new Identifier("clutter", "armchairs"));
    public static final TagKey<Block> FLOOR_SEATING = TagKey.of(Registry.BLOCK.getKey(), new Identifier("clutter", "floor_seating"));
    public static final TagKey<Block> BOOKSHELVES = TagKey.of(Registry.BLOCK.getKey(), new Identifier("c", "bookshelves"));
    public static final TagKey<Block> FULL_SCULK_BLOCKS = TagKey.of(Registry.BLOCK.getKey(), new Identifier("clutter", "full_sculk_blocks"));
    public static final TagKey<Block> CHAINS = TagKey.of(Registry.BLOCK.getKey(), new Identifier("clutter", "chains"));
    public static final TagKey<Block> COPPER_DOORS = TagKey.of(Registry.BLOCK.getKey(), new Identifier("clutter", "copper_doors"));
}
