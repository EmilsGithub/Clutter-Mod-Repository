package net.emilsg.clutter.util;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.config.Configs;
import net.emilsg.clutter.config.ModConfigManager;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetComponentsLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.EnchantmentsPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.Arrays;
import java.util.List;

public class ModLootTableModifiers {
    private static final RegistryKey<Registry<LootTable>> LOOT = RegistryKeys.LOOT_TABLE;


    private static final RegistryKey<LootTable> SNIFFER_DIGGING_ID = makeMCRegistryKey("gameplay/sniffer_digging");

    private static final RegistryKey<LootTable> FERN_ID = makeMCRegistryKey("blocks/fern");
    private static final RegistryKey<LootTable> CHERRY_LEAVES_ID = makeMCRegistryKey("blocks/cherry_leaves");

    private static final RegistryKey<LootTable> FISHING_JUNK_ID = makeMCRegistryKey("gameplay/fishing/junk");
    private static final RegistryKey<LootTable> VILLAGE_FLETCHER_ID = makeMCRegistryKey( "chests/village/village_fletcher");
    private static final RegistryKey<LootTable> VILLAGE_BUTCHER_ID = makeMCRegistryKey("chests/village/village_butcher");
    private static final RegistryKey<LootTable> VILLAGE_TANNERY_ID = makeMCRegistryKey("chests/village/village_tannery");
    private static final RegistryKey<LootTable> VILLAGE_SHEPHERD_ID = makeMCRegistryKey("chests/village/village_shepherd");
    private static final RegistryKey<LootTable> IGLOO_CHEST_ID = makeMCRegistryKey("chests/igloo_chest");
    private static final RegistryKey<LootTable> ABANDONED_MINESHAFT_ID = makeMCRegistryKey("chests/abandoned_mineshaft");
    private static final RegistryKey<LootTable> ANCIENT_CITY_ID = makeMCRegistryKey("chests/ancient_city");
    private static final RegistryKey<LootTable> BASTION_TREASURE_ID = makeMCRegistryKey("chests/bastion_treasure");
    private static final RegistryKey<LootTable> BURIED_TREASURE_ID = makeMCRegistryKey("chests/buried_treasure");
    private static final RegistryKey<LootTable> SHIPWRECK_TREASURE_ID = makeMCRegistryKey("chests/shipwreck_treasure");
    private static final RegistryKey<LootTable> DESERT_PYRAMID_ID = makeMCRegistryKey("chests/desert_pyramid");
    private static final RegistryKey<LootTable> END_CITY_TREASURE_ID = makeMCRegistryKey("chests/end_city_treasure");
    private static final RegistryKey<LootTable> JUNGLE_TEMPLE_ID = makeMCRegistryKey("chests/jungle_temple");
    private static final RegistryKey<LootTable> RUINED_PORTAL_ID = makeMCRegistryKey("chests/ruined_portal");
    private static final RegistryKey<LootTable> SIMPLE_DUNGEON_ID = makeMCRegistryKey("chests/simple_dungeon");
    private static final RegistryKey<LootTable> WOODLAND_MANSION_ID = makeMCRegistryKey("chests/woodland_mansion");

    private static final RegistryKey<LootTable> BLAZE_ID = makeMCRegistryKey("entities/blaze");
    private static final RegistryKey<LootTable> PIGLIN_BRUTE_ID = makeMCRegistryKey("entities/piglin_brute");
    private static final RegistryKey<LootTable> ELDER_GUARDIAN_ID = makeMCRegistryKey("entities/elder_guardian");
    private static final RegistryKey<LootTable> WITHER_ID = makeMCRegistryKey("entities/wither");
    private static final RegistryKey<LootTable> ENDER_DRAGON_ID = makeMCRegistryKey("entities/ender_dragon");

    static final List<RegistryKey<LootTable>> structureIds = Arrays.asList(
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

    private static RegistryKey<LootTable> makeMCRegistryKey(String path) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("minecraft", path));
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((key, tableBuilder, source, registries) -> {

            if (key.equals(FERN_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f))
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS))).build())
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.HOPS_SEEDS).weight(1)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COTTON_SEEDS).weight(1)))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(CHERRY_LEAVES_ID)) {
                RegistryWrapper.Impl<Enchantment> impl = registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);

                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS))).build())
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().subPredicate(
                                ItemSubPredicateTypes.ENCHANTMENTS,
                                EnchantmentsPredicate.enchantments(List.of(new EnchantmentPredicate(impl.getOrThrow(Enchantments.SILK_TOUCH), NumberRange.IntRange.atLeast(1))))
                        ))).build())
                        .with(ItemEntry.builder(ModItems.CHERRIES).conditionally(RandomChanceLootCondition.builder(ModConfigManager.get(Configs.cherryDropRate, 0.075f))))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(SNIFFER_DIGGING_ID)) {
                tableBuilder.modifyPools(builder -> {
                    builder
                            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.THORNBLOOM_SEEDS)))
                            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.KIWI_SEEDS)))
                            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.GLOWLILY_SEEDLING)));
                });
            }

            if (key.equals(END_CITY_TREASURE_ID)) {
                tableBuilder.modifyPools(builder -> {
                    builder.with(AlternativeEntry.builder(ItemEntry.builder(ModItems.DECORATED_ELYTRA_SMITHING_TEMPLATE).weight(2)));
                });
            }

            //Coins
            if (ModConfigManager.get(Configs.doCoinDropsAndLootGeneration, true)) {
                for (RegistryKey<LootTable> structureId : structureIds) {
                    if (key.equals(structureId) && ModConfigManager.get(Configs.chestLootCoinPouches, true)) {
                        LootPool.Builder poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(2))
                                .conditionally(RandomChanceLootCondition.builder(0.5f))
                                .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COIN_POUCH).weight(12).apply(SetComponentsLootFunction.builder(DataComponentTypes.RARITY, Rarity.COMMON))))
                                .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COIN_POUCH).weight(4).apply(SetComponentsLootFunction.builder(DataComponentTypes.RARITY, Rarity.UNCOMMON))))
                                .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COIN_POUCH).weight(1).apply(SetComponentsLootFunction.builder(DataComponentTypes.RARITY, Rarity.RARE))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                        tableBuilder.pool(poolBuilder.build());
                    }
                }

                if (ModConfigManager.get(Configs.mobsDropCoinPouches, true)) {
                    if (key.equals(PIGLIN_BRUTE_ID) || key.equals(ELDER_GUARDIAN_ID)) {
                        LootPool.Builder poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COIN_POUCH).weight(10).apply(SetComponentsLootFunction.builder(DataComponentTypes.RARITY, Rarity.COMMON))))
                                .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COIN_POUCH).weight(2).apply(SetComponentsLootFunction.builder(DataComponentTypes.RARITY, Rarity.UNCOMMON))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                        tableBuilder.pool(poolBuilder.build());
                    }

                    if (key.equals(WITHER_ID) || key.equals(ENDER_DRAGON_ID)) {
                        LootPool.Builder poolBuilder = LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(6))
                                .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COIN_POUCH).weight(2).apply(SetComponentsLootFunction.builder(DataComponentTypes.RARITY, Rarity.RARE))))
                                .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.COIN_POUCH).weight(1).apply(SetComponentsLootFunction.builder(DataComponentTypes.RARITY, Rarity.EPIC))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                        tableBuilder.pool(poolBuilder.build());
                    }
                }
            }

            if (key.equals(BLAZE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.SULPHUR))
                        .conditionally(DamageSourcePropertiesLootCondition.builder(new DamageSourcePredicate.Builder().tag(TagPredicate.expected(DamageTypeTags.IS_DROWNING))).build())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(ENDER_DRAGON_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.ENDER_DRAGON_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(1.0f))))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(JUNGLE_TEMPLE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.PANDA_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.15f))))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.OCELOT_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.15f))))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(BURIED_TREASURE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.SQUID_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.3f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(IGLOO_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.FOX_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.2f))))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.SNOW_FOX_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.2f))))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).conditionally(RandomChanceLootCondition.builder(0.1f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(VILLAGE_SHEPHERD_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.SHEEP_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(VILLAGE_TANNERY_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.COW_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(VILLAGE_BUTCHER_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.PIG_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(VILLAGE_FLETCHER_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.CHICKEN_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (key.equals(FISHING_JUNK_ID)) {
                tableBuilder.modifyPools(builder -> {
                    builder.with(ItemEntry.builder(ModItems.SMALL_LILY_PADS).weight(10));
                    builder.with(ItemEntry.builder(ModItems.GIANT_LILY_PAD).weight(5));
                    builder.with(ItemEntry.builder(ModItems.GIANT_LILY_PAD_SEEDLING).weight(5));
                });
            }
        }));
    }
}
