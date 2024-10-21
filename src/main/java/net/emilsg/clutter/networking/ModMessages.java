package net.emilsg.clutter.networking;

import net.emilsg.clutter.Clutter;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier SYNC_ITEMS = Identifier.of(Clutter.MOD_ID, "sync_items");
    public static final Identifier SYNC_PETS = Identifier.of(Clutter.MOD_ID, "sync_pets");
    public static final Identifier VERSION_HANDSHAKE_PACKET_ID = Identifier.of(Clutter.MOD_ID, "version_handshake");


    //public static void registerS2CPackets() {
    //    ClientPlayNetworking.registerGlobalReceiver(SYNC_ITEMS, ItemStackSyncS2CPacket::receive);
    //}

    //public static void registerC2SPackets() {
    //    ServerPlayNetworking.registerGlobalReceiver(SYNC_PETS, SyncPetsC2SPacket::receive);
    //}

    //public static void registerHandshakePackets() {
    //    ServerPlayNetworking.registerGlobalReceiver(VERSION_HANDSHAKE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
    //        String clientVersion = buf.readString();

    //        if (!Clutter.MOD_VERSION.equals(clientVersion)) {
    //            player.networkHandler.disconnect(Text.literal("Mismatched mod version! Please use Clutter version " + Clutter.MOD_VERSION + ". If you're using a Modpack please make sure it's the same version as the Server."));
    //        }
    //    });

    //}
}
