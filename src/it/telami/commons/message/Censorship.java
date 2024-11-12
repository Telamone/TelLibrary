package it.telami.commons.message;

import java.util.Set;
import java.util.function.Function;

public final class Censorship {
    private final Set<String> toCensor;

    public Censorship (final Set<String> toCensor) {
        this.toCensor = toCensor;
    }

    public String censor (final String s) {
        return null;
    }
    public String censor (final String s, final Function<String, String> replacement) {
        return null;
    }
    private static String applyConventions (final String s) {
        return null;
    }

    public boolean mayContain (final String s) {
        return false;
    }
    private static String removeConventions (final String s) {
        return null;
    }
}
