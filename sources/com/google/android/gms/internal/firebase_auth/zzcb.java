package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.PhoneAuthCredential;

public final class zzcb extends AbstractSafeParcelable {
    public static final Creator<zzcb> CREATOR = new zzca();
    private final String zzjv;
    private final PhoneAuthCredential zzke;
    private final String zzkf;

    public zzcb(PhoneAuthCredential phoneAuthCredential, String str, String str2) {
        this.zzke = phoneAuthCredential;
        this.zzkf = str;
        this.zzjv = str2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzke, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzkf, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzjv, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
