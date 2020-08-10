package p043io.opencensus.trace.export;

import p043io.opencensus.common.Timestamp;
import p043io.opencensus.trace.export.SpanData.TimedEvent;

/* renamed from: io.opencensus.trace.export.AutoValue_SpanData_TimedEvent */
final class AutoValue_SpanData_TimedEvent<T> extends TimedEvent<T> {
    private final T event;
    private final Timestamp timestamp;

    AutoValue_SpanData_TimedEvent(Timestamp timestamp2, T t) {
        if (timestamp2 != null) {
            this.timestamp = timestamp2;
            if (t != null) {
                this.event = t;
                return;
            }
            throw new NullPointerException("Null event");
        }
        throw new NullPointerException("Null timestamp");
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public T getEvent() {
        return this.event;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TimedEvent{timestamp=");
        sb.append(this.timestamp);
        sb.append(", event=");
        sb.append(this.event);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimedEvent)) {
            return false;
        }
        TimedEvent timedEvent = (TimedEvent) obj;
        if (!this.timestamp.equals(timedEvent.getTimestamp()) || !this.event.equals(timedEvent.getEvent())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.timestamp.hashCode() ^ 1000003) * 1000003) ^ this.event.hashCode();
    }
}
