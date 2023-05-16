package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup CLUTTER_BLOCKS;
    public static ItemGroup CLUTTER_ITEMS;

    public static void registerItemGroups() {
        CLUTTER_BLOCKS = FabricItemGroup.builder(new Identifier(Clutter.MOD_ID, "clutter_blocks"))
                .displayName(Text.translatable("itemgroup.clutter_blocks"))
                .icon(() -> new ItemStack(ModBlocks.COPPER_LANTERN)).build();

        CLUTTER_ITEMS = FabricItemGroup.builder(new Identifier(Clutter.MOD_ID, "clutter_items"))
                .displayName(Text.translatable("itemgroup.clutter_items"))
                .icon(() -> new ItemStack(ModItems.COPPER_NUGGET)).build();
    }
}
