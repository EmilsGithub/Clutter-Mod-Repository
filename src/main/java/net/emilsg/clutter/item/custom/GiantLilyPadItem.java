package net.emilsg.clutter.item.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.plants.GiantLilyPadBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import static net.emilsg.clutter.block.custom.plants.GiantLilyPadBlock.LILY_PAD_DIRECTIONS;

public class GiantLilyPadItem extends BlockItem {

    public GiantLilyPadItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult rayTraceResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        ItemStack stackInHand = user.getStackInHand(hand);

        if (rayTraceResult.getType() == BlockHitResult.Type.MISS)
            return ActionResult.PASS;
        else if (rayTraceResult.getType() == BlockHitResult.Type.BLOCK) {
            BlockPos blockpos = rayTraceResult.getBlockPos();
            Direction direction = rayTraceResult.getSide();
            if (!world.canPlayerModifyAt(user, blockpos) || !user.canPlaceOn(blockpos.offset(direction), direction, stackInHand)) {
                return ActionResult.FAIL;
            }

            BlockPos blockPosUp = blockpos.up();
            BlockState blockstate = world.getBlockState(blockpos);
            FluidState fluidState = world.getFluidState(blockpos);
            if ((fluidState.getFluid() == Fluids.WATER || blockstate.getBlock() instanceof IceBlock) && world.isAir(blockPosUp)) {
                if (world.getBlockState(blockPosUp.north()).isReplaceable()
                        && world.getBlockState(blockPosUp.east()).isReplaceable()
                        && world.getBlockState(blockPosUp.north().east()).isReplaceable()
                        && world.getBlockState(blockPosUp.north()).isAir()
                        && world.getBlockState(blockPosUp.east()).isAir()
                        && world.getBlockState(blockPosUp.north().east()).isAir()
                        && world.getFluidState(blockPosUp.down()).isOf(Fluids.WATER)
                        && world.getFluidState(blockPosUp.north().down()).isOf(Fluids.WATER)
                        && world.getFluidState(blockPosUp.east().down()).isOf(Fluids.WATER)
                        && world.getFluidState(blockPosUp.north().east().down()).isOf(Fluids.WATER)) {
                    world.setBlockState(blockPosUp, ModBlocks.GIANT_LILY_PAD.getDefaultState());
                    place(world, blockPosUp, ModBlocks.GIANT_LILY_PAD.getDefaultState());
                    if (!user.getAbilities().creativeMode) {
                        stackInHand.decrement(1);
                    }
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                    world.playSound(null, blockpos, SoundEvents.BLOCK_LILY_PAD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }

    public void place(World world, BlockPos pos, BlockState state) {
        BlockPos blockPosNorth = pos.north();
        BlockPos blockPosEast = pos.east();
        BlockPos blockPosNorthEast = pos.north().east();
        world.setBlockState(blockPosNorth, state.with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.NORTH_WEST), 3);
        world.setBlockState(blockPosEast, state.with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.SOUTH_EAST), 3);
        world.setBlockState(blockPosNorthEast, state.with(LILY_PAD_DIRECTIONS, GiantLilyPadBlock.LilyPadDirections.NORTH_EAST), 3);
    }

}
