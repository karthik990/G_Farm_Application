package p043io.netty.util;

import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.ReferenceCountUtil */
public final class ReferenceCountUtil {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);

    /* renamed from: io.netty.util.ReferenceCountUtil$ReleasingTask */
    private static final class ReleasingTask implements Runnable {
        private final int decrement;
        private final ReferenceCounted obj;

        ReleasingTask(ReferenceCounted referenceCounted, int i) {
            this.obj = referenceCounted;
            this.decrement = i;
        }

        public void run() {
            try {
                if (!this.obj.release(this.decrement)) {
                    ReferenceCountUtil.logger.warn("Non-zero refCnt: {}", (Object) this);
                } else {
                    ReferenceCountUtil.logger.debug("Released: {}", (Object) this);
                }
            } catch (Exception e) {
                ReferenceCountUtil.logger.warn("Failed to release an object: {}", this.obj, e);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtil.simpleClassName((Object) this.obj));
            sb.append(".release(");
            sb.append(this.decrement);
            sb.append(") refCnt: ");
            sb.append(this.obj.refCnt());
            return sb.toString();
        }
    }

    public static <T> T retain(T t) {
        return t instanceof ReferenceCounted ? ((ReferenceCounted) t).retain() : t;
    }

    public static <T> T retain(T t, int i) {
        return t instanceof ReferenceCounted ? ((ReferenceCounted) t).retain(i) : t;
    }

    public static <T> T touch(T t) {
        return t instanceof ReferenceCounted ? ((ReferenceCounted) t).touch() : t;
    }

    public static <T> T touch(T t, Object obj) {
        return t instanceof ReferenceCounted ? ((ReferenceCounted) t).touch(obj) : t;
    }

    public static boolean release(Object obj) {
        if (obj instanceof ReferenceCounted) {
            return ((ReferenceCounted) obj).release();
        }
        return false;
    }

    public static boolean release(Object obj, int i) {
        if (obj instanceof ReferenceCounted) {
            return ((ReferenceCounted) obj).release(i);
        }
        return false;
    }

    public static void safeRelease(Object obj) {
        try {
            release(obj);
        } catch (Throwable th) {
            logger.warn("Failed to release a message: {}", obj, th);
        }
    }

    public static void safeRelease(Object obj, int i) {
        try {
            release(obj, i);
        } catch (Throwable th) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to release a message: {} (decrement: {})", obj, Integer.valueOf(i), th);
            }
        }
    }

    @Deprecated
    public static <T> T releaseLater(T t) {
        return releaseLater(t, 1);
    }

    @Deprecated
    public static <T> T releaseLater(T t, int i) {
        if (t instanceof ReferenceCounted) {
            ThreadDeathWatcher.watch(Thread.currentThread(), new ReleasingTask((ReferenceCounted) t, i));
        }
        return t;
    }

    public static int refCnt(Object obj) {
        if (obj instanceof ReferenceCounted) {
            return ((ReferenceCounted) obj).refCnt();
        }
        return -1;
    }

    private ReferenceCountUtil() {
    }
}
