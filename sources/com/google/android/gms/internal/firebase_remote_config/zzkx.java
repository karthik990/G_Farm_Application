package com.google.android.gms.internal.firebase_remote_config;

public final class zzkx {

    public static final class zza extends zzhi<zza, C6217zza> implements zziq {
        private static volatile zziz<zza> zzmf;
        /* access modifiers changed from: private */
        public static final zza zzzt = new zza();
        private String zzzs = "";

        /* renamed from: com.google.android.gms.internal.firebase_remote_config.zzkx$zza$zza reason: collision with other inner class name */
        public static final class C6217zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zza, C6217zza> implements zziq {
            private C6217zza() {
                super(zza.zzzt);
            }

            /* synthetic */ C6217zza(zzky zzky) {
                this();
            }
        }

        private zza() {
        }

        /* JADX WARNING: type inference failed for: r1v8, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zza>] */
        /* JADX WARNING: type inference failed for: r1v9, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r1v11, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zza>] */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: type inference failed for: r1v13, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zza>, com.google.android.gms.internal.firebase_remote_config.zzhi$zzb] */
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
        public final java.lang.Object zzb(int r1, java.lang.Object r2, java.lang.Object r3) {
            /*
                r0 = this;
                int[] r2 = com.google.android.gms.internal.firebase_remote_config.zzky.zzlz
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zza> r1 = zzmf
                if (r1 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzkx$zza> r2 = com.google.android.gms.internal.firebase_remote_config.zzkx.zza.class
                monitor-enter(r2)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zza> r1 = zzmf     // Catch:{ all -> 0x002c }
                if (r1 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r1 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzkx$zza r3 = zzzt     // Catch:{ all -> 0x002c }
                r1.<init>(r3)     // Catch:{ all -> 0x002c }
                zzmf = r1     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzkx$zza r1 = zzzt
                return r1
            L_0x0033:
                java.lang.Object[] r1 = new java.lang.Object[r3]
                r2 = 0
                java.lang.String r3 = "zzzs"
                r1[r2] = r3
                com.google.android.gms.internal.firebase_remote_config.zzkx$zza r2 = zzzt
                java.lang.String r3 = "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ"
                java.lang.Object r1 = zza(r2, r3, r1)
                return r1
            L_0x0043:
                com.google.android.gms.internal.firebase_remote_config.zzkx$zza$zza r1 = new com.google.android.gms.internal.firebase_remote_config.zzkx$zza$zza
                r1.<init>(r2)
                return r1
            L_0x0049:
                com.google.android.gms.internal.firebase_remote_config.zzkx$zza r1 = new com.google.android.gms.internal.firebase_remote_config.zzkx$zza
                r1.<init>()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzkx.zza.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        static {
            zzhi.zza(zza.class, zzzt);
        }
    }

    public static final class zzb extends zzhi<zzb, zza> implements zziq {
        /* access modifiers changed from: private */
        public static final zzb zzaag = new zzb();
        private static volatile zziz<zzb> zzmf;
        private String zzaaa;
        private String zzaab;
        private String zzaac;
        private String zzaad;
        private int zzaae;
        private zzhn<zza> zzaaf = zzgw();
        private int zzma;
        private String zzzs;
        private String zzzu;
        private long zzzv;
        private String zzzw;
        private long zzzx;
        private long zzzy;
        private String zzzz;

        public static final class zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zzb, zza> implements zziq {
            private zza() {
                super(zzb.zzaag);
            }

            /* synthetic */ zza(zzky zzky) {
                this();
            }
        }

        private zzb() {
            String str = "";
            this.zzzs = str;
            this.zzzu = str;
            this.zzzw = str;
            this.zzzz = str;
            this.zzaaa = str;
            this.zzaab = str;
            this.zzaac = str;
            this.zzaad = str;
        }

        public final String zzjq() {
            return this.zzzs;
        }

        public final String zzjr() {
            return this.zzzu;
        }

        public final long zzjs() {
            return this.zzzv;
        }

        public final String zzjt() {
            return this.zzzw;
        }

        public final long zzju() {
            return this.zzzx;
        }

        public final long zzjv() {
            return this.zzzy;
        }

        public static zzb zzf(byte[] bArr) throws zzho {
            return (zzb) zzhi.zza(zzaag, bArr);
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zzb>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zzb>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zzb>, com.google.android.gms.internal.firebase_remote_config.zzhi$zzb] */
        /* JADX WARNING: type inference failed for: r2v17 */
        /* JADX WARNING: type inference failed for: r2v18 */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v13
          assigns: []
          uses: []
          mth insns count: 66
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
        public final java.lang.Object zzb(int r2, java.lang.Object r3, java.lang.Object r4) {
            /*
                r1 = this;
                int[] r3 = com.google.android.gms.internal.firebase_remote_config.zzky.zzlz
                r4 = 1
                int r2 = r2 - r4
                r2 = r3[r2]
                r3 = 0
                switch(r2) {
                    case 1: goto L_0x0097;
                    case 2: goto L_0x0091;
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zzb> r2 = zzmf
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzkx$zzb> r3 = com.google.android.gms.internal.firebase_remote_config.zzkx.zzb.class
                monitor-enter(r3)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzkx$zzb> r2 = zzmf     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzkx$zzb r4 = zzaag     // Catch:{ all -> 0x002c }
                r2.<init>(r4)     // Catch:{ all -> 0x002c }
                zzmf = r2     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzkx$zzb r2 = zzaag
                return r2
            L_0x0033:
                r2 = 15
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzma"
                r2[r3] = r0
                java.lang.String r3 = "zzzs"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzzu"
                r2[r3] = r4
                r3 = 3
                java.lang.String r4 = "zzzv"
                r2[r3] = r4
                r3 = 4
                java.lang.String r4 = "zzzw"
                r2[r3] = r4
                r3 = 5
                java.lang.String r4 = "zzzx"
                r2[r3] = r4
                r3 = 6
                java.lang.String r4 = "zzzy"
                r2[r3] = r4
                r3 = 7
                java.lang.String r4 = "zzzz"
                r2[r3] = r4
                r3 = 8
                java.lang.String r4 = "zzaaa"
                r2[r3] = r4
                r3 = 9
                java.lang.String r4 = "zzaab"
                r2[r3] = r4
                r3 = 10
                java.lang.String r4 = "zzaac"
                r2[r3] = r4
                r3 = 11
                java.lang.String r4 = "zzaad"
                r2[r3] = r4
                r3 = 12
                java.lang.String r4 = "zzaae"
                r2[r3] = r4
                r3 = 13
                java.lang.String r4 = "zzaaf"
                r2[r3] = r4
                r3 = 14
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzkx$zza> r4 = com.google.android.gms.internal.firebase_remote_config.zzkx.zza.class
                r2[r3] = r4
                com.google.android.gms.internal.firebase_remote_config.zzkx$zzb r3 = zzaag
                java.lang.String r4 = "\u0000\r\u0000\u0001\u0001\r\r\u0000\u0001\u0000\u0001Ȉ\u0002Ȉ\u0003\u0002\u0004Ȉ\u0005\u0002\u0006\u0002\u0007Ȉ\bȈ\tȈ\nȈ\u000bȈ\f\f\r\u001b"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0091:
                com.google.android.gms.internal.firebase_remote_config.zzkx$zzb$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzkx$zzb$zza
                r2.<init>(r3)
                return r2
            L_0x0097:
                com.google.android.gms.internal.firebase_remote_config.zzkx$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzkx$zzb
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzkx.zzb.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        static {
            zzhi.zza(zzb.class, zzaag);
        }
    }
}
