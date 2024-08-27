package net.emilsg.clutter.entity.variants;

import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.Comparator;

public enum ButterflyVariant {
    YELLOW(0, "yellow", Formatting.YELLOW),
    RED(1, "red", Formatting.DARK_RED),
    BLUE(2, "blue", Formatting.DARK_BLUE),
    PURPLE(3, "purple", Formatting.DARK_PURPLE),
    WHITE(4, "white", Formatting.WHITE),
    GRAY(5, "gray", Formatting.DARK_GRAY),
    ORANGE(6, "orange", Formatting.GOLD),
    LIME(7, "lime", Formatting.GREEN),
    GREEN(8, "green", Formatting.DARK_GREEN),
    BLACK(9, "black", Formatting.BLACK),
    LIGHT_GRAY(10, "light_gray", Formatting.GRAY),
    LIGHT_BLUE(11, "light_blue", Formatting.BLUE),
    BROWN(12, "brown", Formatting.GOLD),
    CYAN(13, "cyan", Formatting.DARK_AQUA),
    MAGENTA(14, "magenta", Formatting.LIGHT_PURPLE),
    PINK(15, "pink", Formatting.RED),
    WARPED(16, "warped", Formatting.DARK_AQUA),
    CRIMSON(17, "crimson", Formatting.DARK_RED),
    SOUL(18, "soul", Formatting.GOLD);

    private static final ButterflyVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(ButterflyVariant::getId)).toArray(ButterflyVariant[]::new);
    private final int id;
    private final String name;
    private final Formatting colorFormatting;

    ButterflyVariant(int id, String name, Formatting colorFormatting) {
        this.id = id;
        this.name = name;
        this.colorFormatting = colorFormatting;
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

    public Formatting getColorFormatting() {return this.colorFormatting;}
}
