package p043io.netty.util.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.internal.CleanerJava9 */
final class CleanerJava9 implements Cleaner {
    private static final Method INVOKE_CLEANER;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CleanerJava9.class);

    CleanerJava9() {
    }

    static {
        Throwable th;
        Object obj;
        Method method = null;
        if (PlatformDependent0.hasUnsafe()) {
            ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1);
            try {
                Method declaredMethod = PlatformDependent0.UNSAFE.getClass().getDeclaredMethod("invokeCleaner", new Class[]{ByteBuffer.class});
                declaredMethod.invoke(PlatformDependent0.UNSAFE, new Object[]{allocateDirect});
                obj = declaredMethod;
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                obj = e;
            }
            if (obj instanceof Throwable) {
                th = (Throwable) obj;
            } else {
                th = null;
                method = (Method) obj;
            }
        } else {
            th = new UnsupportedOperationException("sun.misc.Unsafe unavailable");
        }
        if (th == null) {
            logger.debug("java.nio.ByteBuffer.cleaner(): available");
        } else {
            logger.debug("java.nio.ByteBuffer.cleaner(): unavailable", th);
        }
        INVOKE_CLEANER = method;
    }

    static boolean isSupported() {
        return INVOKE_CLEANER != null;
    }

    public void freeDirectBuffer(ByteBuffer byteBuffer) {
        try {
            INVOKE_CLEANER.invoke(PlatformDependent0.UNSAFE, new Object[]{byteBuffer});
        } catch (Throwable th) {
            PlatformDependent0.throwException(th);
        }
    }
}
