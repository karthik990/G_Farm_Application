package p043io.opencensus.trace;

import javax.annotation.Nullable;
import p043io.opencensus.common.Timestamp;
import p043io.opencensus.internal.C5887Utils;

@Deprecated
/* renamed from: io.opencensus.trace.NetworkEvent */
public abstract class NetworkEvent extends BaseMessageEvent {

    @Deprecated
    /* renamed from: io.opencensus.trace.NetworkEvent$Builder */
    public static abstract class Builder {
        public abstract NetworkEvent build();

        public abstract Builder setCompressedMessageSize(long j);

        public abstract Builder setKernelTimestamp(@Nullable Timestamp timestamp);

        /* access modifiers changed from: 0000 */
        public abstract Builder setMessageId(long j);

        /* access modifiers changed from: 0000 */
        public abstract Builder setType(Type type);

        public abstract Builder setUncompressedMessageSize(long j);

        @Deprecated
        public Builder setMessageSize(long j) {
            return setUncompressedMessageSize(j);
        }

        Builder() {
        }
    }

    /* renamed from: io.opencensus.trace.NetworkEvent$Type */
    public enum Type {
        SENT,
        RECV
    }

    public abstract long getCompressedMessageSize();

    @Nullable
    public abstract Timestamp getKernelTimestamp();

    public abstract long getMessageId();

    public abstract Type getType();

    public abstract long getUncompressedMessageSize();

    public static Builder builder(Type type, long j) {
        return new Builder().setType((Type) C5887Utils.checkNotNull(type, "type")).setMessageId(j).setUncompressedMessageSize(0).setCompressedMessageSize(0);
    }

    @Deprecated
    public long getMessageSize() {
        return getUncompressedMessageSize();
    }

    NetworkEvent() {
    }
}
