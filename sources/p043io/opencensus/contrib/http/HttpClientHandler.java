package p043io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import p043io.opencensus.contrib.http.util.HttpMeasureConstants;
import p043io.opencensus.stats.Stats;
import p043io.opencensus.stats.StatsRecorder;
import p043io.opencensus.tags.TagContextBuilder;
import p043io.opencensus.tags.TagKey;
import p043io.opencensus.tags.TagValue;
import p043io.opencensus.tags.Tagger;
import p043io.opencensus.tags.Tags;
import p043io.opencensus.trace.Span;
import p043io.opencensus.trace.Span.Kind;
import p043io.opencensus.trace.Span.Options;
import p043io.opencensus.trace.SpanContext;
import p043io.opencensus.trace.Tracer;
import p043io.opencensus.trace.propagation.TextFormat;
import p043io.opencensus.trace.propagation.TextFormat.Setter;

/* renamed from: io.opencensus.contrib.http.HttpClientHandler */
public class HttpClientHandler<Q, P, C> extends AbstractHttpHandler<Q, P> {
    private final Setter<C> setter;
    private final StatsRecorder statsRecorder = Stats.getStatsRecorder();
    private final Tagger tagger = Tags.getTagger();
    private final TextFormat textFormat;
    private final Tracer tracer;

    public /* bridge */ /* synthetic */ Span getSpanFromContext(HttpRequestContext httpRequestContext) {
        return super.getSpanFromContext(httpRequestContext);
    }

    public HttpClientHandler(Tracer tracer2, HttpExtractor<Q, P> httpExtractor, TextFormat textFormat2, Setter<C> setter2) {
        super(httpExtractor);
        Preconditions.checkNotNull(setter2, "setter");
        Preconditions.checkNotNull(textFormat2, "textFormat");
        Preconditions.checkNotNull(tracer2, "tracer");
        this.setter = setter2;
        this.textFormat = textFormat2;
        this.tracer = tracer2;
    }

    public HttpRequestContext handleStart(@Nullable Span span, C c, Q q) {
        Preconditions.checkNotNull(c, "carrier");
        Preconditions.checkNotNull(q, "request");
        if (span == null) {
            span = this.tracer.getCurrentSpan();
        }
        Span startSpan = this.tracer.spanBuilderWithExplicitParent(getSpanName(q, this.extractor), span).setSpanKind(Kind.CLIENT).startSpan();
        if (startSpan.getOptions().contains(Options.RECORD_EVENTS)) {
            addSpanRequestAttributes(startSpan, q, this.extractor);
        }
        SpanContext context = startSpan.getContext();
        if (!context.equals(SpanContext.INVALID)) {
            this.textFormat.inject(context, c, this.setter);
        }
        return getNewContext(startSpan, this.tagger.getCurrentTagContext());
    }

    public void handleEnd(HttpRequestContext httpRequestContext, @Nullable Q q, @Nullable P p, @Nullable Throwable th) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        int statusCode = this.extractor.getStatusCode(p);
        recordStats(httpRequestContext, q, statusCode);
        spanEnd(httpRequestContext.span, statusCode, th);
    }

    private void recordStats(HttpRequestContext httpRequestContext, @Nullable Q q, int i) {
        String str;
        String str2;
        double millis = (double) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - httpRequestContext.requestStartTime);
        String str3 = "";
        if (q == null) {
            str = str3;
        } else {
            str = this.extractor.getMethod(q);
        }
        TagContextBuilder builder = this.tagger.toBuilder(httpRequestContext.tagContext);
        TagKey tagKey = HttpMeasureConstants.HTTP_CLIENT_METHOD;
        if (str == null) {
            str = str3;
        }
        TagContextBuilder put = builder.put(tagKey, TagValue.create(str), HttpRequestContext.METADATA_NO_PROPAGATION);
        TagKey tagKey2 = HttpMeasureConstants.HTTP_CLIENT_STATUS;
        if (i == 0) {
            str2 = "error";
        } else {
            str2 = Integer.toString(i);
        }
        this.statsRecorder.newMeasureMap().put(HttpMeasureConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY, millis).put(HttpMeasureConstants.HTTP_CLIENT_SENT_BYTES, httpRequestContext.sentMessageSize.get()).put(HttpMeasureConstants.HTTP_CLIENT_RECEIVED_BYTES, httpRequestContext.receiveMessageSize.get()).record(put.put(tagKey2, TagValue.create(str2), HttpRequestContext.METADATA_NO_PROPAGATION).build());
    }
}
