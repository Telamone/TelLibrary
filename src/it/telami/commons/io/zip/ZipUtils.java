package it.telami.commons.io.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public final class ZipUtils {
    private ZipUtils () {}

    public static void eraseThis () throws IOException, URISyntaxException {}

    public static InputStream extractResourceFrom (
            final Class<?> anyOfJarClasses,
            final String resourcePath) {
        return null;
    }

    public static void extractResourceAndCopyTo (
            final Class<?> anyOfJarClasses,
            final String resourcePath,
            final File destinationFile,
            final boolean updateIfExist) {}

    public static File getExternalJarDirectory (final String name) {
        return null;
    }
}
