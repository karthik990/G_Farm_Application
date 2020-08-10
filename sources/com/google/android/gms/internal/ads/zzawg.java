package com.google.android.gms.internal.ads;

public final class zzawg extends zzbbo<zzawg, zza> implements zzbcw {
    private static volatile zzbdf<zzawg> zzakh;
    /* access modifiers changed from: private */
    public static final zzawg zzdja = new zzawg();
    private int zzdir;

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzawg, zza> implements zzbcw {
        private zza() {
            super(zzawg.zzdja);
        }

        /* synthetic */ zza(zzawh zzawh) {
            this();
        }
    }

    static {
        zzbbo.zza(zzawg.class, zzdja);
    }

    private zzawg() {
    }

    public static zzawg zzt(zzbah zzbah) throws zzbbu {
        return (zzawg) zzbbo.zza(zzdja, zzbah);
    }

    public final int getKeySize() {
        return this.zzdir;
    }

    /* JADX WARNING: type inference failed for: r1v8, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawg>] */
    /* JADX WARNING: type inference failed for: r1v9, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r1v11, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawg>] */
    /* JADX WARNING: type inference failed for: r1v12 */
    /* JADX WARNING: type inference failed for: r1v13, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawg>, com.google.android.gms.internal.ads.zzbbo$zzb] */
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
            int[] r2 = com.google.android.gms.internal.ads.zzawh.zzakf
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
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawg> r1 = zzakh
            if (r1 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzawg> r2 = com.google.android.gms.internal.ads.zzawg.class
            monitor-enter(r2)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawg> r1 = zzakh     // Catch:{ all -> 0x002c }
            if (r1 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r1 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzawg r3 = zzdja     // Catch:{ all -> 0x002c }
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
            com.google.android.gms.internal.ads.zzawg r1 = zzdja
            return r1
        L_0x0033:
            java.lang.Object[] r1 = new java.lang.Object[r3]
            r2 = 0
            java.lang.String r3 = "zzdir"
            r1[r2] = r3
            com.google.android.gms.internal.ads.zzawg r2 = zzdja
            java.lang.String r3 = "\u0000\u0001\u0000\u0000\u0002\u0002\u0001\u0003\u0000\u0000\u0000\u0002\u000b"
            java.lang.Object r1 = zza(r2, r3, r1)
            return r1
        L_0x0043:
            com.google.android.gms.internal.ads.zzawg$zza r1 = new com.google.android.gms.internal.ads.zzawg$zza
            r1.<init>(r2)
            return r1
        L_0x0049:
            com.google.android.gms.internal.ads.zzawg r1 = new com.google.android.gms.internal.ads.zzawg
            r1.<init>()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzawg.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }
}
