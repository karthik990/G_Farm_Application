package p043io.grpc.internal;

import p043io.grpc.InternalChannelz.ChannelStats.Builder;
import p043io.grpc.InternalChannelz.ServerStats;

/* renamed from: io.grpc.internal.CallTracer */
final class CallTracer {
    static final Factory DEFAULT_FACTORY = new Factory() {
        public CallTracer create() {
            return new CallTracer(TimeProvider.SYSTEM_TIME_PROVIDER);
        }
    };
    private final LongCounter callsFailed = LongCounterFactory.create();
    private final LongCounter callsStarted = LongCounterFactory.create();
    private final LongCounter callsSucceeded = LongCounterFactory.create();
    private volatile long lastCallStartedNanos;
    private final TimeProvider timeProvider;

    /* renamed from: io.grpc.internal.CallTracer$Factory */
    public interface Factory {
        CallTracer create();
    }

    CallTracer(TimeProvider timeProvider2) {
        this.timeProvider = timeProvider2;
    }

    public void reportCallStarted() {
        this.callsStarted.add(1);
        this.lastCallStartedNanos = this.timeProvider.currentTimeNanos();
    }

    public void reportCallEnded(boolean z) {
        if (z) {
            this.callsSucceeded.add(1);
        } else {
            this.callsFailed.add(1);
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateBuilder(Builder builder) {
        builder.setCallsStarted(this.callsStarted.value()).setCallsSucceeded(this.callsSucceeded.value()).setCallsFailed(this.callsFailed.value()).setLastCallStartedNanos(this.lastCallStartedNanos);
    }

    /* access modifiers changed from: 0000 */
    public void updateBuilder(ServerStats.Builder builder) {
        builder.setCallsStarted(this.callsStarted.value()).setCallsSucceeded(this.callsSucceeded.value()).setCallsFailed(this.callsFailed.value()).setLastCallStartedNanos(this.lastCallStartedNanos);
    }

    public static Factory getDefaultFactory() {
        return DEFAULT_FACTORY;
    }
}
