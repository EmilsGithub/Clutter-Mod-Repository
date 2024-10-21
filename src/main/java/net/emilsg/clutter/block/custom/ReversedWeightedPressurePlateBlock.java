package net.emilsg.clutter.block.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

    public static final MapCodec<ReversedWeightedPressurePlateBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.intRange(1, 16).fieldOf("weight").forGetter(block -> block.weight),
                    createSettingsCodec(),
                    BlockSetType.CODEC.fieldOf("block_set_type").forGetter(block -> block.blockSetType)
            ).apply(instance, ReversedWeightedPressurePlateBlock::new)
    );

    @Override
    protected MapCodec<? extends AbstractPressurePlateBlock> getCodec() {
        return CODEC;
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
