package net.emilsg.clutter.data;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.item.ModItems;
import net.emilsg.clutter.util.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGen extends FabricTagProvider.ItemTagProvider {
    public ItemTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        /** Vanilla **/

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR).add(
                ModItems.SILVER_HELMET,
                ModItems.SILVER_CHESTPLATE,
                ModItems.SILVER_LEGGINGS,
                ModItems.SILVER_BOOTS,
                ModItems.COPPER_DIVING_HELMET,
                ModItems.COPPER_DIVING_CHESTPLATE,
                ModItems.COPPER_DIVING_LEGGINGS,
                ModItems.COPPER_DIVING_BOOTS
        );

        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS).add(
                ModItems.ONYX,
                ModItems.SILVER_INGOT
        );

        /** Modded **/

        getOrCreateTagBuilder(ModItemTags.FRUITS_AND_BERRIES).add(
                ModItems.CHERRIES,
                Items.APPLE,
                Items.MELON_SLICE,
                Items.GLOW_BERRIES,
                Items.SWEET_BERRIES
        );

        getOrCreateTagBuilder(ModItemTags.BOTTLED_BUTTERFLIES).add(
                ModItems.WHITE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.LIGHT_GRAY_BUTTERFLY_IN_A_BOTTLE,
                ModItems.GRAY_BUTTERFLY_IN_A_BOTTLE,
                ModItems.BLACK_BUTTERFLY_IN_A_BOTTLE,
                ModItems.BROWN_BUTTERFLY_IN_A_BOTTLE,
                ModItems.RED_BUTTERFLY_IN_A_BOTTLE,
                ModItems.ORANGE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.YELLOW_BUTTERFLY_IN_A_BOTTLE,
                ModItems.LIME_BUTTERFLY_IN_A_BOTTLE,
                ModItems.GREEN_BUTTERFLY_IN_A_BOTTLE,
                ModItems.CYAN_BUTTERFLY_IN_A_BOTTLE,
                ModItems.LIGHT_BLUE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.BLUE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.PURPLE_BUTTERFLY_IN_A_BOTTLE,
                ModItems.MAGENTA_BUTTERFLY_IN_A_BOTTLE,
                ModItems.PINK_BUTTERFLY_IN_A_BOTTLE,
                ModItems.CRIMSON_BUTTERFLY_IN_A_BOTTLE,
                ModItems.WARPED_BUTTERFLY_IN_A_BOTTLE,
                ModItems.SOUL_BUTTERFLY_IN_A_BOTTLE
        );

        getOrCreateTagBuilder(ModItemTags.PLUSHIES).add(
                ModBlocks.SHEEP_PLUSHIE.asItem(),
                ModBlocks.COW_PLUSHIE.asItem(),
                ModBlocks.PIG_PLUSHIE.asItem(),
                ModBlocks.SQUID_PLUSHIE.asItem(),
                ModBlocks.CHICKEN_PLUSHIE.asItem(),
                ModBlocks.FOX_PLUSHIE.asItem(),
                ModBlocks.SNOW_FOX_PLUSHIE.asItem(),
                ModBlocks.OCELOT_PLUSHIE.asItem(),
                ModBlocks.PANDA_PLUSHIE.asItem(),
                ModBlocks.ENDER_DRAGON_PLUSHIE.asItem()
        );

        getOrCreateTagBuilder(ModItemTags.TRELLIS_ITEMS).add(
                Items.VINE,
                Items.ROSE_BUSH,
                Items.PEONY,
                Items.LILAC,
                Items.GLOW_BERRIES,
                Items.WEEPING_VINES,
                Items.TWISTING_VINES,
                Items.GLOW_LICHEN
        );

        getOrCreateTagBuilder(ModItemTags.ELYTRON).add(
                Items.ELYTRA,
                ModItems.WHITE_BUTTERFLY_ELYTRA,
                ModItems.LIGHT_GRAY_BUTTERFLY_ELYTRA,
                ModItems.GRAY_BUTTERFLY_ELYTRA,
                ModItems.BLACK_BUTTERFLY_ELYTRA,
                ModItems.BROWN_BUTTERFLY_ELYTRA,
                ModItems.RED_BUTTERFLY_ELYTRA,
                ModItems.ORANGE_BUTTERFLY_ELYTRA,
                ModItems.YELLOW_BUTTERFLY_ELYTRA,
                ModItems.LIME_BUTTERFLY_ELYTRA,
                ModItems.GREEN_BUTTERFLY_ELYTRA,
                ModItems.CYAN_BUTTERFLY_ELYTRA,
                ModItems.LIGHT_BLUE_BUTTERFLY_ELYTRA,
                ModItems.BLUE_BUTTERFLY_ELYTRA,
                ModItems.PURPLE_BUTTERFLY_ELYTRA,
                ModItems.MAGENTA_BUTTERFLY_ELYTRA,
                ModItems.PINK_BUTTERFLY_ELYTRA,
                ModItems.CRIMSON_BUTTERFLY_ELYTRA,
                ModItems.WARPED_BUTTERFLY_ELYTRA,
                ModItems.SOUL_BUTTERFLY_ELYTRA,
                ModItems.AMETHYST_GEMSTONE_ELYTRA,
                ModItems.DIAMOND_GEMSTONE_ELYTRA,
                ModItems.EMERALD_GEMSTONE_ELYTRA,
                ModItems.QUARTZ_GEMSTONE_ELYTRA
        );

        getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS).add(
                ModItems.SILVER_COIN,
                ModItems.GOLDEN_COIN,
                ModItems.SILVER_INGOT,
                ModItems.ONYX
        );

        getOrCreateTagBuilder(ModItemTags.SEEDS).add(
          ModItems.COTTON_SEEDS,
          ModItems.GLOWLILY_SEEDLING,
          ModItems.HOPS_SEEDS,
          ModItems.KIWI_SEEDS,
          ModItems.THORNBLOOM_SEEDS,
          Items.BEETROOT_SEEDS,
          Items.MELON_SEEDS,
          Items.PUMPKIN_SEEDS,
          Items.WHEAT_SEEDS,
          Items.TORCHFLOWER_SEEDS,
          Items.PITCHER_POD
        );

        getOrCreateTagBuilder(ModItemTags.DYES).add(
                Items.WHITE_DYE,
                Items.LIGHT_GRAY_DYE,
                Items.GRAY_DYE,
                Items.BLACK_DYE,
                Items.BROWN_DYE,
                Items.RED_DYE,
                Items.ORANGE_DYE,
                Items.YELLOW_DYE,
                Items.LIME_DYE,
                Items.GREEN_DYE,
                Items.CYAN_DYE,
                Items.LIGHT_BLUE_DYE,
                Items.BLUE_DYE,
                Items.PURPLE_DYE,
                Items.MAGENTA_DYE,
                Items.PINK_DYE
        );

        getOrCreateTagBuilder(ModItemTags.STRIPPABLE_LOGS).add(
                Items.ACACIA_LOG,
                Items.BIRCH_LOG,
                Items.CRIMSON_STEM,
                Items.DARK_OAK_LOG,
                Items.JUNGLE_LOG,
                Items.MANGROVE_LOG,
                Items.OAK_LOG,
                Items.SPRUCE_LOG,
                Items.WARPED_STEM,
                Items.CHERRY_LOG,
                Items.ACACIA_WOOD,
                Items.BIRCH_WOOD,
                Items.CRIMSON_HYPHAE,
                Items.DARK_OAK_WOOD,
                Items.JUNGLE_WOOD,
                Items.MANGROVE_WOOD,
                Items.OAK_WOOD,
                Items.SPRUCE_WOOD,
                Items.WARPED_HYPHAE,
                Items.CHERRY_WOOD,
                Items.BAMBOO_BLOCK
        );

        /** Common **/

        getOrCreateTagBuilder(ModItemTags.C_COPPER_NUGGETS).add(
                ModItems.COPPER_NUGGET
        );

        getOrCreateTagBuilder(ModItemTags.C_ORES).add(
                ModBlocks.SILVER_ORE.asItem(),
                ModBlocks.DEEPSLATE_SILVER_ORE.asItem(),
                ModBlocks.BASALT_SULPHUR_ORE.asItem(),
                ModBlocks.BLACKSTONE_SULPHUR_ORE.asItem(),
                ModBlocks.ONYX_ORE.asItem()
        );

        getOrCreateTagBuilder(ModItemTags.C_RAW_SILVER_BLOCKS).add(ModBlocks.RAW_SILVER_BLOCK.asItem());
        getOrCreateTagBuilder(ModItemTags.C_RAW_SILVER_ORES).add(ModBlocks.SILVER_ORE.asItem());
        getOrCreateTagBuilder(ModItemTags.C_RAW_SILVERS).add(ModItems.RAW_SILVER);
        getOrCreateTagBuilder(ModItemTags.C_SILVER_BLOCKS).add(ModBlocks.SILVER_BLOCK.asItem());
        getOrCreateTagBuilder(ModItemTags.C_SILVER_INGOTS).add(ModItems.SILVER_INGOT.asItem());
        getOrCreateTagBuilder(ModItemTags.C_SILVER_NUGGETS).add(ModItems.SILVER_NUGGET.asItem());


        /** Trinkets **/

        getOrCreateTagBuilder(ModItemTags.TRINKETS_HAT).add(
                ModItems.BEACH_HAT,
                ModItems.TOP_HAT,
                ModItems.BERET,
                ModItems.COWBOY_HAT,
                ModItems.BUTTERFLY_WINGS,
                ModItems.CROWN,
                ModItems.CAP,
                ModItems.PROPELLER_CAP,
                ModItems.TIARA,
                ModItems.SILVER_TIARA,
                ModItems.VIKING_HELMET
                ).addTag(ModItemTags.PLUSHIES);

        getOrCreateTagBuilder(ModItemTags.TRINKETS_CAPE).addTag(ModItemTags.ELYTRON);
    }
}
