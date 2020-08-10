package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zze;

public final class zzaxp extends zzbbo<zzaxp, zza> implements zzbcw {
    private static volatile zzbdf<zzaxp> zzakh;
    /* access modifiers changed from: private */
    public static final zzaxp zzdlp = new zzaxp();
    private String zzdks;
    private String zzdll;
    private int zzdlm;
    private boolean zzdln;
    private String zzdlo;

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzaxp, zza> implements zzbcw {
        private zza() {
            super(zzaxp.zzdlp);
        }

        /* synthetic */ zza(zzaxq zzaxq) {
            this();
        }

        public final zza zzao(boolean z) {
            zzadh();
            ((zzaxp) this.zzdtx).zzan(true);
            return this;
        }

        public final zza zzaz(int i) {
            zzadh();
            ((zzaxp) this.zzdtx).zzay(0);
            return this;
        }

        public final zza zzee(String str) {
            zzadh();
            ((zzaxp) this.zzdtx).zzec(str);
            return this;
        }

        public final zza zzef(String str) {
            zzadh();
            ((zzaxp) this.zzdtx).zzea(str);
            return this;
        }

        public final zza zzeg(String str) {
            zzadh();
            ((zzaxp) this.zzdtx).zzed(str);
            return this;
        }
    }

    static {
        zzbbo.zza(zzaxp.class, zzdlp);
    }

    private zzaxp() {
        String str = "";
        this.zzdll = str;
        this.zzdks = str;
        this.zzdlo = str;
    }

    /* access modifiers changed from: private */
    public final void zzan(boolean z) {
        this.zzdln = z;
    }

    /* access modifiers changed from: private */
    public final void zzay(int i) {
        this.zzdlm = i;
    }

    /* access modifiers changed from: private */
    public final void zzea(String str) {
        if (str != null) {
            this.zzdks = str;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public final void zzec(String str) {
        if (str != null) {
            this.zzdll = str;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public final void zzed(String str) {
        if (str != null) {
            this.zzdlo = str;
            return;
        }
        throw new NullPointerException();
    }

    public static zza zzzi() {
        return (zza) ((com.google.android.gms.internal.ads.zzbbo.zza) zzdlp.zza(zze.zzdue, (Object) null, (Object) null));
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxp>] */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxp>] */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxp>, com.google.android.gms.internal.ads.zzbbo$zzb] */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v13
      assigns: []
      uses: []
      mth insns count: 46
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
            int[] r3 = com.google.android.gms.internal.ads.zzaxq.zzakf
            r4 = 1
            int r2 = r2 - r4
            r2 = r3[r2]
            r3 = 0
            switch(r2) {
                case 1: goto L_0x005d;
                case 2: goto L_0x0057;
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
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxp> r2 = zzakh
            if (r2 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzaxp> r3 = com.google.android.gms.internal.ads.zzaxp.class
            monitor-enter(r3)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxp> r2 = zzakh     // Catch:{ all -> 0x002c }
            if (r2 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzaxp r4 = zzdlp     // Catch:{ all -> 0x002c }
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
            com.google.android.gms.internal.ads.zzaxp r2 = zzdlp
            return r2
        L_0x0033:
            r2 = 5
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            java.lang.String r0 = "zzdll"
            r2[r3] = r0
            java.lang.String r3 = "zzdks"
            r2[r4] = r3
            r3 = 2
            java.lang.String r4 = "zzdlm"
            r2[r3] = r4
            r3 = 3
            java.lang.String r4 = "zzdln"
            r2[r3] = r4
            r3 = 4
            java.lang.String r4 = "zzdlo"
            r2[r3] = r4
            com.google.android.gms.internal.ads.zzaxp r3 = zzdlp
            java.lang.String r4 = "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0006\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003\u000b\u0004\u0007\u0005Ȉ"
            java.lang.Object r2 = zza(r3, r4, r2)
            return r2
        L_0x0057:
            com.google.android.gms.internal.ads.zzaxp$zza r2 = new com.google.android.gms.internal.ads.zzaxp$zza
            r2.<init>(r3)
            return r2
        L_0x005d:
            com.google.android.gms.internal.ads.zzaxp r2 = new com.google.android.gms.internal.ads.zzaxp
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaxp.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final String zzyw() {
        return this.zzdks;
    }

    public final String zzze() {
        return this.zzdll;
    }

    public final int zzzf() {
        return this.zzdlm;
    }

    public final boolean zzzg() {
        return this.zzdln;
    }

    public final String zzzh() {
        return this.zzdlo;
    }
}
