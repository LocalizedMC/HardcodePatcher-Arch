package org.localmc.tools.hardcodepatcher.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherConfig;

import java.util.List;

public class ListCommand implements Command<CommandSourceStack> {
    public static ListCommand instance = new ListCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(new TranslatableComponent("commands.hardcodepatcher.list.warning.wip"), false);
        context.getSource().sendSuccess(new TranslatableComponent("commands.hardcodepatcher.list.tips.modslist"), false);
        List<String> mods = HardcodePatcherConfig.getMods();
        StringBuilder str = new StringBuilder();
        for (String mod : mods) {
            str.append("§a").append(mod).append(",");
        }
        context.getSource().sendSuccess(new TextComponent(str.toString()), false);
        return 0;
    }
}
