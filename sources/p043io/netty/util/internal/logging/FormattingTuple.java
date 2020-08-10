package p043io.netty.util.internal.logging;

/* renamed from: io.netty.util.internal.logging.FormattingTuple */
final class FormattingTuple {
    private final String message;
    private final Throwable throwable;

    FormattingTuple(String str, Throwable th) {
        this.message = str;
        this.throwable = th;
    }

    public String getMessage() {
        return this.message;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}
