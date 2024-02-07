package net.emilsg.clutter.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.emilsg.clutter.config.ClutterConfig;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class UpdateConfigsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("clutter")
                .requires(source -> source.hasPermissionLevel(3))
                .then(CommandManager.literal("update_configs").executes(UpdateConfigsCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ClutterConfig.getInstance().addMissingConfigsAndUpdateVersion();
        MutableText operatorOnlyText = Text.translatable("clutter.commands.config.op_only").formatted(Formatting.RED);
        context.getSource().sendFeedback(() -> Text.translatable("clutter.commands.config.update_configs", operatorOnlyText).formatted(Formatting.YELLOW).formatted(Formatting.ITALIC), true);
        return 1;
    }


}
