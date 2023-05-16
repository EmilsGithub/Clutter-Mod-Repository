package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum ButterflyVariant {
    YELLOW(0),
    RED(1),
    BLUE(2),
    PURPLE(3),
    WHITE(4),
    GRAY(5),
    ORANGE(6),
    LIME(7),
    GREEN(8),
    BLACK(9),
    LIGHT_GRAY(10),
    LIGHT_BLUE(11),
    BROWN(12),
    CYAN(13),
    MAGENTA(14),
    PINK(15),
    WARPED(16),
    CRIMSON(17),
    SOUL(18);

    private static final ButterflyVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(ButterflyVariant::getId)).toArray(ButterflyVariant[]::new);
    private final int id;

    ButterflyVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static ButterflyVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
