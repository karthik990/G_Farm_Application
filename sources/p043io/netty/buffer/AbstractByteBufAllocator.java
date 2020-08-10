package p043io.netty.buffer;

import p043io.netty.util.ResourceLeakDetector;
import p043io.netty.util.ResourceLeakDetector.Level;
import p043io.netty.util.ResourceLeakTracker;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.buffer.AbstractByteBufAllocator */
public abstract class AbstractByteBufAllocator implements ByteBufAllocator {
    static final int CALCULATE_THRESHOLD = 4194304;
    static final int DEFAULT_INITIAL_CAPACITY = 256;
    static final int DEFAULT_MAX_CAPACITY = Integer.MAX_VALUE;
    static final int DEFAULT_MAX_COMPONENTS = 16;
    private final boolean directByDefault;
    private final ByteBuf emptyBuf;

    /* renamed from: io.netty.buffer.AbstractByteBufAllocator$1 */
    static /* synthetic */ class C55251 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$util$ResourceLeakDetector$Level = new int[Level.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                io.netty.util.ResourceLeakDetector$Level[] r0 = p043io.netty.util.ResourceLeakDetector.Level.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$util$ResourceLeakDetector$Level = r0
                int[] r0 = $SwitchMap$io$netty$util$ResourceLeakDetector$Level     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.util.ResourceLeakDetector$Level r1 = p043io.netty.util.ResourceLeakDetector.Level.SIMPLE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$util$ResourceLeakDetector$Level     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.util.ResourceLeakDetector$Level r1 = p043io.netty.util.ResourceLeakDetector.Level.ADVANCED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$util$ResourceLeakDetector$Level     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.util.ResourceLeakDetector$Level r1 = p043io.netty.util.ResourceLeakDetector.Level.PARANOID     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.buffer.AbstractByteBufAllocator.C55251.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public abstract ByteBuf newDirectBuffer(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract ByteBuf newHeapBuffer(int i, int i2);

    protected static ByteBuf toLeakAwareBuffer(ByteBuf byteBuf) {
        int i = C55251.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()];
        if (i == 1) {
            ResourceLeakTracker track = AbstractByteBuf.leakDetector.track(byteBuf);
            if (track != null) {
                return new SimpleLeakAwareByteBuf(byteBuf, track);
            }
        } else if (i == 2 || i == 3) {
            ResourceLeakTracker track2 = AbstractByteBuf.leakDetector.track(byteBuf);
            if (track2 != null) {
                return new AdvancedLeakAwareByteBuf(byteBuf, track2);
            }
        }
        return byteBuf;
    }

    protected static CompositeByteBuf toLeakAwareBuffer(CompositeByteBuf compositeByteBuf) {
        int i = C55251.$SwitchMap$io$netty$util$ResourceLeakDetector$Level[ResourceLeakDetector.getLevel().ordinal()];
        if (i == 1) {
            ResourceLeakTracker track = AbstractByteBuf.leakDetector.track(compositeByteBuf);
            if (track != null) {
                return new SimpleLeakAwareCompositeByteBuf(compositeByteBuf, track);
            }
        } else if (i == 2 || i == 3) {
            ResourceLeakTracker track2 = AbstractByteBuf.leakDetector.track(compositeByteBuf);
            if (track2 != null) {
                return new AdvancedLeakAwareCompositeByteBuf(compositeByteBuf, track2);
            }
        }
        return compositeByteBuf;
    }

    protected AbstractByteBufAllocator() {
        this(false);
    }

    protected AbstractByteBufAllocator(boolean z) {
        this.directByDefault = z && PlatformDependent.hasUnsafe();
        this.emptyBuf = new EmptyByteBuf(this);
    }

    public ByteBuf buffer() {
        if (this.directByDefault) {
            return directBuffer();
        }
        return heapBuffer();
    }

    public ByteBuf buffer(int i) {
        if (this.directByDefault) {
            return directBuffer(i);
        }
        return heapBuffer(i);
    }

    public ByteBuf buffer(int i, int i2) {
        if (this.directByDefault) {
            return directBuffer(i, i2);
        }
        return heapBuffer(i, i2);
    }

    public ByteBuf ioBuffer() {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(256);
        }
        return heapBuffer(256);
    }

    public ByteBuf ioBuffer(int i) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(i);
        }
        return heapBuffer(i);
    }

    public ByteBuf ioBuffer(int i, int i2) {
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(i, i2);
        }
        return heapBuffer(i, i2);
    }

    public ByteBuf heapBuffer() {
        return heapBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int i) {
        return heapBuffer(i, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return this.emptyBuf;
        }
        validate(i, i2);
        return newHeapBuffer(i, i2);
    }

    public ByteBuf directBuffer() {
        return directBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int i) {
        return directBuffer(i, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int i, int i2) {
        if (i == 0 && i2 == 0) {
            return this.emptyBuf;
        }
        validate(i, i2);
        return newDirectBuffer(i, i2);
    }

    public CompositeByteBuf compositeBuffer() {
        if (this.directByDefault) {
            return compositeDirectBuffer();
        }
        return compositeHeapBuffer();
    }

    public CompositeByteBuf compositeBuffer(int i) {
        if (this.directByDefault) {
            return compositeDirectBuffer(i);
        }
        return compositeHeapBuffer(i);
    }

    public CompositeByteBuf compositeHeapBuffer() {
        return compositeHeapBuffer(16);
    }

    public CompositeByteBuf compositeHeapBuffer(int i) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, false, i));
    }

    public CompositeByteBuf compositeDirectBuffer() {
        return compositeDirectBuffer(16);
    }

    public CompositeByteBuf compositeDirectBuffer(int i) {
        return toLeakAwareBuffer(new CompositeByteBuf(this, true, i));
    }

    private static void validate(int i, int i2) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("initialCapacity: ");
            sb.append(i);
            sb.append(" (expected: 0+)");
            throw new IllegalArgumentException(sb.toString());
        } else if (i > i2) {
            throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append("(directByDefault: ");
        sb.append(this.directByDefault);
        sb.append(')');
        return sb.toString();
    }

    public int calculateNewCapacity(int i, int i2) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("minNewCapacity: ");
            sb.append(i);
            sb.append(" (expected: 0+)");
            throw new IllegalArgumentException(sb.toString());
        } else if (i > i2) {
            throw new IllegalArgumentException(String.format("minNewCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        } else if (i == 4194304) {
            return 4194304;
        } else {
            if (i > 4194304) {
                int i3 = (i / 4194304) * 4194304;
                if (i3 <= i2 - 4194304) {
                    i2 = i3 + 4194304;
                }
                return i2;
            }
            int i4 = 64;
            while (i4 < i) {
                i4 <<= 1;
            }
            return Math.min(i4, i2);
        }
    }
}
