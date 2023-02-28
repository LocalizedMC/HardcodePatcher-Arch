package org.localmc.tools.hardcodepatcher.fabric;

import org.localmc.tools.hardcodepatcher.HardcodePatcher;
import net.fabricmc.api.ModInitializer;

public class HardcodePatcherFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HardcodePatcher.init();
    }
}
