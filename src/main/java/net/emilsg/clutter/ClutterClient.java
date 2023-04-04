package net.emilsg.clutter;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.emilsg.clutter.util.ModSit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

import static net.emilsg.clutter.block.ModBlocks.*;

public class ClutterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModSit.SEAT, EmptyRenderer::new);

        List<Block> blocksToRender = Arrays.asList(
                FOOD_BOX,
                CARROT_FOOD_BOX,
                POTATO_FOOD_BOX,
                BEETROOT_FOOD_BOX,
                APPLE_FOOD_BOX,
                SILVER_LANTERN,
                GOLDEN_LANTERN,
                SILVER_SOUL_LANTERN,
                GOLDEN_SOUL_LANTERN,
                WOODEN_MUG,
                COBBLESTONE_CHIMNEY,
                BRICK_CHIMNEY,
                STONE_BRICK_CHIMNEY,
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
                CRIMSON_WINDOW_SILL,
                WARPED_WINDOW_SILL,
                MANGROVE_WINDOW_SILL,
                OAK_WALL_BOOKSHELF,
                DARK_OAK_WALL_BOOKSHELF,
                BIRCH_WALL_BOOKSHELF,
                JUNGLE_WALL_BOOKSHELF,
                ACACIA_WALL_BOOKSHELF,
                SPRUCE_WALL_BOOKSHELF,
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
                GOLDEN_CHANDELIER,
                GOLDEN_CHAIN,
                SILVER_CHAIN,
                IRON_CHANDELIER,
                SILVER_CHANDELIER,
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
                COPPER_CHANDELIER,
                EXPOSED_COPPER_CHANDELIER,
                WEATHERED_COPPER_CHANDELIER,
                OXIDIZED_COPPER_CHANDELIER,
                WAXED_COPPER_CHANDELIER,
                WAXED_EXPOSED_COPPER_CHANDELIER,
                WAXED_WEATHERED_COPPER_CHANDELIER,
                WAXED_OXIDIZED_COPPER_CHANDELIER,
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
                CRIMSON_TRELLIS,
                WARPED_TRELLIS,
                MANGROVE_TRELLIS,
                SINK
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), blocksToRender.toArray(new Block[blocksToRender.size()]));
    }


    private static class EmptyRenderer extends EntityRenderer<SeatEntity> {
        protected EmptyRenderer(EntityRendererFactory.Context ctx) {
            super(ctx);
        }

        @Override
        public boolean shouldRender(SeatEntity entity, Frustum frustum, double d, double e, double f) {
            return false;
        }

        @Override
        public Identifier getTexture(SeatEntity entity) {
            return null;
        }
    }

}
