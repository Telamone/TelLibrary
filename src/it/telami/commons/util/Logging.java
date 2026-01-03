package it.telami.commons.util;

import java.util.function.Consumer;
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

    private static final boolean defaultClock = Boolean.getBoolean("efine_IsLoggingClockActiveByDefault");
    public static final Logger commonLogger = newAlignedLogger("TelLib", defaultClock, false, _ -> {});
    public static final Logger minecraftLogger = newAlignedLogger("TelLib", defaultClock, true, _ -> {});

    /**
     * Return a new aligned {@link Logger logger} basing on the given name and the given clock and
     * minecraft color codes decoding choice. <br>
     * The formatted results produced by this logger will be consumed by the given 'resultConsumer'.
     * @param name the given new logger's name
     * @param addClock chose if add clock
     * @param useMinecraftColorDecoder chose if decode minecraft color codes
     * @param resultConsumer a <b>non-null</b> {@link Consumer consumer} that takes as input the formatted results
     * @apiNote Use {@code _ -> {}} as 'resultConsumer' if it's not desired to consume the formatted result
     * @return a new aligned logger with the given capabilities
     * @see Logging
     * @author Telami
     * @since 1.0.1
     */
    public static Logger newAlignedLogger (final String name,
                                           final boolean addClock,
                                           final boolean useMinecraftColorDecoder,
                                           final Consumer<String> resultConsumer) {
        //Hidden implementation...
        return null;
    }
}
