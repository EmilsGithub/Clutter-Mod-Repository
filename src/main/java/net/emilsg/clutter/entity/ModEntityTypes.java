package net.emilsg.clutter.entity;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.entity.SeatEntity;
import net.emilsg.clutter.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityTypes {

    public static final EntityType<ButterflyEntity> BUTTERFLY = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "butterfly"),
            EntityType.Builder.create(ButterflyEntity::new, SpawnGroup.CREATURE).dimensions(0.5f, 0.5f).build());

    public static final EntityType<SeatEntity> SEAT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "seat"),
            EntityType.Builder.<SeatEntity>create(SeatEntity::new, SpawnGroup.MISC).dimensions(0.001F, 0.001F).build());

    public static final EntityType<ChameleonEntity> CHAMELEON = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "chameleon"),
            EntityType.Builder.create(ChameleonEntity::new, SpawnGroup.CREATURE).dimensions(0.75f, 0.5f).build());

    public static final EntityType<EchofinEntity> ECHOFIN = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "echofin"),
            EntityType.Builder.create(EchofinEntity::new, SpawnGroup.AMBIENT).dimensions(0.5f, 0.5f).build());

    public static final EntityType<MossbloomEntity> MOSSBLOOM = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "mossbloom"),
            EntityType.Builder.create(MossbloomEntity::new, SpawnGroup.AMBIENT).dimensions(0.9f, 1.15f).build());

    public static final EntityType<KiwiBirdEntity> KIWI_BIRD = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "kiwi_bird"),
            EntityType.Builder.create(KiwiBirdEntity::new, SpawnGroup.CREATURE).dimensions(0.5f, 0.5f).build());

    public static final EntityType<EmperorPenguinEntity> EMPEROR_PENGUIN = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "emperor_penguin"),
            EntityType.Builder.create(EmperorPenguinEntity::new, SpawnGroup.CREATURE).dimensions(0.75f, 1.35f).build());

    public static final EntityType<BeaverEntity> BEAVER = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "beaver"),
            EntityType.Builder.create(BeaverEntity::new, SpawnGroup.CREATURE).dimensions(0.9f, 0.65f).build());

    public static final EntityType<CapybaraEntity> CAPYBARA = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "capybara"),
            EntityType.Builder.create(CapybaraEntity::new, SpawnGroup.CREATURE).dimensions(0.7f, 0.8f).build());

    public static final EntityType<CrimsonNewtEntity> CRIMSON_NEWT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "crimson_newt"),
            EntityType.Builder.create(CrimsonNewtEntity::new, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).makeFireImmune().build());

    public static final EntityType<WarpedNewtEntity> WARPED_NEWT = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "warped_newt"),
            EntityType.Builder.create(WarpedNewtEntity::new, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).makeFireImmune().build());

    public static final EntityType<EmberTortoiseEntity> EMBER_TORTOISE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "ember_tortoise"),
            EntityType.Builder.create(EmberTortoiseEntity::new, SpawnGroup.CREATURE).dimensions(1.5f, 1.45f).makeFireImmune().build());

    public static final EntityType<JellyfishEntity> JELLYFISH = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "jellyfish"),
            EntityType.Builder.create(JellyfishEntity::new, SpawnGroup.WATER_AMBIENT).dimensions(0.5f, 0.5f).build());

    public static final EntityType<MantaRayEntity> MANTA_RAY = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "manta_ray"),
            EntityType.Builder.create(MantaRayEntity::new, SpawnGroup.WATER_CREATURE).dimensions(1f, 0.5f).build());

    public static final EntityType<SeahorseEntity> SEAHORSE = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Clutter.MOD_ID, "seahorse"),
            EntityType.Builder.create(SeahorseEntity::new, SpawnGroup.WATER_AMBIENT).dimensions(0.4f, 0.5f).build());


}
