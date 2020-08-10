package p043io.opencensus.metrics;

import com.anjlab.android.iab.p020v3.Constants;
import com.braintreepayments.api.models.PostalAddressParser;
import java.util.List;
import p043io.opencensus.common.ToLongFunction;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.metrics.DerivedLongCumulative */
public abstract class DerivedLongCumulative {

    /* renamed from: io.opencensus.metrics.DerivedLongCumulative$NoopDerivedLongCumulative */
    private static final class NoopDerivedLongCumulative extends DerivedLongCumulative {
        private final int labelKeysSize;

        public void clear() {
        }

        static NoopDerivedLongCumulative create(String str, String str2, String str3, List<LabelKey> list) {
            return new NoopDerivedLongCumulative(str, str2, str3, list);
        }

        NoopDerivedLongCumulative(String str, String str2, String str3, List<LabelKey> list) {
            C5887Utils.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
            C5887Utils.checkNotNull(str2, Constants.RESPONSE_DESCRIPTION);
            C5887Utils.checkNotNull(str3, "unit");
            C5887Utils.checkListElementNotNull((List) C5887Utils.checkNotNull(list, "labelKeys"), "labelKey");
            this.labelKeysSize = list.size();
        }

        public <T> void createTimeSeries(List<LabelValue> list, T t, ToLongFunction<T> toLongFunction) {
            C5887Utils.checkListElementNotNull((List) C5887Utils.checkNotNull(list, "labelValues"), "labelValue");
            C5887Utils.checkArgument(this.labelKeysSize == list.size(), "Label Keys and Label Values don't have same size.");
            C5887Utils.checkNotNull(toLongFunction, "function");
        }

        public void removeTimeSeries(List<LabelValue> list) {
            C5887Utils.checkNotNull(list, "labelValues");
        }
    }

    public abstract void clear();

    public abstract <T> void createTimeSeries(List<LabelValue> list, T t, ToLongFunction<T> toLongFunction);

    public abstract void removeTimeSeries(List<LabelValue> list);

    static DerivedLongCumulative newNoopDerivedLongCumulative(String str, String str2, String str3, List<LabelKey> list) {
        return NoopDerivedLongCumulative.create(str, str2, str3, list);
    }
}
