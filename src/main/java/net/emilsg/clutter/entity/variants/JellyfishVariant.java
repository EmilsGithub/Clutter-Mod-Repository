package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum JellyfishVariant {
    GREEN(0),
    BLUE(1),
    PURPLE(2);

    private static final JellyfishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(JellyfishVariant::getId)).toArray(JellyfishVariant[]::new);
    private final int id;

    JellyfishVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static JellyfishVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
