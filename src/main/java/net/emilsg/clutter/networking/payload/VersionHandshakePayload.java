package net.emilsg.clutter.networking.payload;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.networking.ModMessages;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record VersionHandshakePayload(String getClientVersion) implements CustomPayload {
    public static final Id<VersionHandshakePayload> ID = new Id<>(ModMessages.VERSION_HANDSHAKE_PACKET_ID);

    public static final PacketCodec<RegistryByteBuf, VersionHandshakePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, VersionHandshakePayload::getClientVersion,
            VersionHandshakePayload::new
    );

    public VersionHandshakePayload (String getClientVersion) {
        this.getClientVersion = Clutter.MOD_VERSION;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
