package net.emilsg.clutter.block;

import net.emilsg.clutter.Clutter;
import net.emilsg.clutter.block.custom.*;
import net.emilsg.clutter.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.BlockView;

public class ModBlocks {

    public static final Block WHITE_LAMP = registerBlock("white_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.WHITE_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block LIGHT_GRAY_LAMP = registerBlock("light_gray_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.LIGHT_GRAY_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block GRAY_LAMP = registerBlock("gray_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.GRAY_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block BLACK_LAMP = registerBlock("black_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.BLACK_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block BROWN_LAMP = registerBlock("brown_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.BROWN_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block RED_LAMP = registerBlock("red_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.RED_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block ORANGE_LAMP = registerBlock("orange_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.ORANGE_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block YELLOW_LAMP = registerBlock("yellow_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.YELLOW_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block LIME_LAMP = registerBlock("lime_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.LIME_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block GREEN_LAMP = registerBlock("green_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.GREEN_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block CYAN_LAMP = registerBlock("cyan_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.CYAN_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block LIGHT_BLUE_LAMP = registerBlock("light_blue_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.LIGHT_BLUE_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block BLUE_LAMP = registerBlock("blue_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.BLUE_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block PURPLE_LAMP = registerBlock("purple_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.PURPLE_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block MAGENTA_LAMP = registerBlock("magenta_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.MAGENTA_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block PINK_LAMP = registerBlock("pink_lamp", new LampBlock(FabricBlockSettings.copy(Blocks.PINK_WOOL).luminance(LampBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);

    public static final Block WHITE_ARMCHAIR = registerBlock("white_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.WHITE_WOOL)), ModItemGroup.CLUTTER);
    public static final Block LIGHT_GRAY_ARMCHAIR = registerBlock("light_gray_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.LIGHT_GRAY_WOOL)), ModItemGroup.CLUTTER);
    public static final Block GRAY_ARMCHAIR = registerBlock("gray_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.GRAY_WOOL)), ModItemGroup.CLUTTER);
    public static final Block BLACK_ARMCHAIR = registerBlock("black_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.BLACK_WOOL)), ModItemGroup.CLUTTER);
    public static final Block BROWN_ARMCHAIR = registerBlock("brown_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.BROWN_WOOL)), ModItemGroup.CLUTTER);
    public static final Block RED_ARMCHAIR = registerBlock("red_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.RED_WOOL)), ModItemGroup.CLUTTER);
    public static final Block ORANGE_ARMCHAIR = registerBlock("orange_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.ORANGE_WOOL)), ModItemGroup.CLUTTER);
    public static final Block YELLOW_ARMCHAIR = registerBlock("yellow_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.YELLOW_WOOL)), ModItemGroup.CLUTTER);
    public static final Block LIME_ARMCHAIR = registerBlock("lime_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.LIME_WOOL)), ModItemGroup.CLUTTER);
    public static final Block GREEN_ARMCHAIR = registerBlock("green_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.GREEN_WOOL)), ModItemGroup.CLUTTER);
    public static final Block CYAN_ARMCHAIR = registerBlock("cyan_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.CYAN_WOOL)), ModItemGroup.CLUTTER);
    public static final Block LIGHT_BLUE_ARMCHAIR = registerBlock("light_blue_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.LIGHT_BLUE_WOOL)), ModItemGroup.CLUTTER);
    public static final Block BLUE_ARMCHAIR = registerBlock("blue_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.BLUE_WOOL)), ModItemGroup.CLUTTER);
    public static final Block PURPLE_ARMCHAIR = registerBlock("purple_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.PURPLE_WOOL)), ModItemGroup.CLUTTER);
    public static final Block MAGENTA_ARMCHAIR = registerBlock("magenta_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.MAGENTA_WOOL)), ModItemGroup.CLUTTER);
    public static final Block PINK_ARMCHAIR = registerBlock("pink_armchair", new ArmchairBlock(FabricBlockSettings.copy(Blocks.PINK_WOOL)), ModItemGroup.CLUTTER);
    public static final Block WHITE_BUNK_BED = registerBlock("white_bunk_bed", new BunkBedBlock(DyeColor.WHITE, FabricBlockSettings.copy(Blocks.WHITE_BED)), ModItemGroup.CLUTTER);
    public static final Block LIGHT_GRAY_BUNK_BED = registerBlock("light_gray_bunk_bed", new BunkBedBlock(DyeColor.LIGHT_GRAY, FabricBlockSettings.copy(Blocks.LIGHT_GRAY_BED)), ModItemGroup.CLUTTER);
    public static final Block GRAY_BUNK_BED = registerBlock("gray_bunk_bed", new BunkBedBlock(DyeColor.GRAY, FabricBlockSettings.copy(Blocks.GRAY_BED)), ModItemGroup.CLUTTER);
    public static final Block BLACK_BUNK_BED = registerBlock("black_bunk_bed", new BunkBedBlock(DyeColor.BLACK, FabricBlockSettings.copy(Blocks.BLACK_BED)), ModItemGroup.CLUTTER);
    public static final Block BROWN_BUNK_BED = registerBlock("brown_bunk_bed", new BunkBedBlock(DyeColor.BROWN, FabricBlockSettings.copy(Blocks.BROWN_BED)), ModItemGroup.CLUTTER);
    public static final Block RED_BUNK_BED = registerBlock("red_bunk_bed", new BunkBedBlock(DyeColor.RED, FabricBlockSettings.copy(Blocks.RED_BED)), ModItemGroup.CLUTTER);
    public static final Block ORANGE_BUNK_BED = registerBlock("orange_bunk_bed", new BunkBedBlock(DyeColor.ORANGE, FabricBlockSettings.copy(Blocks.ORANGE_BED)), ModItemGroup.CLUTTER);
    public static final Block YELLOW_BUNK_BED = registerBlock("yellow_bunk_bed", new BunkBedBlock(DyeColor.YELLOW, FabricBlockSettings.copy(Blocks.YELLOW_BED)), ModItemGroup.CLUTTER);
    public static final Block LIME_BUNK_BED = registerBlock("lime_bunk_bed", new BunkBedBlock(DyeColor.LIME, FabricBlockSettings.copy(Blocks.LIME_BED)), ModItemGroup.CLUTTER);
    public static final Block GREEN_BUNK_BED = registerBlock("green_bunk_bed", new BunkBedBlock(DyeColor.GREEN, FabricBlockSettings.copy(Blocks.GREEN_BED)), ModItemGroup.CLUTTER);
    public static final Block CYAN_BUNK_BED = registerBlock("cyan_bunk_bed", new BunkBedBlock(DyeColor.CYAN, FabricBlockSettings.copy(Blocks.CYAN_BED)), ModItemGroup.CLUTTER);
    public static final Block LIGHT_BLUE_BUNK_BED = registerBlock("light_blue_bunk_bed", new BunkBedBlock(DyeColor.LIGHT_BLUE, FabricBlockSettings.copy(Blocks.LIGHT_BLUE_BED)), ModItemGroup.CLUTTER);
    public static final Block BLUE_BUNK_BED = registerBlock("blue_bunk_bed", new BunkBedBlock(DyeColor.BLUE, FabricBlockSettings.copy(Blocks.BLUE_BED)), ModItemGroup.CLUTTER);
    public static final Block PURPLE_BUNK_BED = registerBlock("purple_bunk_bed", new BunkBedBlock(DyeColor.PURPLE, FabricBlockSettings.copy(Blocks.PURPLE_BED)), ModItemGroup.CLUTTER);
    public static final Block MAGENTA_BUNK_BED = registerBlock("magenta_bunk_bed", new BunkBedBlock(DyeColor.MAGENTA, FabricBlockSettings.copy(Blocks.MAGENTA_BED)), ModItemGroup.CLUTTER);
    public static final Block PINK_BUNK_BED = registerBlock("pink_bunk_bed", new BunkBedBlock(DyeColor.PINK, FabricBlockSettings.copy(Blocks.PINK_BED)), ModItemGroup.CLUTTER);

    public static final Block DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(4.5f, 3.0f).requiresTool().mapColor(MapColor.DEEPSLATE_GRAY), UniformIntProvider.create(4, 8)), ModItemGroup.CLUTTER);
    public static final Block SILVER_DOOR = registerBlock("silver_door", new DoorBlock(FabricBlockSettings.of(Material.METAL, MapColor.LIGHT_GRAY).requiresTool().strength(3.0f).sounds(BlockSoundGroup.METAL).nonOpaque(), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block SILVER_ORE = registerBlock("silver_ore", new ExperienceDroppingBlock(FabricBlockSettings.of(Material.STONE).strength(3.0f, 3.0f).requiresTool(), UniformIntProvider.create(4, 8)), ModItemGroup.CLUTTER);
    public static final Block RAW_SILVER_BLOCK = registerBlock("raw_silver_block", new Block(FabricBlockSettings.copy(Blocks.RAW_GOLD_BLOCK).mapColor(MapColor.LIGHT_GRAY)), ModItemGroup.CLUTTER);
    public static final Block SILVER_BLOCK = registerBlock("silver_block", new Block(FabricBlockSettings.copy(Blocks.GOLD_BLOCK).mapColor(MapColor.LIGHT_GRAY)), ModItemGroup.CLUTTER);
    public static final Block SILVER_BUTTON = registerBlock("silver_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 80, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block SILVER_CANDELABRA = registerBlock("silver_candelabra", new CandelabraBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).luminance(CandelabraBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block SILVER_CHAIN = registerBlock("silver_chain", new ChainBlock(FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block SILVER_CHANDELIER = registerBlock("silver_chandelier", new ChandelierBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).luminance(ChandelierBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block SILVER_LANTERN = registerBlock("silver_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block SILVER_SOUL_LANTERN = registerBlock("silver_soul_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block SILVER_TRAPDOOR = registerBlock("silver_trapdoor", new ModTrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);

    public static final Block COPPER_BARS = registerBlock("copper_bars", new OxidizablePaneBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.COPPER)), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_BARS = registerBlock("exposed_copper_bars", new OxidizablePaneBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.COPPER)), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_BARS = registerBlock("weathered_copper_bars", new OxidizablePaneBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.of(Material.METAL, MapColor.DARK_AQUA).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.COPPER)), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_BARS = registerBlock("oxidized_copper_bars", new OxidizablePaneBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.of(Material.METAL, MapColor.TEAL).requiresTool().strength(3.0f, 6.0f).sounds(BlockSoundGroup.COPPER)), ModItemGroup.CLUTTER);
    public static final Block COPPER_BUTTON = registerBlock("copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.copy(Blocks.STONE_BUTTON), 10, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_BUTTON = registerBlock("exposed_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copy(Blocks.STONE_BUTTON), 20, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_BUTTON = registerBlock("weathered_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copy(Blocks.STONE_BUTTON), 30, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_BUTTON = registerBlock("oxidized_copper_button", new OxidizableButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copy(Blocks.STONE_BUTTON), 40, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block COPPER_CANDELABRA = registerBlock("copper_candelabra", new OxidizableCandelabraBlock(Oxidizable.OxidationLevel.UNAFFECTED,FabricBlockSettings.copy(Blocks.COPPER_BLOCK).luminance(CandelabraBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_CANDELABRA = registerBlock("exposed_copper_candelabra", new OxidizableCandelabraBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copy(Blocks.EXPOSED_COPPER).luminance(CandelabraBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_CANDELABRA = registerBlock("weathered_copper_candelabra", new OxidizableCandelabraBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copy(Blocks.WEATHERED_COPPER).luminance(CandelabraBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_CANDELABRA = registerBlock("oxidized_copper_candelabra", new OxidizableCandelabraBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copy(Blocks.OXIDIZED_COPPER).luminance(CandelabraBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block COPPER_CHANDELIER = registerBlock("copper_chandelier", new OxidizedChandelierBlock(Oxidizable.OxidationLevel.UNAFFECTED,FabricBlockSettings.copy(Blocks.COPPER_BLOCK).luminance(ChandelierBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_CHANDELIER = registerBlock("exposed_copper_chandelier", new OxidizedChandelierBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copy(Blocks.EXPOSED_COPPER).luminance(ChandelierBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_CHANDELIER = registerBlock("weathered_copper_chandelier", new OxidizedChandelierBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copy(Blocks.WEATHERED_COPPER).luminance(ChandelierBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_CHANDELIER = registerBlock("oxidized_copper_chandelier", new OxidizedChandelierBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copy(Blocks.OXIDIZED_COPPER).luminance(ChandelierBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block COPPER_CHAIN = registerBlock("copper_chain", new OxidizableChainBlock(Oxidizable.OxidationLevel.UNAFFECTED,FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_CHAIN = registerBlock("exposed_copper_chain", new OxidizableChainBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_CHAIN = registerBlock("weathered_copper_chain", new OxidizableChainBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_CHAIN = registerBlock("oxidized_copper_chain", new OxidizableChainBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block COPPER_DOOR = registerBlock("copper_door", new OxidizableDoorBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).requiresTool().strength(5.0f).sounds(BlockSoundGroup.METAL).nonOpaque(), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_DOOR = registerBlock("exposed_copper_door", new OxidizableDoorBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.of(Material.METAL, MapColor.TERRACOTTA_LIGHT_GRAY).requiresTool().strength(5.0f).sounds(BlockSoundGroup.METAL).nonOpaque(), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_DOOR = registerBlock("weathered_copper_door", new OxidizableDoorBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.of(Material.METAL, MapColor.DARK_AQUA).requiresTool().strength(5.0f).sounds(BlockSoundGroup.METAL).nonOpaque(), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_DOOR = registerBlock("oxidized_copper_door", new OxidizableDoorBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.of(Material.METAL, MapColor.TEAL).requiresTool().strength(5.0f).sounds(BlockSoundGroup.METAL).nonOpaque(), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block COPPER_LANTERN = registerBlock("copper_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_LANTERN = registerBlock("exposed_copper_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_LANTERN = registerBlock("weathered_copper_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_LANTERN = registerBlock("oxidized_copper_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block COPPER_SOUL_LANTERN = registerBlock("copper_soul_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_SOUL_LANTERN = registerBlock("exposed_copper_soul_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_SOUL_LANTERN = registerBlock("weathered_copper_soul_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_SOUL_LANTERN = registerBlock("oxidized_copper_soul_lantern", new OxidizableLanternBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block COPPER_PRESSURE_PLATE = registerBlock("copper_pressure_plate", new OxidizablePressurePlateBlock(Oxidizable.OxidationLevel.UNAFFECTED, PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_PRESSURE_PLATE = registerBlock("exposed_copper_pressure_plate", new OxidizablePressurePlateBlock(Oxidizable.OxidationLevel.EXPOSED, PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_PRESSURE_PLATE = registerBlock("weathered_copper_pressure_plate", new OxidizablePressurePlateBlock(Oxidizable.OxidationLevel.WEATHERED, PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_PRESSURE_PLATE = registerBlock("oxidized_copper_pressure_plate", new OxidizablePressurePlateBlock(Oxidizable.OxidationLevel.OXIDIZED, PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block COPPER_TRAPDOOR = registerBlock("copper_trapdoor", new OxidizableTrapdoorBlock(Oxidizable.OxidationLevel.UNAFFECTED, FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block EXPOSED_COPPER_TRAPDOOR = registerBlock("exposed_copper_trapdoor", new OxidizableTrapdoorBlock(Oxidizable.OxidationLevel.EXPOSED, FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WEATHERED_COPPER_TRAPDOOR = registerBlock("weathered_copper_trapdoor", new OxidizableTrapdoorBlock(Oxidizable.OxidationLevel.WEATHERED, FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block OXIDIZED_COPPER_TRAPDOOR = registerBlock("oxidized_copper_trapdoor", new OxidizableTrapdoorBlock(Oxidizable.OxidationLevel.OXIDIZED, FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);

    public static final Block WAXED_COPPER_BARS = registerBlock("waxed_copper_bars", new PaneBlock(FabricBlockSettings.copy(ModBlocks.COPPER_BARS)), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_BARS = registerBlock("waxed_exposed_copper_bars", new PaneBlock(FabricBlockSettings.copy(ModBlocks.EXPOSED_COPPER_BARS)), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_BARS = registerBlock("waxed_weathered_copper_bars", new PaneBlock(FabricBlockSettings.copy(ModBlocks.WEATHERED_COPPER_BARS)), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_BARS = registerBlock("waxed_oxidized_copper_bars", new PaneBlock(FabricBlockSettings.copy(ModBlocks.OXIDIZED_COPPER_BARS)), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_BUTTON = registerBlock("waxed_copper_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 10, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_BUTTON = registerBlock("waxed_exposed_copper_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 20, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_BUTTON = registerBlock("waxed_weathered_copper_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 30, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_BUTTON = registerBlock("waxed_oxidized_copper_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 40, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_CANDELABRA = registerBlock("waxed_copper_candelabra", new CandelabraBlock(FabricBlockSettings.copy(ModBlocks.COPPER_CANDELABRA)), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_CANDELABRA = registerBlock("waxed_exposed_copper_candelabra", new CandelabraBlock(FabricBlockSettings.copy(ModBlocks.EXPOSED_COPPER_CANDELABRA)), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_CANDELABRA = registerBlock("waxed_weathered_copper_candelabra", new CandelabraBlock(FabricBlockSettings.copy(ModBlocks.WEATHERED_COPPER_CANDELABRA)), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_CANDELABRA = registerBlock("waxed_oxidized_copper_candelabra", new CandelabraBlock(FabricBlockSettings.copy(ModBlocks.OXIDIZED_COPPER_CANDELABRA)), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_CHANDELIER = registerBlock("waxed_copper_chandelier", new ChandelierBlock(FabricBlockSettings.copy(ModBlocks.COPPER_CHANDELIER)), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_CHANDELIER = registerBlock("waxed_exposed_copper_chandelier", new ChandelierBlock(FabricBlockSettings.copy(ModBlocks.EXPOSED_COPPER_CHANDELIER)), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_CHANDELIER = registerBlock("waxed_weathered_copper_chandelier", new ChandelierBlock(FabricBlockSettings.copy(ModBlocks.WEATHERED_COPPER_CHANDELIER)), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_CHANDELIER = registerBlock("waxed_oxidized_copper_chandelier", new ChandelierBlock(FabricBlockSettings.copy(ModBlocks.OXIDIZED_COPPER_CHANDELIER)), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_CHAIN = registerBlock("waxed_copper_chain", new ChainBlock(FabricBlockSettings.copy(ModBlocks.COPPER_CHAIN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_CHAIN = registerBlock("waxed_exposed_copper_chain", new ChainBlock(FabricBlockSettings.copy(ModBlocks.EXPOSED_COPPER_CHAIN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_CHAIN = registerBlock("waxed_weathered_copper_chain", new ChainBlock(FabricBlockSettings.copy(ModBlocks.WEATHERED_COPPER_CHAIN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_CHAIN = registerBlock("waxed_oxidized_copper_chain", new ChainBlock(FabricBlockSettings.copy(ModBlocks.OXIDIZED_COPPER_CHAIN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_DOOR = registerBlock("waxed_copper_door", new DoorBlock(FabricBlockSettings.copy(ModBlocks.COPPER_DOOR), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_DOOR = registerBlock("waxed_exposed_copper_door", new DoorBlock(FabricBlockSettings.copy(ModBlocks.EXPOSED_COPPER_DOOR), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_DOOR = registerBlock("waxed_weathered_copper_door", new DoorBlock(FabricBlockSettings.copy(ModBlocks.WEATHERED_COPPER_DOOR), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_DOOR = registerBlock("waxed_oxidized_copper_door", new DoorBlock(FabricBlockSettings.copy(ModBlocks.OXIDIZED_COPPER_DOOR), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_LANTERN = registerBlock("waxed_copper_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_LANTERN = registerBlock("waxed_exposed_copper_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_LANTERN = registerBlock("waxed_weathered_copper_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_LANTERN = registerBlock("waxed_oxidized_copper_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_SOUL_LANTERN = registerBlock("waxed_copper_soul_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_SOUL_LANTERN = registerBlock("waxed_exposed_copper_soul_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_SOUL_LANTERN = registerBlock("waxed_weathered_copper_soul_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_SOUL_LANTERN = registerBlock("waxed_oxidized_copper_soul_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_PRESSURE_PLATE = registerBlock("waxed_copper_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(ModBlocks.COPPER_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_PRESSURE_PLATE = registerBlock("waxed_exposed_copper_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_PRESSURE_PLATE = registerBlock("waxed_weathered_copper_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_PRESSURE_PLATE = registerBlock("waxed_oxidized_copper_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, FabricBlockSettings.copy(ModBlocks.OXIDIZED_COPPER_PRESSURE_PLATE), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block WAXED_COPPER_TRAPDOOR = registerBlock("waxed_copper_trapdoor", new ModTrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WAXED_EXPOSED_COPPER_TRAPDOOR = registerBlock("waxed_exposed_copper_trapdoor", new ModTrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WAXED_WEATHERED_COPPER_TRAPDOOR = registerBlock("waxed_weathered_copper_trapdoor", new ModTrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block WAXED_OXIDIZED_COPPER_TRAPDOOR = registerBlock("waxed_oxidized_copper_trapdoor", new ModTrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);

    public static final Block GOLDEN_BUTTON = registerBlock("golden_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 20, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block GOLDEN_CANDELABRA = registerBlock("golden_candelabra", new CandelabraBlock(FabricBlockSettings.copy(Blocks.GOLD_BLOCK).luminance(CandelabraBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block GOLDEN_CHAIN = registerBlock("golden_chain", new ChainBlock(FabricBlockSettings.copy(Blocks.CHAIN)), ModItemGroup.CLUTTER);
    public static final Block GOLDEN_CHANDELIER = registerBlock("golden_chandelier", new ChandelierBlock(FabricBlockSettings.copy(Blocks.GOLD_BLOCK).luminance(ChandelierBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block GOLDEN_DOOR = registerBlock("golden_door", new DoorBlock(FabricBlockSettings.copy(Blocks.IRON_DOOR), SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundEvents.BLOCK_IRON_DOOR_OPEN), ModItemGroup.CLUTTER);
    public static final Block GOLDEN_LANTERN = registerBlock("golden_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.LANTERN)), ModItemGroup.CLUTTER);
    public static final Block GOLDEN_SOUL_LANTERN = registerBlock("golden_soul_lantern", new LanternBlock(FabricBlockSettings.copy(Blocks.SOUL_LANTERN)), ModItemGroup.CLUTTER);
    public static final Block GOLDEN_TRAPDOOR = registerBlock("golden_trapdoor", new ModTrapdoorBlock(FabricBlockSettings.copy(Blocks.IRON_TRAPDOOR), SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN), ModItemGroup.CLUTTER);

    public static final Block IRON_BUTTON = registerBlock("iron_button", new ButtonBlock(FabricBlockSettings.copy(Blocks.STONE_BUTTON), 60, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON), ModItemGroup.CLUTTER);
    public static final Block IRON_CANDELABRA = registerBlock("iron_candelabra", new CandelabraBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).luminance(CandelabraBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);
    public static final Block IRON_CHANDELIER = registerBlock("iron_chandelier", new ChandelierBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).luminance(ChandelierBlock.createLightLevelFromLitBlockState(12))), ModItemGroup.CLUTTER);

    public static final Block ANDESITE_CHIMNEY = registerBlock("andesite_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.ANDESITE_WALL)), ModItemGroup.CLUTTER);
    public static final Block BLACKSTONE_CHIMNEY = registerBlock("blackstone_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.BLACKSTONE_WALL)), ModItemGroup.CLUTTER);
    public static final Block BRICK_CHIMNEY = registerBlock("brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block COBBLESTONE_CHIMNEY = registerBlock("cobblestone_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.COBBLESTONE_WALL)), ModItemGroup.CLUTTER);
    public static final Block DEEPSLATE_BRICK_CHIMNEY = registerBlock("deepslate_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block DEEPSLATE_TILE_CHIMNEY = registerBlock("deepslate_tile_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.DEEPSLATE_TILE_WALL)), ModItemGroup.CLUTTER);
    public static final Block DIORITE_CHIMNEY = registerBlock("diorite_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.DIORITE_WALL)), ModItemGroup.CLUTTER);
    public static final Block END_STONE_BRICK_CHIMNEY = registerBlock("end_stone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.END_STONE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block GRANITE_CHIMNEY = registerBlock("granite_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.GRANITE_WALL)), ModItemGroup.CLUTTER);
    public static final Block MOSSY_STONE_BRICK_CHIMNEY = registerBlock("mossy_stone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.MOSSY_STONE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block MUD_BRICK_CHIMNEY = registerBlock("mud_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.MUD_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block NETHER_BRICK_CHIMNEY = registerBlock("nether_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.NETHER_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block POLISHED_BLACKSTONE_BRICK_CHIMNEY = registerBlock("polished_blackstone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.POLISHED_BLACKSTONE_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block RED_NETHER_BRICK_CHIMNEY = registerBlock("red_nether_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.RED_NETHER_BRICK_WALL)), ModItemGroup.CLUTTER);
    public static final Block STONE_BRICK_CHIMNEY = registerBlock("stone_brick_chimney", new ChimneyBlock(FabricBlockSettings.copy(Blocks.STONE_BRICK_WALL)), ModItemGroup.CLUTTER);

    public static final Block OAK_WALL_BOOKSHELF = registerBlock("oak_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block OAK_WINDOW_SILL = registerBlock("oak_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block SPRUCE_WALL_BOOKSHELF = registerBlock("spruce_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block SPRUCE_WINDOW_SILL = registerBlock("spruce_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.SPRUCE_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block BIRCH_WALL_BOOKSHELF = registerBlock("birch_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.BIRCH_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block BIRCH_WINDOW_SILL = registerBlock("birch_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.BIRCH_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block JUNGLE_WALL_BOOKSHELF = registerBlock("jungle_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.JUNGLE_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block JUNGLE_WINDOW_SILL = registerBlock("jungle_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.JUNGLE_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block ACACIA_WALL_BOOKSHELF = registerBlock("acacia_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.ACACIA_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block ACACIA_WINDOW_SILL = registerBlock("acacia_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.ACACIA_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block DARK_OAK_WALL_BOOKSHELF = registerBlock("dark_oak_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.DARK_OAK_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block DARK_OAK_WINDOW_SILL = registerBlock("dark_oak_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.DARK_OAK_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block MANGROVE_WALL_BOOKSHELF = registerBlock("mangrove_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.MANGROVE_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block MANGROVE_WINDOW_SILL = registerBlock("mangrove_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.MANGROVE_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block CRIMSON_WALL_BOOKSHELF = registerBlock("crimson_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.CRIMSON_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block CRIMSON_WINDOW_SILL = registerBlock("crimson_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.CRIMSON_PLANKS)), ModItemGroup.CLUTTER);
    public static final Block WARPED_WALL_BOOKSHELF = registerBlock("warped_wall_bookshelf", new WallBookshelfBlock(FabricBlockSettings.copy(Blocks.WARPED_PLANKS).luminance(WallBookshelfBlock.createLightLevelFromLitBlockState(8))), ModItemGroup.CLUTTER);
    public static final Block WARPED_WINDOW_SILL = registerBlock("warped_window_sill", new WindowSillBlock(FabricBlockSettings.copy(Blocks.WARPED_PLANKS)), ModItemGroup.CLUTTER);

    public static final Block APPLE_FOOD_BOX = registerBlock("apple_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block BEETROOT_FOOD_BOX = registerBlock("beetroot_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block CARROT_FOOD_BOX = registerBlock("carrot_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block POTATO_FOOD_BOX = registerBlock("potato_food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);
    public static final Block FOOD_BOX = registerBlock("food_box", new FoodBoxBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), ModItemGroup.CLUTTER);

    public static final Block BROWN_WALL_MUSHROOMS = registerBlock("brown_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);
    public static final Block RED_WALL_MUSHROOMS = registerBlock("red_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);
    public static final Block SCULK_MUSHROOM = registerBlock("sculk_mushroom", new ModMushroomPlantBlock(FabricBlockSettings.copy(Blocks.DANDELION).luminance(state -> 6)), ModItemGroup.CLUTTER);
    public static final Block SCULK_WALL_MUSHROOMS = registerBlock("sculk_wall_mushrooms", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE).luminance(state -> 6)), ModItemGroup.CLUTTER);
    public static final Block CRIMSON_WALL_FUNGI = registerBlock("crimson_wall_fungi", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);
    public static final Block WARPED_WALL_FUNGI = registerBlock("warped_wall_fungi", new LadderBlock(FabricBlockSettings.copy(Blocks.VINE)), ModItemGroup.CLUTTER);

    public static final Block WHITE_KITCHEN_CURTAINS = registerBlock("white_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.WHITE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LIGHT_GRAY_KITCHEN_CURTAINS = registerBlock("light_gray_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.LIGHT_GRAY_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block GRAY_KITCHEN_CURTAINS = registerBlock("gray_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.GRAY_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block BLACK_KITCHEN_CURTAINS = registerBlock("black_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.BLACK_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block BROWN_KITCHEN_CURTAINS = registerBlock("brown_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.BROWN_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block RED_KITCHEN_CURTAINS = registerBlock("red_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.RED_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block ORANGE_KITCHEN_CURTAINS = registerBlock("orange_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.ORANGE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block YELLOW_KITCHEN_CURTAINS = registerBlock("yellow_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.YELLOW_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LIME_KITCHEN_CURTAINS = registerBlock("lime_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.LIME_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block GREEN_KITCHEN_CURTAINS = registerBlock("green_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.GREEN_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block CYAN_KITCHEN_CURTAINS = registerBlock("cyan_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.CYAN_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LIGHT_BLUE_KITCHEN_CURTAINS = registerBlock("light_blue_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.LIGHT_BLUE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block BLUE_KITCHEN_CURTAINS = registerBlock("blue_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.BLUE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block PURPLE_KITCHEN_CURTAINS = registerBlock("purple_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.PURPLE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block MAGENTA_KITCHEN_CURTAINS = registerBlock("magenta_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.MAGENTA_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block PINK_KITCHEN_CURTAINS = registerBlock("pink_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.PINK_WOOL).noCollision()), ModItemGroup.CLUTTER);

    public static final Block LONG_WHITE_KITCHEN_CURTAINS = registerBlock("long_white_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.WHITE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_LIGHT_GRAY_KITCHEN_CURTAINS = registerBlock("long_light_gray_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.LIGHT_GRAY_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_GRAY_KITCHEN_CURTAINS = registerBlock("long_gray_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.GRAY_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_BLACK_KITCHEN_CURTAINS = registerBlock("long_black_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.BLACK_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_BROWN_KITCHEN_CURTAINS = registerBlock("long_brown_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.BROWN_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_RED_KITCHEN_CURTAINS = registerBlock("long_red_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.RED_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_ORANGE_KITCHEN_CURTAINS = registerBlock("long_orange_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.LIGHT_BLUE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_YELLOW_KITCHEN_CURTAINS = registerBlock("long_yellow_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.YELLOW_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_LIME_KITCHEN_CURTAINS = registerBlock("long_lime_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.LIME_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_GREEN_KITCHEN_CURTAINS = registerBlock("long_green_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.GREEN_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_CYAN_KITCHEN_CURTAINS = registerBlock("long_cyan_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.CYAN_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_LIGHT_BLUE_KITCHEN_CURTAINS = registerBlock("long_light_blue_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.LIGHT_BLUE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_BLUE_KITCHEN_CURTAINS = registerBlock("long_blue_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.BLUE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_PURPLE_KITCHEN_CURTAINS = registerBlock("long_purple_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.PURPLE_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_MAGENTA_KITCHEN_CURTAINS = registerBlock("long_magenta_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.MAGENTA_WOOL).noCollision()), ModItemGroup.CLUTTER);
    public static final Block LONG_PINK_KITCHEN_CURTAINS = registerBlock("long_pink_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.PINK_WOOL).noCollision()), ModItemGroup.CLUTTER);

    public static final Block PRIDE_KITCHEN_CURTAINS = registerBlock("pride_kitchen_curtains", new KitchenCurtainBlock(FabricBlockSettings.copy(Blocks.BLACK_WOOL).noCollision()), ModItemGroup.CLUTTER);

    public static final Block FLOOR_SEATING = registerBlock("floor_seating", new FloorSeatBlock(FabricBlockSettings.copy(Blocks.LIGHT_GRAY_WOOL)), ModItemGroup.CLUTTER);

    public static final Block BEER_MUG = registerBlockWithoutItem("beer_mug", new BeerMugBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).breakInstantly()));
    public static final Block WOODEN_MUG = registerBlockWithoutItem("wooden_mug", new MugBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).breakInstantly()));
    public static final Block HOPS_CROP = registerBlockWithoutItem("hops_crop", new HopsCropBlock(FabricBlockSettings.copy(Blocks.CARROTS)));
    public static final Block COTTON_CROP = registerBlockWithoutItem("cotton_crop", new CottonCropBlock(FabricBlockSettings.copy(Blocks.WHEAT)));



    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(Clutter.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Clutter.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        Item item = Registry.register(Registries.ITEM, new Identifier(Clutter.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    public static void oxidizableBlockPairs() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_BARS, EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_BARS, WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_BARS, OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_BARS, WAXED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_BARS, WAXED_EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_BARS, WAXED_WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_BARS, WAXED_OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_BUTTON, EXPOSED_COPPER_BUTTON);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_BUTTON, WEATHERED_COPPER_BUTTON);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_BUTTON, OXIDIZED_COPPER_BUTTON);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_BUTTON, WAXED_COPPER_BUTTON);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_BUTTON, WAXED_EXPOSED_COPPER_BUTTON);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_BUTTON, WAXED_WEATHERED_COPPER_BUTTON);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_BUTTON, WAXED_OXIDIZED_COPPER_BUTTON);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_CANDELABRA, EXPOSED_COPPER_CANDELABRA);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_CANDELABRA, WEATHERED_COPPER_CANDELABRA);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_CANDELABRA, OXIDIZED_COPPER_CANDELABRA);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_CANDELABRA, WAXED_COPPER_CANDELABRA);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_CANDELABRA, WAXED_EXPOSED_COPPER_CANDELABRA);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_CANDELABRA, WAXED_WEATHERED_COPPER_CANDELABRA);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_CANDELABRA, WAXED_OXIDIZED_COPPER_CANDELABRA);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_CHANDELIER, EXPOSED_COPPER_CHANDELIER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_CHANDELIER, WEATHERED_COPPER_CHANDELIER);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_CHANDELIER, OXIDIZED_COPPER_CHANDELIER);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_CHANDELIER, WAXED_COPPER_CHANDELIER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_CHANDELIER, WAXED_EXPOSED_COPPER_CHANDELIER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_CHANDELIER, WAXED_WEATHERED_COPPER_CHANDELIER);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_CHANDELIER, WAXED_OXIDIZED_COPPER_CHANDELIER);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_CHAIN, EXPOSED_COPPER_CHAIN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_CHAIN, WEATHERED_COPPER_CHAIN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_CHAIN, OXIDIZED_COPPER_CHAIN);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_CHAIN, WAXED_COPPER_CHAIN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_CHAIN, WAXED_EXPOSED_COPPER_CHAIN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_CHAIN, WAXED_WEATHERED_COPPER_CHAIN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_CHAIN, WAXED_OXIDIZED_COPPER_CHAIN);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_DOOR, EXPOSED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_DOOR, WEATHERED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_DOOR, OXIDIZED_COPPER_DOOR);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_DOOR, WAXED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_DOOR, WAXED_EXPOSED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_DOOR, WAXED_WEATHERED_COPPER_DOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_DOOR, WAXED_OXIDIZED_COPPER_DOOR);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_LANTERN, EXPOSED_COPPER_LANTERN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_LANTERN, WEATHERED_COPPER_LANTERN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_LANTERN, OXIDIZED_COPPER_LANTERN);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_LANTERN, WAXED_COPPER_LANTERN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_LANTERN, WAXED_EXPOSED_COPPER_LANTERN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_LANTERN, WAXED_WEATHERED_COPPER_LANTERN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_LANTERN, WAXED_OXIDIZED_COPPER_LANTERN);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_SOUL_LANTERN, EXPOSED_COPPER_SOUL_LANTERN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_SOUL_LANTERN, WEATHERED_COPPER_SOUL_LANTERN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_SOUL_LANTERN, OXIDIZED_COPPER_SOUL_LANTERN);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_SOUL_LANTERN, WAXED_COPPER_SOUL_LANTERN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_SOUL_LANTERN, WAXED_EXPOSED_COPPER_SOUL_LANTERN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_SOUL_LANTERN, WAXED_WEATHERED_COPPER_SOUL_LANTERN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_SOUL_LANTERN, WAXED_OXIDIZED_COPPER_SOUL_LANTERN);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_PRESSURE_PLATE, EXPOSED_COPPER_PRESSURE_PLATE);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_PRESSURE_PLATE, WEATHERED_COPPER_PRESSURE_PLATE);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_PRESSURE_PLATE, OXIDIZED_COPPER_PRESSURE_PLATE);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_PRESSURE_PLATE, WAXED_COPPER_PRESSURE_PLATE);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_PRESSURE_PLATE, WAXED_EXPOSED_COPPER_PRESSURE_PLATE);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_PRESSURE_PLATE, WAXED_WEATHERED_COPPER_PRESSURE_PLATE);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_PRESSURE_PLATE, WAXED_OXIDIZED_COPPER_PRESSURE_PLATE);

        OxidizableBlocksRegistry.registerOxidizableBlockPair(COPPER_TRAPDOOR, EXPOSED_COPPER_TRAPDOOR);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(EXPOSED_COPPER_TRAPDOOR, WEATHERED_COPPER_TRAPDOOR);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WEATHERED_COPPER_TRAPDOOR, OXIDIZED_COPPER_TRAPDOOR);

        OxidizableBlocksRegistry.registerWaxableBlockPair(COPPER_TRAPDOOR, WAXED_COPPER_TRAPDOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(EXPOSED_COPPER_TRAPDOOR, WAXED_EXPOSED_COPPER_TRAPDOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WEATHERED_COPPER_TRAPDOOR, WAXED_WEATHERED_COPPER_TRAPDOOR);
        OxidizableBlocksRegistry.registerWaxableBlockPair(OXIDIZED_COPPER_TRAPDOOR, WAXED_OXIDIZED_COPPER_TRAPDOOR);
    }

    public static void registerModBlocks() {
        Clutter.LOGGER.info("Registering ModBlocks for " + Clutter.MOD_ID);
    }
}