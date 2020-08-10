package com.google.android.gms.internal.ads;

import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdRequest.Gender;
import com.google.ads.mediation.MediationAdRequest;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@zzadh
public final class zzzc {
    public static int zza(ErrorCode errorCode) {
        int i = zzzd.zzbvg[errorCode.ordinal()];
        if (i == 2) {
            return 1;
        }
        if (i != 3) {
            return i != 4 ? 0 : 3;
        }
        return 2;
    }

    public static MediationAdRequest zza(zzjj zzjj, boolean z) {
        Set hashSet = zzjj.zzapy != null ? new HashSet(zzjj.zzapy) : null;
        Date date = new Date(zzjj.zzapw);
        int i = zzjj.zzapx;
        Gender gender = i != 1 ? i != 2 ? Gender.UNKNOWN : Gender.FEMALE : Gender.MALE;
        MediationAdRequest mediationAdRequest = new MediationAdRequest(date, gender, hashSet, z, zzjj.zzaqe);
        return mediationAdRequest;
    }
}
