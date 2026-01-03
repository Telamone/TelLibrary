package it.telami.commons.data_structure.cache;

import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.data_structure.DataStructure;

import java.util.concurrent.TimeUnit;

/**
 * Handle dynamic key-value associations through a fast, efficient
 * and GC friendly cache.
 * <p>
 * Pros: <ul>
 * <li>support null values;</li>
 * <li><b>try</b> to ensure a value from being collected by the
 * GC for a specified amount of time or permanently;</li>
 * <li>if the GC decides that the memory MUST be freed, then it's free
 * to manipulate this cache;</li>
 * <li>can invalidate manually values;</li>
 * <li>very fast, as a very high amount of effort and engineering has
 * been put in the process of making this cache;</li>
 * <li>strictly defined through 4 interfaces: {@link CacheLoader},
 * {@link CacheStateExtractor}, {@link CacheReConstructor} and
 * {@link CacheRemovalHandler} (the only one that is <b>not</b>
 * optional is the loader).</li>
 * </ul>
 * </p>
 * <p>
 * Cons: <ul>
 * <li>introduction of the concept of <b>state</b>, that may confuse
 * a little initially (briefly explained in {@link CacheStateExtractor});</li>
 * <li>bad use of the resources (not following what the documentation
 * suggests) can lead to memory leaks (as for every other cache, but
 * it's worth specifying).</li>
 * </ul>
 * </p>
 * When handling values' time, remember this <b>golden time rule</b>:
 * "A temporary value may become permanent, but a permanent value can't
 * become temporary unless {@link Cache#forceTime(Object, long, TimeUnit) forceTime(...)}
 * is used".
 * @param <K> key's type
 * @param <V> value's type
 * @param <V_state> value's state's type
 * @author Telami
 * @since 1.0.0
 */
public final class Cache<K, V, V_state> implements DataStructure, AutoCloseable {
    /**
     * Create a new {@link Cache}. <br>
     * @param initialCapacity the initial table capacity
     * @param loadFactor the initial table density
     * @param concurrencyLevel the estimated number of concurrency updating threads
     * @param loader see {@link CacheLoader}
     * @param stateExtractor see {@link CacheStateExtractor}
     * @param reConstructor see {@link CacheReConstructor}
     * @param removalHandler see {@link CacheRemovalHandler}
     * @throws IllegalArgumentException if one of the first 3 arguments is 0 (except
     *                                  for 'initialCapacity') or negative
     * @author Telami
     * @since 1.0.0
     */
    public Cache (final int initialCapacity,
                  final float loadFactor,
                  final int concurrencyLevel,
                  final ContentionHandler handler,
                  final CacheLoader<K, V> loader,
                  final CacheStateExtractor<V, V_state> stateExtractor,
                  final CacheReConstructor<V, V_state> reConstructor,
                  final CacheRemovalHandler<K, V_state> removalHandler) {
        //Hidden implementation...
    }

    public boolean isThreadSafe () {
        return true;
    }

    /**
     * Load <b>pseudo-permanently</b> into the cache the value
     * generated through the {@link CacheLoader loader} by passing
     * the given key as an argument. <br>
     * The 'pseudo' (that means 'not totally') is specified because,
     * as the {@link Cache} states, the GC is free to manage this
     * cache in case of necessity, so it can decide to free a value
     * that was marked in precedence as permanent.
     * @param key the given key
     * @return the result of the {@link CacheLoader loader}, or the
     *         already existing value
     * @author Telami
     * @since 1.0.0
     */
    public V load (final K key) {
        //Hidden implementation...
        return null;
    }
    /**
     * Load into the cache the value generated through the {@link CacheLoader loader}
     * by passing the given key as an argument, <b>trying</b> to ensure that it
     * remains loaded for the given time per unit. <br>
     * It will only 'try' to ensure because, as the {@link Cache} states, the GC is
     * free to manage this cache in case of necessity, so it can decide to free a
     * value that has not already reached its defined time-to-live or maintain it
     * if there is no clue in removing it.
     * @param key the given key
     * @param time the given time
     * @param unit the given unit
     * @return the result of the {@link CacheLoader loader}, or the already existing value
     * @author Telami
     * @since 1.0.0
     */
    public V load (final K key,
                   final long time,
                   final TimeUnit unit) {
        //Hidden implementation...
        return null;
    }

    /**
     * Check if the given key is present in the {@link Cache cache}. <br>
     * This doesn't affect the removal timer, if present.
     * @param key the given key
     * @return {@code true} if the given key is present in the {@link Cache cache}, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean contains (final K key) {
        //Hidden implementation...
        return false;
    }

    /**
     * As the <b>golden time rule</b> states in {@link Cache}, this method
     * can force a value marked as permanent to become temporary if the
     * value is actually marked as permanent, otherwise it will try to
     * simply update value's time.
     * @param key the given key
     * @param time the given time
     * @param unit the given unit
     * @return {@code true} if value's time has been changed, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean forceTime (final K key,
                              final long time,
                              final TimeUnit unit) {
        //Hidden implementation...
        return false;
    }

    /**
     * Invalidate the given key's corresponding value.
     * @param key the given key
     * @author Telami
     * @since 1.0.0
     */
    public void invalidate (final K key) {
        //Hidden implementation...
    }

    /**
     * Clears this {@link Cache cache}.
     * @author Telami
     * @since 1.0.0
     */
    public void clear () {
        //Hidden implementation...
    }

    /**
     * Check if this {@link Cache cache} is still currently open.
     * @return {@code true} if the {@link Cache cache} is currently open, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean isOpen () {
        //Hidden implementation...
        return false;
    }

    public void close () {
        //Hidden implementation...
    }
}
