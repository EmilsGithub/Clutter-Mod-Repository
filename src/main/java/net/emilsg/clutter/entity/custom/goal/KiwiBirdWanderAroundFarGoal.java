package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.KiwiBirdEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class KiwiBirdWanderAroundFarGoal extends WanderAroundFarGoal {
    KiwiBirdEntity kiwiBird;

    public KiwiBirdWanderAroundFarGoal(KiwiBirdEntity kiwiBird, PathAwareEntity mob, double speed, float probability) {
        super(mob, speed, probability);
        this.kiwiBird = kiwiBird;
    }

    @Override
    public boolean canStart() {
        return !this.kiwiBird.isDancing() && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && !this.kiwiBird.isDancing();
    }
}
