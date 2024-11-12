package it.telami.license;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class License {
    protected License (final String name) {}

    public static License getByName (final String name) {
        return null;
    }

    protected static synchronized boolean setGenerator (final Function<String, License> generator) {
        return false;
    }

    public String getName () {
        return null;
    }

    public abstract CompletableFuture<LicenseState> getState ();
}
