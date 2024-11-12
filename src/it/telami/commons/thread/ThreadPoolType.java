package it.telami.commons.thread;

/**
 * Used for describing {@link ThreadPool}'s behavior. <p>
 * Can be implemented and used as an {@link Enum} like
 * this library's thread pool's types ({@link TelThreadPoolType}).
 * @author Dr4aKy
 * @since 1.0.0
 */
public interface ThreadPoolType {
    /**
     * Describes whether the submitted tasks are executed in parallel or not.
     * @return true if the {@link ThreadPool} supports parallel task execution, otherwise false
     * @author Dr4aKy
     * @since 1.0.0
     */
    boolean isParallel ();
}
