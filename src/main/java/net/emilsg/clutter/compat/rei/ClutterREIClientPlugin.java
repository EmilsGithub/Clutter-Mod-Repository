package net.emilsg.clutter.compat.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.recipe.KilningRecipe;
import net.emilsg.clutter.screen.BrickKilnScreen;

public class ClutterREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new KilningCategory());
        registry.addWorkstations(KilningCategory.KILNING, EntryStacks.of(ModBlocks.BRICK_KILN));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(KilningRecipe.class, KilningRecipe.Type.INSTANCE, KilningDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(((screen.width - 176) / 2) + 78, ((screen.height - 166) / 2) + 31, 25, 18),
                BrickKilnScreen.class, KilningCategory.KILNING);
    }
}
