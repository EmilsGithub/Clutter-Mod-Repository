package net.emilsg.clutter.world.biome.surface;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.world.biome.ModBiomes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.noise.NoiseParametersKeys;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule OVERGROWN_PACKED_MUD = makeStateRule(ModBlocks.OVERGROWN_PACKED_MUD);
    private static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);
    private static final MaterialRules.MaterialRule PACKED_MUD = makeStateRule(Blocks.PACKED_MUD);
    private static final MaterialRules.MaterialRule COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final MaterialRules.MaterialRule PODZOL = makeStateRule(Blocks.PODZOL);
    private static final MaterialRules.MaterialRule BEDROCK = makeStateRule(Blocks.BEDROCK);

    public static final MaterialRules.MaterialCondition WATER_CHECK = MaterialRules.water(-1, 0);

    public static MaterialRules.MaterialRule makeRules() {

        MaterialRules.MaterialRule overGrownStoneRule = MaterialRules.sequence(
                MaterialRules.condition(
                        MaterialRules.aboveY(YOffset.fixed(62), 0), MaterialRules.condition(
                                WATER_CHECK, MaterialRules.condition(
                                        MaterialRules.STONE_DEPTH_FLOOR, OVERGROWN_PACKED_MUD))),
                MaterialRules.condition(
                        MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, PACKED_MUD));

        MaterialRules.MaterialRule redwoodRule =
                MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.biome(ModBiomes.GIANT_REDWOOD_FOREST),
                                MaterialRules.sequence(
                                        overGrownStoneRule
                                )));



        return MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.biome(ModBiomes.GIANT_REDWOOD_FOREST), redwoodRule)



        );
    }

    private static MaterialRules.MaterialCondition surfaceNoiseAbove(double noise) {
        return MaterialRules.noiseThreshold(NoiseParametersKeys.SURFACE, noise / 8.25D, Double.MAX_VALUE);
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
