package p043io.opencensus.trace.config;

import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.trace.Sampler;
import p043io.opencensus.trace.samplers.Samplers;

/* renamed from: io.opencensus.trace.config.TraceParams */
public abstract class TraceParams {
    public static final TraceParams DEFAULT = builder().setSampler(DEFAULT_SAMPLER).setMaxNumberOfAttributes(32).setMaxNumberOfAnnotations(32).setMaxNumberOfMessageEvents(128).setMaxNumberOfLinks(32).build();
    private static final double DEFAULT_PROBABILITY = 1.0E-4d;
    private static final Sampler DEFAULT_SAMPLER = Samplers.probabilitySampler(DEFAULT_PROBABILITY);
    private static final int DEFAULT_SPAN_MAX_NUM_ANNOTATIONS = 32;
    private static final int DEFAULT_SPAN_MAX_NUM_ATTRIBUTES = 32;
    private static final int DEFAULT_SPAN_MAX_NUM_LINKS = 32;
    private static final int DEFAULT_SPAN_MAX_NUM_MESSAGE_EVENTS = 128;

    /* renamed from: io.opencensus.trace.config.TraceParams$Builder */
    public static abstract class Builder {
        /* access modifiers changed from: 0000 */
        public abstract TraceParams autoBuild();

        public abstract Builder setMaxNumberOfAnnotations(int i);

        public abstract Builder setMaxNumberOfAttributes(int i);

        public abstract Builder setMaxNumberOfLinks(int i);

        public abstract Builder setMaxNumberOfMessageEvents(int i);

        public abstract Builder setSampler(Sampler sampler);

        @Deprecated
        public Builder setMaxNumberOfNetworkEvents(int i) {
            return setMaxNumberOfMessageEvents(i);
        }

        public TraceParams build() {
            TraceParams autoBuild = autoBuild();
            boolean z = true;
            C5887Utils.checkArgument(autoBuild.getMaxNumberOfAttributes() > 0, "maxNumberOfAttributes");
            C5887Utils.checkArgument(autoBuild.getMaxNumberOfAnnotations() > 0, "maxNumberOfAnnotations");
            C5887Utils.checkArgument(autoBuild.getMaxNumberOfMessageEvents() > 0, "maxNumberOfMessageEvents");
            if (autoBuild.getMaxNumberOfLinks() <= 0) {
                z = false;
            }
            C5887Utils.checkArgument(z, "maxNumberOfLinks");
            return autoBuild;
        }
    }

    public abstract int getMaxNumberOfAnnotations();

    public abstract int getMaxNumberOfAttributes();

    public abstract int getMaxNumberOfLinks();

    public abstract int getMaxNumberOfMessageEvents();

    public abstract Sampler getSampler();

    public abstract Builder toBuilder();

    @Deprecated
    public int getMaxNumberOfNetworkEvents() {
        return getMaxNumberOfMessageEvents();
    }

    private static Builder builder() {
        return new Builder();
    }
}
