package p043io.netty.buffer;

import com.fasterxml.jackson.core.JsonPointer;

/* renamed from: io.netty.buffer.PoolChunk */
final class PoolChunk<T> implements PoolChunkMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INTEGER_SIZE_MINUS_ONE = 31;
    final PoolArena<T> arena;
    private final int chunkSize;
    private final byte[] depthMap;
    private int freeBytes;
    private final int log2ChunkSize;
    private final int maxOrder;
    private final int maxSubpageAllocs;
    final T memory;
    private final byte[] memoryMap;
    PoolChunk<T> next;
    final int offset;
    private final int pageShifts;
    private final int pageSize;
    PoolChunkList<T> parent;
    PoolChunk<T> prev;
    private final int subpageOverflowMask;
    private final PoolSubpage<T>[] subpages;
    final boolean unpooled = true;
    private final byte unusable;

    private static int bitmapIdx(long j) {
        return (int) (j >>> 32);
    }

    private static int memoryMapIdx(long j) {
        return (int) j;
    }

    PoolChunk(PoolArena<T> poolArena, T t, int i, int i2, int i3, int i4, int i5) {
        this.arena = poolArena;
        this.memory = t;
        this.pageSize = i;
        this.pageShifts = i3;
        this.maxOrder = i2;
        this.chunkSize = i4;
        this.offset = i5;
        this.unusable = (byte) (i2 + 1);
        this.log2ChunkSize = log2(i4);
        this.subpageOverflowMask = (i - 1) ^ -1;
        this.freeBytes = i4;
        this.maxSubpageAllocs = 1 << i2;
        this.memoryMap = new byte[(this.maxSubpageAllocs << 1)];
        this.depthMap = new byte[this.memoryMap.length];
        int i6 = 0;
        int i7 = 1;
        while (i6 <= i2) {
            int i8 = 1 << i6;
            int i9 = i7;
            for (int i10 = 0; i10 < i8; i10++) {
                byte b = (byte) i6;
                this.memoryMap[i9] = b;
                this.depthMap[i9] = b;
                i9++;
            }
            i6++;
            i7 = i9;
        }
        this.subpages = newSubpageArray(this.maxSubpageAllocs);
    }

    PoolChunk(PoolArena<T> poolArena, T t, int i, int i2) {
        this.arena = poolArena;
        this.memory = t;
        this.offset = i2;
        this.memoryMap = null;
        this.depthMap = null;
        this.subpages = null;
        this.subpageOverflowMask = 0;
        this.pageSize = 0;
        this.pageShifts = 0;
        this.maxOrder = 0;
        this.unusable = (byte) (this.maxOrder + 1);
        this.chunkSize = i;
        this.log2ChunkSize = log2(this.chunkSize);
        this.maxSubpageAllocs = 0;
    }

    private PoolSubpage<T>[] newSubpageArray(int i) {
        return new PoolSubpage[i];
    }

    public int usage() {
        int i;
        synchronized (this.arena) {
            i = this.freeBytes;
        }
        return usage(i);
    }

    private int usage(int i) {
        if (i == 0) {
            return 100;
        }
        int i2 = (int) ((((long) i) * 100) / ((long) this.chunkSize));
        if (i2 == 0) {
            return 99;
        }
        return 100 - i2;
    }

    /* access modifiers changed from: 0000 */
    public long allocate(int i) {
        if ((this.subpageOverflowMask & i) != 0) {
            return allocateRun(i);
        }
        return allocateSubpage(i);
    }

    private void updateParentsAlloc(int i) {
        while (i > 1) {
            int i2 = i >>> 1;
            byte value = value(i);
            byte value2 = value(i ^ 1);
            if (value < value2) {
                value2 = value;
            }
            setValue(i2, value2);
            i = i2;
        }
    }

    private void updateParentsFree(int i) {
        int depth = depth(i) + 1;
        while (i > 1) {
            int i2 = i >>> 1;
            int value = value(i);
            byte value2 = value(i ^ 1);
            depth--;
            if (value == depth && value2 == depth) {
                setValue(i2, (byte) (depth - 1));
            } else {
                if (value < value2) {
                    value2 = value;
                }
                setValue(i2, value2);
            }
            i = i2;
        }
    }

    private int allocateNode(int i) {
        int i2 = 1;
        int i3 = -(1 << i);
        byte value = value(1);
        if (value > i) {
            return -1;
        }
        while (true) {
            if (value < i || (i2 & i3) == 0) {
                i2 <<= 1;
                value = value(i2);
                if (value > i) {
                    i2 ^= 1;
                    value = value(i2);
                }
            } else {
                value(i2);
                setValue(i2, this.unusable);
                updateParentsAlloc(i2);
                return i2;
            }
        }
    }

    private long allocateRun(int i) {
        int allocateNode = allocateNode(this.maxOrder - (log2(i) - this.pageShifts));
        if (allocateNode < 0) {
            return (long) allocateNode;
        }
        this.freeBytes -= runLength(allocateNode);
        return (long) allocateNode;
    }

    private long allocateSubpage(int i) {
        PoolSubpage findSubpagePoolHead = this.arena.findSubpagePoolHead(i);
        synchronized (findSubpagePoolHead) {
            int allocateNode = allocateNode(this.maxOrder);
            if (allocateNode < 0) {
                long j = (long) allocateNode;
                return j;
            }
            PoolSubpage<T>[] poolSubpageArr = this.subpages;
            int i2 = this.pageSize;
            this.freeBytes -= i2;
            int subpageIdx = subpageIdx(allocateNode);
            PoolSubpage<T> poolSubpage = poolSubpageArr[subpageIdx];
            if (poolSubpage == null) {
                PoolSubpage<T> poolSubpage2 = new PoolSubpage<>(findSubpagePoolHead, this, allocateNode, runOffset(allocateNode), i2, i);
                poolSubpageArr[subpageIdx] = poolSubpage2;
                poolSubpage = poolSubpage2;
            } else {
                poolSubpage.init(findSubpagePoolHead, i);
            }
            long allocate = poolSubpage.allocate();
            return allocate;
        }
    }

    /* access modifiers changed from: 0000 */
    public void free(long j) {
        int memoryMapIdx = memoryMapIdx(j);
        int bitmapIdx = bitmapIdx(j);
        if (bitmapIdx != 0) {
            PoolSubpage<T> poolSubpage = this.subpages[subpageIdx(memoryMapIdx)];
            PoolSubpage findSubpagePoolHead = this.arena.findSubpagePoolHead(poolSubpage.elemSize);
            synchronized (findSubpagePoolHead) {
                if (poolSubpage.free(findSubpagePoolHead, bitmapIdx & 1073741823)) {
                    return;
                }
            }
        }
        this.freeBytes += runLength(memoryMapIdx);
        setValue(memoryMapIdx, depth(memoryMapIdx));
        updateParentsFree(memoryMapIdx);
    }

    /* access modifiers changed from: 0000 */
    public void initBuf(PooledByteBuf<T> pooledByteBuf, long j, int i) {
        int memoryMapIdx = memoryMapIdx(j);
        int bitmapIdx = bitmapIdx(j);
        if (bitmapIdx == 0) {
            value(memoryMapIdx);
            pooledByteBuf.init(this, j, runOffset(memoryMapIdx) + this.offset, i, runLength(memoryMapIdx), this.arena.parent.threadCache());
            return;
        }
        initBufWithSubpage(pooledByteBuf, j, bitmapIdx, i);
    }

    /* access modifiers changed from: 0000 */
    public void initBufWithSubpage(PooledByteBuf<T> pooledByteBuf, long j, int i) {
        initBufWithSubpage(pooledByteBuf, j, bitmapIdx(j), i);
    }

    private void initBufWithSubpage(PooledByteBuf<T> pooledByteBuf, long j, int i, int i2) {
        int memoryMapIdx = memoryMapIdx(j);
        PoolSubpage<T> poolSubpage = this.subpages[subpageIdx(memoryMapIdx)];
        pooledByteBuf.init(this, j, runOffset(memoryMapIdx) + ((i & 1073741823) * poolSubpage.elemSize) + this.offset, i2, poolSubpage.elemSize, this.arena.parent.threadCache());
    }

    private byte value(int i) {
        return this.memoryMap[i];
    }

    private void setValue(int i, byte b) {
        this.memoryMap[i] = b;
    }

    private byte depth(int i) {
        return this.depthMap[i];
    }

    private static int log2(int i) {
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    private int runLength(int i) {
        return 1 << (this.log2ChunkSize - depth(i));
    }

    private int runOffset(int i) {
        return ((1 << depth(i)) ^ i) * runLength(i);
    }

    private int subpageIdx(int i) {
        return i ^ this.maxSubpageAllocs;
    }

    public int chunkSize() {
        return this.chunkSize;
    }

    public int freeBytes() {
        int i;
        synchronized (this.arena) {
            i = this.freeBytes;
        }
        return i;
    }

    public String toString() {
        int i;
        synchronized (this.arena) {
            i = this.freeBytes;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Chunk(");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(": ");
        sb.append(usage(i));
        sb.append("%, ");
        sb.append(this.chunkSize - i);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(this.chunkSize);
        sb.append(')');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void destroy() {
        this.arena.destroyChunk(this);
    }
}
