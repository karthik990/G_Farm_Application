package p043io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import p043io.opencensus.contrib.http.util.HttpTraceAttributeConstants;
import p043io.opencensus.contrib.http.util.HttpTraceUtil;
import p043io.opencensus.tags.TagContext;
import p043io.opencensus.trace.AttributeValue;
import p043io.opencensus.trace.MessageEvent;
import p043io.opencensus.trace.MessageEvent.Type;
import p043io.opencensus.trace.Span;
import p043io.opencensus.trace.Span.Options;

/* renamed from: io.opencensus.contrib.http.AbstractHttpHandler */
abstract class AbstractHttpHandler<Q, P> {
    final HttpExtractor<Q, P> extractor;

    AbstractHttpHandler(HttpExtractor<Q, P> httpExtractor) {
        Preconditions.checkNotNull(httpExtractor, "extractor");
        this.extractor = httpExtractor;
    }

    static void recordMessageEvent(Span span, long j, Type type, long j2, long j3) {
        span.addMessageEvent(MessageEvent.builder(type, j).setUncompressedMessageSize(j2).setCompressedMessageSize(j3).build());
    }

    private static void putAttributeIfNotEmptyOrNull(Span span, String str, @Nullable String str2) {
        if (str2 != null && !str2.isEmpty()) {
            span.putAttribute(str, AttributeValue.stringAttributeValue(str2));
        }
    }

    public final void handleMessageSent(HttpRequestContext httpRequestContext, long j) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        httpRequestContext.sentMessageSize.addAndGet(j);
        if (httpRequestContext.span.getOptions().contains(Options.RECORD_EVENTS)) {
            recordMessageEvent(httpRequestContext.span, httpRequestContext.sentSeqId.addAndGet(1), Type.SENT, j, 0);
        }
    }

    public final void handleMessageReceived(HttpRequestContext httpRequestContext, long j) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        httpRequestContext.receiveMessageSize.addAndGet(j);
        if (httpRequestContext.span.getOptions().contains(Options.RECORD_EVENTS)) {
            recordMessageEvent(httpRequestContext.span, httpRequestContext.receviedSeqId.addAndGet(1), Type.RECEIVED, j, 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public void spanEnd(Span span, int i, @Nullable Throwable th) {
        if (span.getOptions().contains(Options.RECORD_EVENTS)) {
            span.putAttribute(HttpTraceAttributeConstants.HTTP_STATUS_CODE, AttributeValue.longAttributeValue((long) i));
            span.setStatus(HttpTraceUtil.parseResponseStatus(i, th));
        }
        span.end();
    }

    /* access modifiers changed from: 0000 */
    public final String getSpanName(Q q, HttpExtractor<Q, P> httpExtractor) {
        String path = httpExtractor.getPath(q);
        String str = "/";
        if (path == null) {
            path = str;
        }
        if (path.startsWith(str)) {
            return path;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(path);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final void addSpanRequestAttributes(Span span, Q q, HttpExtractor<Q, P> httpExtractor) {
        putAttributeIfNotEmptyOrNull(span, HttpTraceAttributeConstants.HTTP_USER_AGENT, httpExtractor.getUserAgent(q));
        putAttributeIfNotEmptyOrNull(span, HttpTraceAttributeConstants.HTTP_HOST, httpExtractor.getHost(q));
        putAttributeIfNotEmptyOrNull(span, HttpTraceAttributeConstants.HTTP_METHOD, httpExtractor.getMethod(q));
        putAttributeIfNotEmptyOrNull(span, HttpTraceAttributeConstants.HTTP_PATH, httpExtractor.getPath(q));
        putAttributeIfNotEmptyOrNull(span, "http.route", httpExtractor.getRoute(q));
        putAttributeIfNotEmptyOrNull(span, HttpTraceAttributeConstants.HTTP_URL, httpExtractor.getUrl(q));
    }

    public Span getSpanFromContext(HttpRequestContext httpRequestContext) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        return httpRequestContext.span;
    }

    /* access modifiers changed from: 0000 */
    public HttpRequestContext getNewContext(Span span, TagContext tagContext) {
        return new HttpRequestContext(span, tagContext);
    }
}
