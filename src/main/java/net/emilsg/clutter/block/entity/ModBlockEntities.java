package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.Clutter;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.emilsg.clutter.block.ModBlocks.*;

public class ModBlockEntities {
    public static BlockEntityType<ChimneyBlockEntity> CHIMNEY;
    public static BlockEntityType<WallBookshelfInventoryBlockEntity> WALL_BOOKSHELF;
    public static BlockEntityType<WallCupboardInventoryBlockEntity> WALL_CUPBOARD;

    public static void registerBlockEntities() {
        CHIMNEY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "chimney"),
                FabricBlockEntityTypeBuilder.create(ChimneyBlockEntity::new,
                                COBBLESTONE_CHIMNEY,
                                BRICK_CHIMNEY,
                                STONE_BRICK_CHIMNEY,
                                MOSSY_STONE_BRICK_CHIMNEY,
                                DEEPSLATE_BRICK_CHIMNEY,
                                MUD_BRICK_CHIMNEY,
                                NETHER_BRICK_CHIMNEY,
                                RED_NETHER_BRICK_CHIMNEY,
                                POLISHED_BLACKSTONE_BRICK_CHIMNEY,
                                BLACKSTONE_CHIMNEY,
                                DIORITE_CHIMNEY,
                                ANDESITE_CHIMNEY,
                                GRANITE_CHIMNEY,
                                END_STONE_BRICK_CHIMNEY,
                                DEEPSLATE_TILE_CHIMNEY).build());

        WALL_BOOKSHELF = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "wall_bookshelf"),
                FabricBlockEntityTypeBuilder.create(WallBookshelfInventoryBlockEntity::new,
                        OAK_WALL_BOOKSHELF,
                        DARK_OAK_WALL_BOOKSHELF,
                        BIRCH_WALL_BOOKSHELF,
                        JUNGLE_WALL_BOOKSHELF,
                        ACACIA_WALL_BOOKSHELF,
                        SPRUCE_WALL_BOOKSHELF,
                        CRIMSON_WALL_BOOKSHELF,
                        WARPED_WALL_BOOKSHELF,
                        MANGROVE_WALL_BOOKSHELF).build());

        WALL_CUPBOARD = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "wall_cupboard"),
                FabricBlockEntityTypeBuilder.create(WallCupboardInventoryBlockEntity::new,
                        OAK_WALL_CUPBOARD).build());
    }
}
