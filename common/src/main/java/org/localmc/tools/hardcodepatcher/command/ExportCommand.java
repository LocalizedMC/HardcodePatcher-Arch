package org.localmc.tools.hardcodepatcher.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;
import org.localmc.tools.hardcodepatcher.HardcodePatcherExpectPlatform;
import org.localmc.tools.hardcodepatcher.HardcodePatcherUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportCommand implements Command<CommandSourceStack> {
    public static ExportCommand instance = new ExportCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(new TranslatableComponent("commands.hardcodepatcher.export.warning.wip"), true);
        Gson gson = new Gson();
        String json = gson.toJson(HardcodePatcherUtils.exportList, new TypeToken<ArrayList<String>>() {
        }.getType());
        // Export
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                            HardcodePatcherExpectPlatform.getGameDir().resolve("langpacther.json").toFile()
                    ));
            bw.write(json);
            bw.flush();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.getSource().sendSuccess(new TranslatableComponent("commands.hardcodepatcher.export.tips.success"), true);
        return 0;
    }
}
