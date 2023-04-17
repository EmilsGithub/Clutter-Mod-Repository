package net.emilsg.clutter.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.StoneButtonBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class OxidizableButtonBlock extends StoneButtonBlock implements Oxidizable {
    private final SoundEvent clickOffSound;
    private final SoundEvent clickOnSound;
    private final int pressTicks;
    private final boolean wooden;

    private final OxidationLevel oxidationLevel;

    public OxidizableButtonBlock(OxidationLevel oxidationLevel,  Settings settings, int pressTicks, boolean wooden, SoundEvent clickOffSound, SoundEvent clickOnSound) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
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

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        if (!state.get(POWERED)) {
            return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
        } else {
            return false;
        }
    }

    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}
