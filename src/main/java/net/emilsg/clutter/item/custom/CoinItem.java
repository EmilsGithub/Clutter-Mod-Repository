package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.block.custom.coins.AbstractCoinStackBlock;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoinItem extends Item {
    private final Block coinStackBlock;

    public CoinItem(Settings settings, Block coinStackBlock) {
        super(settings);
        this.coinStackBlock = coinStackBlock;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        int coinCount = stack.getCount();

        if (coinCount < 8) return ActionResult.PASS;

        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(blockPos);

        if (state.isOf(coinStackBlock) && state.get(ModProperties.COIN_LAYERS) != 8) {
            world.setBlockState(blockPos, AbstractCoinStackBlock.increaseLayersByOne(state), Block.NOTIFY_ALL);
            stack.decrement(8);
            return ActionResult.success(world.isClient);
        }


        return ActionResult.PASS;
    }
}
