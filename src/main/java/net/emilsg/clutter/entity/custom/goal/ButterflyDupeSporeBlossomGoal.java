package net.emilsg.clutter.entity.custom.goal;

import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class ButterflyDupeSporeBlossomGoal extends MoveToTargetPosGoal {
    ButterflyEntity butterflyEntity;
    int dupeCooldown;

    public ButterflyDupeSporeBlossomGoal(ButterflyEntity butterflyEntity, double speed, int dupeCooldown) {
        super(butterflyEntity, speed, 8);
        this.butterflyEntity = butterflyEntity;
        this.dupeCooldown = dupeCooldown;
    }

    public boolean canStart() {
        return this.butterflyEntity.getDupeTimer() >= dupeCooldown && super.canStart();
    }

    public boolean shouldContinue() {
        return super.shouldContinue() && this.butterflyEntity.getDupeTimer() >= dupeCooldown;
    }

    public void tick() {
        super.tick();
        if (this.hasReached()) {
            this.butterflyEntity.setDupeTimer(0);
            this.butterflyEntity.dropStack(new ItemStack(Items.SPORE_BLOSSOM));
            this.stop();
        }
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return (state.isOf(Blocks.SPORE_BLOSSOM));
    }
}
