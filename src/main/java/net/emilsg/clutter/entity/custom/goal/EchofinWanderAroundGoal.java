package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class EchofinWanderAroundGoal extends Goal {
    EchofinEntity echofinEntity;
    private BlockPos homePos;

    public EchofinWanderAroundGoal(EchofinEntity echofinEntity) {
        this.echofinEntity = echofinEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return echofinEntity.getNavigation().isIdle() && echofinEntity.getRandom().nextInt(10) == 0;
    }

    public boolean shouldContinue() {
        return echofinEntity.getNavigation().isFollowingPath();
    }

    public void start() {
        Vec3d vec3d = this.getRandomLocation();
        if (vec3d != null) {
            echofinEntity.getNavigation().startMovingAlong(echofinEntity.getNavigation().findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
        }
    }

    private Vec3d getRandomLocation() {
        if (this.homePos == null) {
            this.homePos = echofinEntity.getHomePos();
        }

        Vec3d vec3d2 = echofinEntity.getRotationVec(0.0F);
        Vec3d vec3d3 = AboveGroundTargeting.find(echofinEntity, 24, 7, vec3d2.x, vec3d2.z, 1.5707964F, 3, 2);

        if (vec3d3 != null && echofinEntity.getBlockPos().getSquaredDistance(Vec3d.ofCenter(homePos)) > 2 * 2) {
            return vec3d3;
        }

        if (echofinEntity.getWorld().isNight() && echofinEntity.getBlockPos().getSquaredDistance(Vec3d.ofCenter(homePos)) > 8 * 8) {
            return Vec3d.ofCenter(homePos);
        }

        BlockPos blockpos = homePos.add(-2 + echofinEntity.getRandom().nextInt(5), -1 + echofinEntity.getRandom().nextInt(3), -2 + echofinEntity.getRandom().nextInt(5));

        if (!echofinEntity.getWorld().getBlockState(blockpos).isOpaque()) {
            return Vec3d.ofCenter(blockpos);
        }

        return null;
    }
}
