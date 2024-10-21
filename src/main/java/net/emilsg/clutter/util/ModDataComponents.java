package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final ComponentType<Integer> COIN_POUCH_RARITY = register("rarity", (builder) -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.VAR_INT));

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return (ComponentType) Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Clutter.MOD_ID, id), ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }
}
