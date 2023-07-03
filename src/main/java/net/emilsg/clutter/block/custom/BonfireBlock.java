package net.emilsg.clutter.block.custom;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.entity.BonfireBlockEntity;
import net.emilsg.clutter.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.ToIntFunction;

public class BonfireBlock extends BlockWithEntity {
    public static final EnumProperty<BONFIRE_BLOCK_MODEL> BLOCK_SHAPE = EnumProperty.of("block_shape", BONFIRE_BLOCK_MODEL.class);
    private static final BooleanProperty LIT = Properties.LIT;
    private static final VoxelShape UP_NORTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5,0,8,16,8,16)
    );
    private static final VoxelShape UP_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(8,0,0,16,8,16)
    );
    private static final VoxelShape UP_SOUTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5,0,0,16,8,8)
    );
    private static final VoxelShape UP_NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0,0,8,16,8,16)
    );
    private static final VoxelShape UP_CENTER_SHAPE  = VoxelShapes.union(
            Block.createCuboidShape(0,0,0,16,16,16)
    );
    private static final VoxelShape UP_SOUTH_SHAPE  = VoxelShapes.union(
            Block.createCuboidShape(0,0,0,16,8,8)
    );
    private static final VoxelShape UP_NORTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0,0,8,11,8,16)
    );
    private static final VoxelShape UP_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0,0,0,8,8,16)
    );
    private static final VoxelShape UP_SOUTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0,0,0,11,8,8)
    );
    private static final VoxelShape FULL_SHAPE = Block.createCuboidShape(0,0,0,16,16,16);

    public BonfireBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LIT, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_SHAPE, LIT);
    }

    public enum BONFIRE_BLOCK_MODEL implements StringIdentifiable {
        NORTH_WEST("north_west"),
        WEST("west"),
        SOUTH_WEST("south_west"),
        NORTH("north"),
        CENTER("center"),
        SOUTH("south"),
        NORTH_EAST("north_east"),
        EAST("east"),
        SOUTH_EAST("south_east"),
        UP_NORTH_WEST("up_north_west"),
        UP_WEST("up_west"),
        UP_SOUTH_WEST("up_south_west"),
        UP_NORTH("up_north"),
        UP_CENTER("up_center"),
        UP_SOUTH("up_south"),
        UP_NORTH_EAST("up_north_east"),
        UP_EAST("up_east"),
        UP_SOUTH_EAST("up_south_east");

        private final String name;

        BONFIRE_BLOCK_MODEL(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static final Direction[] cardinalDirections = {
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST
    };

    public static boolean checkAirAround(BlockPos pos, World world, boolean checkBonfireBlocks) {
        BlockPos[] positionsToCheck = {pos, pos.up()};

        for (BlockPos basePos : positionsToCheck) {
            for (Direction direction1 : cardinalDirections) {
                BlockPos neighborPos1 = basePos.offset(direction1);
                BlockState neighborState1 = world.getBlockState(neighborPos1);
                if (neighborState1.getBlock() != Blocks.AIR && !neighborState1.isReplaceable()) {
                    return false;
                }
                for (Direction direction2 : cardinalDirections) {
                    if (direction2.getAxis() != direction1.getAxis()) {
                        BlockPos diagonalPos = neighborPos1.offset(direction2);
                        BlockState diagonalState = world.getBlockState(diagonalPos);
                        if (diagonalState.getBlock() != Blocks.AIR && !diagonalState.isReplaceable()) {
                            return false;
                        }
                    }
                }
            }
        }
        if (checkBonfireBlocks) {
            int[] xOffsetArray = {-2, -1, 0, 1, 2};
            int[] yOffsetArray = {-1, 0, 1, 2};
            int[] zOffsetArray = {-2, -1, 0, 1, 2};

            for (int yOffset : yOffsetArray) {
                BlockPos yOffsetPos = pos.add(0, yOffset, 0);
                for (int xOffset : xOffsetArray) {
                    for (int zOffset : zOffsetArray) {
                        BlockPos targetPos = yOffsetPos.add(xOffset, 0, zOffset);
                        BlockState targetState = world.getBlockState(targetPos);
                        if (targetState.getBlock() instanceof BonfireBlock) {
                            return false;
                        }
                    }
                }
            }
        }

        return world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
    }


    public void placeBonfireBlocks(BlockPos pos, World world) {
        int index = 0;
        int[] offsets = {-1, 0, 1};

        for (int yOffset = 0; yOffset < 2; yOffset++) {
            BlockPos yOffsetPos = pos.up(yOffset);
            for (int xOffset : offsets) {
                for (int zOffset : offsets) {
                    BlockPos targetPos = yOffsetPos.add(xOffset, 0, zOffset);
                    world.setBlockState(targetPos, this.getDefaultState().with(BonfireBlock.BLOCK_SHAPE, BonfireBlock.BONFIRE_BLOCK_MODEL.values()[index]));
                    index++;
                }
            }
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.getAbilities().creativeMode) {
            super.onBreak(world, pos, state, player);

            Queue<BlockPos> queue = new LinkedList<>();
            queue.add(pos);
            Set<BlockPos> visited = new HashSet<>();

            while (!queue.isEmpty()) {
                BlockPos currentPos = queue.poll();
                visited.add(currentPos);

                for (Direction direction : Direction.values()) {
                    BlockPos neighborPos = currentPos.offset(direction);
                    BlockState neighborState = world.getBlockState(neighborPos);

                    if (neighborState.getBlock() instanceof BonfireBlock && !visited.contains(neighborPos)) {
                        world.breakBlock(neighborPos, true, player);
                        queue.add(neighborPos);
                    }
                }
            }
        } else if (!world.isClient() && player.getAbilities().creativeMode) {
            super.onBreak(world, pos, state, player);

            Queue<BlockPos> queue = new LinkedList<>();
            queue.add(pos);
            Set<BlockPos> visited = new HashSet<>();

            while (!queue.isEmpty()) {
                BlockPos currentPos = queue.poll();
                visited.add(currentPos);

                for (Direction direction : Direction.values()) {
                    BlockPos neighborPos = currentPos.offset(direction);
                    BlockState neighborState = world.getBlockState(neighborPos);

                    if (neighborState.getBlock() instanceof BonfireBlock && !visited.contains(neighborPos)) {
                        world.breakBlock(neighborPos, false, player);
                        queue.add(neighborPos);
                    }
                }
            }
        }


        else {
            super.onBreak(world, pos, state, player);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemInHand = player.getStackInHand(hand);
        if (player.getAbilities().allowModifyWorld && itemInHand.getItem() == Items.FLINT_AND_STEEL && !state.get(LIT)) {
            setLitStateForConnectedBonfires(world, pos, true, player);
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                itemInHand.damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
            }
            return ActionResult.SUCCESS;
        } else if (player.getAbilities().allowModifyWorld && itemInHand.getItem() == Items.FIRE_CHARGE && !state.get(LIT)) {
            setLitStateForConnectedBonfires(world, pos, true, player);
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                itemInHand.decrement(1);
            }
            return ActionResult.SUCCESS;
        } else if (player.getAbilities().allowModifyWorld && itemInHand.getItem() instanceof ShovelItem && state.get(LIT)) {
            setLitStateForConnectedBonfires(world, pos, false, player);
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 0.8f);
            if (!player.getAbilities().creativeMode) {
                itemInHand.damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    private void setLitStateForConnectedBonfires(World world, BlockPos pos, boolean lit, PlayerEntity player) {
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(pos);
        Set<BlockPos> visited = new HashSet<>();
        world.setBlockState(pos, world.getBlockState(pos).with(LIT, lit), Block.NOTIFY_ALL);

        while (!queue.isEmpty()) {
            BlockPos currentPos = queue.poll();
            visited.add(currentPos);

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.offset(direction);
                BlockState neighborState = world.getBlockState(neighborPos);

                if (neighborState.getBlock() instanceof BonfireBlock && !visited.contains(neighborPos)) {
                    world.setBlockState(neighborPos, neighborState.with(LIT, lit), 3);
                    queue.add(neighborPos);
                }
            }
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return checkAirAround(pos, (World) world, true);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        placeBonfireBlocks(pos, world);
    }

    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("block.clutter.3x2x3_area_tooltip.tooltip").formatted(Formatting.BLUE));
        tooltip.add(Text.translatable("block.clutter.not_next_to_tooltip.tooltip").formatted(Formatting.BLUE));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(LIT) ? litLevel : 0;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            if (state.get(BLOCK_SHAPE).asString().startsWith("up_") && !state.get(BLOCK_SHAPE).asString().contains("center_")) {
                spawnThickSmokeParticles(world, pos, state, 0.3);
                spawnSmokeParticles(world, pos, state, 0.0);
            } else if (state.get(BLOCK_SHAPE).asString().contains("up_center")) {
                spawnThickSmokeParticles(world, pos, state, 1);
                spawnSmokeParticles(world, pos, state, 1);
            } else {
                spawnSmokeParticles(world, pos, state, 0.3);
            }
            if (random.nextInt(8) == 0 && state.get(BLOCK_SHAPE) == BONFIRE_BLOCK_MODEL.CENTER && state.get(LIT)) {
                world.playSound((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5f + random.nextFloat(), random.nextFloat() * 0.7f + 0.2f, false);
            }
            if (random.nextInt(5) == 0 && state.get(BLOCK_SHAPE).asString().startsWith("up_") && this == ModBlocks.BONFIRE) {
                for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                    world.addParticle(ParticleTypes.LAVA, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, random.nextFloat() / 2.0f, 5.0E-5, random.nextFloat() / 2.0f);
                }
            }
        }
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if ((Boolean)state.get(LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.damage(world.getDamageSources().inFire(), 2);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    public static void spawnThickSmokeParticles(World world, BlockPos pos, BlockState state, double yOffset) {
        Random random = world.getRandom();
        world.addImportantParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, (double) pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble() + yOffset, (double) pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), random.nextDouble() * 0.04 - 0.02, 0.07, random.nextDouble() * 0.04 - 0.02);
    }

    public static void spawnSmokeParticles(World world, BlockPos pos, BlockState state, double yOffset) {
        Random random = world.getRandom();
        world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5 + random.nextDouble() / 2.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + yOffset -0.1, (double) pos.getZ() + 0.5 + random.nextDouble() / 2.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 0.005, 0.0);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BonfireBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() && type == ModBlockEntities.BONFIRE ? BonfireBlockEntity::clientTick : null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        BONFIRE_BLOCK_MODEL model = state.get(BonfireBlock.BLOCK_SHAPE);
        return switch (model) {
            case UP_NORTH_WEST -> UP_NORTH_WEST_SHAPE;
            case UP_WEST -> UP_WEST_SHAPE;
            case UP_SOUTH_WEST -> UP_SOUTH_WEST_SHAPE;
            case UP_NORTH -> UP_NORTH_SHAPE;
            case UP_CENTER -> UP_CENTER_SHAPE;
            case UP_SOUTH -> UP_SOUTH_SHAPE;
            case UP_NORTH_EAST -> UP_NORTH_EAST_SHAPE;
            case UP_EAST -> UP_EAST_SHAPE;
            case UP_SOUTH_EAST -> UP_SOUTH_EAST_SHAPE;
            default -> FULL_SHAPE;
        };
    }
}