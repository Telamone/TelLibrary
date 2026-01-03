package it.telami.commons.concurrency.gate;

import it.telami.commons.concurrency.thread.ContentionHandler;

import java.lang.invoke.VarHandle;

/**
 * This class handles synchronization of one-to-many, used when it's
 * needed to wait on many threads to finish their work. <br>
 * The Gate remains {@link MultiWriterGate#isOpen() open} until a thread
 * (or many) request the {@link MultiWriterGate#waitUntilClose(ContentionHandler)
 * closure}, so it will wait until all the other threads
 * {@link MultiWriterGate#getAndDecrease() decrease} the counter of the
 * threads {@link MultiWriterGate#tryIncrease() inside} the Gate.
 * @author Telami
 * @since 1.0.0
 */
public final class MultiWriterGate {
    /**
     * Create a new {@link MultiWriterGate} instance.
     * @author Telami
     * @since 1.0.0
     */
    public MultiWriterGate () {
        this(ContentionHandler.SMART);
    }
    /**
     * Create a new {@link MultiWriterGate} instance.
     * @param handler a given {@link ContentionHandler handler}
     * @author Telami
     * @since 1.0.2
     */
    public MultiWriterGate (final ContentionHandler handler) {
        //Hidden implementation...
    }

    /**
     * Useful if monitoring gate's state is needed. <br>
     * This method has the same memory effects as {@link VarHandle#getAcquire(Object...) getAcquire}.
     * @return {@code true} if the gate is open, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean isOpen () {
        //Hidden implementation...
        return false;
    }

    /**
     * Try to increase the amount of working threads. <br>
     * If the closure has been requested, the operation will succeed
     * as long as there is at least another working thread already inside.
     * @return {@code true} if the gate is still open, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean tryIncrease () {
        //Hidden implementation...
        return false;
    }

    /**
     * Try to increase the amount of working threads and then return the current
     * amount. <br>
     * If the closure has been requested, the operation will succeed
     * as long as there is at least another working thread already inside.
     * @return {@code result > 0} if the gate is still open, {@code 0} otherwise
     * @author Telami
     * @since 1.0.1
     */
    public int tryIncreaseAndGet () {
        //Hidden implementation...
        return 0;
    }

    /**
     * Decrease the amount of working threads and return the
     * previous number of working threads. <br>
     * Calling this method twice or more times may lead to
     * corruption if it is not known what this method exactly
     * does! <br>
     * If the closure has been requested, this method returns
     * the current count of working threads. <br> <br>
     * Example of how to use this method correctly:
     * <pre> {@code
     * assert mwg instanceof MultiWriterGate;
     *
     * if (mwg.tryIncrease()) try {
     *     //The gate is still open...
     * } finally {
     *     mwg.getAndDecrease();
     * } else {
     *     //The gate has been closed...
     * }
     * } </pre>
     *
     * @return {@code result > 0} if the gate is still open,
     *         {@code 0} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public int getAndDecrease () {
        //Hidden implementation...
        return 0;
    }

    /**
     * Waits until there are no threads inside the gate, then closes it. <br>
     * Additionally takes as input a {@link ContentionHandler handler}
     * that may be useful to specify since the implementation doesn't
     * know the type of workload it's waiting for.
     * @param handler the given {@link ContentionHandler handler}
     * @author Telami
     * @since 1.0.0
     */
    public void waitUntilClose (final ContentionHandler handler) {
        //Hidden implementation...
    }

    /**
     * Re-open the gate if it was closed. <br>
     * This is particularly heavy respect to the other
     * operations since it's not allowed to fail under
     * a correct code flow like this:
     * <pre> {@code
     * waitUntilClose(...);
     * //Shouldn't get reordered!
     * //Shouldn't fail spuriously!
     * //Must return 'true' exactly ONCE!
     * reset();
     * } </pre>
     * @return {@code true} if the operation succeed, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean reset () {
        //Hidden implementation...
        return false;
    }
}
