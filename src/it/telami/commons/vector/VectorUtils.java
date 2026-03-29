package it.telami.commons.vector;

import it.telami.commons.unsafe.Unrestricted;

/**
 * Class used for providing utilities inherent to the Vector APIs. <br>
 * Currently empty because the API is still an 'incubator' until
 * Valhalla releases.
 * @author Telami
 * @since 1.0.0
 */
public final class VectorUtils {
    private VectorUtils () {}

    public static final boolean SUPPORTED;
    static {
        Class<?> c = null;
        try {
            c = Unrestricted.findClass("jdk.incubator.vector.Vector");
        } catch (final ClassNotFoundException | IllegalAccessException _) {}
        SUPPORTED = c != null;
    }
}
