package net.emilsg.clutter.recipe;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.Set;

public class BrickKilnRecipeBookScreen extends AbstractFurnaceRecipeBookScreen {

    private static final Text TOGGLE_BRICK_KILN_RECIPES_TEXT = Text.translatable("gui.clutter.recipebook.toggleRecipes.brick_kiln");

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_BRICK_KILN_RECIPES_TEXT;
    }

    @Override
    protected Set<Item> getAllowedFuels() {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
    }
}
