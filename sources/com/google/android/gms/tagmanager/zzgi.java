package com.google.android.gms.tagmanager;

final class zzgi extends Number implements Comparable<zzgi> {
    private double zzall;
    private long zzalm;
    private boolean zzaln = false;

    private zzgi(double d) {
        this.zzall = d;
    }

    private zzgi(long j) {
        this.zzalm = j;
    }

    public static zzgi zza(Double d) {
        return new zzgi(d.doubleValue());
    }

    public static zzgi zzm(long j) {
        return new zzgi(j);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
        return new com.google.android.gms.tagmanager.zzgi(java.lang.Double.parseDouble(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        throw new java.lang.NumberFormatException(java.lang.String.valueOf(r3).concat(" is not a valid TypedNumber"));
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x000a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.tagmanager.zzgi zzbo(java.lang.String r3) throws java.lang.NumberFormatException {
        /*
            com.google.android.gms.tagmanager.zzgi r0 = new com.google.android.gms.tagmanager.zzgi     // Catch:{ NumberFormatException -> 0x000a }
            long r1 = java.lang.Long.parseLong(r3)     // Catch:{ NumberFormatException -> 0x000a }
            r0.<init>(r1)     // Catch:{ NumberFormatException -> 0x000a }
            return r0
        L_0x000a:
            com.google.android.gms.tagmanager.zzgi r0 = new com.google.android.gms.tagmanager.zzgi     // Catch:{ NumberFormatException -> 0x0014 }
            double r1 = java.lang.Double.parseDouble(r3)     // Catch:{ NumberFormatException -> 0x0014 }
            r0.<init>(r1)     // Catch:{ NumberFormatException -> 0x0014 }
            return r0
        L_0x0014:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r3 = java.lang.String.valueOf(r3)
            java.lang.String r1 = " is not a valid TypedNumber"
            java.lang.String r3 = r3.concat(r1)
            r0.<init>(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzgi.zzbo(java.lang.String):com.google.android.gms.tagmanager.zzgi");
    }

    public final String toString() {
        return this.zzaln ? Long.toString(this.zzalm) : Double.toString(this.zzall);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof zzgi) && compareTo((zzgi) obj) == 0;
    }

    public final int hashCode() {
        return new Long(longValue()).hashCode();
    }

    /* renamed from: zza */
    public final int compareTo(zzgi zzgi) {
        if (!this.zzaln || !zzgi.zzaln) {
            return Double.compare(doubleValue(), zzgi.doubleValue());
        }
        return new Long(this.zzalm).compareTo(Long.valueOf(zzgi.zzalm));
    }

    public final boolean zzju() {
        return !this.zzaln;
    }

    public final boolean zzjv() {
        return this.zzaln;
    }

    public final double doubleValue() {
        return this.zzaln ? (double) this.zzalm : this.zzall;
    }

    public final float floatValue() {
        return (float) doubleValue();
    }

    public final long longValue() {
        return this.zzaln ? this.zzalm : (long) this.zzall;
    }

    public final int intValue() {
        return (int) longValue();
    }

    public final short shortValue() {
        return (short) ((int) longValue());
    }

    public final byte byteValue() {
        return (byte) ((int) longValue());
    }
}
