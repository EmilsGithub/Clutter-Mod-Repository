package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.BeaverEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BeaverModel extends GeoModel<BeaverEntity> {
    private static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/beaver.png");
    private static final Identifier ANIMATIONS = new Identifier(Clutter.MOD_ID, "animations/animation.beaver.json");
    private static final Identifier MODEL = new Identifier(Clutter.MOD_ID, "geo/beaver.geo.json");
    private static final Identifier BABY_MODEL = new Identifier(Clutter.MOD_ID, "geo/beaver.geo.json");

    @Override
    public Identifier getModelResource(BeaverEntity animatable) {
        if(animatable.isBaby()) {
            return BABY_MODEL;
        } else {
            return MODEL;
        }
    }


    @Override
    public Identifier getTextureResource(BeaverEntity animatable) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(BeaverEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(BeaverEntity animatable, long instanceId, AnimationState<BeaverEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
