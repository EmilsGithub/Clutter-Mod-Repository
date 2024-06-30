package net.emilsg.clutter.block.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ReversedWeightedPressurePlateBlock extends AbstractPressurePlateBlock {
    public static final IntProperty POWER = Properties.POWER;
    private final int weight;

    public ReversedWeightedPressurePlateBlock(int weight, AbstractBlock.Settings settings, BlockSetType blockSetType) {
        super(settings, blockSetType);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWER, 0));
        this.weight = weight;
    }

    protected int getRedstoneOutput(World world, BlockPos pos) {
        int i = Math.min(getEntityCount(world, BOX.offset(pos), Entity.class), this.weight);
        if (i > 0) {
            float f = (float) Math.min(this.weight, i) / (float) this.weight;
            return MathHelper.clamp(16 - MathHelper.ceil(f * 15.0F), 1, 15);
        } else {
            return 0;
        }
    }

    protected int getRedstoneOutput(BlockState state) {
        return state.get(POWER);
    }

    protected BlockState setRedstoneOutput(BlockState state, int rsOut) {
        return state.with(POWER, rsOut);
    }

    protected int getTickRate() {
        return 10;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }
}
