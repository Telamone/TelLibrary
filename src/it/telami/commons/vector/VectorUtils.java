package it.telami.commons.vector;

public final class VectorUtils {
    private VectorUtils () {}

    public static final boolean SUPPORTED;
    static {
        Class<?> c = null;
        try {
            c = Class.forName("jdk.incubator.vector.Vector");
        } catch (final ClassNotFoundException _) {}
        SUPPORTED = c != null;
    }
}
