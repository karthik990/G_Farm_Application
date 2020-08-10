package p043io.opencensus.trace.export;

import javax.annotation.Nullable;
import p043io.opencensus.common.Timestamp;
import p043io.opencensus.trace.Annotation;
import p043io.opencensus.trace.MessageEvent;
import p043io.opencensus.trace.Span.Kind;
import p043io.opencensus.trace.SpanContext;
import p043io.opencensus.trace.SpanId;
import p043io.opencensus.trace.Status;
import p043io.opencensus.trace.export.SpanData.Attributes;
import p043io.opencensus.trace.export.SpanData.Links;
import p043io.opencensus.trace.export.SpanData.TimedEvents;

/* renamed from: io.opencensus.trace.export.AutoValue_SpanData */
final class AutoValue_SpanData extends SpanData {
    private final TimedEvents<Annotation> annotations;
    private final Attributes attributes;
    private final Integer childSpanCount;
    private final SpanContext context;
    private final Timestamp endTimestamp;
    private final Boolean hasRemoteParent;
    private final Kind kind;
    private final Links links;
    private final TimedEvents<MessageEvent> messageEvents;
    private final String name;
    private final SpanId parentSpanId;
    private final Timestamp startTimestamp;
    private final Status status;

    AutoValue_SpanData(SpanContext spanContext, @Nullable SpanId spanId, @Nullable Boolean bool, String str, @Nullable Kind kind2, Timestamp timestamp, Attributes attributes2, TimedEvents<Annotation> timedEvents, TimedEvents<MessageEvent> timedEvents2, Links links2, @Nullable Integer num, @Nullable Status status2, @Nullable Timestamp timestamp2) {
        if (spanContext != null) {
            this.context = spanContext;
            this.parentSpanId = spanId;
            this.hasRemoteParent = bool;
            if (str != null) {
                this.name = str;
                this.kind = kind2;
                if (timestamp != null) {
                    this.startTimestamp = timestamp;
                    if (attributes2 != null) {
                        this.attributes = attributes2;
                        if (timedEvents != null) {
                            this.annotations = timedEvents;
                            if (timedEvents2 != null) {
                                this.messageEvents = timedEvents2;
                                if (links2 != null) {
                                    this.links = links2;
                                    this.childSpanCount = num;
                                    this.status = status2;
                                    this.endTimestamp = timestamp2;
                                    return;
                                }
                                throw new NullPointerException("Null links");
                            }
                            throw new NullPointerException("Null messageEvents");
                        }
                        throw new NullPointerException("Null annotations");
                    }
                    throw new NullPointerException("Null attributes");
                }
                throw new NullPointerException("Null startTimestamp");
            }
            throw new NullPointerException("Null name");
        }
        throw new NullPointerException("Null context");
    }

    public SpanContext getContext() {
        return this.context;
    }

    @Nullable
    public SpanId getParentSpanId() {
        return this.parentSpanId;
    }

    @Nullable
    public Boolean getHasRemoteParent() {
        return this.hasRemoteParent;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public Kind getKind() {
        return this.kind;
    }

    public Timestamp getStartTimestamp() {
        return this.startTimestamp;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public TimedEvents<Annotation> getAnnotations() {
        return this.annotations;
    }

    public TimedEvents<MessageEvent> getMessageEvents() {
        return this.messageEvents;
    }

    public Links getLinks() {
        return this.links;
    }

    @Nullable
    public Integer getChildSpanCount() {
        return this.childSpanCount;
    }

    @Nullable
    public Status getStatus() {
        return this.status;
    }

    @Nullable
    public Timestamp getEndTimestamp() {
        return this.endTimestamp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SpanData{context=");
        sb.append(this.context);
        sb.append(", parentSpanId=");
        sb.append(this.parentSpanId);
        sb.append(", hasRemoteParent=");
        sb.append(this.hasRemoteParent);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", kind=");
        sb.append(this.kind);
        sb.append(", startTimestamp=");
        sb.append(this.startTimestamp);
        sb.append(", attributes=");
        sb.append(this.attributes);
        sb.append(", annotations=");
        sb.append(this.annotations);
        sb.append(", messageEvents=");
        sb.append(this.messageEvents);
        sb.append(", links=");
        sb.append(this.links);
        sb.append(", childSpanCount=");
        sb.append(this.childSpanCount);
        sb.append(", status=");
        sb.append(this.status);
        sb.append(", endTimestamp=");
        sb.append(this.endTimestamp);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SpanData)) {
            return false;
        }
        SpanData spanData = (SpanData) obj;
        if (this.context.equals(spanData.getContext())) {
            SpanId spanId = this.parentSpanId;
            if (spanId != null ? spanId.equals(spanData.getParentSpanId()) : spanData.getParentSpanId() == null) {
                Boolean bool = this.hasRemoteParent;
                if (bool != null ? bool.equals(spanData.getHasRemoteParent()) : spanData.getHasRemoteParent() == null) {
                    if (this.name.equals(spanData.getName())) {
                        Kind kind2 = this.kind;
                        if (kind2 != null ? kind2.equals(spanData.getKind()) : spanData.getKind() == null) {
                            if (this.startTimestamp.equals(spanData.getStartTimestamp()) && this.attributes.equals(spanData.getAttributes()) && this.annotations.equals(spanData.getAnnotations()) && this.messageEvents.equals(spanData.getMessageEvents()) && this.links.equals(spanData.getLinks())) {
                                Integer num = this.childSpanCount;
                                if (num != null ? num.equals(spanData.getChildSpanCount()) : spanData.getChildSpanCount() == null) {
                                    Status status2 = this.status;
                                    if (status2 != null ? status2.equals(spanData.getStatus()) : spanData.getStatus() == null) {
                                        Timestamp timestamp = this.endTimestamp;
                                        if (timestamp != null) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        int hashCode = (this.context.hashCode() ^ 1000003) * 1000003;
        SpanId spanId = this.parentSpanId;
        int i = 0;
        int hashCode2 = (hashCode ^ (spanId == null ? 0 : spanId.hashCode())) * 1000003;
        Boolean bool = this.hasRemoteParent;
        int hashCode3 = (((hashCode2 ^ (bool == null ? 0 : bool.hashCode())) * 1000003) ^ this.name.hashCode()) * 1000003;
        Kind kind2 = this.kind;
        int hashCode4 = (((((((((((hashCode3 ^ (kind2 == null ? 0 : kind2.hashCode())) * 1000003) ^ this.startTimestamp.hashCode()) * 1000003) ^ this.attributes.hashCode()) * 1000003) ^ this.annotations.hashCode()) * 1000003) ^ this.messageEvents.hashCode()) * 1000003) ^ this.links.hashCode()) * 1000003;
        Integer num = this.childSpanCount;
        int hashCode5 = (hashCode4 ^ (num == null ? 0 : num.hashCode())) * 1000003;
        Status status2 = this.status;
        int hashCode6 = (hashCode5 ^ (status2 == null ? 0 : status2.hashCode())) * 1000003;
        Timestamp timestamp = this.endTimestamp;
        if (timestamp != null) {
            i = timestamp.hashCode();
        }
        return hashCode6 ^ i;
    }
}
