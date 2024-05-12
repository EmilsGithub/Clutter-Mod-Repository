package net.emilsg.clutter.util;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ModCallbackRegistry {

    public static void handleCallbacks() {
        if (!Clutter.IS_SUPPLEMENTARIES_LOADED) handlePlacingBooks();
        handlePlacingNautilusShells();
    }

    public static void handlePlacingBooks() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(blockPos);
            Direction side = hitResult.getSide();
            BlockState stateSide = world.getBlockState(hitResult.getBlockPos().offset(side));
            BlockPos blockPosSide = hitResult.getBlockPos().offset(side);

            if (world.isClient || !world.canPlayerModifyAt(player, blockPos) || !player.getStackInHand(hand).isOf(Items.BOOK)) {
                return ActionResult.PASS;
            }

            if (player.getStackInHand(hand).isOf(Items.BOOK)) {
                if (state.isOf(ModBlocks.BOOK_PILE) && !state.get(ModProperties.LAYERS).equals(7) && !player.isSneaking()) {
                    world.setBlockState(blockPos, ModBlocks.BOOK_PILE.getDefaultState()
                            .with(ModProperties.LAYERS, state.get(ModProperties.LAYERS) + 1), Block.NOTIFY_ALL);
                    return decrementAndPlaySound(player, world, blockPos, hand, SoundEvents.ITEM_BOOK_PUT);
                }

                if (player.isSneaking()) {
                    if (!state.isOf(ModBlocks.BOOK_PILE) && stateSide.isReplaceable()) {
                        world.setBlockState(blockPosSide, ModBlocks.BOOK_PILE.getDefaultState()
                                .with(Properties.HORIZONTAL_FACING, player.getHorizontalFacing()), Block.NOTIFY_ALL);
                        return decrementAndPlaySound(player, world, blockPos, hand, SoundEvents.ITEM_BOOK_PUT);
                    }
                }
            }
            return ActionResult.PASS;
        });
    }

    public static void handlePlacingNautilusShells() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Direction side = hitResult.getSide();
            BlockState stateSide = world.getBlockState(hitResult.getBlockPos().offset(side));
            BlockPos blockPosSide = hitResult.getBlockPos().offset(side);

            if (world.isClient || !world.canPlayerModifyAt(player, blockPosSide) || !player.getStackInHand(hand).isOf(Items.NAUTILUS_SHELL)) {
                return ActionResult.PASS;
            }

            if (player.getStackInHand(hand).isOf(Items.NAUTILUS_SHELL) && player.isSneaking()) {
                if (stateSide.isReplaceable() && Block.sideCoversSmallSquare(world, blockPosSide.down(), Direction.UP)) {
                    world.setBlockState(blockPosSide, ModBlocks.NAUTILUS_SHELL_BLOCK.getDefaultState()
                            .with(Properties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite())
                            .with(Properties.WATERLOGGED, world.getFluidState(blockPosSide).isOf(Fluids.WATER)), Block.NOTIFY_ALL);
                    return decrementAndPlaySound(player, world, blockPosSide, hand, SoundEvents.BLOCK_BONE_BLOCK_PLACE);
                }
            }
            return ActionResult.PASS;
        });
    }

    private static ActionResult decrementAndPlaySound(PlayerEntity player, World world, BlockPos blockPos, Hand hand, SoundEvent events) {
        if (!player.getAbilities().creativeMode) player.getStackInHand(hand).decrement(1);
        world.playSound(null, blockPos, events, SoundCategory.BLOCKS, 1.0f, 0.9f);
        return ActionResult.SUCCESS;
    }
}
