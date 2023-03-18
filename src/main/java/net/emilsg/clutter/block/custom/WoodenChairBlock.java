package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.util.ModBlockTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WoodenChairBlock extends SeatBlock{
    protected static final VoxelShape LEGS_AND_SEAT = VoxelShapes.union(
            Block.createCuboidShape(3.0, 6.0, 3.0, 13.0, 8.0, 13.0),
            Block.createCuboidShape(3.0, 0.0, 3.0, 5.0, 6.0, 5.0),
            Block.createCuboidShape(11.0, 0.0, 3.0, 13.0, 6.0, 5.0),
            Block.createCuboidShape(3.0, 0.0, 11.0, 5.0, 6.0, 13.0),
            Block.createCuboidShape(11.0, 0.0, 11.0, 13.0, 6.0, 13.0)
    );
    protected static final VoxelShape NORTH = VoxelShapes.union(
            Block.createCuboidShape(3.0, 8.0, 12.0, 13.0, 16.0, 13.0),
            LEGS_AND_SEAT
    );
    protected static final VoxelShape EAST = VoxelShapes.union(
            Block.createCuboidShape(3.0, 8.0, 3.0, 4.0, 16.0, 13.0),
            LEGS_AND_SEAT
    );
    protected static final VoxelShape SOUTH = VoxelShapes.union(
            Block.createCuboidShape(3.0, 6.0, 3.0, 13.0, 8.0, 4.0),
            LEGS_AND_SEAT
    );
    protected static final VoxelShape WEST = VoxelShapes.union(
            Block.createCuboidShape(12, 8.0, 3.0, 13.0, 16.0, 13.0),
            LEGS_AND_SEAT
    );

    public WoodenChairBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction i = state.get(FACING);
        return switch (i) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> VoxelShapes.empty(); // return an empty shape if no matching direction is found
        };
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof AxeItem && state.isIn(ModBlockTags.STRIPPABLE_CHAIRS)) {
            BlockState strippedState = getStrippedState(state);
            world.setBlockState(pos, strippedState);
            world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative()) {
                itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    private BlockState getStrippedState(BlockState state) {
        if (state.getBlock() == ModBlocks.OAK_CHAIR) {
            return ModBlocks.STRIPPED_OAK_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.SPRUCE_CHAIR) {
            return ModBlocks.STRIPPED_SPRUCE_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.BIRCH_CHAIR) {
            return ModBlocks.STRIPPED_BIRCH_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.JUNGLE_CHAIR) {
            return ModBlocks.STRIPPED_JUNGLE_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.ACACIA_CHAIR) {
            return ModBlocks.STRIPPED_ACACIA_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.DARK_OAK_CHAIR) {
            return ModBlocks.STRIPPED_DARK_OAK_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.MANGROVE_CHAIR) {
            return ModBlocks.STRIPPED_MANGROVE_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.CRIMSON_CHAIR) {
            return ModBlocks.STRIPPED_CRIMSON_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.WARPED_CHAIR) {
            return ModBlocks.STRIPPED_WARPED_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else {
            return state;
        }
    }
}
