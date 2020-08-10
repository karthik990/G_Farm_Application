package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.ActionCodeSettings;

public final class zzct extends AbstractSafeParcelable {
    public static final Creator<zzct> CREATOR = new zzcs();
    private final String zzhy;
    private final String zzif;
    private final ActionCodeSettings zzkk;

    public zzct(String str, ActionCodeSettings actionCodeSettings, String str2) {
        this.zzif = str;
        this.zzkk = actionCodeSettings;
        this.zzhy = str2;
    }

    public final String getEmail() {
        return this.zzif;
    }

    public final ActionCodeSettings zzdj() {
        return this.zzkk;
    }

    public final String zzba() {
        return this.zzhy;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzif, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkk, i, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
