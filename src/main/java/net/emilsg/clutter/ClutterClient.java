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

public class ClutterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModSit.SEAT, EmptyRenderer::new);

        List<Block> blocksToRender = Arrays.asList(
                ModBlocks.FOOD_BOX,
                ModBlocks.CARROT_FOOD_BOX,
                ModBlocks.POTATO_FOOD_BOX,
                ModBlocks.BEETROOT_FOOD_BOX,
                ModBlocks.APPLE_FOOD_BOX,
                ModBlocks.SILVER_LANTERN,
                ModBlocks.GOLDEN_LANTERN,
                ModBlocks.SILVER_SOUL_LANTERN,
                ModBlocks.GOLDEN_SOUL_LANTERN,
                ModBlocks.WOODEN_MUG,
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
                ModBlocks.DEEPSLATE_TILE_CHIMNEY,
                ModBlocks.OAK_WINDOW_SILL,
                ModBlocks.DARK_OAK_WINDOW_SILL,
                ModBlocks.BIRCH_WINDOW_SILL,
                ModBlocks.JUNGLE_WINDOW_SILL,
                ModBlocks.ACACIA_WINDOW_SILL,
                ModBlocks.SPRUCE_WINDOW_SILL,
                ModBlocks.CRIMSON_WINDOW_SILL,
                ModBlocks.WARPED_WINDOW_SILL,
                ModBlocks.MANGROVE_WINDOW_SILL,
                ModBlocks.OAK_WALL_BOOKSHELF,
                ModBlocks.DARK_OAK_WALL_BOOKSHELF,
                ModBlocks.BIRCH_WALL_BOOKSHELF,
                ModBlocks.JUNGLE_WALL_BOOKSHELF,
                ModBlocks.ACACIA_WALL_BOOKSHELF,
                ModBlocks.SPRUCE_WALL_BOOKSHELF,
                ModBlocks.CRIMSON_WALL_BOOKSHELF,
                ModBlocks.WARPED_WALL_BOOKSHELF,
                ModBlocks.MANGROVE_WALL_BOOKSHELF,
                ModBlocks.WHITE_LAMP,
                ModBlocks.ORANGE_LAMP,
                ModBlocks.MAGENTA_LAMP,
                ModBlocks.LIGHT_BLUE_LAMP,
                ModBlocks.YELLOW_LAMP,
                ModBlocks.LIME_LAMP,
                ModBlocks.PINK_LAMP,
                ModBlocks.GRAY_LAMP,
                ModBlocks.LIGHT_GRAY_LAMP,
                ModBlocks.CYAN_LAMP,
                ModBlocks.PURPLE_LAMP,
                ModBlocks.BLUE_LAMP,
                ModBlocks.BROWN_LAMP,
                ModBlocks.GREEN_LAMP,
                ModBlocks.RED_LAMP,
                ModBlocks.BLACK_LAMP,
                ModBlocks.GOLDEN_CHANDELIER,
                ModBlocks.GOLDEN_CHAIN,
                ModBlocks.SILVER_CHAIN,
                ModBlocks.IRON_CHANDELIER,
                ModBlocks.SILVER_CHANDELIER,
                ModBlocks.COTTON_CROP,
                ModBlocks.HOPS_CROP,
                ModBlocks.OXIDIZED_COPPER_BARS,
                ModBlocks.WEATHERED_COPPER_BARS,
                ModBlocks.EXPOSED_COPPER_BARS,
                ModBlocks.COPPER_BARS,
                ModBlocks.WAXED_OXIDIZED_COPPER_BARS,
                ModBlocks.WAXED_WEATHERED_COPPER_BARS,
                ModBlocks.WAXED_EXPOSED_COPPER_BARS,
                ModBlocks.WAXED_COPPER_BARS,
                ModBlocks.COPPER_CHANDELIER,
                ModBlocks.EXPOSED_COPPER_CHANDELIER,
                ModBlocks.WEATHERED_COPPER_CHANDELIER,
                ModBlocks.OXIDIZED_COPPER_CHANDELIER,
                ModBlocks.WAXED_COPPER_CHANDELIER,
                ModBlocks.WAXED_EXPOSED_COPPER_CHANDELIER,
                ModBlocks.WAXED_WEATHERED_COPPER_CHANDELIER,
                ModBlocks.WAXED_OXIDIZED_COPPER_CHANDELIER,
                ModBlocks.COPPER_CHAIN,
                ModBlocks.EXPOSED_COPPER_CHAIN,
                ModBlocks.WEATHERED_COPPER_CHAIN,
                ModBlocks.OXIDIZED_COPPER_CHAIN,
                ModBlocks.WAXED_COPPER_CHAIN,
                ModBlocks.WAXED_EXPOSED_COPPER_CHAIN,
                ModBlocks.WAXED_WEATHERED_COPPER_CHAIN,
                ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN,
                ModBlocks.COPPER_DOOR,
                ModBlocks.EXPOSED_COPPER_DOOR,
                ModBlocks.WEATHERED_COPPER_DOOR,
                ModBlocks.OXIDIZED_COPPER_DOOR,
                ModBlocks.WAXED_COPPER_DOOR,
                ModBlocks.WAXED_EXPOSED_COPPER_DOOR,
                ModBlocks.WAXED_WEATHERED_COPPER_DOOR,
                ModBlocks.WAXED_OXIDIZED_COPPER_DOOR,
                ModBlocks.COPPER_LANTERN,
                ModBlocks.EXPOSED_COPPER_LANTERN,
                ModBlocks.WEATHERED_COPPER_LANTERN,
                ModBlocks.OXIDIZED_COPPER_LANTERN,
                ModBlocks.WAXED_COPPER_LANTERN,
                ModBlocks.WAXED_EXPOSED_COPPER_LANTERN,
                ModBlocks.WAXED_WEATHERED_COPPER_LANTERN,
                ModBlocks.WAXED_OXIDIZED_COPPER_LANTERN,
                ModBlocks.COPPER_SOUL_LANTERN,
                ModBlocks.EXPOSED_COPPER_SOUL_LANTERN,
                ModBlocks.WEATHERED_COPPER_SOUL_LANTERN,
                ModBlocks.OXIDIZED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_EXPOSED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_WEATHERED_COPPER_SOUL_LANTERN,
                ModBlocks.WAXED_OXIDIZED_COPPER_SOUL_LANTERN,
                ModBlocks.COPPER_TRAPDOOR,
                ModBlocks.EXPOSED_COPPER_TRAPDOOR,
                ModBlocks.WEATHERED_COPPER_TRAPDOOR,
                ModBlocks.OXIDIZED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                ModBlocks.WAXED_OXIDIZED_COPPER_TRAPDOOR,
                ModBlocks.SILVER_DOOR,
                ModBlocks.WHITE_KITCHEN_CURTAINS,
                ModBlocks.LIGHT_GRAY_KITCHEN_CURTAINS,
                ModBlocks.GRAY_KITCHEN_CURTAINS,
                ModBlocks.BLACK_KITCHEN_CURTAINS,
                ModBlocks.BROWN_KITCHEN_CURTAINS,
                ModBlocks.RED_KITCHEN_CURTAINS,
                ModBlocks.ORANGE_KITCHEN_CURTAINS,
                ModBlocks.YELLOW_KITCHEN_CURTAINS,
                ModBlocks.LIME_KITCHEN_CURTAINS,
                ModBlocks.GREEN_KITCHEN_CURTAINS,
                ModBlocks.CYAN_KITCHEN_CURTAINS,
                ModBlocks.LIGHT_BLUE_KITCHEN_CURTAINS,
                ModBlocks.BLUE_KITCHEN_CURTAINS,
                ModBlocks.PURPLE_KITCHEN_CURTAINS,
                ModBlocks.MAGENTA_KITCHEN_CURTAINS,
                ModBlocks.PINK_KITCHEN_CURTAINS,
                ModBlocks.LONG_WHITE_KITCHEN_CURTAINS,
                ModBlocks.LONG_LIGHT_GRAY_KITCHEN_CURTAINS,
                ModBlocks.LONG_GRAY_KITCHEN_CURTAINS,
                ModBlocks.LONG_BLACK_KITCHEN_CURTAINS,
                ModBlocks.LONG_BROWN_KITCHEN_CURTAINS,
                ModBlocks.LONG_RED_KITCHEN_CURTAINS,
                ModBlocks.LONG_ORANGE_KITCHEN_CURTAINS,
                ModBlocks.LONG_YELLOW_KITCHEN_CURTAINS,
                ModBlocks.LONG_LIME_KITCHEN_CURTAINS,
                ModBlocks.LONG_GREEN_KITCHEN_CURTAINS,
                ModBlocks.LONG_CYAN_KITCHEN_CURTAINS,
                ModBlocks.LONG_LIGHT_BLUE_KITCHEN_CURTAINS,
                ModBlocks.LONG_BLUE_KITCHEN_CURTAINS,
                ModBlocks.LONG_PURPLE_KITCHEN_CURTAINS,
                ModBlocks.LONG_MAGENTA_KITCHEN_CURTAINS,
                ModBlocks.LONG_PINK_KITCHEN_CURTAINS,
                ModBlocks.PRIDE_KITCHEN_CURTAINS,
                ModBlocks.GOLDEN_DOOR,
                ModBlocks.GOLDEN_TRAPDOOR,
                ModBlocks.SILVER_TRAPDOOR,
                ModBlocks.OAK_TRELLIS,
                ModBlocks.DARK_OAK_TRELLIS,
                ModBlocks.BIRCH_TRELLIS,
                ModBlocks.JUNGLE_TRELLIS,
                ModBlocks.ACACIA_TRELLIS,
                ModBlocks.SPRUCE_TRELLIS,
                ModBlocks.CRIMSON_TRELLIS,
                ModBlocks.WARPED_TRELLIS,
                ModBlocks.MANGROVE_TRELLIS
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
