package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.EmperorPenguinEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class EmperorPenguinRenderer extends GeoEntityRenderer<EmperorPenguinEntity> {

    private static final Identifier ADULT_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/adult_emperor_penguin.png");
    private static final Identifier BABY_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/baby_emperor_penguin.png");

    public EmperorPenguinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new EmperorPenguinModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public Identifier getTextureLocation(EmperorPenguinEntity animatable) {
        if (animatable.isBaby()) {
            return BABY_TEXTURE;
        } else {
            return ADULT_TEXTURE;
        }
    }

    @Override
    public float getMotionAnimThreshold(EmperorPenguinEntity animatable) {
        return 0.0075f;
    }
}
