package net.emilsg.clutter.entity;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.emilsg.clutter.entity.custom.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<SeatEntity> SEAT = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "seat"),
            FabricEntityTypeBuilder.<SeatEntity>create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.001F, 0.001F))
                    .build());

    public static final EntityType<ButterflyEntity> BUTTERFLY = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "butterfly"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ButterflyEntity::new).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build());

    public static final EntityType<ChameleonEntity> CHAMELEON = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "chameleon"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChameleonEntity::new).dimensions(EntityDimensions.changing(0.75f, 0.5f)).build());

    public static final EntityType<EchofinEntity> ECHOFIN = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "echofin"),
            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, EchofinEntity::new).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build());

    public static final EntityType<MossbloomEntity> MOSSBLOOM = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "mossbloom"),
            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, MossbloomEntity::new).dimensions(EntityDimensions.changing(0.9f, 1.15f)).build());

    public static final EntityType<KiwiBirdEntity> KIWI_BIRD = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "kiwi_bird"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, KiwiBirdEntity::new).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build());

    public static final EntityType<EmperorPenguinEntity> EMPEROR_PENGUIN = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "emperor_penguin"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EmperorPenguinEntity::new).dimensions(EntityDimensions.changing(0.75f, 1.35f)).build());

    public static final EntityType<BeaverEntity> BEAVER = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "beaver"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BeaverEntity::new).dimensions(EntityDimensions.changing(0.9f, 0.65f)).build());

    public static final EntityType<CapybaraEntity> CAPYBARA = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "capybara"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CapybaraEntity::new).dimensions(EntityDimensions.changing(0.7f, 0.8f)).build());

    public static final EntityType<CrimsonNewtEntity> CRIMSON_NEWT = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "crimson_newt"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrimsonNewtEntity::new).dimensions(EntityDimensions.changing(0.75f, 0.75f)).fireImmune().build());

    public static final EntityType<EmberTortoiseEntity> EMBER_TORTOISE = Registry.register(Registries.ENTITY_TYPE, new Identifier(Clutter.MOD_ID, "ember_tortoise"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, EmberTortoiseEntity::new).dimensions(EntityDimensions.changing(1.5f, 1.45f)).fireImmune().build());
}
