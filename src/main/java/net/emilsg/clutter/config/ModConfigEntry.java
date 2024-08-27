
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
 * Represents a configuration entry with a key, value, default value, and an optional comment.
 *
 * @param <T> The type of the configuration value.
 */
public class ModConfigEntry<T> {
    // The comment describing the purpose of this configuration entry.
    private final String comment;
    // The default value of this configuration entry.
    private final T defaultValue;
    // The unique key identifying this configuration entry.
    private final String key;
    // The current value of this configuration entry.
    private T value;

    /**
     * Constructs a new ModConfigEntry with the given key, value, and comment.
     *
     * @param key     The unique key for this configuration entry.
     * @param value   The initial value for this configuration entry, which is also set as the default value.
     * @param comment An optional comment describing this configuration entry.
     */
    public ModConfigEntry(String key, T value, String comment) {
        this.key = key;
        this.value = value;
        this.defaultValue = value;
        this.comment = comment;
    }

    /**
     * Returns the key associated with this configuration entry.
     *
     * @return The key as a String.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the current value of this configuration entry.
     *
     * @return The current value of type T.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value of this configuration entry.
     *
     * @param value The new value of type T.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns the default value of this configuration entry.
     *
     * @return The default value of type T.
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns the comment associated with this configuration entry.
     *
     * @return The comment as a String.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Resets the value of this configuration entry to its default value.
     */
    public void resetToDefault() {
        this.value = this.defaultValue;
    }
}
