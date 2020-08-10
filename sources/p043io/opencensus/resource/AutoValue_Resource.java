package p043io.opencensus.resource;

import java.util.Map;
import javax.annotation.Nullable;

/* renamed from: io.opencensus.resource.AutoValue_Resource */
final class AutoValue_Resource extends Resource {
    private final Map<String, String> labels;
    private final String type;

    AutoValue_Resource(@Nullable String str, Map<String, String> map) {
        this.type = str;
        if (map != null) {
            this.labels = map;
            return;
        }
        throw new NullPointerException("Null labels");
    }

    @Nullable
    public String getType() {
        return this.type;
    }

    public Map<String, String> getLabels() {
        return this.labels;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resource{type=");
        sb.append(this.type);
        sb.append(", labels=");
        sb.append(this.labels);
        sb.append("}");
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
        if (r4.labels.equals(r5.getLabels()) != false) goto L_0x002e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r5 != r4) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof p043io.opencensus.resource.Resource
            r2 = 0
            if (r1 == 0) goto L_0x002f
            io.opencensus.resource.Resource r5 = (p043io.opencensus.resource.Resource) r5
            java.lang.String r1 = r4.type
            if (r1 != 0) goto L_0x0016
            java.lang.String r1 = r5.getType()
            if (r1 != 0) goto L_0x002d
            goto L_0x0020
        L_0x0016:
            java.lang.String r3 = r5.getType()
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x002d
        L_0x0020:
            java.util.Map<java.lang.String, java.lang.String> r1 = r4.labels
            java.util.Map r5 = r5.getLabels()
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x002d
            goto L_0x002e
        L_0x002d:
            r0 = 0
        L_0x002e:
            return r0
        L_0x002f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.opencensus.resource.AutoValue_Resource.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        String str = this.type;
        return (((str == null ? 0 : str.hashCode()) ^ 1000003) * 1000003) ^ this.labels.hashCode();
    }
}
