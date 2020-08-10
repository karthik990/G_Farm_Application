package com.google.android.gms.internal.firebase_auth;

final class zzhd {
    private static final Class<?> zzxa = zzhm();

    private static Class<?> zzhm() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public static zzhf zzhn() {
        if (zzxa != null) {
            try {
                return zzdk("getEmptyRegistry");
            } catch (Exception unused) {
            }
        }
        return zzhf.zzxf;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0014  */
    /* JADX WARNING: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x000e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.google.android.gms.internal.firebase_auth.zzhf zzho() {
        /*
            java.lang.Class<?> r0 = zzxa
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = "loadGeneratedRegistry"
            com.google.android.gms.internal.firebase_auth.zzhf r0 = zzdk(r0)     // Catch:{ Exception -> 0x000b }
            goto L_0x000c
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 != 0) goto L_0x0012
            com.google.android.gms.internal.firebase_auth.zzhf r0 = com.google.android.gms.internal.firebase_auth.zzhf.zzho()
        L_0x0012:
            if (r0 != 0) goto L_0x0018
            com.google.android.gms.internal.firebase_auth.zzhf r0 = zzhn()
        L_0x0018:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzhd.zzho():com.google.android.gms.internal.firebase_auth.zzhf");
    }

    private static final zzhf zzdk(String str) throws Exception {
        return (zzhf) zzxa.getDeclaredMethod(str, new Class[0]).invoke(null, new Object[0]);
    }
}
