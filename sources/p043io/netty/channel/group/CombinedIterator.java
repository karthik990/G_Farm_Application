package p043io.netty.channel.group;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: io.netty.channel.group.CombinedIterator */
final class CombinedIterator<E> implements Iterator<E> {
    private Iterator<E> currentIterator;

    /* renamed from: i1 */
    private final Iterator<E> f3701i1;

    /* renamed from: i2 */
    private final Iterator<E> f3702i2;

    CombinedIterator(Iterator<E> it, Iterator<E> it2) {
        if (it == null) {
            throw new NullPointerException("i1");
        } else if (it2 != null) {
            this.f3701i1 = it;
            this.f3702i2 = it2;
            this.currentIterator = it;
        } else {
            throw new NullPointerException("i2");
        }
    }

    public boolean hasNext() {
        while (!this.currentIterator.hasNext()) {
            if (this.currentIterator != this.f3701i1) {
                return false;
            }
            this.currentIterator = this.f3702i2;
        }
        return true;
    }

    public E next() {
        while (true) {
            try {
                return this.currentIterator.next();
            } catch (NoSuchElementException e) {
                if (this.currentIterator == this.f3701i1) {
                    this.currentIterator = this.f3702i2;
                } else {
                    throw e;
                }
            }
        }
    }

    public void remove() {
        this.currentIterator.remove();
    }
}
