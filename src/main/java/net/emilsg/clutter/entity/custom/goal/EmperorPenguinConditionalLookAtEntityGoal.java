package net.emilsg.clutter.entity.custom.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

public class EmperorPenguinConditionalLookAtEntityGoal extends LookAtEntityGoal {

    public EmperorPenguinConditionalLookAtEntityGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range) {
        super(mob, targetType, range);
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !mob.isSubmergedInWater();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && !mob.isSubmergedInWater();
    }
}
