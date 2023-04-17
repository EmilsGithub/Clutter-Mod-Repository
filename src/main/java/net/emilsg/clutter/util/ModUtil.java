package net.emilsg.clutter.util;

import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.VillagerInteractionRegistries;
import net.fabricmc.fabric.api.registry.VillagerPlantableRegistry;
import net.minecraft.item.Item;

public class ModUtil {

    public static void registerModUtil() {
        registerFlammableBlocks();
        registerStrippableBlocks();
        registerCompostableItems();
        registerVillagerInteractions();
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(ModBlockTags.FLAMMABLE, 5, 20);
    }

    public static void registerStrippableBlocks() {

    }

    public static void registerCompostableItems() {
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(ModItems.HOPS_SEEDS, 0.3f);
        registry.add(ModItems.COTTON_SEEDS, 0.3f);
        registry.add(ModItems.HOPS, 0.3f);
        registry.add(ModItems.COTTON, 0.3f);
    }

    public static void registerVillagerInteractions() {
        VillagerInteractionRegistries.registerCompostable(ModItems.HOPS_SEEDS);
        VillagerInteractionRegistries.registerCompostable(ModItems.COTTON_SEEDS);
        VillagerInteractionRegistries.registerCompostable(ModItems.HOPS);
        VillagerInteractionRegistries.registerCompostable(ModItems.COTTON);

        VillagerPlantableRegistry.register(ModItems.HOPS_SEEDS);
        VillagerPlantableRegistry.register(ModItems.COTTON_SEEDS);
    }
}
