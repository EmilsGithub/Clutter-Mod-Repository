package net.emilsg.clutter.screen;

import net.emilsg.clutter.recipe.ModRecipeTypes;
import net.emilsg.clutter.screen.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class BrickKilnScreenHandler extends AbstractFurnaceScreenHandler {

    public BrickKilnScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.BRICK_KILN_SCREEN_HANDLER, ModRecipeTypes.BRICK_KILN_RECIPE_TYPE, RecipeBookCategory.FURNACE, syncId, playerInventory);
    }

    public BrickKilnScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.BRICK_KILN_SCREEN_HANDLER, ModRecipeTypes.BRICK_KILN_RECIPE_TYPE, RecipeBookCategory.FURNACE, syncId, playerInventory, inventory, propertyDelegate);
    }
}
