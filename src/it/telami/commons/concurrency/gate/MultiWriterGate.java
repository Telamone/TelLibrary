package it.telami.commons.concurrency.gate;

import it.telami.commons.concurrency.thread.ThreadSecondarySeedHandler;

import java.lang.invoke.VarHandle;

/**
 * This class handles synchronization of one-to-many, used when it's
 * needed to wait on many threads to finish their work. <br>
 * The Gate remains {@link MultiWriterGate#isOpen() open} until a thread
 * (or many) request the {@link MultiWriterGate#waitUntilClose(Runnable)
 * closure}, so it will wait until all the other threads {@link
 * MultiWriterGate#getAndDecrease() decrease} the counter of the threads
 * {@link MultiWriterGate#tryIncrease() inside} the Gate.
 * @author Telami
 * @since 1.0.0
 */
public final class MultiWriterGate {
    public MultiWriterGate () {}

    /**
     * Useful if monitoring gate's state is needed. <br>
     * This method has the same memory effects as {@link VarHandle#getAcquire(Object...) getAcquire}.
     * @return {@code true} if the gate is open, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean isOpen () {
        return false;
    }

    /**
     * Try to increase the amount of working threads. <br>
     * If the "guardian" has requested the closure, the operation will succeed
     * as long as there is at least another working thread already inside.
     * @return {@code true} if the gate is still open, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean tryIncrease () {
        return false;
    }

    /**
     * Decrease the amount of working threads and return the
     * number of threads that where inside previously (plus
     * the "guardian", if it was not waiting for closure).
     * @return the number of threads inside the Gate (+1 if the
     *         closure was not requested)
     * @author Telami
     * @since 1.0.0
     */
    public int getAndDecrease () {
        return 0;
    }

    /**
     * This thread becomes the "guardian" and waits until there are
     * no threads inside the gate, then closes it. <br>
     * Additionally takes as input an action that may be useful to
     * specify since the implementation doesn't know the workload. <br>
     * It's highly suggested to use actions like {@link ThreadSecondarySeedHandler#spinOrYieldHeuristically() spin-yield}
     * for lite workloads (faster execution) or {@link ThreadSecondarySeedHandler#parkSpeculatively(int, int) park}
     * for heavy workloads (more efficient).
     * @param spinHandler action performed during the <b>spinning</b>,
     *                    a technic used for avoiding the waste of
     *                    too many resources during failing cycles
     * @author Telami
     * @since 1.0.0
     */
    public void waitUntilClose (final Runnable spinHandler) {}

    /**
     * Re-open the gate if it was closed. <br>
     * This is particularly heavy respect to the other
     * operations since it's not allowed to fail under
     * a correct code flow like this:
     * <pre> {@code
     * waitUntilClose(...);
     * //Shouldn't get reordered!
     * //Shouldn't fail spuriously!
     * //Must return 'true' exactly ONCE,
     * //other threads should read 'false'!
     * reset();
     * } </pre>
     * @return {@code true} if the operation succeed, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean reset () {
        return false;
    }
}
