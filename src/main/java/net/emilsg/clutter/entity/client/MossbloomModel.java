package net.emilsg.clutter.entity.client;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.custom.MossbloomEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MossbloomModel extends GeoModel<MossbloomEntity> {
    @Override
    public Identifier getModelResource(MossbloomEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "geo/mossbloom.geo.json");
    }

    @Override
    public Identifier getTextureResource(MossbloomEntity animatable) {
        return MossbloomRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(MossbloomEntity animatable) {
        return new Identifier(Clutter.MOD_ID, "animations/animation.mossbloom.json");
    }

    @Override
    public void setCustomAnimations(MossbloomEntity animatable, long instanceId, AnimationState<MossbloomEntity> animationState) {
        CoreGeoBone neck = getAnimationProcessor().getBone("neck");

        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            neck.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            neck.setRotY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
