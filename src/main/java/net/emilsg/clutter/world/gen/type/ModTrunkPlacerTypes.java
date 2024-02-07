package net.emilsg.clutter.world.gen.type;

import net.emilsg.clutter.mixin.TrunkPlacerTypeInvoker;
import net.emilsg.clutter.world.gen.tree.DeadRedwoodTrunkPlacer;
import net.emilsg.clutter.world.gen.tree.RedwoodTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {

    public static final TrunkPlacerType<?> REDWOOD_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("redwood_trunk_placer", RedwoodTrunkPlacer.CODEC);
    public static final TrunkPlacerType<?> DEAD_REDWOOD_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("dead_redwood_trunk_placer", DeadRedwoodTrunkPlacer.CODEC);
    public static void register() {

    }

}
