package net.emilsg.clutter.config;

import com.mojang.datafixers.util.Pair;
import net.emilsg.clutter.Clutter;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static float GREED_CHANCE_PER_LEVEL;


    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Clutter.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("greed.chance.per.level", 0.01f), "Coin drop chance per level of Greed.");
    }

    private static void assignConfigs() {

        GREED_CHANCE_PER_LEVEL = (float) CONFIG.getOrDefault("greed.chance.per.level", 0.01f);
    }
}
