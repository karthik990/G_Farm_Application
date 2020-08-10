package p043io.opencensus.common;

/* renamed from: io.opencensus.common.AutoValue_ServerStats */
final class AutoValue_ServerStats extends ServerStats {
    private final long lbLatencyNs;
    private final long serviceLatencyNs;
    private final byte traceOption;

    AutoValue_ServerStats(long j, long j2, byte b) {
        this.lbLatencyNs = j;
        this.serviceLatencyNs = j2;
        this.traceOption = b;
    }

    public long getLbLatencyNs() {
        return this.lbLatencyNs;
    }

    public long getServiceLatencyNs() {
        return this.serviceLatencyNs;
    }

    public byte getTraceOption() {
        return this.traceOption;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServerStats{lbLatencyNs=");
        sb.append(this.lbLatencyNs);
        sb.append(", serviceLatencyNs=");
        sb.append(this.serviceLatencyNs);
        sb.append(", traceOption=");
        sb.append(this.traceOption);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ServerStats)) {
            return false;
        }
        ServerStats serverStats = (ServerStats) obj;
        if (!(this.lbLatencyNs == serverStats.getLbLatencyNs() && this.serviceLatencyNs == serverStats.getServiceLatencyNs() && this.traceOption == serverStats.getTraceOption())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.lbLatencyNs;
        long j3 = (long) (((int) (j ^ (j2 ^ (j2 >>> 32)))) * 1000003);
        long j4 = this.serviceLatencyNs;
        return this.traceOption ^ (((int) (j3 ^ (j4 ^ (j4 >>> 32)))) * 1000003);
    }
}
