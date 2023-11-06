package net.emilsg.clutter.compat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class KilningCategory implements DisplayCategory<BasicDisplay> {
    public static final Identifier TEXTURE = new Identifier(Clutter.MOD_ID, "textures/gui/container/brick_kiln_rei.png");
    public static final CategoryIdentifier<KilningDisplay> KILNING = CategoryIdentifier.of(Clutter.MOD_ID, "kilning");

    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return KILNING;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("recipe_type.clutter.kilning");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.BRICK_KILN.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 87, bounds.getCenterY() - 35);
        List<Widget> widgets = new LinkedList<>();

        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 176, 79)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 56, startPoint.y + 26))
                .entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 116, startPoint.y + 35))
                .markOutput().entries(display.getOutputEntries().get(0)));

        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 57, startPoint.y + 45))
                .animationDurationMS(10000));

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 79, startPoint.y + 34))
                .animationDurationTicks(100));


        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 80;
    }
}
