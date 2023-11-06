package net.emilsg.clutter.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class PlaceOnWaterItem extends AliasedBlockItem {
    private final Block placingBlock;

    public PlaceOnWaterItem(Block block, Settings settings) {
        super(block, settings);
        this.placingBlock = block;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult rayTraceResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        ItemStack stackInHand = user.getStackInHand(hand);

        if (rayTraceResult.getType() == BlockHitResult.Type.MISS) return new TypedActionResult<>(ActionResult.PASS, stackInHand);
        else if (rayTraceResult.getType() == BlockHitResult.Type.BLOCK) {
            BlockPos blockpos = rayTraceResult.getBlockPos();
            Direction direction = rayTraceResult.getSide();
            if (!world.canPlayerModifyAt(user, blockpos) || !user.canPlaceOn(blockpos.offset(direction), direction, stackInHand)) {
                return new TypedActionResult<>(ActionResult.FAIL, stackInHand);
            }

            BlockPos blockPosUp = blockpos.up();
            BlockState blockstate = world.getBlockState(blockpos);
            FluidState fluidState = world.getFluidState(blockpos);
            if ((fluidState.getFluid() == Fluids.WATER || blockstate.getBlock() instanceof IceBlock) && world.isAir(blockPosUp)) {
                    world.setBlockState(blockPosUp, placingBlock.getDefaultState().withIfExists(Properties.HORIZONTAL_FACING, user.getHorizontalFacing()));

                    if (!user.getAbilities().creativeMode) {
                        stackInHand.decrement(1);
                    }
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    world.playSound(null, blockpos, SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F);
                    return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
                }
            }
        return new TypedActionResult<>(ActionResult.FAIL, stackInHand);
    }
}
