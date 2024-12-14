package it.telami.commons.data_structure.cache;

import java.util.concurrent.ScheduledExecutorService;

public interface DataHandler<K> extends AutoCloseable {
    ScheduledExecutorService getScheduler ();

    void push (final K key, final byte[] data);
    byte[] fetch (final K key);

    void close ();
}
