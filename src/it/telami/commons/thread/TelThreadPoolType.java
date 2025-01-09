package it.telami.commons.thread;

/**
 * This library's {@link ThreadPoolType} implementation. <p>
 * See the values' documentation for gaining information about
 * the {@link ThreadPool ThreadPools}' behavior.
 * @author Dr4aKy
 * @since 1.0.0
 */
public enum TelThreadPoolType implements ThreadPoolType {
    ;

    private final boolean parallel;
    TelThreadPoolType (final boolean parallel) {
        this.parallel = parallel;
    }

    public boolean isParallel () {
        return parallel;
    }
}
