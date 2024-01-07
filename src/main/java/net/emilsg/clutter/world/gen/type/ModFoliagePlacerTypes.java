package net.emilsg.clutter.world.gen.type;

import net.emilsg.clutter.mixin.FoliagePlacerTypeInvoker;
import net.emilsg.clutter.world.gen.tree.RedwoodFoliagePlacer;
import net.emilsg.clutter.world.gen.tree.SmallRedwoodFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ModFoliagePlacerTypes {

    public static final FoliagePlacerType<?> REDWOOD_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("redwood_foliage_placer", RedwoodFoliagePlacer.CODEC);
    public static final FoliagePlacerType<?> SMALL_REDWOOD_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("small_redwood_foliage_placer", SmallRedwoodFoliagePlacer.CODEC);

    public static void register() {

    }

}
