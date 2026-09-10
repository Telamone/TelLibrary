package it.telami.commons.unsafe;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/*
 * Class left here for showing how the translations are handled...
 */
final class Translations {
    private Translations () {}

    static final String requirements;
    static final String license_not_found;
    static final String fragmentation_not_supported;
    static final String license_initialization_failure;
    static final String buffer_underflow;
    static final String system_overwhelmed;
    static final String corrupted_session;
    static final String system_closed;
    static final String timed_out;
    static final String corrupted_data;
    static final String blacklisted;
    static final String server_validation_error;
    static final String session_already_active;
    static final String server_security_fail_0;
    static final String server_security_fail_1;
    static final String server_security_fail_2;
    static final String packet_desync_error;
    static final String class_not_found;
    static final String malformed_packet_header;
    static final String unexpected_error;
    static final File externalJarDirectory;

    static {
        final Map<String, String> translationMap = new HashMap<>();
        //Initialize defaults!
        translationMap.put("requirements", "Requirements not met:\n- At least Java 26\n- An intact version of this jar\n- Use of the sequent flags (writing the last 2 flags IN ORDER and AS LAST, REPLACING -jar [The jar to execute will be indicated through -DefineJar=Example.jar and if not indicated, then the TelLib standalone version will be executed]): ");
        translationMap.put("license_not_found", "You don't have an active library license!\nThe license is needed even in the free version.\nRequest it freely here:\n");
        translationMap.put("fragmentation_not_supported", "DisableFragmentation is enabled, but not found as an option!\nCheck that the JDK is used instead of the JRE, or include 'jdk.net.ExtendedSocketOptions' class in the classpath somehow.\nNote that \"not found\" doesn't necessary mean \"supported if found\"!");
        translationMap.put("license_initialization_failure", "Failed to initialize the license system! (");
        translationMap.put("buffer_underflow", "Data has been shrunk!");
        translationMap.put("system_overwhelmed", "The system is slowing down...");
        translationMap.put("corrupted_session", "Your session got corrupted! (Contact us as soon is possible)");
        translationMap.put("system_closed", "System closed!");
        translationMap.put("timed_out", "Timed out! (Check your session state)");
        translationMap.put("corrupted_data", "Some data got modified externally!");
        translationMap.put("blacklisted", "You got blacklisted since you used an invalid license! (Contact us if you think this may be an error)");
        translationMap.put("server_validation_error", "Server failed to validate your license!");
        translationMap.put("session_already_active", "You already have an active session! (Contact us if you think this may be an error)");
        translationMap.put("server_security_fail_0", "Server failed to establish a secure connection (Phase 0)!\nAborting the process...");
        translationMap.put("server_security_fail_1", "Server failed to establish a secure connection (Phase 1)!\nAborting the process...");
        translationMap.put("server_security_fail_2", "Server failed to establish a secure connection (Phase 2)!\nAborting the process...");
        translationMap.put("packet_desync_error", "Packets out of sync!\nAborting the process...");
        translationMap.put("class_not_found", ") Class not found!\nIf the relative plugin/resource is present, it may not already being loaded by the server.\nIn that case, add the jar in the 'libs' directory.");
        translationMap.put("malformed_packet_header", "Malformed packet header!");
        translationMap.put("unexpected_error", ") Unexpected error!\nYour session will be suspended! (Contact us as soon is possible)");
        try {
            //Reading translations...
            final File d;
            try {
                final String s;
                d = new File(externalJarDirectory
                        = new File((s
                        = Translations
                        .class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
                        .getPath())
                        .substring(0, s.lastIndexOf
                                (File.separatorChar)
                                + 1) + "TelLib"),
                        "ServiceTranslations.tel");
            } catch (final URISyntaxException e) {
                throw new RuntimeException("Error obtaining jar's URI! (" + e.getMessage() + ')');
            }
            if (d.getParentFile().exists() || d.getParentFile().mkdirs())
                try (final InputStream resource = Translations.class.getResourceAsStream("/ServiceTranslations.tel")) {
                    if (resource == null)
                        throw new FileNotFoundException("Resource not found: ServiceTranslations.tel");
                    if (!d.exists())
                        Files.copy(resource, d.toPath());
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            else throw new IllegalArgumentException("Destination's file directory could not be created!");
            try (final RandomAccessFile raf = new RandomAccessFile(d, "r")) {
                final long l;
                long i;
                if ((l = raf.length()) > (i = 0L)) {
                    StringBuilder bob = new StringBuilder();
                    String varName = null;
                    byte c;
                    byte j = (byte) 0;
                    do switch (j) {
                        case 0:
                            if ((c = raf.readByte()) == (byte) '=')
                                j = (byte) 1;
                            else if (c == (byte) '\n')
                                bob = new StringBuilder();
                            else bob.append((char) c);
                            break;
                        case 1:
                            if ((c = raf.readByte()) == (byte) '"') {
                                j = (byte) 2;
                                varName = bob.toString();
                                bob = new StringBuilder();
                            } else {
                                bob.append('=');
                                if (c != (byte) '=') {
                                    j = (byte) 0;
                                    bob.append((char) c);
                                }
                            }
                            break;
                        case 2:
                            if ((c = raf.readByte()) == (byte) '"')
                                j = (byte) 3;
                            else bob.append((char) c);
                            break;
                        case 3:
                            if ((c = raf.readByte()) == (byte) '\n') {
                                j = (byte) 0;
                                final String translation = bob.toString();
                                if (translationMap.computeIfPresent(
                                        varName,
                                        (_, _) -> translation) == null)
                                    System.err.print("Translation not found! (" + varName + ")\n");
                                bob = new StringBuilder();
                            } else {
                                bob.append('"');
                                if (c != (byte) '"') {
                                    j = (byte) 2;
                                    bob.append((char) c);
                                }
                            }
                            break;
                        default:
                            throw new IllegalStateException();
                    } while (++i < l);
                }
            } catch (final IOException _) {
                System.err.print("Cannot apply initialization translations! Using default English translations.\n");
            }
        } finally {
            requirements = translationMap.getOrDefault("requirements", "");
            license_not_found = translationMap.getOrDefault("license_not_found", "");
            fragmentation_not_supported = translationMap.getOrDefault("fragmentation_not_supported", "");
            license_initialization_failure = translationMap.getOrDefault("license_initialization_failure", "");
            buffer_underflow = translationMap.getOrDefault("buffer_underflow", "");
            system_overwhelmed = translationMap.getOrDefault("system_overwhelmed", "");
            corrupted_session = translationMap.getOrDefault("corrupted_session", "");
            system_closed = translationMap.getOrDefault("system_closed", "");
            timed_out = translationMap.getOrDefault("timed_out", "");
            corrupted_data = translationMap.getOrDefault("corrupted_data", "");
            blacklisted = translationMap.getOrDefault("blacklisted", "");
            server_validation_error = translationMap.getOrDefault("server_validation_error", "");
            session_already_active = translationMap.getOrDefault("session_already_active", "");
            server_security_fail_0 = translationMap.getOrDefault("server_security_fail_0", "");
            server_security_fail_1 = translationMap.getOrDefault("server_security_fail_1", "");
            server_security_fail_2 = translationMap.getOrDefault("server_security_fail_2", "");
            packet_desync_error = translationMap.getOrDefault("packet_desync_error", "");
            class_not_found = translationMap.getOrDefault("class_not_found", "");
            malformed_packet_header = translationMap.getOrDefault("malformed_packet_header", "");
            unexpected_error = translationMap.getOrDefault("unexpected_error", "");
        }
    }
}
