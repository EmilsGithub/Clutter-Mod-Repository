package net.emilsg.clutter.util;

import net.emilsg.clutter.block.ModBlocks;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;

public class ModFlammableAndOrStrippableBlocks {

    public static void registerFlammableAndStrippableBlocks() {
        registerFlammableBlocks();
        registerStrippables();
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(ModBlockTags.WOODEN_CHAIRS, 5, 20);
        registry.add(ModBlockTags.ARMCHAIRS, 5, 20);
        registry.add(ModBlockTags.TABLES, 5, 20);
    }

    public static void registerStrippables() {

    }
}
