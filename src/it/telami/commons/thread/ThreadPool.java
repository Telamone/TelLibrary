package it.telami.commons.thread;

import java.util.concurrent.Executor;

/**
 * This class implements {@link Executor} allowing compatibility with
 * {@link java.util.concurrent.CompletableFuture CompletableFuture} and other classes.
 * @author Dr4aKy
 * @since 1.0.0
 */
public abstract class ThreadPool implements Executor {
    protected final ThreadGroup threadGroup;
    protected final short poolID;
    protected final short parallelism;

    protected ThreadPool (final short poolID,
                          final short parallelism) {
        ThreadGroup root = Thread
                .currentThread()
                .getThreadGroup();
        while (root.getParent() != null)
            root = root.getParent();
        this.threadGroup = new ThreadGroup(
                root,
                "TelThreadGroup - " + poolID);
        this.poolID = poolID;
        this.parallelism = parallelism;
    }

    /**
     * Returns this {@link ThreadPool}'s ID.
     * @return this thread pool's ID
     * @author Dr4aKy
     * @since 1.0.0
     */
    public final short getPoolID () {
        return this.poolID;
    }

    /**
     * Returns this {@link ThreadPool}'s {@link ThreadPoolType type}. <p>
     * In the case of this library's thread pools, the type can be
     * cast to {@link TelThreadPoolType} for treating it as an {@link Enum}.
     * @return this thread pool's type
     * @author Dr4aKy
     * @since 1.0.0
     */
    public abstract ThreadPoolType getType ();
    /**
     * Returns the amount of {@link Thread threads} that are
     * used in this {@link ThreadPool}.
     * @return this thread pool's parallelism
     * @author Dr4aKy
     * @since 1.0.0
     */
    public final short getParallelism () {
        return parallelism;
    }
    /**
     * Returns this {@link ThreadPool}'s {@link ThreadGroup}.
     * @return this thread pool's thread group
     * @author Dr4aKy
     * @since 1.0.0
     */
    public final ThreadGroup getThreadGroup () {
        return threadGroup;
    }

    /**
     * Submits a {@link Runnable} to this {@link ThreadPool} that
     * will be executed as soon as the {@link Thread threads} are
     * not busy and no other non-executed tasks last before.
     * @param r the task to submit
     * @author Dr4aKy
     * @since 1.0.0
     */
    public abstract void execute (final Runnable r);

    /**
     * Denies the future submissions and ends all the waiting {@link Thread threads}
     * in this {@link ThreadPool} and leaves the running ones finish their current executions.
     * @author Dr4aKy
     * @since 1.0.0
     */
    public abstract void shutdown ();
    /**
     * Denies the future submissions and tries ending all the waiting and running
     * {@link Thread threads} in this {@link ThreadPool} forcing them to quit their executions.
     * @return all the non-executed submitted tasks
     * @author Dr4aKy
     * @since 1.0.0
     */
    public abstract Runnable[] shutdownNow ();
}