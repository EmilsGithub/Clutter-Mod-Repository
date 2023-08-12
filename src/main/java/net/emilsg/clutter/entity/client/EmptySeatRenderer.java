package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.block.entity.SeatEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class EmptySeatRenderer extends EntityRenderer<SeatEntity> {
    public EmptySeatRenderer(EntityRendererFactory.Context ctx) {
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
