package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.SmallLilyPadBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SmallLilyPadBlockItem extends PlaceableOnWaterItem {

    public SmallLilyPadBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        IntProperty pads = SmallLilyPadBlock.PAD_AMOUNT;
        BlockPos pos = context.getBlockPos();
        BlockState state = context.getWorld().getBlockState(pos);
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        Hand hand = context.getHand();

        if(world.isClient && state.isOf(ModBlocks.SMALL_LILY_PADS) && state.get(SmallLilyPadBlock.PAD_AMOUNT) < 4) {
            return ActionResult.SUCCESS;
        }

        if(!world.isClient && player != null && state.isOf(ModBlocks.SMALL_LILY_PADS) && state.get(SmallLilyPadBlock.PAD_AMOUNT) < 4) {
            world.setBlockState(pos, state.with(pads, state.get(pads) + 1), Block.NOTIFY_ALL);
            player.getStackInHand(hand).decrement(1);
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }
}
