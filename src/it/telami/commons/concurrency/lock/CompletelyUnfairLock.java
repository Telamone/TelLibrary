package it.telami.commons.concurrency.lock;

import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * A {@link Lock} based class that handles thread unlocking as LIFO. <br>
 * The {@link Thread} that fails to acquire during {@link CompletelyUnfairLock#lock()}
 * starts parking immediately.
 * @author Telami
 * @since 1.0.0
 */
public final class CompletelyUnfairLock implements Lock, AutoCloseable {
    public CompletelyUnfairLock () {}

    public boolean tryLock () {
        return false;
    }
    public void lock () {}
    /**
     * Forcefully lock, this might break everything, pay attention using this! <br>
     * This method has the same memory effects as {@link VarHandle#setOpaque(Object...) setOpaque}.
     * @author Telami
     * @since 1.0.0
     */
    public void forceLock () {}

    /**
     * Check if this lock is currently locked. <br>
     * This method has the same memory effects as {@link VarHandle#getAcquire(Object...) getAcquire}.
     * @author Telami
     * @since 1.0.0
     * @return {@code true} if locked, {@code false} otherwise
     */
    public boolean isLocked () {
        return false;
    }

    /**
     * Make this Thread wait until unlocked, if the lock is active.
     * @return {@code true} if the lock was active, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean tryWait () {
        return false;
    }

    public void unlock () {}
    public void close () {}


    public boolean tryLock (final long time, final TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    public void lockInterruptibly () {
        throw new UnsupportedOperationException();
    }
    public Condition newCondition () {
        throw new UnsupportedOperationException();
    }
}
