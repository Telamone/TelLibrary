package it.telami.commons.io.zip;

import java.io.File;
import java.io.InputStream;

/**
 * This class offers some useful util for handling zip
 * and derived (like jar) files.
 * @author Telami
 * @since 1.0.0
 */
public final class ZipUtils {
    private ZipUtils () {}

    /**
     * Completely erase the content of this jar, the already loaded classes
     * will still function.
     * @return {@code true} if the operation succeed, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public static boolean eraseThis () {
        return false;
    }

    /**
     * Extract a stream representing a jar's entry specifying its
     * path in the jar and including <b>/</b> at the beginning of
     * the path.
     * @param anyOfJarClasses any of the jar's classes
     * @param resourcePath the path to the resource
     * @return an {@link InputStream} representing the resource
     * @author Telami
     * @since 1.0.0
     */
    public static InputStream extractResourceFrom (
            final Class<?> anyOfJarClasses,
            final String resourcePath) {
        return null;
    }

    /**
     * Copy a resource to a given destination from inside a jar, specifying
     * its path in the jar and including <b>/</b> at the beginning of the path.
     * @param anyOfJarClasses any of the jar's classes
     * @param resourcePath the path to the resource
     * @param destinationFile the destination
     * @param updateIfExist chose if override the already existing file
     * @author Telami
     * @since 1.0.0
     */
    public static void extractResourceAndCopyTo (
            final Class<?> anyOfJarClasses,
            final String resourcePath,
            final File destinationFile,
            final boolean updateIfExist) {}

    /**
     * Get a file outside the library jar, given its additional path.
     * @param path the path to the new file
     * @return the file
     * @author Telami
     * @since 1.0.0
     */
    public static File getExternalJarFile (final String path) {
        return null;
    }
}
