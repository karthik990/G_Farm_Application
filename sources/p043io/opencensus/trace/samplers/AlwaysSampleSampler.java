package p043io.opencensus.trace.samplers;

import java.util.List;
import javax.annotation.Nullable;
import p043io.opencensus.trace.Sampler;
import p043io.opencensus.trace.Span;
import p043io.opencensus.trace.SpanContext;
import p043io.opencensus.trace.SpanId;
import p043io.opencensus.trace.TraceId;

/* renamed from: io.opencensus.trace.samplers.AlwaysSampleSampler */
final class AlwaysSampleSampler extends Sampler {
    public boolean shouldSample(@Nullable SpanContext spanContext, @Nullable Boolean bool, TraceId traceId, SpanId spanId, String str, List<Span> list) {
        return true;
    }

    public String toString() {
        return "AlwaysSampleSampler";
    }

    AlwaysSampleSampler() {
    }

    public String getDescription() {
        return toString();
    }
}
