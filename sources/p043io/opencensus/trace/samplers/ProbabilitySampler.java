package p043io.opencensus.trace.samplers;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.List;
import javax.annotation.Nullable;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.trace.Sampler;
import p043io.opencensus.trace.Span;
import p043io.opencensus.trace.SpanContext;
import p043io.opencensus.trace.SpanId;
import p043io.opencensus.trace.TraceId;

/* renamed from: io.opencensus.trace.samplers.ProbabilitySampler */
abstract class ProbabilitySampler extends Sampler {
    /* access modifiers changed from: 0000 */
    public abstract long getIdUpperBound();

    /* access modifiers changed from: 0000 */
    public abstract double getProbability();

    ProbabilitySampler() {
    }

    static ProbabilitySampler create(double d) {
        C5887Utils.checkArgument(d >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && d <= 1.0d, "probability must be in range [0.0, 1.0]");
        long j = d == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? Long.MIN_VALUE : d == 1.0d ? Long.MAX_VALUE : (long) (9.223372036854776E18d * d);
        return new AutoValue_ProbabilitySampler(d, j);
    }

    public final boolean shouldSample(@Nullable SpanContext spanContext, @Nullable Boolean bool, TraceId traceId, SpanId spanId, String str, @Nullable List<Span> list) {
        boolean z = true;
        if (spanContext != null && spanContext.getTraceOptions().isSampled()) {
            return true;
        }
        if (list != null) {
            for (Span context : list) {
                if (context.getContext().getTraceOptions().isSampled()) {
                    return true;
                }
            }
        }
        if (Math.abs(traceId.getLowerLong()) >= getIdUpperBound()) {
            z = false;
        }
        return z;
    }

    public final String getDescription() {
        return String.format("ProbabilitySampler{%.6f}", new Object[]{Double.valueOf(getProbability())});
    }
}
