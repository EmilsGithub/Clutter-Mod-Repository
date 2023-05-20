package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.ChameleonEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ChameleonModel extends GeoModel<ChameleonEntity> {
    @Override
    public Identifier getModelResource(ChameleonEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "geo/chameleon.geo.json");
    }

    @Override
    public Identifier getTextureResource(ChameleonEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "textures/entity/chameleon.png");
    }

    @Override
    public Identifier getAnimationResource(ChameleonEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "animations/animation.chameleon.json");
    }

    @Override
    public RenderLayer getRenderType(ChameleonEntity animatable, Identifier texture) {
        return super.getRenderType(animatable, texture);
    }



    @Override
    public void setCustomAnimations(ChameleonEntity animatable, long instanceId, AnimationState<ChameleonEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
