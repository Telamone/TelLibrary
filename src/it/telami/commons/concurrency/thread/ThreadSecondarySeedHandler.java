package it.telami.commons.concurrency.thread;

import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;

/**
 * This class provides {@link Thread current thread}'s internal variable <b>'secondary
 * seed'</b> handling, offering many function like efficient spin-yielding, parking
 * and so on. <br>
 * The main function are {@link ThreadSecondarySeedHandler#getSecondarySeed()} and
 * {@link ThreadSecondarySeedHandler#setSecondarySeed(int)} for manipulating it
 * manually. <br>
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
        //Hidden implementation...
        return 0;
    }

    /**
     * Sets the new secondary seed with the memory effects
     * specified by {@link VarHandle#set(Object...) set}.
     * @param ss the new secondary seed
     * @author Telami
     * @since 1.0.0
     */
    public static void setSecondarySeed (final int ss) {
        //Hidden implementation...
    }

    /**
     * Decides to spin or yield in a heuristic way. <br>
     * Useful when it's necessary to balance {@link Thread#yield()}
     * and {@link Thread#onSpinWait()}.
     * @author Telami
     * @since 1.0.0
     */
    public static void spinOrYieldHeuristically () {
        //Hidden implementation...
    }

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
     * @apiNote This method's functionality may be altered by
     *          {@link ThreadSecondarySeedHandler#spinOrYieldHeuristically()}! <br>
     *          For this reason, in an environment where they are both used in an
     *          alternated way, it's recommended to set the return value of this
     *          method as the current seed through
     *          {@link ThreadSecondarySeedHandler#setSecondarySeed(int)}.
     * @param maxTimeThreshold the max wait-time in nanoseconds
     * @param lastElapsedTime the last total wait-time in nanoseconds
     * @return the time spent parking
     * @author Telami
     * @since 1.0.0
     */
    public static int parkSpeculatively (final int maxTimeThreshold,
                                         int lastElapsedTime) {
        //Hidden implementation...
        return 0;
    }

    /**
     * Spin basing on the given {@link ContentionHandler handler}, until
     * the given {@link BooleanSupplier condition} return {@code false}. <br>
     * If side effects are required, it's recommended to use
     * {@link java.util.concurrent.atomic atomics} and their respective
     * {@link AtomicReference#getPlain() getPlain()} and
     * {@link AtomicReference#setPlain(Object) setPlain(...)}
     * methods inside and outside the given condition.
     * @param handler the given {@link ContentionHandler handler}
     * @param condition the given {@link BooleanSupplier condition}
     * @apiNote This method rely on {@link ThreadSecondarySeedHandler#spinOrYieldHeuristically()}. <br>
     *          Personal comment: <i>"<s>I don't know why, but</s> this method is built on a pattern recognized
     *          (at least in my tests) by the JIT and optimized in a special way, performing better than
     *          if the same spinning is done in-place!"</i>
     * @author Telami
     * @since 1.0.1
     */
    public static void spinUntil (final ContentionHandler handler,
                                  final BooleanSupplier condition) {
        //Hidden implementation...
    }
}
