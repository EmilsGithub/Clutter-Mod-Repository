package net.emilsg.clutter.item;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup CLUTTER;

    public static void registerItemGroups() {
        CLUTTER = FabricItemGroup.builder(new Identifier(Clutter.MOD_ID, "clutter"))
                .displayName(Text.translatable("itemgroup.clutter"))
                .icon(() -> new ItemStack(ModBlocks.COPPER_LANTERN)).build();
    }
}
