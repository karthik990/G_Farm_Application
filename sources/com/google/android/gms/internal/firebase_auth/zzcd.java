package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.PhoneAuthCredential;

public final class zzcd extends AbstractSafeParcelable {
    public static final Creator<zzcd> CREATOR = new zzcc();
    private final PhoneAuthCredential zzke;
    private final String zzkg;

    public zzcd(PhoneAuthCredential phoneAuthCredential, String str) {
        this.zzke = phoneAuthCredential;
        this.zzkg = str;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzke, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkg, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
