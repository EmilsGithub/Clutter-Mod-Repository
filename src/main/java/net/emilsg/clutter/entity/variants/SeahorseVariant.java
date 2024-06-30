package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum SeahorseVariant {
    YELLOW(0, "yellow"),
    LIGHT_BLUE(1, "light_blue");

    private static final SeahorseVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SeahorseVariant::getId)).toArray(SeahorseVariant[]::new);
    private final int id;
    private final String name;

    SeahorseVariant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SeahorseVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }
}
