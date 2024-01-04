package net.emilsg.clutter.world.gen.type;

import com.mojang.serialization.Codec;
import net.emilsg.clutter.Clutter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {


    private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String id, Codec<P> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, new Identifier(Clutter.MOD_ID, id), new TrunkPlacerType<P>(codec));
    }
}
