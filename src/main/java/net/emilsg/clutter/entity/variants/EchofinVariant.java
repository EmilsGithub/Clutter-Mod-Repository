package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum EchofinVariant {
    LEVITATING(0),
    CHORUS(1);

    private static final EchofinVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(EchofinVariant::getId)).toArray(EchofinVariant[]::new);
    private final int id;

    EchofinVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EchofinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
