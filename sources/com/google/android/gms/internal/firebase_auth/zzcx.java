package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzcx extends AbstractSafeParcelable {
    public static final Creator<zzcx> CREATOR = new zzcw();
    private final String zzkm;

    public zzcx(String str) {
        this.zzkm = str;
    }

    public final String zzdl() {
        return this.zzkm;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzkm, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
