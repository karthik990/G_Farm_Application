package p043io.opencensus.metrics.export;

import java.util.List;
import p043io.opencensus.metrics.LabelKey;
import p043io.opencensus.metrics.export.MetricDescriptor.Type;

/* renamed from: io.opencensus.metrics.export.AutoValue_MetricDescriptor */
final class AutoValue_MetricDescriptor extends MetricDescriptor {
    private final String description;
    private final List<LabelKey> labelKeys;
    private final String name;
    private final Type type;
    private final String unit;

    AutoValue_MetricDescriptor(String str, String str2, String str3, Type type2, List<LabelKey> list) {
        if (str != null) {
            this.name = str;
            if (str2 != null) {
                this.description = str2;
                if (str3 != null) {
                    this.unit = str3;
                    if (type2 != null) {
                        this.type = type2;
                        if (list != null) {
                            this.labelKeys = list;
                            return;
                        }
                        throw new NullPointerException("Null labelKeys");
                    }
                    throw new NullPointerException("Null type");
                }
                throw new NullPointerException("Null unit");
            }
            throw new NullPointerException("Null description");
        }
        throw new NullPointerException("Null name");
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUnit() {
        return this.unit;
    }

    public Type getType() {
        return this.type;
    }

    public List<LabelKey> getLabelKeys() {
        return this.labelKeys;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MetricDescriptor{name=");
        sb.append(this.name);
        sb.append(", description=");
        sb.append(this.description);
        sb.append(", unit=");
        sb.append(this.unit);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", labelKeys=");
        sb.append(this.labelKeys);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MetricDescriptor)) {
            return false;
        }
        MetricDescriptor metricDescriptor = (MetricDescriptor) obj;
        if (!this.name.equals(metricDescriptor.getName()) || !this.description.equals(metricDescriptor.getDescription()) || !this.unit.equals(metricDescriptor.getUnit()) || !this.type.equals(metricDescriptor.getType()) || !this.labelKeys.equals(metricDescriptor.getLabelKeys())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((((this.name.hashCode() ^ 1000003) * 1000003) ^ this.description.hashCode()) * 1000003) ^ this.unit.hashCode()) * 1000003) ^ this.type.hashCode()) * 1000003) ^ this.labelKeys.hashCode();
    }
}
