package it.telami.commons.data_structure.cache;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public abstract class CacheComponent<K extends Serializable, SELF extends CacheComponent<K, SELF>> implements Cloneable, Serializable {

    protected CacheComponent (final DataCache<K, SELF> cache, final K key) {}

    protected abstract void onFetch ();
    protected abstract void onPush ();
    protected abstract void onClone ();
    protected abstract void onFree ();

    public void scheduleRemoval (final long time, final TimeUnit unit) {}

    protected final SELF clone () {
        return null;
    }
}
