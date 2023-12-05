package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.CapybaraEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CapybaraModel extends GeoModel<CapybaraEntity> {
    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/capybara.png");
    private static final Identifier ANIMATIONS = new Identifier(Clutter.MOD_ID, "animations/animation.capybara.json");
    private static final Identifier MODEL = new Identifier(Clutter.MOD_ID, "geo/capybara.geo.json");
    private static final Identifier BABY_MODEL = new Identifier(Clutter.MOD_ID, "geo/baby_capybara.geo.json");

    @Override
    public Identifier getModelResource(CapybaraEntity animatable) {
        return animatable.isBaby() ? BABY_MODEL : MODEL;
    }

    @Override
    public Identifier getTextureResource(CapybaraEntity animatable) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(CapybaraEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(CapybaraEntity animatable, long instanceId, AnimationState<CapybaraEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if(head != null && !animatable.isSleeping()) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
