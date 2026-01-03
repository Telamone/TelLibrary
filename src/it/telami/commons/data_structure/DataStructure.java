package it.telami.commons.data_structure;

import it.telami.commons.data_structure.cache.Cache;

/**
 * A Data Structure is a specific implementation for storing and managing
 * <i>generated</i> or <i>given</i> data. <p>
 * Every data structure has his own logic, so take care about what they
 * are and how to use them reading carefully their documentation.
 * @author Telami
 * @since 1.0.0
 * @see it.telami.commons.data_structure.queue.ConcurrentQueue ConcurrentQueue
 * @see Cache DataCache
 * @see it.telami.commons.data_structure.buffer.NetworkBuffer NetworkBuffer
 */
public interface DataStructure {
    /**
     * This refers to the implementation and states
     * if its methods are thread safe.
     * @return {@code true} if the implementation's methods
     *         are thread-safe, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    boolean isThreadSafe ();
}
