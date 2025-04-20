package it.telami.commons.util;

/**
 * Class representing the type of operating system the JVM is running on.
 * @author Telami
 * @since 1.0.0
 */
public enum OperatingSystem {
    //Not in CAPS for using '.getName()' in a clean way!
    Windows,
    Linux,
    Mac,
    Unknown;

    public static final OperatingSystem currentOS;
    public static final String currentVersion;
    static {
        final String osn;
        currentOS = switch ((osn = System
                .getProperty("os.name")
                .toLowerCase())
                .substring(0, Math.min(osn
                        .indexOf(' ')
                        & 0x7fffffff, osn
                        .length()))) {
            case "windows" -> Windows;
            case "linux" -> Linux;
            case "macos", "mac" -> Mac;
            default -> Unknown;
        };
        currentVersion = System.getProperty("os.version");
    }
}
