package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzbp extends AbstractSafeParcelable {
    public static final Creator<zzbp> CREATOR = new zzbo();
    private final String zzif;
    private final String zzii;

    public zzbp(String str, String str2) {
        this.zzii = str;
        this.zzif = str2;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzif, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
