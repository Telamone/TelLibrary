package it.telami.commons.concurrency.gate;

import it.telami.commons.concurrency.thread.ContentionHandler;

/**
 * "Unsafe" version of the {@link MultiWriterGate}
 * (the logic is still the same). <br>
 * For enabling some optimizations and guaranteeing
 * faster computation, some methods change behaviour
 * or signature in comparison to the "safer" version.
 * @author Telami
 * @since 1.0.3
 */
public final class UnsafeMultiWriterGate {
    /**
     * Create a new {@link UnsafeMultiWriterGate} instance.
     * @author Telami
     * @since 1.0.3
     */
    public UnsafeMultiWriterGate () {}

    /**
     * See {@link MultiWriterGate#isOpen()}.
     * @return {@code true} if the gate is open, {@code false} otherwise
     * @author Telami
     * @since 1.0.3
     */
    public boolean isOpen () {
        //Hidden implementation...
        return false;
    }
    /**
     * Theoretically faster implementation of {@link UnsafeMultiWriterGate#isOpen()}.
     * @return {@code true} if the gate is open, {@code false} otherwise
     * @author Telami
     * @since 1.0.3
     */
    public boolean isOpenUnsafe () {
        //Hidden implementation...
        return false;
    }

    /**
     * See {@link MultiWriterGate#tryIncrease()}. <br>
     * This method uses the given handler as a strategy in
     * case of contention.
     * @param handler the given {@link ContentionHandler handler}
     * @return {@code true} if the gate is open, {@code false} otherwise
     * @author Telami
     * @since 1.0.3
     */
    public boolean tryIncrease (final ContentionHandler handler) {
        //Hidden implementation...
        return false;
    }
    /**
     * See {@link MultiWriterGate#tryIncreaseAndGet()}. <br>
     * This method uses the given handler as a strategy in
     * case of contention.
     * @param handler the given {@link ContentionHandler handler}
     * @return {@code result > 0} if the gate is still open, {@code 0} otherwise
     * @author Telami
     * @since 1.0.3
     */
    public int tryIncreaseAndGet (final ContentionHandler handler) {
        //Hidden implementation...
        return 0;
    }

    /**
     * This method behaves the same as {@link MultiWriterGate#getAndDecrease()},
     * but, if called more than {@link UnsafeMultiWriterGate#tryIncrease(ContentionHandler)}
     * or {@link UnsafeMultiWriterGate#tryIncreaseAndGet(ContentionHandler)}, the gate
     * become automatically corrupted until the number of calls become equal.
     * @return {@code result > 0} if the gate is still open, {@code 0} if closed,
     *         {@code result < 0} if corrupted
     * @author Telami
     * @since 1.0.3
     */
    public int getAndDecrease () {
        //Hidden implementation...
        return 0;
    }

    /**
     * See {@link MultiWriterGate#waitUntilClose(ContentionHandler)}.
     * @param handler the given {@link ContentionHandler handler}
     * @author Telami
     * @since 1.0.3
     */
    public void waitUntilClose (final ContentionHandler handler) {
        //Hidden implementation...
    }

    /**
     * See {@link MultiWriterGate#reset()}.
     * @return {@code true} if the operation succeed, {@code false} otherwise
     * @author Telami
     * @since 1.0.3
     */
    public boolean reset () {
        //Hidden implementation...
        return false;
    }
}
