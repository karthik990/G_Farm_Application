package p043io.netty.util;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentMap;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.ResourceLeakDetector */
public class ResourceLeakDetector<T> {
    /* access modifiers changed from: private */
    public static final Level DEFAULT_LEVEL = Level.SIMPLE;
    private static final int DEFAULT_MAX_RECORDS = 4;
    static final int DEFAULT_SAMPLING_INTERVAL = 128;
    /* access modifiers changed from: private */
    public static final int MAX_RECORDS;
    private static final String PROP_LEVEL = "io.netty.leakDetection.level";
    private static final String PROP_LEVEL_OLD = "io.netty.leakDetectionLevel";
    private static final String PROP_MAX_RECORDS = "io.netty.leakDetection.maxRecords";
    private static final String[] STACK_TRACE_ELEMENT_EXCLUSIONS = {"io.netty.util.ReferenceCountUtil.touch(", "io.netty.buffer.AdvancedLeakAwareByteBuf.touch(", "io.netty.buffer.AbstractByteBufAllocator.toLeakAwareBuffer(", "io.netty.buffer.AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation("};
    private static Level level;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
    /* access modifiers changed from: private */
    public final ConcurrentMap<DefaultResourceLeak, LeakEntry> allLeaks;
    /* access modifiers changed from: private */
    public final ReferenceQueue<Object> refQueue;
    private final ConcurrentMap<String, Boolean> reportedLeaks;
    private final String resourceType;
    private final int samplingInterval;

    /* renamed from: io.netty.util.ResourceLeakDetector$DefaultResourceLeak */
    private final class DefaultResourceLeak extends PhantomReference<Object> implements ResourceLeakTracker<T>, ResourceLeak {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final String creationRecord;
        private final Deque<String> lastRecords = new ArrayDeque();
        private int removedRecords;
        private final int trackedHash;

        static {
            Class<ResourceLeakDetector> cls = ResourceLeakDetector.class;
        }

        DefaultResourceLeak(Object obj) {
            super(obj, ResourceLeakDetector.this.refQueue);
            this.trackedHash = System.identityHashCode(obj);
            if (ResourceLeakDetector.getLevel().ordinal() >= Level.ADVANCED.ordinal()) {
                this.creationRecord = ResourceLeakDetector.newRecord(null, 3);
            } else {
                this.creationRecord = null;
            }
            ResourceLeakDetector.this.allLeaks.put(this, LeakEntry.INSTANCE);
        }

        public void record() {
            record0(null, 3);
        }

        public void record(Object obj) {
            record0(obj, 3);
        }

        private void record0(Object obj, int i) {
            if (this.creationRecord != null) {
                String newRecord = ResourceLeakDetector.newRecord(obj, i);
                synchronized (this.lastRecords) {
                    int size = this.lastRecords.size();
                    if (size == 0 || !((String) this.lastRecords.getLast()).equals(newRecord)) {
                        this.lastRecords.add(newRecord);
                    }
                    if (size > ResourceLeakDetector.MAX_RECORDS) {
                        this.lastRecords.removeFirst();
                        this.removedRecords++;
                    }
                }
            }
        }

        public boolean close() {
            return ResourceLeakDetector.this.allLeaks.remove(this, LeakEntry.INSTANCE);
        }

        public boolean close(T t) {
            return close() && t != null;
        }

        public String toString() {
            Object[] array;
            int i;
            if (this.creationRecord == null) {
                return "";
            }
            synchronized (this.lastRecords) {
                array = this.lastRecords.toArray();
                i = this.removedRecords;
            }
            StringBuilder sb = new StringBuilder(16384);
            sb.append(StringUtil.NEWLINE);
            if (i > 0) {
                sb.append("WARNING: ");
                sb.append(i);
                sb.append(" leak records were discarded because the leak record count is limited to ");
                sb.append(ResourceLeakDetector.MAX_RECORDS);
                sb.append(". Use system property ");
                sb.append(ResourceLeakDetector.PROP_MAX_RECORDS);
                sb.append(" to increase the limit.");
                sb.append(StringUtil.NEWLINE);
            }
            sb.append("Recent access records: ");
            sb.append(array.length);
            sb.append(StringUtil.NEWLINE);
            if (array.length > 0) {
                for (int length = array.length - 1; length >= 0; length--) {
                    sb.append('#');
                    sb.append(length + 1);
                    sb.append(':');
                    sb.append(StringUtil.NEWLINE);
                    sb.append(array[length]);
                }
            }
            sb.append("Created at:");
            sb.append(StringUtil.NEWLINE);
            sb.append(this.creationRecord);
            sb.setLength(sb.length() - StringUtil.NEWLINE.length());
            return sb.toString();
        }
    }

    /* renamed from: io.netty.util.ResourceLeakDetector$LeakEntry */
    private static final class LeakEntry {
        private static final int HASH = System.identityHashCode(INSTANCE);
        static final LeakEntry INSTANCE = new LeakEntry();

        public boolean equals(Object obj) {
            return obj == this;
        }

        private LeakEntry() {
        }

        public int hashCode() {
            return HASH;
        }
    }

    /* renamed from: io.netty.util.ResourceLeakDetector$Level */
    public enum Level {
        DISABLED,
        SIMPLE,
        ADVANCED,
        PARANOID;

        static Level parseLevel(String str) {
            Level[] values;
            String trim = str.trim();
            for (Level level : values()) {
                if (trim.equalsIgnoreCase(level.name()) || trim.equals(String.valueOf(level.ordinal()))) {
                    return level;
                }
            }
            return ResourceLeakDetector.DEFAULT_LEVEL;
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void reportInstancesLeak(String str) {
    }

    static {
        boolean z;
        String str = "io.netty.noResourceLeakDetection";
        String str2 = SystemPropertyUtil.get(str);
        String str3 = PROP_LEVEL;
        if (str2 != null) {
            z = SystemPropertyUtil.getBoolean(str, false);
            logger.debug("-Dio.netty.noResourceLeakDetection: {}", (Object) Boolean.valueOf(z));
            logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", str3, DEFAULT_LEVEL.name().toLowerCase());
        } else {
            z = false;
        }
        Level parseLevel = Level.parseLevel(SystemPropertyUtil.get(str3, SystemPropertyUtil.get(PROP_LEVEL_OLD, (z ? Level.DISABLED : DEFAULT_LEVEL).name())));
        String str4 = PROP_MAX_RECORDS;
        MAX_RECORDS = SystemPropertyUtil.getInt(str4, 4);
        level = parseLevel;
        if (logger.isDebugEnabled()) {
            String str5 = "-D{}: {}";
            logger.debug(str5, str3, parseLevel.name().toLowerCase());
            logger.debug(str5, str4, Integer.valueOf(MAX_RECORDS));
        }
    }

    @Deprecated
    public static void setEnabled(boolean z) {
        setLevel(z ? Level.SIMPLE : Level.DISABLED);
    }

    public static boolean isEnabled() {
        return getLevel().ordinal() > Level.DISABLED.ordinal();
    }

    public static void setLevel(Level level2) {
        if (level2 != null) {
            level = level2;
            return;
        }
        throw new NullPointerException(Param.LEVEL);
    }

    public static Level getLevel() {
        return level;
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> cls) {
        this(StringUtil.simpleClassName(cls));
    }

    @Deprecated
    public ResourceLeakDetector(String str) {
        this(str, 128, Long.MAX_VALUE);
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> cls, int i, long j) {
        this(cls, i);
    }

    public ResourceLeakDetector(Class<?> cls, int i) {
        this(StringUtil.simpleClassName(cls), i, Long.MAX_VALUE);
    }

    @Deprecated
    public ResourceLeakDetector(String str, int i, long j) {
        this.allLeaks = PlatformDependent.newConcurrentHashMap();
        this.refQueue = new ReferenceQueue<>();
        this.reportedLeaks = PlatformDependent.newConcurrentHashMap();
        if (str != null) {
            this.resourceType = str;
            this.samplingInterval = i;
            return;
        }
        throw new NullPointerException("resourceType");
    }

    @Deprecated
    public final ResourceLeak open(T t) {
        return track0(t);
    }

    public final ResourceLeakTracker<T> track(T t) {
        return track0(t);
    }

    private DefaultResourceLeak track0(T t) {
        Level level2 = level;
        if (level2 == Level.DISABLED) {
            return null;
        }
        if (level2.ordinal() >= Level.PARANOID.ordinal()) {
            reportLeak(level2);
            return new DefaultResourceLeak<>(t);
        } else if (PlatformDependent.threadLocalRandom().nextInt(this.samplingInterval) != 0) {
            return null;
        } else {
            reportLeak(level2);
            return new DefaultResourceLeak<>(t);
        }
    }

    private void reportLeak(Level level2) {
        if (!logger.isErrorEnabled()) {
            while (true) {
                DefaultResourceLeak defaultResourceLeak = (DefaultResourceLeak) this.refQueue.poll();
                if (defaultResourceLeak != null) {
                    defaultResourceLeak.close();
                } else {
                    return;
                }
            }
        } else {
            while (true) {
                DefaultResourceLeak defaultResourceLeak2 = (DefaultResourceLeak) this.refQueue.poll();
                if (defaultResourceLeak2 != null) {
                    defaultResourceLeak2.clear();
                    if (defaultResourceLeak2.close()) {
                        String defaultResourceLeak3 = defaultResourceLeak2.toString();
                        if (this.reportedLeaks.putIfAbsent(defaultResourceLeak3, Boolean.TRUE) == null) {
                            if (defaultResourceLeak3.isEmpty()) {
                                reportUntracedLeak(this.resourceType);
                            } else {
                                reportTracedLeak(this.resourceType, defaultResourceLeak3);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void reportTracedLeak(String str, String str2) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}", str, str2);
    }

    /* access modifiers changed from: protected */
    public void reportUntracedLeak(String str) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.", str, PROP_LEVEL, Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName((Object) this));
    }

    static String newRecord(Object obj, int i) {
        StackTraceElement[] stackTrace;
        boolean z;
        StringBuilder sb = new StringBuilder(4096);
        if (obj != null) {
            sb.append("\tHint: ");
            if (obj instanceof ResourceLeakHint) {
                sb.append(((ResourceLeakHint) obj).toHintString());
            } else {
                sb.append(obj);
            }
            sb.append(StringUtil.NEWLINE);
        }
        int i2 = i;
        for (StackTraceElement stackTraceElement : new Throwable().getStackTrace()) {
            if (i2 > 0) {
                i2--;
            } else {
                String stackTraceElement2 = stackTraceElement.toString();
                String[] strArr = STACK_TRACE_ELEMENT_EXCLUSIONS;
                int length = strArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        z = false;
                        break;
                    } else if (stackTraceElement2.startsWith(strArr[i3])) {
                        z = true;
                        break;
                    } else {
                        i3++;
                    }
                }
                if (!z) {
                    sb.append(9);
                    sb.append(stackTraceElement2);
                    sb.append(StringUtil.NEWLINE);
                }
            }
        }
        return sb.toString();
    }
}
