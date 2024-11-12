package it.telami.commons.thread;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * This library's {@link ThreadPoolType} implementation. <p>
 * See the values' documentation for gaining information about
 * the {@link ThreadPool ThreadPools}' behavior.
 * @author Dr4aKy
 * @since 1.0.0
 */
public enum TelThreadPoolType implements ThreadPoolType {
    /**
     * Behaves as described by the {@link TimedThreadPool}'s documentation,
     * but <b>does not support</b> {@link TimedThreadPool#schedule(Consumer, long, TimeUnit)  schedule}
     * (if used, will throw an {@link UnsupportedOperationException}),
     * incrementing the performances of {@link TimedThreadPool#scheduleLast(Consumer, long, TimeUnit)  scheduleLast}.
     * @since 1.0.0
     */
    SEQUENTIAL(true);

    private final boolean parallel;
    TelThreadPoolType (final boolean parallel) {
        this.parallel = parallel;
    }

    public boolean isParallel () {
        return parallel;
    }
}
