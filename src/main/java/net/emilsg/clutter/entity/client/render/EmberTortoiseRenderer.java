package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.client.layer.ModModelLayers;
import net.emilsg.clutter.entity.client.model.EmberTortoiseModel;
import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class EmberTortoiseRenderer extends MobEntityRenderer<EmberTortoiseEntity, EmberTortoiseModel<EmberTortoiseEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Clutter.MOD_ID, "textures/entity/ember_tortoise_box.png");
    private static final Identifier FIRE_TEXTURE = Identifier.of(Clutter.MOD_ID, "textures/entity/ember_tortoise_fire_box.png");

    public EmberTortoiseRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new EmberTortoiseModel<>(ctx.getPart(ModModelLayers.EMBER_TORTOISE)), 0.9f);
    }

    @Override
    public Identifier getTexture(EmberTortoiseEntity entity) {
        return entity.isShielding() ? FIRE_TEXTURE : TEXTURE;
    }
}
