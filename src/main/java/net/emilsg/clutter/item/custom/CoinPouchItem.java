package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class CoinPouchItem extends Item {
    private final int minAmount;
    private final int maxAmount;

    public CoinPouchItem(Settings settings, int minAmount, int maxAmount) {
        super(settings);
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Random random = new Random();
        ItemStack copperCoins = new ItemStack(ModItems.COPPER_COIN);
        copperCoins.setCount(minAmount + random.nextInt(maxAmount - minAmount + 1));
        if(world.isClient) {
            user.swingHand(hand);
        }
        if(!world.isClient()){
            user.dropItem(copperCoins, true);
            if(this.getRarity(user.getStackInHand(hand)) == Rarity.EPIC) {
                ItemStack silverCoins = new ItemStack(ModItems.SILVER_COIN);
                silverCoins.setCount(random.nextInt(2));
                user.dropItem(silverCoins, true);
            }
            itemStack.decrement(1);
            world.playSound(null, user.getBlockPos(), ModSounds.COIN_POUCH_USE, SoundCategory.PLAYERS,1f,1f);
        }
        return super.use(world, user, hand);
    }
}
