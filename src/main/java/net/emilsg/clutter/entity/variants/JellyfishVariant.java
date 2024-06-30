package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum JellyfishVariant {
    GREEN(0, "green"),
    BLUE(1, "blue"),
    PURPLE(2, "purple");

    private static final JellyfishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(JellyfishVariant::getId)).toArray(JellyfishVariant[]::new);
    private final int id;
    private final String name;

    JellyfishVariant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static JellyfishVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }
}
