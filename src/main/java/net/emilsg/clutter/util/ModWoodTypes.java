package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class ModWoodTypes {
    public static final WoodType REDWOOD = WoodTypeRegistry.register(new Identifier(Clutter.MOD_ID, "redwood"), BlockSetType.CHERRY);
}
