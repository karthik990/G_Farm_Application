package p043io.opencensus.trace.export;

import java.util.List;
import p043io.opencensus.trace.export.SpanData.TimedEvent;
import p043io.opencensus.trace.export.SpanData.TimedEvents;

/* renamed from: io.opencensus.trace.export.AutoValue_SpanData_TimedEvents */
final class AutoValue_SpanData_TimedEvents<T> extends TimedEvents<T> {
    private final int droppedEventsCount;
    private final List<TimedEvent<T>> events;

    AutoValue_SpanData_TimedEvents(List<TimedEvent<T>> list, int i) {
        if (list != null) {
            this.events = list;
            this.droppedEventsCount = i;
            return;
        }
        throw new NullPointerException("Null events");
    }

    public List<TimedEvent<T>> getEvents() {
        return this.events;
    }

    public int getDroppedEventsCount() {
        return this.droppedEventsCount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TimedEvents{events=");
        sb.append(this.events);
        sb.append(", droppedEventsCount=");
        sb.append(this.droppedEventsCount);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimedEvents)) {
            return false;
        }
        TimedEvents timedEvents = (TimedEvents) obj;
        if (!this.events.equals(timedEvents.getEvents()) || this.droppedEventsCount != timedEvents.getDroppedEventsCount()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.events.hashCode() ^ 1000003) * 1000003) ^ this.droppedEventsCount;
    }
}
