package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzbr extends AbstractSafeParcelable {
    public static final Creator<zzbr> CREATOR = new zzbq();
    private final String zzig;
    private final String zzii;

    public zzbr(String str, String str2) {
        this.zzii = str;
        this.zzig = str2;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final String getPassword() {
        return this.zzig;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzig, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
