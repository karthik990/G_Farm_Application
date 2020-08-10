package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzel extends zzfz {

    /* renamed from: ID */
    private static final String f1571ID = zza.REGEX.toString();
    private static final String zzajb = zzb.IGNORE_CASE.toString();

    public zzel() {
        super(f1571ID);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(String str, String str2, Map<String, zzl> map) {
        try {
            return Pattern.compile(str2, zzgj.zzg((zzl) map.get(zzajb)).booleanValue() ? 66 : 64).matcher(str).find();
        } catch (PatternSyntaxException unused) {
            return false;
        }
    }
}
