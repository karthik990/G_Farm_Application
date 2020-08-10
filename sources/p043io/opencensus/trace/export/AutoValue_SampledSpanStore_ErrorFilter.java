package p043io.opencensus.trace.export;

import javax.annotation.Nullable;
import p043io.opencensus.trace.Status.CanonicalCode;
import p043io.opencensus.trace.export.SampledSpanStore.ErrorFilter;

/* renamed from: io.opencensus.trace.export.AutoValue_SampledSpanStore_ErrorFilter */
final class AutoValue_SampledSpanStore_ErrorFilter extends ErrorFilter {
    private final CanonicalCode canonicalCode;
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_SampledSpanStore_ErrorFilter(String str, @Nullable CanonicalCode canonicalCode2, int i) {
        if (str != null) {
            this.spanName = str;
            this.canonicalCode = canonicalCode2;
            this.maxSpansToReturn = i;
            return;
        }
        throw new NullPointerException("Null spanName");
    }

    public String getSpanName() {
        return this.spanName;
    }

    @Nullable
    public CanonicalCode getCanonicalCode() {
        return this.canonicalCode;
    }

    public int getMaxSpansToReturn() {
        return this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ErrorFilter{spanName=");
        sb.append(this.spanName);
        sb.append(", canonicalCode=");
        sb.append(this.canonicalCode);
        sb.append(", maxSpansToReturn=");
        sb.append(this.maxSpansToReturn);
        sb.append("}");
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0032, code lost:
        if (r4.maxSpansToReturn == r5.getMaxSpansToReturn()) goto L_0x0036;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof p043io.opencensus.trace.export.SampledSpanStore.ErrorFilter
            r2 = 0
            if (r1 == 0) goto L_0x0037
            io.opencensus.trace.export.SampledSpanStore$ErrorFilter r5 = (p043io.opencensus.trace.export.SampledSpanStore.ErrorFilter) r5
            java.lang.String r1 = r4.spanName
            java.lang.String r3 = r5.getSpanName()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0035
            io.opencensus.trace.Status$CanonicalCode r1 = r4.canonicalCode
            if (r1 != 0) goto L_0x0022
            io.opencensus.trace.Status$CanonicalCode r1 = r5.getCanonicalCode()
            if (r1 != 0) goto L_0x0035
            goto L_0x002c
        L_0x0022:
            io.opencensus.trace.Status$CanonicalCode r3 = r5.getCanonicalCode()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0035
        L_0x002c:
            int r1 = r4.maxSpansToReturn
            int r5 = r5.getMaxSpansToReturn()
            if (r1 != r5) goto L_0x0035
            goto L_0x0036
        L_0x0035:
            r0 = 0
        L_0x0036:
            return r0
        L_0x0037:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.opencensus.trace.export.AutoValue_SampledSpanStore_ErrorFilter.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int hashCode = (this.spanName.hashCode() ^ 1000003) * 1000003;
        CanonicalCode canonicalCode2 = this.canonicalCode;
        return ((hashCode ^ (canonicalCode2 == null ? 0 : canonicalCode2.hashCode())) * 1000003) ^ this.maxSpansToReturn;
    }
}
