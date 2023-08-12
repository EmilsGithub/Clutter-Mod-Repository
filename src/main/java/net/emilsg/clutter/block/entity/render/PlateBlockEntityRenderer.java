package net.emilsg.clutter.block.entity.render;

import net.emilsg.clutter.block.custom.ShelfBlock;
import net.emilsg.clutter.block.entity.PlateInventoryBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Objects;

public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateInventoryBlockEntity> {

    public PlateBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(PlateInventoryBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        Direction direction = entity.getCachedState().get(ShelfBlock.FACING);

        Map<Direction, float[][]> translations = Map.of(
                Direction.NORTH, new float[][]{{0.4f, 0.0725f, 0.45f}, {0.45f, 0.07f, 0.675f}, {0.65f, 0.075f, 0.525f}},
                Direction.EAST, new float[][]{{0.325f, 0.0725f, 0.4f}, {0.55f, 0.07f, 0.45f}, {0.4f, 0.075f, 0.65f}},
                Direction.SOUTH, new float[][]{{0.35f, 0.0725f, 0.325f}, {0.4f, 0.07f, 0.55f}, {0.6f, 0.075f, 0.4f}},
                Direction.WEST, new float[][]{{0.45f, 0.0725f, 0.375f}, {0.5f, 0.07f, 0.6f}, {0.675f, 0.075f, 0.45f}}
        );

        for (int i = 0; i < entity.size(); i++) {
            ItemStack itemStack = entity.getRenderStack(i);
            matrices.push();

            float[] translation = translations.get(direction)[i];
            matrices.translate(translation[0], translation[1], translation[2]);

            matrices.scale(0.75f, 0.75f, 0.75f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));

            int zRot;
            switch (direction) {
                default -> zRot = 0;
                case EAST -> zRot = 270;
                case SOUTH -> zRot = 180;
                case WEST -> zRot = 90;
            }

            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(zRot));
            itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND, getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.pop();
        }
    }


    private int getLightLevel(World world, BlockPos blockPos) {
        int bLight = world.getLightLevel(LightType.BLOCK, blockPos);
        int sLight = world.getLightLevel(LightType.SKY, blockPos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
