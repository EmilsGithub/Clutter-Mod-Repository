package net.emilsg.clutter.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LargeChandelierBlock extends Block {
    public static final EnumProperty<LargeChandelierDirections> BLOCK_SHAPE = EnumProperty.of("block_shape", LargeChandelierDirections.class);

    protected static final VoxelShape CENTER_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 10, 6, 5, 14, 10),
            Block.createCuboidShape(6, 10, 11, 10, 14, 16),
            Block.createCuboidShape(11, 10, 6, 16, 14, 10),
            Block.createCuboidShape(6, 10, 0, 10, 14, 5),
            Block.createCuboidShape(4, 14, 4, 12, 16, 12),
            Block.createCuboidShape(4, 12, 4, 6, 14, 6),
            Block.createCuboidShape(3, 12, 3, 5, 14, 5),
            Block.createCuboidShape(2, 12, 2, 4, 14, 4),
            Block.createCuboidShape(1, 12, 1, 3, 14, 3),
            Block.createCuboidShape(0, 12, 0, 2, 14, 2),
            Block.createCuboidShape(4, 8, 4, 12, 10, 12),
            Block.createCuboidShape(5, 10, 5, 11, 14, 11),
            Block.createCuboidShape(14, 12, 0, 16, 14, 2),
            Block.createCuboidShape(13, 12, 1, 15, 14, 3),
            Block.createCuboidShape(12, 12, 2, 14, 14, 4),
            Block.createCuboidShape(11, 12, 3, 13, 14, 5),
            Block.createCuboidShape(10, 12, 10, 12, 14, 12),
            Block.createCuboidShape(11, 12, 11, 13, 14, 13),
            Block.createCuboidShape(0, 12, 14, 2, 14, 16),
            Block.createCuboidShape(1, 12, 13, 3, 14, 15),
            Block.createCuboidShape(2, 12, 12, 4, 14, 14),
            Block.createCuboidShape(3, 12, 11, 5, 14, 13),
            Block.createCuboidShape(4, 12, 10, 6, 14, 12),
            Block.createCuboidShape(12, 12, 12, 14, 14, 14),
            Block.createCuboidShape(13, 12, 13, 15, 14, 15),
            Block.createCuboidShape(14, 12, 14, 16, 14, 16),
            Block.createCuboidShape(10, 12, 4, 12, 14, 6)
    );
    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 14, 3, 13, 16, 13), Block.createCuboidShape(6, 10, 6, 10, 14, 16)
    );
    protected static final VoxelShape NORTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(8, 12, 6, 10, 14, 8),
            Block.createCuboidShape(7, 12, 7, 9, 14, 9),
            Block.createCuboidShape(6, 12, 8, 8, 14, 10),
            Block.createCuboidShape(5, 12, 9, 7, 14, 11),
            Block.createCuboidShape(4, 12, 10, 6, 14, 12),
            Block.createCuboidShape(3, 12, 11, 5, 14, 13),
            Block.createCuboidShape(2, 12, 12, 4, 14, 14),
            Block.createCuboidShape(1, 12, 13, 3, 14, 15),
            Block.createCuboidShape(0, 12, 14, 2, 14, 16),
            Block.createCuboidShape(-1, 12, 15, 1, 14, 17),
            Block.createCuboidShape(5, 14, 5, 11, 16, 11)
    );
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 14, 3, 13, 16, 13), Block.createCuboidShape(0, 10, 6, 10, 14, 10)
    );
    protected static final VoxelShape SOUTH_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(8, 12, 8, 10, 14, 10),
            Block.createCuboidShape(7, 12, 7, 9, 14, 9),
            Block.createCuboidShape(6, 12, 6, 8, 14, 8),
            Block.createCuboidShape(5, 12, 5, 7, 14, 7),
            Block.createCuboidShape(4, 12, 4, 6, 14, 6),
            Block.createCuboidShape(3, 12, 3, 5, 14, 5),
            Block.createCuboidShape(2, 12, 2, 4, 14, 4),
            Block.createCuboidShape(1, 12, 1, 3, 14, 3),
            Block.createCuboidShape(0, 12, 0, 2, 14, 2),
            Block.createCuboidShape(-1, 12, -1, 1, 14, 1),
            Block.createCuboidShape(5, 14, 5, 11, 16, 11)
    );
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 14, 3, 13, 16, 13), Block.createCuboidShape(6, 10, 0, 10, 14, 10)
    );
    protected static final VoxelShape SOUTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6, 12, 8, 8, 14, 10),
            Block.createCuboidShape(7, 12, 7, 9, 14, 9),
            Block.createCuboidShape(8, 12, 6, 10, 14, 8),
            Block.createCuboidShape(9, 12, 5, 11, 14, 7),
            Block.createCuboidShape(10, 12, 4, 12, 14, 6),
            Block.createCuboidShape(11, 12, 3, 13, 14, 5),
            Block.createCuboidShape(12, 12, 2, 14, 14, 4),
            Block.createCuboidShape(13, 12, 1, 15, 14, 3),
            Block.createCuboidShape(14, 12, 0, 16, 14, 2),
            Block.createCuboidShape(15, 12, -1, 17, 14, 1),
            Block.createCuboidShape(5, 14, 5, 11, 16, 11)
    );
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3, 14, 3, 13, 16, 13), Block.createCuboidShape(6, 10, 6, 16, 14, 10)
    );
    protected static final VoxelShape NORTH_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(6, 12, 6, 8, 14, 8),
            Block.createCuboidShape(7, 12, 7, 9, 14, 9),
            Block.createCuboidShape(8, 12, 8, 10, 14, 10),
            Block.createCuboidShape(9, 12, 9, 11, 14, 11),
            Block.createCuboidShape(10, 12, 10, 12, 14, 12),
            Block.createCuboidShape(11, 12, 11, 13, 14, 13),
            Block.createCuboidShape(12, 12, 12, 14, 14, 14),
            Block.createCuboidShape(13, 12, 13, 15, 14, 15),
            Block.createCuboidShape(14, 12, 14, 16, 14, 16),
            Block.createCuboidShape(15, 12, 15, 17, 14, 17),
            Block.createCuboidShape(5, 14, 5, 11, 16, 11)
    );

    public LargeChandelierBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(BLOCK_SHAPE, LargeChandelierDirections.CENTER)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(BLOCK_SHAPE)) {
            case NORTH_WEST -> NORTH_WEST_SHAPE;
            case WEST -> WEST_SHAPE;
            case SOUTH_WEST -> SOUTH_WEST_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case CENTER -> CENTER_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case NORTH_EAST -> NORTH_EAST_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH_EAST -> SOUTH_EAST_SHAPE;
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_SHAPE);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos[] directions = {
                pos.north(), pos.north().east(), pos.east(), pos.south().east(),
                pos.south(), pos.south().west(), pos.west(), pos.north().west()
        };

        return Arrays.stream(directions).allMatch(direction -> world.getBlockState(direction).isReplaceable());
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        placeWithDirection(world, pos);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);


        LargeChandelierDirections currentDirection = state.get(BLOCK_SHAPE);
        BlockPos centerPos = currentDirection.opposite().toBlockPos(pos);

        List<Pair<BlockPos, LargeChandelierDirections>> directions = Arrays.asList(
                new Pair<>(centerPos, LargeChandelierDirections.CENTER),
                new Pair<>(centerPos.north(), LargeChandelierDirections.NORTH),
                new Pair<>(centerPos.north().east(), LargeChandelierDirections.NORTH_EAST),
                new Pair<>(centerPos.east(), LargeChandelierDirections.EAST),
                new Pair<>(centerPos.south().east(), LargeChandelierDirections.SOUTH_EAST),
                new Pair<>(centerPos.south(), LargeChandelierDirections.SOUTH),
                new Pair<>(centerPos.south().west(), LargeChandelierDirections.SOUTH_WEST),
                new Pair<>(centerPos.west(), LargeChandelierDirections.WEST),
                new Pair<>(centerPos.north().west(), LargeChandelierDirections.NORTH_WEST)
        );

        for (Pair<BlockPos, LargeChandelierDirections> pair : directions) {
            if (world.getBlockState(pair.getLeft()).getBlock() instanceof LargeChandelierBlock && world.getBlockState(pair.getLeft()).get(BLOCK_SHAPE) == pair.getRight()) {
                world.breakBlock(pair.getLeft(), true);
            }
        }
    }

    public void placeWithDirection(World world, BlockPos pos) {
        world.setBlockState(pos.north(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.NORTH));
        world.setBlockState(pos.north().east(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.NORTH_EAST));
        world.setBlockState(pos.east(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.EAST));
        world.setBlockState(pos.south().east(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.SOUTH_EAST));
        world.setBlockState(pos.south(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.SOUTH));
        world.setBlockState(pos.south().west(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.SOUTH_WEST));
        world.setBlockState(pos.west(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.WEST));
        world.setBlockState(pos.north().west(), this.getDefaultState().with(BLOCK_SHAPE, LargeChandelierDirections.NORTH_WEST));
    }

    public enum LargeChandelierDirections implements StringIdentifiable {
        NORTH_WEST("north_west"),
        WEST("west"),
        SOUTH_WEST("south_west"),
        NORTH("north"),
        CENTER("center"),
        SOUTH("south"),
        NORTH_EAST("north_east"),
        EAST("east"),
        SOUTH_EAST("south_east");

        private final String name;

        LargeChandelierDirections(String name) {
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

        public LargeChandelierDirections opposite() {
            return switch (this) {
                case NORTH_WEST -> SOUTH_EAST;
                case WEST -> EAST;
                case SOUTH_WEST -> NORTH_EAST;
                case NORTH -> SOUTH;
                case SOUTH -> NORTH;
                case NORTH_EAST -> SOUTH_WEST;
                case EAST -> WEST;
                case SOUTH_EAST -> NORTH_WEST;
                case CENTER -> CENTER;
            };
        }

        public BlockPos toBlockPos(BlockPos pos) {
            return switch (this) {
                case NORTH_WEST -> pos.north().west();
                case WEST -> pos.west();
                case SOUTH_WEST -> pos.south().west();
                case NORTH -> pos.north();
                case SOUTH -> pos.south();
                case NORTH_EAST -> pos.north().east();
                case EAST -> pos.east();
                case SOUTH_EAST -> pos.south().east();
                case CENTER -> pos;
            };
        }

    }

    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("block.clutter.3x1x3_area_tooltip.tooltip").formatted(Formatting.BLUE));
        super.appendTooltip(stack, world, tooltip, context);
    }

}
