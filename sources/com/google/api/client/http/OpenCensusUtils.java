package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p043io.opencensus.contrib.http.util.HttpPropagationUtil;
import p043io.opencensus.trace.BlankSpan;
import p043io.opencensus.trace.EndSpanOptions;
import p043io.opencensus.trace.EndSpanOptions.Builder;
import p043io.opencensus.trace.MessageEvent;
import p043io.opencensus.trace.MessageEvent.Type;
import p043io.opencensus.trace.Span;
import p043io.opencensus.trace.Status;
import p043io.opencensus.trace.Tracer;
import p043io.opencensus.trace.Tracing;
import p043io.opencensus.trace.propagation.TextFormat;
import p043io.opencensus.trace.propagation.TextFormat.Setter;

public class OpenCensusUtils {
    public static final String SPAN_NAME_HTTP_REQUEST_EXECUTE;
    private static final AtomicLong idGenerator = new AtomicLong();
    private static volatile boolean isRecordEvent = true;
    private static final Logger logger = Logger.getLogger(OpenCensusUtils.class.getName());
    @Nullable
    static volatile TextFormat propagationTextFormat;
    @Nullable
    static volatile Setter propagationTextFormatSetter;
    private static final Tracer tracer = Tracing.getTracer();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("Sent.");
        sb.append(HttpRequest.class.getName());
        sb.append(".execute");
        SPAN_NAME_HTTP_REQUEST_EXECUTE = sb.toString();
        propagationTextFormat = null;
        propagationTextFormatSetter = null;
        try {
            propagationTextFormat = HttpPropagationUtil.getCloudTraceFormat();
            propagationTextFormatSetter = new Setter<HttpHeaders>() {
                public void put(HttpHeaders httpHeaders, String str, String str2) {
                    httpHeaders.set(str, (Object) str2);
                }
            };
        } catch (Exception e) {
            logger.log(Level.WARNING, "Cannot initialize default OpenCensus HTTP propagation text format.", e);
        }
        try {
            Tracing.getExportComponent().getSampledSpanStore().registerSpanNamesForCollection(ImmutableList.m1884of(SPAN_NAME_HTTP_REQUEST_EXECUTE));
        } catch (Exception e2) {
            logger.log(Level.WARNING, "Cannot register default OpenCensus span names for collection.", e2);
        }
    }

    public static void setPropagationTextFormat(@Nullable TextFormat textFormat) {
        propagationTextFormat = textFormat;
    }

    public static void setPropagationTextFormatSetter(@Nullable Setter setter) {
        propagationTextFormatSetter = setter;
    }

    public static void setIsRecordEvent(boolean z) {
        isRecordEvent = z;
    }

    public static Tracer getTracer() {
        return tracer;
    }

    public static boolean isRecordEvent() {
        return isRecordEvent;
    }

    public static void propagateTracingContext(Span span, HttpHeaders httpHeaders) {
        boolean z = true;
        Preconditions.checkArgument(span != null, "span should not be null.");
        if (httpHeaders == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "headers should not be null.");
        if (propagationTextFormat != null && propagationTextFormatSetter != null && !span.equals(BlankSpan.INSTANCE)) {
            propagationTextFormat.inject(span.getContext(), httpHeaders, propagationTextFormatSetter);
        }
    }

    public static EndSpanOptions getEndSpanOptions(@Nullable Integer num) {
        Builder builder = EndSpanOptions.builder();
        if (num == null) {
            builder.setStatus(Status.UNKNOWN);
        } else if (!HttpStatusCodes.isSuccess(num.intValue())) {
            int intValue = num.intValue();
            if (intValue == 400) {
                builder.setStatus(Status.INVALID_ARGUMENT);
            } else if (intValue == 401) {
                builder.setStatus(Status.UNAUTHENTICATED);
            } else if (intValue == 403) {
                builder.setStatus(Status.PERMISSION_DENIED);
            } else if (intValue == 404) {
                builder.setStatus(Status.NOT_FOUND);
            } else if (intValue == 412) {
                builder.setStatus(Status.FAILED_PRECONDITION);
            } else if (intValue != 500) {
                builder.setStatus(Status.UNKNOWN);
            } else {
                builder.setStatus(Status.UNAVAILABLE);
            }
        } else {
            builder.setStatus(Status.f3765OK);
        }
        return builder.build();
    }

    public static void recordSentMessageEvent(Span span, long j) {
        recordMessageEvent(span, j, Type.SENT);
    }

    public static void recordReceivedMessageEvent(Span span, long j) {
        recordMessageEvent(span, j, Type.RECEIVED);
    }

    static void recordMessageEvent(Span span, long j, Type type) {
        Preconditions.checkArgument(span != null, "span should not be null.");
        if (j < 0) {
            j = 0;
        }
        span.addMessageEvent(MessageEvent.builder(type, idGenerator.getAndIncrement()).setUncompressedMessageSize(j).build());
    }

    private OpenCensusUtils() {
    }
}
