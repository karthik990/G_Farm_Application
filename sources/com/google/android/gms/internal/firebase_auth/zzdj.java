package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.PhoneAuthCredential;

public final class zzdj extends AbstractSafeParcelable {
    public static final Creator<zzdj> CREATOR = new zzdi();
    private final String zzhy;
    private final PhoneAuthCredential zzkj;

    public zzdj(PhoneAuthCredential phoneAuthCredential, String str) {
        this.zzkj = phoneAuthCredential;
        this.zzhy = str;
    }

    public final PhoneAuthCredential zzdi() {
        return this.zzkj;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkj, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzhy, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
