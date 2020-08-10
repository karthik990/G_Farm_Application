package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.ArrayList;
import java.util.List;

public final class zzeu extends AbstractSafeParcelable {
    public static final Creator<zzeu> CREATOR = new zzex();
    private final String zzjv;
    private final String zzrz;
    private final String zzsa;
    private final long zzsb;

    public zzeu(String str, String str2, String str3, long j) {
        this.zzrz = str;
        this.zzsa = Preconditions.checkNotEmpty(str2);
        this.zzjv = str3;
        this.zzsb = j;
    }

    public final String zzbk() {
        return this.zzrz;
    }

    public final String zzbl() {
        return this.zzsa;
    }

    public final String getDisplayName() {
        return this.zzjv;
    }

    public final long zzex() {
        return this.zzsb;
    }

    public static zzeu zza(zzr zzr) {
        zzeu zzeu = new zzeu(zzr.zzbk(), zzr.zzbl(), zzr.getDisplayName(), zzr.zzbm().getSeconds());
        return zzeu;
    }

    public static List<zzeu> zzd(List<zzr> list) {
        if (list == null) {
            return zzay.zzce();
        }
        ArrayList arrayList = new ArrayList();
        for (zzr zza : list) {
            arrayList.add(zza(zza));
        }
        return arrayList;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzrz, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzsa, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzjv, false);
        SafeParcelWriter.writeLong(parcel, 4, this.zzsb);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
