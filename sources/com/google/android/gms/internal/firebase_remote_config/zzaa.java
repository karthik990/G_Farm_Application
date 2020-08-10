package com.google.android.gms.internal.firebase_remote_config;

public final class zzaa {
    private zzu zzao;
    private zzw zzba;
    private zzx zzbb = new zzx();
    private zzx zzbc = new zzx();
    private int zzbd = 10;
    private int zzbe = 16384;
    private boolean zzbf = true;
    private boolean zzbg = true;
    private zzt zzbh;
    private zzs zzbi;
    private int zzbj = 20000;
    private int zzbk = 20000;
    private zzag zzbl;
    private boolean zzbm = true;
    private boolean zzbn = true;
    @Deprecated
    private boolean zzbo = false;
    private zzcj zzbp = zzcj.zzgg;
    private zzch zzl;
    private final zzah zzo;
    private String zzr;

    zzaa(zzah zzah, String str) {
        this.zzo = zzah;
        zzw(null);
    }

    public final zzah zzt() {
        return this.zzo;
    }

    public final String getRequestMethod() {
        return this.zzr;
    }

    public final zzaa zzw(String str) {
        if (str == null || zzz.zzv(str)) {
            this.zzr = str;
            return this;
        }
        throw new IllegalArgumentException();
    }

    public final zzs zzu() {
        return this.zzbi;
    }

    public final zzaa zza(zzs zzs) {
        this.zzbi = (zzs) zzds.checkNotNull(zzs);
        return this;
    }

    public final zzt zzv() {
        return this.zzbh;
    }

    public final zzaa zza(zzt zzt) {
        this.zzbh = zzt;
        return this;
    }

    public final zzaa zza(zzu zzu) {
        this.zzao = zzu;
        return this;
    }

    public final int zzw() {
        return this.zzbe;
    }

    public final boolean zzx() {
        return this.zzbf;
    }

    public final zzaa zza(int i) {
        this.zzbj = 5000;
        return this;
    }

    public final zzaa zzb(int i) {
        this.zzbk = 10000;
        return this;
    }

    public final zzx zzy() {
        return this.zzbb;
    }

    public final zzx zzz() {
        return this.zzbc;
    }

    public final zzaa zza(zzw zzw) {
        this.zzba = zzw;
        return this;
    }

    public final zzag zzaa() {
        return this.zzbl;
    }

    public final zzaa zza(zzag zzag) {
        this.zzbl = zzag;
        return this;
    }

    public final zzaa zza(zzch zzch) {
        this.zzl = zzch;
        return this;
    }

    public final zzch zzab() {
        return this.zzl;
    }

    public final boolean zzac() {
        return this.zzbn;
    }

    /* JADX WARNING: type inference failed for: r12v3 */
    /* JADX WARNING: type inference failed for: r15v0, types: [com.google.android.gms.internal.firebase_remote_config.zzcl] */
    /* JADX WARNING: type inference failed for: r12v4, types: [com.google.android.gms.internal.firebase_remote_config.zzcl] */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zzv, com.google.android.gms.internal.firebase_remote_config.zzcl] */
    /* JADX WARNING: type inference failed for: r15v2 */
    /* JADX WARNING: type inference failed for: r15v3, types: [com.google.android.gms.internal.firebase_remote_config.zzcf] */
    /* JADX WARNING: type inference failed for: r12v12 */
    /* JADX WARNING: type inference failed for: r12v13 */
    /* JADX WARNING: type inference failed for: r15v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0281 A[Catch:{ all -> 0x02ac }] */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x0285  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x02a9 A[LOOP:0: B:6:0x0019->B:124:0x02a9, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x028b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01d5  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0209  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0222 A[Catch:{ all -> 0x02ac }] */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.firebase_remote_config.zzad zzad() throws java.io.IOException {
        /*
            r20 = this;
            r1 = r20
            int r0 = r1.zzbd
            if (r0 < 0) goto L_0x0008
            r0 = 1
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            if (r0 == 0) goto L_0x02be
            int r0 = r1.zzbd
            java.lang.String r4 = r1.zzr
            com.google.android.gms.internal.firebase_remote_config.zzds.checkNotNull(r4)
            com.google.android.gms.internal.firebase_remote_config.zzs r4 = r1.zzbi
            com.google.android.gms.internal.firebase_remote_config.zzds.checkNotNull(r4)
            r5 = r0
            r0 = 0
        L_0x0019:
            if (r0 == 0) goto L_0x001e
            r0.ignore()
        L_0x001e:
            com.google.android.gms.internal.firebase_remote_config.zzw r0 = r1.zzba
            if (r0 == 0) goto L_0x0025
            r0.zzb(r1)
        L_0x0025:
            com.google.android.gms.internal.firebase_remote_config.zzs r0 = r1.zzbi
            java.lang.String r0 = r0.zzp()
            com.google.android.gms.internal.firebase_remote_config.zzah r6 = r1.zzo
            java.lang.String r7 = r1.zzr
            com.google.android.gms.internal.firebase_remote_config.zzai r6 = r6.zzc(r7, r0)
            java.util.logging.Logger r7 = com.google.android.gms.internal.firebase_remote_config.zzah.zzbz
            boolean r8 = r1.zzbf
            if (r8 == 0) goto L_0x0043
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG
            boolean r8 = r7.isLoggable(r8)
            if (r8 == 0) goto L_0x0043
            r8 = 1
            goto L_0x0044
        L_0x0043:
            r8 = 0
        L_0x0044:
            java.lang.String r9 = "GET"
            if (r8 == 0) goto L_0x0087
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "-------------- REQUEST  --------------"
            r10.append(r11)
            java.lang.String r11 = com.google.android.gms.internal.firebase_remote_config.zzcm.zzgh
            r10.append(r11)
            java.lang.String r11 = r1.zzr
            r10.append(r11)
            r11 = 32
            r10.append(r11)
            r10.append(r0)
            java.lang.String r11 = com.google.android.gms.internal.firebase_remote_config.zzcm.zzgh
            r10.append(r11)
            boolean r11 = r1.zzbg
            if (r11 == 0) goto L_0x0088
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r12 = "curl -v --compressed"
            r11.<init>(r12)
            java.lang.String r12 = r1.zzr
            boolean r12 = r12.equals(r9)
            if (r12 != 0) goto L_0x0089
            java.lang.String r12 = " -X "
            r11.append(r12)
            java.lang.String r12 = r1.zzr
            r11.append(r12)
            goto L_0x0089
        L_0x0087:
            r10 = 0
        L_0x0088:
            r11 = 0
        L_0x0089:
            com.google.android.gms.internal.firebase_remote_config.zzx r12 = r1.zzbb
            java.lang.String r12 = r12.zzr()
            if (r12 != 0) goto L_0x0099
            com.google.android.gms.internal.firebase_remote_config.zzx r13 = r1.zzbb
            java.lang.String r14 = "Google-HTTP-Java-Client/1.26.0-SNAPSHOT (gzip)"
            r13.zzu(r14)
            goto L_0x00b9
        L_0x0099:
            com.google.android.gms.internal.firebase_remote_config.zzx r13 = r1.zzbb
            java.lang.String r14 = java.lang.String.valueOf(r12)
            int r14 = r14.length()
            int r14 = r14 + 47
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>(r14)
            r15.append(r12)
            java.lang.String r14 = " Google-HTTP-Java-Client/1.26.0-SNAPSHOT (gzip)"
            r15.append(r14)
            java.lang.String r14 = r15.toString()
            r13.zzu(r14)
        L_0x00b9:
            com.google.android.gms.internal.firebase_remote_config.zzx r13 = r1.zzbb
            com.google.android.gms.internal.firebase_remote_config.zzx.zza(r13, r10, r11, r7, r6)
            com.google.android.gms.internal.firebase_remote_config.zzx r13 = r1.zzbb
            r13.zzu(r12)
            com.google.android.gms.internal.firebase_remote_config.zzt r12 = r1.zzbh
            if (r12 == 0) goto L_0x00ca
            r12.zzo()
        L_0x00ca:
            java.lang.String r13 = "'"
            if (r12 == 0) goto L_0x01cf
            com.google.android.gms.internal.firebase_remote_config.zzt r14 = r1.zzbh
            java.lang.String r14 = r14.getType()
            if (r8 == 0) goto L_0x00e2
            com.google.android.gms.internal.firebase_remote_config.zzcf r15 = new com.google.android.gms.internal.firebase_remote_config.zzcf
            java.util.logging.Logger r2 = com.google.android.gms.internal.firebase_remote_config.zzah.zzbz
            java.util.logging.Level r3 = java.util.logging.Level.CONFIG
            int r4 = r1.zzbe
            r15.<init>(r12, r2, r3, r4)
            goto L_0x00e3
        L_0x00e2:
            r15 = r12
        L_0x00e3:
            com.google.android.gms.internal.firebase_remote_config.zzu r2 = r1.zzao
            if (r2 != 0) goto L_0x00f0
            com.google.android.gms.internal.firebase_remote_config.zzt r2 = r1.zzbh
            long r2 = r2.getLength()
            r12 = r15
            r4 = 0
            goto L_0x0102
        L_0x00f0:
            java.lang.String r4 = r2.getName()
            com.google.android.gms.internal.firebase_remote_config.zzv r2 = new com.google.android.gms.internal.firebase_remote_config.zzv
            com.google.android.gms.internal.firebase_remote_config.zzu r3 = r1.zzao
            r2.<init>(r15, r3)
            long r16 = com.google.android.gms.internal.firebase_remote_config.zzca.zzb(r2)
            r12 = r2
            r2 = r16
        L_0x0102:
            if (r8 == 0) goto L_0x01b7
            java.lang.String r15 = " -H '"
            if (r14 == 0) goto L_0x014f
            r16 = r9
            java.lang.String r9 = "Content-Type: "
            java.lang.String r1 = java.lang.String.valueOf(r14)
            int r17 = r1.length()
            if (r17 == 0) goto L_0x011b
            java.lang.String r1 = r9.concat(r1)
            goto L_0x0120
        L_0x011b:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r9)
        L_0x0120:
            r10.append(r1)
            java.lang.String r9 = com.google.android.gms.internal.firebase_remote_config.zzcm.zzgh
            r10.append(r9)
            if (r11 == 0) goto L_0x014c
            java.lang.String r9 = java.lang.String.valueOf(r1)
            int r9 = r9.length()
            int r9 = r9 + 6
            r17 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r9)
            r5.append(r15)
            r5.append(r1)
            r5.append(r13)
            java.lang.String r1 = r5.toString()
            r11.append(r1)
            goto L_0x0153
        L_0x014c:
            r17 = r5
            goto L_0x0153
        L_0x014f:
            r17 = r5
            r16 = r9
        L_0x0153:
            if (r4 == 0) goto L_0x0195
            java.lang.String r1 = "Content-Encoding: "
            java.lang.String r5 = java.lang.String.valueOf(r4)
            int r9 = r5.length()
            if (r9 == 0) goto L_0x0166
            java.lang.String r1 = r1.concat(r5)
            goto L_0x016c
        L_0x0166:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r1)
            r1 = r5
        L_0x016c:
            r10.append(r1)
            java.lang.String r5 = com.google.android.gms.internal.firebase_remote_config.zzcm.zzgh
            r10.append(r5)
            if (r11 == 0) goto L_0x0195
            java.lang.String r5 = java.lang.String.valueOf(r1)
            int r5 = r5.length()
            int r5 = r5 + 6
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>(r5)
            r9.append(r15)
            r9.append(r1)
            r9.append(r13)
            java.lang.String r1 = r9.toString()
            r11.append(r1)
        L_0x0195:
            r18 = 0
            int r1 = (r2 > r18 ? 1 : (r2 == r18 ? 0 : -1))
            if (r1 < 0) goto L_0x01bb
            r1 = 36
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r1)
            java.lang.String r1 = "Content-Length: "
            r5.append(r1)
            r5.append(r2)
            java.lang.String r1 = r5.toString()
            r10.append(r1)
            java.lang.String r1 = com.google.android.gms.internal.firebase_remote_config.zzcm.zzgh
            r10.append(r1)
            goto L_0x01bb
        L_0x01b7:
            r17 = r5
            r16 = r9
        L_0x01bb:
            if (r11 == 0) goto L_0x01c2
            java.lang.String r1 = " -d '@-'"
            r11.append(r1)
        L_0x01c2:
            r6.setContentType(r14)
            r6.setContentEncoding(r4)
            r6.setContentLength(r2)
            r6.zza(r12)
            goto L_0x01d3
        L_0x01cf:
            r17 = r5
            r16 = r9
        L_0x01d3:
            if (r8 == 0) goto L_0x0205
            java.util.logging.Level r1 = java.util.logging.Level.CONFIG
            java.lang.String r2 = r10.toString()
            java.lang.String r3 = "execute"
            java.lang.String r4 = "com.google.api.client.http.HttpRequest"
            r7.logp(r1, r4, r3, r2)
            if (r11 == 0) goto L_0x0205
            java.lang.String r1 = " -- '"
            r11.append(r1)
            java.lang.String r1 = "'\"'\"'"
            java.lang.String r0 = r0.replaceAll(r13, r1)
            r11.append(r0)
            r11.append(r13)
            if (r12 == 0) goto L_0x01fc
            java.lang.String r0 = " << $$$"
            r11.append(r0)
        L_0x01fc:
            java.util.logging.Level r0 = java.util.logging.Level.CONFIG
            java.lang.String r1 = r11.toString()
            r7.logp(r0, r4, r3, r1)
        L_0x0205:
            if (r17 <= 0) goto L_0x0209
            r0 = 1
            goto L_0x020a
        L_0x0209:
            r0 = 0
        L_0x020a:
            r1 = r20
            int r2 = r1.zzbj
            int r3 = r1.zzbk
            r6.zza(r2, r3)
            com.google.android.gms.internal.firebase_remote_config.zzaj r2 = r6.zzai()     // Catch:{ IOException -> 0x02bc }
            com.google.android.gms.internal.firebase_remote_config.zzad r3 = new com.google.android.gms.internal.firebase_remote_config.zzad     // Catch:{ all -> 0x02b1 }
            r3.<init>(r1, r2)     // Catch:{ all -> 0x02b1 }
            boolean r2 = r3.zzae()     // Catch:{ all -> 0x02ac }
            if (r2 != 0) goto L_0x0285
            int r2 = r3.getStatusCode()     // Catch:{ all -> 0x02ac }
            com.google.android.gms.internal.firebase_remote_config.zzx r4 = r3.zzy()     // Catch:{ all -> 0x02ac }
            java.lang.String r4 = r4.getLocation()     // Catch:{ all -> 0x02ac }
            boolean r5 = r1.zzbm     // Catch:{ all -> 0x02ac }
            if (r5 == 0) goto L_0x027c
            r5 = 307(0x133, float:4.3E-43)
            if (r2 == r5) goto L_0x023b
            switch(r2) {
                case 301: goto L_0x023b;
                case 302: goto L_0x023b;
                case 303: goto L_0x023b;
                default: goto L_0x0239;
            }     // Catch:{ all -> 0x02ac }
        L_0x0239:
            r5 = 0
            goto L_0x023c
        L_0x023b:
            r5 = 1
        L_0x023c:
            if (r5 == 0) goto L_0x027c
            if (r4 == 0) goto L_0x027c
            com.google.android.gms.internal.firebase_remote_config.zzs r5 = new com.google.android.gms.internal.firebase_remote_config.zzs     // Catch:{ all -> 0x02ac }
            com.google.android.gms.internal.firebase_remote_config.zzs r6 = r1.zzbi     // Catch:{ all -> 0x02ac }
            java.net.URL r4 = r6.zzk(r4)     // Catch:{ all -> 0x02ac }
            r5.<init>(r4)     // Catch:{ all -> 0x02ac }
            r1.zza(r5)     // Catch:{ all -> 0x02ac }
            r4 = 303(0x12f, float:4.25E-43)
            if (r2 != r4) goto L_0x025b
            r2 = r16
            r1.zzw(r2)     // Catch:{ all -> 0x02ac }
            r2 = 0
            r1.zzbh = r2     // Catch:{ all -> 0x02ac }
            goto L_0x025c
        L_0x025b:
            r2 = 0
        L_0x025c:
            com.google.android.gms.internal.firebase_remote_config.zzx r4 = r1.zzbb     // Catch:{ all -> 0x02ac }
            r4.zzo(r2)     // Catch:{ all -> 0x02ac }
            com.google.android.gms.internal.firebase_remote_config.zzx r4 = r1.zzbb     // Catch:{ all -> 0x02ac }
            r4.zzq(r2)     // Catch:{ all -> 0x02ac }
            com.google.android.gms.internal.firebase_remote_config.zzx r4 = r1.zzbb     // Catch:{ all -> 0x02ac }
            r4.zzr(r2)     // Catch:{ all -> 0x02ac }
            com.google.android.gms.internal.firebase_remote_config.zzx r4 = r1.zzbb     // Catch:{ all -> 0x02ac }
            r4.zzp(r2)     // Catch:{ all -> 0x02ac }
            com.google.android.gms.internal.firebase_remote_config.zzx r4 = r1.zzbb     // Catch:{ all -> 0x02ac }
            r4.zzs(r2)     // Catch:{ all -> 0x02ac }
            com.google.android.gms.internal.firebase_remote_config.zzx r4 = r1.zzbb     // Catch:{ all -> 0x02ac }
            r4.zzt(r2)     // Catch:{ all -> 0x02ac }
            r4 = 1
            goto L_0x027e
        L_0x027c:
            r2 = 0
            r4 = 0
        L_0x027e:
            r0 = r0 & r4
            if (r0 == 0) goto L_0x0287
            r3.ignore()     // Catch:{ all -> 0x02ac }
            goto L_0x0287
        L_0x0285:
            r2 = 0
            r0 = 0
        L_0x0287:
            int r5 = r17 + -1
            if (r0 != 0) goto L_0x02a9
            com.google.android.gms.internal.firebase_remote_config.zzag r0 = r1.zzbl
            if (r0 == 0) goto L_0x0292
            r0.zzb(r3)
        L_0x0292:
            boolean r0 = r1.zzbn
            if (r0 == 0) goto L_0x02a8
            boolean r0 = r3.zzae()
            if (r0 == 0) goto L_0x029d
            goto L_0x02a8
        L_0x029d:
            com.google.android.gms.internal.firebase_remote_config.zzae r0 = new com.google.android.gms.internal.firebase_remote_config.zzae     // Catch:{ all -> 0x02a3 }
            r0.<init>(r3)     // Catch:{ all -> 0x02a3 }
            throw r0     // Catch:{ all -> 0x02a3 }
        L_0x02a3:
            r0 = move-exception
            r3.disconnect()
            throw r0
        L_0x02a8:
            return r3
        L_0x02a9:
            r0 = r3
            goto L_0x0019
        L_0x02ac:
            r0 = move-exception
            r3.disconnect()
            throw r0
        L_0x02b1:
            r0 = move-exception
            java.io.InputStream r2 = r2.getContent()     // Catch:{ IOException -> 0x02bc }
            if (r2 == 0) goto L_0x02bb
            r2.close()     // Catch:{ IOException -> 0x02bc }
        L_0x02bb:
            throw r0     // Catch:{ IOException -> 0x02bc }
        L_0x02bc:
            r0 = move-exception
            throw r0
        L_0x02be:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            goto L_0x02c5
        L_0x02c4:
            throw r0
        L_0x02c5:
            goto L_0x02c4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzaa.zzad():com.google.android.gms.internal.firebase_remote_config.zzad");
    }
}
