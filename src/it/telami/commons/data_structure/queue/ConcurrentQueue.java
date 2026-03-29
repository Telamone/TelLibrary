package it.telami.commons.data_structure.queue;

import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.data_structure.DataStructure;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Spliterator;

/**
 * Class representing a {@link Queue} implementation for fast
 * and {@link DataStructure#isThreadSafe() thread-safe} operations. <br> <br>
 * The only restriction is that, once inserted, elements cannot
 * be randomly removed from the queue. <br>
 * Calling any method involving a random removal of any element will
 * result in an {@link UnsupportedOperationException}.
 * @param <T> elements' type
 * @author Telami
 * @since 1.0.2
 */
public final class ConcurrentQueue<T> implements DataStructure, Queue<T> {
    /**
     * Create a new {@link ConcurrentQueue} using the given parameters.
     * @param handler the given {@link ContentionHandler}, used in case of spinning
     * @param spinOnOffer the given condition, used for determining
     *                    whether to spin or instant insert an element when
     *                    calling {@link Queue#offer(Object) offer(...)}. <br>
     *                    This may change how the {@link ConcurrentQueue queue}
     *                    behaves: <ul>
     *                    <li><b>spinning</b>: handles better the
     *                    element insertion under high-contention</li>
     *                    <li><b>one-shot</b>: instantly enqueue a given element
     *                    during its insertion. <br>
     *                    This comes with a trade-off, in case of high contention
     *                    the insertion may slow down.</li>
     *                    </ul>
     * @author Telami
     * @since 1.0.2
     */
    public ConcurrentQueue (final ContentionHandler handler,
                            final boolean spinOnOffer) {
        //Hidden implementation...
    }
    /**
     * Create a new {@link ConcurrentQueue} with a default <b>spinOnExecute</b>
     * (equal to {@code false}). <br>
     * For more information about these values, see
     * {@link ConcurrentQueue#ConcurrentQueue(ContentionHandler, boolean) this constructor}.
     * @author Telami
     * @since 1.0.2
     */
    public ConcurrentQueue (final ContentionHandler handler) {
        this(handler, false);
    }
    /**
     * Create a new {@link ConcurrentQueue} with a default {@link ContentionHandler}
     * (equal to {@link ContentionHandler#SMART SMART}). <br>
     * For more information about these values, see
     * {@link ConcurrentQueue#ConcurrentQueue(ContentionHandler, boolean) this constructor}.
     * @author Telami
     * @since 1.0.2
     */
    public ConcurrentQueue (final boolean spinOnOffer) {
        this(ContentionHandler.SMART, spinOnOffer);
    }
    /**
     * Create a new {@link ConcurrentQueue} with a default <b>spinOnExecute</b>
     * (equal to {@code false}) and {@link ContentionHandler} (equal to
     * {@link ContentionHandler#SMART SMART}). <br>
     * For more information about these values, see
     * {@link ConcurrentQueue#ConcurrentQueue(ContentionHandler, boolean) this constructor}.
     * @author Telami
     * @since 1.0.2
     */
    public ConcurrentQueue () {
        this(ContentionHandler.SMART, false);
    }

    public boolean isThreadSafe () {
        return true;
    }

    public int size () {
        //Hidden implementation...
        return 0;
    }

    public boolean isEmpty () {
        //Hidden implementation...
        return false;
    }

    public boolean contains (final Object o) {
        //Hidden implementation...
        return false;
    }

    public Iterator<T> iterator () {
        //Hidden implementation...
        return null;
    }
    public Spliterator<T> spliterator () {
        //Hidden implementation...
        return null;
    }

    public Object[] toArray () {
        //Hidden implementation...
        return null;
    }
    public <R> R[] toArray (final R[] a) {
        //Hidden implementation...
        return null;
    }

    public boolean add (final T t) {
        //Hidden implementation...
        return false;
    }

    public boolean remove (final Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll (final Collection<?> c) {
        //Hidden implementation...
        return false;
    }

    public boolean addAll (final Collection<? extends T> c) {
        //Hidden implementation...
        return false;
    }

    public boolean removeAll (final Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    public boolean retainAll (final Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear () {
        //Hidden implementation...
    }


    public boolean offer (final T t) {
        //Hidden implementation...
        return false;
    }

    public T remove () {
        //Hidden implementation...
        return null;
    }

    public T poll () {
        //Hidden implementation...
        return null;
    }

    public T element () {
        //Hidden implementation...
        return null;
    }

    public T peek () {
        //Hidden implementation...
        return null;
    }
}
