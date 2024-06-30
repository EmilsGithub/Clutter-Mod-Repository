package net.emilsg.clutter.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum EchofinVariant {
    LEVITATING(0, "levitating"),
    CHORUS(1, "chorus");

    private static final EchofinVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(EchofinVariant::getId)).toArray(EchofinVariant[]::new);
    private final int id;
    private final String name;

    EchofinVariant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EchofinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }
}
