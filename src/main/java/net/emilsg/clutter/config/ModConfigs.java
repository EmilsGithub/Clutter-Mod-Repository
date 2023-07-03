package net.emilsg.clutter.config;

import com.mojang.datafixers.util.Pair;
import net.emilsg.clutter.Clutter;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static float GREED_CHANCE_PER_LEVEL;
    public static boolean COIN_DROPS_AND_LOOT_GEN;

    public static boolean SPAWN_CLUTTER_MOBS;
    public static boolean SPAWN_BUTTERFLIES;
    public static boolean SPAWN_CHAMELEONS;
    public static boolean SPAWN_ECHOFINS;
    public static boolean SPAWN_MOSSBLOOMS;
    public static boolean SPAWN_KIWI_BIRDS;


    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Clutter.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("greed.chance.per.level", 0.01f), "Coin drop chance per level of Greed.");
        configs.addKeyValuePair(new Pair<>("coin.drops.and.loot.gen", true), "Coin drops and generation.");

        configs.addKeyValuePair(new Pair<>("spawn.clutter.mobs", true), "Spawn Clutter Mobs.");
        configs.addKeyValuePair(new Pair<>("spawn.butterflies", true), "Spawn Butterflies.");
        configs.addKeyValuePair(new Pair<>("spawn.chameleons", true), "Spawn Chameleons.");
        configs.addKeyValuePair(new Pair<>("spawn.echofins", true), "Spawn Echofins.");
        configs.addKeyValuePair(new Pair<>("spawn.mossblooms", true), "Spawn Mossblooms.");
        configs.addKeyValuePair(new Pair<>("spawn.kiwi_birds", true), "Spawn Kiwi Birds.");
    }

    private static void assignConfigs() {
        GREED_CHANCE_PER_LEVEL = (float) CONFIG.getOrDefault("greed.chance.per.level", 0.01f);
        COIN_DROPS_AND_LOOT_GEN = CONFIG.getOrDefault("coin.drops.and.loot.gen", true);

        SPAWN_CLUTTER_MOBS = CONFIG.getOrDefault("spawn.clutter.mobs", true);
        SPAWN_BUTTERFLIES = CONFIG.getOrDefault("spawn.butterflies", true);
        SPAWN_CHAMELEONS = CONFIG.getOrDefault("spawn.chameleons", true);
        SPAWN_ECHOFINS = CONFIG.getOrDefault("spawn.echofins", true);
        SPAWN_MOSSBLOOMS = CONFIG.getOrDefault("spawn.mossblooms", true);
        SPAWN_KIWI_BIRDS = CONFIG.getOrDefault("spawn.kiwi_birds", true);
    }
}
