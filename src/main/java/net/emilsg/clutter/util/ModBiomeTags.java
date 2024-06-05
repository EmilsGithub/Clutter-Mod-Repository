package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {

    public static final TagKey<Biome> SPAWNS_CRIMSON_NEWTS = create(Clutter.MOD_ID, "spawns_crimson_newts");
    public static final TagKey<Biome> SPAWNS_WARPED_NEWTS = create(Clutter.MOD_ID, "spawns_warped_newts");
    public static final TagKey<Biome> SPAWNS_NETHER_BUTTERFLIES = create(Clutter.MOD_ID, "spawns_nether_butterflies");
    public static final TagKey<Biome> SPAWNS_EMBER_TORTOISES = create(Clutter.MOD_ID, "spawns_ember_tortoises");


    private static TagKey<Biome> create(String namespace, String path) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(namespace, path));
    }
}
