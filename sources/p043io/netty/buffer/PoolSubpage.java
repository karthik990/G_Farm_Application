package p043io.netty.buffer;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.primitives.Longs;

/* renamed from: io.netty.buffer.PoolSubpage */
final class PoolSubpage<T> implements PoolSubpageMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final long[] bitmap;
    private int bitmapLength;
    final PoolChunk<T> chunk;
    boolean doNotDestroy;
    int elemSize;
    private int maxNumElems;
    private final int memoryMapIdx;
    PoolSubpage<T> next;
    private int nextAvail;
    private int numAvail;
    private final int pageSize;
    PoolSubpage<T> prev;
    private final int runOffset;

    PoolSubpage(int i) {
        this.chunk = null;
        this.memoryMapIdx = -1;
        this.runOffset = -1;
        this.elemSize = -1;
        this.pageSize = i;
        this.bitmap = null;
    }

    PoolSubpage(PoolSubpage<T> poolSubpage, PoolChunk<T> poolChunk, int i, int i2, int i3, int i4) {
        this.chunk = poolChunk;
        this.memoryMapIdx = i;
        this.runOffset = i2;
        this.pageSize = i3;
        this.bitmap = new long[(i3 >>> 10)];
        init(poolSubpage, i4);
    }

    /* access modifiers changed from: 0000 */
    public void init(PoolSubpage<T> poolSubpage, int i) {
        this.doNotDestroy = true;
        this.elemSize = i;
        if (i != 0) {
            int i2 = this.pageSize / i;
            this.numAvail = i2;
            this.maxNumElems = i2;
            this.nextAvail = 0;
            int i3 = this.maxNumElems;
            this.bitmapLength = i3 >>> 6;
            if ((i3 & 63) != 0) {
                this.bitmapLength++;
            }
            for (int i4 = 0; i4 < this.bitmapLength; i4++) {
                this.bitmap[i4] = 0;
            }
        }
        addToPool(poolSubpage);
    }

    /* access modifiers changed from: 0000 */
    public long allocate() {
        if (this.elemSize == 0) {
            return toHandle(0);
        }
        if (this.numAvail == 0 || !this.doNotDestroy) {
            return -1;
        }
        int nextAvail2 = getNextAvail();
        int i = nextAvail2 >>> 6;
        int i2 = nextAvail2 & 63;
        long[] jArr = this.bitmap;
        jArr[i] = jArr[i] | (1 << i2);
        int i3 = this.numAvail - 1;
        this.numAvail = i3;
        if (i3 == 0) {
            removeFromPool();
        }
        return toHandle(nextAvail2);
    }

    /* access modifiers changed from: 0000 */
    public boolean free(PoolSubpage<T> poolSubpage, int i) {
        if (this.elemSize == 0) {
            return true;
        }
        int i2 = i >>> 6;
        int i3 = i & 63;
        long[] jArr = this.bitmap;
        jArr[i2] = jArr[i2] ^ (1 << i3);
        setNextAvail(i);
        int i4 = this.numAvail;
        this.numAvail = i4 + 1;
        if (i4 == 0) {
            addToPool(poolSubpage);
            return true;
        } else if (this.numAvail != this.maxNumElems || this.prev == this.next) {
            return true;
        } else {
            this.doNotDestroy = false;
            removeFromPool();
            return false;
        }
    }

    private void addToPool(PoolSubpage<T> poolSubpage) {
        this.prev = poolSubpage;
        this.next = poolSubpage.next;
        this.next.prev = this;
        poolSubpage.next = this;
    }

    private void removeFromPool() {
        PoolSubpage<T> poolSubpage = this.prev;
        poolSubpage.next = this.next;
        this.next.prev = poolSubpage;
        this.next = null;
        this.prev = null;
    }

    private void setNextAvail(int i) {
        this.nextAvail = i;
    }

    private int getNextAvail() {
        int i = this.nextAvail;
        if (i < 0) {
            return findNextAvail();
        }
        this.nextAvail = -1;
        return i;
    }

    private int findNextAvail() {
        long[] jArr = this.bitmap;
        int i = this.bitmapLength;
        for (int i2 = 0; i2 < i; i2++) {
            long j = jArr[i2];
            if ((-1 ^ j) != 0) {
                return findNextAvail0(i2, j);
            }
        }
        return -1;
    }

    private int findNextAvail0(int i, long j) {
        int i2 = this.maxNumElems;
        int i3 = i << 6;
        int i4 = 0;
        while (true) {
            if (i4 >= 64) {
                break;
            } else if ((1 & j) == 0) {
                int i5 = i3 | i4;
                if (i5 < i2) {
                    return i5;
                }
            } else {
                j >>>= 1;
                i4++;
            }
        }
        return -1;
    }

    private long toHandle(int i) {
        return (((long) i) << 32) | Longs.MAX_POWER_OF_TWO | ((long) this.memoryMapIdx);
    }

    public String toString() {
        int i;
        int i2;
        int i3;
        boolean z;
        synchronized (this.chunk.arena) {
            if (!this.doNotDestroy) {
                z = false;
                i3 = -1;
                i2 = -1;
                i = -1;
            } else {
                z = true;
                i3 = this.maxNumElems;
                i2 = this.numAvail;
                i = this.elemSize;
            }
        }
        if (!z) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            sb.append(this.memoryMapIdx);
            sb.append(": not in use)");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("(");
        sb2.append(this.memoryMapIdx);
        sb2.append(": ");
        sb2.append(i3 - i2);
        sb2.append(JsonPointer.SEPARATOR);
        sb2.append(i3);
        sb2.append(", offset: ");
        sb2.append(this.runOffset);
        sb2.append(", length: ");
        sb2.append(this.pageSize);
        sb2.append(", elemSize: ");
        sb2.append(i);
        sb2.append(')');
        return sb2.toString();
    }

    public int maxNumElements() {
        int i;
        synchronized (this.chunk.arena) {
            i = this.maxNumElems;
        }
        return i;
    }

    public int numAvailable() {
        int i;
        synchronized (this.chunk.arena) {
            i = this.numAvail;
        }
        return i;
    }

    public int elementSize() {
        int i;
        synchronized (this.chunk.arena) {
            i = this.elemSize;
        }
        return i;
    }

    public int pageSize() {
        return this.pageSize;
    }

    /* access modifiers changed from: 0000 */
    public void destroy() {
        PoolChunk<T> poolChunk = this.chunk;
        if (poolChunk != null) {
            poolChunk.destroy();
        }
    }
}
