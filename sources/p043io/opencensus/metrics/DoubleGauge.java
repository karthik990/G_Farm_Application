package p043io.opencensus.metrics;

import com.anjlab.android.iab.p020v3.Constants;
import com.braintreepayments.api.models.PostalAddressParser;
import java.util.List;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.metrics.DoubleGauge */
public abstract class DoubleGauge {

    /* renamed from: io.opencensus.metrics.DoubleGauge$DoublePoint */
    public static abstract class DoublePoint {
        public abstract void add(double d);

        public abstract void set(double d);
    }

    /* renamed from: io.opencensus.metrics.DoubleGauge$NoopDoubleGauge */
    private static final class NoopDoubleGauge extends DoubleGauge {
        private final int labelKeysSize;

        /* renamed from: io.opencensus.metrics.DoubleGauge$NoopDoubleGauge$NoopDoublePoint */
        private static final class NoopDoublePoint extends DoublePoint {
            /* access modifiers changed from: private */
            public static final NoopDoublePoint INSTANCE = new NoopDoublePoint();

            public void add(double d) {
            }

            public void set(double d) {
            }

            private NoopDoublePoint() {
            }
        }

        public void clear() {
        }

        static NoopDoubleGauge create(String str, String str2, String str3, List<LabelKey> list) {
            return new NoopDoubleGauge(str, str2, str3, list);
        }

        NoopDoubleGauge(String str, String str2, String str3, List<LabelKey> list) {
            C5887Utils.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
            C5887Utils.checkNotNull(str2, Constants.RESPONSE_DESCRIPTION);
            C5887Utils.checkNotNull(str3, "unit");
            C5887Utils.checkListElementNotNull((List) C5887Utils.checkNotNull(list, "labelKeys"), "labelKey");
            this.labelKeysSize = list.size();
        }

        public NoopDoublePoint getOrCreateTimeSeries(List<LabelValue> list) {
            C5887Utils.checkListElementNotNull((List) C5887Utils.checkNotNull(list, "labelValues"), "labelValue");
            C5887Utils.checkArgument(this.labelKeysSize == list.size(), "Label Keys and Label Values don't have same size.");
            return NoopDoublePoint.INSTANCE;
        }

        public NoopDoublePoint getDefaultTimeSeries() {
            return NoopDoublePoint.INSTANCE;
        }

        public void removeTimeSeries(List<LabelValue> list) {
            C5887Utils.checkNotNull(list, "labelValues");
        }
    }

    public abstract void clear();

    public abstract DoublePoint getDefaultTimeSeries();

    public abstract DoublePoint getOrCreateTimeSeries(List<LabelValue> list);

    public abstract void removeTimeSeries(List<LabelValue> list);

    static DoubleGauge newNoopDoubleGauge(String str, String str2, String str3, List<LabelKey> list) {
        return NoopDoubleGauge.create(str, str2, str3, list);
    }
}
