package p043io.opencensus.metrics;

import java.util.List;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.metrics.LongCumulative */
public abstract class LongCumulative {

    /* renamed from: io.opencensus.metrics.LongCumulative$LongPoint */
    public static abstract class LongPoint {
        public abstract void add(long j);
    }

    /* renamed from: io.opencensus.metrics.LongCumulative$NoopLongCumulative */
    private static final class NoopLongCumulative extends LongCumulative {
        private final int labelKeysSize;

        /* renamed from: io.opencensus.metrics.LongCumulative$NoopLongCumulative$NoopLongPoint */
        private static final class NoopLongPoint extends LongPoint {
            /* access modifiers changed from: private */
            public static final NoopLongPoint INSTANCE = new NoopLongPoint();

            public void add(long j) {
            }

            private NoopLongPoint() {
            }
        }

        public void clear() {
        }

        static NoopLongCumulative create(String str, String str2, String str3, List<LabelKey> list) {
            return new NoopLongCumulative(str, str2, str3, list);
        }

        NoopLongCumulative(String str, String str2, String str3, List<LabelKey> list) {
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

    static LongCumulative newNoopLongCumulative(String str, String str2, String str3, List<LabelKey> list) {
        return NoopLongCumulative.create(str, str2, str3, list);
    }
}
