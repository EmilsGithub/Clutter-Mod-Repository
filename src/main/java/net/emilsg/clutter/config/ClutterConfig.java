package net.emilsg.clutter.config;

import net.emilsg.clutter.Clutter;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

import java.io.*;
import java.util.*;

public class ClutterConfig {
    public static final String FILE_VERSION = "0.6.0";
    private static final Logger LOGGER = Clutter.LOGGER;
    private final Map<String, ConfigEntry<?>> configs;
    private final File configFile;
    private static ClutterConfig instance;
    private static final String fileName = "clutter.properties";

    public ClutterConfig(String configFilePath) {
        this.configFile = new File(configFilePath);
        this.configs = new HashMap<>();
        initializeConfigs();
        loadOrCreateConfig();
    }

    public static void init() {
        LOGGER.info("Initializing Clutter Config.");
    }

    public static ClutterConfig getInstance() {
        if (instance == null) {
            instance = new ClutterConfig(FabricLoader.getInstance().getConfigDir().resolve(fileName).toString());
        }
        return instance;
    }

    public static final String DISABLE_GREED_GENERATION = "disable_greed_loot_generation";
    public static final String COIN_DROPS_AND_LOOT_GEN = "coin_drops_and_loot_gen";
    public static final String CHERRY_DROP_RATE = "cherry_drop_rate";
    public static final String GREED_CHANCE_PER_LEVEL = "greed_chance_per_level";
    public static final String GREED_WITH_LOOTING_AND_MENDING = "greed_with_looting_and_mending";
    public static final String MELTDOWN_DESTROYS_BLOCKS = "meltdown_destroys_blocks";
    public static final String SPAWN_CLUTTER_MOBS = "spawn_clutter_mobs";
    public static final String SPAWN_BUTTERFLIES = "spawn_butterflies";
    public static final String SPAWN_CHAMELEONS = "spawn_chameleons";
    public static final String SPAWN_ECHOFINS = "spawn_echofins";
    public static final String SPAWN_MOSSBLOOMS = "spawn_mossblooms";
    public static final String SPAWN_KIWIS = "spawn_kiwis";
    public static final String SPAWN_EMPEROR_PENGUINS = "spawn_emperor_penguins";
    public static final String SPAWN_BEAVERS = "spawn_beavers";
    public static final String SPAWN_CAPYBARAS = "spawn_capybaras";
    public static final String SPAWN_CRIMSON_NEWTS = "spawn_crimson_newts";
    public static final String SPAWN_EMBER_TORTOISES = "spawn_ember_tortoises";
    public static final String SPAWN_JELLYFISHES = "spawn_jellyfishes";
    public static final String GENERATE_GEODES = "generate_geodes";
    public static final String GENERATE_ORES = "generate_ores";
    public static final String GENERATE_FOLIAGE = "generate_foliage";
    public static final String GENERATE_SILVER_ORES = "generate_silver_ores";
    public static final String GENERATE_SULPHUR_ORES = "generate_sulphur_ores";
    public static final String GENERATE_BIOMES = "generate_biomes";
    public static final String GENERATE_REDWOOD_FORESTS = "generate_redwood_forests";
    public static final String GENERATE_LUPINE_FIELDS = "generate_lupine_fields";
    public static final String GENERATE_SEASHELLS = "generate_seashells";

    public void initializeConfigs() {
        configs.put(DISABLE_GREED_GENERATION, new ConfigEntry<>( false, "Disables the generation of Greed Books"));
        configs.put(GREED_WITH_LOOTING_AND_MENDING, new ConfigEntry<>( false, "Greed compatible with Looting and Mending."));
        configs.put(GREED_CHANCE_PER_LEVEL, new ConfigEntry<>( 0.01f, "Coin drop chance per level of Greed. (Default 1% per level of Greed)"));
        configs.put(CHERRY_DROP_RATE, new ConfigEntry<>( 0.075f, "Drop rate for cherries. (Default, 7.5% chance for 1 to 2 Cherries to Drop, not affected by Fortune)"));
        configs.put(COIN_DROPS_AND_LOOT_GEN, new ConfigEntry<>( true, "Coin drops and generation."));
        configs.put(MELTDOWN_DESTROYS_BLOCKS, new ConfigEntry<>( false, "Meltdown destroys blocks."));
        configs.put(SPAWN_CLUTTER_MOBS, new ConfigEntry<>( true, "Spawn Clutter Mobs."));
        configs.put(SPAWN_BUTTERFLIES, new ConfigEntry<>( true, "Spawn Butterflies."));
        configs.put(SPAWN_CHAMELEONS, new ConfigEntry<>( true, "Spawn Chameleons."));
        configs.put(SPAWN_ECHOFINS, new ConfigEntry<>( true, "Spawn Echofins."));
        configs.put(SPAWN_MOSSBLOOMS, new ConfigEntry<>( true, "Spawn Mossblooms."));
        configs.put(SPAWN_KIWIS, new ConfigEntry<>( true, "Spawn Kiwis."));
        configs.put(SPAWN_EMPEROR_PENGUINS, new ConfigEntry<>( true, "Spawn Emperor Penguins."));
        configs.put(SPAWN_BEAVERS, new ConfigEntry<>( true, "Spawn Beavers."));
        configs.put(SPAWN_CAPYBARAS, new ConfigEntry<>( true, "Spawn Capybaras."));
        configs.put(SPAWN_CRIMSON_NEWTS, new ConfigEntry<>( true, "Spawn Crimson Newts."));
        configs.put(SPAWN_EMBER_TORTOISES, new ConfigEntry<>( true, "Spawn Ember Tortoises."));
        configs.put(SPAWN_JELLYFISHES, new ConfigEntry<>( true, "Spawn Jellyfishes."));
        configs.put(GENERATE_GEODES, new ConfigEntry<>( true, "Generate Geodes."));
        configs.put(GENERATE_ORES, new ConfigEntry<>( true, "Generate Ores."));
        configs.put(GENERATE_FOLIAGE, new ConfigEntry<>( true, "Generate Foliage."));
        configs.put(GENERATE_SILVER_ORES, new ConfigEntry<>( true, "Generate Silver Ore."));
        configs.put(GENERATE_SULPHUR_ORES, new ConfigEntry<>( true, "Generate Sulphur Ores."));
        configs.put(GENERATE_BIOMES, new ConfigEntry<>( true, "Generate Clutter Biomes."));
        configs.put(GENERATE_REDWOOD_FORESTS, new ConfigEntry<>( true, "Generate Redwood Forests."));
        configs.put(GENERATE_LUPINE_FIELDS, new ConfigEntry<>( true, "Generate Lupine Fields."));
        configs.put(GENERATE_SEASHELLS, new ConfigEntry<>( true, "Generate Seashells."));
    }

    public boolean isConfigVersionCurrent() {
        if(getCurrentFileVersion() != null) {
            return getCurrentFileVersion().equals("# File Version: " + FILE_VERSION);
        }
        return false;
    }

    public String getCurrentFileVersion() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("# File Version:")) {
                    return line;
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error reading config file for version check.", e);
        }
        return null;
    }

    public String getFileVersionNumber(String versionLine) {
        return versionLine.replace("# File Version: ", "");
    }

    private void loadOrCreateConfig() {
        if (configFile.exists()) {
            loadConfig();
        } else {
            LOGGER.info( "Clutter config file is missing, generating default one." );
            createDefaultConfig();
        }
    }

    private void loadConfig() {
        Properties props = new Properties();

        try (FileReader reader = new FileReader(configFile)) {
            props.load(reader);

            for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
                String key = entry.getKey();
                ConfigEntry<?> configEntry = entry.getValue();

                if (props.containsKey(key)) {

                    updateConfigEntry(key, configEntry, props.getProperty(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMissingConfigsAndUpdateVersion() {
        loadConfig();

        StringBuilder newFileContent = new StringBuilder();
        boolean versionMismatch = true;
        boolean changesMade = false;

        try (FileReader reader = new FileReader(configFile)) {
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("# File Version:")) {
                        if (line.equals("# File Version: " + FILE_VERSION)) {
                            versionMismatch = false;
                        } else {
                            line = "# File Version: " + FILE_VERSION;
                            changesMade = true;
                        }
                    }
                    newFileContent.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read existing configuration for version check.", e);
        }

        for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
            if (!newFileContent.toString().contains(entry.getKey() + "=")) {
                ConfigEntry<?> configEntry = entry.getValue();
                String key = entry.getKey();
                String value = configEntry.getValue().toString();
                String comment = configEntry.getComment();

                if (comment != null && !comment.isEmpty()) {
                    newFileContent.append("\n# ").append(comment).append(" Default value: ").append(value);
                }
                newFileContent.append("\n").append(key).append("=").append(value).append("\n");
                changesMade = true;
            }
        }

        if (changesMade) {
            try (FileWriter writer = new FileWriter(configFile, false)) {
                writer.write(newFileContent.toString());
                LOGGER.info("Configuration file updated with missing entries and/or version update.");
            } catch (IOException e) {
                LOGGER.error("Failed to update configuration file.", e);
            }
        } else {
            LOGGER.info("Configuration file up-to-date. No changes made.");
        }
    }



    @SuppressWarnings("unchecked")
    private <T> void updateConfigEntry(String key, ConfigEntry<T> configEntry, String value) {
        T convertedValue = (T) convertStringToType(key, value, configEntry.getValue().getClass(), configEntry);
        configEntry.setValue(convertedValue);
    }

    private <T> Object convertStringToType(String key, String value, Class<?> type, ConfigEntry<T> configEntry) {
        try {
            if (Boolean.class.isAssignableFrom(type)) {
                return type.cast(Boolean.parseBoolean(value));
            }
            if (Integer.class.isAssignableFrom(type)) {
                return type.cast(Integer.parseInt(value));
            }
            if (Float.class.isAssignableFrom(type)) {
                return type.cast(Float.parseFloat(value));
            }
            if (Double.class.isAssignableFrom(type)) {
                return type.cast(Double.parseDouble(value));
            }
        } catch (Exception e) {
            LOGGER.warn("Error converting config value for key '" + key + "' with value '" + value + "'. Please check your clutter.properties file.");
            return configEntry.getDefaultValue();
        }
        throw new IllegalArgumentException("Unsupported type for configuration key '" + key + "': " + type);
    }

    public void createDefaultConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("# File Version: " + ClutterConfig.FILE_VERSION + "\n\n");

            for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
                String key = entry.getKey();
                ConfigEntry<?> configEntry = entry.getValue();
                String value = configEntry.getValue().toString();
                String comment = configEntry.getComment();

                if (comment != null && !comment.isEmpty()) {
                    writer.write("# " + comment + " Default value: " + value + "\n");
                }
                writer.write(key + "=" + value + "\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void resetConfigEntryValue(ConfigEntry<T> configEntry) {
        configEntry.setValue(configEntry.getDefaultValue());
    }

    public void resetConfig() {
        for (Map.Entry<String, ConfigEntry<?>> entry : configs.entrySet()) {
            resetConfigEntryValue(entry.getValue());
        }
        createDefaultConfig();
        LOGGER.info("Configuration reset to default values.");
    }

    public boolean getBoolean(String key) {
        return (boolean) configs.get(key).getValue();
    }

    public int getInteger(String key) {
        return (int) configs.get(key).getValue();
    }

    public float getFloat(String key) {
        return (float) configs.get(key).getValue();
    }

    public double getDouble(String key) {
        return (double) configs.get(key).getValue();
    }

    private static class ConfigEntry<T> {
        private T value;
        private final T defaultValue;
        private final String comment;

        public ConfigEntry(T defaultValue, String comment) {
            this.defaultValue = defaultValue;
            this.comment = comment;
            this.value = defaultValue;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public T getDefaultValue() {
            return defaultValue;
        }

        public String getComment() {
            return comment;
        }
    }
}
