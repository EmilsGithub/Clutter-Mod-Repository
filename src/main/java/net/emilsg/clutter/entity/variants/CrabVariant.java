package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum CrabVariant {
    BLUE(0),
    BROWN(1),
    PURPLE(2),
    ORANGE(3);

    private static final CrabVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(CrabVariant::getId)).toArray(CrabVariant[]::new);
    private final int id;

    CrabVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static CrabVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
