package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import p043io.grpc.InternalChannelz.ChannelStats;
import p043io.grpc.InternalChannelz.ChannelTrace;
import p043io.grpc.InternalChannelz.ChannelTrace.Event;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Severity;

/* renamed from: io.grpc.internal.ChannelTracer */
final class ChannelTracer {
    private final long channelCreationTimeNanos;
    private final Collection<Event> events;
    /* access modifiers changed from: private */
    public int eventsLogged;
    private final Object lock = new Object();

    ChannelTracer(final int i, long j, String str) {
        Preconditions.checkArgument(i > 0, "maxEvents must be greater than zero");
        Preconditions.checkNotNull(str, "channelType");
        this.events = new ArrayDeque<Event>() {
            public boolean add(Event event) {
                if (size() == i) {
                    removeFirst();
                }
                ChannelTracer.this.eventsLogged = ChannelTracer.this.eventsLogged + 1;
                return super.add(event);
            }
        };
        this.channelCreationTimeNanos = j;
        Builder builder = new Builder();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" created");
        reportEvent(builder.setDescription(sb.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(j).build());
    }

    /* access modifiers changed from: 0000 */
    public void reportEvent(Event event) {
        synchronized (this.lock) {
            this.events.add(event);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateBuilder(ChannelStats.Builder builder) {
        int i;
        ArrayList arrayList;
        synchronized (this.lock) {
            i = this.eventsLogged;
            arrayList = new ArrayList(this.events);
        }
        builder.setChannelTrace(new ChannelTrace.Builder().setNumEventsLogged((long) i).setCreationTimeNanos(this.channelCreationTimeNanos).setEvents(arrayList).build());
    }
}
