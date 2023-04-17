package net.emilsg.clutter;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.block.entity.ModBlockEntities;
import net.emilsg.clutter.effect.ModEffects;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.potion.ModPotions;
import net.emilsg.clutter.util.ModItemGroup;
import net.emilsg.clutter.util.ModLootTableModifiers;
import net.emilsg.clutter.util.ModSit;
import net.emilsg.clutter.util.ModUtil;
import net.emilsg.clutter.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clutter implements ModInitializer {

	public static final String MOD_ID = "clutter";
	public static final Logger LOGGER = LoggerFactory.getLogger("clutter");

	@Override
	public void onInitialize() {
		ModItemGroup.registerItemGroups();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModBlockEntities.registerBlockEntities();

		ModWorldGeneration.generateModWorldGen();

		ModSit.registerSitUtil();
		ModUtil.registerModUtil();

		ModEffects.registerEffects();
		ModPotions.registerPotionRecipes();

		ModLootTableModifiers.modifyLootTables();
		ModBlocks.copperBlockPairs();
	}
}