package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzcz extends AbstractSafeParcelable {
    public static final Creator<zzcz> CREATOR = new zzcy();
    private final String zzhy;

    public zzcz(String str) {
        this.zzhy = str;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
