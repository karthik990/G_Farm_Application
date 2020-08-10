package p043io.netty.buffer;

import java.nio.ByteBuffer;
import java.util.Queue;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.ThreadDeathWatcher;
import p043io.netty.util.internal.MathUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.buffer.PoolThreadCache */
final class PoolThreadCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
    private int allocations;
    private final Thread deathWatchThread;
    final PoolArena<ByteBuffer> directArena;
    private final int freeSweepAllocationThreshold;
    private final Runnable freeTask;
    final PoolArena<byte[]> heapArena;
    private final MemoryRegionCache<ByteBuffer>[] normalDirectCaches;
    private final MemoryRegionCache<byte[]>[] normalHeapCaches;
    private final int numShiftsNormalDirect;
    private final int numShiftsNormalHeap;
    private final MemoryRegionCache<ByteBuffer>[] smallSubPageDirectCaches;
    private final MemoryRegionCache<byte[]>[] smallSubPageHeapCaches;
    private final MemoryRegionCache<ByteBuffer>[] tinySubPageDirectCaches;
    private final MemoryRegionCache<byte[]>[] tinySubPageHeapCaches;

    /* renamed from: io.netty.buffer.PoolThreadCache$2 */
    static /* synthetic */ class C55432 {
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
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.PoolThreadCache.C55432.<clinit>():void");
        }
    }

    /* renamed from: io.netty.buffer.PoolThreadCache$MemoryRegionCache */
    private static abstract class MemoryRegionCache<T> {
        private static final Recycler<Entry> RECYCLER = new Recycler<Entry>() {
            /* access modifiers changed from: protected */
            public Entry newObject(Handle<Entry> handle) {
                return new Entry(handle);
            }
        };
        private int allocations;
        private final Queue<Entry<T>> queue = PlatformDependent.newFixedMpscQueue(this.size);
        private final int size;
        private final SizeClass sizeClass;

        /* renamed from: io.netty.buffer.PoolThreadCache$MemoryRegionCache$Entry */
        static final class Entry<T> {
            PoolChunk<T> chunk;
            long handle = -1;
            final Handle<Entry<?>> recyclerHandle;

            Entry(Handle<Entry<?>> handle2) {
                this.recyclerHandle = handle2;
            }

            /* access modifiers changed from: 0000 */
            public void recycle() {
                this.chunk = null;
                this.handle = -1;
                this.recyclerHandle.recycle(this);
            }
        }

        /* access modifiers changed from: protected */
        public abstract void initBuf(PoolChunk<T> poolChunk, long j, PooledByteBuf<T> pooledByteBuf, int i);

        MemoryRegionCache(int i, SizeClass sizeClass2) {
            this.size = MathUtil.safeFindNextPositivePowerOfTwo(i);
            this.sizeClass = sizeClass2;
        }

        public final boolean add(PoolChunk<T> poolChunk, long j) {
            Entry newEntry = newEntry(poolChunk, j);
            boolean offer = this.queue.offer(newEntry);
            if (!offer) {
                newEntry.recycle();
            }
            return offer;
        }

        public final boolean allocate(PooledByteBuf<T> pooledByteBuf, int i) {
            Entry entry = (Entry) this.queue.poll();
            if (entry == null) {
                return false;
            }
            initBuf(entry.chunk, entry.handle, pooledByteBuf, i);
            entry.recycle();
            this.allocations++;
            return true;
        }

        public final int free() {
            return free(Integer.MAX_VALUE);
        }

        private int free(int i) {
            int i2 = 0;
            while (i2 < i) {
                Entry entry = (Entry) this.queue.poll();
                if (entry == null) {
                    break;
                }
                freeEntry(entry);
                i2++;
            }
            return i2;
        }

        public final void trim() {
            int i = this.size - this.allocations;
            this.allocations = 0;
            if (i > 0) {
                free(i);
            }
        }

        private void freeEntry(Entry entry) {
            PoolChunk<T> poolChunk = entry.chunk;
            long j = entry.handle;
            entry.recycle();
            poolChunk.arena.freeChunk(poolChunk, j, this.sizeClass);
        }

        private static Entry newEntry(PoolChunk<?> poolChunk, long j) {
            Entry entry = (Entry) RECYCLER.get();
            entry.chunk = poolChunk;
            entry.handle = j;
            return entry;
        }
    }

    /* renamed from: io.netty.buffer.PoolThreadCache$NormalMemoryRegionCache */
    private static final class NormalMemoryRegionCache<T> extends MemoryRegionCache<T> {
        NormalMemoryRegionCache(int i) {
            super(i, SizeClass.Normal);
        }

        /* access modifiers changed from: protected */
        public void initBuf(PoolChunk<T> poolChunk, long j, PooledByteBuf<T> pooledByteBuf, int i) {
            poolChunk.initBuf(pooledByteBuf, j, i);
        }
    }

    /* renamed from: io.netty.buffer.PoolThreadCache$SubPageMemoryRegionCache */
    private static final class SubPageMemoryRegionCache<T> extends MemoryRegionCache<T> {
        SubPageMemoryRegionCache(int i, SizeClass sizeClass) {
            super(i, sizeClass);
        }

        /* access modifiers changed from: protected */
        public void initBuf(PoolChunk<T> poolChunk, long j, PooledByteBuf<T> pooledByteBuf, int i) {
            poolChunk.initBufWithSubpage(pooledByteBuf, j, i);
        }
    }

    private static int log2(int i) {
        int i2 = 0;
        while (i > 1) {
            i >>= 1;
            i2++;
        }
        return i2;
    }

    PoolThreadCache(PoolArena<byte[]> poolArena, PoolArena<ByteBuffer> poolArena2, int i, int i2, int i3, int i4, int i5) {
        if (i4 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxCachedBufferCapacity: ");
            sb.append(i4);
            sb.append(" (expected: >= 0)");
            throw new IllegalArgumentException(sb.toString());
        } else if (i5 >= 1) {
            this.freeSweepAllocationThreshold = i5;
            this.heapArena = poolArena;
            this.directArena = poolArena2;
            if (poolArena2 != null) {
                this.tinySubPageDirectCaches = createSubPageCaches(i, 32, SizeClass.Tiny);
                this.smallSubPageDirectCaches = createSubPageCaches(i2, poolArena2.numSmallSubpagePools, SizeClass.Small);
                this.numShiftsNormalDirect = log2(poolArena2.pageSize);
                this.normalDirectCaches = createNormalCaches(i3, i4, poolArena2);
                poolArena2.numThreadCaches.getAndIncrement();
            } else {
                this.tinySubPageDirectCaches = null;
                this.smallSubPageDirectCaches = null;
                this.normalDirectCaches = null;
                this.numShiftsNormalDirect = -1;
            }
            if (poolArena != null) {
                this.tinySubPageHeapCaches = createSubPageCaches(i, 32, SizeClass.Tiny);
                this.smallSubPageHeapCaches = createSubPageCaches(i2, poolArena.numSmallSubpagePools, SizeClass.Small);
                this.numShiftsNormalHeap = log2(poolArena.pageSize);
                this.normalHeapCaches = createNormalCaches(i3, i4, poolArena);
                poolArena.numThreadCaches.getAndIncrement();
            } else {
                this.tinySubPageHeapCaches = null;
                this.smallSubPageHeapCaches = null;
                this.normalHeapCaches = null;
                this.numShiftsNormalHeap = -1;
            }
            if (this.tinySubPageDirectCaches == null && this.smallSubPageDirectCaches == null && this.normalDirectCaches == null && this.tinySubPageHeapCaches == null && this.smallSubPageHeapCaches == null && this.normalHeapCaches == null) {
                this.freeTask = null;
                this.deathWatchThread = null;
                return;
            }
            this.freeTask = new Runnable() {
                public void run() {
                    PoolThreadCache.this.free0();
                }
            };
            this.deathWatchThread = Thread.currentThread();
            ThreadDeathWatcher.watch(this.deathWatchThread, this.freeTask);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("freeSweepAllocationThreshold: ");
            sb2.append(i5);
            sb2.append(" (expected: > 0)");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    private static <T> MemoryRegionCache<T>[] createSubPageCaches(int i, int i2, SizeClass sizeClass) {
        if (i <= 0) {
            return null;
        }
        MemoryRegionCache<T>[] memoryRegionCacheArr = new MemoryRegionCache[i2];
        for (int i3 = 0; i3 < memoryRegionCacheArr.length; i3++) {
            memoryRegionCacheArr[i3] = new SubPageMemoryRegionCache(i, sizeClass);
        }
        return memoryRegionCacheArr;
    }

    private static <T> MemoryRegionCache<T>[] createNormalCaches(int i, int i2, PoolArena<T> poolArena) {
        if (i <= 0) {
            return null;
        }
        MemoryRegionCache<T>[] memoryRegionCacheArr = new MemoryRegionCache[Math.max(1, log2(Math.min(poolArena.chunkSize, i2) / poolArena.pageSize) + 1)];
        for (int i3 = 0; i3 < memoryRegionCacheArr.length; i3++) {
            memoryRegionCacheArr[i3] = new NormalMemoryRegionCache(i);
        }
        return memoryRegionCacheArr;
    }

    /* access modifiers changed from: 0000 */
    public boolean allocateTiny(PoolArena<?> poolArena, PooledByteBuf<?> pooledByteBuf, int i, int i2) {
        return allocate(cacheForTiny(poolArena, i2), pooledByteBuf, i);
    }

    /* access modifiers changed from: 0000 */
    public boolean allocateSmall(PoolArena<?> poolArena, PooledByteBuf<?> pooledByteBuf, int i, int i2) {
        return allocate(cacheForSmall(poolArena, i2), pooledByteBuf, i);
    }

    /* access modifiers changed from: 0000 */
    public boolean allocateNormal(PoolArena<?> poolArena, PooledByteBuf<?> pooledByteBuf, int i, int i2) {
        return allocate(cacheForNormal(poolArena, i2), pooledByteBuf, i);
    }

    private boolean allocate(MemoryRegionCache<?> memoryRegionCache, PooledByteBuf pooledByteBuf, int i) {
        if (memoryRegionCache == null) {
            return false;
        }
        boolean allocate = memoryRegionCache.allocate(pooledByteBuf, i);
        int i2 = this.allocations + 1;
        this.allocations = i2;
        if (i2 >= this.freeSweepAllocationThreshold) {
            this.allocations = 0;
            trim();
        }
        return allocate;
    }

    /* access modifiers changed from: 0000 */
    public boolean add(PoolArena<?> poolArena, PoolChunk poolChunk, long j, int i, SizeClass sizeClass) {
        MemoryRegionCache cache = cache(poolArena, i, sizeClass);
        if (cache == null) {
            return false;
        }
        return cache.add(poolChunk, j);
    }

    private MemoryRegionCache<?> cache(PoolArena<?> poolArena, int i, SizeClass sizeClass) {
        int i2 = C55432.$SwitchMap$io$netty$buffer$PoolArena$SizeClass[sizeClass.ordinal()];
        if (i2 == 1) {
            return cacheForNormal(poolArena, i);
        }
        if (i2 == 2) {
            return cacheForSmall(poolArena, i);
        }
        if (i2 == 3) {
            return cacheForTiny(poolArena, i);
        }
        throw new Error();
    }

    /* access modifiers changed from: 0000 */
    public void free() {
        Runnable runnable = this.freeTask;
        if (runnable != null) {
            ThreadDeathWatcher.unwatch(this.deathWatchThread, runnable);
        }
        free0();
    }

    /* access modifiers changed from: private */
    public void free0() {
        int free = free((MemoryRegionCache<?>[]) this.tinySubPageDirectCaches) + free((MemoryRegionCache<?>[]) this.smallSubPageDirectCaches) + free((MemoryRegionCache<?>[]) this.normalDirectCaches) + free((MemoryRegionCache<?>[]) this.tinySubPageHeapCaches) + free((MemoryRegionCache<?>[]) this.smallSubPageHeapCaches) + free((MemoryRegionCache<?>[]) this.normalHeapCaches);
        if (free > 0 && logger.isDebugEnabled()) {
            logger.debug("Freed {} thread-local buffer(s) from thread: {}", Integer.valueOf(free), Thread.currentThread().getName());
        }
        PoolArena<ByteBuffer> poolArena = this.directArena;
        if (poolArena != null) {
            poolArena.numThreadCaches.getAndDecrement();
        }
        PoolArena<byte[]> poolArena2 = this.heapArena;
        if (poolArena2 != null) {
            poolArena2.numThreadCaches.getAndDecrement();
        }
    }

    private static int free(MemoryRegionCache<?>[] memoryRegionCacheArr) {
        if (memoryRegionCacheArr == null) {
            return 0;
        }
        int i = 0;
        for (MemoryRegionCache<?> free : memoryRegionCacheArr) {
            i += free(free);
        }
        return i;
    }

    private static int free(MemoryRegionCache<?> memoryRegionCache) {
        if (memoryRegionCache == null) {
            return 0;
        }
        return memoryRegionCache.free();
    }

    /* access modifiers changed from: 0000 */
    public void trim() {
        trim((MemoryRegionCache<?>[]) this.tinySubPageDirectCaches);
        trim((MemoryRegionCache<?>[]) this.smallSubPageDirectCaches);
        trim((MemoryRegionCache<?>[]) this.normalDirectCaches);
        trim((MemoryRegionCache<?>[]) this.tinySubPageHeapCaches);
        trim((MemoryRegionCache<?>[]) this.smallSubPageHeapCaches);
        trim((MemoryRegionCache<?>[]) this.normalHeapCaches);
    }

    private static void trim(MemoryRegionCache<?>[] memoryRegionCacheArr) {
        if (memoryRegionCacheArr != null) {
            for (MemoryRegionCache<?> trim : memoryRegionCacheArr) {
                trim(trim);
            }
        }
    }

    private static void trim(MemoryRegionCache<?> memoryRegionCache) {
        if (memoryRegionCache != null) {
            memoryRegionCache.trim();
        }
    }

    private MemoryRegionCache<?> cacheForTiny(PoolArena<?> poolArena, int i) {
        int tinyIdx = PoolArena.tinyIdx(i);
        if (poolArena.isDirect()) {
            return cache(this.tinySubPageDirectCaches, tinyIdx);
        }
        return cache(this.tinySubPageHeapCaches, tinyIdx);
    }

    private MemoryRegionCache<?> cacheForSmall(PoolArena<?> poolArena, int i) {
        int smallIdx = PoolArena.smallIdx(i);
        if (poolArena.isDirect()) {
            return cache(this.smallSubPageDirectCaches, smallIdx);
        }
        return cache(this.smallSubPageHeapCaches, smallIdx);
    }

    private MemoryRegionCache<?> cacheForNormal(PoolArena<?> poolArena, int i) {
        if (poolArena.isDirect()) {
            return cache(this.normalDirectCaches, log2(i >> this.numShiftsNormalDirect));
        }
        return cache(this.normalHeapCaches, log2(i >> this.numShiftsNormalHeap));
    }

    private static <T> MemoryRegionCache<T> cache(MemoryRegionCache<T>[] memoryRegionCacheArr, int i) {
        if (memoryRegionCacheArr == null || i > memoryRegionCacheArr.length - 1) {
            return null;
        }
        return memoryRegionCacheArr[i];
    }
}
