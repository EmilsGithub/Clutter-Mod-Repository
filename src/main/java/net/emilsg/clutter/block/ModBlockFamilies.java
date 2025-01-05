package net.emilsg.clutter.block;

import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;

public class ModBlockFamilies {
    public static final BlockFamily REDWOOD = BlockFamilies.register(ModBlocks.REDWOOD_PLANKS)
            .button(ModBlocks.REDWOOD_BUTTON)
            .fence(ModBlocks.REDWOOD_FENCE)
            .fenceGate(ModBlocks.REDWOOD_FENCE_GATE)
            .pressurePlate(ModBlocks.REDWOOD_PRESSURE_PLATE)
            .sign(ModBlocks.REDWOOD_SIGN, ModBlocks.REDWOOD_WALL_SIGN)
            .slab(ModBlocks.REDWOOD_SLAB)
            .stairs(ModBlocks.REDWOOD_STAIRS)
            .door(ModBlocks.REDWOOD_DOOR)
            .trapdoor(ModBlocks.REDWOOD_TRAPDOOR)
            .group("wooden")
            .unlockCriterionName("has_planks")
            .build();
}
