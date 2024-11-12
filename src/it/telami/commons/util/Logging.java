package it.telami.commons.util;

import java.util.logging.Logger;

public final class Logging {
    private Logging () {}

    public static final Logger commonLogger = newAlignedLogger("TelLib");

    public static Logger newAlignedLogger (final String name/*, final boolean useMinecraftColorDecoder*/) {
        return null;
    }
}
