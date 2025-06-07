package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {

    public static final TagKey<Biome> SPAWNS_CRIMSON_NEWTS = create(Clutter.MOD_ID, "spawns_crimson_newts");
    public static final TagKey<Biome> SPAWNS_WARPED_NEWTS = create(Clutter.MOD_ID, "spawns_warped_newts");
    public static final TagKey<Biome> SPAWNS_EMBER_TORTOISES = create(Clutter.MOD_ID, "spawns_ember_tortoises");
    public static final TagKey<Biome> SPAWNS_ECHOFINS = create(Clutter.MOD_ID, "spawns_echofins");
    public static final TagKey<Biome> SPAWNS_SEAHORSES = create(Clutter.MOD_ID, "spawns_seahorses");
    public static final TagKey<Biome> SPAWNS_MANTA_RAYS = create(Clutter.MOD_ID, "spawns_manta_rays");
    public static final TagKey<Biome> SPAWNS_JELLYFISHES = create(Clutter.MOD_ID, "spawns_jellyfishes");
    public static final TagKey<Biome> SPAWNS_BUTTERFLIES = create(Clutter.MOD_ID, "spawns_butterflies");
    public static final TagKey<Biome> SPAWNS_CAPYBARAS = create(Clutter.MOD_ID, "spawns_capybaras");
    public static final TagKey<Biome> SPAWNS_BEAVERS = create(Clutter.MOD_ID, "spawns_beavers");
    public static final TagKey<Biome> SPAWNS_EMPEROR_PENGUINS = create(Clutter.MOD_ID, "spawns_emperor_penguins");
    public static final TagKey<Biome> SPAWNS_KIWIS = create(Clutter.MOD_ID, "spawns_kiwis");
    public static final TagKey<Biome> SPAWNS_CHAMELEONS = create(Clutter.MOD_ID, "spawns_chameleons");
    public static final TagKey<Biome> SPAWNS_MOSSBLOOMS = create(Clutter.MOD_ID, "spawns_mossblooms");

    private static TagKey<Biome> create(String namespace, String path) {
        return TagKey.of(RegistryKeys.BIOME, new Identifier(namespace, path));
    }
}
