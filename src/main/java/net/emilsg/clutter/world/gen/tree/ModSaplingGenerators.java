package net.emilsg.clutter.world.gen.tree;

import net.emilsg.clutter.world.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerators {

    public static final SaplingGenerator KIWI_TREE = new SaplingGenerator(
            "spruce",
            0.5F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.KIWI_TREE_KEY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );

    public static final SaplingGenerator REDWOOD_TREE = new SaplingGenerator(
            "spruce",
            0.5F,
            Optional.of(ModConfiguredFeatures.REDWOOD_KEY),
            Optional.of(ModConfiguredFeatures.REDWOOD_KEY_2),
            Optional.of(ModConfiguredFeatures.SMALL_REDWOOD_KEY),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );
}