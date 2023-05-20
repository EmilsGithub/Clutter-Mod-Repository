package net.emilsg.clutter;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.entity.ModBlockEntities;
import net.emilsg.clutter.config.ModConfigs;
import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.enchantment.ModEnchantments;
import net.emilsg.clutter.entity.custom.ButterflyEntity;
import net.emilsg.clutter.entity.ModEntities;
import net.emilsg.clutter.entity.custom.ChameleonEntity;
import net.emilsg.clutter.entity.custom.EchofinEntity;
import net.emilsg.clutter.entity.custom.MossbloomEntity;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.potion.ModPotions;
import net.emilsg.clutter.util.ModItemGroup;
import net.emilsg.clutter.util.ModLootTableModifiers;
import net.emilsg.clutter.util.ModSit;
import net.emilsg.clutter.util.ModUtil;
import net.emilsg.clutter.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clutter implements ModInitializer {

	public static final String MOD_ID = "clutter";
	public static final Logger LOGGER = LoggerFactory.getLogger("clutter");

	@Override
	public void onInitialize() {
		ModConfigs.registerConfigs();

		ModItemGroup.registerItemGroups();
		ModEffects.registerEffects();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEnchantments.registerModEnchantments();
		ModLootTableModifiers.modifyLootTables();
		ModBlockEntities.registerBlockEntities();

		ModWorldGeneration.generateModWorldGen();

		ModSit.registerSitUtil();
		ModUtil.registerModUtil();

		ModPotions.registerPotionRecipes();

		ModBlocks.copperBlockPairs();
		FabricDefaultAttributeRegistry.register(ModEntities.BUTTERFLY, ButterflyEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CHAMELEON, ChameleonEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ECHOFIN, EchofinEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MOSSBLOOM, MossbloomEntity.setAttributes());
	}
}