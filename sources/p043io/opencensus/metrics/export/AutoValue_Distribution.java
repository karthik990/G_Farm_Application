package p043io.opencensus.metrics.export;

import java.util.List;
import javax.annotation.Nullable;
import p043io.opencensus.metrics.export.Distribution.Bucket;
import p043io.opencensus.metrics.export.Distribution.BucketOptions;

/* renamed from: io.opencensus.metrics.export.AutoValue_Distribution */
final class AutoValue_Distribution extends Distribution {
    private final BucketOptions bucketOptions;
    private final List<Bucket> buckets;
    private final long count;
    private final double sum;
    private final double sumOfSquaredDeviations;

    AutoValue_Distribution(long j, double d, double d2, @Nullable BucketOptions bucketOptions2, List<Bucket> list) {
        this.count = j;
        this.sum = d;
        this.sumOfSquaredDeviations = d2;
        this.bucketOptions = bucketOptions2;
        if (list != null) {
            this.buckets = list;
            return;
        }
        throw new NullPointerException("Null buckets");
    }

    public long getCount() {
        return this.count;
    }

    public double getSum() {
        return this.sum;
    }

    public double getSumOfSquaredDeviations() {
        return this.sumOfSquaredDeviations;
    }

    @Nullable
    public BucketOptions getBucketOptions() {
        return this.bucketOptions;
    }

    public List<Bucket> getBuckets() {
        return this.buckets;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Distribution{count=");
        sb.append(this.count);
        sb.append(", sum=");
        sb.append(this.sum);
        sb.append(", sumOfSquaredDeviations=");
        sb.append(this.sumOfSquaredDeviations);
        sb.append(", bucketOptions=");
        sb.append(this.bucketOptions);
        sb.append(", buckets=");
        sb.append(this.buckets);
        sb.append("}");
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0058, code lost:
        if (r7.buckets.equals(r8.getBuckets()) != false) goto L_0x005c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 1
            if (r8 != r7) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r8 instanceof p043io.opencensus.metrics.export.Distribution
            r2 = 0
            if (r1 == 0) goto L_0x005d
            io.opencensus.metrics.export.Distribution r8 = (p043io.opencensus.metrics.export.Distribution) r8
            long r3 = r7.count
            long r5 = r8.getCount()
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x005b
            double r3 = r7.sum
            long r3 = java.lang.Double.doubleToLongBits(r3)
            double r5 = r8.getSum()
            long r5 = java.lang.Double.doubleToLongBits(r5)
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x005b
            double r3 = r7.sumOfSquaredDeviations
            long r3 = java.lang.Double.doubleToLongBits(r3)
            double r5 = r8.getSumOfSquaredDeviations()
            long r5 = java.lang.Double.doubleToLongBits(r5)
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x005b
            io.opencensus.metrics.export.Distribution$BucketOptions r1 = r7.bucketOptions
            if (r1 != 0) goto L_0x0044
            io.opencensus.metrics.export.Distribution$BucketOptions r1 = r8.getBucketOptions()
            if (r1 != 0) goto L_0x005b
            goto L_0x004e
        L_0x0044:
            io.opencensus.metrics.export.Distribution$BucketOptions r3 = r8.getBucketOptions()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x005b
        L_0x004e:
            java.util.List<io.opencensus.metrics.export.Distribution$Bucket> r1 = r7.buckets
            java.util.List r8 = r8.getBuckets()
            boolean r8 = r1.equals(r8)
            if (r8 == 0) goto L_0x005b
            goto L_0x005c
        L_0x005b:
            r0 = 0
        L_0x005c:
            return r0
        L_0x005d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.opencensus.metrics.export.AutoValue_Distribution.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.count;
        int doubleToLongBits = ((int) (((long) (((int) (((long) (((int) (j ^ (j2 ^ (j2 >>> 32)))) * 1000003)) ^ ((Double.doubleToLongBits(this.sum) >>> 32) ^ Double.doubleToLongBits(this.sum)))) * 1000003)) ^ ((Double.doubleToLongBits(this.sumOfSquaredDeviations) >>> 32) ^ Double.doubleToLongBits(this.sumOfSquaredDeviations)))) * 1000003;
        BucketOptions bucketOptions2 = this.bucketOptions;
        return this.buckets.hashCode() ^ (((bucketOptions2 == null ? 0 : bucketOptions2.hashCode()) ^ doubleToLongBits) * 1000003);
    }
}
