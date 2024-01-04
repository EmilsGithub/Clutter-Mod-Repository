package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;

public class EmberTortoiseWanderAroundFarGoal extends WanderAroundFarGoal {
    EmberTortoiseEntity emberTortoise;

    public EmberTortoiseWanderAroundFarGoal(EmberTortoiseEntity emberTortoise, double speed, float probability) {
        super(emberTortoise, speed, probability);
        this.emberTortoise = emberTortoise;
    }

    @Override
    public boolean canStart() {
        return !this.emberTortoise.isShielding() && super.canStart();
    }

    @Override
    public void tick() {
        super.tick();
        if(this.emberTortoise.isShielding()) stop();
    }
}
