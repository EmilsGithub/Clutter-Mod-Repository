package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum ButterflyVariant {
    YELLOW(0, "yellow"),
    RED(1, "red"),
    BLUE(2, "blue"),
    PURPLE(3, "purple"),
    WHITE(4, "white"),
    GRAY(5, "gray"),
    ORANGE(6, "orange"),
    LIME(7, "lime"),
    GREEN(8, "green"),
    BLACK(9, "black"),
    LIGHT_GRAY(10, "light_gray"),
    LIGHT_BLUE(11, "light_blue"),
    BROWN(12, "brown"),
    CYAN(13, "cyan"),
    MAGENTA(14, "magenta"),
    PINK(15, "pink"),
    WARPED(16, "warped"),
    CRIMSON(17, "crimson"),
    SOUL(18, "soul");

    private static final ButterflyVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ButterflyVariant::getId)).toArray(ButterflyVariant[]::new);
    private final int id;
    private final String name;

    ButterflyVariant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ButterflyVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
