package it.telami.commons.concurrency.atomic;

import java.io.Serializable;

/**
 * Extremely simple 256-bit integer representation.
 * @author Telami
 * @since 1.0.3
 */
public final class I256 implements Serializable {
    public long i0;
    public long i64;
    public long i128;
    public long i192;

    public I256 () {}
    public I256 (final long i0,
                 final long i64,
                 final long i128,
                 final long i192) {
        this.i0 = i0;
        this.i64 = i64;
        this.i128 = i128;
        this.i192 = i192;
    }
}
