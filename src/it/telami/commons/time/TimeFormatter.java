package it.telami.commons.time;

import com.moandjiezana.toml.Toml;
import org.bukkit.configuration.Configuration;

public final class TimeFormatter {
    private final String second;
    private final String seconds;
    private final String minute;
    private final String minutes;
    private final String hour;
    private final String hours;
    private final String day;
    private final String days;
    private final String fromNumberSeparator;
    private final String fromNextSeparator;

    //Default
    public TimeFormatter () {
        this.second = "second";
        this.seconds = "seconds";
        this.minute = "minute";
        this.minutes = "minutes";
        this.hour = "hour";
        this.hours = "hours";
        this.day = "day";
        this.days = "days";
        this.fromNumberSeparator = " ";
        this.fromNextSeparator = " | ";
    }
    //General
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
            final String fromNextSeparator) {
        this.second = second;
        this.seconds = seconds;
        this.minute = minute;
        this.minutes = minutes;
        this.hour = hour;
        this.hours = hours;
        this.day = day;
        this.days = days;
        this.fromNumberSeparator = fromNumberSeparator;
        this.fromNextSeparator = fromNextSeparator;
    }
    //Bukkit
    public TimeFormatter (final Configuration config, final String path) {
        this.second = null;
        this.seconds = null;
        this.minute = null;
        this.minutes = null;
        this.hour = null;
        this.hours = null;
        this.day = null;
        this.days = null;
        this.fromNumberSeparator = null;
        this.fromNextSeparator = null;
    }
    //Velocity
    public TimeFormatter (final Toml config, final String path) {
        this.second = null;
        this.seconds = null;
        this.minute = null;
        this.minutes = null;
        this.hour = null;
        this.hours = null;
        this.day = null;
        this.days = null;
        this.fromNumberSeparator = null;
        this.fromNextSeparator = null;
    }
    /*
     * Specific section end...
     */

    public String format (final long millis) {
        return null;
    }
}
