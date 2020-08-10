package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzcl extends AbstractSafeParcelable {
    public static final Creator<zzcl> CREATOR = new zzck();
    private final String zzii;
    private final zzfm zzki;

    public zzcl(String str, zzfm zzfm) {
        this.zzii = str;
        this.zzki = zzfm;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final zzfm zzdh() {
        return this.zzki;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzki, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
