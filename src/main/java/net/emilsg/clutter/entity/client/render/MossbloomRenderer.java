package net.emilsg.clutter.entity.client.render;

import com.google.common.collect.Maps;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.MossbloomModel;
import net.emilsg.clutter.entity.client.render.feature.EmissiveRenderer;
import net.emilsg.clutter.entity.custom.MossbloomEntity;
import net.emilsg.clutter.entity.variants.MossbloomVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class MossbloomRenderer extends MobEntityRenderer<MossbloomEntity, MossbloomModel<MossbloomEntity>> {
    public static final Map<MossbloomVariant, Identifier> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(MossbloomVariant.class), (map) -> {
        map.put(MossbloomVariant.M, Identifier.of(Clutter.MOD_ID, "textures/entity/mossbloom_m.png"));
        map.put(MossbloomVariant.F, Identifier.of(Clutter.MOD_ID, "textures/entity/mossbloom_f.png"));
    });

    public static final Identifier GLOW_ID = Identifier.of(Clutter.MOD_ID, "textures/entity/mossbloom_m_glowmask.png");

    public MossbloomRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MossbloomModel<>(ctx.getPart(ModModelLayers.MOSSBLOOM)), 0.5f);
        this.addFeature(new EmissiveRenderer<>(this, MossbloomRenderer::getEmissiveTexture));
    }

    @Override
    public void render(MossbloomEntity mossbloomEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.5f;

        super.render(mossbloomEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(MossbloomEntity mossbloomEntity) {
        return LOCATION_BY_VARIANT.get(mossbloomEntity.getVariant());
    }

    private static Identifier getEmissiveTexture(MossbloomEntity mossbloomEntity) {
        return mossbloomEntity.isVariantOf(MossbloomVariant.M) && !mossbloomEntity.isBaby() ? GLOW_ID : null;
    }
}
