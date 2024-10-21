package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.recipe.KilningRecipe;
import net.emilsg.clutter.screen.BrickKilnScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class BrickKilnFurnaceBlockEntity extends AbstractFurnaceBlockEntity {

    public BrickKilnFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BRICK_KILN_BLOCK_ENTITY, pos, state, KilningRecipe.Type.INSTANCE);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("block.clutter.brick_kiln");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BrickKilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}