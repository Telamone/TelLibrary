package it.telami.commons.time;

import com.moandjiezana.toml.Toml;
import org.bukkit.configuration.Configuration;

/**
 * Class used for formatting time into string in a coherent way: excluding
 * unused units, using correctly the singular and plural, specifying the
 * milliseconds only when needed and so on. <br>
 * For example, instead of:
 * <pre>
 * {@code 1 days | 2 hours | 0 minutes | 1.1 seconds}
 * </pre>
 * it will return:
 * <pre>
 * {@code 1 day | 2 hours | 1 second}
 * </pre>
 * @author Telami
 * @since 1.0.0
 */
public final class TimeFormatter {
    /**
     * Create a {@link TimeFormatter time formatter} using the default time representations: <br>
     * second -> "second" <br>
     * seconds -> "seconds" <br>
     * minute -> "minute" <br>
     * minutes -> "minutes" <br>
     * hour -> "hour" <br>
     * hours -> "hours" <br>
     * day -> "day" <br>
     * days -> "days" <br>
     * fromNumberSeparator -> " " <br>
     * fromNextSeparator -> " | "
     * @author Telami
     * @since 1.0.0
     */
    public TimeFormatter () {}
    /**
     * Create a {@link TimeFormatter time formatter} using the given time representations.
     * @param second the given second representation
     * @param seconds the given seconds representation
     * @param minute the given minute representation
     * @param minutes the given minutes representation
     * @param hour the given hour representation
     * @param hours the given hours representation
     * @param day the given day representation
     * @param days the given days representation
     * @param fromNumberSeparator the given fromNumberSeparator representation
     * @param fromNextSeparator the given fromNextSeparator representation
     * @author Telami
     * @since 1.0.0
     */
    public TimeFormatter (
            final String second,
            final String seconds,
            final String minute,
            final String minutes,
            final String hour,
            final String hours,
            final String day,
            final String days,
            final String fromNumberSeparator,
            final String fromNextSeparator) {}

    /**
     * Create a {@link TimeFormatter time formatter} from a given {@link Configuration configuration},
     * eventually using the default values if the given path derivatives are not found as specified in
     * {@link TimeFormatter#TimeFormatter() TimeFormatter}.
     * @param config the given configuration
     * @param path the given path
     * @author Telami
     * @since 1.0.0
     */
    //Bukkit
    public TimeFormatter (final Configuration config, final String path) {}
    /**
     * Create a {@link TimeFormatter time formatter} from a given {@link Toml configuration},
     * eventually using the default values if the given path derivatives are not found as specified in
     * {@link TimeFormatter#TimeFormatter() TimeFormatter}.
     * @param config the given configuration
     * @param path the given path
     * @author Telami
     * @since 1.0.0
     */
    //Velocity
    public TimeFormatter (final Toml config, final String path) {}

    /**
     * Format the given time in a coherent way as described by {@link TimeFormatter}.
     * @param millis the given time expressed in milliseconds
     * @return a coherent time format
     * @author Telami
     * @since 1.0.0
     */
    public String format (long millis) {
        return null;
    }
}
