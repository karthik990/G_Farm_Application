package p043io.netty.util.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: io.netty.util.internal.EmptyPriorityQueue */
public final class EmptyPriorityQueue<T> implements PriorityQueue<T> {
    private static final PriorityQueue<Object> INSTANCE = new EmptyPriorityQueue();

    public boolean add(T t) {
        return false;
    }

    public boolean addAll(Collection<? extends T> collection) {
        return false;
    }

    public void clear() {
    }

    public boolean contains(Object obj) {
        return false;
    }

    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    public boolean containsTyped(T t) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean offer(T t) {
        return false;
    }

    public T peek() {
        return null;
    }

    public T poll() {
        return null;
    }

    public void priorityChanged(T t) {
    }

    public boolean remove(Object obj) {
        return false;
    }

    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    public boolean removeTyped(T t) {
        return false;
    }

    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    public int size() {
        return 0;
    }

    private EmptyPriorityQueue() {
    }

    public static <V> EmptyPriorityQueue<V> instance() {
        return (EmptyPriorityQueue) INSTANCE;
    }

    public Iterator<T> iterator() {
        return Collections.emptyList().iterator();
    }

    public Object[] toArray() {
        return EmptyArrays.EMPTY_OBJECTS;
    }

    public <T1> T1[] toArray(T1[] t1Arr) {
        if (t1Arr.length > 0) {
            t1Arr[0] = null;
        }
        return t1Arr;
    }

    public boolean equals(Object obj) {
        return (obj instanceof PriorityQueue) && ((PriorityQueue) obj).isEmpty();
    }

    public T remove() {
        throw new NoSuchElementException();
    }

    public T element() {
        throw new NoSuchElementException();
    }

    public String toString() {
        return EmptyPriorityQueue.class.getSimpleName();
    }
}
