package it.telami.commons.data_structure.cache;

import it.telami.commons.data_structure.DataStructure;
import it.telami.commons.util.Logging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class DataCache<K extends Serializable, V extends CacheComponent<K, V>> implements DataStructure {

    public DataCache (final DataHandler<K> dataHandler, final CacheType<K, V> type) {}

    public boolean isThreadSafe () {
        return true;
    }

    public V timedLoad (final K key, final long time, final TimeUnit unit) {
        return null;
    }

    public V permanentLoad (final K key) {
        return null;
    }
}
