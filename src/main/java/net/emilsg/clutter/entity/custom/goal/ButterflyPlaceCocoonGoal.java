package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ButterflyPlaceCocoonGoal extends MoveToTargetPosGoal {
    ButterflyEntity butterfly;

    public ButterflyPlaceCocoonGoal(ButterflyEntity butterfly, double speed) {
        super(butterfly, speed, 16);
        this.butterfly = butterfly;
    }

    public boolean canStart() {
        return this.butterfly.hasCocoon() && super.canStart();
    }

    public boolean shouldContinue() {
        return super.shouldContinue() && this.butterfly.hasCocoon();
    }

    public void tick() {
        super.tick();
        if (this.hasReached()) {
            World world = this.butterfly.getWorld();
            BlockPos cocoonPos = this.targetPos;
            world.setBlockState(cocoonPos, ModBlocks.BUTTERFLY_COCOON.getDefaultState(), 3);
            this.butterfly.setHasCocoon(false);
        }
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        Block block = world.getBlockState(pos.up()).getBlock();
        BlockState state = world.getBlockState(pos.up());
        return (world.isAir(pos) || world.getBlockState(pos).isReplaceable()) && (block instanceof LeavesBlock || state.isIn(BlockTags.LOGS) || state.isIn(BlockTags.WART_BLOCKS) || state.isOf(Blocks.BONE_BLOCK));
    }
}
