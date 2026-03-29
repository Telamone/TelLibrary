package it.telami.commons.concurrency.atomic;

/**
 * Extremely simple 128-bit integer representation.
 * @author Telami
 * @since 1.0.2
 */
public final class I128 {
    public long high;
    public long low;

    public I128 () {}
    public I128 (final long high,
                 final long low) {
        this.high = high;
        this.low = low;
    }
}
