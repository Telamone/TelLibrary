package it.telami.commons.concurrency.thread;

import java.lang.invoke.VarHandle;

//This class should not use strange synchronization technics since ALL is done locally in the same thread!
/**
 * This class provides {@link Thread current thread}'s internal variable <b>'secondary
 * seed'</b> handling, offering many function like efficient spin-yielding, parking
 * and so on. <br>
 * The main function are {@link ThreadSecondarySeedHandler#getSecondarySeed()} and
 * {@link ThreadSecondarySeedHandler#setSecondarySeed(int)} for manipulating it
 * personally. <br>
 * Always observe the effects that this manipulation might have on the program!
 * @author Telami
 * @since 1.0.0
 */
public final class ThreadSecondarySeedHandler {
    private ThreadSecondarySeedHandler () {}

    /**
     * Returns the current secondary seed with the memory effects
     * specified by {@link VarHandle#get(Object...) get}.
     * @return the current secondary seed
     * @author Telami
     * @since 1.0.0
     */
    public static int getSecondarySeed () {
        return 0;
    }

    /**
     * Sets the new secondary seed with the memory effects
     * specified by {@link VarHandle#set(Object...) set}.
     * @param ss the new secondary seed
     * @author Telami
     * @since 1.0.0
     */
    public static void setSecondarySeed (final int ss) {}

    /**
     * Decides to spin or yield in a heuristic way. <br>
     * Useful when it's necessary to balance {@link Thread#yield()}
     * and {@link Thread#onSpinWait()}.
     * @author Telami
     * @since 1.0.0
     */
    public static void spinOrYieldHeuristically () {}

    /**
     * Parks trying to achieve the best wait-time, basing on the max
     * threshold and the last total wait-time from the first call. <br>
     * Example:
     * <pre> {@code
     * for (final long start = System.nanoTime(); condition;)
     *     ThreadSecondarySeedHandler.parkSpeculatively(
     *             1_000, //1 microsecond...
     *             (int) (System.nanoTime() - start));
     * } </pre>
     * @param maxTimeThreshold the max wait-time in nanoseconds
     * @param lastElapsedTime the last total wait-time in nanoseconds
     * @author Telami
     * @since 1.0.0
     */
    public static void parkSpeculatively (final int maxTimeThreshold,
                                          int lastElapsedTime) {}
}
