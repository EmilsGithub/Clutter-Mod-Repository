package net.emilsg.clutter.util;

import net.emilsg.clutter.block.ModBlocks;
import net.emilsg.clutter.command.ResetConfigCommand;
import net.emilsg.clutter.command.UpdateConfigsCommand;
import net.emilsg.clutter.config.ClutterConfig;
import net.emilsg.clutter.item.ModItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;


public class ModUtil {
    public static void registerModUtil() {
        registerFlammableBlocks();
        registerStrippableBlocks();
        registerCompostableItems();
        registerFuel();
        registerCommands();
        registerJoinServerEvents();
    }

    private static void registerJoinServerEvents() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ServerPlayConnectionEvents.JOIN.register((handler, sender, server1) -> {
                ServerPlayerEntity player = handler.player;
                if (server1.getPlayerManager().isOperator(player.getGameProfile())) {
                    boolean isCurrent = ClutterConfig.getInstance().isConfigVersionCurrent();
                    if (!isCurrent) {

                        MutableText operatorOnlyText = Text.translatable("clutter.commands.config.op_only").formatted(Formatting.RED);

                        MutableText startText = Text.translatable("clutter.commands.config.config_update_prompt", ClutterConfig.getInstance().getFileVersionNumber(ClutterConfig.getInstance().getCurrentFileVersion()));

                        MutableText versionText = Text.literal("(" + ClutterConfig.FILE_VERSION + ")");

                        MutableText updatePromptText = Texts.bracketed(
                                Text.translatable("clutter.commands.config.config_update")).styled(style -> style.withColor(Formatting.GREEN)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/clutter update_configs"))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("clutter.commands.config.update_tooltip"))));

                        player.sendMessage(Text.translatable("clutter.commands.config.config_prompt", operatorOnlyText, startText, versionText, updatePromptText), false);
                    }
                }
            });
        });
    }

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(ResetConfigCommand::register);
        CommandRegistrationCallback.EVENT.register(UpdateConfigsCommand::register);
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(ModBlockTags.FLAMMABLE, 60, 20);
    }

    public static void registerStrippableBlocks() {
        StrippableBlockRegistry.register(ModBlocks.REDWOOD_LOG, ModBlocks.STRIPPED_REDWOOD_LOG);
        StrippableBlockRegistry.register(ModBlocks.REDWOOD_WOOD, ModBlocks.STRIPPED_REDWOOD_WOOD);
    }

    public static void registerCompostableItems() {
        CompostingChanceRegistry registry = CompostingChanceRegistry.INSTANCE;

        registry.add(ModBlocks.LUSH_MOSS, 0.3f);
        registry.add(ModItems.HOPS_SEEDS, 0.3f);
        registry.add(ModItems.HOPS, 0.3f);
        registry.add(ModItems.COTTON_SEEDS, 0.3f);
        registry.add(ModItems.COTTON, 0.3f);
        registry.add(ModItems.KIWI_SEEDS, 0.3f);
        registry.add(ModItems.KIWI, 0.3f);
        registry.add(ModBlocks.KIWI_TREE_SAPLING, 0.3f);
        registry.add(ModItems.THORNBLOOM_SEEDS, 0.3f);
        registry.add(ModItems.THORNBLOOM_PEAR, 0.2f);
        registry.add(ModItems.BAKED_THORNBLOOM_PEAR, 0.3f);
        registry.add(ModBlocks.THORNBLOOM, 0.3f);
        registry.add(ModBlocks.KIWI_LEAVES, 0.2f);
        registry.add(ModItems.CHERRIES, 0.2f);
    }

    public static void registerFuel() {
        FuelRegistry registry = FuelRegistry.INSTANCE;

        registry.add(ModBlocks.SULPHUR_BLOCK, 400);

        registry.add(ModItems.SULPHUR, 100);
        registry.add(ModItems.WOODEN_MUG, 100);

        registry.add(ModBlocks.OAK_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.OAK_WINDOW_SILL, 100);
        registry.add(ModBlocks.SPRUCE_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.SPRUCE_WINDOW_SILL, 100);
        registry.add(ModBlocks.BIRCH_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.BIRCH_WINDOW_SILL, 100);
        registry.add(ModBlocks.JUNGLE_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.JUNGLE_WINDOW_SILL, 100);
        registry.add(ModBlocks.ACACIA_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.ACACIA_WINDOW_SILL, 100);
        registry.add(ModBlocks.DARK_OAK_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.DARK_OAK_WINDOW_SILL, 100);
        registry.add(ModBlocks.MANGROVE_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.MANGROVE_WINDOW_SILL, 100);
        registry.add(ModBlocks.BAMBOO_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.BAMBOO_WINDOW_SILL, 100);
        registry.add(ModBlocks.CHERRY_WALL_BOOKSHELF, 100);
        registry.add(ModBlocks.CHERRY_WINDOW_SILL, 100);

        registry.add(ModBlocks.FOOD_BOX, 100);

        registry.add(ModBlocks.OAK_TABLE, 100);
        registry.add(ModBlocks.SPRUCE_TABLE, 100);
        registry.add(ModBlocks.BIRCH_TABLE, 100);
        registry.add(ModBlocks.JUNGLE_TABLE, 100);
        registry.add(ModBlocks.ACACIA_TABLE, 100);
        registry.add(ModBlocks.DARK_OAK_TABLE, 100);
        registry.add(ModBlocks.MANGROVE_TABLE, 100);
        registry.add(ModBlocks.BAMBOO_TABLE, 100);
        registry.add(ModBlocks.CHERRY_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_OAK_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_SPRUCE_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_BIRCH_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_JUNGLE_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_ACACIA_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_DARK_OAK_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_MANGROVE_TABLE, 100);
        registry.add(ModBlocks.STRIPPED_CHERRY_TABLE, 100);

        registry.add(ModBlocks.OAK_CHAIR, 100);
        registry.add(ModBlocks.SPRUCE_CHAIR, 100);
        registry.add(ModBlocks.BIRCH_CHAIR, 100);
        registry.add(ModBlocks.JUNGLE_CHAIR, 100);
        registry.add(ModBlocks.ACACIA_CHAIR, 100);
        registry.add(ModBlocks.DARK_OAK_CHAIR, 100);
        registry.add(ModBlocks.MANGROVE_CHAIR, 100);
        registry.add(ModBlocks.BAMBOO_CHAIR, 100);
        registry.add(ModBlocks.CHERRY_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_OAK_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_SPRUCE_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_BIRCH_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_JUNGLE_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_ACACIA_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_DARK_OAK_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_MANGROVE_CHAIR, 100);
        registry.add(ModBlocks.STRIPPED_CHERRY_CHAIR, 100);

        registry.add(ModBlocks.OAK_CUPBOARD, 100);
        registry.add(ModBlocks.SPRUCE_CUPBOARD, 100);
        registry.add(ModBlocks.BIRCH_CUPBOARD, 100);
        registry.add(ModBlocks.JUNGLE_CUPBOARD, 100);
        registry.add(ModBlocks.ACACIA_CUPBOARD, 100);
        registry.add(ModBlocks.DARK_OAK_CUPBOARD, 100);
        registry.add(ModBlocks.MANGROVE_CUPBOARD, 100);
        registry.add(ModBlocks.BAMBOO_CUPBOARD, 100);
        registry.add(ModBlocks.CHERRY_CUPBOARD, 100);

        registry.add(ModBlocks.OAK_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.SPRUCE_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.BIRCH_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.JUNGLE_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.ACACIA_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.DARK_OAK_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.MANGROVE_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.BAMBOO_WALL_CUPBOARD, 100);
        registry.add(ModBlocks.CHERRY_WALL_CUPBOARD, 100);

        registry.add(ModBlocks.OAK_TRELLIS, 100);
        registry.add(ModBlocks.SPRUCE_TRELLIS, 100);
        registry.add(ModBlocks.BIRCH_TRELLIS, 100);
        registry.add(ModBlocks.JUNGLE_TRELLIS, 100);
        registry.add(ModBlocks.ACACIA_TRELLIS, 100);
        registry.add(ModBlocks.DARK_OAK_TRELLIS, 100);
        registry.add(ModBlocks.MANGROVE_TRELLIS, 100);
        registry.add(ModBlocks.BAMBOO_TRELLIS, 100);
        registry.add(ModBlocks.CHERRY_TRELLIS, 100);

        registry.add(ModBlocks.OAK_BENCH, 100);
        registry.add(ModBlocks.SPRUCE_BENCH, 100);
        registry.add(ModBlocks.BIRCH_BENCH, 100);
        registry.add(ModBlocks.JUNGLE_BENCH, 100);
        registry.add(ModBlocks.ACACIA_BENCH, 100);
        registry.add(ModBlocks.DARK_OAK_BENCH, 100);
        registry.add(ModBlocks.MANGROVE_BENCH, 100);
        registry.add(ModBlocks.BAMBOO_BENCH, 100);
        registry.add(ModBlocks.CHERRY_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_OAK_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_SPRUCE_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_BIRCH_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_JUNGLE_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_ACACIA_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_DARK_OAK_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_MANGROVE_BENCH, 100);
        registry.add(ModBlocks.STRIPPED_CHERRY_BENCH, 100);

        registry.add(ModBlocks.OAK_MOSAIC, 300);
        registry.add(ModBlocks.SPRUCE_MOSAIC, 300);
        registry.add(ModBlocks.BIRCH_MOSAIC, 300);
        registry.add(ModBlocks.JUNGLE_MOSAIC, 300);
        registry.add(ModBlocks.ACACIA_MOSAIC, 300);
        registry.add(ModBlocks.DARK_OAK_MOSAIC, 300);
        registry.add(ModBlocks.MANGROVE_MOSAIC, 300);
        registry.add(ModBlocks.CHERRY_MOSAIC, 300);

        registry.add(ModBlocks.OAK_MOSAIC_SLAB, 100);
        registry.add(ModBlocks.SPRUCE_MOSAIC_SLAB, 100);
        registry.add(ModBlocks.BIRCH_MOSAIC_SLAB, 100);
        registry.add(ModBlocks.JUNGLE_MOSAIC_SLAB, 100);
        registry.add(ModBlocks.ACACIA_MOSAIC_SLAB, 100);
        registry.add(ModBlocks.DARK_OAK_MOSAIC_SLAB, 100);
        registry.add(ModBlocks.MANGROVE_MOSAIC_SLAB, 100);
        registry.add(ModBlocks.CHERRY_MOSAIC_SLAB, 100);

        registry.add(ModBlocks.OAK_MOSAIC_STAIRS, 100);
        registry.add(ModBlocks.SPRUCE_MOSAIC_STAIRS, 100);
        registry.add(ModBlocks.BIRCH_MOSAIC_STAIRS, 100);
        registry.add(ModBlocks.JUNGLE_MOSAIC_STAIRS, 100);
        registry.add(ModBlocks.ACACIA_MOSAIC_STAIRS, 100);
        registry.add(ModBlocks.DARK_OAK_MOSAIC_STAIRS, 100);
        registry.add(ModBlocks.MANGROVE_MOSAIC_STAIRS, 100);
        registry.add(ModBlocks.CHERRY_MOSAIC_STAIRS, 100);
    }
}
