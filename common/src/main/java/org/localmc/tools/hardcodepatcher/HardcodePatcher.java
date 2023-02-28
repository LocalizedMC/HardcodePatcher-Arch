package org.localmc.tools.hardcodepatcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.localmc.tools.hardcodepatcher.config.HardcodePatcherPatch;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class HardcodePatcher {
    public static final String MODID = "hardcodepatcher";
    public static final String MODNAME = "HardcodePatcher";
    public static final String MAPPINGNAME = HardcodePatcherExpectPlatform.getMappingName();
    public static final Path CONFIGPATH = HardcodePatcherExpectPlatform.getConfigDir().resolve(MODNAME);
    public static final Logger LOGGER = LogManager.getLogger();
    public static List<HardcodePatcherPatch> hpps = new ArrayList<>();
    
    public static void init() {
        System.out.println(HardcodePatcherExpectPlatform.getConfigDir().toAbsolutePath().normalize().toString());
    }
}