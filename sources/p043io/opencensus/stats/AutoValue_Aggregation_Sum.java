package p043io.opencensus.stats;

import p043io.opencensus.stats.Aggregation.Sum;

/* renamed from: io.opencensus.stats.AutoValue_Aggregation_Sum */
final class AutoValue_Aggregation_Sum extends Sum {
    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "Sum{}";
    }

    AutoValue_Aggregation_Sum() {
    }

    public boolean equals(Object obj) {
        return obj == this || (obj instanceof Sum);
    }
}
