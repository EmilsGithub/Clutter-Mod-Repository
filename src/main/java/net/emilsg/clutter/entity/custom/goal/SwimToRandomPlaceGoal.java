package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.parent.ClutterFishEntity;
import net.minecraft.entity.ai.goal.SwimAroundGoal;

public class SwimToRandomPlaceGoal extends SwimAroundGoal {
    private final ClutterFishEntity fish;

    public SwimToRandomPlaceGoal(ClutterFishEntity fish, double speed) {
        super(fish, speed, 40);
        this.fish = fish;
    }

    public boolean canStart() {
        return this.fish.getHasSelfControl() && super.canStart();
    }
}
