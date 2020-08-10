package p043io.opencensus.trace.unsafe;

import javax.annotation.Nullable;
import p043io.grpc.Context;
import p043io.grpc.Context.Key;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.trace.BlankSpan;
import p043io.opencensus.trace.Span;

/* renamed from: io.opencensus.trace.unsafe.ContextUtils */
public final class ContextUtils {
    @Deprecated
    public static final Key<Span> CONTEXT_SPAN_KEY = Context.key("opencensus-trace-span-key");

    private ContextUtils() {
    }

    public static Context withValue(Context context, @Nullable Span span) {
        return ((Context) C5887Utils.checkNotNull(context, "context")).withValue(CONTEXT_SPAN_KEY, span);
    }

    public static Span getValue(Context context) {
        Span span = (Span) CONTEXT_SPAN_KEY.get((Context) C5887Utils.checkNotNull(context, "context"));
        return span == null ? BlankSpan.INSTANCE : span;
    }
}
