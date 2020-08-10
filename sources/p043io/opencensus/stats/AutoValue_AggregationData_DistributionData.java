package p043io.opencensus.stats;

import java.util.List;
import p043io.opencensus.metrics.data.Exemplar;
import p043io.opencensus.stats.AggregationData.DistributionData;

/* renamed from: io.opencensus.stats.AutoValue_AggregationData_DistributionData */
final class AutoValue_AggregationData_DistributionData extends DistributionData {
    private final List<Long> bucketCounts;
    private final long count;
    private final List<Exemplar> exemplars;
    private final double mean;
    private final double sumOfSquaredDeviations;

    AutoValue_AggregationData_DistributionData(double d, long j, double d2, List<Long> list, List<Exemplar> list2) {
        this.mean = d;
        this.count = j;
        this.sumOfSquaredDeviations = d2;
        if (list != null) {
            this.bucketCounts = list;
            if (list2 != null) {
                this.exemplars = list2;
                return;
            }
            throw new NullPointerException("Null exemplars");
        }
        throw new NullPointerException("Null bucketCounts");
    }

    public double getMean() {
        return this.mean;
    }

    public long getCount() {
        return this.count;
    }

    public double getSumOfSquaredDeviations() {
        return this.sumOfSquaredDeviations;
    }

    public List<Long> getBucketCounts() {
        return this.bucketCounts;
    }

    public List<Exemplar> getExemplars() {
        return this.exemplars;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DistributionData{mean=");
        sb.append(this.mean);
        sb.append(", count=");
        sb.append(this.count);
        sb.append(", sumOfSquaredDeviations=");
        sb.append(this.sumOfSquaredDeviations);
        sb.append(", bucketCounts=");
        sb.append(this.bucketCounts);
        sb.append(", exemplars=");
        sb.append(this.exemplars);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DistributionData)) {
            return false;
        }
        DistributionData distributionData = (DistributionData) obj;
        if (!(Double.doubleToLongBits(this.mean) == Double.doubleToLongBits(distributionData.getMean()) && this.count == distributionData.getCount() && Double.doubleToLongBits(this.sumOfSquaredDeviations) == Double.doubleToLongBits(distributionData.getSumOfSquaredDeviations()) && this.bucketCounts.equals(distributionData.getBucketCounts()) && this.exemplars.equals(distributionData.getExemplars()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long doubleToLongBits = (long) (((int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.mean) >>> 32) ^ Double.doubleToLongBits(this.mean)))) * 1000003);
        long j = this.count;
        return this.exemplars.hashCode() ^ ((this.bucketCounts.hashCode() ^ (((int) (((long) (((int) (doubleToLongBits ^ (j ^ (j >>> 32)))) * 1000003)) ^ ((Double.doubleToLongBits(this.sumOfSquaredDeviations) >>> 32) ^ Double.doubleToLongBits(this.sumOfSquaredDeviations)))) * 1000003)) * 1000003);
    }
}
