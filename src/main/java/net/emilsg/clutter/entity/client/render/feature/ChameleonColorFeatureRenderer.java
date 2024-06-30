package net.emilsg.clutter.entity.client.render.feature;

import net.emilsg.clutter.entity.client.model.ChameleonModel;
import net.emilsg.clutter.entity.custom.ChameleonEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MapColor;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.awt.*;


public class ChameleonColorFeatureRenderer extends FeatureRenderer<ChameleonEntity, ChameleonModel<ChameleonEntity>> {

    public ChameleonColorFeatureRenderer(FeatureRendererContext<ChameleonEntity, ChameleonModel<ChameleonEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ChameleonEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Color chameleonColor = getChameleonColor(entity);
        float red = chameleonColor.getRed() / 255.0f;
        float green = chameleonColor.getGreen() / 255.0f;
        float blue = chameleonColor.getBlue() / 255.0f;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getContextModel().getLayer(this.getTexture(entity)));
        this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0f);
    }

    private Color getChameleonColor(ChameleonEntity chameleon) {
        BlockPos chameleonPos = chameleon.getBlockPos();
        BlockState belowState = getBelowState(chameleon);

        int colorInt = 0;
        MapColor mapColor = belowState.getBlock().getDefaultMapColor();

        if (belowState.getBlock() instanceof LeavesBlock leavesBlock) {
            colorInt = BiomeColors.getFoliageColor(chameleon.getWorld(), chameleonPos);
            if (leavesBlock == Blocks.CHERRY_LEAVES) colorInt = Color.PINK.getRGB();
        } else if (belowState.isOf(Blocks.GRASS_BLOCK)) {
            colorInt = BiomeColors.getGrassColor(chameleon.getWorld(), chameleonPos);
            if (belowState.get(Properties.SNOWY)) colorInt = Color.WHITE.getRGB();
        } else if (mapColor != null) {
            colorInt = mapColor.color;
        }


        return new Color(colorInt);
    }

    public BlockState getBelowState(ChameleonEntity entity) {
        World world = entity.getEntityWorld();
        BlockPos entityPos = entity.getBlockPos();
        BlockPos groundPos = world.raycast(new RaycastContext(
                entityPos.toCenterPos(),
                entityPos.add(0, World.MIN_Y - entityPos.getY(), 0).toCenterPos(),
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.ANY,
                entity
        )).getBlockPos();

        return world.getBlockState(groundPos);
    }
}
