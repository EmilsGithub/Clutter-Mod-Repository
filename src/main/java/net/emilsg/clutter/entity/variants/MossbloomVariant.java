package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum MossbloomVariant {
    M(0),
    F(1);

    private static final MossbloomVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(MossbloomVariant::getId)).toArray(MossbloomVariant[]::new);
    private final int id;

    MossbloomVariant(int id) {
        this.id = id;
    }

    public static MossbloomVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return this.id;
    }
}
