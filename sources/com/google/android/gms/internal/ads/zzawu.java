package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zze;

public final class zzawu extends zzbbo<zzawu, zza> implements zzbcw {
    private static volatile zzbdf<zzawu> zzakh;
    /* access modifiers changed from: private */
    public static final zzawu zzdjt = new zzawu();
    private int zzdih;
    private zzawq zzdjj;
    private zzbah zzdjr = zzbah.zzdpq;
    private zzbah zzdjs = zzbah.zzdpq;

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzawu, zza> implements zzbcw {
        private zza() {
            super(zzawu.zzdjt);
        }

        /* synthetic */ zza(zzawv zzawv) {
            this();
        }

        public final zza zzac(zzbah zzbah) {
            zzadh();
            ((zzawu) this.zzdtx).zzz(zzbah);
            return this;
        }

        public final zza zzad(zzbah zzbah) {
            zzadh();
            ((zzawu) this.zzdtx).zzaa(zzbah);
            return this;
        }

        public final zza zzas(int i) {
            zzadh();
            ((zzawu) this.zzdtx).setVersion(0);
            return this;
        }

        public final zza zzc(zzawq zzawq) {
            zzadh();
            ((zzawu) this.zzdtx).zzb(zzawq);
            return this;
        }
    }

    static {
        zzbbo.zza(zzawu.class, zzdjt);
    }

    private zzawu() {
    }

    /* access modifiers changed from: private */
    public final void setVersion(int i) {
        this.zzdih = i;
    }

    /* access modifiers changed from: private */
    public final void zzaa(zzbah zzbah) {
        if (zzbah != null) {
            this.zzdjs = zzbah;
            return;
        }
        throw new NullPointerException();
    }

    public static zzawu zzab(zzbah zzbah) throws zzbbu {
        return (zzawu) zzbbo.zza(zzdjt, zzbah);
    }

    /* access modifiers changed from: private */
    public final void zzb(zzawq zzawq) {
        if (zzawq != null) {
            this.zzdjj = zzawq;
            return;
        }
        throw new NullPointerException();
    }

    public static zza zzye() {
        return (zza) ((com.google.android.gms.internal.ads.zzbbo.zza) zzdjt.zza(zze.zzdue, (Object) null, (Object) null));
    }

    public static zzawu zzyf() {
        return zzdjt;
    }

    /* access modifiers changed from: private */
    public final void zzz(zzbah zzbah) {
        if (zzbah != null) {
            this.zzdjr = zzbah;
            return;
        }
        throw new NullPointerException();
    }

    public final int getVersion() {
        return this.zzdih;
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawu>] */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawu>] */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawu>, com.google.android.gms.internal.ads.zzbbo$zzb] */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v13
      assigns: []
      uses: []
      mth insns count: 44
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
            int[] r3 = com.google.android.gms.internal.ads.zzawv.zzakf
            r4 = 1
            int r2 = r2 - r4
            r2 = r3[r2]
            r3 = 0
            switch(r2) {
                case 1: goto L_0x0058;
                case 2: goto L_0x0052;
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
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawu> r2 = zzakh
            if (r2 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzawu> r3 = com.google.android.gms.internal.ads.zzawu.class
            monitor-enter(r3)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawu> r2 = zzakh     // Catch:{ all -> 0x002c }
            if (r2 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzawu r4 = zzdjt     // Catch:{ all -> 0x002c }
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
            com.google.android.gms.internal.ads.zzawu r2 = zzdjt
            return r2
        L_0x0033:
            r2 = 4
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            java.lang.String r0 = "zzdih"
            r2[r3] = r0
            java.lang.String r3 = "zzdjj"
            r2[r4] = r3
            r3 = 2
            java.lang.String r4 = "zzdjr"
            r2[r3] = r4
            r3 = 3
            java.lang.String r4 = "zzdjs"
            r2[r3] = r4
            com.google.android.gms.internal.ads.zzawu r3 = zzdjt
            java.lang.String r4 = "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001\u000b\u0002\t\u0003\n\u0004\n"
            java.lang.Object r2 = zza(r3, r4, r2)
            return r2
        L_0x0052:
            com.google.android.gms.internal.ads.zzawu$zza r2 = new com.google.android.gms.internal.ads.zzawu$zza
            r2.<init>(r3)
            return r2
        L_0x0058:
            com.google.android.gms.internal.ads.zzawu r2 = new com.google.android.gms.internal.ads.zzawu
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzawu.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final zzawq zzxs() {
        zzawq zzawq = this.zzdjj;
        return zzawq == null ? zzawq.zzxx() : zzawq;
    }

    public final zzbah zzyc() {
        return this.zzdjr;
    }

    public final zzbah zzyd() {
        return this.zzdjs;
    }
}
