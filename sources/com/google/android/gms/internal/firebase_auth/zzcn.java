package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.PhoneAuthCredential;

public final class zzcn extends AbstractSafeParcelable {
    public static final Creator<zzcn> CREATOR = new zzcm();
    private final String zzii;
    private final PhoneAuthCredential zzkj;

    public zzcn(String str, PhoneAuthCredential phoneAuthCredential) {
        this.zzii = str;
        this.zzkj = phoneAuthCredential;
    }

    public final String zzcp() {
        return this.zzii;
    }

    public final PhoneAuthCredential zzdi() {
        return this.zzkj;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzii, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzkj, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
