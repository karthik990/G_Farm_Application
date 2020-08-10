package p043io.opencensus.trace;

import javax.annotation.Nullable;

/* renamed from: io.opencensus.trace.EndSpanOptions */
public abstract class EndSpanOptions {
    public static final EndSpanOptions DEFAULT = builder().build();

    /* renamed from: io.opencensus.trace.EndSpanOptions$Builder */
    public static abstract class Builder {
        public abstract EndSpanOptions build();

        public abstract Builder setSampleToLocalSpanStore(boolean z);

        public abstract Builder setStatus(Status status);

        Builder() {
        }
    }

    public abstract boolean getSampleToLocalSpanStore();

    @Nullable
    public abstract Status getStatus();

    public static Builder builder() {
        return new Builder().setSampleToLocalSpanStore(false);
    }

    EndSpanOptions() {
    }
}
