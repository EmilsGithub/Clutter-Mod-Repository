package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class OxidizableButtonBlock extends ButtonBlock implements Oxidizable {

    private final OxidationLevel oxidationLevel;

    public OxidizableButtonBlock(OxidationLevel oxidationLevel, Settings settings, BlockSetType blockSetType, int pressTicks, boolean wooden) {
        super(settings,blockSetType, pressTicks, wooden);
        this.oxidationLevel = oxidationLevel;
    }


    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}
