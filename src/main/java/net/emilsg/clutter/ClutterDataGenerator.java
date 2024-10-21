package net.emilsg.clutter;

import net.emilsg.clutter.data.*;
import net.emilsg.clutter.enchantment.ModEnchantments;
import net.emilsg.clutter.world.ModConfiguredFeatures;
import net.emilsg.clutter.world.ModPlacedFeatures;
import net.emilsg.clutter.world.biome.ModBiomes;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class ClutterDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ItemTagDataGen::new);
        pack.addProvider(EntityTagDataGen::new);
        pack.addProvider(ModelDataGen::new);
        pack.addProvider(BlockTagDataGen::new);
        pack.addProvider(LootTableDataGen::new);
        pack.addProvider(RecipeDataGen::new);
        pack.addProvider(BiomeTagDataGen::new);
        pack.addProvider(WorldDataGen::new);
        pack.addProvider(ModRegistryProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ModEnchantments::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
    }
}
