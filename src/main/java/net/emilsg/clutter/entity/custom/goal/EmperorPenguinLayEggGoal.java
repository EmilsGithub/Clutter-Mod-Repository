package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.EmperorPenguinEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class EmperorPenguinLayEggGoal extends MoveToTargetPosGoal {
    EmperorPenguinEntity emperorPenguinEntity;
    BlockState eggState;

    public EmperorPenguinLayEggGoal(EmperorPenguinEntity emperorPenguinEntity, double speed, BlockState eggState) {
        super(emperorPenguinEntity, speed, 16);
        this.emperorPenguinEntity = emperorPenguinEntity;
        this.eggState = eggState;
    }

    public boolean canStart() {
        return this.emperorPenguinEntity.hasEgg() && super.canStart() && this.emperorPenguinEntity.getEggTimer() >= 400;
    }

    public boolean shouldContinue() {
        return super.shouldContinue() && this.emperorPenguinEntity.hasEgg() && this.emperorPenguinEntity.getEggTimer() >= 400;
    }

    public void tick() {
        super.tick();
        if (this.hasReached()) {
            World world = this.emperorPenguinEntity.getWorld();
            world.setBlockState(targetPos.up(), eggState, 3);
            world.emitGameEvent(GameEvent.BLOCK_PLACE, targetPos.up(), GameEvent.Emitter.of(this.emperorPenguinEntity, eggState));
            this.emperorPenguinEntity.setHasEgg(false);
            this.emperorPenguinEntity.setEggTimer(0);
        }
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return (world.isAir(pos.up()) || world.getBlockState(pos.up()).isReplaceable()) && (state.isIn(BlockTags.ICE) || state.isOf(Blocks.SNOW_BLOCK) || state.isIn(BlockTags.DIRT)) && !world.getFluidState(pos.up()).isOf(Fluids.WATER);
    }
}
