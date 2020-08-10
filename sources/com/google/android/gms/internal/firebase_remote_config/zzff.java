package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class zzff {

    public static final class zza extends zzhi<zza, C6216zza> implements zziq {
        /* access modifiers changed from: private */
        public static final zza zzme = new zza();
        private static volatile zziz<zza> zzmf;
        private int zzma;
        private zzhn<zzd> zzmb = zzgw();
        private long zzmc;
        private zzhn<zzfw> zzmd = zzgw();

        /* renamed from: com.google.android.gms.internal.firebase_remote_config.zzff$zza$zza reason: collision with other inner class name */
        public static final class C6216zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zza, C6216zza> implements zziq {
            private C6216zza() {
                super(zza.zzme);
            }

            /* synthetic */ C6216zza(zzfg zzfg) {
                this();
            }
        }

        private zza() {
        }

        public final List<zzd> zzdj() {
            return this.zzmb;
        }

        public final long getTimestamp() {
            return this.zzmc;
        }

        public final List<zzfw> zzdk() {
            return this.zzmd;
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zza>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zza>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zza>, com.google.android.gms.internal.firebase_remote_config.zzhi$zzb] */
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
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object zzb(int r2, java.lang.Object r3, java.lang.Object r4) {
            /*
                r1 = this;
                int[] r3 = com.google.android.gms.internal.firebase_remote_config.zzfg.zzlz
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zza> r2 = zzmf
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zza> r3 = com.google.android.gms.internal.firebase_remote_config.zzff.zza.class
                monitor-enter(r3)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zza> r2 = zzmf     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzff$zza r4 = zzme     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzff$zza r2 = zzme
                return r2
            L_0x0033:
                r2 = 5
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzma"
                r2[r3] = r0
                java.lang.String r3 = "zzmb"
                r2[r4] = r3
                r3 = 2
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zzd> r4 = com.google.android.gms.internal.firebase_remote_config.zzff.zzd.class
                r2[r3] = r4
                r3 = 3
                java.lang.String r4 = "zzmc"
                r2[r3] = r4
                r3 = 4
                java.lang.String r4 = "zzmd"
                r2[r3] = r4
                com.google.android.gms.internal.firebase_remote_config.zzff$zza r3 = zzme
                java.lang.String r4 = "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0002\u0000\u0001\u001b\u0002\u0005\u0000\u0003\u001c"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0057:
                com.google.android.gms.internal.firebase_remote_config.zzff$zza$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zza$zza
                r2.<init>(r3)
                return r2
            L_0x005d:
                com.google.android.gms.internal.firebase_remote_config.zzff$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zza
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzff.zza.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public static zza zzdl() {
            return zzme;
        }

        static {
            zzhi.zza(zza.class, zzme);
        }
    }

    public static final class zzb extends zzhi<zzb, zza> implements zziq {
        private static volatile zziz<zzb> zzmf;
        /* access modifiers changed from: private */
        public static final zzb zzmi = new zzb();
        private int zzma;
        private String zzmg = "";
        private zzfw zzmh = zzfw.zzop;

        public static final class zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zzb, zza> implements zziq {
            private zza() {
                super(zzb.zzmi);
            }

            /* synthetic */ zza(zzfg zzfg) {
                this();
            }
        }

        private zzb() {
        }

        public final String getKey() {
            return this.zzmg;
        }

        public final zzfw zzdn() {
            return this.zzmh;
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzb>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzb>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzb>, com.google.android.gms.internal.firebase_remote_config.zzhi$zzb] */
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
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object zzb(int r2, java.lang.Object r3, java.lang.Object r4) {
            /*
                r1 = this;
                int[] r3 = com.google.android.gms.internal.firebase_remote_config.zzfg.zzlz
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzb> r2 = zzmf
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zzb> r3 = com.google.android.gms.internal.firebase_remote_config.zzff.zzb.class
                monitor-enter(r3)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzb> r2 = zzmf     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzff$zzb r4 = zzmi     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzff$zzb r2 = zzmi
                return r2
            L_0x0033:
                r2 = 3
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzma"
                r2[r3] = r0
                java.lang.String r3 = "zzmg"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzmh"
                r2[r3] = r4
                com.google.android.gms.internal.firebase_remote_config.zzff$zzb r3 = zzmi
                java.lang.String r4 = "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\b\u0000\u0002\n\u0001"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x004d:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzb$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzb$zza
                r2.<init>(r3)
                return r2
            L_0x0053:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzb
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzff.zzb.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        static {
            zzhi.zza(zzb.class, zzmi);
        }
    }

    public static final class zzc extends zzhi<zzc, zza> implements zziq {
        private static volatile zziz<zzc> zzmf;
        /* access modifiers changed from: private */
        public static final zzc zzmm = new zzc();
        private int zzma;
        private int zzmj;
        private boolean zzmk;
        private long zzml;

        public static final class zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zzc, zza> implements zziq {
            private zza() {
                super(zzc.zzmm);
            }

            /* synthetic */ zza(zzfg zzfg) {
                this();
            }
        }

        private zzc() {
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzc>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzc>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzc>, com.google.android.gms.internal.firebase_remote_config.zzhi$zzb] */
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
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object zzb(int r2, java.lang.Object r3, java.lang.Object r4) {
            /*
                r1 = this;
                int[] r3 = com.google.android.gms.internal.firebase_remote_config.zzfg.zzlz
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzc> r2 = zzmf
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zzc> r3 = com.google.android.gms.internal.firebase_remote_config.zzff.zzc.class
                monitor-enter(r3)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzc> r2 = zzmf     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzff$zzc r4 = zzmm     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzff$zzc r2 = zzmm
                return r2
            L_0x0033:
                r2 = 4
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzma"
                r2[r3] = r0
                java.lang.String r3 = "zzmj"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzmk"
                r2[r3] = r4
                r3 = 3
                java.lang.String r4 = "zzml"
                r2[r3] = r4
                com.google.android.gms.internal.firebase_remote_config.zzff$zzc r3 = zzmm
                java.lang.String r4 = "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0007\u0001\u0003\u0005\u0002"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0052:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzc$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzc$zza
                r2.<init>(r3)
                return r2
            L_0x0058:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzc r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzc
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzff.zzc.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        static {
            zzhi.zza(zzc.class, zzmm);
        }
    }

    public static final class zzd extends zzhi<zzd, zza> implements zziq {
        private static volatile zziz<zzd> zzmf;
        /* access modifiers changed from: private */
        public static final zzd zzmp = new zzd();
        private int zzma;
        private String zzmn = "";
        private zzhn<zzb> zzmo = zzgw();

        public static final class zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zzd, zza> implements zziq {
            private zza() {
                super(zzd.zzmp);
            }

            /* synthetic */ zza(zzfg zzfg) {
                this();
            }
        }

        private zzd() {
        }

        public final String getNamespace() {
            return this.zzmn;
        }

        public final List<zzb> zzdq() {
            return this.zzmo;
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzd>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzd>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zzhi$zzb, com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzd>] */
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
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object zzb(int r2, java.lang.Object r3, java.lang.Object r4) {
            /*
                r1 = this;
                int[] r3 = com.google.android.gms.internal.firebase_remote_config.zzfg.zzlz
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzd> r2 = zzmf
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zzd> r3 = com.google.android.gms.internal.firebase_remote_config.zzff.zzd.class
                monitor-enter(r3)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzd> r2 = zzmf     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzff$zzd r4 = zzmp     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzff$zzd r2 = zzmp
                return r2
            L_0x0033:
                r2 = 4
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzma"
                r2[r3] = r0
                java.lang.String r3 = "zzmn"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzmo"
                r2[r3] = r4
                r3 = 3
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zzb> r4 = com.google.android.gms.internal.firebase_remote_config.zzff.zzb.class
                r2[r3] = r4
                com.google.android.gms.internal.firebase_remote_config.zzff$zzd r3 = zzmp
                java.lang.String r4 = "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\b\u0000\u0002\u001b"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0052:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzd$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzd$zza
                r2.<init>(r3)
                return r2
            L_0x0058:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzd r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzd
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzff.zzd.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        static {
            zzhi.zza(zzd.class, zzmp);
        }
    }

    public static final class zze extends zzhi<zze, zza> implements zziq {
        private static volatile zziz<zze> zzmf;
        /* access modifiers changed from: private */
        public static final zze zzmv = new zze();
        private int zzma;
        private zza zzmq;
        private zza zzmr;
        private zza zzms;
        private zzc zzmt;
        private zzhn<zzf> zzmu = zzgw();

        public static final class zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zze, zza> implements zziq {
            private zza() {
                super(zze.zzmv);
            }

            /* synthetic */ zza(zzfg zzfg) {
                this();
            }
        }

        private zze() {
        }

        public final zza zzds() {
            zza zza2 = this.zzmq;
            return zza2 == null ? zza.zzdl() : zza2;
        }

        public final zza zzdt() {
            zza zza2 = this.zzmr;
            return zza2 == null ? zza.zzdl() : zza2;
        }

        public final zza zzdu() {
            zza zza2 = this.zzms;
            return zza2 == null ? zza.zzdl() : zza2;
        }

        public static zze zzb(InputStream inputStream) throws IOException {
            return (zze) zzhi.zza(zzmv, inputStream);
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zze>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zze>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zze>, com.google.android.gms.internal.firebase_remote_config.zzhi$zzb] */
        /* JADX WARNING: type inference failed for: r2v17 */
        /* JADX WARNING: type inference failed for: r2v18 */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v13
          assigns: []
          uses: []
          mth insns count: 50
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
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object zzb(int r2, java.lang.Object r3, java.lang.Object r4) {
            /*
                r1 = this;
                int[] r3 = com.google.android.gms.internal.firebase_remote_config.zzfg.zzlz
                r4 = 1
                int r2 = r2 - r4
                r2 = r3[r2]
                r3 = 0
                switch(r2) {
                    case 1: goto L_0x0067;
                    case 2: goto L_0x0061;
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zze> r2 = zzmf
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zze> r3 = com.google.android.gms.internal.firebase_remote_config.zzff.zze.class
                monitor-enter(r3)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zze> r2 = zzmf     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzff$zze r4 = zzmv     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzff$zze r2 = zzmv
                return r2
            L_0x0033:
                r2 = 7
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzma"
                r2[r3] = r0
                java.lang.String r3 = "zzmq"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzmr"
                r2[r3] = r4
                r3 = 3
                java.lang.String r4 = "zzms"
                r2[r3] = r4
                r3 = 4
                java.lang.String r4 = "zzmt"
                r2[r3] = r4
                r3 = 5
                java.lang.String r4 = "zzmu"
                r2[r3] = r4
                r3 = 6
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zzf> r4 = com.google.android.gms.internal.firebase_remote_config.zzff.zzf.class
                r2[r3] = r4
                com.google.android.gms.internal.firebase_remote_config.zzff$zze r3 = zzmv
                java.lang.String r4 = "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003\u0005\u001b"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0061:
                com.google.android.gms.internal.firebase_remote_config.zzff$zze$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zze$zza
                r2.<init>(r3)
                return r2
            L_0x0067:
                com.google.android.gms.internal.firebase_remote_config.zzff$zze r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zze
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzff.zze.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        static {
            zzhi.zza(zze.class, zzmv);
        }
    }

    public static final class zzf extends zzhi<zzf, zza> implements zziq {
        private static volatile zziz<zzf> zzmf;
        /* access modifiers changed from: private */
        public static final zzf zzmy = new zzf();
        private int zzma;
        private String zzmn = "";
        private int zzmw;
        private long zzmx;

        public static final class zza extends com.google.android.gms.internal.firebase_remote_config.zzhi.zza<zzf, zza> implements zziq {
            private zza() {
                super(zzf.zzmy);
            }

            /* synthetic */ zza(zzfg zzfg) {
                this();
            }
        }

        private zzf() {
        }

        /* JADX WARNING: type inference failed for: r2v9, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzf>] */
        /* JADX WARNING: type inference failed for: r2v10, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r2v12, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzf>] */
        /* JADX WARNING: type inference failed for: r2v13 */
        /* JADX WARNING: type inference failed for: r2v14, types: [com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzf>, com.google.android.gms.internal.firebase_remote_config.zzhi$zzb] */
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
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object zzb(int r2, java.lang.Object r3, java.lang.Object r4) {
            /*
                r1 = this;
                int[] r3 = com.google.android.gms.internal.firebase_remote_config.zzfg.zzlz
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
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzf> r2 = zzmf
                if (r2 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.firebase_remote_config.zzff$zzf> r3 = com.google.android.gms.internal.firebase_remote_config.zzff.zzf.class
                monitor-enter(r3)
                com.google.android.gms.internal.firebase_remote_config.zziz<com.google.android.gms.internal.firebase_remote_config.zzff$zzf> r2 = zzmf     // Catch:{ all -> 0x002c }
                if (r2 != 0) goto L_0x002a
                com.google.android.gms.internal.firebase_remote_config.zzhi$zzb r2 = new com.google.android.gms.internal.firebase_remote_config.zzhi$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.firebase_remote_config.zzff$zzf r4 = zzmy     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.firebase_remote_config.zzff$zzf r2 = zzmy
                return r2
            L_0x0033:
                r2 = 4
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r3 = 0
                java.lang.String r0 = "zzma"
                r2[r3] = r0
                java.lang.String r3 = "zzmw"
                r2[r4] = r3
                r3 = 2
                java.lang.String r4 = "zzmx"
                r2[r3] = r4
                r3 = 3
                java.lang.String r4 = "zzmn"
                r2[r3] = r4
                com.google.android.gms.internal.firebase_remote_config.zzff$zzf r3 = zzmy
                java.lang.String r4 = "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0005\u0001\u0003\b\u0002"
                java.lang.Object r2 = zza(r3, r4, r2)
                return r2
            L_0x0052:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzf$zza r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzf$zza
                r2.<init>(r3)
                return r2
            L_0x0058:
                com.google.android.gms.internal.firebase_remote_config.zzff$zzf r2 = new com.google.android.gms.internal.firebase_remote_config.zzff$zzf
                r2.<init>()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzff.zzf.zzb(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        static {
            zzhi.zza(zzf.class, zzmy);
        }
    }
}
