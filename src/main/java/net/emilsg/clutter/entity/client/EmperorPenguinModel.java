package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.EmperorPenguinEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class EmperorPenguinModel extends GeoModel<EmperorPenguinEntity> {
    private static final Identifier ADULT_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/adult_emperor_penguin.png");
    private static final Identifier BABY_TEXTURE = new Identifier(Clutter.MOD_ID, "textures/entity/baby_emperor_penguin.png");
    private static final Identifier ANIMATIONS = new Identifier(Clutter.MOD_ID, "animations/animation.emperor_penguin.json");
    private static final Identifier ADULT_MODEL = new Identifier(Clutter.MOD_ID, "geo/adult_emperor_penguin.geo.json");
    private static final Identifier BABY_MODEL = new Identifier(Clutter.MOD_ID, "geo/baby_emperor_penguin.geo.json");

    @Override
    public Identifier getModelResource(EmperorPenguinEntity animatable) {
        if(animatable.isBaby()) {
            return BABY_MODEL;
        } else {
            return ADULT_MODEL;
        }
    }


    @Override
    public Identifier getTextureResource(EmperorPenguinEntity animatable) {
        if(animatable.isBaby()) {
            return BABY_TEXTURE;
        } else {
            return ADULT_TEXTURE;
        }
    }

    @Override
    public Identifier getAnimationResource(EmperorPenguinEntity animatable) {
        return ANIMATIONS;
    }

    @Override
    public void setCustomAnimations(EmperorPenguinEntity animatable, long instanceId, AnimationState<EmperorPenguinEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
