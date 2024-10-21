package net.emilsg.clutter.screen;

import net.emilsg.clutter.block.entity.BrickKilnFurnaceBlockEntity;
import net.emilsg.clutter.recipe.KilningRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class BrickKilnScreenHandler extends AbstractFurnaceScreenHandler {
    public BrickKilnScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.BRICK_KILN_SCREEN_HANDLER, KilningRecipe.Type.INSTANCE, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public BrickKilnScreenHandler(int syncId, PlayerInventory playerInventory, BrickKilnFurnaceBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.BRICK_KILN_SCREEN_HANDLER, KilningRecipe.Type.INSTANCE, RecipeBookCategory.FURNACE, syncId, playerInventory, blockEntity, propertyDelegate);
    }
}
