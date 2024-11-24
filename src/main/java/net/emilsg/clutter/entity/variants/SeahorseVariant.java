package net.emilsg.clutter.entity.variants;

import com.mojang.serialization.Codec;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.Comparator;

public enum SeahorseVariant {
    YELLOW(0, "yellow", Formatting.YELLOW),
    LIGHT_BLUE(1, "light_blue", Formatting.AQUA),
    RED(2, "red", Formatting.RED),
    PURPLE(3, "purple", Formatting.DARK_PURPLE);

    private static final SeahorseVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(SeahorseVariant::getId)).toArray(SeahorseVariant[]::new);
    private final int id;
    private final String name;
    private final Formatting colorFormatting;
    public static final Codec<SeahorseVariant> CODEC = Codec.INT.xmap(SeahorseVariant::byId, SeahorseVariant::getId);

    SeahorseVariant(int id, String name, Formatting colorFormatting) {
        this.id = id;
        this.name = name;
        this.colorFormatting = colorFormatting;
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

    public Formatting getColorFormatting() {return this.colorFormatting;}
}
