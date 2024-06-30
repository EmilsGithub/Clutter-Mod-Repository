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

public class WoodenChairBlock extends SeatBlock {

    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 8, 11, 13, 16, 12),
            Block.createCuboidShape(4, 6, 12, 6, 16, 13),
            Block.createCuboidShape(10, 6, 12, 12, 16, 13),
            Block.createCuboidShape(11, 0, 10, 13, 6, 12),
            Block.createCuboidShape(3, 0, 10, 5, 6, 12),
            Block.createCuboidShape(3, 6, 3, 13, 8, 12),
            Block.createCuboidShape(3, 0, 3, 5, 6, 5),
            Block.createCuboidShape(11, 0, 3, 13, 6, 5)
    );
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(4, 8, 3, 5, 16, 13),
            Block.createCuboidShape(3, 6, 4, 4, 16, 6),
            Block.createCuboidShape(3, 6, 10, 4, 16, 12),
            Block.createCuboidShape(4, 0, 11, 6, 6, 13),
            Block.createCuboidShape(4, 0, 3, 6, 6, 5),
            Block.createCuboidShape(4, 6, 3, 13, 8, 13),
            Block.createCuboidShape(11, 0, 3, 13, 6, 5),
            Block.createCuboidShape(11, 0, 11, 13, 6, 13)
    );
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 8, 4, 13, 16, 5),
            Block.createCuboidShape(10, 6, 3, 12, 16, 4),
            Block.createCuboidShape(4, 6, 3, 6, 16, 4),
            Block.createCuboidShape(3, 0, 4, 5, 6, 6),
            Block.createCuboidShape(11, 0, 4, 13, 6, 6),
            Block.createCuboidShape(3, 6, 4, 13, 8, 13),
            Block.createCuboidShape(11, 0, 11, 13, 6, 13),
            Block.createCuboidShape(3, 0, 11, 5, 6, 13)
    );
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(11, 8, 3, 12, 16, 13),
            Block.createCuboidShape(12, 6, 10, 13, 16, 12),
            Block.createCuboidShape(12, 6, 4, 13, 16, 6),
            Block.createCuboidShape(10, 0, 3, 12, 6, 5),
            Block.createCuboidShape(10, 0, 11, 12, 6, 13),
            Block.createCuboidShape(3, 6, 3, 12, 8, 13),
            Block.createCuboidShape(3, 0, 11, 5, 6, 13),
            Block.createCuboidShape(3, 0, 3, 5, 6, 5)
    );

    public WoodenChairBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected float getYOffset() {
        return 0.3f;
    }

    @Override
    protected boolean isStrippable() {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction i = state.get(FACING);
        return switch (i) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> VoxelShapes.empty();
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
        }
        return super.onUse(state, world, pos, player, hand, hit);
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
        } else if (state.getBlock() == ModBlocks.CHERRY_CHAIR) {
            return ModBlocks.STRIPPED_CHERRY_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else if (state.getBlock() == ModBlocks.REDWOOD_CHAIR) {
            return ModBlocks.STRIPPED_REDWOOD_CHAIR.getDefaultState().with(FACING, state.get(FACING));
        } else {
            return state;
        }
    }
}
