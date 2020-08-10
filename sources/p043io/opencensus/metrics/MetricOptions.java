package p043io.opencensus.metrics;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.metrics.MetricOptions */
public abstract class MetricOptions {

    /* renamed from: io.opencensus.metrics.MetricOptions$Builder */
    public static abstract class Builder {
        /* access modifiers changed from: 0000 */
        public abstract MetricOptions autoBuild();

        /* access modifiers changed from: 0000 */
        public abstract Map<LabelKey, LabelValue> getConstantLabels();

        /* access modifiers changed from: 0000 */
        public abstract List<LabelKey> getLabelKeys();

        public abstract Builder setConstantLabels(Map<LabelKey, LabelValue> map);

        public abstract Builder setDescription(String str);

        public abstract Builder setLabelKeys(List<LabelKey> list);

        public abstract Builder setUnit(String str);

        public MetricOptions build() {
            setLabelKeys(Collections.unmodifiableList(new ArrayList(getLabelKeys())));
            setConstantLabels(Collections.unmodifiableMap(new LinkedHashMap(getConstantLabels())));
            MetricOptions autoBuild = autoBuild();
            C5887Utils.checkListElementNotNull(autoBuild.getLabelKeys(), "labelKeys elements");
            C5887Utils.checkMapElementNotNull(autoBuild.getConstantLabels(), "constantLabels elements");
            HashSet hashSet = new HashSet();
            for (LabelKey labelKey : autoBuild.getLabelKeys()) {
                if (!hashSet.contains(labelKey.getKey())) {
                    hashSet.add(labelKey.getKey());
                } else {
                    throw new IllegalArgumentException("Invalid LabelKey in labelKeys");
                }
            }
            for (Entry entry : autoBuild.getConstantLabels().entrySet()) {
                if (!hashSet.contains(((LabelKey) entry.getKey()).getKey())) {
                    hashSet.add(((LabelKey) entry.getKey()).getKey());
                } else {
                    throw new IllegalArgumentException("Invalid LabelKey in constantLabels");
                }
            }
            return autoBuild;
        }

        Builder() {
        }
    }

    public abstract Map<LabelKey, LabelValue> getConstantLabels();

    public abstract String getDescription();

    public abstract List<LabelKey> getLabelKeys();

    public abstract String getUnit();

    public static Builder builder() {
        return new Builder().setDescription("").setUnit(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE).setLabelKeys(Collections.emptyList()).setConstantLabels(Collections.emptyMap());
    }

    MetricOptions() {
    }
}
