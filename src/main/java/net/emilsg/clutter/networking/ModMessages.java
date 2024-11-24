package net.emilsg.clutter.networking;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.networking.payload.VersionHandshakePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class ModMessages {
    public static final Identifier SYNC_ITEMS_PACKET_ID = Identifier.of(Clutter.MOD_ID, "sync_items");
    public static final Identifier VERSION_HANDSHAKE_PACKET_ID = Identifier.of(Clutter.MOD_ID, "version_handshake");


    public static void registerS2CPackets() {
        ClientPlayConnectionEvents.JOIN.register((handler, client, isConnected) -> {
            handler.sendPacket(ClientPlayNetworking.createC2SPacket(new VersionHandshakePayload(Clutter.MOD_VERSION)));
        });

        //ClientPlayNetworking.registerGlobalReceiver(ItemStackSyncS2CPayload.ID, (((itemStackSyncS2CPayload, context) -> {
        //    context.client().execute(() -> {
        //        ItemStackSyncS2CPayload.receive(context.client(), context.client().getNetworkHandler(), context.player());
        //    });
        //})));
    }

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(VersionHandshakePayload.ID, VersionHandshakePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(VersionHandshakePayload.ID, ((versionHandshakePayload, context) -> {
            if (!Objects.equals(versionHandshakePayload.getClientVersion(), Clutter.MOD_VERSION)) context.player().networkHandler
                    .disconnect(Text.literal("Mismatched mod version! Please use Clutter version " + Clutter.MOD_VERSION + ". If you're using a Modpack please make sure it's the same version as the Server."));
        }));
    }
}
