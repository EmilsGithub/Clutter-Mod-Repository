package net.emilsg.clutter.data;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.emilsg.clutter.block.ClutterWoodType;
import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.custom.CandelabraBlock;
import net.emilsg.clutter.block.custom.ChandelierBlock;
import net.emilsg.clutter.block.custom.ChimneyBlock;
import net.emilsg.clutter.block.custom.WallCandleBlock;
import net.emilsg.clutter.block.custom.plushies.AbstractPlushieBlock;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;

import java.util.concurrent.CompletableFuture;

public class LootTableDataGen extends FabricBlockLootTableProvider {
    public static final float[] SAPLING_DROP_CHANCE = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    public static final float[] LEAVES_STICK_DROP_CHANCE = new float[]{0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F};

    public LootTableDataGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
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
        this.addDrop(ModBlocks.NAUTILUS_SHELL_BLOCK, Items.NAUTILUS_SHELL);

        this.addDrop(ModBlocks.OVERGROWN_PACKED_MUD, this.silkTouchDrops(ModBlocks.OVERGROWN_PACKED_MUD, Blocks.PACKED_MUD));
        this.addDrop(ModBlocks.GIANT_FERN, (block) -> this.tallPlantDrops(block, Blocks.LARGE_FERN));

        this.addFoodBoxDrops(ModBlocks.FOOD_BOX, null);
        this.addFoodBoxDrops(ModBlocks.APPLE_FOOD_BOX, Items.APPLE);
        this.addFoodBoxDrops(ModBlocks.BEETROOT_FOOD_BOX, Items.BEETROOT);
        this.addFoodBoxDrops(ModBlocks.BREAD_FOOD_BOX, Items.BREAD);
        this.addFoodBoxDrops(ModBlocks.CARROT_FOOD_BOX, Items.CARROT);
        this.addFoodBoxDrops(ModBlocks.MELON_FOOD_BOX, Items.MELON);
        this.addFoodBoxDrops(ModBlocks.POTATO_FOOD_BOX, Items.POTATO);
        this.addFoodBoxDrops(ModBlocks.CHORUS_FRUIT_FOOD_BOX, Items.CHORUS_FRUIT);
        this.addFoodBoxDrops(ModBlocks.SWEET_BERRY_FOOD_BOX, Items.SWEET_BERRIES);
        this.addFoodBoxDrops(ModBlocks.GLOW_BERRY_FOOD_BOX, Items.GLOW_BERRIES);

        this.addOreDropsWithCount(ModBlocks.SILVER_ORE, ModItems.RAW_SILVER, 2.0f, 4.0f);
        this.addOreDropsWithCount(ModBlocks.DEEPSLATE_SILVER_ORE, ModItems.RAW_SILVER, 2.0f, 4.0f);
        this.addOreDropsWithCount(ModBlocks.BASALT_SULPHUR_ORE, ModItems.SULPHUR, 1.0f, 2.0f);
        this.addOreDropsWithCount(ModBlocks.BLACKSTONE_SULPHUR_ORE, ModItems.SULPHUR, 1.0f, 2.0f);
        this.addOreDropsWithCount(ModBlocks.ONYX_ORE, ModItems.RAW_ONYX, 2.0f, 4.0f);

        this.addOreDropsWithCount(ModBlocks.ONYX_CLUSTER, ModItems.ONYX, 2.0f, 4.0f);
        this.addDropOnlyWithSilkTouch(ModBlocks.LARGE_ONYX_BUD);
        this.addDropOnlyWithSilkTouch(ModBlocks.MEDIUM_ONYX_BUD);
        this.addDropOnlyWithSilkTouch(ModBlocks.SMALL_ONYX_BUD);

        for (Block block : Registries.BLOCK) {
            if (block instanceof AbstractPlushieBlock plushieBlock) this.addDrop(plushieBlock);
            if (block instanceof ChimneyBlock chimneyBlock) this.addDrop(chimneyBlock);

            if (block instanceof ChandelierBlock chandelierBlock) this.addDrop(chandelierBlock);
            if (block instanceof CandelabraBlock candelabraBlock) this.addDrop(candelabraBlock);
            if (block instanceof WallCandleBlock wallCandleBlock) this.addDrop(wallCandleBlock);
        }

        this.addPottedPlantGroupDrops(
                ModBlocks.POTTED_SMALL_BLUE_LUPINE,
                ModBlocks.POTTED_SMALL_MAGENTA_LUPINE,
                ModBlocks.POTTED_SMALL_PURPLE_LUPINE,
                ModBlocks.POTTED_SMALL_YELLOW_LUPINE,
                ModBlocks.POTTED_SMALL_RED_LUPINE,
                ModBlocks.POTTED_SMALL_WHITE_LUPINE
        );

        this.addSlabGroupDrops(
                ModBlocks.REDWOOD_MOSAIC_SLAB,
                ModBlocks.REDWOOD_SLAB,
                ModBlocks.POLISHED_BLACK_ONYX_SLAB,
                ModBlocks.BLACK_ONYX_SLAB
        );

        this.addDoubleBlockGroupDrops(
                ModBlocks.BLUE_LUPINE,
                ModBlocks.MAGENTA_LUPINE,
                ModBlocks.PURPLE_LUPINE,
                ModBlocks.YELLOW_LUPINE,
                ModBlocks.WHITE_LUPINE,
                ModBlocks.RED_LUPINE,
                ModBlocks.REDWOOD_DOOR,
                ModBlocks.CATTAILS
        );

        this.addSingleGroupDrops(
                ModBlocks.BLACK_ONYX_BLOCK,
                ModBlocks.BLACK_ONYX_STAIRS,
                ModBlocks.BLACK_ONYX_WALL,
                ModBlocks.POLISHED_BLACK_ONYX,
                ModBlocks.POLISHED_BLACK_ONYX_STAIRS,
                ModBlocks.POLISHED_BLACK_ONYX_WALL,
                ModBlocks.MAILBOX,
                ModBlocks.GLOWLILY_BLOCK,
                ModBlocks.AQUATIC_TORCH,
                ModBlocks.AQUATIC_WALL_TORCH,
                ModBlocks.EXPOSED_AQUATIC_TORCH,
                ModBlocks.EXPOSED_AQUATIC_WALL_TORCH,
                ModBlocks.WEATHERED_AQUATIC_TORCH,
                ModBlocks.WEATHERED_AQUATIC_WALL_TORCH,
                ModBlocks.OXIDIZED_AQUATIC_TORCH,
                ModBlocks.OXIDIZED_AQUATIC_WALL_TORCH,
                ModBlocks.WAXED_AQUATIC_TORCH,
                ModBlocks.WAXED_AQUATIC_WALL_TORCH,
                ModBlocks.WAXED_EXPOSED_AQUATIC_TORCH,
                ModBlocks.WAXED_EXPOSED_AQUATIC_WALL_TORCH,
                ModBlocks.WAXED_WEATHERED_AQUATIC_TORCH,
                ModBlocks.WAXED_WEATHERED_AQUATIC_WALL_TORCH,
                ModBlocks.WAXED_OXIDIZED_AQUATIC_TORCH,
                ModBlocks.WAXED_OXIDIZED_AQUATIC_WALL_TORCH,
                ModBlocks.PRISMARINE_TORCH,
                ModBlocks.PRISMARINE_WALL_TORCH,
                ModBlocks.PET_BED,
                ModBlocks.REDWOOD_WOOD,
                ModBlocks.STRIPPED_REDWOOD_WOOD,
                ModBlocks.REDWOOD_LOG,
                ModBlocks.STRIPPED_REDWOOD_LOG,
                ModBlocks.REDWOOD_PLANKS,
                ModBlocks.REDWOOD_STAIRS,
                ModBlocks.REDWOOD_FENCE,
                ModBlocks.REDWOOD_FENCE_GATE,
                ModBlocks.REDWOOD_BUTTON,
                ModBlocks.REDWOOD_PRESSURE_PLATE,
                ModBlocks.REDWOOD_MOSAIC,
                ModBlocks.REDWOOD_MOSAIC_STAIRS,
                ModBlocks.REDWOOD_SAPLING,
                ModBlocks.REDWOOD_TRAPDOOR,
                ModBlocks.SMALL_BLUE_LUPINE,
                ModBlocks.SMALL_MAGENTA_LUPINE,
                ModBlocks.SMALL_PURPLE_LUPINE,
                ModBlocks.SMALL_YELLOW_LUPINE,
                ModBlocks.SMALL_RED_LUPINE,
                ModBlocks.SMALL_WHITE_LUPINE,
                ModBlocks.ANCHOR_BLOCK
        );

        this.addWoodDrops(ClutterWoodType.REDWOOD);
        this.addWoodDrops(ClutterWoodType.OAK);
        this.addWoodDrops(ClutterWoodType.BIRCH);
        this.addWoodDrops(ClutterWoodType.JUNGLE);
        this.addWoodDrops(ClutterWoodType.ACACIA);
        this.addWoodDrops(ClutterWoodType.SPRUCE);
        this.addWoodDrops(ClutterWoodType.DARK_OAK);
        this.addWoodDrops(ClutterWoodType.MANGROVE);
        this.addWoodDrops(ClutterWoodType.CRIMSON);
        this.addWoodDrops(ClutterWoodType.WARPED);
        this.addWoodDrops(ClutterWoodType.CHERRY);
        this.addWoodDrops(ClutterWoodType.BAMBOO);

        this.addDrop(ModBlocks.QUARTZ_CRYSTAL, (block) -> {
            return dropsWithSilkTouch(block, (ItemEntry.builder(Items.QUARTZ).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)))));
        });
    }

    private void addWoodDrops(ClutterWoodType woodType) {
        if(woodType.sapling() != null) this.addDrop(woodType.sapling());
        if(woodType.leaves() != null) this.addLeaves(woodType.leaves(), woodType.sapling());
        if(woodType.log() != null) this.addDrop(woodType.log());
        if(woodType.wood() != null) this.addDrop(woodType.wood());
        if(woodType.strippedLog() != null) this.addDrop(woodType.strippedLog());
        if(woodType.strippedWood() != null) this.addDrop(woodType.strippedWood());
        if(woodType.planks() != null) this.addDrop(woodType.planks());
        if(woodType.stairs() != null) this.addDrop(woodType.stairs());
        if(woodType.slab() != null) this.addDrop(woodType.slab(), slabDrops(woodType.slab()));
        if(woodType.fence() != null) this.addDrop(woodType.fence());
        if(woodType.fenceGate() != null) this.addDrop(woodType.fenceGate());
        if(woodType.button() != null) this.addDrop(woodType.button());
        if(woodType.pressurePlate() != null) this.addDrop(woodType.pressurePlate());
        if(woodType.mosaic() != null) this.addDrop(woodType.mosaic());
        if(woodType.mosaicStairs() != null) this.addDrop(woodType.mosaicStairs());
        if(woodType.mosaicSlab() != null) this.addDrop(woodType.mosaicSlab(), slabDrops(woodType.mosaicSlab()));
        if(woodType.door() != null) this.addDrop(woodType.door(), doorDrops(woodType.door()));
        if(woodType.trapdoor() != null) this.addDrop(woodType.trapdoor());
        if(woodType.wallBookshelf() != null) this.addDrop(woodType.wallBookshelf());
        if(woodType.shelf() != null) this.addDrop(woodType.shelf());
        if(woodType.table() != null) this.addDrop(woodType.table());
        if(woodType.strippedTable() != null) this.addDrop(woodType.strippedTable());
        if(woodType.chair() != null) this.addDrop(woodType.chair());
        if(woodType.strippedChair() != null) this.addDrop(woodType.strippedChair());
        if(woodType.shortBench() != null) this.addDrop(woodType.shortBench());
        if(woodType.wallCupboard() != null) this.addDrop(woodType.wallCupboard());
        if(woodType.windowSill() != null) this.addDrop(woodType.windowSill());
        if(woodType.cupboard() != null) this.addDrop(woodType.cupboard());
        //if(woodType.trellis() != null) this.addDrop(woodType.trellis());
        if(woodType.bench() != null) this.addDrop(woodType.bench());
        if(woodType.strippedBench() != null) this.addDrop(woodType.strippedBench());
        if(woodType.signBlock() != null) this.addDrop(woodType.signBlock(), woodType.signItem());
        if(woodType.wallSignBlock() != null) this.addDrop(woodType.wallSignBlock(), woodType.signItem());
        if(woodType.hangingSignBlock() != null) this.addDrop(woodType.hangingSignBlock(), woodType.hangingSignItem());
        if(woodType.hangingWallSignBlock() != null) this.addDrop(woodType.hangingWallSignBlock(), woodType.hangingSignItem());
    }

    private void addLeaves(Block leaves, Block sapling) {
        this.addDrop(leaves, (block) -> this.leavesDrops(block, sapling, SAPLING_DROP_CHANCE));
    }

    private void addDoubleBlockGroupDrops(Block... blocks) {
        for (Block block1 : blocks) {
            this.addDrop(block1, (block) -> this.dropsWithProperty(block, Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
        }
    }

    private void addSingleGroupDrops(Block... blocks) {
        for (Block block : blocks) {
            this.addDrop(block);
        }
    }

    private void addSlabGroupDrops(Block... blocks) {
        for (Block block : blocks) {
            this.addDrop(block, slabDrops(block));
        }
    }

    private void addPottedPlantGroupDrops(Block... blocks) {
        for (Block block : blocks) {
            this.addPottedPlantDrops(block);
        }
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
        LootTable.Builder tableBuilder = LootTable.builder().pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(AlternativeEntry.builder(ItemEntry.builder(coralBlock)
                        .conditionally(createSilkTouchCondition()), ItemEntry.builder(deadCoralBlock)
                        .conditionally(createWithoutSilkTouchCondition()))));
        this.addDrop(coralBlock, tableBuilder);
    }

    public LootTable.Builder silkTouchDrops(Block dropWithSilkTouch, Block drop) {
        return this.dropsWithSilkTouch(dropWithSilkTouch, this.applyExplosionDecay(dropWithSilkTouch, ItemEntry.builder(drop)));
    }

    public void addFoodBoxDrops(Block foodBox, @Nullable Item food) {
        if (food == null) {
            this.addDrop(foodBox, ModBlocks.FOOD_BOX);
            return;
        }

        LootTable.Builder tableBuilder = LootTable.builder().pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(AlternativeEntry.builder(
                        ItemEntry.builder(foodBox)
                                .conditionally(createSilkTouchCondition()),
                        ItemEntry.builder(ModBlocks.FOOD_BOX)
                                .conditionally(createWithoutSilkTouchCondition())))
        );

        tableBuilder.pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(food).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(8)))
                        .conditionally(createWithoutSilkTouchCondition()))
        );

        this.addDrop(foodBox, tableBuilder);
    }

    public void addOreDropsWithCount(Block oreBlock, ItemConvertible rawOreItem, float countMin, float countMax) {
        this.addDrop(oreBlock, this.oreDropsWithCount(oreBlock, rawOreItem, countMin, countMax));
    }

    public LootTable.Builder oreDropsWithCount(Block oreBlock, ItemConvertible rawOreItem, float countMin, float countMax) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(oreBlock, this.applyExplosionDecay(oreBlock, ItemEntry.builder(rawOreItem).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(countMin,countMax))).apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }

    public void addDropOnlyWithSilkTouch(Block block) {
        LootTable.Builder tableBuilder = LootTable.builder().pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(block).conditionally(createSilkTouchCondition()))
        );

        this.addDrop(block, tableBuilder);
    }

    public void addDropOnlyWithSilkTouch(Block block, ItemConvertible drop) {
        LootTable.Builder tableBuilder = LootTable.builder().pool(LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .with(ItemEntry.builder(drop).conditionally(createSilkTouchCondition()))
        );

        this.addDrop(block, tableBuilder);
    }

}
