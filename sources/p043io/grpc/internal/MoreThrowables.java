package p043io.grpc.internal;

import com.google.common.base.Preconditions;

/* renamed from: io.grpc.internal.MoreThrowables */
public final class MoreThrowables {
    public static void throwIfUnchecked(Throwable th) {
        Preconditions.checkNotNull(th);
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        }
    }

    private MoreThrowables() {
    }
}
