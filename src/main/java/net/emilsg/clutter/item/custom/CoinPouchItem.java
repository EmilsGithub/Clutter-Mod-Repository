package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.component.DataComponentTypes;
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

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Rarity rarity = itemStack.getOrDefault(DataComponentTypes.RARITY, Rarity.COMMON);
        int rarityInt = getIntFromRarity(rarity);
        Random random = new Random();
        ItemStack copperCoins = new ItemStack(ModItems.COPPER_COIN);
        copperCoins.setCount(getMaxFromRarity(rarityInt) + random.nextInt(getMaxFromRarity(rarityInt) - getMinFromRarity(rarityInt) + 1));
        if (world.isClient) {
            user.swingHand(hand);
        }
        if (!world.isClient()) {
            user.dropItem(copperCoins, true);
            if (rarityInt == 3) {
                ItemStack silverCoins = new ItemStack(ModItems.SILVER_COIN);
                silverCoins.setCount(random.nextInt(2));
                user.dropItem(silverCoins, true);
            }
            itemStack.decrement(1);
            world.playSound(null, user.getBlockPos(), ModSounds.COIN_POUCH_USE, SoundCategory.PLAYERS, 1f, 1f);
        }
        return super.use(world, user, hand);
    }

    private int getMinFromRarity(int rarity) {
        return switch (rarity) {
            default -> 2;
            case 1 -> 3;
            case 2 -> 6;
            case 3 -> 9;
        };
    }

    private int getMaxFromRarity(int rarity) {
        return switch (rarity) {
            default -> 3;
            case 1 -> 6;
            case 2 -> 9;
            case 3 -> 12;
        };
    }

    public static int getIntFromRarity(Rarity rarity) {
        return switch (rarity) {
            default -> 1;
            case UNCOMMON -> 2;
            case RARE -> 3;
            case EPIC -> 4;
        };
    }

    //@Override
    //public Rarity getRarity(ItemStack stack) {
    //    if (stack.getNbt() == null || !stack.getNbt().contains("Rarity", 3)) return super.getRarity(stack);

    //    int rarity = stack.getNbt().getInt("Rarity");

    //    return switch (rarity) {
    //        default -> Rarity.COMMON;
    //        case 1 -> Rarity.UNCOMMON;
    //        case 2 -> Rarity.RARE;
    //        case 3 -> Rarity.EPIC;
    //    };
    //}

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Rarity rarity = stack.getOrDefault(DataComponentTypes.RARITY, Rarity.COMMON);
        String string = "clutter.coin_pouch_" + getIntFromRarity(rarity);
        MutableText mutableText = Text.translatable(string);
        mutableText.formatted(rarity.getFormatting());
        tooltip.add(mutableText);
    }
}
