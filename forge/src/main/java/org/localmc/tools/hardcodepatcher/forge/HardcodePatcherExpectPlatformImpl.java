package org.localmc.tools.hardcodepatcher.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class HardcodePatcherExpectPlatformImpl {
    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static String getMappingName() {
        return "SRG";
    }
}
