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
import p043io.opencensus.trace.Link;
import p043io.opencensus.trace.Link.Type;
import p043io.opencensus.trace.Span;
import p043io.opencensus.trace.Span.Kind;
import p043io.opencensus.trace.Span.Options;
import p043io.opencensus.trace.SpanBuilder;
import p043io.opencensus.trace.SpanContext;
import p043io.opencensus.trace.Tracer;
import p043io.opencensus.trace.propagation.SpanContextParseException;
import p043io.opencensus.trace.propagation.TextFormat;
import p043io.opencensus.trace.propagation.TextFormat.Getter;

/* renamed from: io.opencensus.contrib.http.HttpServerHandler */
public class HttpServerHandler<Q, P, C> extends AbstractHttpHandler<Q, P> {
    private final Getter<C> getter;
    private final Boolean publicEndpoint;
    private final StatsRecorder statsRecorder = Stats.getStatsRecorder();
    private final Tagger tagger = Tags.getTagger();
    private final TextFormat textFormat;
    private final Tracer tracer;

    public /* bridge */ /* synthetic */ Span getSpanFromContext(HttpRequestContext httpRequestContext) {
        return super.getSpanFromContext(httpRequestContext);
    }

    public HttpServerHandler(Tracer tracer2, HttpExtractor<Q, P> httpExtractor, TextFormat textFormat2, Getter<C> getter2, Boolean bool) {
        super(httpExtractor);
        Preconditions.checkNotNull(tracer2, "tracer");
        Preconditions.checkNotNull(textFormat2, "textFormat");
        Preconditions.checkNotNull(getter2, "getter");
        Preconditions.checkNotNull(bool, "publicEndpoint");
        this.tracer = tracer2;
        this.textFormat = textFormat2;
        this.getter = getter2;
        this.publicEndpoint = bool;
    }

    public HttpRequestContext handleStart(C c, Q q) {
        SpanContext spanContext;
        SpanBuilder spanBuilder;
        Preconditions.checkNotNull(c, "carrier");
        Preconditions.checkNotNull(q, "request");
        String spanName = getSpanName(q, this.extractor);
        try {
            spanContext = this.textFormat.extract(c, this.getter);
        } catch (SpanContextParseException unused) {
            spanContext = null;
        }
        if (spanContext == null || this.publicEndpoint.booleanValue()) {
            spanBuilder = this.tracer.spanBuilder(spanName);
        } else {
            spanBuilder = this.tracer.spanBuilderWithRemoteParent(spanName, spanContext);
        }
        Span startSpan = spanBuilder.setSpanKind(Kind.SERVER).startSpan();
        if (this.publicEndpoint.booleanValue() && spanContext != null) {
            startSpan.addLink(Link.fromSpanContext(spanContext, Type.PARENT_LINKED_SPAN));
        }
        if (startSpan.getOptions().contains(Options.RECORD_EVENTS)) {
            addSpanRequestAttributes(startSpan, q, this.extractor);
        }
        return getNewContext(startSpan, this.tagger.getCurrentTagContext());
    }

    public void handleEnd(HttpRequestContext httpRequestContext, Q q, @Nullable P p, @Nullable Throwable th) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        Preconditions.checkNotNull(q, "request");
        int statusCode = this.extractor.getStatusCode(p);
        recordStats(httpRequestContext, q, statusCode);
        spanEnd(httpRequestContext.span, statusCode, th);
    }

    private void recordStats(HttpRequestContext httpRequestContext, Q q, int i) {
        String str;
        double millis = (double) TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - httpRequestContext.requestStartTime);
        String method = this.extractor.getMethod(q);
        String route = this.extractor.getRoute(q);
        TagContextBuilder builder = this.tagger.toBuilder(httpRequestContext.tagContext);
        TagKey tagKey = HttpMeasureConstants.HTTP_SERVER_METHOD;
        String str2 = "";
        if (method == null) {
            method = str2;
        }
        TagContextBuilder put = builder.put(tagKey, TagValue.create(method), HttpRequestContext.METADATA_NO_PROPAGATION);
        TagKey tagKey2 = HttpMeasureConstants.HTTP_SERVER_ROUTE;
        if (route == null) {
            route = str2;
        }
        TagContextBuilder put2 = put.put(tagKey2, TagValue.create(route), HttpRequestContext.METADATA_NO_PROPAGATION);
        TagKey tagKey3 = HttpMeasureConstants.HTTP_SERVER_STATUS;
        if (i == 0) {
            str = "error";
        } else {
            str = Integer.toString(i);
        }
        this.statsRecorder.newMeasureMap().put(HttpMeasureConstants.HTTP_SERVER_LATENCY, millis).put(HttpMeasureConstants.HTTP_SERVER_RECEIVED_BYTES, httpRequestContext.receiveMessageSize.get()).put(HttpMeasureConstants.HTTP_SERVER_SENT_BYTES, httpRequestContext.sentMessageSize.get()).record(put2.put(tagKey3, TagValue.create(str), HttpRequestContext.METADATA_NO_PROPAGATION).build());
    }
}
