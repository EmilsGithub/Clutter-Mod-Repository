package net.emilsg.clutter.screen;

import net.emilsg.clutter.Clutter;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BrickKilnScreen extends AbstractFurnaceScreen<BrickKilnScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(Clutter.MOD_ID, "textures/gui/container/brick_kiln.png");
    private static final Identifier LIT_PROGRESS_TEXTURE = Identifier.of("container/furnace/lit_progress");
    private static final Identifier BURN_PROGRESS_TEXTURE = Identifier.of("container/furnace/burn_progress");

    public BrickKilnScreen(BrickKilnScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, new KilningRecipeBookComponent(), inventory, title, TEXTURE, LIT_PROGRESS_TEXTURE, BURN_PROGRESS_TEXTURE);
    }
}
