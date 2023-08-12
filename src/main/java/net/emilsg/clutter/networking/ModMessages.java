package net.emilsg.clutter.networking;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.networking.packet.ItemStackSyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier SYNC_ITEMS = new Identifier(Clutter.MOD_ID, "sync_items");
    public static final Identifier VERSION_HANDSHAKE_PACKET_ID = new Identifier(Clutter.MOD_ID, "version_handshake");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_ITEMS, ItemStackSyncS2CPacket::receive);
    }

    public static void registerHandshakePackets() {
        ServerPlayNetworking.registerGlobalReceiver(VERSION_HANDSHAKE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            String clientVersion = buf.readString();

            if (!Clutter.MOD_VERSION.equals(clientVersion)) {
                player.networkHandler.disconnect(Text.literal("Mismatched mod version! Please use Clutter version " + Clutter.MOD_VERSION + "."));
            }
        });

    }
}
