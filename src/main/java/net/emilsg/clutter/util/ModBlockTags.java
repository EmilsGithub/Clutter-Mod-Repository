package net.emilsg.clutter.util;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> ARMCHAIRS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "armchairs"));
    public static final TagKey<Block> FLOOR_SEATING = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "floor_seating"));
    public static final TagKey<Block> BOOKSHELVES = TagKey.of(Registries.BLOCK.getKey(), new Identifier("c", "bookshelves"));
}
