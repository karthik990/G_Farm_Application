package p043io.netty.buffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p043io.netty.util.NettyRuntime;
import p043io.netty.util.concurrent.FastThreadLocal;
import p043io.netty.util.concurrent.FastThreadLocalThread;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.buffer.PooledByteBufAllocator */
public class PooledByteBufAllocator extends AbstractByteBufAllocator implements ByteBufAllocatorMetricProvider {
    public static final PooledByteBufAllocator DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    /* access modifiers changed from: private */
    public static final int DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
    private static final int DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT = SystemPropertyUtil.getInt("io.netty.allocator.directMemoryCacheAlignment", 0);
    /* access modifiers changed from: private */
    public static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
    private static final int DEFAULT_MAX_ORDER;
    private static final int DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
    private static final int DEFAULT_NUM_DIRECT_ARENA;
    private static final int DEFAULT_NUM_HEAP_ARENA;
    private static final int DEFAULT_PAGE_SIZE;
    private static final int DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
    private static final int DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
    private static final boolean DEFAULT_USE_CACHE_FOR_ALL_THREADS = SystemPropertyUtil.getBoolean("io.netty.allocator.useCacheForAllThreads", true);
    private static final int MAX_CHUNK_SIZE = 1073741824;
    private static final int MIN_PAGE_SIZE = 4096;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
    private final int chunkSize;
    private final List<PoolArenaMetric> directArenaMetrics;
    /* access modifiers changed from: private */
    public final PoolArena<ByteBuffer>[] directArenas;
    private final List<PoolArenaMetric> heapArenaMetrics;
    /* access modifiers changed from: private */
    public final PoolArena<byte[]>[] heapArenas;
    private final PooledByteBufAllocatorMetric metric;
    /* access modifiers changed from: private */
    public final int normalCacheSize;
    /* access modifiers changed from: private */
    public final int smallCacheSize;
    private final PoolThreadLocalCache threadCache;
    /* access modifiers changed from: private */
    public final int tinyCacheSize;

    /* renamed from: io.netty.buffer.PooledByteBufAllocator$PoolThreadLocalCache */
    final class PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache> {
        private final boolean useCacheForAllThreads;

        PoolThreadLocalCache(boolean z) {
            this.useCacheForAllThreads = z;
        }

        /* access modifiers changed from: protected */
        public synchronized PoolThreadCache initialValue() {
            PoolArena leastUsedArena = leastUsedArena(PooledByteBufAllocator.this.heapArenas);
            PoolArena leastUsedArena2 = leastUsedArena(PooledByteBufAllocator.this.directArenas);
            if (!this.useCacheForAllThreads) {
                if (!(Thread.currentThread() instanceof FastThreadLocalThread)) {
                    PoolThreadCache poolThreadCache = new PoolThreadCache(leastUsedArena, leastUsedArena2, 0, 0, 0, 0, 0);
                    return poolThreadCache;
                }
            }
            PoolThreadCache poolThreadCache2 = new PoolThreadCache(leastUsedArena, leastUsedArena2, PooledByteBufAllocator.this.tinyCacheSize, PooledByteBufAllocator.this.smallCacheSize, PooledByteBufAllocator.this.normalCacheSize, PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL);
            return poolThreadCache2;
        }

        /* access modifiers changed from: protected */
        public void onRemoval(PoolThreadCache poolThreadCache) {
            poolThreadCache.free();
        }

        private <T> PoolArena<T> leastUsedArena(PoolArena<T>[] poolArenaArr) {
            if (poolArenaArr == null || poolArenaArr.length == 0) {
                return null;
            }
            PoolArena<T> poolArena = poolArenaArr[0];
            for (int i = 1; i < poolArenaArr.length; i++) {
                PoolArena<T> poolArena2 = poolArenaArr[i];
                if (poolArena2.numThreadCaches.get() < poolArena.numThreadCaches.get()) {
                    poolArena = poolArena2;
                }
            }
            return poolArena;
        }
    }

    static {
        Object obj;
        int i = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
        Object th = null;
        try {
            validateAndCalculatePageShifts(i);
            obj = null;
        } catch (Throwable th2) {
            obj = th2;
            i = 8192;
        }
        DEFAULT_PAGE_SIZE = i;
        int i2 = 11;
        int i3 = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
        try {
            validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, i3);
            i2 = i3;
        } catch (Throwable th3) {
            th = th3;
        }
        DEFAULT_MAX_ORDER = i2;
        long availableProcessors = (long) (NettyRuntime.availableProcessors() * 2);
        long j = (long) (DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER);
        DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int) Math.min(availableProcessors, ((Runtime.getRuntime().maxMemory() / j) / 2) / 3)));
        DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int) Math.min(availableProcessors, ((PlatformDependent.maxDirectMemory() / j) / 2) / 3)));
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.allocator.numHeapArenas: {}", (Object) Integer.valueOf(DEFAULT_NUM_HEAP_ARENA));
            logger.debug("-Dio.netty.allocator.numDirectArenas: {}", (Object) Integer.valueOf(DEFAULT_NUM_DIRECT_ARENA));
            String str = "-Dio.netty.allocator.pageSize: {}";
            if (obj == null) {
                logger.debug(str, (Object) Integer.valueOf(DEFAULT_PAGE_SIZE));
            } else {
                logger.debug(str, Integer.valueOf(DEFAULT_PAGE_SIZE), obj);
            }
            String str2 = "-Dio.netty.allocator.maxOrder: {}";
            if (th == null) {
                logger.debug(str2, (Object) Integer.valueOf(DEFAULT_MAX_ORDER));
            } else {
                logger.debug(str2, Integer.valueOf(DEFAULT_MAX_ORDER), th);
            }
            logger.debug("-Dio.netty.allocator.chunkSize: {}", (Object) Integer.valueOf(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
            logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", (Object) Integer.valueOf(DEFAULT_TINY_CACHE_SIZE));
            logger.debug("-Dio.netty.allocator.smallCacheSize: {}", (Object) Integer.valueOf(DEFAULT_SMALL_CACHE_SIZE));
            logger.debug("-Dio.netty.allocator.normalCacheSize: {}", (Object) Integer.valueOf(DEFAULT_NORMAL_CACHE_SIZE));
            logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", (Object) Integer.valueOf(DEFAULT_MAX_CACHED_BUFFER_CAPACITY));
            logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", (Object) Integer.valueOf(DEFAULT_CACHE_TRIM_INTERVAL));
            logger.debug("-Dio.netty.allocator.useCacheForAllThreads: {}", (Object) Boolean.valueOf(DEFAULT_USE_CACHE_FOR_ALL_THREADS));
        }
    }

    public PooledByteBufAllocator() {
        this(false);
    }

    public PooledByteBufAllocator(boolean z) {
        this(z, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
    }

    public PooledByteBufAllocator(int i, int i2, int i3, int i4) {
        this(false, i, i2, i3, i4);
    }

    @Deprecated
    public PooledByteBufAllocator(boolean z, int i, int i2, int i3, int i4) {
        this(z, i, i2, i3, i4, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
    }

    @Deprecated
    public PooledByteBufAllocator(boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this(z, i, i2, i3, i4, i5, i6, i7, DEFAULT_USE_CACHE_FOR_ALL_THREADS, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
    }

    public PooledByteBufAllocator(boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z2) {
        this(z, i, i2, i3, i4, i5, i6, i7, z2, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
    }

    public PooledByteBufAllocator(boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z2, int i8) {
        int i9 = i;
        int i10 = i2;
        int i11 = i8;
        super(z);
        this.threadCache = new PoolThreadLocalCache(z2);
        this.tinyCacheSize = i5;
        this.smallCacheSize = i6;
        this.normalCacheSize = i7;
        this.chunkSize = validateAndCalculateChunkSize(i3, i4);
        String str = " (expected: >= 0)";
        if (i9 < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("nHeapArena: ");
            sb.append(i9);
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        } else if (i10 >= 0) {
            String str2 = "directMemoryCacheAlignment: ";
            if (i11 < 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(i11);
                sb2.append(str);
                throw new IllegalArgumentException(sb2.toString());
            } else if (i11 > 0 && !isDirectMemoryCacheAlignmentSupported()) {
                throw new IllegalArgumentException("directMemoryCacheAlignment is not supported");
            } else if (((-i11) & i11) == i11) {
                int validateAndCalculatePageShifts = validateAndCalculatePageShifts(i3);
                if (i9 > 0) {
                    this.heapArenas = newArenaArray(i);
                    ArrayList arrayList = new ArrayList(this.heapArenas.length);
                    for (int i12 = 0; i12 < this.heapArenas.length; i12++) {
                        HeapArena heapArena = new HeapArena(this, i3, i4, validateAndCalculatePageShifts, this.chunkSize, i8);
                        this.heapArenas[i12] = heapArena;
                        arrayList.add(heapArena);
                    }
                    this.heapArenaMetrics = Collections.unmodifiableList(arrayList);
                } else {
                    this.heapArenas = null;
                    this.heapArenaMetrics = Collections.emptyList();
                }
                if (i10 > 0) {
                    this.directArenas = newArenaArray(i2);
                    ArrayList arrayList2 = new ArrayList(this.directArenas.length);
                    for (int i13 = 0; i13 < this.directArenas.length; i13++) {
                        DirectArena directArena = new DirectArena(this, i3, i4, validateAndCalculatePageShifts, this.chunkSize, i8);
                        this.directArenas[i13] = directArena;
                        arrayList2.add(directArena);
                    }
                    this.directArenaMetrics = Collections.unmodifiableList(arrayList2);
                } else {
                    this.directArenas = null;
                    this.directArenaMetrics = Collections.emptyList();
                }
                this.metric = new PooledByteBufAllocatorMetric(this);
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str2);
                sb3.append(i11);
                sb3.append(" (expected: power of two)");
                throw new IllegalArgumentException(sb3.toString());
            }
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("nDirectArea: ");
            sb4.append(i10);
            sb4.append(str);
            throw new IllegalArgumentException(sb4.toString());
        }
    }

    private static <T> PoolArena<T>[] newArenaArray(int i) {
        return new PoolArena[i];
    }

    private static int validateAndCalculatePageShifts(int i) {
        String str = "pageSize: ";
        if (i < 4096) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(i);
            sb.append(" (expected: ");
            sb.append(4096);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        } else if (((i - 1) & i) == 0) {
            return 31 - Integer.numberOfLeadingZeros(i);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(i);
            sb2.append(" (expected: power of 2)");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    private static int validateAndCalculateChunkSize(int i, int i2) {
        if (i2 <= 14) {
            int i3 = i;
            int i4 = i2;
            while (i4 > 0) {
                if (i3 <= 536870912) {
                    i3 <<= 1;
                    i4--;
                } else {
                    throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(1073741824)}));
                }
            }
            return i3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("maxOrder: ");
        sb.append(i2);
        sb.append(" (expected: 0-14)");
        throw new IllegalArgumentException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public ByteBuf newHeapBuffer(int i, int i2) {
        ByteBuf byteBuf;
        PoolThreadCache poolThreadCache = (PoolThreadCache) this.threadCache.get();
        PoolArena<byte[]> poolArena = poolThreadCache.heapArena;
        if (poolArena != null) {
            byteBuf = poolArena.allocate(poolThreadCache, i, i2);
        } else {
            byteBuf = PlatformDependent.hasUnsafe() ? new UnpooledUnsafeHeapByteBuf(this, i, i2) : new UnpooledHeapByteBuf((ByteBufAllocator) this, i, i2);
        }
        return toLeakAwareBuffer(byteBuf);
    }

    /* access modifiers changed from: protected */
    public ByteBuf newDirectBuffer(int i, int i2) {
        PoolThreadCache poolThreadCache = (PoolThreadCache) this.threadCache.get();
        PoolArena<ByteBuffer> poolArena = poolThreadCache.directArena;
        ByteBuf byteBuf = poolArena != null ? poolArena.allocate(poolThreadCache, i, i2) : PlatformDependent.hasUnsafe() ? UnsafeByteBufUtil.newUnsafeDirectByteBuf(this, i, i2) : new UnpooledDirectByteBuf((ByteBufAllocator) this, i, i2);
        return toLeakAwareBuffer(byteBuf);
    }

    public static int defaultNumHeapArena() {
        return DEFAULT_NUM_HEAP_ARENA;
    }

    public static int defaultNumDirectArena() {
        return DEFAULT_NUM_DIRECT_ARENA;
    }

    public static int defaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public static int defaultMaxOrder() {
        return DEFAULT_MAX_ORDER;
    }

    public static int defaultTinyCacheSize() {
        return DEFAULT_TINY_CACHE_SIZE;
    }

    public static int defaultSmallCacheSize() {
        return DEFAULT_SMALL_CACHE_SIZE;
    }

    public static int defaultNormalCacheSize() {
        return DEFAULT_NORMAL_CACHE_SIZE;
    }

    public static boolean isDirectMemoryCacheAlignmentSupported() {
        return PlatformDependent.hasUnsafe();
    }

    public boolean isDirectBufferPooled() {
        return this.directArenas != null;
    }

    @Deprecated
    public boolean hasThreadLocalCache() {
        return this.threadCache.isSet();
    }

    @Deprecated
    public void freeThreadLocalCache() {
        this.threadCache.remove();
    }

    public PooledByteBufAllocatorMetric metric() {
        return this.metric;
    }

    @Deprecated
    public int numHeapArenas() {
        return this.heapArenaMetrics.size();
    }

    @Deprecated
    public int numDirectArenas() {
        return this.directArenaMetrics.size();
    }

    @Deprecated
    public List<PoolArenaMetric> heapArenas() {
        return this.heapArenaMetrics;
    }

    @Deprecated
    public List<PoolArenaMetric> directArenas() {
        return this.directArenaMetrics;
    }

    @Deprecated
    public int numThreadLocalCaches() {
        PoolArena[] poolArenaArr = this.heapArenas;
        if (poolArenaArr == null) {
            poolArenaArr = this.directArenas;
        }
        if (poolArenaArr == null) {
            return 0;
        }
        int i = 0;
        for (PoolArena poolArena : poolArenaArr) {
            i += poolArena.numThreadCaches.get();
        }
        return i;
    }

    @Deprecated
    public int tinyCacheSize() {
        return this.tinyCacheSize;
    }

    @Deprecated
    public int smallCacheSize() {
        return this.smallCacheSize;
    }

    @Deprecated
    public int normalCacheSize() {
        return this.normalCacheSize;
    }

    @Deprecated
    public final int chunkSize() {
        return this.chunkSize;
    }

    /* access modifiers changed from: 0000 */
    public final long usedHeapMemory() {
        return usedMemory(this.heapArenas);
    }

    /* access modifiers changed from: 0000 */
    public final long usedDirectMemory() {
        return usedMemory(this.directArenas);
    }

    private static long usedMemory(PoolArena<?>... poolArenaArr) {
        if (poolArenaArr == null) {
            return -1;
        }
        long j = 0;
        for (PoolArena<?> numActiveBytes : poolArenaArr) {
            j += numActiveBytes.numActiveBytes();
            if (j < 0) {
                return Long.MAX_VALUE;
            }
        }
        return j;
    }

    /* access modifiers changed from: 0000 */
    public final PoolThreadCache threadCache() {
        return (PoolThreadCache) this.threadCache.get();
    }

    public String dumpStats() {
        PoolArena<byte[]>[] poolArenaArr = this.heapArenas;
        int length = poolArenaArr == null ? 0 : poolArenaArr.length;
        StringBuilder sb = new StringBuilder(512);
        sb.append(length);
        sb.append(" heap arena(s):");
        sb.append(StringUtil.NEWLINE);
        if (length > 0) {
            for (PoolArena<byte[]> append : this.heapArenas) {
                sb.append(append);
            }
        }
        PoolArena<ByteBuffer>[] poolArenaArr2 = this.directArenas;
        int length2 = poolArenaArr2 == null ? 0 : poolArenaArr2.length;
        sb.append(length2);
        sb.append(" direct arena(s):");
        sb.append(StringUtil.NEWLINE);
        if (length2 > 0) {
            for (PoolArena<ByteBuffer> append2 : this.directArenas) {
                sb.append(append2);
            }
        }
        return sb.toString();
    }
}
