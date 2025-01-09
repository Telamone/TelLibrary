package it.telami.commons.thread;

import java.util.concurrent.Delayed;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * A TimedTask is used by the {@link TimedThreadPool} for representing the submitted tasks. <p>
 * This class offers methods for managing the scheduled task.
 * @author Telami
 * @since 1.0.0
 */
public interface TimedTask extends ScheduledFuture<Object> {
    /**
     * Returns this task's ID. <p>
     * The tasks' ID increases in a linear way.
     * @return this task's ID
     * @author Telami
     * @since 1.0.0
     */
    int getID ();

    /**
     * Returns this task's {@link TimedThreadPool scheduler}.
     * @return the scheduler
     * @author Telami
     * @since 1.0.0
     */
    TimedThreadPool getScheduler ();

    /**
     * Returns this task's associated action that takes the task itself as an argument.
     * @return the submitted {@link Consumer action}
     * @author Telami
     * @since 1.0.0
     */
    Consumer<TimedTask> getAction ();

    /**
     * Cancel this task's scheduling if not already executed or cancelled.
     * @return true if this task has been cancelled by this method's call, false otherwise
     * @author Telami
     * @since 1.0.0
     */
    boolean cancel ();

    /**
     * If this task hasn't already been executed or cancelled,
     * {@link TimedTask#cancel() cancel} the current scheduling. <br>
     * Then schedule this task at a different time.
     * @return true if this task has been cancelled by this method's call, false otherwise
     * @param time the new time
     * @param unit the new unit
     * @author Telami
     * @since 1.0.0
     */
    boolean reSchedule (final long time, final TimeUnit unit);

    /**
     * If this task hasn't already been executed or cancelled,
     * {@link TimedTask#cancel() cancel} the current scheduling. <br>
     * If the precedent task has been cancelled by this method's
     * call, then schedule this task at a different time.
     * @return true if this task has been scheduled, false otherwise
     * @param time the new time
     * @param unit the new unit
     * @author Telami
     * @since 1.0.0
     */
    boolean reScheduleIfCancelled (final long time, final TimeUnit unit);

    /**
     * Reschedule this task without any cancellation check. <p>
     * <b>WARNING</b> <br>
     * This method should be used <b>only</b> for scheduling
     * this task during its execution or when its known that
     * it has been already executed!
     * @param time the new time
     * @param unit the new unit
     * @author Telami
     * @since 1.0.0
     */
    void reScheduleUnchecked (final long time, final TimeUnit unit);


    default boolean cancel(boolean mayInterruptIfRunning) {
        return cancel();
    }
    default boolean isCancelled() {
        throw new UnsupportedOperationException();
    }
    default boolean isDone() {
        throw new UnsupportedOperationException();
    }
    default Object get() {
        throw new UnsupportedOperationException();
    }
    default Object get(long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    default long getDelay(TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    default int compareTo(Delayed o) {
        throw new UnsupportedOperationException();
    }
}
