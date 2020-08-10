package p043io.opencensus.trace.export;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.trace.export.RunningSpanStore */
public abstract class RunningSpanStore {
    private static final RunningSpanStore NOOP_RUNNING_SPAN_STORE = new NoopRunningSpanStore();

    /* renamed from: io.opencensus.trace.export.RunningSpanStore$Filter */
    public static abstract class Filter {
        public abstract int getMaxSpansToReturn();

        public abstract String getSpanName();

        Filter() {
        }

        public static Filter create(String str, int i) {
            C5887Utils.checkArgument(i >= 0, "Negative maxSpansToReturn.");
            return new AutoValue_RunningSpanStore_Filter(str, i);
        }
    }

    /* renamed from: io.opencensus.trace.export.RunningSpanStore$NoopRunningSpanStore */
    private static final class NoopRunningSpanStore extends RunningSpanStore {
        private static final Summary EMPTY_SUMMARY = Summary.create(Collections.emptyMap());

        private NoopRunningSpanStore() {
        }

        public Summary getSummary() {
            return EMPTY_SUMMARY;
        }

        public Collection<SpanData> getRunningSpans(Filter filter) {
            C5887Utils.checkNotNull(filter, "filter");
            return Collections.emptyList();
        }
    }

    /* renamed from: io.opencensus.trace.export.RunningSpanStore$PerSpanNameSummary */
    public static abstract class PerSpanNameSummary {
        public abstract int getNumRunningSpans();

        PerSpanNameSummary() {
        }

        public static PerSpanNameSummary create(int i) {
            C5887Utils.checkArgument(i >= 0, "Negative numRunningSpans.");
            return new AutoValue_RunningSpanStore_PerSpanNameSummary(i);
        }
    }

    /* renamed from: io.opencensus.trace.export.RunningSpanStore$Summary */
    public static abstract class Summary {
        public abstract Map<String, PerSpanNameSummary> getPerSpanNameSummary();

        Summary() {
        }

        public static Summary create(Map<String, PerSpanNameSummary> map) {
            return new AutoValue_RunningSpanStore_Summary(Collections.unmodifiableMap(new HashMap((Map) C5887Utils.checkNotNull(map, "perSpanNameSummary"))));
        }
    }

    public abstract Collection<SpanData> getRunningSpans(Filter filter);

    public abstract Summary getSummary();

    protected RunningSpanStore() {
    }

    static RunningSpanStore getNoopRunningSpanStore() {
        return NOOP_RUNNING_SPAN_STORE;
    }
}
