package p043io.opencensus.common;

/* renamed from: io.opencensus.common.ServerStats */
public abstract class ServerStats {
    public abstract long getLbLatencyNs();

    public abstract long getServiceLatencyNs();

    public abstract byte getTraceOption();

    ServerStats() {
    }

    public static ServerStats create(long j, long j2, byte b) {
        if (j < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("'getLbLatencyNs' is less than zero: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (j2 >= 0) {
            AutoValue_ServerStats autoValue_ServerStats = new AutoValue_ServerStats(j, j2, b);
            return autoValue_ServerStats;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("'getServiceLatencyNs' is less than zero: ");
            sb2.append(j2);
            throw new IllegalArgumentException(sb2.toString());
        }
    }
}
