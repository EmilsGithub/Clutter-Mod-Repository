package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ButterflyWanderOverworldGoal extends Goal {
    ButterflyEntity butterfly;

    public ButterflyWanderOverworldGoal(ButterflyEntity butterfly) {
        this.setControls(EnumSet.of(Control.MOVE));
        this.butterfly = butterfly;
    }

    @Override
    public boolean canStart() {
        return butterfly.getWorld().getDimensionEntry() == (DimensionTypes.OVERWORLD) && butterfly.getNavigation().isIdle() && butterfly.getRandom().nextInt(10) == 0;
    }

    @Override
    public boolean shouldContinue() {
        return butterfly.getNavigation().isFollowingPath();
    }

    @Override
    public void start() {
        Vec3d vec3d = this.getRandomLocation();
        if (vec3d != null) {
            butterfly.getNavigation().startMovingAlong(butterfly.getNavigation().findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
        }
    }

    @Nullable
    private Vec3d getRandomLocation() {
        Vec3d vec3d2 = butterfly.getRotationVec(0.0f);

        int i = 8;
        Vec3d vec3d3 = AboveGroundTargeting.find(butterfly, 8, 7, vec3d2.x, vec3d2.z, 1.5707964f, 4, 2);
        if (vec3d3 != null) {
            return vec3d3;
        }
        return NoPenaltySolidTargeting.find(butterfly, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
    }
}
