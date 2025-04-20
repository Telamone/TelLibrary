package it.telami.commons.data_structure.cache;

/**
 * Used by {@link Cache} for obtaining key's corresponding value. <br>
 * It's recommended to <b>NOT</b> maintain value's references outside the cache
 * unless it's in use!
 * @param <K> key's type
 * @param <V> value's type
 * @author Telami
 * @since 1.0.0
 */
@FunctionalInterface
public interface CacheLoader<K, V> {
    /**
     * Load the <b>nullable</b> value, generated through the given key,
     * as defined by {@link CacheLoader}.
     * @param key the given key
     * @return a <b>non-null</b> value
     * @author Telami
     * @since 1.0.0
     */
    V load (final K key);
}
