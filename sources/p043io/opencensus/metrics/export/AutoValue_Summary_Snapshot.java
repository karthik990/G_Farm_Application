package p043io.opencensus.metrics.export;

import java.util.List;
import javax.annotation.Nullable;
import p043io.opencensus.metrics.export.Summary.Snapshot;
import p043io.opencensus.metrics.export.Summary.Snapshot.ValueAtPercentile;

/* renamed from: io.opencensus.metrics.export.AutoValue_Summary_Snapshot */
final class AutoValue_Summary_Snapshot extends Snapshot {
    private final Long count;
    private final Double sum;
    private final List<ValueAtPercentile> valueAtPercentiles;

    AutoValue_Summary_Snapshot(@Nullable Long l, @Nullable Double d, List<ValueAtPercentile> list) {
        this.count = l;
        this.sum = d;
        if (list != null) {
            this.valueAtPercentiles = list;
            return;
        }
        throw new NullPointerException("Null valueAtPercentiles");
    }

    @Nullable
    public Long getCount() {
        return this.count;
    }

    @Nullable
    public Double getSum() {
        return this.sum;
    }

    public List<ValueAtPercentile> getValueAtPercentiles() {
        return this.valueAtPercentiles;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Snapshot{count=");
        sb.append(this.count);
        sb.append(", sum=");
        sb.append(this.sum);
        sb.append(", valueAtPercentiles=");
        sb.append(this.valueAtPercentiles);
        sb.append("}");
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        if (r4.valueAtPercentiles.equals(r5.getValueAtPercentiles()) != false) goto L_0x0043;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof p043io.opencensus.metrics.export.Summary.Snapshot
            r2 = 0
            if (r1 == 0) goto L_0x0044
            io.opencensus.metrics.export.Summary$Snapshot r5 = (p043io.opencensus.metrics.export.Summary.Snapshot) r5
            java.lang.Long r1 = r4.count
            if (r1 != 0) goto L_0x0016
            java.lang.Long r1 = r5.getCount()
            if (r1 != 0) goto L_0x0042
            goto L_0x0020
        L_0x0016:
            java.lang.Long r3 = r5.getCount()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0042
        L_0x0020:
            java.lang.Double r1 = r4.sum
            if (r1 != 0) goto L_0x002b
            java.lang.Double r1 = r5.getSum()
            if (r1 != 0) goto L_0x0042
            goto L_0x0035
        L_0x002b:
            java.lang.Double r3 = r5.getSum()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0042
        L_0x0035:
            java.util.List<io.opencensus.metrics.export.Summary$Snapshot$ValueAtPercentile> r1 = r4.valueAtPercentiles
            java.util.List r5 = r5.getValueAtPercentiles()
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x0042
            goto L_0x0043
        L_0x0042:
            r0 = 0
        L_0x0043:
            return r0
        L_0x0044:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.opencensus.metrics.export.AutoValue_Summary_Snapshot.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        Long l = this.count;
        int i = 0;
        int hashCode = ((l == null ? 0 : l.hashCode()) ^ 1000003) * 1000003;
        Double d = this.sum;
        if (d != null) {
            i = d.hashCode();
        }
        return ((hashCode ^ i) * 1000003) ^ this.valueAtPercentiles.hashCode();
    }
}
