package com.google.android.gms.internal.firebase_remote_config;

import java.io.IOException;

public class zzm<T> extends zzf<T> {
    private final Object zzaf;

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v2, types: [com.google.android.gms.internal.firebase_remote_config.zzau] */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v4 */
    /* JADX WARNING: type inference failed for: r5v0, types: [com.google.android.gms.internal.firebase_remote_config.zzt] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.lang.String, com.google.android.gms.internal.firebase_remote_config.zzau]
      uses: [?[OBJECT, ARRAY], java.lang.String]
      mth insns count: 22
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
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected zzm(com.google.android.gms.internal.firebase_remote_config.zzk r8, java.lang.String r9, java.lang.String r10, java.lang.Object r11, java.lang.Class<T> r12) {
        /*
            r7 = this;
            r0 = 0
            if (r11 != 0) goto L_0x0005
        L_0x0003:
            r5 = r0
            goto L_0x0026
        L_0x0005:
            com.google.android.gms.internal.firebase_remote_config.zzau r1 = new com.google.android.gms.internal.firebase_remote_config.zzau
            com.google.android.gms.internal.firebase_remote_config.zzax r2 = r8.zzl()
            r1.<init>(r2, r11)
            com.google.android.gms.internal.firebase_remote_config.zzch r2 = r8.zze()
            com.google.android.gms.internal.firebase_remote_config.zzaz r2 = (com.google.android.gms.internal.firebase_remote_config.zzaz) r2
            java.util.Set r2 = r2.zzay()
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x001f
            goto L_0x0021
        L_0x001f:
            java.lang.String r0 = "data"
        L_0x0021:
            com.google.android.gms.internal.firebase_remote_config.zzau r0 = r1.zzab(r0)
            goto L_0x0003
        L_0x0026:
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r6 = r12
            r1.<init>(r2, r3, r4, r5, r6)
            r7.zzaf = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzm.<init>(com.google.android.gms.internal.firebase_remote_config.zzk, java.lang.String, java.lang.String, java.lang.Object, java.lang.Class):void");
    }

    /* renamed from: zzm */
    public zzk zzf() {
        return (zzk) super.zzf();
    }

    /* renamed from: zzd */
    public zzm<T> zzb(String str, Object obj) {
        return (zzm) super.zzb(str, obj);
    }

    public /* synthetic */ zzf zzc(String str, Object obj) {
        return (zzm) zzb(str, obj);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IOException zza(zzad zzad) {
        return zzd.zza(((zzk) zzf()).zzl(), zzad);
    }
}
