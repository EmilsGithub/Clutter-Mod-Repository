package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.ChameleonEntity;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ChameleonRenderer extends GeoEntityRenderer<ChameleonEntity> {
    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/chameleon.png");

    public ChameleonRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ChameleonModel());
        this.shadowRadius = 0.25f;
    }

    @Override
    public Identifier getTextureLocation(ChameleonEntity animatable) {
        return TEXTURE;
    }

    @Override
    public Color getRenderColor(ChameleonEntity animatable, float partialTick, int packedLight) {
        return getFoliageColor(animatable);
    }

    public Color getFoliageColor(Entity animatable) {
        BlockPos animatablePos = animatable.getBlockPos();
        RegistryEntry<Biome> registryEntry = animatable.getWorld().getBiome(animatablePos);

        int color;
        if(animatable.isTouchingWater() || getBlockOrFluidBelowEntity(animatable).getFluidState().isIn(FluidTags.WATER)) {
            color = BiomeColors.getWaterColor(animatable.getWorld(), animatablePos);
        } else if(registryEntry.isIn(BiomeTags.IS_OVERWORLD)) {
            if (getBlockOrFluidBelowEntity(animatable).getBlock() instanceof LeavesBlock) {
                color = BiomeColors.getFoliageColor(animatable.getWorld(), animatablePos);
            } else {
                color = BiomeColors.getGrassColor(animatable.getWorld(), animatablePos);
            }
        } else if (registryEntry.isIn(BiomeTags.IS_NETHER)) {
            if (registryEntry.matchesKey(BiomeKeys.WARPED_FOREST) || getBlockOrFluidBelowEntity(animatable).isOf(Blocks.WARPED_NYLIUM)) {
                color = 878665;
            } else if (registryEntry.matchesKey(BiomeKeys.CRIMSON_FOREST) || getBlockOrFluidBelowEntity(animatable).isOf(Blocks.CRIMSON_NYLIUM)) {
                color = 10495779;
            } else if (registryEntry.matchesKey(BiomeKeys.SOUL_SAND_VALLEY) || getBlockOrFluidBelowEntity(animatable).isIn(BlockTags.SOUL_FIRE_BASE_BLOCKS)) {
                color = 5192235;
            } else if (registryEntry.matchesKey(BiomeKeys.BASALT_DELTAS) || getBlockOrFluidBelowEntity(animatable).isIn(ModBlockTags.BASALT)) {
                color = 3552564;
            } else {
                color = 8536385;
            }
        } else if (registryEntry.isIn(BiomeTags.IS_END) || getBlockOrFluidBelowEntity(animatable).isOf(Blocks.END_STONE)) {
            color = 14079125;
        } else {
            color = -1;
        }
        return new Color(color);
    }

    @Override
    public void render(ChameleonEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.4f, 0.4f, 0.4f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public float getMotionAnimThreshold(ChameleonEntity animatable) {
        return 0.0075f;
    }

    public BlockState getBlockOrFluidBelowEntity(Entity entity) {
        World world = entity.getEntityWorld();
        BlockPos entityPos = entity.getBlockPos();
        BlockPos groundPos = world.raycast(new RaycastContext(
                entityPos.toCenterPos(),
                entityPos.add(0, World.MIN_Y - entityPos.getY(), 0).toCenterPos(),
                RaycastContext.ShapeType.COLLIDER,
                RaycastContext.FluidHandling.ANY,  // Changed to ANY to include fluids
                entity
        )).getBlockPos();

        return world.getBlockState(groundPos);
    }
}
