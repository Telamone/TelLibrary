package it.telami.commons.concurrency.gate;

import it.telami.commons.concurrency.thread.ContentionHandler;

/**
 * Class similar to {@link MultiWriterGate} that enables <b>fairness</b>, this means
 * that once {@link FairMultiWriterGate#waitUntilClose(ContentionHandler)
 * waitUntilClose(...)}
 * is called, any further {@link FairMultiWriterGate#tryEnter(ContentionHandler)
 * tryEnter(...)}
 * call will fail, even if there are other {@link Thread threads} still inside
 * the Gate. <br>
 * In contrast, the amount of working threads inside the Gate remains unknown
 * during the {@link FairMultiWriterGate#tryEnter(ContentionHandler) entrance}
 * and the {@link FairMultiWriterGate#exit(ContentionHandler) exit}. <br>
 * This class perform significantly better in case of contention.
 * @author Telami
 * @since 1.0.3
 */
public final class FairMultiWriterGate {
    /**
     * Create a new {@link FairMultiWriterGate} instance.
     * @author Telami
     * @since 1.0.3
     */
    public FairMultiWriterGate () {}

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
     * Try entering the Gate and maintaining it {@link FairMultiWriterGate#isOpen() open}
     * until {@link FairMultiWriterGate#exit(ContentionHandler) exit(...)}
     * is called. <br>
     * A correct use of this method should result in a code
     * like this:
     * <pre> {@code
     * assert gate instanceof FairMultiWriterGate;
     * assert handler instanceof ContentionHandler;
     * if (gate.tryEnter(handler)) try {
     *     //The gate is still open...
     * } finally {
     *     gate.exit(handler);
     * } else {
     *     //The gate has been closed...
     * }
     * } </pre>
     * @param handler the given {@link ContentionHandler handler}
     * @return {@code true} if the gate is open, {@code false} otherwise
     * @author Telami
     * @since 1.0.3
     */
    public boolean tryEnter (final ContentionHandler handler) {
        //Hidden implementation...
        return false;
    }

    /**
     * Exit from the Gate. <br>
     * If {@link FairMultiWriterGate#waitUntilClose(ContentionHandler) waitUntilClose(...)}
     * has been called and this was the last {@link Thread} inside the Gate, then the
     * waiting {@link Thread} may proceed with the execution. <br>
     * It is recommended to call this method <b>only once</b> everytime the
     * {@link FairMultiWriterGate#tryEnter(ContentionHandler) tryEnter(...)} is called. <br>
     * A correct use of this method should result in a code like this:
     * <pre> {@code
     * assert gate instanceof FairMultiWriterGate;
     * assert handler instanceof ContentionHandler;
     * if (gate.tryEnter(handler)) try {
     *     //The gate is still open...
     * } finally {
     *     gate.exit(handler);
     * } else {
     *     //The gate has been closed...
     * }
     * } </pre>
     * @param handler the given {@link ContentionHandler handler}
     * @author Telami
     * @since 1.0.3
     */
    public void exit (final ContentionHandler handler) {
        //Hidden implementation...
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
