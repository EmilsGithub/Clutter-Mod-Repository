package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.SeahorseEntity;
import net.minecraft.block.*;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class SeahorseMoveToCoralGoal extends MoveToTargetPosGoal {
    SeahorseEntity seahorseEntity;

    public SeahorseMoveToCoralGoal(SeahorseEntity seahorseEntity, double speed, int range) {
        super(seahorseEntity, speed, range);
        this.seahorseEntity = seahorseEntity;
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof CoralFanBlock || state.getBlock() instanceof CoralWallFanBlock || state.getBlock() instanceof CoralBlock;
    }
}
