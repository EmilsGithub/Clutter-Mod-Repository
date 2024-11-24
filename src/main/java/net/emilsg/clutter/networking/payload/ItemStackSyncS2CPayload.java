package net.emilsg.clutter.networking.payload;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.emilsg.clutter.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ItemStackSyncS2CPayload(MinecraftClient client, PacketByteBuf buf) implements CustomPayload {
    public static final Id<ItemStackSyncS2CPayload> ID = new Id<>(ModMessages.SYNC_ITEMS_PACKET_ID);

    public static final PacketCodec<RegistryByteBuf, VersionHandshakePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, VersionHandshakePayload::getClientVersion,
            VersionHandshakePayload::new
    );


    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //int size = buf.readInt();
        //DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        //for (int i = 0; i < size; i++) list.set(i, buf.readItemStack());
        //BlockPos pos = buf.readBlockPos();
        //assert client.world != null;
        //if (client.world.getBlockEntity(pos) instanceof ShelfInventoryBlockEntity shelfBlockEntity) shelfBlockEntity.setInventory(list);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
