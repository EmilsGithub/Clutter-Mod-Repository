package net.emilsg.clutter.entity.custom.goal;

import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class WanderFarUnderwaterGoal extends WanderAroundFarGoal {

    public WanderFarUnderwaterGoal(PathAwareEntity mob, double speed, float probability) {
        super(mob, speed, probability);
    }


}

