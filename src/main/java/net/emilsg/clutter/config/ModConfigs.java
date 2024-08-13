package net.emilsg.clutter.config;

import com.mojang.datafixers.util.Pair;
import net.emilsg.clutter.Clutter;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean DISABLE_GREED_LOOT_GENERATION;
    public static boolean GREED_WITH_LOOTING_AND_MENDING;
    public static float GREED_CHANCE_PER_LEVEL;
    public static boolean GLOBAL_COIN_LOOT;
    public static boolean CHEST_COIN_LOOT;
    public static boolean MOB_COIN_LOOT;

    public static float CHERRY_DROP_RATE;

    public static boolean MELTDOWN_DESTROY_BLOCKS;

    public static boolean SPAWN_CLUTTER_MOBS;
    public static boolean SPAWN_BUTTERFLIES;
    public static boolean SPAWN_CHAMELEONS;
    public static boolean SPAWN_ECHOFINS;
    public static boolean SPAWN_MOSSBLOOMS;
    public static boolean SPAWN_KIWI_BIRDS;
    public static boolean SPAWN_EMPEROR_PENGUINS;
    public static boolean SPAWN_BEAVERS;
    public static boolean SPAWN_CAPYBARAS;
    public static boolean SPAWN_CRIMSON_NEWTS;
    public static boolean SPAWN_EMBER_TORTOISES;

    public static boolean GENERATE_ORES;
    public static boolean GENERATE_SILVER_ORE;

    public static boolean GENERATE_GEODES;

    public static boolean GENERATE_FOLIAGE;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Clutter.MOD_ID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("disable.greed_loot_generation", false), "Disables the generation of Greed Books");
        configs.addKeyValuePair(new Pair<>("greed.with_looting_and_mending", false), "Greed compatible with Looting and Mending.");
        configs.addKeyValuePair(new Pair<>("greed.chance.per.level", 0.01f), "Coin drop chance per level of Greed. (Default 1% per level of Greed)");

        configs.addKeyValuePair(new Pair<>("cherry.drop.rate", 0.075f), "Drop rate for cherries. (Default, 7.5% chance for 1 to 2 Cherries to Drop, not affected by Fortune)");

        configs.addKeyValuePair(new Pair<>("global.coin.loot", true), "Disables or Enables coins from chest -and mob loot.");
        configs.addKeyValuePair(new Pair<>("chest.coin.loot", true), "Coin generation in chests.");
        configs.addKeyValuePair(new Pair<>("mob.coin.loot", true), "Coin drops from certain mobs.");

        configs.addKeyValuePair(new Pair<>("meltdown.destroy_blocks", false), "Meltdown destroys blocks.");

        configs.addKeyValuePair(new Pair<>("spawn.clutter.mobs", true), "Spawn Clutter Mobs.");
        configs.addKeyValuePair(new Pair<>("spawn.butterflies", true), "Spawn Butterflies.");
        configs.addKeyValuePair(new Pair<>("spawn.chameleons", true), "Spawn Chameleons.");
        configs.addKeyValuePair(new Pair<>("spawn.echofins", true), "Spawn Echofins.");
        configs.addKeyValuePair(new Pair<>("spawn.mossblooms", true), "Spawn Mossblooms.");
        configs.addKeyValuePair(new Pair<>("spawn.kiwi_birds", true), "Spawn Kiwi Birds.");
        configs.addKeyValuePair(new Pair<>("spawn.emperor_penguins", true), "Spawn Emperor Penguins.");
        configs.addKeyValuePair(new Pair<>("spawn.beavers", true), "Spawn Beavers.");
        configs.addKeyValuePair(new Pair<>("spawn.capybaras", true), "Spawn Capybaras.");
        configs.addKeyValuePair(new Pair<>("spawn.crimson_newts", true), "Spawn Crimson Newts.");
        configs.addKeyValuePair(new Pair<>("spawn.ember_tortoises", true), "Spawn Ember Tortoises.");

        configs.addKeyValuePair(new Pair<>("generate.geodes", true), "Generate Geodes.");

        configs.addKeyValuePair(new Pair<>("generate.ores", true), "Generate Ores.");
        configs.addKeyValuePair(new Pair<>("generate.silver_ore", true), "Generate Silver Ore.");

        configs.addKeyValuePair(new Pair<>("generate.foliage", true), "Generate Foliage.");
    }

    private static void assignConfigs() {
        DISABLE_GREED_LOOT_GENERATION = CONFIG.getOrDefault("disable.greed_loot_generation", false);
        GREED_WITH_LOOTING_AND_MENDING = CONFIG.getOrDefault("greed.with_looting_and_mending", false);
        GREED_CHANCE_PER_LEVEL = (float) CONFIG.getOrDefault("greed.chance.per.level", 0.01f);
        GLOBAL_COIN_LOOT = CONFIG.getOrDefault("global.coin.loot", true);
        CHEST_COIN_LOOT = CONFIG.getOrDefault("chest.coin.loot", true);
        MOB_COIN_LOOT = CONFIG.getOrDefault("mob.coin.loot", true);

        CHERRY_DROP_RATE = (float) CONFIG.getOrDefault("cherry.drop.rate", 0.075f);

        MELTDOWN_DESTROY_BLOCKS = CONFIG.getOrDefault("meltdown.destroy_blocks", false);

        SPAWN_CLUTTER_MOBS = CONFIG.getOrDefault("spawn.clutter.mobs", true);
        SPAWN_BUTTERFLIES = CONFIG.getOrDefault("spawn.butterflies", true);
        SPAWN_CHAMELEONS = CONFIG.getOrDefault("spawn.chameleons", true);
        SPAWN_ECHOFINS = CONFIG.getOrDefault("spawn.echofins", true);
        SPAWN_MOSSBLOOMS = CONFIG.getOrDefault("spawn.mossblooms", true);
        SPAWN_KIWI_BIRDS = CONFIG.getOrDefault("spawn.kiwi_birds", true);
        SPAWN_EMPEROR_PENGUINS = CONFIG.getOrDefault("spawn.emperor_penguins", true);
        SPAWN_BEAVERS = CONFIG.getOrDefault("spawn.beavers", true);
        SPAWN_CAPYBARAS = CONFIG.getOrDefault("spawn.capybaras", true);
        SPAWN_CRIMSON_NEWTS = CONFIG.getOrDefault("spawn.crimson_newts", true);
        SPAWN_EMBER_TORTOISES = CONFIG.getOrDefault("spawn.ember_tortoises", true);

        GENERATE_GEODES = CONFIG.getOrDefault("generate.geodes", true);

        GENERATE_ORES = CONFIG.getOrDefault("generate.ores", true);
        GENERATE_SILVER_ORE = CONFIG.getOrDefault("generate.silver_ore", true);

        GENERATE_FOLIAGE = CONFIG.getOrDefault("generate.foliage", true);
    }
}
