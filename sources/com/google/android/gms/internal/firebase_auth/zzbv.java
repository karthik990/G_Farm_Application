package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzbv extends AbstractSafeParcelable {
    public static final Creator<zzbv> CREATOR = new zzbu();
    private final String zzhu;
    private final String zzhy;
    private final String zzkd;

    public zzbv(String str, String str2, String str3) {
        this.zzhu = str;
        this.zzkd = str2;
        this.zzhy = str3;
    }

    public final String zzcn() {
        return this.zzhu;
    }

    public final String zzdg() {
        return this.zzkd;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzhu, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkd, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
