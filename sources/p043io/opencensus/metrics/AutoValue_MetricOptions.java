package p043io.opencensus.metrics;

import java.util.List;
import java.util.Map;

/* renamed from: io.opencensus.metrics.AutoValue_MetricOptions */
final class AutoValue_MetricOptions extends MetricOptions {
    private final Map<LabelKey, LabelValue> constantLabels;
    private final String description;
    private final List<LabelKey> labelKeys;
    private final String unit;

    /* renamed from: io.opencensus.metrics.AutoValue_MetricOptions$Builder */
    static final class Builder extends p043io.opencensus.metrics.MetricOptions.Builder {
        private Map<LabelKey, LabelValue> constantLabels;
        private String description;
        private List<LabelKey> labelKeys;
        private String unit;

        Builder() {
        }

        public p043io.opencensus.metrics.MetricOptions.Builder setDescription(String str) {
            if (str != null) {
                this.description = str;
                return this;
            }
            throw new NullPointerException("Null description");
        }

        public p043io.opencensus.metrics.MetricOptions.Builder setUnit(String str) {
            if (str != null) {
                this.unit = str;
                return this;
            }
            throw new NullPointerException("Null unit");
        }

        public p043io.opencensus.metrics.MetricOptions.Builder setLabelKeys(List<LabelKey> list) {
            if (list != null) {
                this.labelKeys = list;
                return this;
            }
            throw new NullPointerException("Null labelKeys");
        }

        /* access modifiers changed from: 0000 */
        public List<LabelKey> getLabelKeys() {
            List<LabelKey> list = this.labelKeys;
            if (list != null) {
                return list;
            }
            throw new IllegalStateException("Property \"labelKeys\" has not been set");
        }

        public p043io.opencensus.metrics.MetricOptions.Builder setConstantLabels(Map<LabelKey, LabelValue> map) {
            if (map != null) {
                this.constantLabels = map;
                return this;
            }
            throw new NullPointerException("Null constantLabels");
        }

        /* access modifiers changed from: 0000 */
        public Map<LabelKey, LabelValue> getConstantLabels() {
            Map<LabelKey, LabelValue> map = this.constantLabels;
            if (map != null) {
                return map;
            }
            throw new IllegalStateException("Property \"constantLabels\" has not been set");
        }

        /* access modifiers changed from: 0000 */
        public MetricOptions autoBuild() {
            String str = "";
            if (this.description == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" description");
                str = sb.toString();
            }
            if (this.unit == null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(" unit");
                str = sb2.toString();
            }
            if (this.labelKeys == null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str);
                sb3.append(" labelKeys");
                str = sb3.toString();
            }
            if (this.constantLabels == null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str);
                sb4.append(" constantLabels");
                str = sb4.toString();
            }
            if (str.isEmpty()) {
                AutoValue_MetricOptions autoValue_MetricOptions = new AutoValue_MetricOptions(this.description, this.unit, this.labelKeys, this.constantLabels);
                return autoValue_MetricOptions;
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Missing required properties:");
            sb5.append(str);
            throw new IllegalStateException(sb5.toString());
        }
    }

    private AutoValue_MetricOptions(String str, String str2, List<LabelKey> list, Map<LabelKey, LabelValue> map) {
        this.description = str;
        this.unit = str2;
        this.labelKeys = list;
        this.constantLabels = map;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUnit() {
        return this.unit;
    }

    public List<LabelKey> getLabelKeys() {
        return this.labelKeys;
    }

    public Map<LabelKey, LabelValue> getConstantLabels() {
        return this.constantLabels;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MetricOptions{description=");
        sb.append(this.description);
        sb.append(", unit=");
        sb.append(this.unit);
        sb.append(", labelKeys=");
        sb.append(this.labelKeys);
        sb.append(", constantLabels=");
        sb.append(this.constantLabels);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MetricOptions)) {
            return false;
        }
        MetricOptions metricOptions = (MetricOptions) obj;
        if (!this.description.equals(metricOptions.getDescription()) || !this.unit.equals(metricOptions.getUnit()) || !this.labelKeys.equals(metricOptions.getLabelKeys()) || !this.constantLabels.equals(metricOptions.getConstantLabels())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((this.description.hashCode() ^ 1000003) * 1000003) ^ this.unit.hashCode()) * 1000003) ^ this.labelKeys.hashCode()) * 1000003) ^ this.constantLabels.hashCode();
    }
}
