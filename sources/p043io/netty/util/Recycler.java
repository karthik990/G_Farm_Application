package p043io.netty.util;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.util.concurrent.FastThreadLocal;
import p043io.netty.util.internal.MathUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.Recycler */
public abstract class Recycler<T> {
    private static final int DEFAULT_INITIAL_MAX_CAPACITY_PER_THREAD = 32768;
    private static final int DEFAULT_MAX_CAPACITY_PER_THREAD;
    /* access modifiers changed from: private */
    public static final FastThreadLocal<Map<Stack<?>, WeakOrderQueue>> DELAYED_RECYCLED = new FastThreadLocal<Map<Stack<?>, WeakOrderQueue>>() {
        /* access modifiers changed from: protected */
        public Map<Stack<?>, WeakOrderQueue> initialValue() {
            return new WeakHashMap();
        }
    };
    /* access modifiers changed from: private */
    public static final AtomicInteger ID_GENERATOR = new AtomicInteger(Integer.MIN_VALUE);
    /* access modifiers changed from: private */
    public static final int INITIAL_CAPACITY = Math.min(DEFAULT_MAX_CAPACITY_PER_THREAD, 256);
    /* access modifiers changed from: private */
    public static final int LINK_CAPACITY = MathUtil.safeFindNextPositivePowerOfTwo(Math.max(SystemPropertyUtil.getInt("io.netty.recycler.linkCapacity", 16), 16));
    private static final int MAX_DELAYED_QUEUES_PER_THREAD = Math.max(0, SystemPropertyUtil.getInt("io.netty.recycler.maxDelayedQueuesPerThread", NettyRuntime.availableProcessors() * 2));
    private static final int MAX_SHARED_CAPACITY_FACTOR = Math.max(2, SystemPropertyUtil.getInt("io.netty.recycler.maxSharedCapacityFactor", 2));
    private static final Handle NOOP_HANDLE = new Handle() {
        public void recycle(Object obj) {
        }
    };
    /* access modifiers changed from: private */
    public static final int OWN_THREAD_ID = ID_GENERATOR.getAndIncrement();
    private static final int RATIO = MathUtil.safeFindNextPositivePowerOfTwo(SystemPropertyUtil.getInt("io.netty.recycler.ratio", 8));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Recycler.class);
    /* access modifiers changed from: private */
    public final int maxCapacityPerThread;
    /* access modifiers changed from: private */
    public final int maxDelayedQueuesPerThread;
    /* access modifiers changed from: private */
    public final int maxSharedCapacityFactor;
    /* access modifiers changed from: private */
    public final int ratioMask;
    private final FastThreadLocal<Stack<T>> threadLocal;

    /* renamed from: io.netty.util.Recycler$DefaultHandle */
    static final class DefaultHandle<T> implements Handle<T> {
        boolean hasBeenRecycled;
        /* access modifiers changed from: private */
        public int lastRecycledId;
        /* access modifiers changed from: private */
        public int recycleId;
        /* access modifiers changed from: private */
        public Stack<?> stack;
        /* access modifiers changed from: private */
        public Object value;

        DefaultHandle(Stack<?> stack2) {
            this.stack = stack2;
        }

        public void recycle(Object obj) {
            if (obj == this.value) {
                this.stack.push(this);
                return;
            }
            throw new IllegalArgumentException("object does not belong to handle");
        }
    }

    /* renamed from: io.netty.util.Recycler$Handle */
    public interface Handle<T> {
        void recycle(T t);
    }

    /* renamed from: io.netty.util.Recycler$Stack */
    static final class Stack<T> {
        final AtomicInteger availableSharedCapacity;
        private WeakOrderQueue cursor;
        /* access modifiers changed from: private */
        public DefaultHandle<?>[] elements;
        private int handleRecycleCount = -1;
        private volatile WeakOrderQueue head;
        private final int maxCapacity;
        final int maxDelayedQueues;
        final Recycler<T> parent;
        private WeakOrderQueue prev;
        private final int ratioMask;
        /* access modifiers changed from: private */
        public int size;
        final Thread thread;

        Stack(Recycler<T> recycler, Thread thread2, int i, int i2, int i3, int i4) {
            this.parent = recycler;
            this.thread = thread2;
            this.maxCapacity = i;
            this.availableSharedCapacity = new AtomicInteger(Math.max(i / i2, Recycler.LINK_CAPACITY));
            this.elements = new DefaultHandle[Math.min(Recycler.INITIAL_CAPACITY, i)];
            this.ratioMask = i3;
            this.maxDelayedQueues = i4;
        }

        /* access modifiers changed from: 0000 */
        public synchronized void setHead(WeakOrderQueue weakOrderQueue) {
            weakOrderQueue.setNext(this.head);
            this.head = weakOrderQueue;
        }

        /* access modifiers changed from: 0000 */
        public int increaseCapacity(int i) {
            int length = this.elements.length;
            int i2 = this.maxCapacity;
            do {
                length <<= 1;
                if (length >= i) {
                    break;
                }
            } while (length < i2);
            int min = Math.min(length, i2);
            DefaultHandle<?>[] defaultHandleArr = this.elements;
            if (min != defaultHandleArr.length) {
                this.elements = (DefaultHandle[]) Arrays.copyOf(defaultHandleArr, min);
            }
            return min;
        }

        /* access modifiers changed from: 0000 */
        public DefaultHandle<T> pop() {
            int i = this.size;
            if (i == 0) {
                if (!scavenge()) {
                    return null;
                }
                i = this.size;
            }
            int i2 = i - 1;
            DefaultHandle<?>[] defaultHandleArr = this.elements;
            DefaultHandle<?> defaultHandle = defaultHandleArr[i2];
            defaultHandleArr[i2] = null;
            if (defaultHandle.lastRecycledId == defaultHandle.recycleId) {
                defaultHandle.recycleId = 0;
                defaultHandle.lastRecycledId = 0;
                this.size = i2;
                return defaultHandle;
            }
            throw new IllegalStateException("recycled multiple times");
        }

        /* access modifiers changed from: 0000 */
        public boolean scavenge() {
            if (scavengeSome()) {
                return true;
            }
            this.prev = null;
            this.cursor = this.head;
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean scavengeSome() {
            WeakOrderQueue weakOrderQueue;
            WeakOrderQueue access$1800;
            WeakOrderQueue weakOrderQueue2 = this.cursor;
            boolean z = false;
            if (weakOrderQueue2 == null) {
                WeakOrderQueue weakOrderQueue3 = this.head;
                if (weakOrderQueue3 == null) {
                    return false;
                }
                WeakOrderQueue weakOrderQueue4 = weakOrderQueue3;
                weakOrderQueue = null;
                weakOrderQueue2 = weakOrderQueue4;
            } else {
                weakOrderQueue = this.prev;
            }
            while (true) {
                if (weakOrderQueue2.transfer(this)) {
                    z = true;
                    break;
                }
                access$1800 = weakOrderQueue2.next;
                if (weakOrderQueue2.owner.get() == null) {
                    if (weakOrderQueue2.hasFinalData()) {
                        while (weakOrderQueue2.transfer(this)) {
                            z = true;
                        }
                    }
                    if (weakOrderQueue != null) {
                        weakOrderQueue.setNext(access$1800);
                    }
                } else {
                    weakOrderQueue = weakOrderQueue2;
                }
                if (access$1800 == null || z) {
                    weakOrderQueue2 = access$1800;
                } else {
                    weakOrderQueue2 = access$1800;
                }
            }
            weakOrderQueue2 = access$1800;
            this.prev = weakOrderQueue;
            this.cursor = weakOrderQueue2;
            return z;
        }

        /* access modifiers changed from: 0000 */
        public void push(DefaultHandle<?> defaultHandle) {
            Thread currentThread = Thread.currentThread();
            if (this.thread == currentThread) {
                pushNow(defaultHandle);
            } else {
                pushLater(defaultHandle, currentThread);
            }
        }

        private void pushNow(DefaultHandle<?> defaultHandle) {
            if ((defaultHandle.recycleId | defaultHandle.lastRecycledId) == 0) {
                defaultHandle.recycleId = defaultHandle.lastRecycledId = Recycler.OWN_THREAD_ID;
                int i = this.size;
                if (i < this.maxCapacity && !dropHandle(defaultHandle)) {
                    DefaultHandle<?>[] defaultHandleArr = this.elements;
                    if (i == defaultHandleArr.length) {
                        this.elements = (DefaultHandle[]) Arrays.copyOf(defaultHandleArr, Math.min(i << 1, this.maxCapacity));
                    }
                    this.elements[i] = defaultHandle;
                    this.size = i + 1;
                    return;
                }
                return;
            }
            throw new IllegalStateException("recycled already");
        }

        private void pushLater(DefaultHandle<?> defaultHandle, Thread thread2) {
            Map map = (Map) Recycler.DELAYED_RECYCLED.get();
            WeakOrderQueue weakOrderQueue = (WeakOrderQueue) map.get(this);
            if (weakOrderQueue == null) {
                if (map.size() >= this.maxDelayedQueues) {
                    map.put(this, WeakOrderQueue.DUMMY);
                    return;
                }
                weakOrderQueue = WeakOrderQueue.allocate(this, thread2);
                if (weakOrderQueue != null) {
                    map.put(this, weakOrderQueue);
                } else {
                    return;
                }
            } else if (weakOrderQueue == WeakOrderQueue.DUMMY) {
                return;
            }
            weakOrderQueue.add(defaultHandle);
        }

        /* access modifiers changed from: 0000 */
        public boolean dropHandle(DefaultHandle<?> defaultHandle) {
            if (!defaultHandle.hasBeenRecycled) {
                int i = this.handleRecycleCount + 1;
                this.handleRecycleCount = i;
                if ((i & this.ratioMask) != 0) {
                    return true;
                }
                defaultHandle.hasBeenRecycled = true;
            }
            return false;
        }

        /* access modifiers changed from: 0000 */
        public DefaultHandle<T> newHandle() {
            return new DefaultHandle<>(this);
        }
    }

    /* renamed from: io.netty.util.Recycler$WeakOrderQueue */
    private static final class WeakOrderQueue {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final WeakOrderQueue DUMMY = new WeakOrderQueue();
        private final AtomicInteger availableSharedCapacity;
        private Link head;

        /* renamed from: id */
        private final int f3744id;
        /* access modifiers changed from: private */
        public WeakOrderQueue next;
        /* access modifiers changed from: private */
        public final WeakReference<Thread> owner;
        private Link tail;

        /* renamed from: io.netty.util.Recycler$WeakOrderQueue$Link */
        private static final class Link extends AtomicInteger {
            /* access modifiers changed from: private */
            public final DefaultHandle<?>[] elements;
            /* access modifiers changed from: private */
            public Link next;
            /* access modifiers changed from: private */
            public int readIndex;

            private Link() {
                this.elements = new DefaultHandle[Recycler.LINK_CAPACITY];
            }
        }

        static {
            Class<Recycler> cls = Recycler.class;
        }

        private WeakOrderQueue() {
            this.f3744id = Recycler.ID_GENERATOR.getAndIncrement();
            this.owner = null;
            this.availableSharedCapacity = null;
        }

        private WeakOrderQueue(Stack<?> stack, Thread thread) {
            this.f3744id = Recycler.ID_GENERATOR.getAndIncrement();
            Link link = new Link();
            this.tail = link;
            this.head = link;
            this.owner = new WeakReference<>(thread);
            this.availableSharedCapacity = stack.availableSharedCapacity;
        }

        static WeakOrderQueue newQueue(Stack<?> stack, Thread thread) {
            WeakOrderQueue weakOrderQueue = new WeakOrderQueue(stack, thread);
            stack.setHead(weakOrderQueue);
            return weakOrderQueue;
        }

        /* access modifiers changed from: private */
        public void setNext(WeakOrderQueue weakOrderQueue) {
            this.next = weakOrderQueue;
        }

        static WeakOrderQueue allocate(Stack<?> stack, Thread thread) {
            if (reserveSpace(stack.availableSharedCapacity, Recycler.LINK_CAPACITY)) {
                return newQueue(stack, thread);
            }
            return null;
        }

        private static boolean reserveSpace(AtomicInteger atomicInteger, int i) {
            int i2;
            do {
                i2 = atomicInteger.get();
                if (i2 < i) {
                    return false;
                }
            } while (!atomicInteger.compareAndSet(i2, i2 - i));
            return true;
        }

        private void reclaimSpace(int i) {
            this.availableSharedCapacity.addAndGet(i);
        }

        /* access modifiers changed from: 0000 */
        public void add(DefaultHandle<?> defaultHandle) {
            defaultHandle.lastRecycledId = this.f3744id;
            Link link = this.tail;
            int i = link.get();
            if (i == Recycler.LINK_CAPACITY) {
                if (reserveSpace(this.availableSharedCapacity, Recycler.LINK_CAPACITY)) {
                    link = link.next = new Link();
                    this.tail = link;
                    i = link.get();
                } else {
                    return;
                }
            }
            link.elements[i] = defaultHandle;
            defaultHandle.stack = null;
            link.lazySet(i + 1);
        }

        /* access modifiers changed from: 0000 */
        public boolean hasFinalData() {
            return this.tail.readIndex != this.tail.get();
        }

        /* access modifiers changed from: 0000 */
        public boolean transfer(Stack<?> stack) {
            Link link = this.head;
            if (link == null) {
                return false;
            }
            if (link.readIndex == Recycler.LINK_CAPACITY) {
                if (link.next == null) {
                    return false;
                }
                link = link.next;
                this.head = link;
            }
            int access$1400 = link.readIndex;
            int i = link.get();
            int i2 = i - access$1400;
            if (i2 == 0) {
                return false;
            }
            int access$700 = stack.size;
            int i3 = i2 + access$700;
            if (i3 > stack.elements.length) {
                i = Math.min((stack.increaseCapacity(i3) + access$1400) - access$700, i);
            }
            if (access$1400 == i) {
                return false;
            }
            DefaultHandle[] access$1300 = link.elements;
            DefaultHandle[] access$600 = stack.elements;
            while (access$1400 < i) {
                DefaultHandle defaultHandle = access$1300[access$1400];
                if (defaultHandle.recycleId == 0) {
                    defaultHandle.recycleId = defaultHandle.lastRecycledId;
                } else if (defaultHandle.recycleId != defaultHandle.lastRecycledId) {
                    throw new IllegalStateException("recycled already");
                }
                access$1300[access$1400] = null;
                if (!stack.dropHandle(defaultHandle)) {
                    defaultHandle.stack = stack;
                    int i4 = access$700 + 1;
                    access$600[access$700] = defaultHandle;
                    access$700 = i4;
                }
                access$1400++;
            }
            if (i == Recycler.LINK_CAPACITY && link.next != null) {
                reclaimSpace(Recycler.LINK_CAPACITY);
                this.head = link.next;
            }
            link.readIndex = i;
            if (stack.size == access$700) {
                return false;
            }
            stack.size = access$700;
            return true;
        }

        /* access modifiers changed from: protected */
        public void finalize() throws Throwable {
            try {
                super.finalize();
            } finally {
                for (Link link = this.head; link != null; link = link.next) {
                    reclaimSpace(Recycler.LINK_CAPACITY);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract T newObject(Handle<T> handle);

    static {
        int i = 32768;
        int i2 = SystemPropertyUtil.getInt("io.netty.recycler.maxCapacityPerThread", SystemPropertyUtil.getInt("io.netty.recycler.maxCapacity", 32768));
        if (i2 >= 0) {
            i = i2;
        }
        DEFAULT_MAX_CAPACITY_PER_THREAD = i;
        if (logger.isDebugEnabled()) {
            int i3 = DEFAULT_MAX_CAPACITY_PER_THREAD;
            if (i3 == 0) {
                logger.debug("-Dio.netty.recycler.maxCapacityPerThread: disabled");
                logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: disabled");
                logger.debug("-Dio.netty.recycler.linkCapacity: disabled");
                logger.debug("-Dio.netty.recycler.ratio: disabled");
            } else {
                logger.debug("-Dio.netty.recycler.maxCapacityPerThread: {}", (Object) Integer.valueOf(i3));
                logger.debug("-Dio.netty.recycler.maxSharedCapacityFactor: {}", (Object) Integer.valueOf(MAX_SHARED_CAPACITY_FACTOR));
                logger.debug("-Dio.netty.recycler.linkCapacity: {}", (Object) Integer.valueOf(LINK_CAPACITY));
                logger.debug("-Dio.netty.recycler.ratio: {}", (Object) Integer.valueOf(RATIO));
            }
        }
    }

    protected Recycler() {
        this(DEFAULT_MAX_CAPACITY_PER_THREAD);
    }

    protected Recycler(int i) {
        this(i, MAX_SHARED_CAPACITY_FACTOR);
    }

    protected Recycler(int i, int i2) {
        this(i, i2, RATIO, MAX_DELAYED_QUEUES_PER_THREAD);
    }

    protected Recycler(int i, int i2, int i3, int i4) {
        this.threadLocal = new FastThreadLocal<Stack<T>>() {
            /* access modifiers changed from: protected */
            public Stack<T> initialValue() {
                Stack stack = new Stack(Recycler.this, Thread.currentThread(), Recycler.this.maxCapacityPerThread, Recycler.this.maxSharedCapacityFactor, Recycler.this.ratioMask, Recycler.this.maxDelayedQueuesPerThread);
                return stack;
            }
        };
        this.ratioMask = MathUtil.safeFindNextPositivePowerOfTwo(i3) - 1;
        if (i <= 0) {
            this.maxCapacityPerThread = 0;
            this.maxSharedCapacityFactor = 1;
            this.maxDelayedQueuesPerThread = 0;
            return;
        }
        this.maxCapacityPerThread = i;
        this.maxSharedCapacityFactor = Math.max(1, i2);
        this.maxDelayedQueuesPerThread = Math.max(0, i4);
    }

    public final T get() {
        if (this.maxCapacityPerThread == 0) {
            return newObject(NOOP_HANDLE);
        }
        Stack stack = (Stack) this.threadLocal.get();
        DefaultHandle pop = stack.pop();
        if (pop == null) {
            pop = stack.newHandle();
            pop.value = newObject(pop);
        }
        return pop.value;
    }

    @Deprecated
    public final boolean recycle(T t, Handle<T> handle) {
        if (handle == NOOP_HANDLE) {
            return false;
        }
        DefaultHandle defaultHandle = (DefaultHandle) handle;
        if (defaultHandle.stack.parent != this) {
            return false;
        }
        defaultHandle.recycle(t);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final int threadLocalCapacity() {
        return ((Stack) this.threadLocal.get()).elements.length;
    }

    /* access modifiers changed from: 0000 */
    public final int threadLocalSize() {
        return ((Stack) this.threadLocal.get()).size;
    }
}
