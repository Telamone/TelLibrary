package it.telami.commons.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * An extension of {@link ThreadPool}. <p>
 * The implementations offer methods like
 * {@link TimedThreadPool#schedule(Consumer, long, TimeUnit) schedule}
 * for scheduling at a given time tasks that can be manipulated. <p>
 * Please note that the tasks cannot be stopped during their
 * execution, but only if queued, as specified by {@link TimedTask#cancel() cancel()}.
 * @author Dr4aKy
 * @since 1.0.0
 */
public abstract class TimedThreadPool extends ThreadPool implements ScheduledExecutorService {
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
     * @author Telami
     * @since 1.0.0
     */
    public abstract TimedTask schedule (final Consumer<TimedTask> r,
                                        final long time,
                                        final TimeUnit unit);

    public ScheduledFuture<?> schedule(Runnable command,
                                       long delay, TimeUnit unit) {
        return schedule(_ -> command.run(), delay, unit);
    }
    public <V> ScheduledFuture<V> schedule(Callable<V> callable,
                                           long delay, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
}
