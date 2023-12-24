package net.emilsg.clutter.entity;

import net.emilsg.clutter.entity.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ClutterAttributes {
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.BUTTERFLY, ButterflyEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CHAMELEON, ChameleonEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ECHOFIN, EchofinEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MOSSBLOOM, MossbloomEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.KIWI_BIRD, KiwiBirdEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.EMPEROR_PENGUIN, EmperorPenguinEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BEAVER, BeaverEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CAPYBARA, CapybaraEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CRIMSON_NEWT, CrimsonNewtEntity.setAttributes());
    }
}
