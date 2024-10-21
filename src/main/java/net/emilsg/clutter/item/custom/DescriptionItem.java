package net.emilsg.clutter.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class DescriptionItem extends Item {
    private final String description;
    private final Formatting formatting;

    public DescriptionItem(Settings settings, String description, Formatting formatting) {
        super(settings);
        this.description = description;
        this.formatting = formatting;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable(description).formatted(formatting));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
