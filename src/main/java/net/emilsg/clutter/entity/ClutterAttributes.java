package net.emilsg.clutter.entity;

import net.emilsg.clutter.entity.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ClutterAttributes {
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntityTypes.BUTTERFLY, ButterflyEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.CHAMELEON, ChameleonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.ECHOFIN, EchofinEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.MOSSBLOOM, MossbloomEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.KIWI_BIRD, KiwiBirdEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.EMPEROR_PENGUIN, EmperorPenguinEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.BEAVER, BeaverEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.CAPYBARA, CapybaraEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.CRIMSON_NEWT, CrimsonNewtEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.WARPED_NEWT, WarpedNewtEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.EMBER_TORTOISE, EmberTortoiseEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.JELLYFISH, JellyfishEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.MANTA_RAY, MantaRayEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntityTypes.SEAHORSE, SeahorseEntity.setAttributes());

    }
}
