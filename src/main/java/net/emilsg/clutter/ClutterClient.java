package net.emilsg.clutter;

import io.netty.buffer.Unpooled;
import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.block.entity.render.CardboardBoxBlockEntityRenderer;
import net.emilsg.clutter.block.entity.render.PlateBlockEntityRenderer;
import net.emilsg.clutter.block.entity.render.ShelfBlockEntityRenderer;
import net.emilsg.clutter.compat.trinkets.TrinketsIntegrationClient;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.client.*;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.CrimsonNewtModel;
import net.emilsg.clutter.entity.client.render.CrimsonNewtRenderer;
import net.emilsg.clutter.networking.ModMessages;
import net.emilsg.clutter.screen.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.network.PacketByteBuf;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static net.emilsg.clutter.Clutter.IS_TRINKETS_LOADED;
import static net.emilsg.clutter.block.ModBlocks.*;

public class ClutterClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if(IS_TRINKETS_LOADED) TrinketsIntegrationClient.registerTrinkets();

        this.registerColorProviders();
        this.registerEntityModelLayers();
        this.registerEntityRenderers();
        this.registerBlockEntityRenderers();
        this.registerScreenHandlers();
        this.registerConnectionEvents();

        List<Block> blocksToRender = Arrays.asList(
                FOOD_BOX,
                CARROT_FOOD_BOX,
                POTATO_FOOD_BOX,
                BEETROOT_FOOD_BOX,
                APPLE_FOOD_BOX,
                MELON_FOOD_BOX,
                SWEET_BERRY_FOOD_BOX,
                GLOW_BERRY_FOOD_BOX,
                CHORUS_FRUIT_FOOD_BOX,
                BREAD_FOOD_BOX,
                SILVER_LANTERN,
                GOLDEN_LANTERN,
                SILVER_SOUL_LANTERN,
                GOLDEN_SOUL_LANTERN,
                WOODEN_MUG,
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
                END_STONE_BRICK_CHIMNEY,
                DEEPSLATE_TILE_CHIMNEY,
                OAK_WINDOW_SILL,
                DARK_OAK_WINDOW_SILL,
                BIRCH_WINDOW_SILL,
                JUNGLE_WINDOW_SILL,
                ACACIA_WINDOW_SILL,
                SPRUCE_WINDOW_SILL,
                CHERRY_WINDOW_SILL,
                BAMBOO_WINDOW_SILL,
                CRIMSON_WINDOW_SILL,
                WARPED_WINDOW_SILL,
                MANGROVE_WINDOW_SILL,
                OAK_WALL_BOOKSHELF,
                DARK_OAK_WALL_BOOKSHELF,
                BIRCH_WALL_BOOKSHELF,
                JUNGLE_WALL_BOOKSHELF,
                ACACIA_WALL_BOOKSHELF,
                SPRUCE_WALL_BOOKSHELF,
                BAMBOO_WALL_BOOKSHELF,
                CHERRY_WALL_BOOKSHELF,
                CRIMSON_WALL_BOOKSHELF,
                WARPED_WALL_BOOKSHELF,
                MANGROVE_WALL_BOOKSHELF,
                WHITE_LAMP,
                ORANGE_LAMP,
                MAGENTA_LAMP,
                LIGHT_BLUE_LAMP,
                YELLOW_LAMP,
                LIME_LAMP,
                PINK_LAMP,
                GRAY_LAMP,
                LIGHT_GRAY_LAMP,
                CYAN_LAMP,
                PURPLE_LAMP,
                BLUE_LAMP,
                BROWN_LAMP,
                GREEN_LAMP,
                RED_LAMP,
                BLACK_LAMP,
                GOLDEN_CHAIN,
                SILVER_CHAIN,
                COPPER_CHANDELIER,
                WHITE_COPPER_CHANDELIER,
                LIGHT_GRAY_COPPER_CHANDELIER,
                GRAY_COPPER_CHANDELIER,
                BLACK_COPPER_CHANDELIER,
                BROWN_COPPER_CHANDELIER,
                RED_COPPER_CHANDELIER,
                ORANGE_COPPER_CHANDELIER,
                YELLOW_COPPER_CHANDELIER,
                LIME_COPPER_CHANDELIER,
                GREEN_COPPER_CHANDELIER,
                CYAN_COPPER_CHANDELIER,
                LIGHT_BLUE_COPPER_CHANDELIER,
                BLUE_COPPER_CHANDELIER,
                PURPLE_COPPER_CHANDELIER,
                MAGENTA_COPPER_CHANDELIER,
                PINK_COPPER_CHANDELIER,
                EXPOSED_COPPER_CHANDELIER,
                EXPOSED_WHITE_COPPER_CHANDELIER,
                EXPOSED_LIGHT_GRAY_COPPER_CHANDELIER,
                EXPOSED_GRAY_COPPER_CHANDELIER,
                EXPOSED_BLACK_COPPER_CHANDELIER,
                EXPOSED_BROWN_COPPER_CHANDELIER,
                EXPOSED_RED_COPPER_CHANDELIER,
                EXPOSED_ORANGE_COPPER_CHANDELIER,
                EXPOSED_YELLOW_COPPER_CHANDELIER,
                EXPOSED_LIME_COPPER_CHANDELIER,
                EXPOSED_GREEN_COPPER_CHANDELIER,
                EXPOSED_CYAN_COPPER_CHANDELIER,
                EXPOSED_LIGHT_BLUE_COPPER_CHANDELIER,
                EXPOSED_BLUE_COPPER_CHANDELIER,
                EXPOSED_PURPLE_COPPER_CHANDELIER,
                EXPOSED_MAGENTA_COPPER_CHANDELIER,
                EXPOSED_PINK_COPPER_CHANDELIER,
                WEATHERED_COPPER_CHANDELIER,
                WEATHERED_WHITE_COPPER_CHANDELIER,
                WEATHERED_LIGHT_GRAY_COPPER_CHANDELIER,
                WEATHERED_GRAY_COPPER_CHANDELIER,
                WEATHERED_BLACK_COPPER_CHANDELIER,
                WEATHERED_BROWN_COPPER_CHANDELIER,
                WEATHERED_RED_COPPER_CHANDELIER,
                WEATHERED_ORANGE_COPPER_CHANDELIER,
                WEATHERED_YELLOW_COPPER_CHANDELIER,
                WEATHERED_LIME_COPPER_CHANDELIER,
                WEATHERED_GREEN_COPPER_CHANDELIER,
                WEATHERED_CYAN_COPPER_CHANDELIER,
                WEATHERED_LIGHT_BLUE_COPPER_CHANDELIER,
                WEATHERED_BLUE_COPPER_CHANDELIER,
                WEATHERED_PURPLE_COPPER_CHANDELIER,
                WEATHERED_MAGENTA_COPPER_CHANDELIER,
                WEATHERED_PINK_COPPER_CHANDELIER,
                OXIDIZED_COPPER_CHANDELIER,
                OXIDIZED_WHITE_COPPER_CHANDELIER,
                OXIDIZED_LIGHT_GRAY_COPPER_CHANDELIER,
                OXIDIZED_GRAY_COPPER_CHANDELIER,
                OXIDIZED_BLACK_COPPER_CHANDELIER,
                OXIDIZED_BROWN_COPPER_CHANDELIER,
                OXIDIZED_RED_COPPER_CHANDELIER,
                OXIDIZED_ORANGE_COPPER_CHANDELIER,
                OXIDIZED_YELLOW_COPPER_CHANDELIER,
                OXIDIZED_LIME_COPPER_CHANDELIER,
                OXIDIZED_GREEN_COPPER_CHANDELIER,
                OXIDIZED_CYAN_COPPER_CHANDELIER,
                OXIDIZED_LIGHT_BLUE_COPPER_CHANDELIER,
                OXIDIZED_BLUE_COPPER_CHANDELIER,
                OXIDIZED_PURPLE_COPPER_CHANDELIER,
                OXIDIZED_MAGENTA_COPPER_CHANDELIER,
                OXIDIZED_PINK_COPPER_CHANDELIER,
                WAXED_COPPER_CHANDELIER,
                WAXED_WHITE_COPPER_CHANDELIER,
                WAXED_LIGHT_GRAY_COPPER_CHANDELIER,
                WAXED_GRAY_COPPER_CHANDELIER,
                WAXED_BLACK_COPPER_CHANDELIER,
                WAXED_BROWN_COPPER_CHANDELIER,
                WAXED_RED_COPPER_CHANDELIER,
                WAXED_ORANGE_COPPER_CHANDELIER,
                WAXED_YELLOW_COPPER_CHANDELIER,
                WAXED_LIME_COPPER_CHANDELIER,
                WAXED_GREEN_COPPER_CHANDELIER,
                WAXED_CYAN_COPPER_CHANDELIER,
                WAXED_LIGHT_BLUE_COPPER_CHANDELIER,
                WAXED_BLUE_COPPER_CHANDELIER,
                WAXED_PURPLE_COPPER_CHANDELIER,
                WAXED_MAGENTA_COPPER_CHANDELIER,
                WAXED_PINK_COPPER_CHANDELIER,
                WAXED_EXPOSED_COPPER_CHANDELIER,
                WAXED_EXPOSED_WHITE_COPPER_CHANDELIER,
                WAXED_EXPOSED_LIGHT_GRAY_COPPER_CHANDELIER,
                WAXED_EXPOSED_GRAY_COPPER_CHANDELIER,
                WAXED_EXPOSED_BLACK_COPPER_CHANDELIER,
                WAXED_EXPOSED_BROWN_COPPER_CHANDELIER,
                WAXED_EXPOSED_RED_COPPER_CHANDELIER,
                WAXED_EXPOSED_ORANGE_COPPER_CHANDELIER,
                WAXED_EXPOSED_YELLOW_COPPER_CHANDELIER,
                WAXED_EXPOSED_LIME_COPPER_CHANDELIER,
                WAXED_EXPOSED_GREEN_COPPER_CHANDELIER,
                WAXED_EXPOSED_CYAN_COPPER_CHANDELIER,
                WAXED_EXPOSED_LIGHT_BLUE_COPPER_CHANDELIER,
                WAXED_EXPOSED_BLUE_COPPER_CHANDELIER,
                WAXED_EXPOSED_PURPLE_COPPER_CHANDELIER,
                WAXED_EXPOSED_MAGENTA_COPPER_CHANDELIER,
                WAXED_EXPOSED_PINK_COPPER_CHANDELIER,
                WAXED_WEATHERED_COPPER_CHANDELIER,
                WAXED_WEATHERED_WHITE_COPPER_CHANDELIER,
                WAXED_WEATHERED_LIGHT_GRAY_COPPER_CHANDELIER,
                WAXED_WEATHERED_GRAY_COPPER_CHANDELIER,
                WAXED_WEATHERED_BLACK_COPPER_CHANDELIER,
                WAXED_WEATHERED_BROWN_COPPER_CHANDELIER,
                WAXED_WEATHERED_RED_COPPER_CHANDELIER,
                WAXED_WEATHERED_ORANGE_COPPER_CHANDELIER,
                WAXED_WEATHERED_YELLOW_COPPER_CHANDELIER,
                WAXED_WEATHERED_LIME_COPPER_CHANDELIER,
                WAXED_WEATHERED_GREEN_COPPER_CHANDELIER,
                WAXED_WEATHERED_CYAN_COPPER_CHANDELIER,
                WAXED_WEATHERED_LIGHT_BLUE_COPPER_CHANDELIER,
                WAXED_WEATHERED_BLUE_COPPER_CHANDELIER,
                WAXED_WEATHERED_PURPLE_COPPER_CHANDELIER,
                WAXED_WEATHERED_MAGENTA_COPPER_CHANDELIER,
                WAXED_WEATHERED_PINK_COPPER_CHANDELIER,
                WAXED_OXIDIZED_COPPER_CHANDELIER,
                WAXED_OXIDIZED_WHITE_COPPER_CHANDELIER,
                WAXED_OXIDIZED_LIGHT_GRAY_COPPER_CHANDELIER,
                WAXED_OXIDIZED_GRAY_COPPER_CHANDELIER,
                WAXED_OXIDIZED_BLACK_COPPER_CHANDELIER,
                WAXED_OXIDIZED_BROWN_COPPER_CHANDELIER,
                WAXED_OXIDIZED_RED_COPPER_CHANDELIER,
                WAXED_OXIDIZED_ORANGE_COPPER_CHANDELIER,
                WAXED_OXIDIZED_YELLOW_COPPER_CHANDELIER,
                WAXED_OXIDIZED_LIME_COPPER_CHANDELIER,
                WAXED_OXIDIZED_GREEN_COPPER_CHANDELIER,
                WAXED_OXIDIZED_CYAN_COPPER_CHANDELIER,
                WAXED_OXIDIZED_LIGHT_BLUE_COPPER_CHANDELIER,
                WAXED_OXIDIZED_BLUE_COPPER_CHANDELIER,
                WAXED_OXIDIZED_PURPLE_COPPER_CHANDELIER,
                WAXED_OXIDIZED_MAGENTA_COPPER_CHANDELIER,
                WAXED_OXIDIZED_PINK_COPPER_CHANDELIER,
                GOLDEN_CHANDELIER,
                WHITE_GOLDEN_CHANDELIER,
                LIGHT_GRAY_GOLDEN_CHANDELIER,
                GRAY_GOLDEN_CHANDELIER,
                BLACK_GOLDEN_CHANDELIER,
                BROWN_GOLDEN_CHANDELIER,
                RED_GOLDEN_CHANDELIER,
                ORANGE_GOLDEN_CHANDELIER,
                YELLOW_GOLDEN_CHANDELIER,
                LIME_GOLDEN_CHANDELIER,
                GREEN_GOLDEN_CHANDELIER,
                CYAN_GOLDEN_CHANDELIER,
                LIGHT_BLUE_GOLDEN_CHANDELIER,
                BLUE_GOLDEN_CHANDELIER,
                PURPLE_GOLDEN_CHANDELIER,
                MAGENTA_GOLDEN_CHANDELIER,
                PINK_GOLDEN_CHANDELIER,
                IRON_CHANDELIER,
                WHITE_IRON_CHANDELIER,
                LIGHT_GRAY_IRON_CHANDELIER,
                GRAY_IRON_CHANDELIER,
                BLACK_IRON_CHANDELIER,
                BROWN_IRON_CHANDELIER,
                RED_IRON_CHANDELIER,
                ORANGE_IRON_CHANDELIER,
                YELLOW_IRON_CHANDELIER,
                LIME_IRON_CHANDELIER,
                GREEN_IRON_CHANDELIER,
                CYAN_IRON_CHANDELIER,
                LIGHT_BLUE_IRON_CHANDELIER,
                BLUE_IRON_CHANDELIER,
                PURPLE_IRON_CHANDELIER,
                MAGENTA_IRON_CHANDELIER,
                PINK_IRON_CHANDELIER,
                SILVER_CHANDELIER,
                WHITE_SILVER_CHANDELIER,
                LIGHT_GRAY_SILVER_CHANDELIER,
                GRAY_SILVER_CHANDELIER,
                BLACK_SILVER_CHANDELIER,
                BROWN_SILVER_CHANDELIER,
                RED_SILVER_CHANDELIER,
                ORANGE_SILVER_CHANDELIER,
                YELLOW_SILVER_CHANDELIER,
                LIME_SILVER_CHANDELIER,
                GREEN_SILVER_CHANDELIER,
                CYAN_SILVER_CHANDELIER,
                LIGHT_BLUE_SILVER_CHANDELIER,
                BLUE_SILVER_CHANDELIER,
                PURPLE_SILVER_CHANDELIER,
                MAGENTA_SILVER_CHANDELIER,
                PINK_SILVER_CHANDELIER,
                COTTON_CROP,
                HOPS_CROP,
                OXIDIZED_COPPER_BARS,
                WEATHERED_COPPER_BARS,
                EXPOSED_COPPER_BARS,
                COPPER_BARS,
                WAXED_OXIDIZED_COPPER_BARS,
                WAXED_WEATHERED_COPPER_BARS,
                WAXED_EXPOSED_COPPER_BARS,
                WAXED_COPPER_BARS,
                COPPER_CHAIN,
                EXPOSED_COPPER_CHAIN,
                WEATHERED_COPPER_CHAIN,
                OXIDIZED_COPPER_CHAIN,
                WAXED_COPPER_CHAIN,
                WAXED_EXPOSED_COPPER_CHAIN,
                WAXED_WEATHERED_COPPER_CHAIN,
                WAXED_OXIDIZED_COPPER_CHAIN,
                COPPER_DOOR,
                EXPOSED_COPPER_DOOR,
                WEATHERED_COPPER_DOOR,
                OXIDIZED_COPPER_DOOR,
                WAXED_COPPER_DOOR,
                WAXED_EXPOSED_COPPER_DOOR,
                WAXED_WEATHERED_COPPER_DOOR,
                WAXED_OXIDIZED_COPPER_DOOR,
                COPPER_LANTERN,
                EXPOSED_COPPER_LANTERN,
                WEATHERED_COPPER_LANTERN,
                OXIDIZED_COPPER_LANTERN,
                WAXED_COPPER_LANTERN,
                WAXED_EXPOSED_COPPER_LANTERN,
                WAXED_WEATHERED_COPPER_LANTERN,
                WAXED_OXIDIZED_COPPER_LANTERN,
                COPPER_SOUL_LANTERN,
                EXPOSED_COPPER_SOUL_LANTERN,
                WEATHERED_COPPER_SOUL_LANTERN,
                OXIDIZED_COPPER_SOUL_LANTERN,
                WAXED_COPPER_SOUL_LANTERN,
                WAXED_EXPOSED_COPPER_SOUL_LANTERN,
                WAXED_WEATHERED_COPPER_SOUL_LANTERN,
                WAXED_OXIDIZED_COPPER_SOUL_LANTERN,
                COPPER_TRAPDOOR,
                EXPOSED_COPPER_TRAPDOOR,
                WEATHERED_COPPER_TRAPDOOR,
                OXIDIZED_COPPER_TRAPDOOR,
                WAXED_COPPER_TRAPDOOR,
                WAXED_EXPOSED_COPPER_TRAPDOOR,
                WAXED_WEATHERED_COPPER_TRAPDOOR,
                WAXED_OXIDIZED_COPPER_TRAPDOOR,
                SILVER_DOOR,
                WHITE_KITCHEN_CURTAINS,
                LIGHT_GRAY_KITCHEN_CURTAINS,
                GRAY_KITCHEN_CURTAINS,
                BLACK_KITCHEN_CURTAINS,
                BROWN_KITCHEN_CURTAINS,
                RED_KITCHEN_CURTAINS,
                ORANGE_KITCHEN_CURTAINS,
                YELLOW_KITCHEN_CURTAINS,
                LIME_KITCHEN_CURTAINS,
                GREEN_KITCHEN_CURTAINS,
                CYAN_KITCHEN_CURTAINS,
                LIGHT_BLUE_KITCHEN_CURTAINS,
                BLUE_KITCHEN_CURTAINS,
                PURPLE_KITCHEN_CURTAINS,
                MAGENTA_KITCHEN_CURTAINS,
                PINK_KITCHEN_CURTAINS,
                LONG_WHITE_KITCHEN_CURTAINS,
                LONG_LIGHT_GRAY_KITCHEN_CURTAINS,
                LONG_GRAY_KITCHEN_CURTAINS,
                LONG_BLACK_KITCHEN_CURTAINS,
                LONG_BROWN_KITCHEN_CURTAINS,
                LONG_RED_KITCHEN_CURTAINS,
                LONG_ORANGE_KITCHEN_CURTAINS,
                LONG_YELLOW_KITCHEN_CURTAINS,
                LONG_LIME_KITCHEN_CURTAINS,
                LONG_GREEN_KITCHEN_CURTAINS,
                LONG_CYAN_KITCHEN_CURTAINS,
                LONG_LIGHT_BLUE_KITCHEN_CURTAINS,
                LONG_BLUE_KITCHEN_CURTAINS,
                LONG_PURPLE_KITCHEN_CURTAINS,
                LONG_MAGENTA_KITCHEN_CURTAINS,
                LONG_PINK_KITCHEN_CURTAINS,
                PRIDE_KITCHEN_CURTAINS,
                GOLDEN_DOOR,
                GOLDEN_TRAPDOOR,
                SILVER_TRAPDOOR,
                OAK_TRELLIS,
                DARK_OAK_TRELLIS,
                BIRCH_TRELLIS,
                JUNGLE_TRELLIS,
                ACACIA_TRELLIS,
                SPRUCE_TRELLIS,
                BAMBOO_TRELLIS,
                CHERRY_TRELLIS,
                CRIMSON_TRELLIS,
                WARPED_TRELLIS,
                MANGROVE_TRELLIS,
                BONFIRE,
                SOUL_BONFIRE,
                CATTAILS,
                THORNBLOOM,
                THORNBLOOM_CROP,
                LUSH_MOSS,
                KIWI_LEAVES,
                RIPE_KIWI_LEAVES,
                KIWI_TREE_SAPLING,
                KIWI_CROP,
                SHEEP_PLUSHIE,
                TALL_WHITE_CURTAINS,
                TALL_LIGHT_GRAY_CURTAINS,
                TALL_GRAY_CURTAINS,
                TALL_BLACK_CURTAINS,
                TALL_BROWN_CURTAINS,
                TALL_RED_CURTAINS,
                TALL_ORANGE_CURTAINS,
                TALL_YELLOW_CURTAINS,
                TALL_LIME_CURTAINS,
                TALL_GREEN_CURTAINS,
                TALL_CYAN_CURTAINS,
                TALL_LIGHT_BLUE_CURTAINS,
                TALL_BLUE_CURTAINS,
                TALL_PURPLE_CURTAINS,
                TALL_MAGENTA_CURTAINS,
                TALL_PINK_CURTAINS,
                GIANT_LILY_PAD,
                GIANT_LILY_PAD_SEEDLING,
                SMALL_LILY_PADS,
                WINE_GLASS,
                PLATE,
                SMALL_ONYX_BUD,
                MEDIUM_ONYX_BUD,
                LARGE_ONYX_BUD,
                ONYX_CLUSTER,
                GREEN_FIRE,
                GLOWLILY_CROP,
                GLOWLILY,
                //IRON_CANDLE_HOLDER,
                //SILVER_CANDLE_HOLDER,
                //GOLDEN_CANDLE_HOLDER,
                //COPPER_CANDLE_HOLDER,
                //EXPOSED_COPPER_CANDLE_HOLDER,
                //WEATHERED_COPPER_CANDLE_HOLDER,
                //OXIDIZED_COPPER_CANDLE_HOLDER,
                //WAXED_COPPER_CANDLE_HOLDER,
                //WAXED_EXPOSED_COPPER_CANDLE_HOLDER,
                //WAXED_WEATHERED_COPPER_CANDLE_HOLDER,
                //WAXED_OXIDIZED_COPPER_CANDLE_HOLDER,
                PRISMARINE_TORCH,
                PRISMARINE_WALL_TORCH,
                AQUATIC_TORCH,
                AQUATIC_WALL_TORCH,
                EXPOSED_AQUATIC_TORCH,
                EXPOSED_AQUATIC_WALL_TORCH,
                WEATHERED_AQUATIC_TORCH,
                WEATHERED_AQUATIC_WALL_TORCH,
                OXIDIZED_AQUATIC_TORCH,
                OXIDIZED_AQUATIC_WALL_TORCH,
                WAXED_AQUATIC_TORCH,
                WAXED_AQUATIC_WALL_TORCH,
                WAXED_EXPOSED_AQUATIC_TORCH,
                WAXED_EXPOSED_AQUATIC_WALL_TORCH,
                WAXED_WEATHERED_AQUATIC_TORCH,
                WAXED_WEATHERED_AQUATIC_WALL_TORCH,
                WAXED_OXIDIZED_AQUATIC_TORCH,
                WAXED_OXIDIZED_AQUATIC_WALL_TORCH,
                DEAD_CUP_CORAL,
                CUP_CORAL,
                DEAD_CUP_CORAL_FAN,
                CUP_CORAL_FAN,
                DEAD_CUP_CORAL_WALL_FAN,
                CUP_CORAL_WALL_FAN,
                DEAD_GHOST_CORAL,
                GHOST_CORAL,
                DEAD_GHOST_CORAL_FAN,
                GHOST_CORAL_FAN,
                DEAD_GHOST_CORAL_WALL_FAN,
                GHOST_CORAL_WALL_FAN,
                DEAD_SLATE_CORAL,
                SLATE_CORAL,
                DEAD_SLATE_CORAL_FAN,
                SLATE_CORAL_FAN,
                DEAD_SLATE_CORAL_WALL_FAN,
                SLATE_CORAL_WALL_FAN,
                DEAD_STONE_CORAL,
                STONE_CORAL,
                DEAD_STONE_CORAL_FAN,
                STONE_CORAL_FAN,
                DEAD_STONE_CORAL_WALL_FAN,
                STONE_CORAL_WALL_FAN,
                DEAD_THORN_CORAL,
                THORN_CORAL,
                DEAD_THORN_CORAL_FAN,
                THORN_CORAL_FAN,
                DEAD_THORN_CORAL_WALL_FAN,
                THORN_CORAL_WALL_FAN,
                DEAD_COCOA_CORAL,
                COCOA_CORAL,
                DEAD_COCOA_CORAL_FAN,
                COCOA_CORAL_FAN,
                DEAD_COCOA_CORAL_WALL_FAN,
                COCOA_CORAL_WALL_FAN,
                DEAD_PASSION_CORAL,
                PASSION_CORAL,
                DEAD_PASSION_CORAL_FAN,
                PASSION_CORAL_FAN,
                DEAD_PASSION_CORAL_WALL_FAN,
                PASSION_CORAL_WALL_FAN,
                DEAD_TOXIC_CORAL,
                TOXIC_CORAL,
                DEAD_TOXIC_CORAL_FAN,
                TOXIC_CORAL_FAN,
                DEAD_TOXIC_CORAL_WALL_FAN,
                TOXIC_CORAL_WALL_FAN,
                DEAD_GEM_CORAL,
                GEM_CORAL,
                DEAD_GEM_CORAL_FAN,
                GEM_CORAL_FAN,
                DEAD_GEM_CORAL_WALL_FAN,
                GEM_CORAL_WALL_FAN,
                DEAD_DIAMOND_CORAL,
                DIAMOND_CORAL,
                DEAD_DIAMOND_CORAL_FAN,
                DIAMOND_CORAL_FAN,
                DEAD_DIAMOND_CORAL_WALL_FAN,
                DIAMOND_CORAL_WALL_FAN,
                DEAD_ANCHOR_CORAL,
                ANCHOR_CORAL,
                DEAD_ANCHOR_CORAL_FAN,
                ANCHOR_CORAL_FAN,
                DEAD_ANCHOR_CORAL_WALL_FAN,
                ANCHOR_CORAL_WALL_FAN,
                RED_PRESENT
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), blocksToRender.toArray(new Block[0]));

        ModMessages.registerS2CPackets();
    }

    private void registerColorProviders(){
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                        (world != null && pos != null) ? BiomeColors.getFoliageColor(world, pos)
                        : FoliageColors.getDefaultColor(),
                RIPE_KIWI_LEAVES,
                KIWI_LEAVES
        );

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                        (world != null && pos != null) ? BiomeColors.getGrassColor(world, pos)
                                : FoliageColors.getDefaultColor(),
                CATTAILS
        );


        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                Objects.requireNonNull(ColorProviderRegistry.BLOCK.get(Blocks.LILY_PAD)).getColor(state, world, pos, tintIndex),
                GIANT_LILY_PAD,
                GIANT_LILY_PAD_SEEDLING,
                SMALL_LILY_PADS
        );

        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                Objects.requireNonNull(ColorProviderRegistry.ITEM.get(Blocks.LILY_PAD)).getColor(stack, tintIndex),
                SMALL_LILY_PADS,
                GIANT_LILY_PAD
        );


        ColorProviderRegistry.ITEM.register((stack, tintIndex) ->
                        FoliageColors.getDefaultColor(),
                RIPE_KIWI_LEAVES,
                KIWI_LEAVES
        );
    }

    private void registerConnectionEvents() {
        ClientPlayConnectionEvents.JOIN.register((handler, client, isConnected) -> {
            handler.sendPacket(ClientPlayNetworking.createC2SPacket(ModMessages.VERSION_HANDSHAKE_PACKET_ID, new PacketByteBuf(Unpooled.buffer()).writeString(Clutter.MOD_VERSION)));
        });
    }

    private void registerEntityModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.CRIMSON_NEWT, CrimsonNewtModel::getTexturedModelData);
    }

    private void registerEntityRenderers() {
        EntityRendererRegistry.register(ModEntities.SEAT, EmptySeatRenderer::new);
        EntityRendererRegistry.register(ModEntities.BUTTERFLY, ButterflyRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHAMELEON, ChameleonRenderer::new);
        EntityRendererRegistry.register(ModEntities.ECHOFIN, EchofinRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOSSBLOOM, MossbloomRenderer::new);
        EntityRendererRegistry.register(ModEntities.KIWI_BIRD, KiwiBirdRenderer::new);
        EntityRendererRegistry.register(ModEntities.EMPEROR_PENGUIN, EmperorPenguinRenderer::new);
        EntityRendererRegistry.register(ModEntities.BEAVER, BeaverRenderer::new);
        EntityRendererRegistry.register(ModEntities.CAPYBARA, CapybaraRenderer::new);

        EntityRendererRegistry.register(ModEntities.CRIMSON_NEWT, CrimsonNewtRenderer::new);
    }

    private void registerBlockEntityRenderers() {
        BlockEntityRendererFactories.register(ModBlockEntities.SHELF, ShelfBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.PLATE, PlateBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.CARDBOARD_BOX, CardboardBoxBlockEntityRenderer::new);
    }

    private void registerScreenHandlers() {
        HandledScreens.register(ModScreenHandlers.BRICK_KILN_SCREEN_HANDLER, BrickKilnScreen::new);
        HandledScreens.register(ModScreenHandlers.CARDBOARD_BOX_SCREEN_HANDLER, CardboardBoxScreen::new);
        HandledScreens.register(ModScreenHandlers.WALL_BOOKSHELF_SCREEN_HANDLER, WallBookshelfScreen::new);
        HandledScreens.register(ModScreenHandlers.PRESENT_SCREEN_HANDLER, PresentScreen::new);

    }
}