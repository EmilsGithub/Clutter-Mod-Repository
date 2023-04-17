package net.emilsg.clutter.util;

import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier BLAZE_ID = new Identifier("minecraft", "entities/blaze");
    private static final Identifier CAVE_SPIDER_ID = new Identifier("minecraft", "entities/cave_spider");
    private static final Identifier CREEPER_ID = new Identifier("minecraft", "entities/creeper");
    private static final Identifier DROWNED_ID = new Identifier("minecraft", "entities/drowned");
    private static final Identifier ELDER_GUARDIAN_ID = new Identifier("minecraft", "entities/elder_guardian");
    private static final Identifier ENDER_DRAGON_ID = new Identifier("minecraft", "entities/ender_dragon");
    private static final Identifier ENDERMITE_ID = new Identifier("minecraft", "entities/endermite");
    private static final Identifier ENDERMAN_ID = new Identifier("minecraft", "entities/enderman");
    private static final Identifier EVOKER_ID = new Identifier("minecraft", "entities/evoker");
    private static final Identifier GHAST_ID = new Identifier("minecraft", "entities/ghast");
    private static final Identifier GUARDIAN_ID = new Identifier("minecraft", "entities/guardian");
    private static final Identifier HOGLIN_ID = new Identifier("minecraft", "entities/hoglin");
    private static final Identifier HUSK_ID = new Identifier("minecraft", "entities/husk");
    private static final Identifier MAGMA_CUBE_ID = new Identifier("minecraft", "entities/magma_cube");
    private static final Identifier PHANTOM_ID = new Identifier("minecraft", "entities/phantom");
    private static final Identifier PIGLIN_BRUTE_ID = new Identifier("minecraft", "entities/piglin_brute");
    private static final Identifier PIGLIN_ID = new Identifier("minecraft", "entities/piglin");
    private static final Identifier RAVAGER_ID = new Identifier("minecraft", "entities/ravager");
    private static final Identifier SHULKER_ID = new Identifier("minecraft", "entities/shulker");
    private static final Identifier SILVERFISH_ID = new Identifier("minecraft", "entities/silverfish");
    private static final Identifier SKELETON_ID = new Identifier("minecraft", "entities/skeleton");
    private static final Identifier SLIME_ID = new Identifier("minecraft", "entities/slime");
    private static final Identifier SPIDER_ID = new Identifier("minecraft", "entities/spider");
    private static final Identifier STRAY_ID = new Identifier("minecraft", "entities/stray");
    private static final Identifier VEX_ID = new Identifier("minecraft", "entities/vex");
    private static final Identifier VINDICATOR_ID = new Identifier("minecraft", "entities/vindicator");
    private static final Identifier WITCH_ID = new Identifier("minecraft", "entities/witch");
    private static final Identifier WITHER_ID = new Identifier("minecraft", "entities/wither");
    private static final Identifier WITHER_SKELETON_ID = new Identifier("minecraft", "entities/wither_skeleton");
    private static final Identifier ZOGLIN_ID = new Identifier("minecraft", "entities/zoglin");
    private static final Identifier ZOMBIE_ID = new Identifier("minecraft", "entities/zombie");
    private static final Identifier ZOMBIE_VILLAGER_ID = new Identifier("minecraft", "entities/zombie_villager");
    private static final Identifier ZOMBIFIED_PIGLIN_ID = new Identifier("minecraft", "entities/zombified_piglin");


    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(BLAZE_ID) || id.equals(CREEPER_ID) || id.equals(DROWNED_ID) || id.equals(ENDERMITE_ID) || id.equals(GHAST_ID) || id.equals(GUARDIAN_ID) || id.equals(HOGLIN_ID) || id.equals(HUSK_ID) || id.equals(MAGMA_CUBE_ID) || id.equals(PHANTOM_ID) || id.equals(PIGLIN_ID) || id.equals(SHULKER_ID) || id.equals(SILVERFISH_ID) || id.equals(SKELETON_ID) || id.equals(SPIDER_ID) || id.equals(STRAY_ID) || id.equals(WITCH_ID) || id.equals(WITHER_SKELETON_ID) || id.equals(ZOMBIE_ID) || id.equals(ZOMBIE_VILLAGER_ID) || id.equals(ZOGLIN_ID) || id.equals(CAVE_SPIDER_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.025f)) // 2.5%
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if (id.equals(ENDERMAN_ID) || id.equals(EVOKER_ID) || id.equals(RAVAGER_ID) || id.equals(VEX_ID) || id.equals(VINDICATOR_ID) || id.equals(ZOMBIFIED_PIGLIN_ID) || id.equals(SLIME_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.01f)) // 1%
                        .with(ItemEntry.builder(ModItems.COPPER_COIN))
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if (id.equals(ELDER_GUARDIAN_ID) || id.equals(PIGLIN_BRUTE_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1.0f)) // 100%
                        .with(ItemEntry.builder(ModItems.SILVER_COIN))
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
            if (id.equals(ENDER_DRAGON_ID) || id.equals(WITHER_ID)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(1.0f))
                        .with(ItemEntry.builder(ModItems.GOLDEN_COIN))
                        .conditionally(KilledByPlayerLootCondition.builder())
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 6.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}