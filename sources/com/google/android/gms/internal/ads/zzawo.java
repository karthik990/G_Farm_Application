package com.google.android.gms.internal.ads;

public final class zzawo extends zzbbo<zzawo, zza> implements zzbcw {
    private static volatile zzbdf<zzawo> zzakh;
    /* access modifiers changed from: private */
    public static final zzawo zzdjk = new zzawo();
    private zzawq zzdjj;

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzawo, zza> implements zzbcw {
        private zza() {
            super(zzawo.zzdjk);
        }

        /* synthetic */ zza(zzawp zzawp) {
            this();
        }
    }

    static {
        zzbbo.zza(zzawo.class, zzdjk);
    }

    private zzawo() {
    }

    public static zzawo zzw(zzbah zzbah) throws zzbbu {
        return (zzawo) zzbbo.zza(zzdjk, zzbah);
    }

    /* JADX WARNING: type inference failed for: r1v8, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawo>] */
    /* JADX WARNING: type inference failed for: r1v9, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v11, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawo>] */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawo>, com.google.android.gms.internal.ads.zzbbo$zzb] */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v12
      assigns: []
      uses: []
      mth insns count: 38
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
    public final java.lang.Object zza(int r1, java.lang.Object r2, java.lang.Object r3) {
        /*
            r0 = this;
            int[] r2 = com.google.android.gms.internal.ads.zzawp.zzakf
            r3 = 1
            int r1 = r1 - r3
            r1 = r2[r1]
            r2 = 0
            switch(r1) {
                case 1: goto L_0x0049;
                case 2: goto L_0x0043;
                case 3: goto L_0x0033;
                case 4: goto L_0x0030;
                case 5: goto L_0x0016;
                case 6: goto L_0x0011;
                case 7: goto L_0x0010;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException
            r1.<init>()
            throw r1
        L_0x0010:
            return r2
        L_0x0011:
            java.lang.Byte r1 = java.lang.Byte.valueOf(r3)
            return r1
        L_0x0016:
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawo> r1 = zzakh
            if (r1 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzawo> r2 = com.google.android.gms.internal.ads.zzawo.class
            monitor-enter(r2)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawo> r1 = zzakh     // Catch:{ all -> 0x002c }
            if (r1 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r1 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzawo r3 = zzdjk     // Catch:{ all -> 0x002c }
            r1.<init>(r3)     // Catch:{ all -> 0x002c }
            zzakh = r1     // Catch:{ all -> 0x002c }
        L_0x002a:
            monitor-exit(r2)     // Catch:{ all -> 0x002c }
            goto L_0x002f
        L_0x002c:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x002c }
            throw r1
        L_0x002f:
            return r1
        L_0x0030:
            com.google.android.gms.internal.ads.zzawo r1 = zzdjk
            return r1
        L_0x0033:
            java.lang.Object[] r1 = new java.lang.Object[r3]
            r2 = 0
            java.lang.String r3 = "zzdjj"
            r1[r2] = r3
            com.google.android.gms.internal.ads.zzawo r2 = zzdjk
            java.lang.String r3 = "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0002\u0000\u0000\u0000\u0001\t"
            java.lang.Object r1 = zza(r2, r3, r1)
            return r1
        L_0x0043:
            com.google.android.gms.internal.ads.zzawo$zza r1 = new com.google.android.gms.internal.ads.zzawo$zza
            r1.<init>(r2)
            return r1
        L_0x0049:
            com.google.android.gms.internal.ads.zzawo r1 = new com.google.android.gms.internal.ads.zzawo
            r1.<init>()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzawo.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final zzawq zzxs() {
        zzawq zzawq = this.zzdjj;
        return zzawq == null ? zzawq.zzxx() : zzawq;
    }
}
