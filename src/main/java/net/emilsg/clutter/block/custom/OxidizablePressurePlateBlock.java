package net.emilsg.clutter.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class OxidizablePressurePlateBlock extends PressurePlateBlock implements Oxidizable {

    private final OxidationLevel oxidationLevel;

    public OxidizablePressurePlateBlock(OxidationLevel oxidationLevel, ActivationRule type, Settings settings, SoundEvent depressSound, SoundEvent pressSound) {
        super(type, settings, depressSound, pressSound);
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
