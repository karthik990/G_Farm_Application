package com.google.android.gms.internal.ads;

import androidx.core.view.PointerIconCompat;

public final class zzhu {

    public static final class zza extends zzbbo<zza, C6212zza> implements zzbcw {
        /* access modifiers changed from: private */
        public static final zza zzakg = new zza();
        private static volatile zzbdf<zza> zzakh;

        /* renamed from: com.google.android.gms.internal.ads.zzhu$zza$zza reason: collision with other inner class name */
        public static final class C6212zza extends com.google.android.gms.internal.ads.zzbbo.zza<zza, C6212zza> implements zzbcw {
            private C6212zza() {
                super(zza.zzakg);
            }

            /* synthetic */ C6212zza(zzhv zzhv) {
                this();
            }
        }

        public enum zzb implements zzbbr {
            UNKNOWN_EVENT_TYPE(0),
            AD_REQUEST(1),
            AD_LOADED(2),
            AD_FAILED_TO_LOAD(3),
            AD_FAILED_TO_LOAD_NO_FILL(4),
            AD_IMPRESSION(5),
            AD_FIRST_CLICK(6),
            AD_SUBSEQUENT_CLICK(7),
            REQUEST_WILL_START(8),
            REQUEST_DID_END(9),
            REQUEST_WILL_UPDATE_SIGNALS(10),
            REQUEST_DID_UPDATE_SIGNALS(11),
            REQUEST_WILL_BUILD_URL(12),
            REQUEST_DID_BUILD_URL(13),
            REQUEST_WILL_MAKE_NETWORK_REQUEST(14),
            REQUEST_DID_RECEIVE_NETWORK_RESPONSE(15),
            REQUEST_WILL_PROCESS_RESPONSE(16),
            REQUEST_DID_PROCESS_RESPONSE(17),
            REQUEST_WILL_RENDER(18),
            REQUEST_DID_RENDER(19),
            REQUEST_WILL_UPDATE_GMS_SIGNALS(1000),
            REQUEST_DID_UPDATE_GMS_SIGNALS(1001),
            REQUEST_FAILED_TO_UPDATE_GMS_SIGNALS(1002),
            REQUEST_FAILED_TO_BUILD_URL(1003),
            REQUEST_FAILED_TO_MAKE_NETWORK_REQUEST(1004),
            REQUEST_FAILED_TO_PROCESS_RESPONSE(1005),
            REQUEST_FAILED_TO_UPDATE_SIGNALS(PointerIconCompat.TYPE_CELL),
            BANNER_SIZE_INVALID(10000),
            BANNER_SIZE_VALID(10001);
            
            private static final zzbbs<zzb> zzall = null;
            private final int value;

            static {
                zzall = new zzhw();
            }

            private zzb(int i) {
                this.value = i;
            }

            public static zzb zzp(int i) {
                if (i == 10000) {
                    return BANNER_SIZE_INVALID;
                }
                if (i == 10001) {
                    return BANNER_SIZE_VALID;
                }
                switch (i) {
                    case 0:
                        return UNKNOWN_EVENT_TYPE;
                    case 1:
                        return AD_REQUEST;
                    case 2:
                        return AD_LOADED;
                    case 3:
                        return AD_FAILED_TO_LOAD;
                    case 4:
                        return AD_FAILED_TO_LOAD_NO_FILL;
                    case 5:
                        return AD_IMPRESSION;
                    case 6:
                        return AD_FIRST_CLICK;
                    case 7:
                        return AD_SUBSEQUENT_CLICK;
                    case 8:
                        return REQUEST_WILL_START;
                    case 9:
                        return REQUEST_DID_END;
                    case 10:
                        return REQUEST_WILL_UPDATE_SIGNALS;
                    case 11:
                        return REQUEST_DID_UPDATE_SIGNALS;
                    case 12:
                        return REQUEST_WILL_BUILD_URL;
                    case 13:
                        return REQUEST_DID_BUILD_URL;
                    case 14:
                        return REQUEST_WILL_MAKE_NETWORK_REQUEST;
                    case 15:
                        return REQUEST_DID_RECEIVE_NETWORK_RESPONSE;
                    case 16:
                        return REQUEST_WILL_PROCESS_RESPONSE;
                    case 17:
                        return REQUEST_DID_PROCESS_RESPONSE;
                    case 18:
                        return REQUEST_WILL_RENDER;
                    case 19:
                        return REQUEST_DID_RENDER;
                    default:
                        switch (i) {
                            case 1000:
                                return REQUEST_WILL_UPDATE_GMS_SIGNALS;
                            case 1001:
                                return REQUEST_DID_UPDATE_GMS_SIGNALS;
                            case 1002:
                                return REQUEST_FAILED_TO_UPDATE_GMS_SIGNALS;
                            case 1003:
                                return REQUEST_FAILED_TO_BUILD_URL;
                            case 1004:
                                return REQUEST_FAILED_TO_MAKE_NETWORK_REQUEST;
                            case 1005:
                                return REQUEST_FAILED_TO_PROCESS_RESPONSE;
                            case PointerIconCompat.TYPE_CELL /*1006*/:
                                return REQUEST_FAILED_TO_UPDATE_SIGNALS;
                            default:
                                return null;
                        }
                }
            }

            public final int zzhq() {
                return this.value;
            }
        }

        static {
            zzbbo.zza(zza.class, zzakg);
        }

        private zza() {
        }

        /* JADX WARNING: type inference failed for: r1v8, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzhu$zza>] */
        /* JADX WARNING: type inference failed for: r1v9, types: [java.lang.Object] */
        /* JADX WARNING: type inference failed for: r1v11, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzhu$zza>] */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: type inference failed for: r1v13, types: [com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzhu$zza>, com.google.android.gms.internal.ads.zzbbo$zzb] */
        /* JADX WARNING: type inference failed for: r1v16 */
        /* JADX WARNING: type inference failed for: r1v17 */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v12
          assigns: []
          uses: []
          mth insns count: 35
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
        public final java.lang.Object zza(int r1, java.lang.Object r2, java.lang.Object r3) {
            /*
                r0 = this;
                int[] r2 = com.google.android.gms.internal.ads.zzhv.zzakf
                r3 = 1
                int r1 = r1 - r3
                r1 = r2[r1]
                r2 = 0
                switch(r1) {
                    case 1: goto L_0x0042;
                    case 2: goto L_0x003c;
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
                com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzhu$zza> r1 = zzakh
                if (r1 != 0) goto L_0x002f
                java.lang.Class<com.google.android.gms.internal.ads.zzhu$zza> r2 = com.google.android.gms.internal.ads.zzhu.zza.class
                monitor-enter(r2)
                com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzhu$zza> r1 = zzakh     // Catch:{ all -> 0x002c }
                if (r1 != 0) goto L_0x002a
                com.google.android.gms.internal.ads.zzbbo$zzb r1 = new com.google.android.gms.internal.ads.zzbbo$zzb     // Catch:{ all -> 0x002c }
                com.google.android.gms.internal.ads.zzhu$zza r3 = zzakg     // Catch:{ all -> 0x002c }
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
                com.google.android.gms.internal.ads.zzhu$zza r1 = zzakg
                return r1
            L_0x0033:
                com.google.android.gms.internal.ads.zzhu$zza r1 = zzakg
                java.lang.String r3 = "\u0001\u0000"
                java.lang.Object r1 = zza(r1, r3, r2)
                return r1
            L_0x003c:
                com.google.android.gms.internal.ads.zzhu$zza$zza r1 = new com.google.android.gms.internal.ads.zzhu$zza$zza
                r1.<init>(r2)
                return r1
            L_0x0042:
                com.google.android.gms.internal.ads.zzhu$zza r1 = new com.google.android.gms.internal.ads.zzhu$zza
                r1.<init>()
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzhu.zza.zza(int, java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }
}
