package net.emilsg.clutter.block.custom.coins;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CopperCoinStackBlock extends AbstractCoinStackBlock {

    public CopperCoinStackBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient && player.getStackInHand(hand).isOf(ModBlocks.COPPER_COIN_STACK.asItem()) && hand.equals(Hand.MAIN_HAND) && state.get(COIN_LAYERS) < 8 && !player.isSneaking()) {
            return ActionResult.SUCCESS;
        }
        if (!world.isClient && player.getStackInHand(hand).isOf(ModBlocks.COPPER_COIN_STACK.asItem()) && hand.equals(Hand.MAIN_HAND) && state.get(COIN_LAYERS) < 8 && !player.isSneaking()) {
            world.setBlockState(pos, state.with(COIN_LAYERS, state.get(COIN_LAYERS) + 1), Block.NOTIFY_ALL);
            world.playSound(null, pos, ModSounds.COIN_PILE_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
