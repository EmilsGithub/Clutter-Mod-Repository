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
}
