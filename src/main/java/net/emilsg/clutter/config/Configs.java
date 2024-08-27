
/*
 * Copyright (c) 2024 EmilSG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.emilsg.clutter.config;

/**
 * A utility class for initializing and storing configuration keys.
 * Contains static configuration entries that are registered with the ModConfigManager.
 */
public class Configs {

    /**
     * Initializes any necessary configurations.
     * Currently, this method is a placeholder for future initialization logic.
     */
    public static void initConfigs() {}

    public static final String spawnClutterMobs = ModConfigManager.register("spawn_clutter_mobs", true, "Spawn Clutter Mobs.").getKey();
    public static final String spawnButterflies = ModConfigManager.register("spawn_butterflies", true, "Spawn Butterflies.").getKey();
    public static final String spawnChameleons = ModConfigManager.register("spawn_chameleons", true, "Spawn Chameleons.").getKey();
    public static final String spawnEchofins = ModConfigManager.register("spawn_echofins", true, "Spawn Echofins.").getKey();
    public static final String spawnMossblooms = ModConfigManager.register("spawn_mossblooms", true, "Spawn Mossblooms.").getKey();
    public static final String spawnKiwis = ModConfigManager.register("spawn_kiwis", true, "Spawn Kiwis.").getKey();
    public static final String spawnEmperorPenguins = ModConfigManager.register("spawn_emperor_penguins", true, "Spawn Emperor Penguins.").getKey();
    public static final String spawnBeavers = ModConfigManager.register("spawn_beavers", true, "Spawn Beavers.").getKey();
    public static final String spawnCapybaras = ModConfigManager.register("spawn_capybaras", true, "Spawn Capybaras.").getKey();
    public static final String spawnCrimsonNewts = ModConfigManager.register("spawn_crimson_newts", true, "Spawn Crimson Newts.").getKey();
    public static final String spawnWarpedNewts = ModConfigManager.register("spawn_warped_newts", true, "Spawn Warped Newts.").getKey();
    public static final String spawnEmberTortoises = ModConfigManager.register("spawn_ember_tortoises", true, "Spawn Ember Tortoises.").getKey();
    public static final String spawnJellyfishes = ModConfigManager.register("spawn_jellyfishes", true, "Spawn Jellyfishes.").getKey();

    public static final String doGreedGeneration = ModConfigManager.register("do_greed_generation", true, "Will Greed generate in certain chests.").getKey();
    public static final String greedChancePerLevel = ModConfigManager.register("greed_chance_per_level", 0.01f, "Coin drop rate per level of Greed. (expressed as a decimal number where the default, 0.01, is equal to 1%)").getKey();
    public static final String cherryDropRate = ModConfigManager.register("cherry_drop_rate", 0.075f, "Drop rate for Cherries. (expressed as a decimal number where the default, 0.075, is equal to 7.5%)").getKey();
    public static final String doCoinDropsAndLootGeneration = ModConfigManager.register("do_coin_drops_and_loot_generation", true, "Will Coins drop from mobs and Coin Pouches generate in certain chests.").getKey();
    public static final String mobsDropCoinPouches = ModConfigManager.register("mobs_drop_coin_pouches", true, "Will Coin Pouches drop from certain mobs?").getKey();
    public static final String chestLootCoinPouches = ModConfigManager.register("chest_loot_coin_pouches", true, "Will Coin Pouches generate in certain chests?").getKey();

    public static final String meltdownDestroysBlocks = ModConfigManager.register("meltdown_destroys_blocks", false, "Does the explosions caused by Meltdown destroy blocks?").getKey();
    public static final String doTrinketsElytraFlight = ModConfigManager.register("do_trinkets_elytra_flight", true, "Will the Elytra and it´s variants work while worn in the cape slot provided by Trinkets?").getKey();

    public static final String generateGeodes = ModConfigManager.register("generate_geodes", true, "Will Geodes added by Clutter generate?").getKey();
    public static final String generateOnyxGeodes = ModConfigManager.register("generate_onyx_geodes", true, "Will Onyx Geodes generate?").getKey();
    public static final String generateOres = ModConfigManager.register("generate_ores", true, "Will Ores added by Clutter generate?").getKey();
    public static final String generateSilverOres = ModConfigManager.register("generate_silver_ores", true, "Will Silver Ores generate?").getKey();
    public static final String generateSulphurOres = ModConfigManager.register("generate_sulphur_ores", true, "Will Sulphur Ores generate?").getKey();

    public static final String generateFoliage = ModConfigManager.register("generate_foliage", true, "Will Foliage generate?").getKey();

    public static final String generateBiomes = ModConfigManager.register("generate_biomes", true, "Will Biomes added by Clutter generate?").getKey();
    public static final String generateRedwoodForests = ModConfigManager.register("generate_redwood_forests", true, "Will Redwood Forests generate?").getKey();
    public static final String generateLupineFields = ModConfigManager.register("generate_lupine_fields", true, "Will Lupine Fields generate?").getKey();

    public static final String generateMiscFeatures = ModConfigManager.register("generate_misc_features", true, "Will Miscellaneous Features generate?").getKey();
    public static final String generateSeashells = ModConfigManager.register("generate_seashells", true, "Will Seashells generate?").getKey();

    public static final String generateUnderwaterFeatures = ModConfigManager.register("generate_underwater_features", true, "Will Underwater Features generate?").getKey();
    public static final String generateSmallSeaSponges = ModConfigManager.register("generate_small_sea_sponges", true, "Will Small Sea Sponges generate in Coral Reefs?").getKey();
    public static final String generateClams = ModConfigManager.register("generate_clams", true, "Will Clams generate?").getKey();

    public static final String doEnhancedBeaconDisplay = ModConfigManager.register("do_enhanced_beacon_display", true, "Change the way Payment Items in the Beacon´s menu are displayed.").getKey();

}
