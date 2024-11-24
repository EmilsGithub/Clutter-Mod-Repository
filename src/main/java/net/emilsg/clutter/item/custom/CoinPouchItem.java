package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class CoinPouchItem extends Item {

    public CoinPouchItem(Settings settings) {
        super(settings);
    }

    public static int getIntFromRarity(Rarity rarity) {
        return switch (rarity) {
            default -> 0;
            case UNCOMMON -> 1;
            case RARE -> 2;
            case EPIC -> 3;
        };
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Rarity rarity = itemStack.getRarity();
        Random random = new Random();
        ItemStack copperCoins = new ItemStack(ModItems.COPPER_COIN);
        copperCoins.setCount(getMaxFromRarity(rarity) + random.nextInt(getMaxFromRarity(rarity) - getMinFromRarity(rarity) + 1));
        if (world.isClient) {
            user.swingHand(hand);
        }
        if (!world.isClient()) {
            user.dropItem(copperCoins, true);
            if (rarity == Rarity.EPIC) {
                ItemStack silverCoins = new ItemStack(ModItems.SILVER_COIN);
                silverCoins.setCount(random.nextInt(2));
                user.dropItem(silverCoins, true);
            }
            if (!user.getAbilities().creativeMode) itemStack.decrement(1);
            world.playSound(null, user.getBlockPos(), ModSounds.COIN_POUCH_USE, SoundCategory.PLAYERS, 1f, 1f);
        }
        return super.use(world, user, hand);
    }

    private int getMinFromRarity(Rarity rarity) {
        return switch (rarity) {
            default -> 2;
            case UNCOMMON -> 3;
            case RARE -> 6;
            case EPIC -> 9;
        };
    }

    private int getMaxFromRarity(Rarity rarity) {
        return switch (rarity) {
            default -> 3;
            case UNCOMMON -> 6;
            case RARE -> 9;
            case EPIC -> 12;
        };
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Rarity rarity = stack.getRarity();
        String string = "clutter.coin_pouch_" + rarity.toString().toLowerCase();
        MutableText mutableText = Text.translatable(string);
        mutableText.formatted(rarity.getFormatting());
        tooltip.add(mutableText);
    }
}
