package net.emilsg.clutter.world.gen.features;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.PearlClamBlock;
import net.emilsg.clutter.block.custom.SmallSpongeBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class ModFeatures {


    public static final Feature<CountConfig> SMALL_SPONGE = register("small_sponge",
            new UnderwaterPatchFeature(CountConfig.CODEC, ModBlocks.SMALL_SPONGE.getDefaultState(),
                    (state, random) -> state.with(SmallSpongeBlock.AGE, random.nextInt(2) + 1).with(Properties.WATERLOGGED, true)));

    public static final Feature<CountConfig> CLAM = register("clam",
            new UnderwaterPatchFeature(CountConfig.CODEC, ModBlocks.PEARL_CLAM_BLOCK.getDefaultState(),
                    (state, random) -> state.with(PearlClamBlock.HAS_PEARL, random.nextInt(4) == 0).with(Properties.WATERLOGGED, true)
                            .with(Properties.HORIZONTAL_FACING, Direction.Type.HORIZONTAL.random(random))));

    public static final Feature<CountConfig> BEACH_PATCH = register("beach_patch", new BeachPatchFeature(CountConfig.CODEC));

    public static final Feature<CountConfig> CATTAILS = register("cattails", new CattailsFeature(CountConfig.CODEC, 8));
    public static final Feature<CountConfig> CATTAILS_RIVER = register("cattails_river", new CattailsFeature(CountConfig.CODEC, 12));

    public static final Feature<WeightedBlockFeatureConfig> STATED_ROCK = register("stated_rock", new StatedRockFeature(WeightedBlockFeatureConfig.CODEC));
    public static final Feature<CountConfig> FALLEN_REDWOOD_TREE = register("fallen_redwood_tree", new FallenTreeFeature(CountConfig.CODEC));


    public static void registerModFeatures() {

    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registries.FEATURE, new Identifier(Clutter.MOD_ID, (name)), feature);
    }
}
