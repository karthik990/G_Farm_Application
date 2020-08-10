package p043io.netty.util.internal;

import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.logging.InternalLogger;

/* renamed from: io.netty.util.internal.PromiseNotificationUtil */
public final class PromiseNotificationUtil {
    private PromiseNotificationUtil() {
    }

    public static void tryCancel(Promise<?> promise, InternalLogger internalLogger) {
        if (!promise.cancel(false) && internalLogger != null) {
            Throwable cause = promise.cause();
            if (cause == null) {
                internalLogger.warn("Failed to cancel promise because it has succeeded already: {}", (Object) promise);
            } else {
                internalLogger.warn("Failed to cancel promise because it has failed already: {}, unnotified cause:", promise, cause);
            }
        }
    }

    public static <V> void trySuccess(Promise<? super V> promise, V v, InternalLogger internalLogger) {
        if (!promise.trySuccess(v) && internalLogger != null) {
            Throwable cause = promise.cause();
            if (cause == null) {
                internalLogger.warn("Failed to mark a promise as success because it has succeeded already: {}", (Object) promise);
            } else {
                internalLogger.warn("Failed to mark a promise as success because it has failed already: {}, unnotified cause:", promise, cause);
            }
        }
    }

    public static void tryFailure(Promise<?> promise, Throwable th, InternalLogger internalLogger) {
        if (!promise.tryFailure(th) && internalLogger != null) {
            Throwable cause = promise.cause();
            if (cause == null) {
                internalLogger.warn("Failed to mark a promise as failure because it has succeeded already: {}", promise, th);
                return;
            }
            internalLogger.warn("Failed to mark a promise as failure because it has failed already: {}, unnotified cause: {}", promise, ThrowableUtil.stackTraceToString(cause), th);
        }
    }
}
