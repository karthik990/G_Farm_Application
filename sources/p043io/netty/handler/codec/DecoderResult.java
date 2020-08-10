package p043io.netty.handler.codec;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import p043io.netty.util.Signal;

/* renamed from: io.netty.handler.codec.DecoderResult */
public class DecoderResult {
    protected static final Signal SIGNAL_SUCCESS;
    protected static final Signal SIGNAL_UNFINISHED;
    public static final DecoderResult SUCCESS = new DecoderResult(SIGNAL_SUCCESS);
    public static final DecoderResult UNFINISHED = new DecoderResult(SIGNAL_UNFINISHED);
    private final Throwable cause;

    static {
        Class<DecoderResult> cls = DecoderResult.class;
        SIGNAL_UNFINISHED = Signal.valueOf(cls, "UNFINISHED");
        SIGNAL_SUCCESS = Signal.valueOf(cls, "SUCCESS");
    }

    public static DecoderResult failure(Throwable th) {
        if (th != null) {
            return new DecoderResult(th);
        }
        throw new NullPointerException("cause");
    }

    protected DecoderResult(Throwable th) {
        if (th != null) {
            this.cause = th;
            return;
        }
        throw new NullPointerException("cause");
    }

    public boolean isFinished() {
        return this.cause != SIGNAL_UNFINISHED;
    }

    public boolean isSuccess() {
        return this.cause == SIGNAL_SUCCESS;
    }

    public boolean isFailure() {
        Throwable th = this.cause;
        return (th == SIGNAL_SUCCESS || th == SIGNAL_UNFINISHED) ? false : true;
    }

    public Throwable cause() {
        if (isFailure()) {
            return this.cause;
        }
        return null;
    }

    public String toString() {
        if (!isFinished()) {
            return "unfinished";
        }
        if (isSuccess()) {
            return Param.SUCCESS;
        }
        String th = cause().toString();
        StringBuilder sb = new StringBuilder(th.length() + 17);
        sb.append("failure(");
        sb.append(th);
        sb.append(')');
        return sb.toString();
    }
}
