package net.emilsg.clutter.util;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.enchantment.ModEnchantments;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.DamageSourcePropertiesLootCondition;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.TagPredicate;
import net.minecraft.predicate.entity.DamageSourcePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModLootTableModifiers {

    private static final Identifier SNIFFER_DIGGING_ID = new Identifier("minecraft", "gameplay/sniffer_digging");

    private static final Identifier FERN_ID = new Identifier("minecraft", "blocks/fern");
    private static final Identifier CHERRY_LEAVES_ID = new Identifier("minecraft", "blocks/cherry_leaves");

    private static final Identifier FISHING_JUNK_ID = new Identifier("minecraft", "gameplay/fishing/junk");

    private static final Identifier VILLAGE_FLETCHER_ID = new Identifier("minecraft", "chests/village/village_fletcher");
    private static final Identifier VILLAGE_BUTCHER_ID = new Identifier("minecraft", "chests/village/village_butcher");
    private static final Identifier VILLAGE_TANNERY_ID = new Identifier("minecraft", "chests/village/village_tannery");
    private static final Identifier VILLAGE_SHEPHERD_ID = new Identifier("minecraft", "chests/village/village_shepherd");
    private static final Identifier IGLOO_CHEST_ID = new Identifier("minecraft", "chests/igloo_chest");
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

    private static final Identifier BLAZE_ID = new Identifier("minecraft", "entities/blaze");
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
        Map<Enchantment, Integer> enchantmentMap = new HashMap<>();
        enchantmentMap.put(Enchantments.SILK_TOUCH, 1);

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

            if (id.equals(CHERRY_LEAVES_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS))).build())
                        .conditionally(InvertedLootCondition.builder(MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))))).build())
                        .with(ItemEntry.builder(ModItems.CHERRIES).conditionally(RandomChanceLootCondition.builder(ModConfigs.CHERRY_DROP_RATE)))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 2)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (id.equals(SNIFFER_DIGGING_ID)) {
                tableBuilder.modifyPools(builder -> { builder
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.THORNBLOOM_SEEDS)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.KIWI_SEEDS)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.GLOWLILY_SEEDLING)));
                });
            }

            if (id.equals(END_CITY_TREASURE_ID)) {
                tableBuilder.modifyPools(builder -> {
                    builder.with(AlternativeEntry.builder(ItemEntry.builder(ModItems.BUTTERFLY_ELYTRA_SMITHING_TEMPLATE).weight(2)));
                });
            }

            //Coins
        if (ModConfigs.COIN_DROPS_AND_LOOT_GEN) {
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

            if (id.equals(END_CITY_TREASURE_ID) && !ModConfigs.DISABLE_GREED_LOOT_GENERATON) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.BOOK).conditionally(RandomChanceLootCondition.builder(0.25f))).apply(EnchantRandomlyLootFunction.create().add(ModEnchantments.GREED))
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
        }

            if(id.equals(BLAZE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.SULPHUR))
                        .conditionally(DamageSourcePropertiesLootCondition.builder(new DamageSourcePredicate.Builder().tag(TagPredicate.expected(DamageTypeTags.IS_DROWNING))).build())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(ENDER_DRAGON_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.ENDER_DRAGON_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(1.0f))))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(JUNGLE_TEMPLE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.PANDA_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.15f))))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.OCELOT_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.15f))))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(BURIED_TREASURE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.SQUID_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.3f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(IGLOO_CHEST_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.FOX_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.2f))))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModBlocks.SNOW_FOX_PLUSHIE).weight(1).conditionally(RandomChanceLootCondition.builder(0.2f))))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).conditionally(RandomChanceLootCondition.builder(0.1f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(VILLAGE_SHEPHERD_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.SHEEP_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(VILLAGE_TANNERY_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.COW_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(VILLAGE_BUTCHER_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.PIG_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(VILLAGE_FLETCHER_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModBlocks.CHICKEN_PLUSHIE)).conditionally(RandomChanceLootCondition.builder(0.4f))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if(id.equals(FISHING_JUNK_ID)) {
                tableBuilder.modifyPools(builder -> {
                    builder.with(ItemEntry.builder(ModItems.SMALL_LILY_PADS).weight(10));
                    builder.with(ItemEntry.builder(ModItems.GIANT_LILY_PAD).weight(5));
                    builder.with(ItemEntry.builder(ModItems.GIANT_LILY_PAD_SEEDLING).weight(5));
                });
            }
        }));
    }
}
