package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SmallTargetBlock extends HorizontalFacingBlock {
    public static final BooleanProperty UP = ModProperties.UP;

    public static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 14, 0, 2, 16, 16),
            Block.createCuboidShape(0, 1, 2, 2, 13, 14),
            Block.createCuboidShape(0, 11, 14, 2, 14, 15),
            Block.createCuboidShape(0, 11, 1, 2, 14, 2));
    public static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(14, 14, 0, 16, 16, 16),
            Block.createCuboidShape(14, 1, 2, 16, 13, 14),
            Block.createCuboidShape(14, 11, 1, 16, 14, 2),
            Block.createCuboidShape(14, 11, 14, 16, 14, 15));
    public static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 14, 0, 16, 16, 2),
            Block.createCuboidShape(2, 1, 0, 14, 13, 2),
            Block.createCuboidShape(1, 11, 0, 2, 14, 2),
            Block.createCuboidShape(14, 11, 0, 15, 14, 2));
    public static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 14, 14, 16, 16, 16),
            Block.createCuboidShape(2, 1, 14, 14, 13, 16),
            Block.createCuboidShape(14, 11, 14, 15, 14, 16),
            Block.createCuboidShape(1, 11, 14, 2, 14, 16));
    public static final VoxelShape UP_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(14, 14, 0, 16, 16, 16),
            Block.createCuboidShape(1, 14, 2, 13, 16, 14),
            Block.createCuboidShape(11, 14, 1, 14, 16, 2),
            Block.createCuboidShape(11, 14, 14, 14, 16, 15));
    public static final VoxelShape UP_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 14, 0, 2, 16, 16),
            Block.createCuboidShape(3, 14, 2, 15, 16, 14),
            Block.createCuboidShape(2, 14, 14, 5, 16, 15),
            Block.createCuboidShape(2, 14, 1, 5, 16, 2));
    public static final VoxelShape UP_NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 14, 0, 16, 16, 2),
            Block.createCuboidShape(2, 14, 3, 14, 16, 15),
            Block.createCuboidShape(1, 14, 2, 2, 16, 5),
            Block.createCuboidShape(14, 14, 2, 15, 16, 5));
    public static final VoxelShape UP_SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 14, 14, 16, 16, 16),
            Block.createCuboidShape(2, 14, 1, 14, 16, 13),
            Block.createCuboidShape(14, 14, 11, 15, 16, 14),
            Block.createCuboidShape(1, 14, 11, 2, 16, 14));


    public SmallTargetBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(UP, false));
    }

    public static final MapCodec<SmallTargetBlock> CODEC = createCodec(SmallTargetBlock::new);

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    private static void spawnParticles(BlockState state, WorldAccess world, BlockPos pos) {
        double x = (double) pos.getX() + 0.5 + 0.3 * ((world.getRandom().nextInt(1) + 1) * (world.getRandom().nextBoolean() ? 1 : -1));
        double y = (double) pos.getY() + 0.8 + (0.1 * (world.getRandom().nextBoolean() ? 1 : -1));
        double z = (double) pos.getZ() + 0.5 + 0.3 * ((world.getRandom().nextInt(1) + 1) * (world.getRandom().nextBoolean() ? 1 : -1));
        world.addParticle(new DustParticleEffect(DustParticleEffect.RED, 0.5f), x, y, z, 0.0, 0.0, 0.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, UP);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        state = state.cycle(UP);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
        world.playSound(null, pos, state.get(UP) ? SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN : SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.25f);
        world.emitGameEvent(player, state.get(UP) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return ActionResult.success(world.isClient);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        Direction hitSide = hit.getSide();
        BlockPos pos = hit.getBlockPos();

        if (hitSide == state.get(FACING) && !state.get(UP)) {
            world.setBlockState(pos, world.getBlockState(pos).with(UP, true), Block.NOTIFY_ALL);
            world.playSound(null, pos, state.get(UP) ? SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN : SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.25f);
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(UP)) {
            return switch (state.get(FACING)) {
                default -> UP_NORTH_SHAPE;
                case SOUTH -> UP_SOUTH_SHAPE;
                case WEST -> UP_WEST_SHAPE;
                case EAST -> UP_EAST_SHAPE;
            };
        } else {
            return switch (state.get(FACING)) {
                default -> NORTH_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case WEST -> WEST_SHAPE;
                case EAST -> EAST_SHAPE;
            };
        }
    }

    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(UP) ? 15 : 0;
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(UP) && random.nextFloat() < 0.5F) {
            spawnParticles(state, world, pos);
        }

    }

}
