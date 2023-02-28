package org.localmc.tools.hardcodepatcher.forge;

import me.shedaniel.architectury.platform.forge.EventBuses;
import org.localmc.tools.hardcodepatcher.HardcodePatcher;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HardcodePatcher.MODID)
public class HardcodePatcherPatcherForge {
    public HardcodePatcherPatcherForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(HardcodePatcher.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        HardcodePatcher.init();
    }
}
