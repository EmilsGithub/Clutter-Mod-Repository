package net.emilsg.clutter.block.custom.cutout;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CLupineBlock extends CFlowerBlock implements Fertilizable {
    private final BlockState tallBlockState;

    public CLupineBlock(StatusEffect stewEffect, BlockState tallBlockState, int duration, Settings settings) {
        super(stewEffect, duration, settings);
        this.tallBlockState = tallBlockState;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (tallBlockState.canPlaceAt(world, pos) && world.isAir(pos.up())) {
            CTallFlowerBlock.placeAt(world, tallBlockState, pos, 2);
        }

    }
}
