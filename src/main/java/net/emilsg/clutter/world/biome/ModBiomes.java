package net.emilsg.clutter.world.biome;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.entity.ModEntities;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class ModBiomes {
    public static final RegistryKey<Biome> GIANT_REDWOOD_FOREST = register("giant_redwood_forest");
    public static final RegistryKey<Biome> LUPINE_FIELDS = register("lupine_fields");

    public static RegistryKey<Biome> register(String id) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(Clutter.MOD_ID, id));
    }

    public static void bootstrap(Registerable<Biome> context) {
        context.register(GIANT_REDWOOD_FOREST, giantRedwoodForest(context));
        context.register(LUPINE_FIELDS, lupineFields(context));
    }

    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static Biome giantRedwoodForest(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 8, 4, 4));
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FOX, 8, 2, 4));

        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder);

        GenerationSettings.LookupBackedBuilder builder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(builder);

        ClutterDefaultBiomeFeatures.addRedwoodTreeFeatures(builder);
        ClutterDefaultBiomeFeatures.addRedwoodFoliageFeatures(builder);

        DefaultBiomeFeatures.addLargeFerns(builder);
        DefaultBiomeFeatures.addDefaultOres(builder);
        DefaultBiomeFeatures.addDefaultDisks(builder);

        DefaultBiomeFeatures.addDefaultFlowers(builder);
        DefaultBiomeFeatures.addGiantTaigaGrass(builder);
        DefaultBiomeFeatures.addDefaultMushrooms(builder);
        DefaultBiomeFeatures.addDefaultVegetation(builder);
        DefaultBiomeFeatures.addSweetBerryBushes(builder);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.8f)
                .temperature(0.3f)
                .generationSettings(builder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(2068169)
                        .waterFogColor(271411)
                        .skyColor(OverworldBiomeCreator.getSkyColor(0.3f))
                        .grassColor(7049587)
                        .foliageColor(5019969)
                        .fogColor(12638463)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_OLD_GROWTH_TAIGA))
                        .build())
                .build();
    }

    private static Biome lupineFields(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.BUTTERFLY, 4, 3, 5));

        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder);

        GenerationSettings.LookupBackedBuilder builder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(builder);

        ClutterDefaultBiomeFeatures.addLupineFieldsFoliageFeatures(builder);
        DefaultBiomeFeatures.addDefaultGrass(builder);
        DefaultBiomeFeatures.addDefaultOres(builder);
        DefaultBiomeFeatures.addDefaultDisks(builder);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.4f)
                .temperature(0.8f)
                .generationSettings(builder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .skyColor(OverworldBiomeCreator.getSkyColor(0.8f))
                        .grassColor(9551193)
                        .foliageColor(7842607)
                        .fogColor(12638463)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_FLOWER_FOREST))
                        .build())
                .build();
    }
}
