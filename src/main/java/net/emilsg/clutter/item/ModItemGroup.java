package net.emilsg.clutter.item;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup CLUTTER = FabricItemGroupBuilder.build(
            new Identifier(Clutter.MOD_ID, "clutter"), () -> new ItemStack(ModBlocks.COPPER_LANTERN));
}
