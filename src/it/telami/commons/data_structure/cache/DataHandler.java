package it.telami.commons.data_structure.cache;

import java.io.Serializable;

public interface DataHandler<K extends Serializable> {
    void push (final K key, final byte[] data);
    byte[] fetch (final K key);
}
