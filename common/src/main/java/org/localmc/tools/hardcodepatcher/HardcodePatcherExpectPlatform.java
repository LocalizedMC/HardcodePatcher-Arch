package org.localmc.tools.hardcodepatcher;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class HardcodePatcherExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDir() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Path getGameDir() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }

    @ExpectPlatform
    public static String getMappingName() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
