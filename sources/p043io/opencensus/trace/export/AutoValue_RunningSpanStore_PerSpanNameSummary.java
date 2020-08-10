package p043io.opencensus.trace.export;

import p043io.opencensus.trace.export.RunningSpanStore.PerSpanNameSummary;

/* renamed from: io.opencensus.trace.export.AutoValue_RunningSpanStore_PerSpanNameSummary */
final class AutoValue_RunningSpanStore_PerSpanNameSummary extends PerSpanNameSummary {
    private final int numRunningSpans;

    AutoValue_RunningSpanStore_PerSpanNameSummary(int i) {
        this.numRunningSpans = i;
    }

    public int getNumRunningSpans() {
        return this.numRunningSpans;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PerSpanNameSummary{numRunningSpans=");
        sb.append(this.numRunningSpans);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PerSpanNameSummary)) {
            return false;
        }
        if (this.numRunningSpans != ((PerSpanNameSummary) obj).getNumRunningSpans()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.numRunningSpans ^ 1000003;
    }
}
