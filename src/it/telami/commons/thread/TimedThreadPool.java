package it.telami.commons.thread;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * An extension of {@link ThreadPool}. <p>
 * The implementations offer methods like
 * {@link TimedThreadPool#schedule(Consumer, long, TimeUnit) schedule} and
 * {@link TimedThreadPool#scheduleLast(Consumer, long, TimeUnit) scheduleLast}
 * for scheduling at a given time tasks that can be manipulated. <p>
 * Please note that the tasks cannot be stopped during their
 * execution, but only if queued, as specified by {@link TimedTask#cancel() cancel()}.
 * @author Dr4aKy
 * @since 1.0.0
 */
public abstract class TimedThreadPool extends ThreadPool {
    protected TimedThreadPool (final short poolID,
                               final short parallelism) {
        super(poolID, parallelism);
    }

    /**
     * Schedules a {@link Consumer} that will be executed, taking
     * this task as an argument, after the given time.
     * @param r the task to schedule
     * @param time the time
     * @param unit the unit
     * @return the corresponding {@link TimedTask}
     * @author Dr4aky
     * @since 1.0.0
     */
    public abstract TimedTask schedule (final Consumer<TimedTask> r,
                                        final long time,
                                        final TimeUnit unit);

    /**
     * Schedules a {@link Consumer} that will be executed, taking
     * this task as an argument, after the given time and only once
     * the precedent submitted tasks finish their execution. <p>
     * This method improves performance and doesn't produce undefined
     * behaviours if <b>all the tasks</b> are submitted at an <b>equal
     * or constantly increasing</b> amount of time. <p>
     * If two or more tasks are submitted at the same time, they could
     * be reordered!
     * @param r the task to schedule
     * @param time the time
     * @param unit the unit
     * @return the corresponding {@link TimedTask}
     * @author Dr4aky
     * @since 1.0.0
     */
    public abstract TimedTask scheduleLast (final Consumer<TimedTask> r,
                                            final long time,
                                            final TimeUnit unit);
}
