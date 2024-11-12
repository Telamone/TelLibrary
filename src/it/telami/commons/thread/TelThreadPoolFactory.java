package it.telami.commons.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A factory util used for creating new {@link ThreadPool} objects.
 * @author Dr4aKy
 * @since 1.0.0
 */
public final class TelThreadPoolFactory {
    //Guarantees a unique poolID until the MAX_SHORT_VALUE is reached
    private static final AtomicInteger instanceCounter = new AtomicInteger(1);

    private TelThreadPoolFactory () {}

    /**
     * Creates a new {@link ThreadPool} that behaves as described
     * by {@link TelThreadPoolType#SEQUENTIAL}. <p>
     * The implementation takes the name 'Sequential' from the tasks' behaviour;
     * that, after being submitted, must wait the execution of the precedent
     * task before being claimed and executed itself.
     * @param parallelism the thread pool's parallelism
     * @return the new thread pool
     * @author Dr4aKy
     * @since 1.0.0
     */
    public static TimedThreadPool newSequentialTimed (final short parallelism) {
        return null;
    }

    /**
     * Does the same as {@link TelThreadPoolFactory#newSequentialTimed(short)},
     * but with the possibility of specifying a factory.
     * @param factory the {@link ThreadFactory thread factory}
     * @param parallelism the thread pool's parallelism
     * @return the new thread pool
     * @see ThreadFactory
     * @author Dr4aKy
     * @since 1.0.0
     */
    public static TimedThreadPool newSequentialTimed (final ThreadFactory factory,
                                                      final short parallelism) {
        return null;
    }
}
