package net.emilsg.clutter.world.gen.tree;

import com.mojang.serialization.Codec;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.world.gen.type.ModTreeDecoratorTypes;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class WallMushroomTreeDecorator extends TreeDecorator {
    public static final WallMushroomTreeDecorator INSTANCE = new WallMushroomTreeDecorator();
    public static final Codec<WallMushroomTreeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModTreeDecoratorTypes.TRUNK_WALL_MUSHROOM;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        Random random = generator.getRandom();
        generator.getLogPositions().forEach(logPos -> {
            placeMushroomIfPossible(logPos.west(), generator, random, Direction.WEST);
            placeMushroomIfPossible(logPos.east(), generator, random, Direction.EAST);
            placeMushroomIfPossible(logPos.north(), generator, random, Direction.NORTH);
            placeMushroomIfPossible(logPos.south(), generator, random, Direction.SOUTH);
        });
    }

    private void placeMushroomIfPossible(BlockPos mushroomPos, TreeDecorator.Generator generator, Random random, Direction direction) {
        if (random.nextFloat() < 0.02f && generator.isAir(mushroomPos)) {
            replaceWithMushrooms(mushroomPos, generator, direction);
        }
    }

    private void replaceWithMushrooms(BlockPos pos, Generator generator, Direction direction) {
        java.util.Random random = new java.util.Random();
        BlockState state = ModBlocks.YELLOW_POLYPORE.getDefaultState();
        generator.replace(pos, state.with(Properties.HORIZONTAL_FACING, direction));

    }

}
