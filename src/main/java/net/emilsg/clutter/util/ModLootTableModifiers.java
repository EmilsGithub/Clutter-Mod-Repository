package net.emilsg.clutter.util;

import com.google.common.collect.ImmutableList;
import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.enchantment.ModEnchantments;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.GroupEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.*;

public class ModLootTableModifiers {
    private static final Identifier FERN_ID = new Identifier("minecraft", "blocks/fern");

    private static final Identifier ABANDONED_MINESHAFT_ID = new Identifier("minecraft", "chests/abandoned_mineshaft");
    private static final Identifier ANCIENT_CITY_ID = new Identifier("minecraft", "chests/ancient_city");
    private static final Identifier BASTION_TREASURE_ID = new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier BURIED_TREASURE_ID = new Identifier("minecraft", "chests/buried_treasure");
    private static final Identifier SHIPWRECK_TREASURE_ID = new Identifier("minecraft", "chests/shipwreck_treasure");
    private static final Identifier DESERT_PYRAMID_ID = new Identifier("minecraft", "chests/desert_pyramid");
    private static final Identifier END_CITY_TREASURE_ID = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier JUNGLE_TEMPLE_ID = new Identifier("minecraft", "chests/jungle_temple");
    private static final Identifier RUINED_PORTAL_ID = new Identifier("minecraft", "chests/ruined_portal");
    private static final Identifier SIMPLE_DUNGEON_ID = new Identifier("minecraft", "chests/simple_dungeon");
    private static final Identifier WOODLAND_MANSION_ID = new Identifier("minecraft", "chests/woodland_mansion");

    private static final Identifier PIGLIN_BRUTE_ID = new Identifier("minecraft", "entities/piglin_brute");
    private static final Identifier ELDER_GUARDIAN_ID = new Identifier("minecraft", "entities/elder_guardian");

    private static final Identifier WITHER_ID = new Identifier("minecraft", "entities/wither");
    private static final Identifier ENDER_DRAGON_ID = new Identifier("minecraft", "entities/ender_dragon");


    static List<Identifier> structureIds = Arrays.asList(
            ABANDONED_MINESHAFT_ID,
            ANCIENT_CITY_ID,
            BASTION_TREASURE_ID,
            BURIED_TREASURE_ID,
            DESERT_PYRAMID_ID,
            SHIPWRECK_TREASURE_ID,
            END_CITY_TREASURE_ID,
            JUNGLE_TEMPLE_ID,
            RUINED_PORTAL_ID,
            SIMPLE_DUNGEON_ID,
            WOODLAND_MANSION_ID
    );

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {

            if (id.equals(FERN_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS))).build())
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.HOPS_SEEDS).weight(1)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COTTON_SEEDS).weight(1)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            for (Identifier structureId : structureIds) {
                if (id.equals(structureId)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(2))
                            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COMMON_COIN_POUCH).conditionally(RandomChanceLootCondition.builder(0.35f)).weight(20)))
                            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.UNCOMMON_COIN_POUCH).conditionally(RandomChanceLootCondition.builder(0.35f)).weight(10)))
                            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.RARE_COIN_POUCH).conditionally(RandomChanceLootCondition.builder(0.35f)).weight(5)))
                            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.EPIC_COIN_POUCH).conditionally(RandomChanceLootCondition.builder(0.35f)).weight(1)))
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                    tableBuilder.pool(poolBuilder.build());
                }
            }

            if (id.equals(END_CITY_TREASURE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.02f))
                        .with(ItemEntry.builder(Items.BOOK)).apply(EnchantRandomlyLootFunction.create().add(ModEnchantments.GREED))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (id.equals(PIGLIN_BRUTE_ID) || id.equals(ELDER_GUARDIAN_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COMMON_COIN_POUCH).weight(500)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.UNCOMMON_COIN_POUCH).weight(375)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.RARE_COIN_POUCH).weight(125)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (id.equals(WITHER_ID) || id.equals(ENDER_DRAGON_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(6))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.RARE_COIN_POUCH).weight(2)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.EPIC_COIN_POUCH).weight(1)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}
