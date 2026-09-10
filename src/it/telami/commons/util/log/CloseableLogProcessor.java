package it.telami.commons.util.log;

/**
 * This class is used for initializing the {@link LogHandler}. <br>
 * Its role is to {@link CloseableLogProcessor#process(String) process}
 * the logged {@link String strings} passed to the {@link LogHandler}. <br>
 * It extends {@link AutoCloseable} for giving the possibility to
 * {@link AutoCloseable#close() close} the {@link CloseableLogProcessor processor}
 * before the JVM shutdown. <br>
 * @apiNote It's not necessary to explicitly {@link AutoCloseable#close() close}
 *          the {@link CloseableLogProcessor processor} since the {@link LogHandler}
 *          already register a {@link Runtime#addShutdownHook(Thread) shutdown hook}
 * @author Telami
 * @since 1.0.3
 */
public interface CloseableLogProcessor extends AutoCloseable {
    /**
     * Return whether this {@link CloseableLogProcessor processor} is closed or not.
     * @return {@code true} if this {@link CloseableLogProcessor processor} has been
     *         {@link AutoCloseable#close() closed}, {@code false} otherwise
     * @author Telami
     * @since 1.0.3
     */
    boolean isClosed ();
    /**
     * Process the given logged {@link String} passed by the {@link LogHandler}.
     * @param s the given logged {@link String}
     * @author Telami
     * @since 1.0.3
     */
    void process (final String s);
}
