package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum EndFishVariant {
    DEFAULT(0),
    DEFAULT_TWO(1);

    private static final EndFishVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(EndFishVariant::getId)).toArray(EndFishVariant[]::new);
    private final int id;

    EndFishVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EndFishVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
