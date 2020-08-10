package p043io.netty.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import p043io.netty.util.internal.StringUtil;
import p043io.reactivex.annotations.SchedulerSupport;

/* renamed from: io.netty.buffer.PoolChunkList */
final class PoolChunkList<T> implements PoolChunkListMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Iterator<PoolChunkMetric> EMPTY_METRICS = Collections.emptyList().iterator();
    private final PoolArena<T> arena;
    private PoolChunk<T> head;
    private final int maxCapacity;
    private final int maxUsage;
    private final int minUsage;
    private final PoolChunkList<T> nextList;
    private PoolChunkList<T> prevList;

    PoolChunkList(PoolArena<T> poolArena, PoolChunkList<T> poolChunkList, int i, int i2, int i3) {
        this.arena = poolArena;
        this.nextList = poolChunkList;
        this.minUsage = i;
        this.maxUsage = i2;
        this.maxCapacity = calculateMaxCapacity(i, i3);
    }

    private static int calculateMaxCapacity(int i, int i2) {
        int minUsage0 = minUsage0(i);
        if (minUsage0 == 100) {
            return 0;
        }
        return (int) ((((long) i2) * (100 - ((long) minUsage0))) / 100);
    }

    /* access modifiers changed from: 0000 */
    public void prevList(PoolChunkList<T> poolChunkList) {
        this.prevList = poolChunkList;
    }

    /* access modifiers changed from: 0000 */
    public boolean allocate(PooledByteBuf<T> pooledByteBuf, int i, int i2) {
        PoolChunk<T> poolChunk = this.head;
        if (poolChunk == null || i2 > this.maxCapacity) {
            return false;
        }
        do {
            long allocate = poolChunk.allocate(i2);
            if (allocate < 0) {
                poolChunk = poolChunk.next;
            } else {
                poolChunk.initBuf(pooledByteBuf, allocate, i);
                if (poolChunk.usage() >= this.maxUsage) {
                    remove(poolChunk);
                    this.nextList.add(poolChunk);
                }
                return true;
            }
        } while (poolChunk != null);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean free(PoolChunk<T> poolChunk, long j) {
        poolChunk.free(j);
        if (poolChunk.usage() >= this.minUsage) {
            return true;
        }
        remove(poolChunk);
        return move0(poolChunk);
    }

    private boolean move(PoolChunk<T> poolChunk) {
        if (poolChunk.usage() < this.minUsage) {
            return move0(poolChunk);
        }
        add0(poolChunk);
        return true;
    }

    private boolean move0(PoolChunk<T> poolChunk) {
        PoolChunkList<T> poolChunkList = this.prevList;
        if (poolChunkList == null) {
            return false;
        }
        return poolChunkList.move(poolChunk);
    }

    /* access modifiers changed from: 0000 */
    public void add(PoolChunk<T> poolChunk) {
        if (poolChunk.usage() >= this.maxUsage) {
            this.nextList.add(poolChunk);
        } else {
            add0(poolChunk);
        }
    }

    /* access modifiers changed from: 0000 */
    public void add0(PoolChunk<T> poolChunk) {
        poolChunk.parent = this;
        PoolChunk<T> poolChunk2 = this.head;
        if (poolChunk2 == null) {
            this.head = poolChunk;
            poolChunk.prev = null;
            poolChunk.next = null;
            return;
        }
        poolChunk.prev = null;
        poolChunk.next = poolChunk2;
        poolChunk2.prev = poolChunk;
        this.head = poolChunk;
    }

    private void remove(PoolChunk<T> poolChunk) {
        if (poolChunk == this.head) {
            this.head = poolChunk.next;
            PoolChunk<T> poolChunk2 = this.head;
            if (poolChunk2 != null) {
                poolChunk2.prev = null;
                return;
            }
            return;
        }
        PoolChunk<T> poolChunk3 = poolChunk.next;
        poolChunk.prev.next = poolChunk3;
        if (poolChunk3 != null) {
            poolChunk3.prev = poolChunk.prev;
        }
    }

    public int minUsage() {
        return minUsage0(this.minUsage);
    }

    public int maxUsage() {
        return Math.min(this.maxUsage, 100);
    }

    private static int minUsage0(int i) {
        return Math.max(1, i);
    }

    public Iterator<PoolChunkMetric> iterator() {
        synchronized (this.arena) {
            if (this.head == null) {
                Iterator<PoolChunkMetric> it = EMPTY_METRICS;
                return it;
            }
            ArrayList arrayList = new ArrayList();
            PoolChunk<T> poolChunk = this.head;
            do {
                arrayList.add(poolChunk);
                poolChunk = poolChunk.next;
            } while (poolChunk != null);
            Iterator<PoolChunkMetric> it2 = arrayList.iterator();
            return it2;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        synchronized (this.arena) {
            if (this.head == null) {
                String str = SchedulerSupport.NONE;
                return str;
            }
            PoolChunk<T> poolChunk = this.head;
            while (true) {
                sb.append(poolChunk);
                poolChunk = poolChunk.next;
                if (poolChunk == null) {
                    return sb.toString();
                }
                sb.append(StringUtil.NEWLINE);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void destroy(PoolArena<T> poolArena) {
        for (PoolChunk<T> poolChunk = this.head; poolChunk != null; poolChunk = poolChunk.next) {
            poolArena.destroyChunk(poolChunk);
        }
        this.head = null;
    }
}
