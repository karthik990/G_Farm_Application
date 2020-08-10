package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zze;

public final class zzaxi extends zzbbo<zzaxi, zza> implements zzbcw {
    private static volatile zzbdf<zzaxi> zzakh;
    /* access modifiers changed from: private */
    public static final zzaxi zzdkv = new zzaxi();
    private String zzdks = "";
    private zzbah zzdkt = zzbah.zzdpq;
    private int zzdku;

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzaxi, zza> implements zzbcw {
        private zza() {
            super(zzaxi.zzdkv);
        }

        /* synthetic */ zza(zzaxj zzaxj) {
            this();
        }

        public final zza zzai(zzbah zzbah) {
            zzadh();
            ((zzaxi) this.zzdtx).zzah(zzbah);
            return this;
        }

        public final zza zzb(zzb zzb) {
            zzadh();
            ((zzaxi) this.zzdtx).zza(zzb);
            return this;
        }

        public final zza zzeb(String str) {
            zzadh();
            ((zzaxi) this.zzdtx).zzea(str);
            return this;
        }
    }

    public enum zzb implements zzbbr {
        UNKNOWN_KEYMATERIAL(0),
        SYMMETRIC(1),
        ASYMMETRIC_PRIVATE(2),
        ASYMMETRIC_PUBLIC(3),
        REMOTE(4),
        UNRECOGNIZED(-1);
        
        private static final zzbbs<zzb> zzall = null;
        private final int value;

        static {
            zzall = new zzaxk();
        }

        private zzb(int i) {
            this.value = i;
        }

        public static zzb zzaw(int i) {
            if (i == 0) {
                return UNKNOWN_KEYMATERIAL;
            }
            if (i == 1) {
                return SYMMETRIC;
            }
            if (i == 2) {
                return ASYMMETRIC_PRIVATE;
            }
            if (i == 3) {
                return ASYMMETRIC_PUBLIC;
            }
            if (i != 4) {
                return null;
            }
            return REMOTE;
        }

        public final int zzhq() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }
    }

    static {
        zzbbo.zza(zzaxi.class, zzdkv);
    }

    private zzaxi() {
    }

    /* access modifiers changed from: private */
    public final void zza(zzb zzb2) {
        if (zzb2 != null) {
            this.zzdku = zzb2.zzhq();
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public final void zzah(zzbah zzbah) {
        if (zzbah != null) {
            this.zzdkt = zzbah;
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public final void zzea(String str) {
        if (str != null) {
            this.zzdks = str;
            return;
        }
        throw new NullPointerException();
    }

    public static zza zzyz() {
        return (zza) ((com.google.android.gms.internal.ads.zzbbo.zza) zzdkv.zza(zze.zzdue, (Object) null, (Object) null));
    }

    public static zzaxi zzza() {
        return zzdkv;
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxi>] */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxi>] */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxi>, com.google.android.gms.internal.ads.zzbbo$zzb] */
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
            int[] r3 = com.google.android.gms.internal.ads.zzaxj.zzakf
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
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxi> r2 = zzakh
            if (r2 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzaxi> r3 = com.google.android.gms.internal.ads.zzaxi.class
            monitor-enter(r3)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxi> r2 = zzakh     // Catch:{ all -> 0x002c }
            if (r2 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzaxi r4 = zzdkv     // Catch:{ all -> 0x002c }
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
            com.google.android.gms.internal.ads.zzaxi r2 = zzdkv
            return r2
        L_0x0033:
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            java.lang.String r0 = "zzdks"
            r2[r3] = r0
            java.lang.String r3 = "zzdkt"
            r2[r4] = r3
            r3 = 2
            java.lang.String r4 = "zzdku"
            r2[r3] = r4
            com.google.android.gms.internal.ads.zzaxi r3 = zzdkv
            java.lang.String r4 = "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001Ȉ\u0002\n\u0003\f"
            java.lang.Object r2 = zza(r3, r4, r2)
            return r2
        L_0x004d:
            com.google.android.gms.internal.ads.zzaxi$zza r2 = new com.google.android.gms.internal.ads.zzaxi$zza
            r2.<init>(r3)
            return r2
        L_0x0053:
            com.google.android.gms.internal.ads.zzaxi r2 = new com.google.android.gms.internal.ads.zzaxi
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaxi.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final String zzyw() {
        return this.zzdks;
    }

    public final zzbah zzyx() {
        return this.zzdkt;
    }

    public final zzb zzyy() {
        zzb zzaw = zzb.zzaw(this.zzdku);
        return zzaw == null ? zzb.UNRECOGNIZED : zzaw;
    }
}
