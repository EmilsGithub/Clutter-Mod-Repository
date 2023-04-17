package net.emilsg.clutter.block.custom;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ButtonBlock extends AbstractButtonBlock {
    private final SoundEvent clickOffSound;
    private final SoundEvent clickOnSound;
    private final int pressTicks;
    private final boolean wooden;

    public ButtonBlock(Settings settings, int pressTicks, boolean wooden, SoundEvent clickOffSound, SoundEvent clickOnSound) {
        super(wooden, settings);
        this.pressTicks = pressTicks;
        this.wooden = wooden;
        this.clickOffSound = clickOffSound;
        this.clickOnSound = clickOnSound;
    }

    @Override
    public void powerOn(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, (BlockState)state.with(POWERED, true), 3);
        this.updateNeighbors(state, world, pos);
        world.createAndScheduleBlockTick(pos, this, this.pressTicks);
    }

    private void updateNeighbors(BlockState state, World world, BlockPos pos) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(getDirection(state).getOpposite()), this);
    }

    protected SoundEvent getClickSound(boolean powered) {
        return powered ? this.clickOnSound : this.clickOffSound;
    }
}
