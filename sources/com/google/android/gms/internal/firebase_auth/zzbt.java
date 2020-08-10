package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzbt extends AbstractSafeParcelable {
    public static final Creator<zzbt> CREATOR = new zzbs();
    private final String zzhu;
    private final String zzhy;

    public zzbt(String str, String str2) {
        this.zzhu = str;
        this.zzhy = str2;
    }

    public final String zzcn() {
        return this.zzhu;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzhu, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
