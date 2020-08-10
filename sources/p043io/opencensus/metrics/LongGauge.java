package p043io.opencensus.metrics;

import java.util.List;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.metrics.LongGauge */
public abstract class LongGauge {

    /* renamed from: io.opencensus.metrics.LongGauge$LongPoint */
    public static abstract class LongPoint {
        public abstract void add(long j);

        public abstract void set(long j);
    }

    /* renamed from: io.opencensus.metrics.LongGauge$NoopLongGauge */
    private static final class NoopLongGauge extends LongGauge {
        private final int labelKeysSize;

        /* renamed from: io.opencensus.metrics.LongGauge$NoopLongGauge$NoopLongPoint */
        private static final class NoopLongPoint extends LongPoint {
            /* access modifiers changed from: private */
            public static final NoopLongPoint INSTANCE = new NoopLongPoint();

            public void add(long j) {
            }

            public void set(long j) {
            }

            private NoopLongPoint() {
            }
        }

        public void clear() {
        }

        static NoopLongGauge create(String str, String str2, String str3, List<LabelKey> list) {
            return new NoopLongGauge(str, str2, str3, list);
        }

        NoopLongGauge(String str, String str2, String str3, List<LabelKey> list) {
            this.labelKeysSize = list.size();
        }

        public NoopLongPoint getOrCreateTimeSeries(List<LabelValue> list) {
            C5887Utils.checkListElementNotNull((List) C5887Utils.checkNotNull(list, "labelValues"), "labelValue");
            C5887Utils.checkArgument(this.labelKeysSize == list.size(), "Label Keys and Label Values don't have same size.");
            return NoopLongPoint.INSTANCE;
        }

        public NoopLongPoint getDefaultTimeSeries() {
            return NoopLongPoint.INSTANCE;
        }

        public void removeTimeSeries(List<LabelValue> list) {
            C5887Utils.checkNotNull(list, "labelValues");
        }
    }

    public abstract void clear();

    public abstract LongPoint getDefaultTimeSeries();

    public abstract LongPoint getOrCreateTimeSeries(List<LabelValue> list);

    public abstract void removeTimeSeries(List<LabelValue> list);

    static LongGauge newNoopLongGauge(String str, String str2, String str3, List<LabelKey> list) {
        return NoopLongGauge.create(str, str2, str3, list);
    }
}
