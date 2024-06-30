package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;

@SuppressWarnings("unchecked")
public class EchofinConditionalActiveTargetGoal extends ActiveTargetGoal {
    EchofinEntity echofinEntity;

    public EchofinConditionalActiveTargetGoal(EchofinEntity echofinEntity, Class targetClass, boolean checkVisibility) {
        super(echofinEntity, targetClass, checkVisibility);
        this.echofinEntity = echofinEntity;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && echofinEntity.hasAbility();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && echofinEntity.hasAbility();
    }
}
