package com.google.android.gms.internal.gtm;

final class zzqo {
    private static final Class<?> zzaxg = zzom();

    private static Class<?> zzom() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzqp zzon() {
        if (zzaxg != null) {
            try {
                return zzdc("getEmptyRegistry");
            } catch (Exception unused) {
            }
        }
        return zzqp.zzaxk;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0014  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x000e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.android.gms.internal.gtm.zzqp zzoo() {
        /*
            java.lang.Class<?> r0 = zzaxg
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = "loadGeneratedRegistry"
            com.google.android.gms.internal.gtm.zzqp r0 = zzdc(r0)     // Catch:{ Exception -> 0x000b }
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 != 0) goto L_0x0012
            com.google.android.gms.internal.gtm.zzqp r0 = com.google.android.gms.internal.gtm.zzqp.zzoo()
        L_0x0012:
            if (r0 != 0) goto L_0x0018
            com.google.android.gms.internal.gtm.zzqp r0 = zzon()
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzqo.zzoo():com.google.android.gms.internal.gtm.zzqp");
    }

    private static final zzqp zzdc(String str) throws Exception {
        return (zzqp) zzaxg.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
