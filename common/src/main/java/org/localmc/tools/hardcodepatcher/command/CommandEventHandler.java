package org.localmc.tools.hardcodepatcher.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.localmc.tools.hardcodepatcher.HardcodePatcher;

public class CommandEventHandler {
    public static void registerClientCommands(CommandDispatcher<CommandSourceStack> dispatcher, Boolean dedicated) {
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(
                Commands.literal(HardcodePatcher.MODID
                ).then(
                        Commands.literal("export")
                                .executes(ExportCommand.instance)
                ).then(
                        Commands.literal("list")
                                .executes(ListCommand.instance)
                ).then(
                        Commands.literal("reload")
                                .executes(ReloadCommand.instance)
                ));
        dispatcher.register(Commands.literal("hp").redirect(cmd));
    }
}
