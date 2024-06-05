package net.emilsg.clutter.entity.client.render;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.AbstractNetherNewtEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class CrimsonNewtRenderer extends AbstractNetherNewtRenderer {
    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/crimson_newt_box.png");

    public CrimsonNewtRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(AbstractNetherNewtEntity entity) {
        return TEXTURE;
    }
}
