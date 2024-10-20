package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class CoinPouchItem extends Item {

    public CoinPouchItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        NbtCompound nbtCompound = itemStack.getNbt();
        if (nbtCompound == null) return TypedActionResult.fail(itemStack);
        int rarity = nbtCompound.getInt("Rarity");
        Random random = new Random();
        ItemStack copperCoins = new ItemStack(ModItems.COPPER_COIN);
        copperCoins.setCount(getMaxFromRarity(rarity) + random.nextInt(getMaxFromRarity(rarity) - getMinFromRarity(rarity) + 1));
        if (world.isClient) {
            user.swingHand(hand);
        }
        if (!world.isClient()) {
            user.dropItem(copperCoins, true);
            if (rarity == 3) {
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

    @Override
    public Rarity getRarity(ItemStack stack) {
        if (stack.getNbt() == null || !stack.getNbt().contains("Rarity", 3)) return super.getRarity(stack);

        int rarity = stack.getNbt().getInt("Rarity");

        return switch (rarity) {
            default -> Rarity.COMMON;
            case 1 -> Rarity.UNCOMMON;
            case 2 -> Rarity.RARE;
            case 3 -> Rarity.EPIC;
        };
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null && nbtCompound.contains("Rarity", 3)) {
            int rarity = nbtCompound.getInt("Rarity");

            Formatting formatting = switch (rarity) {
                default -> Formatting.WHITE;
                case 1 -> Formatting.YELLOW;
                case 2 -> Formatting.AQUA;
                case 3 -> Formatting.LIGHT_PURPLE;
            };

            String string = "clutter.coin_pouch_" + rarity;

            MutableText mutableText = Text.translatable(string);
            mutableText.formatted(formatting);
            tooltip.add(mutableText);
        } else {
            tooltip.add(Text.translatable("clutter.coin_pouch_empty"));
        }
    }
}
