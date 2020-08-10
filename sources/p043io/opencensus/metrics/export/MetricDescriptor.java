package p043io.opencensus.metrics.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.metrics.LabelKey;

/* renamed from: io.opencensus.metrics.export.MetricDescriptor */
public abstract class MetricDescriptor {

    /* renamed from: io.opencensus.metrics.export.MetricDescriptor$Type */
    public enum Type {
        GAUGE_INT64,
        GAUGE_DOUBLE,
        GAUGE_DISTRIBUTION,
        CUMULATIVE_INT64,
        CUMULATIVE_DOUBLE,
        CUMULATIVE_DISTRIBUTION,
        SUMMARY
    }

    public abstract String getDescription();

    public abstract List<LabelKey> getLabelKeys();

    public abstract String getName();

    public abstract Type getType();

    public abstract String getUnit();

    MetricDescriptor() {
    }

    public static MetricDescriptor create(String str, String str2, String str3, Type type, List<LabelKey> list) {
        C5887Utils.checkListElementNotNull((List) C5887Utils.checkNotNull(list, "labelKeys"), "labelKey");
        AutoValue_MetricDescriptor autoValue_MetricDescriptor = new AutoValue_MetricDescriptor(str, str2, str3, type, Collections.unmodifiableList(new ArrayList(list)));
        return autoValue_MetricDescriptor;
    }
}
