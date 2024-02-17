package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> CLUTTER_BLOCKS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Clutter.MOD_ID, "clutter_blocks"));
    public static final RegistryKey<ItemGroup> CLUTTER_ITEMS = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(Clutter.MOD_ID, "clutter_items"));

    public static void registerItemGroups() {

        Registry.register(Registries.ITEM_GROUP, CLUTTER_BLOCKS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.CYAN_ARMCHAIR))
                .displayName(Text.translatable("itemgroup.clutter_blocks")).build());

        Registry.register(Registries.ITEM_GROUP, CLUTTER_ITEMS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.CYAN_BUTTERFLY_ELYTRA))
                .displayName(Text.translatable("itemgroup.clutter_items")).build());
    }
}
