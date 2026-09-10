package it.telami.commons.concurrency.thread;

/**
 * A {@link FunctionalInterface} that is used by {@link Thread threads}
 * living in Thread-pools, like in {@link QuickTaskPool}, for handling
 * {@link Throwable throwables} that were not caught inside their given
 * action. <br>
 * {@link Thread Threads} that {@link ExceptionHandler#handle(Throwable) execute}
 * this will avoid being terminated cause of an error.
 * @author Telami
 * @since 1.0.3
 */
@FunctionalInterface
public interface ExceptionHandler {
    /**
     * Handle the given uncaught {@link Throwable} for avoiding
     * {@link Thread} termination. <br>
     * Any {@link Throwable} thrown inside this method MUST be
     * ignored.
     * @param t the uncaught {@link Throwable}
     * @author Telami
     * @since 1.0.3
     */
    void handle (final Throwable t);
}
