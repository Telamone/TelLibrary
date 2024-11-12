package it.telami.commons.data_structure;

/**
 * A Data Structure is a specific implementation for storing and managing
 * <i>generated</i> or <i>given</i> data. <p>
 * Every data structure has his own logic, so take care about what they
 * are and how to use them reading carefully their documentation.
 * @author Dr4aKy
 * @since 1.0.0
 * @see it.telami.commons.data_structure.queue.ConcurrentOrderedQueue ConcurrentOrderedQueue
 */
public interface DataStructure {
    /**
     * This refers to the implementation and states
     * if its methods are thread safe.
     * @return {@code true} if the implementation's methods
     *         are thread-safe, {@code false} otherwise
     * @author Dr4aKy
     * @since 1.0.0
     */
    boolean isThreadSafe ();
}
