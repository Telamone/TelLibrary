package it.telami.commons.data_structure.cache;

import it.telami.commons.data_structure.DataStructure;

import java.io.Serializable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class DataCache<K extends Serializable, V extends CacheComponent<K, V>> implements DataStructure, AutoCloseable {

    public DataCache (final ScheduledExecutorService scheduler, final DataHandler<K> dataHandler, final CacheType<K, V> type) {}

    public boolean isThreadSafe () {
        return true;
    }

    public V timedLoad (final K key, final long time, final TimeUnit unit) {
        return null;
    }

    public V permanentLoad (final K key) {
        return null;
    }

    public void close () {}
}
