package p043io.opencensus.metrics.export;

import javax.annotation.Nullable;
import p043io.opencensus.metrics.data.Exemplar;
import p043io.opencensus.metrics.export.Distribution.Bucket;

/* renamed from: io.opencensus.metrics.export.AutoValue_Distribution_Bucket */
final class AutoValue_Distribution_Bucket extends Bucket {
    private final long count;
    private final Exemplar exemplar;

    AutoValue_Distribution_Bucket(long j, @Nullable Exemplar exemplar2) {
        this.count = j;
        this.exemplar = exemplar2;
    }

    public long getCount() {
        return this.count;
    }

    @Nullable
    public Exemplar getExemplar() {
        return this.exemplar;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bucket{count=");
        sb.append(this.count);
        sb.append(", exemplar=");
        sb.append(this.exemplar);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Bucket)) {
            return false;
        }
        Bucket bucket = (Bucket) obj;
        if (this.count == bucket.getCount()) {
            Exemplar exemplar2 = this.exemplar;
            if (exemplar2 != null) {
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.count;
        int i = ((int) (j ^ (j2 ^ (j2 >>> 32)))) * 1000003;
        Exemplar exemplar2 = this.exemplar;
        return (exemplar2 == null ? 0 : exemplar2.hashCode()) ^ i;
    }
}
