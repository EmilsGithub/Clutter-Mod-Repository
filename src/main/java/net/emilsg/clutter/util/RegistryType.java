package net.emilsg.clutter.util;

public enum RegistryType {
    VANILLA,
    MODDED;

    public boolean isOf(RegistryType registryType) {
        return this == registryType;
    }
}
