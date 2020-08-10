package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zze;

public final class zzaxc extends zzbbo<zzaxc, zza> implements zzbcw {
    private static volatile zzbdf<zzaxc> zzakh;
    /* access modifiers changed from: private */
    public static final zzaxc zzdkn = new zzaxc();
    private int zzdih;
    private zzbah zzdip = zzbah.zzdpq;
    private zzaxg zzdkm;

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzaxc, zza> implements zzbcw {
        private zza() {
            super(zzaxc.zzdkn);
        }

        /* synthetic */ zza(zzaxd zzaxd) {
            this();
        }

        public final zza zzaf(zzbah zzbah) {
            zzadh();
            ((zzaxc) this.zzdtx).zzk(zzbah);
            return this;
        }

        public final zza zzav(int i) {
            zzadh();
            ((zzaxc) this.zzdtx).setVersion(0);
            return this;
        }

        public final zza zzc(zzaxg zzaxg) {
            zzadh();
            ((zzaxc) this.zzdtx).zzb(zzaxg);
            return this;
        }
    }

    static {
        zzbbo.zza(zzaxc.class, zzdkn);
    }

    private zzaxc() {
    }

    /* access modifiers changed from: private */
    public final void setVersion(int i) {
        this.zzdih = i;
    }

    public static zzaxc zzae(zzbah zzbah) throws zzbbu {
        return (zzaxc) zzbbo.zza(zzdkn, zzbah);
    }

    /* access modifiers changed from: private */
    public final void zzb(zzaxg zzaxg) {
        if (zzaxg != null) {
            this.zzdkm = zzaxg;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public final void zzk(zzbah zzbah) {
        if (zzbah != null) {
            this.zzdip = zzbah;
            return;
        }
        throw new NullPointerException();
    }

    public static zza zzyn() {
        return (zza) ((com.google.android.gms.internal.ads.zzbbo.zza) zzdkn.zza(zze.zzdue, (Object) null, (Object) null));
    }

    public static zzaxc zzyo() {
        return zzdkn;
    }

    public final int getVersion() {
        return this.zzdih;
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxc>] */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxc>] */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxc>, com.google.android.gms.internal.ads.zzbbo$zzb] */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v13
      assigns: []
      uses: []
      mth insns count: 42
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object zza(int r2, java.lang.Object r3, java.lang.Object r4) {
        /*
            r1 = this;
            int[] r3 = com.google.android.gms.internal.ads.zzaxd.zzakf
            r4 = 1
            int r2 = r2 - r4
            r2 = r3[r2]
            r3 = 0
            switch(r2) {
                case 1: goto L_0x0053;
                case 2: goto L_0x004d;
                case 3: goto L_0x0033;
                case 4: goto L_0x0030;
                case 5: goto L_0x0016;
                case 6: goto L_0x0011;
                case 7: goto L_0x0010;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.UnsupportedOperationException r2 = new java.lang.UnsupportedOperationException
            r2.<init>()
            throw r2
        L_0x0010:
            return r3
        L_0x0011:
            java.lang.Byte r2 = java.lang.Byte.valueOf(r4)
            return r2
        L_0x0016:
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxc> r2 = zzakh
            if (r2 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzaxc> r3 = com.google.android.gms.internal.ads.zzaxc.class
            monitor-enter(r3)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxc> r2 = zzakh     // Catch:{ all -> 0x002c }
            if (r2 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzaxc r4 = zzdkn     // Catch:{ all -> 0x002c }
            r2.<init>(r4)     // Catch:{ all -> 0x002c }
            zzakh = r2     // Catch:{ all -> 0x002c }
        L_0x002a:
            monitor-exit(r3)     // Catch:{ all -> 0x002c }
            goto L_0x002f
        L_0x002c:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002c }
            throw r2
        L_0x002f:
            return r2
        L_0x0030:
            com.google.android.gms.internal.ads.zzaxc r2 = zzdkn
            return r2
        L_0x0033:
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            java.lang.String r0 = "zzdih"
            r2[r3] = r0
            java.lang.String r3 = "zzdkm"
            r2[r4] = r3
            r3 = 2
            java.lang.String r4 = "zzdip"
            r2[r3] = r4
            com.google.android.gms.internal.ads.zzaxc r3 = zzdkn
            java.lang.String r4 = "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001\u000b\u0002\t\u0003\n"
            java.lang.Object r2 = zza(r3, r4, r2)
            return r2
        L_0x004d:
            com.google.android.gms.internal.ads.zzaxc$zza r2 = new com.google.android.gms.internal.ads.zzaxc$zza
            r2.<init>(r3)
            return r2
        L_0x0053:
            com.google.android.gms.internal.ads.zzaxc r2 = new com.google.android.gms.internal.ads.zzaxc
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaxc.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final zzbah zzwv() {
        return this.zzdip;
    }

    public final zzaxg zzym() {
        zzaxg zzaxg = this.zzdkm;
        return zzaxg == null ? zzaxg.zzyu() : zzaxg;
    }
}
