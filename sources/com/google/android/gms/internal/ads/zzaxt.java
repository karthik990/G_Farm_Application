package com.google.android.gms.internal.ads;

import com.google.android.gms.internal.ads.zzbbo.zze;

public final class zzaxt extends zzbbo<zzaxt, zza> implements zzbcw {
    private static volatile zzbdf<zzaxt> zzakh;
    /* access modifiers changed from: private */
    public static final zzaxt zzdlz = new zzaxt();
    private int zzdlq;
    private int zzdlr;
    private zzbbt<zzb> zzdly = zzadd();

    public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzaxt, zza> implements zzbcw {
        private zza() {
            super(zzaxt.zzdlz);
        }

        /* synthetic */ zza(zzaxu zzaxu) {
            this();
        }

        public final zza zzb(zzb zzb) {
            zzadh();
            ((zzaxt) this.zzdtx).zza(zzb);
            return this;
        }

        public final zza zzbb(int i) {
            zzadh();
            ((zzaxt) this.zzdtx).zzba(i);
            return this;
        }
    }

    public static final class zzb extends zzbbo<zzb, zza> implements zzbcw {
        private static volatile zzbdf<zzb> zzakh;
        /* access modifiers changed from: private */
        public static final zzb zzdma = new zzb();
        private String zzdks = "";
        private int zzdlj;
        private int zzdlv;
        private int zzdlw;

        public static final class zza extends com.google.android.gms.internal.ads.zzbbo.zza<zzb, zza> implements zzbcw {
            private zza() {
                super(zzb.zzdma);
            }

            /* synthetic */ zza(zzaxu zzaxu) {
                this();
            }

            public final zza zzb(zzaxl zzaxl) {
                zzadh();
                ((zzb) this.zzdtx).zza(zzaxl);
                return this;
            }

            public final zza zzb(zzayd zzayd) {
                zzadh();
                ((zzb) this.zzdtx).zza(zzayd);
                return this;
            }

            public final zza zzbd(int i) {
                zzadh();
                ((zzb) this.zzdtx).zzbc(i);
                return this;
            }

            public final zza zzeh(String str) {
                zzadh();
                ((zzb) this.zzdtx).zzea(str);
                return this;
            }
        }

        static {
            zzbbo.zza(zzb.class, zzdma);
        }

        private zzb() {
        }

        /* access modifiers changed from: private */
        public final void zza(zzaxl zzaxl) {
            if (zzaxl != null) {
                this.zzdlv = zzaxl.zzhq();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public final void zza(zzayd zzayd) {
            if (zzayd != null) {
                this.zzdlj = zzayd.zzhq();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public final void zzbc(int i) {
            this.zzdlw = i;
        }

        /* access modifiers changed from: private */
        public final void zzea(String str) {
            if (str != null) {
                this.zzdks = str;
                return;
            }
            throw new NullPointerException();
        }

        public static zza zzzw() {
            return (zza) ((com.google.android.gms.internal.ads.zzbbo.zza) zzdma.zza(zze.zzdue, (Object) null, (Object) null));
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt$zzb>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt$zzb>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt$zzb>, com.google.android.gms.internal.ads.zzbbo$zzb] */
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
                int[] r3 = com.google.android.gms.internal.ads.zzaxu.zzakf
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
                com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt$zzb> r2 = zzakh
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.ads.zzaxt$zzb> r3 = com.google.android.gms.internal.ads.zzaxt.zzb.class
                monitor-enter(r3)
                com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt$zzb> r2 = zzakh     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.ads.zzaxt$zzb r4 = zzdma     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.ads.zzaxt$zzb r2 = zzdma
                return r2
            L_0x0033:
                r2 = 4
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzdks"
                r2[r3] = r0
                java.lang.String r3 = "zzdlv"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzdlw"
                r2[r3] = r4
                r3 = 3
                java.lang.String r4 = "zzdlj"
                r2[r3] = r4
                com.google.android.gms.internal.ads.zzaxt$zzb r3 = zzdma
                java.lang.String r4 = "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001Ȉ\u0002\f\u0003\u000b\u0004\f"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0052:
                com.google.android.gms.internal.ads.zzaxt$zzb$zza r2 = new com.google.android.gms.internal.ads.zzaxt$zzb$zza
                r2.<init>(r3)
                return r2
            L_0x0058:
                com.google.android.gms.internal.ads.zzaxt$zzb r2 = new com.google.android.gms.internal.ads.zzaxt$zzb
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaxt.zzb.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }

    static {
        zzbbo.zza(zzaxt.class, zzdlz);
    }

    private zzaxt() {
    }

    /* access modifiers changed from: private */
    public final void zza(zzb zzb2) {
        if (zzb2 != null) {
            if (!this.zzdly.zzaay()) {
                zzbbt<zzb> zzbbt = this.zzdly;
                int size = zzbbt.size();
                this.zzdly = zzbbt.zzbm(size == 0 ? 10 : size << 1);
            }
            this.zzdly.add(zzb2);
            return;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: private */
    public final void zzba(int i) {
        this.zzdlr = i;
    }

    public static zza zzzu() {
        return (zza) ((com.google.android.gms.internal.ads.zzbbo.zza) zzdlz.zza(zze.zzdue, (Object) null, (Object) null));
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt>] */
    /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt>] */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt>, com.google.android.gms.internal.ads.zzbbo$zzb] */
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
            int[] r3 = com.google.android.gms.internal.ads.zzaxu.zzakf
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
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt> r2 = zzakh
            if (r2 != 0) goto L_0x002f
            java.lang.Class<com.google.android.gms.internal.ads.zzaxt> r3 = com.google.android.gms.internal.ads.zzaxt.class
            monitor-enter(r3)
            com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxt> r2 = zzakh     // Catch:{ all -> 0x002c }
            if (r2 != 0) goto L_0x002a
            com.google.android.gms.internal.ads.zzbbo$zzb r2 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
            com.google.android.gms.internal.ads.zzaxt r4 = zzdlz     // Catch:{ all -> 0x002c }
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
            com.google.android.gms.internal.ads.zzaxt r2 = zzdlz
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
            java.lang.String r4 = "zzdly"
            r2[r3] = r4
            r3 = 3
            java.lang.Class<com.google.android.gms.internal.ads.zzaxt$zzb> r4 = com.google.android.gms.internal.ads.zzaxt.zzb.class
            r2[r3] = r4
            com.google.android.gms.internal.ads.zzaxt r3 = zzdlz
            java.lang.String r4 = "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0003\u0000\u0001\u0000\u0001\u000b\u0002\u001b"
            java.lang.Object r2 = zza(r3, r4, r2)
            return r2
        L_0x0052:
            com.google.android.gms.internal.ads.zzaxt$zza r2 = new com.google.android.gms.internal.ads.zzaxt$zza
            r2.<init>(r3)
            return r2
        L_0x0058:
            com.google.android.gms.internal.ads.zzaxt r2 = new com.google.android.gms.internal.ads.zzaxt
            r2.<init>()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzaxt.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
    }
}
