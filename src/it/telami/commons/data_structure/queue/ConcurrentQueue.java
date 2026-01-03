package it.telami.commons.data_structure.queue;

import it.telami.commons.concurrency.thread.ContentionHandler;
import it.telami.commons.data_structure.DataStructure;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Spliterator;

public final class ConcurrentQueue<T> implements DataStructure, Queue<T> {
    public ConcurrentQueue (final ContentionHandler handler,
                            final boolean spinOnOffer) {
        //Hidden implementation...
    }
    public ConcurrentQueue (final ContentionHandler handler) {
        this(handler, false);
    }
    public ConcurrentQueue (final boolean spinOnOffer) {
        this(ContentionHandler.SMART, spinOnOffer);
    }
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
