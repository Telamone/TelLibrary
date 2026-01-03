package it.telami.commons.message;

import java.util.Set;
import java.util.function.Function;

/**
 * Class used for censoring a given set of words. <br>
 * It can both directly censor and check if the word MAY be contained in a message. <br>
 * Any use of 'word' in this class is meant to be intended as 'bad word'.
 * @author Telami
 * @since 1.0.0
 */
public final class Censorship {
    /**
     * Create an instance that, as defined by {@link Censorship}, will
     * censor or analyze the given messages basing on the given set
     * of words.
     * @param toCensor the set of words to censor
     * @author Telami
     * @since 1.0.0
     */
    public Censorship (final Set<String> toCensor) {
        //Hidden implementation...
    }

    /**
     * Censor the given message replacing the words with
     * a series of '*' characters with the exact length.
     * @param s the given message
     * @return the censored message
     * @author Telami
     * @since 1.0.0
     */
    public String censor (final String s) {
        //Hidden implementation...
        return null;
    }
    /**
     * Censor the given message replacing the words using
     * the given replacement function.
     * @param s the given message
     * @param replacement the given replacement function
     * @return the censored message
     * @author Telami
     * @since 1.0.0
     */
    public String censor (final String s, final Function<String, String> replacement) {
        //Hidden implementation...
        return null;
    }

    /**
     * It may happen that a word is not censored because it
     * was extremely modified, part of a composite word ecc... <br>
     * This method signal if the message may contain any word
     * without censoring it.
     * @param s the given message
     * @return {@code true} if the given message probably contains
     *         any word in the set, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean mayContain (final String s) {
        //Hidden implementation...
        return false;
    }
}
