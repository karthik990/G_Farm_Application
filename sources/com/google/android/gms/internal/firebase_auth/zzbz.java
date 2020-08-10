package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzbz extends AbstractSafeParcelable {
    public static final Creator<zzbz> CREATOR = new zzby();
    private final String zzii;

    public zzbz(String str) {
        this.zzii = str;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
