package net.emilsg.clutter.data;

import net.emilsg.clutter.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

public class LootTableDataGen extends FabricBlockLootTableProvider {

    public LootTableDataGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        this.addCoralDrops(ModBlocks.CUP_CORAL, ModBlocks.DEAD_CUP_CORAL, ModBlocks.CUP_CORAL_BLOCK, ModBlocks.DEAD_CUP_CORAL_BLOCK, ModBlocks.CUP_CORAL_FAN, ModBlocks.DEAD_CUP_CORAL_FAN, ModBlocks.CUP_CORAL_WALL_FAN, ModBlocks.DEAD_CUP_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.PASSION_CORAL, ModBlocks.DEAD_PASSION_CORAL, ModBlocks.PASSION_CORAL_BLOCK, ModBlocks.DEAD_PASSION_CORAL_BLOCK, ModBlocks.PASSION_CORAL_FAN, ModBlocks.DEAD_PASSION_CORAL_FAN, ModBlocks.PASSION_CORAL_WALL_FAN, ModBlocks.DEAD_PASSION_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.GHOST_CORAL, ModBlocks.DEAD_GHOST_CORAL, ModBlocks.GHOST_CORAL_BLOCK, ModBlocks.DEAD_GHOST_CORAL_BLOCK, ModBlocks.GHOST_CORAL_FAN, ModBlocks.DEAD_GHOST_CORAL_FAN, ModBlocks.GHOST_CORAL_WALL_FAN, ModBlocks.DEAD_GHOST_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.STONE_CORAL, ModBlocks.DEAD_STONE_CORAL, ModBlocks.STONE_CORAL_BLOCK, ModBlocks.DEAD_STONE_CORAL_BLOCK, ModBlocks.STONE_CORAL_FAN, ModBlocks.DEAD_STONE_CORAL_FAN, ModBlocks.STONE_CORAL_WALL_FAN, ModBlocks.DEAD_STONE_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.SLATE_CORAL, ModBlocks.DEAD_SLATE_CORAL, ModBlocks.SLATE_CORAL_BLOCK, ModBlocks.DEAD_SLATE_CORAL_BLOCK, ModBlocks.SLATE_CORAL_FAN, ModBlocks.DEAD_SLATE_CORAL_FAN, ModBlocks.SLATE_CORAL_WALL_FAN, ModBlocks.DEAD_SLATE_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.THORN_CORAL, ModBlocks.DEAD_THORN_CORAL, ModBlocks.THORN_CORAL_BLOCK, ModBlocks.DEAD_THORN_CORAL_BLOCK, ModBlocks.THORN_CORAL_FAN, ModBlocks.DEAD_THORN_CORAL_FAN, ModBlocks.THORN_CORAL_WALL_FAN, ModBlocks.DEAD_THORN_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.COCOA_CORAL, ModBlocks.DEAD_COCOA_CORAL, ModBlocks.COCOA_CORAL_BLOCK, ModBlocks.DEAD_COCOA_CORAL_BLOCK, ModBlocks.COCOA_CORAL_FAN, ModBlocks.DEAD_COCOA_CORAL_FAN, ModBlocks.COCOA_CORAL_WALL_FAN, ModBlocks.DEAD_COCOA_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.TOXIC_CORAL, ModBlocks.DEAD_TOXIC_CORAL, ModBlocks.TOXIC_CORAL_BLOCK, ModBlocks.DEAD_TOXIC_CORAL_BLOCK, ModBlocks.TOXIC_CORAL_FAN, ModBlocks.DEAD_TOXIC_CORAL_FAN, ModBlocks.TOXIC_CORAL_WALL_FAN, ModBlocks.DEAD_TOXIC_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.GEM_CORAL, ModBlocks.DEAD_GEM_CORAL, ModBlocks.GEM_CORAL_BLOCK, ModBlocks.DEAD_GEM_CORAL_BLOCK, ModBlocks.GEM_CORAL_FAN, ModBlocks.DEAD_GEM_CORAL_FAN, ModBlocks.GEM_CORAL_WALL_FAN, ModBlocks.DEAD_GEM_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.DIAMOND_CORAL, ModBlocks.DEAD_DIAMOND_CORAL, ModBlocks.DIAMOND_CORAL_BLOCK, ModBlocks.DEAD_DIAMOND_CORAL_BLOCK, ModBlocks.DIAMOND_CORAL_FAN, ModBlocks.DEAD_DIAMOND_CORAL_FAN, ModBlocks.DIAMOND_CORAL_WALL_FAN, ModBlocks.DEAD_DIAMOND_CORAL_WALL_FAN);
        this.addCoralDrops(ModBlocks.ANCHOR_CORAL, ModBlocks.DEAD_ANCHOR_CORAL, ModBlocks.ANCHOR_CORAL_BLOCK, ModBlocks.DEAD_ANCHOR_CORAL_BLOCK, ModBlocks.ANCHOR_CORAL_FAN, ModBlocks.DEAD_ANCHOR_CORAL_FAN, ModBlocks.ANCHOR_CORAL_WALL_FAN, ModBlocks.DEAD_ANCHOR_CORAL_WALL_FAN);
        addDrop(ModBlocks.BLACK_ONYX_BLOCK);
        addDrop(ModBlocks.BLACK_ONYX_SLAB, slabDrops(ModBlocks.BLACK_ONYX_SLAB));
        addDrop(ModBlocks.BLACK_ONYX_STAIRS);
        addDrop(ModBlocks.BLACK_ONYX_WALL);
        addDrop(ModBlocks.POLISHED_BLACK_ONYX);
        addDrop(ModBlocks.POLISHED_BLACK_ONYX_SLAB, slabDrops(ModBlocks.POLISHED_BLACK_ONYX_SLAB));
        addDrop(ModBlocks.POLISHED_BLACK_ONYX_STAIRS);
        addDrop(ModBlocks.POLISHED_BLACK_ONYX_WALL);
        addDrop(ModBlocks.MAILBOX);
        addDrop(ModBlocks.GLOWLILY_BLOCK);
        addDrop(ModBlocks.AQUATIC_TORCH);
        addDrop(ModBlocks.AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.EXPOSED_AQUATIC_TORCH);
        addDrop(ModBlocks.EXPOSED_AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.WEATHERED_AQUATIC_TORCH);
        addDrop(ModBlocks.WEATHERED_AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.OXIDIZED_AQUATIC_TORCH);
        addDrop(ModBlocks.OXIDIZED_AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.WAXED_AQUATIC_TORCH);
        addDrop(ModBlocks.WAXED_AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.WAXED_EXPOSED_AQUATIC_TORCH);
        addDrop(ModBlocks.WAXED_EXPOSED_AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.WAXED_WEATHERED_AQUATIC_TORCH);
        addDrop(ModBlocks.WAXED_WEATHERED_AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.WAXED_OXIDIZED_AQUATIC_TORCH);
        addDrop(ModBlocks.WAXED_OXIDIZED_AQUATIC_WALL_TORCH);
        addDrop(ModBlocks.PRISMARINE_TORCH);
        addDrop(ModBlocks.PRISMARINE_WALL_TORCH);
        addDrop(ModBlocks.NAUTILUS_SHELL_BLOCK, Items.NAUTILUS_SHELL);
    }

    private void addCoralDrops(Block coral, Block deadCoral, Block coralBlock, Block deadCoralBlock, Block coralFan, Block deadCoralFan, Block coralWallFan, Block deadCoralWallFan) {
        this.addDropWithSilkTouch(coral);
        this.addDropWithSilkTouch(deadCoral);
        this.coralBlockDrops(coralBlock, deadCoralBlock);
        this.addDrop(deadCoralBlock);
        this.addDropWithSilkTouch(coralFan);
        this.addDropWithSilkTouch(deadCoralFan);
        this.addDropWithSilkTouch(coralWallFan);
        this.addDropWithSilkTouch(deadCoralWallFan);
    }

    private void coralBlockDrops(Block coralBlock, Block deadCoralBlock) {
        LootTable.Builder tableBuilder = LootTable.builder().pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).with(AlternativeEntry.builder(ItemEntry.builder(coralBlock).conditionally(WITH_SILK_TOUCH), ItemEntry.builder(deadCoralBlock).conditionally(WITHOUT_SILK_TOUCH))));
        this.addDrop(coralBlock, tableBuilder);
    }
}
