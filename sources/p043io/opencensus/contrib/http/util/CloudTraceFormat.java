package p043io.opencensus.contrib.http.util;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedInts;
import com.google.common.primitives.UnsignedLongs;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import p043io.opencensus.trace.SpanContext;
import p043io.opencensus.trace.SpanId;
import p043io.opencensus.trace.TraceId;
import p043io.opencensus.trace.TraceOptions;
import p043io.opencensus.trace.Tracestate;
import p043io.opencensus.trace.propagation.SpanContextParseException;
import p043io.opencensus.trace.propagation.TextFormat;
import p043io.opencensus.trace.propagation.TextFormat.Getter;
import p043io.opencensus.trace.propagation.TextFormat.Setter;

/* renamed from: io.opencensus.contrib.http.util.CloudTraceFormat */
final class CloudTraceFormat extends TextFormat {
    static final int CLOUD_TRACE_IS_SAMPLED = 1;
    static final List<String> FIELDS = Collections.singletonList(HEADER_NAME);
    static final String HEADER_NAME = "X-Cloud-Trace-Context";
    static final int MIN_HEADER_SIZE = 34;
    static final String NOT_SAMPLED = "0";
    static final TraceOptions OPTIONS_NOT_SAMPLED = TraceOptions.DEFAULT;
    static final TraceOptions OPTIONS_SAMPLED = TraceOptions.builder().setIsSampled(true).build();
    static final String SAMPLED = "1";
    static final char SPAN_ID_DELIMITER = '/';
    static final int SPAN_ID_START_POS = 33;
    private static final Tracestate TRACESTATE_DEFAULT = Tracestate.builder().build();
    static final int TRACE_ID_SIZE = 32;
    static final String TRACE_OPTION_DELIMITER = ";o=";
    static final int TRACE_OPTION_DELIMITER_SIZE = 3;

    CloudTraceFormat() {
    }

    public List<String> fields() {
        return FIELDS;
    }

    public <C> void inject(SpanContext spanContext, C c, Setter<C> setter) {
        Preconditions.checkNotNull(spanContext, "spanContext");
        Preconditions.checkNotNull(setter, "setter");
        Preconditions.checkNotNull(c, "carrier");
        StringBuilder sb = new StringBuilder();
        sb.append(spanContext.getTraceId().toLowerBase16());
        sb.append('/');
        sb.append(UnsignedLongs.toString(spanIdToLong(spanContext.getSpanId())));
        sb.append(TRACE_OPTION_DELIMITER);
        sb.append(spanContext.getTraceOptions().isSampled() ? "1" : NOT_SAMPLED);
        setter.put(c, HEADER_NAME, sb.toString());
    }

    public <C> SpanContext extract(C c, Getter<C> getter) throws SpanContextParseException {
        Preconditions.checkNotNull(c, "carrier");
        Preconditions.checkNotNull(getter, "getter");
        try {
            String str = getter.get(c, HEADER_NAME);
            if (str == null || str.length() < 34) {
                throw new SpanContextParseException("Missing or too short header: X-Cloud-Trace-Context");
            }
            Preconditions.checkArgument(str.charAt(32) == '/', "Invalid TRACE_ID size");
            TraceId fromLowerBase16 = TraceId.fromLowerBase16(str.subSequence(0, 32));
            int indexOf = str.indexOf(TRACE_OPTION_DELIMITER, 32);
            SpanId longToSpanId = longToSpanId(UnsignedLongs.parseUnsignedLong(str.subSequence(33, indexOf < 0 ? str.length() : indexOf).toString(), 10));
            TraceOptions traceOptions = OPTIONS_NOT_SAMPLED;
            if (indexOf > 0 && (UnsignedInts.parseUnsignedInt(str.substring(indexOf + TRACE_OPTION_DELIMITER_SIZE), 10) & 1) != 0) {
                traceOptions = OPTIONS_SAMPLED;
            }
            return SpanContext.create(fromLowerBase16, longToSpanId, traceOptions, TRACESTATE_DEFAULT);
        } catch (IllegalArgumentException e) {
            throw new SpanContextParseException("Invalid input", e);
        }
    }

    private static SpanId longToSpanId(long j) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.putLong(j);
        return SpanId.fromBytes(allocate.array());
    }

    private static long spanIdToLong(SpanId spanId) {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.put(spanId.getBytes());
        return allocate.getLong(0);
    }
}
