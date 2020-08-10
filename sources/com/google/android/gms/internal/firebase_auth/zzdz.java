package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.zzf;

public final class zzdz extends AbstractSafeParcelable {
    public static final Creator<zzdz> CREATOR = new zzdy();
    private final String zzhy;
    private final String zzif;
    private final Status zzkv;
    private final zzf zzkw;

    public zzdz(Status status, zzf zzf, String str, String str2) {
        this.zzkv = status;
        this.zzkw = zzf;
        this.zzif = str;
        this.zzhy = str2;
    }

    public final Status getStatus() {
        return this.zzkv;
    }

    public final zzf zzdo() {
        return this.zzkw;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkv, i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkw, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
