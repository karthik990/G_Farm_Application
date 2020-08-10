package com.google.android.gms.internal.ads;

import java.util.List;

public final class zzaxr extends zzbbo<zzaxr, zza> implements zzbcw {
    private static volatile zzbdf<zzaxr> zzakh;
    /* access modifiers changed from: private */
    public static final zzaxr zzdlt = new zzaxr();
    private int zzdlq;
    private int zzdlr;
    private zzbbt<zzb> zzdls = zzadd();

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzaxr, zza> implements zzbcw {
        private zza() {
            super(zzaxr.zzdlt);
        }

        /* synthetic */ zza(zzaxs zzaxs) {
            this();
        }
    }

    public static final class zzb extends zzbbo<zzb, zza> implements zzbcw {
        private static volatile zzbdf<zzb> zzakh;
        /* access modifiers changed from: private */
        public static final zzb zzdlx = new zzb();
        private int zzdlj;
        private zzaxi zzdlu;
        private int zzdlv;
        private int zzdlw;

        public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzb, zza> implements zzbcw {
            private zza() {
                super(zzb.zzdlx);
            }

            /* synthetic */ zza(zzaxs zzaxs) {
                this();
            }
        }

        static {
            zzbbo.zza(zzb.class, zzdlx);
        }

        private zzb() {
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr$zzb>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr$zzb>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr$zzb>, com.google.android.gms.internal.ads.zzbbo$zzb] */
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
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
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
                int[] r3 = com.google.android.gms.internal.ads.zzaxs.zzakf
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
                com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr$zzb> r2 = zzakh
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.ads.zzaxr$zzb> r3 = com.google.android.gms.internal.ads.zzaxr.zzb.class
                monitor-enter(r3)
                com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr$zzb> r2 = zzakh     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.ads.zzaxr$zzb r4 = zzdlx     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.ads.zzaxr$zzb r2 = zzdlx
                return r2
            L_0x0033:
                r2 = 4
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzdlu"
                r2[r3] = r0
                java.lang.String r3 = "zzdlv"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzdlw"
                r2[r3] = r4
                r3 = 3
                java.lang.String r4 = "zzdlj"
                r2[r3] = r4
                com.google.android.gms.internal.ads.zzaxr$zzb r3 = zzdlx
                java.lang.String r4 = "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001\t\u0002\f\u0003\u000b\u0004\f"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0052:
                com.google.android.gms.internal.ads.zzaxr$zzb$zza r2 = new com.google.android.gms.internal.ads.zzaxr$zzb$zza
                r2.<init>(r3)
                return r2
            L_0x0058:
                com.google.android.gms.internal.ads.zzaxr$zzb r2 = new com.google.android.gms.internal.ads.zzaxr$zzb
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaxr.zzb.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public final boolean zzzo() {
            return this.zzdlu != null;
        }

        public final zzaxi zzzp() {
            zzaxi zzaxi = this.zzdlu;
            return zzaxi == null ? zzaxi.zzza() : zzaxi;
        }

        public final zzaxl zzzq() {
            zzaxl zzax = zzaxl.zzax(this.zzdlv);
            return zzax == null ? zzaxl.UNRECOGNIZED : zzax;
        }

        public final int zzzr() {
            return this.zzdlw;
        }

        public final zzayd zzzs() {
            zzayd zzbg = zzayd.zzbg(this.zzdlj);
            return zzbg == null ? zzayd.UNRECOGNIZED : zzbg;
        }
    }

    static {
        zzbbo.zza(zzaxr.class, zzdlt);
    }

    private zzaxr() {
    }

    public static zzaxr zzj(byte[] bArr) throws zzbbu {
        return (zzaxr) zzbbo.zzb(zzdlt, bArr);
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr>] */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr>] */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr>, com.google.android.gms.internal.ads.zzbbo$zzb] */
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
            int[] r3 = com.google.android.gms.internal.ads.zzaxs.zzakf
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
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr> r2 = zzakh
            if (r2 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzaxr> r3 = com.google.android.gms.internal.ads.zzaxr.class
            monitor-enter(r3)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr> r2 = zzakh     // Catch:{ all -> 0x002c }
            if (r2 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzaxr r4 = zzdlt     // Catch:{ all -> 0x002c }
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
            com.google.android.gms.internal.ads.zzaxr r2 = zzdlt
            return r2
        L_0x0033:
            r2 = 4
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            java.lang.String r0 = "zzdlq"
            r2[r3] = r0
            java.lang.String r3 = "zzdlr"
            r2[r4] = r3
            r3 = 2
            java.lang.String r4 = "zzdls"
            r2[r3] = r4
            r3 = 3
            java.lang.Class<com.google.android.gms.internal.ads.zzaxr$zzb> r4 = com.google.android.gms.internal.ads.zzaxr.zzb.class
            r2[r3] = r4
            com.google.android.gms.internal.ads.zzaxr r3 = zzdlt
            java.lang.String r4 = "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0003\u0000\u0001\u0000\u0001\u000b\u0002\u001b"
            java.lang.Object r2 = zza(r3, r4, r2)
            return r2
        L_0x0052:
            com.google.android.gms.internal.ads.zzaxr$zza r2 = new com.google.android.gms.internal.ads.zzaxr$zza
            r2.<init>(r3)
            return r2
        L_0x0058:
            com.google.android.gms.internal.ads.zzaxr r2 = new com.google.android.gms.internal.ads.zzaxr
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaxr.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public final int zzzk() {
        return this.zzdlr;
    }

    public final List<zzb> zzzl() {
        return this.zzdls;
    }

    public final int zzzm() {
        return this.zzdls.size();
    }
}
