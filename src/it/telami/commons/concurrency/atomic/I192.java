package it.telami.commons.concurrency.atomic;

import java.io.Serializable;

/**
 * Extremely simple 192-bit integer representation.
 * @author Telami
 * @since 1.0.3
 */
public final class I192 implements Serializable {
    public long i0;
    public long i64;
    public long i128;

    public I192 () {}
    public I192 (final long i0,
                 final long i64,
                 final long i128) {
        this.i0 = i0;
        this.i64 = i64;
        this.i128 = i128;
    }
}
