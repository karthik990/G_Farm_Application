package p043io.opencensus.trace.export;

import p043io.opencensus.trace.export.SampledSpanStore.LatencyFilter;

/* renamed from: io.opencensus.trace.export.AutoValue_SampledSpanStore_LatencyFilter */
final class AutoValue_SampledSpanStore_LatencyFilter extends LatencyFilter {
    private final long latencyLowerNs;
    private final long latencyUpperNs;
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_SampledSpanStore_LatencyFilter(String str, long j, long j2, int i) {
        if (str != null) {
            this.spanName = str;
            this.latencyLowerNs = j;
            this.latencyUpperNs = j2;
            this.maxSpansToReturn = i;
            return;
        }
        throw new NullPointerException("Null spanName");
    }

    public String getSpanName() {
        return this.spanName;
    }

    public long getLatencyLowerNs() {
        return this.latencyLowerNs;
    }

    public long getLatencyUpperNs() {
        return this.latencyUpperNs;
    }

    public int getMaxSpansToReturn() {
        return this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LatencyFilter{spanName=");
        sb.append(this.spanName);
        sb.append(", latencyLowerNs=");
        sb.append(this.latencyLowerNs);
        sb.append(", latencyUpperNs=");
        sb.append(this.latencyUpperNs);
        sb.append(", maxSpansToReturn=");
        sb.append(this.maxSpansToReturn);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LatencyFilter)) {
            return false;
        }
        LatencyFilter latencyFilter = (LatencyFilter) obj;
        if (!(this.spanName.equals(latencyFilter.getSpanName()) && this.latencyLowerNs == latencyFilter.getLatencyLowerNs() && this.latencyUpperNs == latencyFilter.getLatencyUpperNs() && this.maxSpansToReturn == latencyFilter.getMaxSpansToReturn())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long hashCode = (long) ((this.spanName.hashCode() ^ 1000003) * 1000003);
        long j = this.latencyLowerNs;
        long j2 = (long) (((int) (hashCode ^ (j ^ (j >>> 32)))) * 1000003);
        long j3 = this.latencyUpperNs;
        return (((int) (j2 ^ (j3 ^ (j3 >>> 32)))) * 1000003) ^ this.maxSpansToReturn;
    }
}
