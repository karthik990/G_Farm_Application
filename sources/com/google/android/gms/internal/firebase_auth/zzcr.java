package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.ActionCodeSettings;

public final class zzcr extends AbstractSafeParcelable {
    public static final Creator<zzcr> CREATOR = new zzcq();
    private final String zzji;
    private final ActionCodeSettings zzkk;

    public zzcr(String str, ActionCodeSettings actionCodeSettings) {
        this.zzji = str;
        this.zzkk = actionCodeSettings;
    }

    public final String getToken() {
        return this.zzji;
    }

    public final ActionCodeSettings zzdj() {
        return this.zzkk;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzji, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkk, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
