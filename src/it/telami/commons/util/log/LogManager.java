package it.telami.commons.util.log;

import it.telami.commons.open_unsafe.Unsafe;

/**
 * Utility Class used for associating the {@link Unsafe#getCaller() caller} class
 * to <b>a single and permanent</b> {@link LogHandler}.
 * @author Telami
 * @since 1.0.3
 */
public final class LogManager {
    private LogManager () {}

    /**
     * Try to register the {@link Unsafe#getCaller() caller} class as the given
     * {@link LogHandler}'s owner.
     * @param handler the given {@link LogHandler}
     * @return {@code true} if the associations was successful, {@code false} otherwise
     * @param <LP> the type of {@link CloseableLogProcessor processor}
     * @param <LH> the {@link LogHandler} supporting the given type of {@link CloseableLogProcessor processor}
     * @author Telami
     * @since 1.0.3
     */
    public static <LP extends CloseableLogProcessor, LH extends LogHandler<LP>> boolean registerHandler (final LH handler) {
        //Hidden implementation...
        return false;
    }

    /**
     * Return the {@link LogHandler} associated with the given registering {@link Class}.
     * @param registeringClazz the given registering {@link Class}
     * @return the associated {@link LogHandler} or {@code null} if the given registering
     *         {@link Class} has not been associated with any {@link LogHandler}
     * @param <LP> the type of {@link CloseableLogProcessor processor}
     * @param <LH> the {@link LogHandler} supporting the given type of {@link CloseableLogProcessor processor}
     * @author Telami
     * @since 1.0.3
     */
    public static <LP extends CloseableLogProcessor, LH extends LogHandler<LP>> LH getHandler (final Class<?> registeringClazz) {
        //Hidden implementation...
        return null;
    }
}