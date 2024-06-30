package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum CrabVariant {
    BLUE(0, "blue"),
    BROWN(1, "brown"),
    PURPLE(2, "purple"),
    ORANGE(3, "orange");

    private static final CrabVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(CrabVariant::getId)).toArray(CrabVariant[]::new);
    private final int id;
    private final String name;

    CrabVariant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CrabVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }
}
