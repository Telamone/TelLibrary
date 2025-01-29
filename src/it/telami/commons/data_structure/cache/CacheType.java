package it.telami.commons.data_structure.cache;

import java.io.Serializable;

public interface CacheType<K extends Serializable, V extends CacheComponent<K, V>> {
    V generate (final DataCache<K, V> cache, final K key);
}