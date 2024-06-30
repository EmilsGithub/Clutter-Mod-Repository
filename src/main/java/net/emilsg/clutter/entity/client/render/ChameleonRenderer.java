package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.ChameleonModel;
import net.emilsg.clutter.entity.client.render.feature.ChameleonColorFeatureRenderer;
import net.emilsg.clutter.entity.custom.ChameleonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ChameleonRenderer extends MobEntityRenderer<ChameleonEntity, ChameleonModel<ChameleonEntity>> {
    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/chameleon_box.png");

    public ChameleonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ChameleonModel<>(ctx.getPart(ModModelLayers.CHAMELEON)), 0.4f);
        this.addFeature(new ChameleonColorFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ChameleonEntity chameleon) {
        return TEXTURE;
    }
}
