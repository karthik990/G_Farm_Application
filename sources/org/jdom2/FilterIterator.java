package org.jdom2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jdom2.filter.Filter;
import org.jdom2.util.IteratorIterable;

final class FilterIterator<T> implements IteratorIterable<T> {
    private boolean canremove = false;
    private final Filter<T> filter;
    private final DescendantIterator iterator;
    private T nextObject;

    public FilterIterator(DescendantIterator descendantIterator, Filter<T> filter2) {
        if (filter2 != null) {
            this.iterator = descendantIterator;
            this.filter = filter2;
            return;
        }
        throw new NullPointerException("Cannot specify a null Filter for a FilterIterator");
    }

    public Iterator<T> iterator() {
        return new FilterIterator(this.iterator.iterator(), this.filter);
    }

    public boolean hasNext() {
        this.canremove = false;
        if (this.nextObject != null) {
            return true;
        }
        while (this.iterator.hasNext()) {
            T filter2 = this.filter.filter((Object) this.iterator.next());
            if (filter2 != null) {
                this.nextObject = filter2;
                return true;
            }
        }
        return false;
    }

    public T next() {
        if (hasNext()) {
            T t = this.nextObject;
            this.nextObject = null;
            this.canremove = true;
            return t;
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        if (this.canremove) {
            this.canremove = false;
            this.iterator.remove();
            return;
        }
        throw new IllegalStateException("remove() can only be called on the FilterIterator immediately after a successful call to next(). A call to remove() immediately after a call to hasNext() or remove() will also fail.");
    }
}
