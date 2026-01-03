package it.telami.commons.concurrency.thread;

/**
 * {@link Enum} defining how to handle contention cases.
 * @see ContentionHandler#LOW_LATENCY
 * @see ContentionHandler#HIGH_THROUGHPUT
 * @see ContentionHandler#BALANCED
 * @see ContentionHandler#LOW_CONTEXT_SWITCH
 * @author Telami
 * @since 1.0.1
 */
@SuppressWarnings("JavadocDeclaration")
public enum ContentionHandler {
    /**
     * Try to obtain the lowest possible latency,
     * reducing a bit the throughput and increasing
     * the context switch.
     * @author Telami
     * @since 1.0.1
     */
    LOW_LATENCY(1, 32, 0L),
    /**
     * Try to obtain the highest possible throughput,
     * increasing the latency and increasing a bit the
     * context switch.
     * @author Telami
     * @since 1.0.1
     */
    HIGH_THROUGHPUT(192, 12, 0L),
    /**
     * Try to obtain the highest throughput and lowest
     * latency while avoiding high processor usage.
     * @author Telami
     * @since 1.0.1
     */
    SMART(24, 8, 1L),
    /**
     * Try to obtain high throughput and low latency
     * while maintaining low processor usage.
     * @author Telami
     * @since 1.0.1
     */
    REACTIVE(392, 8, 4L),
    /**
     * Balance latency, throughput and context switch.
     * @author Telami
     * @since 1.0.1
     */
    BALANCED(512, 4, 8L),
    /**
     * Try to obtain the lowest possible context switch,
     * increasing the latency and reducing a bit the
     * throughput.
     * @author Telami
     * @since 1.0.1
     */
    LOW_CONTEXT_SWITCH(256, 2, 256L),
    /**
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail</li>
     * <li>-Define_FailsUntilPark</li>
     * <li>-Define_ParkForNanos</li>
     * </ul>
     * <br>
     * The default values are the same as {@link ContentionHandler#SMART}.
     * @author Telami
     * @since 1.0.1
     */
    CUSTOM(Integer.getInteger("efine_SpinsPerFail", 24),
            Integer.getInteger("efine_FailsUntilPark", 8),
            Long.getLong("efine_ParkForNanos", 1L));

    public final int iSpins;
    public final int eSpins;
    public final long nanos;

    ContentionHandler (final int iSpins,
                       final int eSpins,
                       final long nanos) {
        this.iSpins = iSpins;
        this.eSpins = eSpins;
        this.nanos = nanos;
    }
}