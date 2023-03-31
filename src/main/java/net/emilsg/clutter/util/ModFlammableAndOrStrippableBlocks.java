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

        registry.add(ModBlockTags.FLAMMABLE_DEFAULT, 5, 20);
    }

    public static void registerStrippables() {

    }
}
