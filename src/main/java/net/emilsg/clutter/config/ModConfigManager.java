
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ModConfigManager {
    // Logger for recording configuration-related activities and errors.
    private static final Logger LOGGER = LogManager.getLogger("ClutterConfig");
    // Gson instance for reading and writing JSON files with pretty printing enabled.
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Configuration file and folder names.
    private static final String CONFIG_FILE_NAME = "clutter_config.json";
    private static final String CONFIG_FILE_FOLDER = "clutter";

    // Stores registered configuration entries by their key.
    private static final Map<String, ModConfigEntry<?>> CONFIG_ENTRIES = new HashMap<>();
    // Maps class types to their corresponding JSON parsing functions.
    private static final Map<Class<?>, Function<JsonElement, ?>> PARSERS = new HashMap<>();

    // Configuration directory and file.
    private static File configDir;
    private static File configFile;
    // Flag to track whether the configuration files have been initialized.
    private static boolean isInitialized = false;

    static {
        // Initialize JSON parsers for various data types.
        PARSERS.put(Boolean.class, JsonElement::getAsBoolean);
        PARSERS.put(Integer.class, JsonElement::getAsInt);
        PARSERS.put(Float.class, JsonElement::getAsFloat);
        PARSERS.put(Double.class, JsonElement::getAsDouble);
        PARSERS.put(String.class, JsonElement::getAsString);
    }

    // Initializes the configuration files and directories if they haven't been initialized yet.
    private static void initConfigFiles() {
        if (!isInitialized) {
            configDir = new File(FabricLoader.getInstance().getConfigDir().toFile(), CONFIG_FILE_FOLDER);
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            configFile = new File(configDir, CONFIG_FILE_NAME);
            isInitialized = true;
        }
    }

    /**
     * Registers a new configuration entry with a key, default value, and an optional comment.
     *
     * @param key          The unique key for this configuration entry.
     * @param defaultValue The default value for this configuration entry.
     * @param comment      An optional comment describing the configuration entry.
     * @param <T>          The type of the configuration value.
     * @return The created ModConfigEntry object.
     */
    public static <T> ModConfigEntry<T> register(String key, T defaultValue, String comment) {
        ModConfigEntry<T> entry = new ModConfigEntry<>(key, defaultValue, comment);
        CONFIG_ENTRIES.put(key, entry);
        return entry;
    }

    /**
     * Retrieves the value associated with the specified key, or returns the provided default value if the key doesn't exist.
     *
     * @param key          The key of the configuration entry.
     * @param defaultValue The default value to return if the key is not found.
     * @param <T>          The type of the configuration value.
     * @return The configuration value or the default value if the key doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, T defaultValue) {
        ModConfigEntry<?> entry = CONFIG_ENTRIES.get(key);
        if (entry != null && entry.getValue() != null) {
            return (T) entry.getValue();
        }
        return defaultValue;
    }

    /**
     * Loads the configuration from the JSON file, updating registered entries with the loaded values.
     * If the configuration file is missing or corrupted, it creates a new one with default values.
     */
    public static void loadConfig() {
        Configs.initConfigs();
        initConfigFiles();

        boolean needsSave = false;

        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                JsonObject jsonObject = GSON.fromJson(reader, JsonObject.class);
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    ModConfigEntry<?> configEntry = CONFIG_ENTRIES.get(entry.getKey());
                    if (configEntry != null) {
                        try {
                            JsonObject entryObject = entry.getValue().getAsJsonObject();
                            JsonElement valueElement = entryObject.get("value");
                            setConfigEntryValue(configEntry, valueElement, entry.getKey());

                        } catch (Exception e) {
                            LOGGER.error("Invalid value for key: {}. Using default value.", entry.getKey());
                            configEntry.resetToDefault();
                            needsSave = true;
                        }
                    } else {
                        LOGGER.error("Unknown config entry: {}", entry.getKey());
                        needsSave = true;
                    }
                }

                // Ensure all registered entries are present in the config file.
                for (Map.Entry<String, ModConfigEntry<?>> registeredEntry : CONFIG_ENTRIES.entrySet()) {
                    if (!jsonObject.has(registeredEntry.getKey())) {
                        LOGGER.error("Adding missing config entry: {}", registeredEntry.getKey());
                        needsSave = true;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Failed to load config file: {}", CONFIG_FILE_NAME);
                e.printStackTrace();
            }
        } else {
            needsSave = true;
        }

        // Save the configuration file if changes were made or it didn't exist.
        if (needsSave) {
            saveConfig();
        }
    }

    /**
     * Sets the value of a specific configuration entry using a JSON element.
     * If the type of the value is unsupported, an error is logged.
     *
     * @param configEntry The configuration entry to update.
     * @param valueElement The JSON element containing the new value.
     * @param key The key associated with the configuration entry.
     * @param <T> The type of the configuration value.
     */
    @SuppressWarnings("unchecked")
    private static <T> void setConfigEntryValue(ModConfigEntry<T> configEntry, JsonElement valueElement, String key) {
        Function<JsonElement, ?> parser = PARSERS.get(configEntry.getDefaultValue().getClass());
        if (parser != null) {
            configEntry.setValue((T) parser.apply(valueElement));
        } else {
            LOGGER.error("Unsupported type for key: {}. Skipping entry.", key);
        }
    }

    /**
     * Saves the current configuration entries to the JSON file.
     * If the file doesn't exist, it is created.
     */
    private static void saveConfig() {
        initConfigFiles();

        JsonObject jsonObject = new JsonObject();

        // Serialize each configuration entry to JSON format.
        CONFIG_ENTRIES.forEach((key, entry) -> {
            JsonObject entryObject = new JsonObject();
            entryObject.addProperty("comment", entry.getComment());
            entryObject.add("value", GSON.toJsonTree(entry.getValue()));
            jsonObject.add(key, entryObject);
        });

        // Write the serialized JSON to the configuration file.
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(jsonObject, writer);
        } catch (IOException e) {
            LOGGER.error("Error when saving config file: {}", CONFIG_FILE_NAME);
            e.printStackTrace();
        }
    }

    /**
     * Resets all configuration entries to their default values and saves the updated configuration to the file.
     */
    public static void resetConfigs() {
        CONFIG_ENTRIES.forEach((key, entry) -> {
            entry.resetToDefault();
        });
        saveConfig();
    }

}
