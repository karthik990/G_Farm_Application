package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.ActionCodeSettings;

public final class zzdx extends AbstractSafeParcelable {
    public static final Creator<zzdx> CREATOR = new zzdw();
    private final String zzib;
    private final ActionCodeSettings zzkk;
    private final String zzku;

    public zzdx(String str, String str2, ActionCodeSettings actionCodeSettings) {
        this.zzib = str;
        this.zzku = str2;
        this.zzkk = actionCodeSettings;
    }

    public final String getIdToken() {
        return this.zzib;
    }

    public final String zzae() {
        return this.zzku;
    }

    public final ActionCodeSettings zzdj() {
        return this.zzkk;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzib, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzku, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzkk, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
