package net.emilsg.clutter.screen;

import net.emilsg.clutter.recipe.BrickKilnRecipeBookScreen;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BrickKilnScreen extends AbstractFurnaceScreen<BrickKilnScreenHandler> {

    private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/container/furnace.png");

    public BrickKilnScreen(BrickKilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new BrickKilnRecipeBookScreen(), inventory, title, BACKGROUND_TEXTURE);
    }
}
