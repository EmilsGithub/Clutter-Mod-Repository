package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.JellyfishModel;
import net.emilsg.clutter.entity.custom.JellyfishEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

public class JellyfishRenderer extends MobEntityRenderer<JellyfishEntity, JellyfishModel<JellyfishEntity>> {
    private static final Identifier PURPLE_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/purple_jellyfish.png");
    private static final Identifier BLUE_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/blue_jellyfish.png");
    private static final Identifier GREEN_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/green_jellyfish.png");

    private static final Identifier PURPLE_EMISSIVE = new Identifier(Clutter.MOD_ID, "textures/entity/purple_jellyfish_emissive.png");
    private static final Identifier BLUE_EMISSIVE = new Identifier(Clutter.MOD_ID, "textures/entity/blue_jellyfish_emissive.png");
    private static final Identifier GREEN_EMISSIVE = new Identifier(Clutter.MOD_ID, "textures/entity/green_jellyfish_emissive.png");

    public JellyfishRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new JellyfishModel<>(ctx.getPart(ModModelLayers.JELLYFISH)), 0.4f);
        this.addFeature(new EmissiveRenderer<>(this, this::getEmissiveTextureForVariant));
    }

    @Nullable
    @Override
    protected RenderLayer getRenderLayer(JellyfishEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return super.getRenderLayer(entity, showBody, true, showOutline);
    }

    @Override
    protected void setupTransforms(JellyfishEntity jellyfishEntity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        float i = MathHelper.lerp(tickDelta, jellyfishEntity.prevTiltAngle, jellyfishEntity.tiltAngle);
        float j = MathHelper.lerp(tickDelta, jellyfishEntity.prevRollAngle, jellyfishEntity.rollAngle);
        matrices.translate(0.0f, 0.25f, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - bodyYaw));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(i));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j));
        matrices.translate(0.0f, 0.0f, 0.0f);
    }

    @Override
    public Identifier getTexture(JellyfishEntity entity) {
        return switch (entity.getVariant()) {
            case GREEN -> GREEN_TEXTURE;
            case BLUE -> BLUE_TEXTURE;
            default -> PURPLE_TEXTURE;
        };
    }

    private Identifier getEmissiveTextureForVariant(JellyfishEntity entity) {
        return switch (entity.getVariant()) {
            case BLUE -> BLUE_EMISSIVE;
            case GREEN -> GREEN_EMISSIVE;
            case PURPLE -> PURPLE_EMISSIVE;
        };
    }
}
