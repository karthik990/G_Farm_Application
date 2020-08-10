package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;
import com.google.android.gms.internal.gtm.zzl;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class zzek extends zzbq {

    /* renamed from: ID */
    private static final String f1570ID = zza.REGEX_GROUP.toString();
    private static final String zzaiz = zzb.ARG0.toString();
    private static final String zzaja = zzb.ARG1.toString();
    private static final String zzajb = zzb.IGNORE_CASE.toString();
    private static final String zzajc = zzb.GROUP.toString();

    public zzek() {
        super(f1570ID, zzaiz, zzaja);
    }

    public final boolean zzgw() {
        return true;
    }

    public final zzl zzb(Map<String, zzl> map) {
        zzl zzl = (zzl) map.get(zzaiz);
        zzl zzl2 = (zzl) map.get(zzaja);
        if (zzl == null || zzl == zzgj.zzkc() || zzl2 == null || zzl2 == zzgj.zzkc()) {
            return zzgj.zzkc();
        }
        int i = 64;
        if (zzgj.zzg((zzl) map.get(zzajb)).booleanValue()) {
            i = 66;
        }
        int i2 = 1;
        zzl zzl3 = (zzl) map.get(zzajc);
        if (zzl3 != null) {
            Long zze = zzgj.zze(zzl3);
            if (zze == zzgj.zzjx()) {
                return zzgj.zzkc();
            }
            i2 = zze.intValue();
            if (i2 < 0) {
                return zzgj.zzkc();
            }
        }
        try {
            String zzc = zzgj.zzc(zzl);
            String zzc2 = zzgj.zzc(zzl2);
            String str = null;
            Matcher matcher = Pattern.compile(zzc2, i).matcher(zzc);
            if (matcher.find() && matcher.groupCount() >= i2) {
                str = matcher.group(i2);
            }
            return str == null ? zzgj.zzkc() : zzgj.zzi(str);
        } catch (PatternSyntaxException unused) {
            return zzgj.zzkc();
        }
    }
}
