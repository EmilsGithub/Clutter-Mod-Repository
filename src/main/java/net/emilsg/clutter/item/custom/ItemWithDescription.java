package net.emilsg.clutter.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithDescription extends Item {
    private final String description;
    private final Formatting formatting;

    public ItemWithDescription(Settings settings, String description, Formatting formatting) {
        super(settings);
        this.description = description;
        this.formatting = formatting;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(description).formatted(formatting));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
