package p043io.opencensus.trace;

import java.util.Map;
import p043io.opencensus.trace.Link.Type;

/* renamed from: io.opencensus.trace.AutoValue_Link */
final class AutoValue_Link extends Link {
    private final Map<String, AttributeValue> attributes;
    private final SpanId spanId;
    private final TraceId traceId;
    private final Type type;

    AutoValue_Link(TraceId traceId2, SpanId spanId2, Type type2, Map<String, AttributeValue> map) {
        if (traceId2 != null) {
            this.traceId = traceId2;
            if (spanId2 != null) {
                this.spanId = spanId2;
                if (type2 != null) {
                    this.type = type2;
                    if (map != null) {
                        this.attributes = map;
                        return;
                    }
                    throw new NullPointerException("Null attributes");
                }
                throw new NullPointerException("Null type");
            }
            throw new NullPointerException("Null spanId");
        }
        throw new NullPointerException("Null traceId");
    }

    public TraceId getTraceId() {
        return this.traceId;
    }

    public SpanId getSpanId() {
        return this.spanId;
    }

    public Type getType() {
        return this.type;
    }

    public Map<String, AttributeValue> getAttributes() {
        return this.attributes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Link{traceId=");
        sb.append(this.traceId);
        sb.append(", spanId=");
        sb.append(this.spanId);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", attributes=");
        sb.append(this.attributes);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Link)) {
            return false;
        }
        Link link = (Link) obj;
        if (!this.traceId.equals(link.getTraceId()) || !this.spanId.equals(link.getSpanId()) || !this.type.equals(link.getType()) || !this.attributes.equals(link.getAttributes())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((this.traceId.hashCode() ^ 1000003) * 1000003) ^ this.spanId.hashCode()) * 1000003) ^ this.type.hashCode()) * 1000003) ^ this.attributes.hashCode();
    }
}
