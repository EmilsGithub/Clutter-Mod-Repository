package net.emilsg.clutter.other;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> FULL_SCULK_BLOCKS = TagKey.of(Registries.BLOCK.getKey(), new Identifier("clutter", "full_sculk_blocks"));
}
