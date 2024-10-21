package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionTypes;

public class ButterflyWanderNetherGoal extends Goal {
    ButterflyEntity butterfly;

    public ButterflyWanderNetherGoal(ButterflyEntity butterfly) {
        this.butterfly = butterfly;
    }

    private static BlockPos getRandomPos(BlockPos center) {
        int x = center.getX() + (int) (Math.random() * 24 * 2) - 24;
        int y = center.getY() + (int) (Math.random() * 8 * 2) - 8;
        int z = center.getZ() + (int) (Math.random() * 24 * 2) - 24;
        return new BlockPos(x, y, z);
    }

    @Override
    public boolean canStart() {
        return butterfly.getWorld().getDimensionEntry() != (DimensionTypes.OVERWORLD) && butterfly.getNavigation().isIdle() && butterfly.getRandom().nextInt(3) == 0;
    }

    public boolean shouldContinue() {
        return this.butterfly.getNavigation().isFollowingPath();
    }

    public void start() {
        BlockPos butterflyBlockPos = this.butterfly.getBlockPos();
        BlockPos randomPos = getRandomPos(butterflyBlockPos);
        if (butterfly.getWorld().getBlockState(randomPos).isReplaceable()) {
            butterfly.getNavigation().startMovingTo(randomPos.getX(), randomPos.getY(), randomPos.getZ(), 1.0f);
        }
    }
}
