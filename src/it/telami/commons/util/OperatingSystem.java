package it.telami.commons.util;

/**
 * Class representing the type of operating system the JVM is running on.
 * @author Telami
 * @since 1.0.0
 */
public enum OperatingSystem {
    UNKNOWN,
    WINDOWS,
    LINUX,
    MAC;

    public static final OperatingSystem currentOS;
    public static final String currentArchitecture;
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
            case "windows" -> WINDOWS;
            case "linux" -> LINUX;
            case "macos", "mac" -> MAC;
            default -> UNKNOWN;
        };
        currentArchitecture = System.getProperty("os.arch");
        currentVersion = System.getProperty("os.version");
    }
}
