package p043io.netty.util.concurrent;

import androidx.core.p012os.EnvironmentCompat;
import java.util.Locale;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.util.concurrent.DefaultThreadFactory */
public class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolId = new AtomicInteger();
    private final boolean daemon;
    private final AtomicInteger nextId;
    private final String prefix;
    private final int priority;
    protected final ThreadGroup threadGroup;

    /* renamed from: io.netty.util.concurrent.DefaultThreadFactory$DefaultRunnableDecorator */
    private static final class DefaultRunnableDecorator implements Runnable {

        /* renamed from: r */
        private final Runnable f3745r;

        DefaultRunnableDecorator(Runnable runnable) {
            this.f3745r = runnable;
        }

        public void run() {
            try {
                this.f3745r.run();
            } finally {
                FastThreadLocal.removeAll();
            }
        }
    }

    public DefaultThreadFactory(Class<?> cls) {
        this(cls, false, 5);
    }

    public DefaultThreadFactory(String str) {
        this(str, false, 5);
    }

    public DefaultThreadFactory(Class<?> cls, boolean z) {
        this(cls, z, 5);
    }

    public DefaultThreadFactory(String str, boolean z) {
        this(str, z, 5);
    }

    public DefaultThreadFactory(Class<?> cls, int i) {
        this(cls, false, i);
    }

    public DefaultThreadFactory(String str, int i) {
        this(str, false, i);
    }

    public DefaultThreadFactory(Class<?> cls, boolean z, int i) {
        this(toPoolName(cls), z, i);
    }

    public static String toPoolName(Class<?> cls) {
        if (cls != null) {
            String simpleClassName = StringUtil.simpleClassName(cls);
            int length = simpleClassName.length();
            if (length == 0) {
                return EnvironmentCompat.MEDIA_UNKNOWN;
            }
            if (length == 1) {
                return simpleClassName.toLowerCase(Locale.US);
            }
            if (Character.isUpperCase(simpleClassName.charAt(0)) && Character.isLowerCase(simpleClassName.charAt(1))) {
                StringBuilder sb = new StringBuilder();
                sb.append(Character.toLowerCase(simpleClassName.charAt(0)));
                sb.append(simpleClassName.substring(1));
                simpleClassName = sb.toString();
            }
            return simpleClassName;
        }
        throw new NullPointerException("poolType");
    }

    public DefaultThreadFactory(String str, boolean z, int i, ThreadGroup threadGroup2) {
        this.nextId = new AtomicInteger();
        if (str == null) {
            throw new NullPointerException("poolName");
        } else if (i < 1 || i > 10) {
            StringBuilder sb = new StringBuilder();
            sb.append("priority: ");
            sb.append(i);
            sb.append(" (expected: Thread.MIN_PRIORITY <= priority <= Thread.MAX_PRIORITY)");
            throw new IllegalArgumentException(sb.toString());
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append('-');
            sb2.append(poolId.incrementAndGet());
            sb2.append('-');
            this.prefix = sb2.toString();
            this.daemon = z;
            this.priority = i;
            this.threadGroup = threadGroup2;
        }
    }

    public DefaultThreadFactory(String str, boolean z, int i) {
        this(str, z, i, System.getSecurityManager() == null ? Thread.currentThread().getThreadGroup() : System.getSecurityManager().getThreadGroup());
    }

    public Thread newThread(Runnable runnable) {
        DefaultRunnableDecorator defaultRunnableDecorator = new DefaultRunnableDecorator(runnable);
        StringBuilder sb = new StringBuilder();
        sb.append(this.prefix);
        sb.append(this.nextId.incrementAndGet());
        Thread newThread = newThread(defaultRunnableDecorator, sb.toString());
        try {
            if (newThread.isDaemon() != this.daemon) {
                newThread.setDaemon(this.daemon);
            }
            if (newThread.getPriority() != this.priority) {
                newThread.setPriority(this.priority);
            }
        } catch (Exception unused) {
        }
        return newThread;
    }

    /* access modifiers changed from: protected */
    public Thread newThread(Runnable runnable, String str) {
        return new FastThreadLocalThread(this.threadGroup, runnable, str);
    }
}
