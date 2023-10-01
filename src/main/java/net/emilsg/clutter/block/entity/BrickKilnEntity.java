package net.emilsg.clutter.block.entity;

import net.emilsg.clutter.block.ModBlockEntities;
import net.emilsg.clutter.recipe.ModRecipeTypes;
import net.emilsg.clutter.screen.BrickKilnScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class BrickKilnEntity extends AbstractFurnaceBlockEntity {

    public BrickKilnEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BRICK_KILN_ENTITY, pos, state, ModRecipeTypes.BRICK_KILN_RECIPE_TYPE);
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BrickKilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.clutter.brick_kiln");
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 2;
    }

}
