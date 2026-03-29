package it.telami.commons.concurrency.thread;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Class defining a new kind of {@link ExecutorService} designed
 * for executing tasks quickly. <br> <br>
 * The direct comparison is done with the {@link ForkJoinPool}
 * since both share the <b>task stealing mechanism</b> that
 * enables them the possibility to avoid the <b>starving</b>
 * of their {@link Thread threads} by sharing the access of
 * their task {@link Queue queues}. <br>
 * @apiNote Unlike the {@link ForkJoinPool} there is no sync/help
 *          mechanism like {@link ForkJoinPool#awaitQuiescence(long,
 *          TimeUnit) awaitQuiescence(...)}, so, in case it is needed,
 *          an external layer of synchronization through the submitted
 *          tasks is needed. <br>
 *          Thought that this might be seen as a limitation, it has
 *          been done to avoid receiving help indefinitely from the
 *          {@link Thread helping thread} since there could be no
 *          limit to the amount of tasks submitted while the help
 *          is needed and that could extend to extra work done while
 *          the result have been already reached.
 * @author Telami
 * @since 1.0.2
 */
public final class QuickTaskPool implements ExecutorService {
    /**
     * Create a new {@link QuickTaskPool} with a default {@link ThreadFactory},
     * number of {@link Thread threads} (equal to {@link Runtime#availableProcessors()})
     * and <b>spinOnExecute</b> (equal to {@code true}). <br>
     * For more information about these values, see
     * {@link QuickTaskPool#QuickTaskPool(ThreadFactory, int, boolean) this constructor}.
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool () {
        this(Thread::new, Runtime.getRuntime().availableProcessors(), true);
    }
    /**
     * Create a new {@link QuickTaskPool} with the given {@link ThreadFactory} and a default
     * number of {@link Thread threads} (equal to {@link Runtime#availableProcessors()})
     * and <b>spinOnExecute</b> (equal to {@code true}). <br>
     * For more information about these values, see
     * {@link QuickTaskPool#QuickTaskPool(ThreadFactory, int, boolean) this constructor}.
     * @param factory the given {@link ThreadFactory}
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool (final ThreadFactory factory) {
        this(factory, Runtime.getRuntime().availableProcessors(), true);
    }
    /**
     * Create a new {@link QuickTaskPool} with the given {@link ThreadFactory} and
     * number of {@link Thread threads} and a default <b>spinOnExecute</b>
     * (equal to {@code true}). <br>
     * For more information about these values, see
     * {@link QuickTaskPool#QuickTaskPool(ThreadFactory, int, boolean) this constructor}.
     * @param factory the given {@link ThreadFactory}
     * @param parallelism the given number of {@link Thread threads}
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool (final ThreadFactory factory, final int parallelism) {
        this(factory, parallelism, true);
    }
    /**
     * Create a new {@link QuickTaskPool} with the given {@link ThreadFactory}
     * and <b>spinOnExecute</b> and a default
     * number of {@link Thread threads} (equal to {@link Runtime#availableProcessors()}). <br>
     * For more information about these values, see
     * {@link QuickTaskPool#QuickTaskPool(ThreadFactory, int, boolean) this constructor}.
     * @param factory the given {@link ThreadFactory}
     * @param spinOnExecute the given condition
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool (final ThreadFactory factory, final boolean spinOnExecute) {
        this(factory, Runtime.getRuntime().availableProcessors(), spinOnExecute);
    }
    /**
     * Create a new {@link QuickTaskPool} with a default {@link ThreadFactory}
     * and <b>spinOnExecute</b> (equal to {@code true}) and the given
     * number of {@link Thread threads}. <br>
     * For more information about these values, see
     * {@link QuickTaskPool#QuickTaskPool(ThreadFactory, int, boolean) this constructor}.
     * @param parallelism the given number of {@link Thread threads}
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool (final int parallelism) {
        this(Thread::new, parallelism, true);
    }
    /**
     * Create a new {@link QuickTaskPool} with a default {@link ThreadFactory}
     * and the given number of {@link Thread threads} and <b>spinOnExecute</b>. <br>
     * For more information about these values, see
     * {@link QuickTaskPool#QuickTaskPool(ThreadFactory, int, boolean) this constructor}.
     * @param parallelism the given number of {@link Thread threads}
     * @param spinOnExecute the given condition
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool (final int parallelism, final boolean spinOnExecute) {
        this(Thread::new, parallelism, spinOnExecute);
    }
    /**
     * Create a new {@link QuickTaskPool} with a default {@link ThreadFactory} and
     * number of {@link Thread threads} (equal to {@link Runtime#availableProcessors()})
     * and the given <b>spinOnExecute</b>. <br>
     * For more information about these values, see
     * {@link QuickTaskPool#QuickTaskPool(ThreadFactory, int, boolean) this constructor}.
     * @param spinOnExecute the given condition
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool (final boolean spinOnExecute) {
        this(Thread::new, Runtime.getRuntime().availableProcessors(), spinOnExecute);
    }
    /**
     * Create a new {@link QuickTaskPool} with the given parameters.
     * @param factory the given {@link ThreadFactory} that is used for
     *                creating new {@link Thread threads} <br>
     * @param parallelism the given number of {@link Thread threads} that
     *                    are used/created in this {@link QuickTaskPool pool} <br>
     * @param spinOnExecute the given condition that is used for determining
     *                      whether to spin or instant submit the task to
     *                      {@link ExecutorService#execute(Runnable) execute}. <br>
     *                      This may change how the {@link QuickTaskPool pool}
     *                      behaves: <ul>
     *                      <li><b>spinning</b>: permits to better balance the
     *                      load between task submission and task retrieval
     *                      maintaining low latency between submission and
     *                      execution even under high-contention</li>
     *                      <li><b>one-shot</b>: prioritize submission using
     *                      a different approach, this help the {@link Thread
     *                      submitter} instantly enqueuing its task and avoiding
     *                      waste of cpu time.<br>
     *                      This comes with a trade-off, in case of high contention
     *                      the execution may slow down.</li>
     *                      </ul>
     * @author Telami
     * @since 1.0.2
     */
    public QuickTaskPool (final ThreadFactory factory,
                          int parallelism,
                          final boolean spinOnExecute) {
        //Hidden implementation...
    }

    public void execute (final Runnable task) {
        //Hidden implementation...
    }

    public void shutdown () {
        //Hidden implementation...
    }
    public List<Runnable> shutdownNow () {
        //Hidden implementation...
        return null;
    }

    public boolean isShutdown () {
        //Hidden implementation...
        return false;
    }
    public boolean isTerminated () {
        //Hidden implementation...
        return false;
    }

    public boolean awaitTermination (final long timeout, final TimeUnit unit) throws InterruptedException {
        //Hidden implementation...
        return false;
    }

    public <T> Future<T> submit (final Callable<T> task) {
        //Hidden implementation...
        return null;
    }
    public <T> Future<T> submit (final Runnable task, final T result) {
        //Hidden implementation...
        return null;
    }
    public Future<?> submit (final Runnable task) {
        //Hidden implementation...
        return null;
    }

    public <T> List<Future<T>> invokeAll (final Collection<? extends Callable<T>> tasks) throws InterruptedException {
        //Hidden implementation...
        return null;
    }
    public <T> List<Future<T>> invokeAll (final Collection<? extends Callable<T>> tasks,
                                          final long timeout,
                                          final TimeUnit unit) throws InterruptedException {
        //Hidden implementation...
        return null;
    }


    public <T> T invokeAny (final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        //Hidden implementation...
        return null;
    }
    public <T> T invokeAny (final Collection<? extends Callable<T>> tasks,
                            final long timeout,
                            final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        //Hidden implementation...
        return null;
    }
}
