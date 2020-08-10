package p043io.netty.buffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.util.internal.LongCounter;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.buffer.PoolArena */
abstract class PoolArena<T> implements PoolArenaMetric {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean HAS_UNSAFE = PlatformDependent.hasUnsafe();
    static final int numTinySubpagePools = 32;
    private final LongCounter activeBytesHuge = PlatformDependent.newLongCounter();
    private final LongCounter allocationsHuge = PlatformDependent.newLongCounter();
    private long allocationsNormal;
    private final LongCounter allocationsSmall = PlatformDependent.newLongCounter();
    private final LongCounter allocationsTiny = PlatformDependent.newLongCounter();
    private final List<PoolChunkListMetric> chunkListMetrics;
    final int chunkSize;
    private final LongCounter deallocationsHuge = PlatformDependent.newLongCounter();
    private long deallocationsNormal;
    private long deallocationsSmall;
    private long deallocationsTiny;
    final int directMemoryCacheAlignment;
    final int directMemoryCacheAlignmentMask;
    private final int maxOrder;
    final int numSmallSubpagePools;
    final AtomicInteger numThreadCaches = new AtomicInteger();
    final int pageShifts;
    final int pageSize;
    final PooledByteBufAllocator parent;
    private final PoolChunkList<T> q000;
    private final PoolChunkList<T> q025;
    private final PoolChunkList<T> q050;
    private final PoolChunkList<T> q075;
    private final PoolChunkList<T> q100;
    private final PoolChunkList<T> qInit;
    private final PoolSubpage<T>[] smallSubpagePools;
    final int subpageOverflowMask;
    private final PoolSubpage<T>[] tinySubpagePools;

    /* renamed from: io.netty.buffer.PoolArena$1 */
    static /* synthetic */ class C55411 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$buffer$PoolArena$SizeClass = new int[SizeClass.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                io.netty.buffer.PoolArena$SizeClass[] r0 = p043io.netty.buffer.PoolArena.SizeClass.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$buffer$PoolArena$SizeClass = r0
                int[] r0 = $SwitchMap$io$netty$buffer$PoolArena$SizeClass     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.buffer.PoolArena$SizeClass r1 = p043io.netty.buffer.PoolArena.SizeClass.Normal     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$buffer$PoolArena$SizeClass     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.buffer.PoolArena$SizeClass r1 = p043io.netty.buffer.PoolArena.SizeClass.Small     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$buffer$PoolArena$SizeClass     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.buffer.PoolArena$SizeClass r1 = p043io.netty.buffer.PoolArena.SizeClass.Tiny     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.PoolArena.C55411.<clinit>():void");
        }
    }

    /* renamed from: io.netty.buffer.PoolArena$DirectArena */
    static final class DirectArena extends PoolArena<ByteBuffer> {
        /* access modifiers changed from: 0000 */
        public boolean isDirect() {
            return true;
        }

        DirectArena(PooledByteBufAllocator pooledByteBufAllocator, int i, int i2, int i3, int i4, int i5) {
            super(pooledByteBufAllocator, i, i2, i3, i4, i5);
        }

        private int offsetCacheLine(ByteBuffer byteBuffer) {
            if (HAS_UNSAFE) {
                return (int) (PlatformDependent.directBufferAddress(byteBuffer) & ((long) this.directMemoryCacheAlignmentMask));
            }
            return 0;
        }

        /* access modifiers changed from: protected */
        public PoolChunk<ByteBuffer> newChunk(int i, int i2, int i3, int i4) {
            if (this.directMemoryCacheAlignment == 0) {
                PoolChunk poolChunk = new PoolChunk(this, allocateDirect(i4), i, i2, i3, i4, 0);
                return poolChunk;
            }
            ByteBuffer allocateDirect = allocateDirect(this.directMemoryCacheAlignment + i4);
            PoolChunk poolChunk2 = new PoolChunk(this, allocateDirect, i, i2, i3, i4, offsetCacheLine(allocateDirect));
            return poolChunk2;
        }

        /* access modifiers changed from: protected */
        public PoolChunk<ByteBuffer> newUnpooledChunk(int i) {
            if (this.directMemoryCacheAlignment == 0) {
                return new PoolChunk<>(this, allocateDirect(i), i, 0);
            }
            ByteBuffer allocateDirect = allocateDirect(this.directMemoryCacheAlignment + i);
            return new PoolChunk<>(this, allocateDirect, i, offsetCacheLine(allocateDirect));
        }

        private static ByteBuffer allocateDirect(int i) {
            return PlatformDependent.useDirectBufferNoCleaner() ? PlatformDependent.allocateDirectNoCleaner(i) : ByteBuffer.allocateDirect(i);
        }

        /* access modifiers changed from: protected */
        public void destroyChunk(PoolChunk<ByteBuffer> poolChunk) {
            if (PlatformDependent.useDirectBufferNoCleaner()) {
                PlatformDependent.freeDirectNoCleaner((ByteBuffer) poolChunk.memory);
            } else {
                PlatformDependent.freeDirectBuffer((ByteBuffer) poolChunk.memory);
            }
        }

        /* access modifiers changed from: protected */
        public PooledByteBuf<ByteBuffer> newByteBuf(int i) {
            if (HAS_UNSAFE) {
                return PooledUnsafeDirectByteBuf.newInstance(i);
            }
            return PooledDirectByteBuf.newInstance(i);
        }

        /* access modifiers changed from: protected */
        public void memoryCopy(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
            if (i3 != 0) {
                if (HAS_UNSAFE) {
                    PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(byteBuffer) + ((long) i), PlatformDependent.directBufferAddress(byteBuffer2) + ((long) i2), (long) i3);
                } else {
                    ByteBuffer duplicate = byteBuffer.duplicate();
                    ByteBuffer duplicate2 = byteBuffer2.duplicate();
                    duplicate.position(i).limit(i + i3);
                    duplicate2.position(i2);
                    duplicate2.put(duplicate);
                }
            }
        }
    }

    /* renamed from: io.netty.buffer.PoolArena$HeapArena */
    static final class HeapArena extends PoolArena<byte[]> {
        /* access modifiers changed from: protected */
        public void destroyChunk(PoolChunk<byte[]> poolChunk) {
        }

        /* access modifiers changed from: 0000 */
        public boolean isDirect() {
            return false;
        }

        HeapArena(PooledByteBufAllocator pooledByteBufAllocator, int i, int i2, int i3, int i4, int i5) {
            super(pooledByteBufAllocator, i, i2, i3, i4, i5);
        }

        private static byte[] newByteArray(int i) {
            return PlatformDependent.allocateUninitializedArray(i);
        }

        /* access modifiers changed from: protected */
        public PoolChunk<byte[]> newChunk(int i, int i2, int i3, int i4) {
            PoolChunk poolChunk = new PoolChunk(this, newByteArray(i4), i, i2, i3, i4, 0);
            return poolChunk;
        }

        /* access modifiers changed from: protected */
        public PoolChunk<byte[]> newUnpooledChunk(int i) {
            return new PoolChunk<>(this, newByteArray(i), i, 0);
        }

        /* access modifiers changed from: protected */
        public PooledByteBuf<byte[]> newByteBuf(int i) {
            if (HAS_UNSAFE) {
                return PooledUnsafeHeapByteBuf.newUnsafeInstance(i);
            }
            return PooledHeapByteBuf.newInstance(i);
        }

        /* access modifiers changed from: protected */
        public void memoryCopy(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
            if (i3 != 0) {
                System.arraycopy(bArr, i, bArr2, i2, i3);
            }
        }
    }

    /* renamed from: io.netty.buffer.PoolArena$SizeClass */
    enum SizeClass {
        Tiny,
        Small,
        Normal
    }

    static boolean isTiny(int i) {
        return (i & -512) == 0;
    }

    static int smallIdx(int i) {
        int i2 = i >>> 10;
        int i3 = 0;
        while (i2 != 0) {
            i2 >>>= 1;
            i3++;
        }
        return i3;
    }

    static int tinyIdx(int i) {
        return i >>> 4;
    }

    /* access modifiers changed from: protected */
    public abstract void destroyChunk(PoolChunk<T> poolChunk);

    /* access modifiers changed from: 0000 */
    public abstract boolean isDirect();

    /* access modifiers changed from: protected */
    public abstract void memoryCopy(T t, int i, T t2, int i2, int i3);

    /* access modifiers changed from: protected */
    public abstract PooledByteBuf<T> newByteBuf(int i);

    /* access modifiers changed from: protected */
    public abstract PoolChunk<T> newChunk(int i, int i2, int i3, int i4);

    /* access modifiers changed from: protected */
    public abstract PoolChunk<T> newUnpooledChunk(int i);

    protected PoolArena(PooledByteBufAllocator pooledByteBufAllocator, int i, int i2, int i3, int i4, int i5) {
        this.parent = pooledByteBufAllocator;
        this.pageSize = i;
        this.maxOrder = i2;
        this.pageShifts = i3;
        this.chunkSize = i4;
        this.directMemoryCacheAlignment = i5;
        this.directMemoryCacheAlignmentMask = i5 - 1;
        this.subpageOverflowMask = (i - 1) ^ -1;
        this.tinySubpagePools = newSubpagePoolArray(32);
        int i6 = 0;
        int i7 = 0;
        while (true) {
            PoolSubpage<T>[] poolSubpageArr = this.tinySubpagePools;
            if (i7 >= poolSubpageArr.length) {
                break;
            }
            poolSubpageArr[i7] = newSubpagePoolHead(i);
            i7++;
        }
        this.numSmallSubpagePools = i3 - 9;
        this.smallSubpagePools = newSubpagePoolArray(this.numSmallSubpagePools);
        while (true) {
            PoolSubpage<T>[] poolSubpageArr2 = this.smallSubpagePools;
            if (i6 < poolSubpageArr2.length) {
                poolSubpageArr2[i6] = newSubpagePoolHead(i);
                i6++;
            } else {
                int i8 = i4;
                PoolChunkList poolChunkList = new PoolChunkList(this, null, 100, Integer.MAX_VALUE, i8);
                this.q100 = poolChunkList;
                PoolChunkList poolChunkList2 = new PoolChunkList(this, this.q100, 75, 100, i8);
                this.q075 = poolChunkList2;
                PoolChunkList poolChunkList3 = new PoolChunkList(this, this.q075, 50, 100, i8);
                this.q050 = poolChunkList3;
                PoolChunkList poolChunkList4 = new PoolChunkList(this, this.q050, 25, 75, i8);
                this.q025 = poolChunkList4;
                PoolChunkList poolChunkList5 = new PoolChunkList(this, this.q025, 1, 50, i8);
                this.q000 = poolChunkList5;
                PoolChunkList poolChunkList6 = new PoolChunkList(this, this.q000, Integer.MIN_VALUE, 25, i8);
                this.qInit = poolChunkList6;
                this.q100.prevList(this.q075);
                this.q075.prevList(this.q050);
                this.q050.prevList(this.q025);
                this.q025.prevList(this.q000);
                this.q000.prevList(null);
                PoolChunkList<T> poolChunkList7 = this.qInit;
                poolChunkList7.prevList(poolChunkList7);
                ArrayList arrayList = new ArrayList(6);
                arrayList.add(this.qInit);
                arrayList.add(this.q000);
                arrayList.add(this.q025);
                arrayList.add(this.q050);
                arrayList.add(this.q075);
                arrayList.add(this.q100);
                this.chunkListMetrics = Collections.unmodifiableList(arrayList);
                return;
            }
        }
    }

    private PoolSubpage<T> newSubpagePoolHead(int i) {
        PoolSubpage<T> poolSubpage = new PoolSubpage<>(i);
        poolSubpage.prev = poolSubpage;
        poolSubpage.next = poolSubpage;
        return poolSubpage;
    }

    private PoolSubpage<T>[] newSubpagePoolArray(int i) {
        return new PoolSubpage[i];
    }

    /* access modifiers changed from: 0000 */
    public PooledByteBuf<T> allocate(PoolThreadCache poolThreadCache, int i, int i2) {
        PooledByteBuf<T> newByteBuf = newByteBuf(i2);
        allocate(poolThreadCache, newByteBuf, i);
        return newByteBuf;
    }

    /* access modifiers changed from: 0000 */
    public boolean isTinyOrSmall(int i) {
        return (i & this.subpageOverflowMask) == 0;
    }

    private void allocate(PoolThreadCache poolThreadCache, PooledByteBuf<T> pooledByteBuf, int i) {
        int i2;
        PoolSubpage<T>[] poolSubpageArr;
        int normalizeCapacity = normalizeCapacity(i);
        if (isTinyOrSmall(normalizeCapacity)) {
            boolean isTiny = isTiny(normalizeCapacity);
            if (isTiny) {
                if (!poolThreadCache.allocateTiny(this, pooledByteBuf, i, normalizeCapacity)) {
                    i2 = tinyIdx(normalizeCapacity);
                    poolSubpageArr = this.tinySubpagePools;
                } else {
                    return;
                }
            } else if (!poolThreadCache.allocateSmall(this, pooledByteBuf, i, normalizeCapacity)) {
                i2 = smallIdx(normalizeCapacity);
                poolSubpageArr = this.smallSubpagePools;
            } else {
                return;
            }
            PoolSubpage<T> poolSubpage = poolSubpageArr[i2];
            synchronized (poolSubpage) {
                PoolSubpage<T> poolSubpage2 = poolSubpage.next;
                if (poolSubpage2 != poolSubpage) {
                    poolSubpage2.chunk.initBufWithSubpage(pooledByteBuf, poolSubpage2.allocate(), i);
                    incTinySmallAllocation(isTiny);
                    return;
                }
                synchronized (this) {
                    allocateNormal(pooledByteBuf, i, normalizeCapacity);
                }
                incTinySmallAllocation(isTiny);
                return;
            }
        }
        if (normalizeCapacity > this.chunkSize) {
            allocateHuge(pooledByteBuf, i);
        } else if (!poolThreadCache.allocateNormal(this, pooledByteBuf, i, normalizeCapacity)) {
            synchronized (this) {
                allocateNormal(pooledByteBuf, i, normalizeCapacity);
                this.allocationsNormal++;
            }
        }
    }

    private void allocateNormal(PooledByteBuf<T> pooledByteBuf, int i, int i2) {
        if (!this.q050.allocate(pooledByteBuf, i, i2) && !this.q025.allocate(pooledByteBuf, i, i2) && !this.q000.allocate(pooledByteBuf, i, i2) && !this.qInit.allocate(pooledByteBuf, i, i2) && !this.q075.allocate(pooledByteBuf, i, i2)) {
            PoolChunk newChunk = newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
            newChunk.initBuf(pooledByteBuf, newChunk.allocate(i2), i);
            this.qInit.add(newChunk);
        }
    }

    private void incTinySmallAllocation(boolean z) {
        if (z) {
            this.allocationsTiny.increment();
        } else {
            this.allocationsSmall.increment();
        }
    }

    private void allocateHuge(PooledByteBuf<T> pooledByteBuf, int i) {
        PoolChunk newUnpooledChunk = newUnpooledChunk(i);
        this.activeBytesHuge.add((long) newUnpooledChunk.chunkSize());
        pooledByteBuf.initUnpooled(newUnpooledChunk, i);
        this.allocationsHuge.increment();
    }

    /* access modifiers changed from: 0000 */
    public void free(PoolChunk<T> poolChunk, long j, int i, PoolThreadCache poolThreadCache) {
        if (poolChunk.unpooled) {
            int chunkSize2 = poolChunk.chunkSize();
            destroyChunk(poolChunk);
            this.activeBytesHuge.add((long) (-chunkSize2));
            this.deallocationsHuge.increment();
        } else {
            SizeClass sizeClass = sizeClass(i);
            if (poolThreadCache == null || !poolThreadCache.add(this, poolChunk, j, i, sizeClass)) {
                freeChunk(poolChunk, j, sizeClass);
            }
        }
    }

    private SizeClass sizeClass(int i) {
        if (!isTinyOrSmall(i)) {
            return SizeClass.Normal;
        }
        return isTiny(i) ? SizeClass.Tiny : SizeClass.Small;
    }

    /* access modifiers changed from: 0000 */
    public void freeChunk(PoolChunk<T> poolChunk, long j, SizeClass sizeClass) {
        boolean z;
        synchronized (this) {
            int i = C55411.$SwitchMap$io$netty$buffer$PoolArena$SizeClass[sizeClass.ordinal()];
            z = true;
            if (i == 1) {
                this.deallocationsNormal++;
            } else if (i == 2) {
                this.deallocationsSmall++;
            } else if (i == 3) {
                this.deallocationsTiny++;
            } else {
                throw new Error();
            }
            if (poolChunk.parent.free(poolChunk, j)) {
                z = false;
            }
        }
        if (z) {
            destroyChunk(poolChunk);
        }
    }

    /* access modifiers changed from: 0000 */
    public PoolSubpage<T> findSubpagePoolHead(int i) {
        PoolSubpage<T>[] poolSubpageArr;
        int i2;
        if (isTiny(i)) {
            int i3 = i >>> 4;
            i2 = i3;
            poolSubpageArr = this.tinySubpagePools;
        } else {
            i2 = 0;
            int i4 = i >>> 10;
            while (i4 != 0) {
                i4 >>>= 1;
                i2++;
            }
            poolSubpageArr = this.smallSubpagePools;
        }
        return poolSubpageArr[i2];
    }

    /* access modifiers changed from: 0000 */
    public int normalizeCapacity(int i) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("capacity: ");
            sb.append(i);
            sb.append(" (expected: 0+)");
            throw new IllegalArgumentException(sb.toString());
        } else if (i >= this.chunkSize) {
            if (this.directMemoryCacheAlignment != 0) {
                i = alignCapacity(i);
            }
            return i;
        } else if (!isTiny(i)) {
            int i2 = i - 1;
            int i3 = i2 | (i2 >>> 1);
            int i4 = i3 | (i3 >>> 2);
            int i5 = i4 | (i4 >>> 4);
            int i6 = i5 | (i5 >>> 8);
            int i7 = (i6 | (i6 >>> 16)) + 1;
            if (i7 < 0) {
                i7 >>>= 1;
            }
            return i7;
        } else if (this.directMemoryCacheAlignment > 0) {
            return alignCapacity(i);
        } else {
            return (i & 15) == 0 ? i : (i & -16) + 16;
        }
    }

    /* access modifiers changed from: 0000 */
    public int alignCapacity(int i) {
        int i2 = this.directMemoryCacheAlignmentMask & i;
        return i2 == 0 ? i : (i + this.directMemoryCacheAlignment) - i2;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void reallocate(p043io.netty.buffer.PooledByteBuf<T> r13, int r14, boolean r15) {
        /*
            r12 = this;
            if (r14 < 0) goto L_0x0060
            int r0 = r13.maxCapacity()
            if (r14 > r0) goto L_0x0060
            int r6 = r13.length
            if (r6 != r14) goto L_0x000d
            return
        L_0x000d:
            io.netty.buffer.PoolChunk<T> r7 = r13.chunk
            long r8 = r13.handle
            T r2 = r13.memory
            int r3 = r13.offset
            int r10 = r13.maxLength
            int r11 = r13.readerIndex()
            int r0 = r13.writerIndex()
            io.netty.buffer.PooledByteBufAllocator r1 = r12.parent
            io.netty.buffer.PoolThreadCache r1 = r1.threadCache()
            r12.allocate(r1, r13, r14)
            if (r14 <= r6) goto L_0x0033
            T r4 = r13.memory
            int r5 = r13.offset
            r1 = r12
            r1.memoryCopy(r2, r3, r4, r5, r6)
            goto L_0x0050
        L_0x0033:
            if (r14 >= r6) goto L_0x0050
            if (r11 >= r14) goto L_0x004e
            if (r0 <= r14) goto L_0x003a
            goto L_0x003b
        L_0x003a:
            r14 = r0
        L_0x003b:
            int r3 = r3 + r11
            T r4 = r13.memory
            int r0 = r13.offset
            int r5 = r0 + r11
            int r6 = r14 - r11
            r0 = r12
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r0.memoryCopy(r1, r2, r3, r4, r5)
            goto L_0x0051
        L_0x004e:
            r11 = r14
            goto L_0x0051
        L_0x0050:
            r14 = r0
        L_0x0051:
            r13.setIndex(r11, r14)
            if (r15 == 0) goto L_0x005f
            io.netty.buffer.PoolThreadCache r5 = r13.cache
            r0 = r12
            r1 = r7
            r2 = r8
            r4 = r10
            r0.free(r1, r2, r4, r5)
        L_0x005f:
            return
        L_0x0060:
            java.lang.IllegalArgumentException r13 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r0 = "newCapacity: "
            r15.append(r0)
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.PoolArena.reallocate(io.netty.buffer.PooledByteBuf, int, boolean):void");
    }

    public int numThreadCaches() {
        return this.numThreadCaches.get();
    }

    public int numTinySubpages() {
        return this.tinySubpagePools.length;
    }

    public int numSmallSubpages() {
        return this.smallSubpagePools.length;
    }

    public int numChunkLists() {
        return this.chunkListMetrics.size();
    }

    public List<PoolSubpageMetric> tinySubpages() {
        return subPageMetricList(this.tinySubpagePools);
    }

    public List<PoolSubpageMetric> smallSubpages() {
        return subPageMetricList(this.smallSubpagePools);
    }

    public List<PoolChunkListMetric> chunkLists() {
        return this.chunkListMetrics;
    }

    private static List<PoolSubpageMetric> subPageMetricList(PoolSubpage<?>[] poolSubpageArr) {
        ArrayList arrayList = new ArrayList();
        for (PoolSubpage<T> poolSubpage : poolSubpageArr) {
            if (poolSubpage.next != poolSubpage) {
                PoolSubpage<T> poolSubpage2 = poolSubpage.next;
                do {
                    arrayList.add(poolSubpage2);
                    poolSubpage2 = poolSubpage2.next;
                } while (poolSubpage2 != poolSubpage);
            }
        }
        return arrayList;
    }

    public long numAllocations() {
        long j;
        synchronized (this) {
            j = this.allocationsNormal;
        }
        return this.allocationsTiny.value() + this.allocationsSmall.value() + j + this.allocationsHuge.value();
    }

    public long numTinyAllocations() {
        return this.allocationsTiny.value();
    }

    public long numSmallAllocations() {
        return this.allocationsSmall.value();
    }

    public synchronized long numNormalAllocations() {
        return this.allocationsNormal;
    }

    public long numDeallocations() {
        long j;
        synchronized (this) {
            j = this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal;
        }
        return j + this.deallocationsHuge.value();
    }

    public synchronized long numTinyDeallocations() {
        return this.deallocationsTiny;
    }

    public synchronized long numSmallDeallocations() {
        return this.deallocationsSmall;
    }

    public synchronized long numNormalDeallocations() {
        return this.deallocationsNormal;
    }

    public long numHugeAllocations() {
        return this.allocationsHuge.value();
    }

    public long numHugeDeallocations() {
        return this.deallocationsHuge.value();
    }

    public long numActiveAllocations() {
        long j;
        long value = ((this.allocationsTiny.value() + this.allocationsSmall.value()) + this.allocationsHuge.value()) - this.deallocationsHuge.value();
        synchronized (this) {
            j = value + (this.allocationsNormal - ((this.deallocationsTiny + this.deallocationsSmall) + this.deallocationsNormal));
        }
        return Math.max(j, 0);
    }

    public long numActiveTinyAllocations() {
        return Math.max(numTinyAllocations() - numTinyDeallocations(), 0);
    }

    public long numActiveSmallAllocations() {
        return Math.max(numSmallAllocations() - numSmallDeallocations(), 0);
    }

    public long numActiveNormalAllocations() {
        long j;
        synchronized (this) {
            j = this.allocationsNormal - this.deallocationsNormal;
        }
        return Math.max(j, 0);
    }

    public long numActiveHugeAllocations() {
        return Math.max(numHugeAllocations() - numHugeDeallocations(), 0);
    }

    public long numActiveBytes() {
        long value = this.activeBytesHuge.value();
        synchronized (this) {
            for (int i = 0; i < this.chunkListMetrics.size(); i++) {
                for (PoolChunkMetric chunkSize2 : (PoolChunkListMetric) this.chunkListMetrics.get(i)) {
                    value += (long) chunkSize2.chunkSize();
                }
            }
        }
        return Math.max(0, value);
    }

    public synchronized String toString() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("Chunk(s) at 0~25%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.qInit);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 0~50%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q000);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 25~75%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q025);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 50~100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q050);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 75~100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q075);
        sb.append(StringUtil.NEWLINE);
        sb.append("Chunk(s) at 100%:");
        sb.append(StringUtil.NEWLINE);
        sb.append(this.q100);
        sb.append(StringUtil.NEWLINE);
        sb.append("tiny subpages:");
        appendPoolSubPages(sb, this.tinySubpagePools);
        sb.append(StringUtil.NEWLINE);
        sb.append("small subpages:");
        appendPoolSubPages(sb, this.smallSubpagePools);
        sb.append(StringUtil.NEWLINE);
        return sb.toString();
    }

    private static void appendPoolSubPages(StringBuilder sb, PoolSubpage<?>[] poolSubpageArr) {
        for (int i = 0; i < poolSubpageArr.length; i++) {
            PoolSubpage<T> poolSubpage = poolSubpageArr[i];
            if (poolSubpage.next != poolSubpage) {
                sb.append(StringUtil.NEWLINE);
                sb.append(i);
                sb.append(": ");
                PoolSubpage<T> poolSubpage2 = poolSubpage.next;
                do {
                    sb.append(poolSubpage2);
                    poolSubpage2 = poolSubpage2.next;
                } while (poolSubpage2 != poolSubpage);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            destroyPoolSubPages(this.smallSubpagePools);
            destroyPoolSubPages(this.tinySubpagePools);
            destroyPoolChunkLists(this.qInit, this.q000, this.q025, this.q050, this.q075, this.q100);
        }
    }

    private static void destroyPoolSubPages(PoolSubpage<?>[] poolSubpageArr) {
        for (PoolSubpage<?> destroy : poolSubpageArr) {
            destroy.destroy();
        }
    }

    private void destroyPoolChunkLists(PoolChunkList<T>... poolChunkListArr) {
        for (PoolChunkList<T> destroy : poolChunkListArr) {
            destroy.destroy(this);
        }
    }
}
