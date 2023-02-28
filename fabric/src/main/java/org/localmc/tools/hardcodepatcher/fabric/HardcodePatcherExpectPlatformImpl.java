package org.localmc.tools.hardcodepatcher.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class HardcodePatcherExpectPlatformImpl {
    public static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static Path getGameDir() {
        return FabricLoader.getInstance().getGameDir();
    }

    public static String getMappingName() {
        return "Intermediaty";
    }
}
