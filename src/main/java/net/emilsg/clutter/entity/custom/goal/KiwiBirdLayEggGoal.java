package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.custom.KiwiBirdEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class KiwiBirdLayEggGoal extends MoveToTargetPosGoal {
    KiwiBirdEntity kiwiBird;

    public KiwiBirdLayEggGoal(KiwiBirdEntity kiwiBird, double speed) {
        super(kiwiBird, speed, 16);
        this.kiwiBird = kiwiBird;
    }

    public boolean canStart() {
        return this.kiwiBird.hasEgg() && super.canStart() && this.kiwiBird.getEggTimer() >= 400;
    }

    public boolean shouldContinue() {
        return super.shouldContinue() && this.kiwiBird.hasEgg() && this.kiwiBird.getEggTimer() >= 400;
    }

    public void tick() {
        super.tick();
        if (this.hasReached()) {
            World world = this.kiwiBird.getWorld();
            world.setBlockState(targetPos.up(), ModBlocks.KIWI_BIRD_EGG.getDefaultState(), 3);
            world.emitGameEvent(GameEvent.BLOCK_PLACE, targetPos.up(), GameEvent.Emitter.of(this.kiwiBird, ModBlocks.KIWI_BIRD_EGG.getDefaultState()));
            this.kiwiBird.setHasEgg(false);
            this.kiwiBird.setEggTimer(0);
        }
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return (world.isAir(pos.up()) || world.getBlockState(pos.up()).isReplaceable()) && (state.isIn(BlockTags.DIRT) || state.isOf(Blocks.HAY_BLOCK)) && !world.getFluidState(pos.up()).isOf(Fluids.WATER);
    }


}
