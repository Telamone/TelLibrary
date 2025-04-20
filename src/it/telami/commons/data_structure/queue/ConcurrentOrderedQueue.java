package it.telami.commons.data_structure.queue;

import it.telami.annotations.PlannedForFuture;
import it.telami.annotations.Unstable;
import it.telami.commons.data_structure.DataStructure;

import java.lang.invoke.VarHandle;
import java.util.*;

/**
 * @author Telami
 * @since 1.0.0
 * @param <T> this queue elements' type
 * @see java.util.Queue
 * @see DataStructure
 * @see ConcurrentOrderedQueue#ConcurrentOrderedQueue(Comparator, ContentionHandler)  ConcurrentOrderedQueue
 * @see ConcurrentOrderedQueue#switchProfile(ContentionHandler)
 */
@Unstable(version = "1.0.2", problems = "Deadlock may happen in high contention contexts! (Safe if used in single thread)")
public final class ConcurrentOrderedQueue<T> implements DataStructure, Queue<T> {
    public enum ContentionHandler {
        SINGLE_SPIN,
        MULTI_SPIN,
        PARKER
    }

    /**
     * This {@link Queue} uses the given {@link Comparator comparator} for ordering
     * elements from the lowest to the highest. <br>
     * The {@link ConcurrentOrderedQueue#offer(Object)} never fails and all the operations
     * are thread safe, as specified by {@link ConcurrentOrderedQueue#isThreadSafe()}.
     * @param comparator the comparator used for ordering the elements
     * @param handler the handler  to use in case of resources contention
     * @author Telami
     * @since 1.0.0
     */
    public ConcurrentOrderedQueue (final Comparator<T> comparator, final ContentionHandler handler) {}

    /**
     * Switches from a spinning profile to another. <br>
     * Affects memory as described by {@link VarHandle#setOpaque(Object...)}.
     * @param handler the handler  to use in case of resources contention
     */
    public void switchProfile (final ContentionHandler handler) {}

    /**
     * @return {@code true} if the implementation's methods are thread-safe, {@code false} otherwise
     * @author Telami
     * @since 1.0.0
     */
    public boolean isThreadSafe () {
        return true;
    }

    /**
     * @return the number of elements in this collection
     * @author Telami
     * @since 1.0.0
     */
    public int size () {
        return 0;
    }

    /**
     * @return {@code true} if this collection contains no elements
     * @author Telami
     * @since 1.0.0
     */
    public boolean isEmpty () {
        return false;
    }

    /**
     * @param o element whose presence in this collection is to be tested
     * @return {@code true} if this collection contains the specified element
     * @author Telami
     * @since 1.0.0
     */
    public boolean contains (final Object o) {
        return false;
    }

    /**
     * @return an {@link Iterator} over the elements in this collection
     * @author Telami
     * @since 1.0.0
     */
    public Iterator<T> iterator () {
        return null;
    }

    /**
     * @return a {@code Spliterator} over the elements in this collection
     * @author Telami
     * @since 1.0.0
     */
    public Spliterator<T> spliterator () {
        return null;
    }

    /**
     * @return an array, whose {@linkplain Class#getComponentType runtime component type}
     *         is {@code Object}, containing all the elements in this collection
     * @author Telami
     * @since 1.0.0
     */
    public Object[] toArray () {
        return null;
    }

    /**
     * @see ConcurrentOrderedQueue#toArray()
     * @param o the array into which the elements of this collection are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.
     * @return an array containing all the elements in this collection
     * @param <O> the component type of the array to contain the collection
     * @author Telami
     * @since 1.0.0
     */
    public <O> O[] toArray (final O[] o) {
        return null;
    }

    /**
     * @param t the element to add
     * @return always {@code true} since {@link ConcurrentOrderedQueue#offer(Object)} never fails
     * @author Telami
     * @since 1.0.0
     */
    public boolean add (final T t) {
        return false;
    }

    /**
     * @param o element to be removed from this collection, if present
     * @return {@code true} if an element was removed as a result of this call
     * @author Telami
     * @since 1.0.0
     */
    @PlannedForFuture(version = "1.1.0")
    public boolean remove (final Object o) {
        throw new UnsupportedOperationException("This method will be available in 1.1.0");
    }

    /**
     * @param c collection to be checked for containment in this collection
     * @return {@code true} if this collection contains all the elements in the specified collection
     * @author Telami
     * @since 1.0.0
     */
    public boolean containsAll (final Collection<?> c) {
        return false;
    }

    /**
     * @param c collection containing elements to be added to this collection
     * @return always {@code true} since {@link ConcurrentOrderedQueue#offer(Object)} never fails
     * @author Telami
     * @since 1.0.0
     */
    public boolean addAll (final Collection<? extends T> c) {
        return false;
    }

    /**
     * @param c collection containing elements to be removed from this collection
     * @return {@code true} if this collection changed as a result of the call
     * @author Telami
     * @since 1.0.0
     */
    @PlannedForFuture(version = "1.1.0")
    public boolean removeAll (final Collection<?> c) {
        return false;
    }

    /**
     * @param c collection containing elements to be retained in this collection
     * @return {@code true} if this collection changed as a result of the call
     * @author Telami
     * @since 1.0.0
     */
    @PlannedForFuture(version = "1.1.0")
    public boolean retainAll (final Collection<?> c) {
        return false;
    }

    /**
     * Removes all the elements from this collection. <br>
     * The collection will be empty after this method returns.
     * @author Telami
     * @since 1.0.0
     */
    public void clear () {}

    /**
     * @param t0 the element to add
     * @return always {@code true} (will never fail)
     * @author Telami
     * @since 1.0.0
     */
    public boolean offer (final T t0) {
        return false;
    }

    /**
     * @author Telami
     * @since 1.0.0
     * @return the lowest priority element.
     */
    public T remove () {
        return null;
    }

    /**
     * @author Telami
     * @since 1.0.0
     * @return the lowest priority element.
     */
    public T poll () {
        return null;
    }

    /**
     * @author Telami
     * @since 1.0.0
     * @return the lowest priority element.
     */
    public T element () {
        return null;
    }

    /**
     * @author Telami
     * @since 1.0.0
     * @return the lowest priority element.
     */
    public T peek () {
        return null;
    }
}
