package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzcj extends AbstractSafeParcelable {
    public static final Creator<zzcj> CREATOR = new zzci();
    private final String zzif;
    private final String zzig;
    private final String zzii;

    public zzcj(String str, String str2, String str3) {
        this.zzif = str;
        this.zzig = str2;
        this.zzii = str3;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String getPassword() {
        return this.zzig;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzig, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzii, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
