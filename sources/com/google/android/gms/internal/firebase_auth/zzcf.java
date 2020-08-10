package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzcf extends AbstractSafeParcelable {
    public static final Creator<zzcf> CREATOR = new zzce();
    private final String zzkh;

    public zzcf(String str) {
        this.zzkh = str;
    }

    public final String zzs() {
        return this.zzkh;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkh, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
