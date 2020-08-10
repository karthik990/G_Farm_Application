package p043io.opencensus.trace;

import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.trace.MessageEvent */
public abstract class MessageEvent extends BaseMessageEvent {

    /* renamed from: io.opencensus.trace.MessageEvent$Builder */
    public static abstract class Builder {
        public abstract MessageEvent build();

        public abstract Builder setCompressedMessageSize(long j);

        /* access modifiers changed from: 0000 */
        public abstract Builder setMessageId(long j);

        /* access modifiers changed from: 0000 */
        public abstract Builder setType(Type type);

        public abstract Builder setUncompressedMessageSize(long j);

        Builder() {
        }
    }

    /* renamed from: io.opencensus.trace.MessageEvent$Type */
    public enum Type {
        SENT,
        RECEIVED
    }

    public abstract long getCompressedMessageSize();

    public abstract long getMessageId();

    public abstract Type getType();

    public abstract long getUncompressedMessageSize();

    public static Builder builder(Type type, long j) {
        return new Builder().setType((Type) C5887Utils.checkNotNull(type, "type")).setMessageId(j).setUncompressedMessageSize(0).setCompressedMessageSize(0);
    }

    MessageEvent() {
    }
}
