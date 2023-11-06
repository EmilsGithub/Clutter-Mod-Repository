package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
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
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ReworkedCandleHolderBlock extends Block implements Waterloggable {
    public static final EnumProperty<Type> TYPE = EnumProperty.of("type", Type.class);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty CANDLES = ModProperties.CANDLES;
    public static final EnumProperty<CandleColor> CANDLE_COLOR = EnumProperty.of("candle_color", CandleColor.class);

    private static final VoxelShape NORTH_FLOOR_SHAPE = VoxelShapes.union(Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), Block.createCuboidShape(7.0, 1.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(0.0, 9.0, 6.0, 4.0, 10.0, 10.0), Block.createCuboidShape(12.0, 9.0, 6.0, 16.0, 10.0, 10.0), Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0), Block.createCuboidShape(1.0, 10.0, 7.0, 3.0, 13.0, 9.0), Block.createCuboidShape(13.0, 10.0, 7.0, 15.0, 14.0, 9.0), Block.createCuboidShape(1.0, 8.0, 7.5, 4.0, 9.0, 8.5), Block.createCuboidShape(3.0, 6.0, 7.5, 6.0, 8.0, 8.5), Block.createCuboidShape(10.0, 6.0, 7.5, 13.0, 8.0, 8.5), Block.createCuboidShape(6.0, 5.0, 7.5, 10.0, 8.0, 8.5), Block.createCuboidShape(12.0, 8.0, 7.5, 15.0, 9.0, 8.5));
    private static final VoxelShape EAST_FLOOR_SHAPE = VoxelShapes.union(Block.createCuboidShape(6.0, 9.0, 0.0, 10.0, 10.0, 4.0), Block.createCuboidShape(6.0, 9.0, 12.0, 10.0, 10.0, 16.0), Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0), Block.createCuboidShape(7.5, 8.0, 1.0, 8.5, 9.0, 4.0), Block.createCuboidShape(7.5, 6.0, 3.0, 8.5, 8.0, 6.0), Block.createCuboidShape(7.5, 6.0, 10.0, 8.5, 8.0, 13.0), Block.createCuboidShape(7.5, 5.0, 6.0, 8.5, 8.0, 10.0), Block.createCuboidShape(7.5, 8.0, 12.0, 8.5, 9.0, 15.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), Block.createCuboidShape(7.0, 10.0, 1.0, 9.0, 13.0, 3.0), Block.createCuboidShape(7.0, 10.0, 13.0, 9.0, 14.0, 15.0));
    private static final VoxelShape SOUTH_FLOOR_SHAPE = VoxelShapes.union(Block.createCuboidShape(6.0, 9.0, 0.0, 10.0, 10.0, 4.0), Block.createCuboidShape(6.0, 9.0, 12.0, 10.0, 10.0, 16.0), Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0), Block.createCuboidShape(7.5, 8.0, 1.0, 8.5, 9.0, 4.0), Block.createCuboidShape(7.5, 6.0, 3.0, 8.5, 8.0, 6.0), Block.createCuboidShape(7.5, 6.0, 10.0, 8.5, 8.0, 13.0), Block.createCuboidShape(7.5, 5.0, 6.0, 8.5, 8.0, 10.0), Block.createCuboidShape(7.5, 8.0, 12.0, 8.5, 9.0, 15.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), Block.createCuboidShape(7.0, 10.0, 1.0, 9.0, 14.0, 3.0), Block.createCuboidShape(7.0, 10.0, 13.0, 9.0, 13.0, 15.0));
    private static final VoxelShape WEST_FLOOR_SHAPE = VoxelShapes.union(Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 1.0, 10.0), Block.createCuboidShape(7.0, 1.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(0.0, 9.0, 6.0, 4.0, 10.0, 10.0), Block.createCuboidShape(12.0, 9.0, 6.0, 16.0, 10.0, 10.0), Block.createCuboidShape(6.0, 10.0, 6.0, 10.0, 11.0, 10.0), Block.createCuboidShape(1.0, 10.0, 7.0, 3.0, 14.0, 9.0), Block.createCuboidShape(13.0, 10.0, 7.0, 15.0, 13.0, 9.0), Block.createCuboidShape(1.0, 8.0, 7.5, 4.0, 9.0, 8.5), Block.createCuboidShape(3.0, 6.0, 7.5, 6.0, 8.0, 8.5), Block.createCuboidShape(10.0, 6.0, 7.5, 13.0, 8.0, 8.5), Block.createCuboidShape(6.0, 5.0, 7.5, 10.0, 8.0, 8.5), Block.createCuboidShape(12.0, 8.0, 7.5, 15.0, 9.0, 8.5));

    private static final VoxelShape NORTH_WALL_SHAPE = VoxelShapes.union(Block.createCuboidShape(7, 3, 11, 9, 5, 15), Block.createCuboidShape(6, 2, 15, 10, 6, 16), Block.createCuboidShape(7, 5, 11, 9, 6, 13), Block.createCuboidShape(6, 6, 10, 10, 7, 14), Block.createCuboidShape(7, 7, 11, 9, 13, 13));
    private static final VoxelShape EAST_WALL_SHAPE = VoxelShapes.union(Block.createCuboidShape(1, 3, 7, 5, 5, 9), Block.createCuboidShape(0, 2, 6, 1, 6, 10), Block.createCuboidShape(3, 5, 7, 5, 6, 9), Block.createCuboidShape(2, 6, 6, 6, 7, 10), Block.createCuboidShape(3, 7, 7, 5, 13, 9));
    private static final VoxelShape SOUTH_WALL_SHAPE = VoxelShapes.union(Block.createCuboidShape(11, 3, 7, 15, 5, 9), Block.createCuboidShape(15, 2, 6, 16, 6, 10), Block.createCuboidShape(11, 5, 7, 13, 6, 9), Block.createCuboidShape(10, 6, 6, 14, 7, 10), Block.createCuboidShape(11, 7, 7, 13, 13, 9));
    private static final VoxelShape WEST_WALL_SHAPE = VoxelShapes.union(Block.createCuboidShape(7, 3, 1, 9, 5, 5), Block.createCuboidShape(6, 2, 0, 10, 6, 1), Block.createCuboidShape(7, 5, 3, 9, 6, 5), Block.createCuboidShape(6, 6, 2, 10, 7, 6), Block.createCuboidShape(7, 7, 3, 9, 13, 5));

    private static final VoxelShape CEILING_SHAPE = VoxelShapes.union(Block.createCuboidShape(6.5, 1.0, 6.5, 9.5, 16.0, 9.5), Block.createCuboidShape(1.5, 1.0, 1.0, 14.5, 8.0, 15.0));

    private static final Vec3d[] CANDLE_POSITIONS_NORTH_FLOOR = {new Vec3d(0.125, 0.90625, 0.5), new Vec3d(0.5, 1.09375, 0.5), new Vec3d(0.875, 0.96875, 0.5)};
    private static final Vec3d[] CANDLE_POSITIONS_SOUTH_FLOOR = {new Vec3d(0.125, 0.96875, 0.5), new Vec3d(0.5, 1.09375, 0.5), new Vec3d(0.875, 0.90625, 0.5)};
    private static final Vec3d[] CANDLE_POSITIONS_EAST_FLOOR = {new Vec3d(0.5, 0.90625, 0.125), new Vec3d(0.5, 1.09375, 0.5), new Vec3d(0.5, 0.96875, 0.875)};
    private static final Vec3d[] CANDLE_POSITIONS_WEST_FLOOR = {new Vec3d(0.5, 0.96875, 0.125), new Vec3d(0.5, 1.09375, 0.5), new Vec3d(0.5, 0.90625, 0.875)};

    private static final Vec3d CANDLE_POSITION_NORTH_WALL = new Vec3d(0.5, 0.90625, 0.75);
    private static final Vec3d CANDLE_POSITION_EAST_WALL = new Vec3d(0.25, 0.90625, 0.5);
    private static final Vec3d CANDLE_POSITION_SOUTH_WALL = new Vec3d(0.5, 0.90625, 0.25);
    private static final Vec3d CANDLE_POSITION_WEST_WALL = new Vec3d(0.75, 0.90625, 0.5);

    private static final Vec3d[] CANDLE_POSITIONS_CEILING = {new Vec3d(0.1875, 0.59375, 0.5), new Vec3d(0.5, 0.59375, 0.8125), new Vec3d(0.5, 0.59375, 0.1875), new Vec3d(0.8125, 0.59375, 0.5)};
    private static final Map<Item, CandleColor> CANDLE_TO_COLOR = new HashMap<>();

    static {
        CANDLE_TO_COLOR.put(Items.WHITE_CANDLE, CandleColor.WHITE);
        CANDLE_TO_COLOR.put(Items.LIGHT_GRAY_CANDLE, CandleColor.LIGHT_GRAY);
        CANDLE_TO_COLOR.put(Items.GRAY_CANDLE, CandleColor.GRAY);
        CANDLE_TO_COLOR.put(Items.BLACK_CANDLE, CandleColor.BLACK);
        CANDLE_TO_COLOR.put(Items.BROWN_CANDLE, CandleColor.BROWN);
        CANDLE_TO_COLOR.put(Items.RED_CANDLE, CandleColor.RED);
        CANDLE_TO_COLOR.put(Items.ORANGE_CANDLE, CandleColor.ORANGE);
        CANDLE_TO_COLOR.put(Items.YELLOW_CANDLE, CandleColor.YELLOW);
        CANDLE_TO_COLOR.put(Items.LIME_CANDLE, CandleColor.LIME);
        CANDLE_TO_COLOR.put(Items.GREEN_CANDLE, CandleColor.GREEN);
        CANDLE_TO_COLOR.put(Items.CYAN_CANDLE, CandleColor.CYAN);
        CANDLE_TO_COLOR.put(Items.LIGHT_BLUE_CANDLE, CandleColor.LIGHT_BLUE);
        CANDLE_TO_COLOR.put(Items.BLUE_CANDLE, CandleColor.BLUE);
        CANDLE_TO_COLOR.put(Items.PURPLE_CANDLE, CandleColor.PURPLE);
        CANDLE_TO_COLOR.put(Items.MAGENTA_CANDLE, CandleColor.MAGENTA);
        CANDLE_TO_COLOR.put(Items.PINK_CANDLE, CandleColor.PINK);
        CANDLE_TO_COLOR.put(Items.CANDLE, CandleColor.NONE);
    }



    public ReworkedCandleHolderBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(WATERLOGGED, false).with(LIT, false).with(FACING, Direction.NORTH).with(TYPE, Type.FLOOR).with(CANDLES, false).with(CANDLE_COLOR, CandleColor.NONE));
    }

    public enum Type implements StringIdentifiable {
        FLOOR("floor"),
        CEILING("ceiling"),
        WALL("wall");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public enum CandleColor implements StringIdentifiable {
        WHITE("white"),
        LIGHT_GRAY("light_gray"),
        GRAY("gray"),
        BLACK("black"),
        BROWN("brown"),
        RED("red"),
        ORANGE("orange"),
        YELLOW("yellow"),
        LIME("lime"),
        GREEN("green"),
        CYAN("cyan"),
        LIGHT_BLUE("light_blue"),
        BLUE("blue"),
        PURPLE("purple"),
        MAGENTA("magenta"),
        PINK("pink"),
        NONE("none");

        private final String name;

        CandleColor(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, WATERLOGGED, LIT, CANDLES, CANDLE_COLOR);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState();
        Direction direction = ctx.getSide();
        World worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = worldView.getFluidState(blockPos);

        state = setTypeBasedOnDirection(state, direction, ctx.getHorizontalPlayerFacing().getOpposite());

        if (isVertical(direction)) {
            return state.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }

        return findWallState(state, worldView, blockPos, ctx, fluidState);
    }

    private BlockState setTypeBasedOnDirection(BlockState state, Direction direction, Direction horizontalFacing) {
        Type type = switch (direction) {
            case UP -> Type.FLOOR;
            case DOWN -> Type.CEILING;
            default -> Type.WALL;
        };

        return state.with(TYPE, type).with(FACING, isVertical(direction) ? horizontalFacing : Direction.NORTH);
    }

    private boolean isVertical(Direction direction) {
        return direction == Direction.DOWN || direction == Direction.UP;
    }

    private BlockState findWallState(BlockState state, World world, BlockPos pos, ItemPlacementContext ctx, FluidState fluidState) {
        for (Direction wallDirection : ctx.getPlacementDirections()) {
            if (wallDirection.getAxis().isHorizontal()) {
                BlockState newState = state.with(FACING, wallDirection.getOpposite());
                if (newState.canPlaceAt(world, pos)) {
                    return newState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }
        return state;
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Type type = state.get(TYPE);
        Direction direction = state.get(FACING);

        if (type == Type.CEILING) return CEILING_SHAPE;

        return type == Type.WALL ? getWallShape(direction) : getFloorShape(direction);
    }

    private VoxelShape getWallShape(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH_WALL_SHAPE;
            case EAST -> EAST_WALL_SHAPE;
            case WEST -> SOUTH_WALL_SHAPE;
            default -> WEST_WALL_SHAPE;
        };
    }

    private VoxelShape getFloorShape(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH_FLOOR_SHAPE;
            case EAST -> EAST_FLOOR_SHAPE;
            case WEST -> SOUTH_FLOOR_SHAPE;
            default -> WEST_FLOOR_SHAPE;
        };
    }


    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || state.get(WATERLOGGED)) {
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack stackInHand = player.getStackInHand(hand);
        boolean isLit = state.get(LIT);
        CandleColor currentColor = state.get(CANDLE_COLOR);
        boolean candles = state.get(CANDLES);
        Type type = state.get(TYPE);

        if (Block.getBlockFromItem(stackInHand.getItem()) instanceof CandleBlock) {
            CandleColor color = CANDLE_TO_COLOR.get(stackInHand.getItem());
            int requiredCount = type == Type.CEILING ? 4 : type == Type.FLOOR ? 3 : 1;
            if (stackInHand.getCount() >= requiredCount && state.get(CANDLE_COLOR) != color) {
                handleCandlePlacement(state, world, pos, player, hand, requiredCount, currentColor, candles);
                return ActionResult.SUCCESS;
            }
        }

        if (stackInHand.isOf(Items.FLINT_AND_STEEL) && !isLit) {
            playSound(world, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE);
            setLit(world, state, pos, true, player);
            damageItemIfNotCreative(player, hand);
            return ActionResult.success(world.isClient);
        }

        if (stackInHand.isOf(Items.FIRE_CHARGE) && !isLit) {
            playSound(world, pos, SoundEvents.ITEM_FIRECHARGE_USE);
            setLit(world, state, pos, true, player);
            decrementItemIfNotCreative(player, hand, 1);
            return ActionResult.success(world.isClient);
        }

        if (stackInHand.isEmpty() && isLit) {
            extinguish(player, state, world, pos);
            return ActionResult.success(world.isClient);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    private void handleCandlePlacement(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, int requiredCount, CandleColor currentColor, boolean candles) {
        ItemStack stackInHand = player.getStackInHand(hand);
        CandleColor newColor = CANDLE_TO_COLOR.get(stackInHand.getItem());

        if (state.get(CANDLE_COLOR) != newColor) {
            setCandleAndColor(world, state, pos, newColor, true, player);
            playSound(world, pos, SoundEvents.BLOCK_CANDLE_PLACE);
            decrementItemIfNotCreative(player, hand, requiredCount);

            if (candles) {
                CANDLE_TO_COLOR.entrySet().stream().filter(entry -> entry.getValue() == currentColor)
                        .map(Map.Entry::getKey).findFirst().ifPresent(oldCandles -> {
                            ItemStack oldCandlesStack = new ItemStack(oldCandles, requiredCount);
                            dropStack(world, pos, oldCandlesStack);
                        });
            }
        }
    }

    public void setCandleAndColor(WorldAccess world, BlockState state, BlockPos pos, CandleColor color, boolean candle, @Nullable PlayerEntity player) {
        world.setBlockState(pos, state.with(CANDLE_COLOR, color).with(CANDLES, candle), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    private void playSound(World world, BlockPos pos, SoundEvent soundEvent) {
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4F + 0.8F);
    }

    private void damageItemIfNotCreative(PlayerEntity player, Hand hand) {
        if (!player.getAbilities().creativeMode) player.getStackInHand(hand).damage(1, player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
    }

    private void decrementItemIfNotCreative(PlayerEntity player, Hand hand, int count) {
        if (!player.getAbilities().creativeMode) player.getStackInHand(hand).decrement(count);
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && this.isNotLit(state)) {
            setLit(world, state, hit.getBlockPos(), true, null);
        }
    }

    protected boolean isNotLit(BlockState state) {
        return !(Boolean)state.get(LIT);
    }

    public void extinguish(@Nullable PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos) {
        WaterloggableLitBlock.setLit(world, state, pos, false, player);
        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    public void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit, @Nullable PlayerEntity player) {
        world.setBlockState(pos, state.with(LIT, lit), Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.getOpposite() == state.get(FACING) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            world.setBlockState(pos, state.with(LIT, false), Block.NOTIFY_ALL);
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {

            world.setBlockState(pos, (state.with(WATERLOGGED, true)), Block.NOTIFY_ALL);
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
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(WATERLOGGED) || !state.get(LIT)) return;

        Type type = state.get(TYPE);
        Direction direction = state.get(FACING);
        Vec3d offset = new Vec3d(pos.getX(), pos.getY(), pos.getZ());

        switch (type) {
            case FLOOR:
                Vec3d[] floorPositions = getCandlePositionsFloor(direction);
                for (Vec3d posFloor : floorPositions) {
                    spawnCandleParticles(world, offset.add(posFloor), random);
                }
                break;
            case WALL:
                Vec3d wallPosition = getCandlePositionWall(direction);
                spawnCandleParticles(world, offset.add(wallPosition), random);
                break;
            case CEILING:
                for (Vec3d posCeiling : CANDLE_POSITIONS_CEILING) {
                    spawnCandleParticles(world, offset.add(posCeiling), random);
                }
                break;
        }
    }

    private Vec3d[] getCandlePositionsFloor(Direction direction) {
        return switch (direction) {
            case NORTH -> CANDLE_POSITIONS_NORTH_FLOOR;
            case EAST -> CANDLE_POSITIONS_EAST_FLOOR;
            case SOUTH -> CANDLE_POSITIONS_SOUTH_FLOOR;
            default -> CANDLE_POSITIONS_WEST_FLOOR;
        };
    }

    private Vec3d getCandlePositionWall(Direction direction) {
        return switch (direction) {
            case NORTH -> CANDLE_POSITION_NORTH_WALL;
            case EAST -> CANDLE_POSITION_EAST_WALL;
            case SOUTH -> CANDLE_POSITION_SOUTH_WALL;
            default -> CANDLE_POSITION_WEST_WALL;
        };
    }

    private void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3f) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17f) {
                world.playSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            }
        }
        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }
}
