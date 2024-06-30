package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EmberTortoiseEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;

public class EmberTortoiseLookAroundGoal extends LookAroundGoal {
    EmberTortoiseEntity emberTortoise;

    public EmberTortoiseLookAroundGoal(EmberTortoiseEntity emberTortoise) {
        super(emberTortoise);
        this.emberTortoise = emberTortoise;
    }

    @Override
    public boolean canStart() {
        return !this.emberTortoise.isShielding() && super.canStart();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.emberTortoise.isShielding()) stop();
    }
}
