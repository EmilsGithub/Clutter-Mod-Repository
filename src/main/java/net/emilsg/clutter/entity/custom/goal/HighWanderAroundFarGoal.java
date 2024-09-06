package net.emilsg.clutter.entity.custom.goal;

import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class HighWanderAroundFarGoal extends WanderAroundGoal {
    protected final float probability;

    public HighWanderAroundFarGoal(PathAwareEntity mob, double speed, float probability) {
        super(mob, speed);
        this.probability = probability;
    }

    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 9);
        return vec3d == null ? super.getWanderTarget() : vec3d;
    }
}
