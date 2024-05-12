package net.emilsg.clutter.block.entity.render;

import net.emilsg.clutter.block.custom.ShelfBlock;
import net.emilsg.clutter.block.entity.ShelfInventoryBlockEntity;
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

import java.util.Objects;

public class ShelfBlockEntityRenderer implements BlockEntityRenderer<ShelfInventoryBlockEntity> {

    public ShelfBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(ShelfInventoryBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        Direction direction = entity.getCachedState().get(ShelfBlock.FACING);
        int itemRot = 181;

        for (int i = 0; i < entity.size(); i++) {
            ItemStack itemStack = entity.getRenderStack(i);
            matrices.push();

            float translateZ;
            switch (direction) {
                default -> translateZ  = 0.25f;
                case SOUTH -> translateZ = 0.75f;
                case EAST, WEST -> translateZ = 0.1f + i * 0.2f;
            }

            float translateX;
            switch (direction) {
                default -> translateX  = 0.1f + i * 0.2f;
                case EAST -> translateX = 0.75f;
                case WEST -> translateX = 0.25f;
            }

            if (direction == Direction.SOUTH) {
                translateX = 1.0f - translateX;
            }
            else if (direction == Direction.WEST) {
                translateZ = 1.0f - translateZ;
            }

            switch(direction) {
                case EAST -> itemRot = 91;
                case SOUTH -> itemRot = 1;
                case WEST -> itemRot= 271;
            }


            matrices.translate(translateX, 0.55, translateZ);
            matrices.scale(0.6f,0.6f,0.6f);

            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(itemRot));

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