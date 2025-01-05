package net.emilsg.clutter.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class WallCandleBlock extends HorizontalFacingBlock implements Waterloggable {
    private static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    private static final BooleanProperty LIT = Properties.LIT;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 3, 11, 9, 5, 15),
            Block.createCuboidShape(6, 2, 15, 10, 6, 16),
            Block.createCuboidShape(7, 5, 11, 9, 6, 13),
            Block.createCuboidShape(6, 6, 10, 10, 7, 14),
            Block.createCuboidShape(7, 7, 11, 9, 13, 13)
    );
    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(1, 3, 7, 5, 5, 9),
            Block.createCuboidShape(0, 2, 6, 1, 6, 10),
            Block.createCuboidShape(3, 5, 7, 5, 6, 9),
            Block.createCuboidShape(2, 6, 6, 6, 7, 10),
            Block.createCuboidShape(3, 7, 7, 5, 13, 9)
    );
    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(7, 3, 1, 9, 5, 5),
            Block.createCuboidShape(6, 2, 0, 10, 6, 1),
            Block.createCuboidShape(7, 5, 3, 9, 6, 5),
            Block.createCuboidShape(6, 6, 2, 10, 7, 6),
            Block.createCuboidShape(7, 7, 3, 9, 13, 5)
    );
    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(11, 3, 7, 15, 5, 9),
            Block.createCuboidShape(15, 2, 6, 16, 6, 10),
            Block.createCuboidShape(11, 5, 7, 13, 6, 9),
            Block.createCuboidShape(10, 6, 6, 14, 7, 10),
            Block.createCuboidShape(11, 7, 7, 13, 13, 9)
    );

    public static final MapCodec<WallCandleBlock> CODEC = createCodec(WallCandleBlock::new);


    public WallCandleBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false).with(LIT, false));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(LIT) ? litLevel : 0;
    }

    private static void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3f) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17f) {
                world.playSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            }
        }
        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }

    public static void extinguish(@Nullable PlayerEntity player, BlockState state, World world, BlockPos pos) {
        WallCandleBlock.setLit(world, state, pos, false);
        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    private static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED) || !state.get(LIT)) {
            return;
        }

        Direction direction = state.get(FACING);
        Vec3d candlePosition;

        switch (direction) {
            case NORTH:
                candlePosition = new Vec3d(0.5, 0.90625, 0.75);
                break;
            case EAST:
                candlePosition = new Vec3d(0.25, 0.90625, 0.5);
                break;
            case SOUTH:
                candlePosition = new Vec3d(0.5, 0.90625, 0.25);
                break;
            case WEST:
                candlePosition = new Vec3d(0.75, 0.90625, 0.5);
                break;
            default:
                return;
        }

        Vec3d offset = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        WallCandleBlock.spawnCandleParticles(world, offset.add(candlePosition), random);
    }

    private boolean canPlaceOn(BlockView world, BlockPos pos, Direction side) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isSideSolidFullSquare(world, pos, side);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = state.get(FACING);
        return this.canPlaceOn(world, pos.offset(direction.getOpposite()), direction);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING, WATERLOGGED, LIT);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state.with(LIT, !state.get(WATERLOGGED)), world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState;
        if (!ctx.canReplaceExisting() && (blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getSide().getOpposite()))).isOf(this) && blockState.get(FACING) == ctx.getSide()) {
            return null;
        }
        blockState = this.getDefaultState();
        World worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        for (Direction direction : ctx.getPlacementDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = blockState.with(FACING, direction.getOpposite())).canPlaceAt(worldView, blockPos))
                continue;
            return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
        return null;
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {

            world.setBlockState(pos, state.with(WATERLOGGED, true), Block.NOTIFY_ALL);
            world.scheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            return true;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isEmpty() && state.get(LIT)) {
            WallCandleBlock.extinguish(player, state, world, pos);
            return ActionResult.SUCCESS;
        } else if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isOf(Items.FLINT_AND_STEEL) && !state.get(LIT)) {
            WallCandleBlock.setLit(world, state, pos, true);
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).damage(1, player, LivingEntity.getSlotForHand(hand));
            }
            return ActionResult.SUCCESS;
        } else if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isOf(Items.FIRE_CHARGE) && !state.get(LIT) && !player.getAbilities().creativeMode) {
            WallCandleBlock.setLit(world, state, pos, true);
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            return ActionResult.SUCCESS;
        } else if (player.getAbilities().allowModifyWorld && player.getStackInHand(hand).isOf(Items.FIRE_CHARGE) && !state.get(LIT) && player.getAbilities().creativeMode) {
            WallCandleBlock.setLit(world, state, pos, true);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && this.isNotLit(state)) {
            setLit(world, state, hit.getBlockPos(), true);
        }
    }

    protected boolean isNotLit(BlockState state) {
        return !(Boolean) state.get(LIT);
    }
}
