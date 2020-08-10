package com.google.firebase.auth.internal;

import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.internal.firebase_auth.zzbl;
import com.google.firebase.auth.GetTokenResult;
import java.util.Collections;
import java.util.Map;

public final class zzan {
    private static final Logger zzjt = new Logger("GetTokenResultFactory", new String[0]);

    public static GetTokenResult zzdf(String str) {
        Map map;
        try {
            map = zzam.zzdd(str);
        } catch (zzbl e) {
            zzjt.mo26591e("Error parsing token claims", e, new Object[0]);
            map = Collections.EMPTY_MAP;
        }
        return new GetTokenResult(str, map);
    }
}
