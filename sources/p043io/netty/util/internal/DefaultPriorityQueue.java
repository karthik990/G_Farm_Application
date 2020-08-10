package p043io.netty.util.internal;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import p043io.netty.util.internal.PriorityQueueNode;

/* renamed from: io.netty.util.internal.DefaultPriorityQueue */
public final class DefaultPriorityQueue<T extends PriorityQueueNode> extends AbstractQueue<T> implements PriorityQueue<T> {
    private static final PriorityQueueNode[] EMPTY_ARRAY = new PriorityQueueNode[0];
    private final Comparator<T> comparator;
    /* access modifiers changed from: private */
    public T[] queue;
    /* access modifiers changed from: private */
    public int size;

    /* renamed from: io.netty.util.internal.DefaultPriorityQueue$PriorityQueueIterator */
    private final class PriorityQueueIterator implements Iterator<T> {
        private int index;

        private PriorityQueueIterator() {
        }

        public boolean hasNext() {
            return this.index < DefaultPriorityQueue.this.size;
        }

        public T next() {
            if (this.index < DefaultPriorityQueue.this.size) {
                T[] access$200 = DefaultPriorityQueue.this.queue;
                int i = this.index;
                this.index = i + 1;
                return access$200[i];
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException(ProductAction.ACTION_REMOVE);
        }
    }

    public DefaultPriorityQueue(Comparator<T> comparator2, int i) {
        this.comparator = (Comparator) ObjectUtil.checkNotNull(comparator2, "comparator");
        this.queue = (PriorityQueueNode[]) (i != 0 ? new PriorityQueueNode[i] : EMPTY_ARRAY);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean contains(Object obj) {
        if (!(obj instanceof PriorityQueueNode)) {
            return false;
        }
        PriorityQueueNode priorityQueueNode = (PriorityQueueNode) obj;
        return contains(priorityQueueNode, priorityQueueNode.priorityQueueIndex(this));
    }

    public boolean containsTyped(T t) {
        return contains(t, t.priorityQueueIndex(this));
    }

    public void clear() {
        for (int i = 0; i < this.size; i++) {
            T t = this.queue[i];
            if (t != null) {
                t.priorityQueueIndex(this, -1);
                this.queue[i] = null;
            }
        }
        this.size = 0;
    }

    public boolean offer(T t) {
        if (t.priorityQueueIndex(this) == -1) {
            int i = this.size;
            T[] tArr = this.queue;
            if (i >= tArr.length) {
                this.queue = (PriorityQueueNode[]) Arrays.copyOf(tArr, tArr.length + (tArr.length < 64 ? tArr.length + 2 : tArr.length >>> 1));
            }
            int i2 = this.size;
            this.size = i2 + 1;
            bubbleUp(i2, t);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("e.priorityQueueIndex(): ");
        sb.append(t.priorityQueueIndex(this));
        sb.append(" (expected: ");
        sb.append(-1);
        sb.append(") + e: ");
        sb.append(t);
        throw new IllegalArgumentException(sb.toString());
    }

    public T poll() {
        if (this.size == 0) {
            return null;
        }
        T t = this.queue[0];
        t.priorityQueueIndex(this, -1);
        T[] tArr = this.queue;
        int i = this.size - 1;
        this.size = i;
        T t2 = tArr[i];
        int i2 = this.size;
        tArr[i2] = null;
        if (i2 != 0) {
            bubbleDown(0, t2);
        }
        return t;
    }

    public T peek() {
        if (this.size == 0) {
            return null;
        }
        return this.queue[0];
    }

    public boolean remove(Object obj) {
        try {
            return removeTyped((T) (PriorityQueueNode) obj);
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public boolean removeTyped(T t) {
        int priorityQueueIndex = t.priorityQueueIndex(this);
        if (!contains(t, priorityQueueIndex)) {
            return false;
        }
        t.priorityQueueIndex(this, -1);
        int i = this.size - 1;
        this.size = i;
        if (i != 0) {
            int i2 = this.size;
            if (i2 != priorityQueueIndex) {
                T[] tArr = this.queue;
                T t2 = tArr[i2];
                tArr[priorityQueueIndex] = t2;
                tArr[i2] = null;
                if (this.comparator.compare(t, t2) < 0) {
                    bubbleDown(priorityQueueIndex, t2);
                } else {
                    bubbleUp(priorityQueueIndex, t2);
                }
                return true;
            }
        }
        this.queue[priorityQueueIndex] = null;
        return true;
    }

    public void priorityChanged(T t) {
        int priorityQueueIndex = t.priorityQueueIndex(this);
        if (contains(t, priorityQueueIndex)) {
            if (priorityQueueIndex == 0) {
                bubbleDown(priorityQueueIndex, t);
            } else {
                if (this.comparator.compare(t, this.queue[(priorityQueueIndex - 1) >>> 1]) < 0) {
                    bubbleUp(priorityQueueIndex, t);
                } else {
                    bubbleDown(priorityQueueIndex, t);
                }
            }
        }
    }

    public Object[] toArray() {
        return Arrays.copyOf(this.queue, this.size);
    }

    public <X> X[] toArray(X[] xArr) {
        int length = xArr.length;
        int i = this.size;
        if (length < i) {
            return Arrays.copyOf(this.queue, i, xArr.getClass());
        }
        System.arraycopy(this.queue, 0, xArr, 0, i);
        int length2 = xArr.length;
        int i2 = this.size;
        if (length2 > i2) {
            xArr[i2] = null;
        }
        return xArr;
    }

    public Iterator<T> iterator() {
        return new PriorityQueueIterator();
    }

    private boolean contains(PriorityQueueNode priorityQueueNode, int i) {
        return i >= 0 && i < this.size && priorityQueueNode.equals(this.queue[i]);
    }

    private void bubbleDown(int i, T t) {
        int i2 = this.size >>> 1;
        while (i < i2) {
            int i3 = (i << 1) + 1;
            T[] tArr = this.queue;
            T t2 = tArr[i3];
            int i4 = i3 + 1;
            if (i4 < this.size && this.comparator.compare(t2, tArr[i4]) > 0) {
                t2 = this.queue[i4];
                i3 = i4;
            }
            if (this.comparator.compare(t, t2) <= 0) {
                break;
            }
            this.queue[i] = t2;
            t2.priorityQueueIndex(this, i);
            i = i3;
        }
        this.queue[i] = t;
        t.priorityQueueIndex(this, i);
    }

    private void bubbleUp(int i, T t) {
        while (i > 0) {
            int i2 = (i - 1) >>> 1;
            T t2 = this.queue[i2];
            if (this.comparator.compare(t, t2) >= 0) {
                break;
            }
            this.queue[i] = t2;
            t2.priorityQueueIndex(this, i);
            i = i2;
        }
        this.queue[i] = t;
        t.priorityQueueIndex(this, i);
    }
}
