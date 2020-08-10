package p043io.netty.util.internal.logging;

/* renamed from: io.netty.util.internal.logging.InternalLoggerFactory */
public abstract class InternalLoggerFactory {
    private static volatile InternalLoggerFactory defaultFactory;

    /* access modifiers changed from: protected */
    public abstract InternalLogger newInstance(String str);

    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        return r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0010 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static p043io.netty.util.internal.logging.InternalLoggerFactory newDefaultFactory(java.lang.String r3) {
        /*
            io.netty.util.internal.logging.Slf4JLoggerFactory r0 = new io.netty.util.internal.logging.Slf4JLoggerFactory     // Catch:{ all -> 0x0010 }
            r1 = 1
            r0.<init>(r1)     // Catch:{ all -> 0x0010 }
            io.netty.util.internal.logging.InternalLogger r1 = r0.newInstance(r3)     // Catch:{ all -> 0x0010 }
            java.lang.String r2 = "Using SLF4J as the default logging framework"
            r1.debug(r2)     // Catch:{ all -> 0x0010 }
            goto L_0x0027
        L_0x0010:
            io.netty.util.internal.logging.InternalLoggerFactory r0 = p043io.netty.util.internal.logging.Log4JLoggerFactory.INSTANCE     // Catch:{ all -> 0x001c }
            io.netty.util.internal.logging.InternalLogger r1 = r0.newInstance(r3)     // Catch:{ all -> 0x001c }
            java.lang.String r2 = "Using Log4J as the default logging framework"
            r1.debug(r2)     // Catch:{ all -> 0x001c }
            goto L_0x0027
        L_0x001c:
            io.netty.util.internal.logging.InternalLoggerFactory r0 = p043io.netty.util.internal.logging.JdkLoggerFactory.INSTANCE
            io.netty.util.internal.logging.InternalLogger r3 = r0.newInstance(r3)
            java.lang.String r1 = "Using java.util.logging as the default logging framework"
            r3.debug(r1)
        L_0x0027:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.logging.InternalLoggerFactory.newDefaultFactory(java.lang.String):io.netty.util.internal.logging.InternalLoggerFactory");
    }

    public static InternalLoggerFactory getDefaultFactory() {
        if (defaultFactory == null) {
            defaultFactory = newDefaultFactory(InternalLoggerFactory.class.getName());
        }
        return defaultFactory;
    }

    public static void setDefaultFactory(InternalLoggerFactory internalLoggerFactory) {
        if (internalLoggerFactory != null) {
            defaultFactory = internalLoggerFactory;
            return;
        }
        throw new NullPointerException("defaultFactory");
    }

    public static InternalLogger getInstance(Class<?> cls) {
        return getInstance(cls.getName());
    }

    public static InternalLogger getInstance(String str) {
        return getDefaultFactory().newInstance(str);
    }
}
