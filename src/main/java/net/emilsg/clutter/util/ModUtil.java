package net.emilsg.clutter.util;

import jdk.jfr.Category;
import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.VillagerInteractionRegistries;
import net.fabricmc.fabric.api.registry.VillagerPlantableRegistry;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;

import java.security.Key;
import java.util.function.BiConsumer;

public class ModUtil {
    public static final Identifier CUSTOM_ELYTRA_PREDICATE = new Identifier("clutter", "custom_elytra");


    public static void registerModUtil() {
        registerFlammableBlocks();
        registerStrippableBlocks();
        registerCompostableItems();
        registerVillagerInteractions();
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(ModBlockTags.FLAMMABLE, 5, 20);
    }

    public static void registerStrippableBlocks() {

    }

    public static void registerCompostableItems() {
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(ModItems.HOPS_SEEDS, 0.3f);
        registry.add(ModItems.COTTON_SEEDS, 0.3f);
        registry.add(ModItems.HOPS, 0.3f);
        registry.add(ModItems.COTTON, 0.3f);
    }

    public static void registerVillagerInteractions() {
        VillagerInteractionRegistries.registerCompostable(ModItems.HOPS_SEEDS);
        VillagerInteractionRegistries.registerCompostable(ModItems.COTTON_SEEDS);
        VillagerInteractionRegistries.registerCompostable(ModItems.HOPS);
        VillagerInteractionRegistries.registerCompostable(ModItems.COTTON);

        VillagerPlantableRegistry.register(ModItems.HOPS_SEEDS);
        VillagerPlantableRegistry.register(ModItems.COTTON_SEEDS);
    }
}
