package net.emilsg.clutter;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.emilsg.clutter.util.Sit;
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
        EntityRendererRegistry.register(Sit.SEAT, EmptyRenderer::new);

        List<Block> blocksToRender = Arrays.asList(
                ModBlocks.COPPER_LANTERN,
                ModBlocks.COPPER_CHAIN,
                ModBlocks.COPPER_BARS,
                ModBlocks.COPPER_TRAPDOOR,
                ModBlocks.COPPER_DOOR,
                ModBlocks.FOOD_BOX,
                ModBlocks.CARROT_FOOD_BOX,
                ModBlocks.POTATO_FOOD_BOX,
                ModBlocks.BEETROOT_FOOD_BOX,
                ModBlocks.APPLE_FOOD_BOX,
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
                ModBlocks.MANGROVE_WALL_BOOKSHELF
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
