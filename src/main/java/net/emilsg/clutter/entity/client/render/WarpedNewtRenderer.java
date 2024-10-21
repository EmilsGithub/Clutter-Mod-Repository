package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.AbstractNetherNewtEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class WarpedNewtRenderer extends AbstractNetherNewtRenderer {
    private static final Identifier TEXTURE = Identifier.of(Clutter.MOD_ID, "textures/entity/warped_newt_box.png");

    public WarpedNewtRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(AbstractNetherNewtEntity entity) {
        return TEXTURE;
    }
}
