package net.emilsg.clutter.block;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.entity.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.emilsg.clutter.block.ModBlocks.*;

public class ModBlockEntities {
    public static BlockEntityType<BrickKilnFurnaceBlockEntity> BRICK_KILN_BLOCK_ENTITY;
    public static BlockEntityType<ChimneyBlockEntity> CHIMNEY;
    public static BlockEntityType<BonfireBlockEntity> BONFIRE;
    public static BlockEntityType<WallBookshelfInventoryBlockEntity> WALL_BOOKSHELF;
    public static BlockEntityType<WallCupboardInventoryBlockEntity> WALL_CUPBOARD;
    public static BlockEntityType<ShelfInventoryBlockEntity> SHELF;
    public static BlockEntityType<CupboardInventoryBlockEntity> CUPBOARD;
    public static BlockEntityType<MailBoxInventoryBlockEntity> MAILBOX;
    public static BlockEntityType<CardboardBoxInventoryBlockEntity> CARDBOARD_BOX;
    public static BlockEntityType<PresentInventoryBlockEntity> PRESENT;
    public static BlockEntityType<BunkBedBlockEntity> MOD_BUNK_BED_BLOCK_ENTITY;


    public static void registerBlockEntities() {
        CHIMNEY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "chimney"),
                BlockEntityType.Builder.create(ChimneyBlockEntity::new,
                        COBBLESTONE_CHIMNEY,
                        BRICK_CHIMNEY,
                        STONE_BRICK_CHIMNEY,
                        PURPUR_CHIMNEY,
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

        WALL_BOOKSHELF = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "wall_bookshelf"),
                BlockEntityType.Builder.create(WallBookshelfInventoryBlockEntity::new,
                        OAK_WALL_BOOKSHELF,
                        DARK_OAK_WALL_BOOKSHELF,
                        BIRCH_WALL_BOOKSHELF,
                        JUNGLE_WALL_BOOKSHELF,
                        ACACIA_WALL_BOOKSHELF,
                        SPRUCE_WALL_BOOKSHELF,
                        CRIMSON_WALL_BOOKSHELF,
                        WARPED_WALL_BOOKSHELF,
                        MANGROVE_WALL_BOOKSHELF,
                        BAMBOO_WALL_BOOKSHELF,
                        CHERRY_WALL_BOOKSHELF,
                        REDWOOD_WALL_BOOKSHELF).build());

        WALL_CUPBOARD = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "wall_cupboard"),
                BlockEntityType.Builder.create(WallCupboardInventoryBlockEntity::new,
                        OAK_WALL_CUPBOARD,
                        DARK_OAK_WALL_CUPBOARD,
                        BIRCH_WALL_CUPBOARD,
                        JUNGLE_WALL_CUPBOARD,
                        ACACIA_WALL_CUPBOARD,
                        SPRUCE_WALL_CUPBOARD,
                        CRIMSON_WALL_CUPBOARD,
                        WARPED_WALL_CUPBOARD,
                        MANGROVE_WALL_CUPBOARD,
                        BAMBOO_WALL_CUPBOARD,
                        CHERRY_WALL_CUPBOARD,
                        REDWOOD_WALL_CUPBOARD).build());

        CUPBOARD = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "cupboard"),
                BlockEntityType.Builder.create(CupboardInventoryBlockEntity::new,
                        OAK_CUPBOARD,
                        DARK_OAK_CUPBOARD,
                        BIRCH_CUPBOARD,
                        JUNGLE_CUPBOARD,
                        ACACIA_CUPBOARD,
                        SPRUCE_CUPBOARD,
                        CRIMSON_CUPBOARD,
                        WARPED_CUPBOARD,
                        MANGROVE_CUPBOARD,
                        BAMBOO_CUPBOARD,
                        CHERRY_CUPBOARD,
                        REDWOOD_CUPBOARD).build());

        SHELF = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "shelf"),
                BlockEntityType.Builder.create(ShelfInventoryBlockEntity::new,
                        OAK_SHELF,
                        DARK_OAK_SHELF,
                        BIRCH_SHELF,
                        JUNGLE_SHELF,
                        ACACIA_SHELF,
                        SPRUCE_SHELF,
                        CRIMSON_SHELF,
                        WARPED_SHELF,
                        MANGROVE_SHELF,
                        BAMBOO_SHELF,
                        CHERRY_SHELF,
                        REDWOOD_SHELF).build());

        BONFIRE = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "bonfire"),
                BlockEntityType.Builder.create(BonfireBlockEntity::new,
                        ModBlocks.BONFIRE, SOUL_BONFIRE).build());

        BRICK_KILN_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "brick_kiln_block_entity"),
                BlockEntityType.Builder.create(BrickKilnFurnaceBlockEntity::new,
                        ModBlocks.BRICK_KILN).build(null));

        MAILBOX = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "mailbox"),
                BlockEntityType.Builder.create(MailBoxInventoryBlockEntity::new,
                        ModBlocks.MAILBOX).build());

        CARDBOARD_BOX = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "cardboard_box"),
                BlockEntityType.Builder.create(CardboardBoxInventoryBlockEntity::new,
                        ModBlocks.CARDBOARD_BOX).build());

        PRESENT = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "present"),
                BlockEntityType.Builder.create(PresentInventoryBlockEntity::new,
                        ModBlocks.RED_PRESENT).build());

        MOD_BUNK_BED_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(Clutter.MOD_ID, "bunk_bed_entity"),
                BlockEntityType.Builder.create(BunkBedBlockEntity::new,
                        ModBlocks.WHITE_BUNK_BED,
                        ModBlocks.LIGHT_GRAY_BUNK_BED,
                        ModBlocks.GRAY_BUNK_BED,
                        ModBlocks.BLACK_BUNK_BED,
                        ModBlocks.BROWN_BUNK_BED,
                        ModBlocks.RED_BUNK_BED,
                        ModBlocks.ORANGE_BUNK_BED,
                        ModBlocks.YELLOW_BUNK_BED,
                        ModBlocks.LIME_BUNK_BED,
                        ModBlocks.GREEN_BUNK_BED,
                        ModBlocks.CYAN_BUNK_BED,
                        ModBlocks.LIGHT_BLUE_BUNK_BED,
                        ModBlocks.BLUE_BUNK_BED,
                        ModBlocks.PURPLE_BUNK_BED,
                        ModBlocks.MAGENTA_BUNK_BED,
                        ModBlocks.PINK_BUNK_BED
                ).build());
    }
}
