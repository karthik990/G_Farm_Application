package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public final class zzdt extends AbstractSafeParcelable {
    public static final Creator<zzdt> CREATOR = new zzds();
    private final String zzii;
    private final String zzks;

    public zzdt(String str, String str2) {
        this.zzks = str;
        this.zzii = str2;
    }

    public final String getProvider() {
        return this.zzks;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzks, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzii, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
