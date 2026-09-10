package it.telami.commons.concurrency.thread;

/**
 * {@link Enum} defining how to handle resource contention cases.
 * @see ContentionHandler#NONE
 * @see ContentionHandler#LOW_LATENCY
 * @see ContentionHandler#HIGH_THROUGHPUT
 * @see ContentionHandler#SMART
 * @see ContentionHandler#REACTIVE
 * @see ContentionHandler#BALANCED
 * @see ContentionHandler#LOW_CONTEXT_SWITCH
 * @see ContentionHandler#TUNABLE_0
 * @see ContentionHandler#TUNABLE_1
 * @see ContentionHandler#TUNABLE_2
 * @see ContentionHandler#TUNABLE_3
 * @author Telami
 * @since 1.0.1
 */
@SuppressWarnings("JavadocDeclaration")
public enum ContentionHandler {
    /**
     * No latency, high context switch, high processor usage. <br>
     * This is the only non-tunable handler since its current values
     * are the only coherent with its name.
     * @author Telami
     * @since 1.0.3
     */
    NONE(1, 1, 0L),
    /**
     * Try to obtain the lowest possible latency,
     * reducing a bit the throughput and increasing
     * the context switch. <br>
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_LOW_LATENCY</li>
     * <li>-Define_FailsUntilPark_LOW_LATENCY</li>
     * <li>-Define_ParkForNanos_LOW_LATENCY</li>
     * </ul>
     * @author Telami
     * @since 1.0.1
     */
    LOW_LATENCY(Integer.getInteger("efine_SpinsPerFail_LOW_LATENCY", 1),
            Integer.getInteger("efine_FailsUntilPark_LOW_LATENCY", 4),
            Long.getLong("efine_ParkForNanos_LOW_LATENCY", 0L)),
    /**
     * Try to obtain the highest possible throughput,
     * increasing the latency and increasing a bit the
     * context switch. <br>
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_HIGH_THROUGHPUT</li>
     * <li>-Define_FailsUntilPark_HIGH_THROUGHPUT</li>
     * <li>-Define_ParkForNanos_HIGH_THROUGHPUT</li>
     * </ul>
     * @author Telami
     * @since 1.0.1
     */
    HIGH_THROUGHPUT(Integer.getInteger("efine_SpinsPerFail_HIGH_THROUGHPUT", 16),
            Integer.getInteger("efine_FailsUntilPark_HIGH_THROUGHPUT", 5),
            Long.getLong("efine_ParkForNanos_HIGH_THROUGHPUT", 0L)),
    /**
     * Try to obtain the highest throughput and lowest
     * latency while avoiding high processor usage. <br>
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_SMART</li>
     * <li>-Define_FailsUntilPark_SMART</li>
     * <li>-Define_ParkForNanos_SMART</li>
     * </ul>
     * @author Telami
     * @since 1.0.1
     */
    SMART(Integer.getInteger("efine_SpinsPerFail_SMART", 8),
            Integer.getInteger("efine_FailsUntilPark_SMART", 5),
            Long.getLong("efine_ParkForNanos_SMART", 2L)),
    /**
     * Try to obtain high throughput and low latency
     * while maintaining low processor usage. <br>
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_REACTIVE</li>
     * <li>-Define_FailsUntilPark_REACTIVE</li>
     * <li>-Define_ParkForNanos_REACTIVE</li>
     * </ul>
     * @author Telami
     * @since 1.0.1
     */
    REACTIVE(Integer.getInteger("efine_SpinsPerFail_REACTIVE", 8),
            Integer.getInteger("efine_FailsUntilPark_REACTIVE", 6),
            Long.getLong("efine_ParkForNanos_REACTIVE", 4L)),
    /**
     * Balance latency, throughput and context switch. <br>
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_BALANCED</li>
     * <li>-Define_FailsUntilPark_BALANCED</li>
     * <li>-Define_ParkForNanos_BALANCED</li>
     * </ul>
     * @author Telami
     * @since 1.0.1
     */
    BALANCED(Integer.getInteger("efine_SpinsPerFail_BALANCED", 16),
            Integer.getInteger("efine_FailsUntilPark_BALANCED", 4),
            Long.getLong("efine_ParkForNanos_BALANCED", 6L)),
    /**
     * Try to obtain the lowest possible context switch,
     * increasing the latency and reducing a bit the
     * throughput. <br>
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_LOW_CONTEXT_SWITCH</li>
     * <li>-Define_FailsUntilPark_LOW_CONTEXT_SWITCH</li>
     * <li>-Define_ParkForNanos_LOW_CONTEXT_SWITCH</li>
     * </ul>
     * @author Telami
     * @since 1.0.1
     */
    LOW_CONTEXT_SWITCH(Integer.getInteger("efine_SpinsPerFail_LOW_CONTEXT_SWITCH", 16),
            Integer.getInteger("efine_FailsUntilPark_LOW_CONTEXT_SWITCH", 3),
            Long.getLong("efine_ParkForNanos_LOW_CONTEXT_SWITCH", 64L)),
    /**
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_0</li>
     * <li>-Define_FailsUntilPark_0</li>
     * <li>-Define_ParkForNanos_0</li>
     * </ul>
     * <br>
     * The default values are the same as {@link ContentionHandler#SMART}.
     * @author Telami
     * @since 1.0.3
     */
    TUNABLE_0(Integer.getInteger("efine_SpinsPerFail_0", SMART.iSpins),
            Integer.getInteger("efine_FailsUntilPark_0", SMART.eSpins),
            Long.getLong("efine_ParkForNanos_0", SMART.nanos)),
    /**
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_1</li>
     * <li>-Define_FailsUntilPark_1</li>
     * <li>-Define_ParkForNanos_1</li>
     * </ul>
     * <br>
     * The default values are the same as {@link ContentionHandler#SMART}.
     * @author Telami
     * @since 1.0.3
     */
    TUNABLE_1(Integer.getInteger("efine_SpinsPerFail_1", SMART.iSpins),
            Integer.getInteger("efine_FailsUntilPark_1", SMART.eSpins),
            Long.getLong("efine_ParkForNanos_1", SMART.nanos)),
    /**
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_2</li>
     * <li>-Define_FailsUntilPark_2</li>
     * <li>-Define_ParkForNanos_2</li>
     * </ul>
     * <br>
     * The default values are the same as {@link ContentionHandler#SMART}.
     * @author Telami
     * @since 1.0.3
     */
    TUNABLE_2(Integer.getInteger("efine_SpinsPerFail_2", SMART.iSpins),
            Integer.getInteger("efine_FailsUntilPark_2", SMART.eSpins),
            Long.getLong("efine_ParkForNanos_2", SMART.nanos)),
    /**
     * Handler tunable through the sequent JVM startup flags:
     * <ul>
     * <li>-Define_SpinsPerFail_3</li>
     * <li>-Define_FailsUntilPark_3</li>
     * <li>-Define_ParkForNanos_3</li>
     * </ul>
     * <br>
     * The default values are the same as {@link ContentionHandler#SMART}.
     * @author Telami
     * @since 1.0.3
     */
    TUNABLE_3(Integer.getInteger("efine_SpinsPerFail_3", SMART.iSpins),
            Integer.getInteger("efine_FailsUntilPark_3", SMART.eSpins),
            Long.getLong("efine_ParkForNanos_3", SMART.nanos));

    public final int iSpins;
    public final int eSpins;
    public final long nanos;

    ContentionHandler (final int iSpins,
                       final int eSpins,
                       final long nanos) {
        if (iSpins < 0
                || eSpins < 1
                || eSpins >= Integer
                .numberOfLeadingZeros(iSpins))
            throw new IllegalArgumentException();
        this.iSpins = iSpins;
        this.eSpins = eSpins;
        this.nanos = nanos;
    }
}