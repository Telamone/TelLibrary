package it.telami.commons.concurrency.atomic;

import java.io.Serializable;

/**
 * Extremely simple 96-bit integer representation.
 * @author Telami
 * @since 1.0.3
 */
public final class I96 implements Serializable {
    public int high;
    public long low;

    public I96 () {}
    public I96 (final int high,
                final long low) {
        this.high = high;
        this.low = low;
    }
}
