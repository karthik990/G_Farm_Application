package p043io.opencensus.metrics.export;

import java.util.List;
import p043io.opencensus.metrics.export.Distribution.BucketOptions.ExplicitOptions;

/* renamed from: io.opencensus.metrics.export.AutoValue_Distribution_BucketOptions_ExplicitOptions */
final class AutoValue_Distribution_BucketOptions_ExplicitOptions extends ExplicitOptions {
    private final List<Double> bucketBoundaries;

    AutoValue_Distribution_BucketOptions_ExplicitOptions(List<Double> list) {
        if (list != null) {
            this.bucketBoundaries = list;
            return;
        }
        throw new NullPointerException("Null bucketBoundaries");
    }

    public List<Double> getBucketBoundaries() {
        return this.bucketBoundaries;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExplicitOptions{bucketBoundaries=");
        sb.append(this.bucketBoundaries);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ExplicitOptions)) {
            return false;
        }
        return this.bucketBoundaries.equals(((ExplicitOptions) obj).getBucketBoundaries());
    }

    public int hashCode() {
        return this.bucketBoundaries.hashCode() ^ 1000003;
    }
}
