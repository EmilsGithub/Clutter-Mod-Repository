package net.emilsg.clutter.block.entity.render;

import net.emilsg.clutter.block.entity.CardboardBoxInventoryBlockEntity;
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
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

public class CardboardBoxBlockEntityRenderer implements BlockEntityRenderer<CardboardBoxInventoryBlockEntity> {

    public CardboardBoxBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(CardboardBoxInventoryBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();


        if(entity.getCachedState().get(Properties.OPEN)) {
            for (int i = 0; i < entity.size(); i++) {
                Direction direction = entity.getCachedState().get(Properties.HORIZONTAL_FACING);
                ItemStack itemStack = entity.getRenderStack(i);
                matrices.push();

                float translateX;
                float translateZ;


                if(entity.getCachedState().get(Properties.HORIZONTAL_FACING) == Direction.NORTH) {
                    switch (i) {
                        case 0, 3, 6 -> translateX = 0.28125f;
                        case 1, 4, 7 -> translateX = 0.5f;
                        default -> translateX = 0.71875f;
                    }
                    switch (i) {
                        case 0, 1, 2 -> translateZ = 0.28125f;
                        case 3, 4, 5 -> translateZ = 0.5f;
                        default -> translateZ = 0.71875f;
                    }
                } else if(entity.getCachedState().get(Properties.HORIZONTAL_FACING) == Direction.EAST) {
                    switch (i) {
                        case 6, 7, 8 -> translateX = 0.28125f;
                        case 3, 4, 5 -> translateX = 0.5f;
                        default -> translateX = 0.71875f;
                    }
                    switch (i) {
                        case 3, 0, 6 -> translateZ = 0.28125f;
                        case 1, 4, 7 -> translateZ = 0.5f;
                        default -> translateZ = 0.71875f;
                    }
                } else if(entity.getCachedState().get(Properties.HORIZONTAL_FACING) == Direction.SOUTH) {
                    switch (i) {
                        case 2, 5, 8 -> translateX = 0.28125f;
                        case 1, 4, 7 -> translateX = 0.5f;
                        default -> translateX = 0.71875f;
                    }
                    switch (i) {
                        case 6, 7, 8 -> translateZ = 0.28125f;
                        case 3, 4, 5 -> translateZ = 0.5f;
                        default -> translateZ = 0.71875f;
                    }
                } else {
                    switch (i) {
                        case 0, 1, 2 -> translateX = 0.28125f;
                        case 3, 4, 5 -> translateX = 0.5f;
                        default -> translateX = 0.71875f;
                    }
                    switch (i) {
                        case 2, 5, 8 -> translateZ = 0.28125f;
                        case 1, 4, 7 -> translateZ = 0.5f;
                        default -> translateZ = 0.71875f;
                    }
                }

                int itemRot = 0;
                switch(direction) {
                    case EAST -> itemRot = 270;
                    case SOUTH -> itemRot = 180;
                    case WEST -> itemRot= 90;
                }

                matrices.translate(translateX, 0.09375f, translateZ);
                matrices.scale(0.7f, 0.7f, 0.7f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(itemRot));

                itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND, getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

                matrices.pop();
            }
        }
    }

    private int getLightLevel(World world, BlockPos blockPos) {
        int bLight = world.getLightLevel(LightType.BLOCK, blockPos);
        int sLight = world.getLightLevel(LightType.SKY, blockPos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
