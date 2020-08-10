package com.google.android.gms.internal.firebase_auth;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.firebase.auth.EmailAuthCredential;

public final class zzdh extends AbstractSafeParcelable {
    public static final Creator<zzdh> CREATOR = new zzdg();
    private final EmailAuthCredential zzkn;

    public zzdh(EmailAuthCredential emailAuthCredential) {
        this.zzkn = emailAuthCredential;
    }

    public final EmailAuthCredential zzdm() {
        return this.zzkn;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzkn, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
