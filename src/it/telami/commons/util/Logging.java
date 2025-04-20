package it.telami.commons.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used for offering additional logging utilities. <br>
 * Such utilities include an auto-alignment for maintaining the logs aligned,
 * with every {@link Level logging level} and every new line escape, and a
 * special decoding for minecraft color codes in consoles supporting ANSI.
 * @author Telami
 * @since 1.0.0
 */
public final class Logging {
    private Logging () {}

    public static final Logger commonLogger = newAlignedLogger("TelLib", false);
    public static final Logger minecraftLogger = newAlignedLogger("TelLib", true);

    /**
     * Return a new aligned logger basing on the given name and the given
     * minecraft color codes decoding choice.
     * @param name the given new logger's name
     * @param useMinecraftColorDecoder chose if decode minecraft color codes
     * @return a new aligned logger with the given capabilities
     * @see Logging
     * @author Telami
     * @since 1.0.0
     */
    public static Logger newAlignedLogger (final String name, final boolean useMinecraftColorDecoder) {
        return null;
    }
}
