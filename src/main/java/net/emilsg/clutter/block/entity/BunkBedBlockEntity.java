package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class BunkBedBlockEntity extends BedBlockEntity {

    public BunkBedBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public BunkBedBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(pos, state, color);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.MOD_BUNK_BED_BLOCK_ENTITY;
    }

    @Override
    public boolean supports(BlockState state) {
        return this.getType().supports(state);
    }
}
