package net.emilsg.clutter.networking.packet;

import net.emilsg.clutter.block.entity.CardboardBoxInventoryBlockEntity;
import net.emilsg.clutter.block.entity.ShelfInventoryBlockEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ItemStackSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) list.set(i, buf.readItemStack());

        BlockPos pos = buf.readBlockPos();

        assert client.world != null;
        if (client.world.getBlockEntity(pos) instanceof ShelfInventoryBlockEntity shelfBlockEntity) shelfBlockEntity.setInventory(list);
        if (client.world.getBlockEntity(pos) instanceof CardboardBoxInventoryBlockEntity cardboardBoxBlockEntity) cardboardBoxBlockEntity.setInventory(list);
    }
}
