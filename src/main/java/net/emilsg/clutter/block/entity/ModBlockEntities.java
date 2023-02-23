package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.ChimneyBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<ChimneyBlockEntity> CHIMNEY;

    public static void registerBlockEntities() {
        CHIMNEY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "chimney"),
                FabricBlockEntityTypeBuilder.create(ChimneyBlockEntity::new,
                    ModBlocks.COBBLESTONE_CHIMNEY,
                    ModBlocks.BRICK_CHIMNEY,
                    ModBlocks.STONE_BRICK_CHIMNEY,
                    ModBlocks.MOSSY_STONE_BRICK_CHIMNEY,
                    ModBlocks.DEEPSLATE_BRICK_CHIMNEY,
                    ModBlocks.MUD_BRICK_CHIMNEY,
                    ModBlocks.NETHER_BRICK_CHIMNEY,
                    ModBlocks.RED_NETHER_BRICK_CHIMNEY,
                    ModBlocks.POLISHED_BLACKSTONE_BRICK_CHIMNEY,
                    ModBlocks.END_STONE_BRICK_CHIMNEY,
                    ModBlocks.DEEPSLATE_TILE_CHIMNEY)
                .build());
    }
}
