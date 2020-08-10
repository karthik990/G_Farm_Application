package p043io.netty.handler.ssl;

import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.SslCompletionEvent */
public abstract class SslCompletionEvent {
    private final Throwable cause;

    SslCompletionEvent() {
        this.cause = null;
    }

    SslCompletionEvent(Throwable th) {
        this.cause = (Throwable) ObjectUtil.checkNotNull(th, "cause");
    }

    public final boolean isSuccess() {
        return this.cause == null;
    }

    public final Throwable cause() {
        return this.cause;
    }

    public final String toString() {
        Throwable cause2 = cause();
        if (cause2 == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append("(SUCCESS)");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getClass().getSimpleName());
        sb2.append('(');
        sb2.append(cause2);
        sb2.append(')');
        return sb2.toString();
    }
}
