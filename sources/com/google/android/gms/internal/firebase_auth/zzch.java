package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzch extends AbstractSafeParcelable {
    public static final Creator<zzch> CREATOR = new zzcg();
    private final String zzhy;
    private final String zzif;

    public zzch(String str, String str2) {
        this.zzif = str;
        this.zzhy = str2;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
