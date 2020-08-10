package p043io.netty.util.internal;

import java.util.Iterator;

/* renamed from: io.netty.util.internal.ReadOnlyIterator */
public final class ReadOnlyIterator<T> implements Iterator<T> {
    private final Iterator<? extends T> iterator;

    public ReadOnlyIterator(Iterator<? extends T> it) {
        if (it != null) {
            this.iterator = it;
            return;
        }
        throw new NullPointerException("iterator");
    }

    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    public T next() {
        return this.iterator.next();
    }

    public void remove() {
        throw new UnsupportedOperationException("read-only");
    }
}
