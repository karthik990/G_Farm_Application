package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zze;

public final class zzawi extends zzbbo<zzawi, zza> implements zzbcw {
    private static volatile zzbdf<zzawi> zzakh;
    /* access modifiers changed from: private */
    public static final zzawi zzdjb = new zzawi();
    private int zzdih;
    private zzbah zzdip = zzbah.zzdpq;

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzawi, zza> implements zzbcw {
        private zza() {
            super(zzawi.zzdjb);
        }

        /* synthetic */ zza(zzawj zzawj) {
            this();
        }

        public final zza zzap(int i) {
            zzadh();
            ((zzawi) this.zzdtx).setVersion(0);
            return this;
        }

        public final zza zzv(zzbah zzbah) {
            zzadh();
            ((zzawi) this.zzdtx).zzk(zzbah);
            return this;
        }
    }

    static {
        zzbbo.zza(zzawi.class, zzdjb);
    }

    private zzawi() {
    }

    /* access modifiers changed from: private */
    public final void setVersion(int i) {
        this.zzdih = i;
    }

    /* access modifiers changed from: private */
    public final void zzk(zzbah zzbah) {
        if (zzbah != null) {
            this.zzdip = zzbah;
            return;
        }
        throw new NullPointerException();
    }

    public static zzawi zzu(zzbah zzbah) throws zzbbu {
        return (zzawi) zzbbo.zza(zzdjb, zzbah);
    }

    public static zza zzxn() {
        return (zza) ((com.google.android.gms.internal.ads.zzbbo.zza) zzdjb.zza(zze.zzdue, (Object) null, (Object) null));
    }

    public final int getVersion() {
        return this.zzdih;
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawi>] */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawi>] */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawi>] */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v13
      assigns: []
      uses: []
      mth insns count: 40
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
            int[] r3 = com.google.android.gms.internal.ads.zzawj.zzakf
            r4 = 1
            int r2 = r2 - r4
            r2 = r3[r2]
            r3 = 0
            switch(r2) {
                case 1: goto L_0x004e;
                case 2: goto L_0x0048;
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
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawi> r2 = zzakh
            if (r2 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzawi> r3 = com.google.android.gms.internal.ads.zzawi.class
            monitor-enter(r3)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawi> r2 = zzakh     // Catch:{ all -> 0x002c }
            if (r2 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzawi r4 = zzdjb     // Catch:{ all -> 0x002c }
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
            com.google.android.gms.internal.ads.zzawi r2 = zzdjb
            return r2
        L_0x0033:
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            java.lang.String r0 = "zzdih"
            r2[r3] = r0
            java.lang.String r3 = "zzdip"
            r2[r4] = r3
            com.google.android.gms.internal.ads.zzawi r3 = zzdjb
            java.lang.String r4 = "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0003\u0000\u0000\u0000\u0001\u000b\u0002\n"
            java.lang.Object r2 = zza(r3, r4, r2)
            return r2
        L_0x0048:
            com.google.android.gms.internal.ads.zzawi$zza r2 = new com.google.android.gms.internal.ads.zzawi$zza
            r2.<init>(r3)
            return r2
        L_0x004e:
            com.google.android.gms.internal.ads.zzawi r2 = new com.google.android.gms.internal.ads.zzawi
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzawi.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final zzbah zzwv() {
        return this.zzdip;
    }
}
